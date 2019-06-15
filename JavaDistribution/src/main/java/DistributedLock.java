/**
 * 分布式锁
 */
public class DistributedLock {

    /**
     * 基于数据库：
     * 乐观锁
     * 悲观锁
     */

    /**
     * 基于redis：
     * 单机模式：
     * setnx() 如果不存在那么在redis中加入一条数据
     * expire() 设置超时时间
     * 执行完成之后删除这个key
     * 集群模式：
     * REDLOCK
     */

    /**
     * 基于zookeeper：
     * 客户端在持久节点下创建临时节点，如果是序号最小的节点，那么获取锁
     */
}
