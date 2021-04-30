/**
 * 幂等
 * 多次调用同样的操作返回相同的结果
 */
public class Idempotent {
    /**
     * Http中GET天生幂等，不必考虑
     * POST需要人为实现幂等
     */

    /**
     * 乐观锁
     * 更新操作：
     * 利用版本号或者时间戳，只有大于当前版本的操作才能更行
     */

    /**
     * redis：
     * 插入操作
     * 插入redis中的set，由于set的中数据的唯一性，那么该操作天生幂等
     */

    /**
     * 唯一id
     * 插入操作：
     * 判断id在数据库中是否存在，如果存在那么不执行任何操作
     */

    /**
     * 去重表
     * 插入操作：
     * 利用unique key的特性，再次插入时会抛出约束异常，执行失败
     */
}