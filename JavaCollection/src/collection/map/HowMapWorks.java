package collection.map;


public class HowMapWorks {
    
    /*
     * Java的Map系列中
     * 所有的Map实现的父接口都是java.util.Map
     * 该接口提供了一些Map的基本操作和内部接口Entry<K,V>(该接口有一个重要方法hashCode()),也就是存储map内部key-value的实例
     * Map是不允许重复key的,但并非不允许key为null(视具体实现而定)
     * AbstractMap是Map接口的大致实现,许多重要的Map都继承自AbstractMap,
     * AbstractMap提供了三种视图:keySet,values,key-valueSet(EntrySet)
     * 许多Map的具体实现会重写AbstractMap中的一些方法(比如containsKey,AbstractMap
     * 的实现实际是遍历了整个entrySet去查找的)
     */
    /*
     * HashMap继承了AbstractMap抽象类,实现了Map接口,有如下特点:
     * 1.允许null的key和value
     * 2.无序并且随着时间推移也不保证保持顺序
     * 3.get和put方法的时间复杂度为常量
     * 4.遍历视图需要bucket的数量加上Entry的数量
     * 5.影响HashMap的因素有两个:init capacity(初始的bucket数量),load factor(加载因子)
     * 注:加载因子用于衡量bucket数量自动增加之前HashMap允许"装入"的Entry数量,当Entry的数量超过当前容量
     * (load factor的承载量,实际上是加载因子*bucket数量),HashMap就会进行一次rehash,[NumberOfEntry > loadFactor*BucketNumber]
     * 这个时候HahMap就会大概膨胀为原来的两倍大(bucket数量),举例来说,假如当前HashMap的bucket数量为100,那么当整个HashMape的Entry个数超过100*loadFactor
     * 的时候,HashMap就会自动rehash,然后扩容
     * 加载因子的默认值是0.75，而init capacity为16
     * 6.使用足够大的HashMap来装载足够多的Entry比HashMap本身扩容的性能更好
     * 7.HashMap的bucket如果变得过于大的话,那么它的bucket(bin)下面的节点将转化为树形(红黑树,这样做的好处在于查找速度更快),(之前是链式的)
     * 而如果bucket变小的话那么节点又会变回链式的,这个过程是通过resize()方法实现的
     * 
     */
    
    /*
     * HashMap的工作原理
     * 在该类中有几个容易让人迷惑的关键词:
     * TREEIFIED 树化
     * CAPACITY 容量,该容量指的是bucket的个数
     * THRESHOLD 阈值,即临界值,是触发resize的值,为(capacity * load factor)
     * 
     * HashMap中几个比较重要的常量值
     * static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; 初始容量(初始bucket的数量),为16,实际上HashMap的初始容量必须时2的次方
     * static final float DEFAULT_LOAD_FACTOR = 0.75f;默认的加载因子的值为0.75
     * static final int TREEIFY_THRESHOLD = 8;树化的阈值为8,即当一个bucket下面的Entry数量超过8之后,这个bucket就会转化为树
     * 为什么这个值为8?理想状态下HashMap中的每个bucke中Entry的数量符合泊松分布,当loadFactor为0.75时,bucket中元素为8的概率基本为0
     * (具体数据可以参考HashMap的源代码),所以正常(理想)情况下一个bucket里面的Entry数量时几乎不可能为8的,如果为8,那么hash方法就设计的有
     * 问题,bucket下面的Entry就需要转化为树形结构
     * static final int UNTREEIFY_THRESHOLD = 6;由树转化为链式结构的阈值,在resize()时,如果bucket中Entry的数量小于这个值那么就会转化为链式结构
     * static final int MIN_TREEIFY_CAPACITY = 64;最小的树化容量,只有HashMap中Entry数量的总和大于等于64的时候才会树化,换句话说,树化的条件不仅
     * 要求一个bucket下面的Entry数量大于8,同时也要求所有的Entry的数量至少为64,这样做是为了防止一个bucket中的Entry数量过多,如果仅仅是一个bucket中的Entry
     * 数量大于8而不满足总体数量大于等于64,那么就不会树化,而是rehash.这个值应该至少是TREEIFY_THRESHOLD的4倍以避免树化和扩容之间的冲突
     * 
     * 
     * HashMap中用于存储值的内部类为Node<K,V>(也就是上文所指的Entry),它存储了节点的hash值,key,value以及下一个节点的引用(next)
     * hash的计算是通过Objects.hashCode(key) ^ Objects.hashCode(value)实现的
     * 而equals是通过Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue())实现的
     * 
     * HashMap中的bucket的存储方式是数组,通过transient Node<K,V>[] table;
     * transient int modCount; 该变量存储的是HashMap被改变的次数(所谓改变就是HashMap中Entry的数量改变,或者rehash,树化等操作),这个值
     * 用于在iterator迭代时的快速失败
     * int threshold;需要rehash的阈值
     * 
     * HashMap中核心的构造函数public HashMap(int initialCapacity, float loadFactor) 该构造函数允许你制定加载因子和初始bucket数量(容量)
     * 但需要注意,这个初始容量会被HashMap的static final int tableSizeFor(int cap)方法转化为2的次方的数(比如16,32,64...)
     * 
     * get方法实现如下:
     * public V get(Object key) {
     *   Node<K,V> e;
     *   return (e = getNode(hash(key), key)) == null ? null : e.value;
     * }
     * 其中的hash(key)方法如下:
     * static final int hash(Object key) {
     *   int h;
     *   //实际上该运算等价于key的hashCode的高16位与之低16位进行亦或运算
     *   return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
     * }
     * 该方法重新计算了一遍key的hash值,也就是说其实HashMap其实并没有用key本身的hashCode方法(由于bucket的个数时2的次方个,因此
     * 直接采用key的hashCode很容易引起冲突(collide,为什么呢?),所以需要做如上转化)
     * 
     * final Node<K,V> getNode(int hash, Object key)方法的具体实现为:
     * 1.首先通过hash值查找在table中的位置(采用((n - 1) & hash)确定数组的索引位置,n是数组长度,这样做的好处在于由于n是2的次方
     * 所以(n - 1) & hash等价于hash%n(取模使得hash的分布更加均匀),但是其速度更快,),
     * 然后检查第一个Node(first),如果该节点的的值符合要求则返回第一个节点
     * 2.如果不符合要求,检查first.next是否为空,为空就直接返回空
     * 3.如果不为空,那么检查这个节点是否是TreeNode,如果是,那么就返回TreeNode的getTreeNode(hash,key)方法,也就是
     * 如果Node是一个树节点,那么调用树节点(TreeNode)的相关查找方法
     * 4.如果不是一个树节点,那么就线性遍历这个bucket(不断调用next方法)
     * 
     * 而public boolean containsKey(Object key)方法实际上也是调用getNode方法,判断回返值是否为空
     * 
     * put方法实现如下:
     * public V put(K key, V value) {
     *   return putVal(hash(key), key, value, false, true);
     * }
     * 如果HashMap中已经包含了相同的key,那么,现在的操作会覆盖掉原来的key-value
     * final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict)的具体实现为:
     * 其中:
     * onlyIfAbsent表示:如果为true,将不会改变已经存在的值(即设置需不需要覆盖)
     * evict表示:如果为false,则表示table处于creation mode
     * 返回值是之前的Node的value值,如果插入的值之前不存在,则返回空,如果存在,视情况而返回相应的值
     * 1.首先判断table是否为空,如果为空的话,那么调用resize方法重新构造HashMap然后得到table的长度
     * 2.判断当前table中的相应hash位置(也就是(n - 1) & hash位置)是否为空,如果为空的话就在这个位置新建一个Node
     * 3.如果不为空,那么再首先判断当前位置的Node的hash值与key值是否和要插入Node的hash值与key值相同,如果相同,那么把
     * 当前位置的Node赋值给一个临时变量e
     * 4.如果不满足上述条件,那么判断这个节点是否 是TreeNode,如果是,则调用TreeNode的putTreeVal方法插入节点,并把返回值
     * 赋给临时变量e
     * 5.如果仍然不满足上述条件(也就说明table相应的位置不为空,且这个位置的值和插入的值不一样,且这个bucket不是树形),那么不断
     * 遍历链式结构,在这期间:
     * 首先判断如果下一个Node为空,那么根据插入值创造一个新的Node,追加在最后一个Node后面,之后检查如果bucket中
     * Node的数量大于树化的阈值(TREEIFY_THRESHOLD),那么就会对这个bucket进行树化,之后跳出循环.
     * 然后再判断当前遍历到的Node的hash值,key值是否和要插入的hash值,key值相同,如果相同,则跳出循环.
     * 遍历完成后,判断临时变量e的值是否为空(如果为空则说明bucket中不存在相同的Node),如果不为空(则HashMap中存在相同的Node,即hash
     * 值,key值都相同),那么首先得到HashMap中相应的Node的value值,如果这个value为空或是覆盖模式(onlyIfAbsent==false),
     * 那么就会把要插入的value赋值给这个Node的value,然后返回这个值,方法结束
     * 6.如果bucket中不存在相同的Node,会将modCount加1,表示进行了一次改变,再判断如果当前的Node数量大于threshold,那么就会进行resize()操作
     * 7.返回空值,方法结束
     * 
     * resize方法实现如下:
     * final Node<K,V>[] resize()
     * 该方法会对table进行2倍扩容(如果容量为0的话就会进行初始化)
     * 在这个方法中,会对threshold和table的长度进行修改(具体地说就是变为原来的两倍)
     * 然后是开始转移原来的bucket中的Entry(使得Entry随着扩容而能够比较均匀的分散开),具体做法如下:
     * 从0开始,遍历原来的table数组,用一个临时变量e存储数组中的值的引用,然后清空原来的引用,循环的过程中会有如下操作:
     * 1.如果e没有下一个entry,那么就把它赋值给新的table中的某个位置(具体是newTab[e.hash & (newCap - 1)],这个位置可能是
     * [原来的位置]也可能是[原来的位置+原来的长度],这样做的另一个好处是原来索引值一样(即hash冲突)的Entry会因为此次计算重新分配位置)
     * 2.如果e是一个树节点(TreeNode),那么调用TreeNode#split方法
     * 3.剩下的情况(e不是树节点并且其之后还有节点,也就是长度大于一的链式结构),则不断遍历该链表,循环过程操作如下:
     *   3.1.如果计算出来的索引值在原位置(即e.hash & oldCap) == 0),那么就会保持原位置不动
     *   3.2.如果计算出来的索引值在[原来的位置+原来的长度]的位置,那么就会将它移到那个位置下
     *   *这个过程中链表中Entry的顺序其实是没有发生改变的(详见代码实现)
     */
    

}
