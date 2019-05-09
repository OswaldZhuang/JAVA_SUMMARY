package db;

/**
 * 数据库隔离等级
 */
public class Isolation {
    /**
     * 数据库事务的特性ACID
     * A（atomicity） 原子性
     * C（consistency） 一致性
     * I（isolation）隔离性
     * D（durability）持久性
     */
    /**
     * read uncommitted
     * 读未提交
     * 一个事务读取另一个事务还未提交的数据
     * 会发生"脏读"
     */

    /**
     * read committed
     * 读已提交
     * 一个事务读取另一个事务已经提交的数据
     * 会发生"不可重复读"(unrepeatable read),即一个事务两次读取相同的数据会得到不一样的结果
     * 比如在A事务中发生了一次读操作,读取B事务已经提交的数据M,之后C事务修改了M并且进行了提交
     * A事务再次读取M的时候就会发现值发生了变化
     */

    /**
     * repeatable read
     * 可重复读
     * 读事务开启后,不允许进行修改操作
     * 会发生"幻读"(phantom read),即一个事务内的两次读操作会返回不同的数据条目
     */

    /**
     * serializable
     * 串形化
     * 同一时刻,有且仅有一个事务能对数据库进行操作
     */
}
