/**
 * 一致性hash
 */
public class ConsistentHash {
    /**
     * 分布式缓存（key-value）的场景：
     * 客户端需要查询数据（使用key值），首先经过的是缓存，如果缓存中没有数据，那么去db中查询
     * 经过缓存的时候，由于有多台server，那么需要一种方式来确定去哪一台查询
     * 一般地，将key的hash值与server数量取模后得到的数值即为要访问哪台server，
     * （插入数据亦然）
     * 但是，当集群中有server挂掉后，由于server数量变化导致客户端的请求被重新分配到不同server
     * 于是无法查询到数据转而去db查询，db压力过大而崩溃，
     */
}
