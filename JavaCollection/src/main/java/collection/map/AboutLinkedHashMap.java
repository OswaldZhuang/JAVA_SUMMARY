package collection.map;

public class AboutLinkedHashMap {
    /**
     * java.util.LinkedHashMap extends HashMap
     * 该数据结构很适合LRU缓存
     * 因为内部的节点会按照访问顺序进行排序（比如调用了get，put，...后），为双向链表结构
     * 在访问了某个节点后，该类调用afterNodeAccess在链表中追加节点，
     * 在新插入节点后，该类调用afterNodeInsertion来删除老旧的节点
     * 该类不是线程安全的
     *
     * protected boolean removeEldestEntry(Map.Entry<K,V> eldest)
     * 如果map需要移除最旧的entry，那么返回true，通常是移除掉头节点
     */

    /**
     * LRU
     * least recently used 最近最少使用
     * 即在最近访问的数据中，如果这个数据的访问时间距离现在较长，那么删除这条数据
     */
}
