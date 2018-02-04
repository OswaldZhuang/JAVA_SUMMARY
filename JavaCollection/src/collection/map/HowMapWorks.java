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
     * (load factor的承载量,实际上是加载因子*bucket数量),HashMap就会进行一次rehash,[NumberOfentry > loadFactor*BucketNumber]
     * 这个时候HahMap就会大概膨胀为原来的两倍大(bucket数量),加载因子的默认值是0.75，而init capacity为16
     * 6.使用足够大的HashMap来装载足够多的Entry比HashMap本身扩容的性能更好
     */
    
    /*
     * HashMap的工作原理
     * 
     */

}
