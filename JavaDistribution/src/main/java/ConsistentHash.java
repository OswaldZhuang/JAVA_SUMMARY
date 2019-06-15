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
     * 于是无法查询到数据转而去db查询，db压力过大而崩溃(缓存穿透)，
     */

    /**
     * 解决方案（一致性hash）
     * 解决上述问题的关键在于，对于cache server的访问不应该依赖于server的数量，当增加或者
     * 减少server的时候，重新分配的影响应该降到最低。
     * 方法如下：
     * 将key值的hash映射到一个环中（比如hash为0，那么其对应的角度为0，hash为某个大数，其对应
     * 的角度为360）
     * 同样的，将server也映射到环中（可以采用server ip的hash值）
     * 那么逆时针方向离key最近的哪个server就是需要存储或者查询的server
     * 为了使得key在分布式缓存中分布的更加均匀，每个server不止要对应一个角度，角度的多少对应server的
     * 权重，比如server的处理能力越强，那么角度更多
     * 于是当某个server挂掉后，依赖于该server的key需要重新分配，而原来的key不受任何影响
     * 而新增加机器，仅仅有k/n的key会重新分配，k是key的数量，n是server数量
     */

}
