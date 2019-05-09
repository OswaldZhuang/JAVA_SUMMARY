package concurrence.chapter5_basicmodule;


public class AboutConcurrentHashMap {
    /**
     * java.util.concurrent.ConcurrentHashMap
     * 该类实现了HashMap的线程安全性
     * 主要考虑以下几个方面的线程安全性：
     * 1.初始化
     * 2.put操作
     * 3.get操作
     * 4.扩容
     *
     * 基于每个bin加锁的主要缺点是：在这个锁保护下的其他node的更新操作会暂停
     *
     * resize通过转移bin来进行，一个接一个，从一个table到另一个table
     * 然而，线程在此之前，会声明一个小的索引块（通过transferIndex字段），以此来减小竞争
     * 分代的标志sizeCtl保证线程的resize不会相互覆盖，由于是两倍扩容，那么bin要么是在原来的
     * 位置，要么是在移动了2的次方数的位置
     * 我们通过找到可重用的久节点来去除不必要的节点创建，平均而言，只有六分之一的节点需要被复制
     * 被替换的节点会被迅速回收掉
     * 在转移的时候，旧的table的bin只包含特殊的forwarding节点（其hash值为MOVED），该节点的key为
     * 下一个table的引用。
     * 在遇到forwarding节点的时候，访问和插入操作会重新开始（重试），使用新的table
     * 每个bin转移需要自己的锁，这可能会导致在resize的时候暂停等待锁的释放。然而，因为其他线程可以
     * 加入和帮助resize而不是竞争锁，因为resize的改进，平均等待时间会变短。
     *
     * 转移操作同样也必须保证所有的新旧table中可访问的bin可被任何遍历所使用。遍历从后往前。
     * 在看到forwarding的节点的时候，那么直接转移到新的table而不会重复遍历
     * 为了保证没有移动中的节点被跳过，甚至因为移动变得无序，遍历过程中在遇到第一个forwarding节点的时候栈（TableStack）被创建，
     * 如果之后再遇到当前的table的时候用来维护其位置。
     *
     * 元素个数通过使用特殊的LongAdder来维护，我们需要包含专门的设计而不是仅仅用LongAdder来访问会导致创建多个CounterCells的
     * 隐含竞争的计数器。计数器机制避免了在更新上的竞争但是会遇到cache thrashing（cache和memory之间的同步）如果在并发访问的情况下读太过频繁
     * 为了避免过于频繁的读，只有当添加节点到一个包含2个或更多节点的bin时候，会尝试竞争下的resize。
     * 在一致的hash分布下，上述情况发生的可能性为13%，意味着只有八分之一的put操作要查看阈值。
     */

    /**
     *
     * transient volatile Node<K,V>[] table;
     *
     * private transient volatile int sizeCtl;
     * 初始化和resize的控制位，
     * 当负数的时候，表示正在被初始化或者resize（-1表示正在初始化 -(1+resize的线程数)表示正在被resize（严格意义来说低16位是这样的））
     * 否则，当table为空的时候，该值为初始化的table大小或者0，初始化完成后，该值变为下一次resize时table的大小
     */

    /**
     * private final Node<K,V>[] initTable()
     * 该方法用于初始化tab，过程如下：
     * 在table为空或者其长度为0的情况下，不断循环，期间：
     * 如果sizeCtl小于0（意味这正在被初始化），那么线程让步
     * 否则cas将sizeCtl设置为-1，成功的话，再次判断table是否为空，如果仍然成立，那么初始化数组
     * 并将sizeCtl的值变为n - (n >>> 2) n为数组长度
     */

    /**
     * final V putVal(K key, V value, boolean onlyIfAbsent)
     * 不断循环，期间：
     * 如果table为空或者table长度为0，那么初始化table
     * 如果需要插入的table的位置的第一个节点值为空，那么cas插入节点，成功的话，跳出循环
     * 如果上述位置的第一个节点值为MOVED，那么调用helpTransfer帮助resize
     * 否则，synchronized上述节点，再次cas判断如果上述节点没有变，那么按照正常方式追加节点（详见hashmap）
     * 判断是否需要将bin树化(不一定会树化，也有可能扩容，扩容即在这一步完成)
     * 最后调用addCount增加元素总个数
     */

    /**
     * private final void treeifyBin(Node<K,V>[] tab, int index)
     * table长度小于64，则调用tryPresize尝试扩容
     * 否则将节点设置为TreeNode
     */

    /**
     * private final void tryPresize(int size)
     * 具体的扩容方法
     * 当izeCtl大于0的时候，不断循环，期间：
     * 如果table为空，那么初始化table
     * 如果扩容的目标大小小于sizeCtl或者超出最大限制，那么直接跳出
     * 如果table未变（即引用未变），首先通过resizeStamp方法拿到resizeStamp（通过table长度拿到），
     * 如果resizeStamp小于0（正在扩容？），再判断一堆无法扩容的条件成不成立，成立的话直接跳出，否则cas将sizeCtl变为resizeStamp+1，然后调用transfer转移
     * 如果resizeStamp大于等于0（第一扩容？），那么将sizeCtl变为(rs << 16) + 2)
     */

    /**
     * static final int resizeStamp(int n)
     * 返回一个扩容的校验值，这个值最终会成为sizeCtl的高16位（最高位为1，即sizeCtl为负），而sizeCtl的低16为（1+扩容线程数）
     * Integer.numberOfLeadingZeros(n) | (1 << 15)
     */

    /**
     * final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f)
     * 判断如果在该bin上没有线程在进行转移操作（具体通过sizeCtl和resizeStamp），那么先cas更新sizeCtl再调用transfer进行resize
     */

    /**
     * private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab)
     * 首先根据cpu的核数和table长度计算步长（最小步长为16），
     * 如果nextTab为空，那么初始化nextTab（长度变为原来2倍）
     * 不断循环，期间：
     * 确定需要复制转移的下标位置i和界限bound
     * 判断是否已经转移完成，如果是finishing的状态，那么将table指向新的table并且重置sizeCtl，并直接返回，如果是当前线程完成了，
     * 那么cas将sizeCtl变为sizeCtl-1
     * 如果i处的node为空，那么cas该节点变为ForwardingNode
     * 如果i处的node的hash为MOVED，则表示已经被转移了
     * 复制节点到新的table上（bin中节点的顺序能够保持一致）
     * 如果是TreeBin，那么依照红黑树的方式进行复制
     */

    /**
     * private final void addCount(long x, int check)
     *
     */

}
