package cache;

/**
 * 数据库和缓存的双写一致性方案
 */
public class DoubleWriteBetweenDBandCache {
    /**
     * 场景：业务代码更改了数据，为了确保数据的一致性，应该如何跟新数据库和缓存
     */

    /**
     * 1.给缓存设置过期时间，从而确保最终一致性
     * 业务代码的所有的写入操作都以数据库为准，缓存达到过期时间会自动被删除
     * 后面的请求会读取数据库并更新缓存
     */

    /**
     * 2.先删除缓存，再更新数据库
     *
     * 简单先删除缓存，再更新数据库仍然可能会存在脏数据的问题，比如：
     * 1.a更新了数据，变为k，v2，然后将k，v1从缓存删除
     * 2.b从缓存中请求k数据，发现不存在，于是从数据库中读取k，然后再跟新缓存，变为k，v1
     * 3.a更新数据库，变为k，v1
     *
     * 于是需要"延时双删策略"，即：
     * 1.删除缓存
     * 2.跟新数据库
     * 3.等待一段时间
     * 4.再次删除缓存（可采用异步的方式）
     * 目的是为了解决读请求所造成的脏数据的问题
     *
     * 但是如果出现更新缓存失败的情况，那么脏数据的问题还是会存在的
     */

    /**
     * 3.先更新数据库，再删除缓存（Cache Aside Pattern）
     *
     * 仍然，出现更新缓存失败的情况下，脏数据还是会存在
     */

    /**
     * 更新缓存失败：
     * 采用重试机制，
     * 1.业务代码在更新缓存失败后将删除缓存k的指令发送到一个mq中，然后自己监听这个mq
     * 缺点在于代码入侵过于严重
     * 2.另起一个新的进程用于订阅数据库的bin log，由这个进程负责对缓存进行删除
     */

}