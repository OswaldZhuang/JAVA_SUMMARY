package MySql.InnoDB;

public class InnoDB_Structure {

    /**
     * 后台线程:
     * 主要用于刷新内存中的数据,同步内存中的数据到磁盘中
     * 1.Master Thread
     * 2.IO Thread
     * 3.Purge Thread
     * 4.Page Cleaner Thread
     * >show engine innodb status;
     * >show variables like 'innodb_%io_threads'/'innodb_purge_threads';
     */

    /**
     * 缓冲池(innodb_buffer_pool):
     * data page(数据页)
     * index page(索引页)
     * insert buffer(插入缓冲)
     * lock info(锁信息)
     * 自适应hash索引
     * 数据字典信息
     *
     * >show variables like 'innodb_buffer_pool_size'/'innodb_buffer_pool_instances';
     *
     * 缓冲池是通过LRU(latest recent used)来进行管理的,也就是说:
     * 最近使用的页(默认大小为16KB)在LRU表的最前面,但实际上在实现的时候,新读取的页会被插入到LRU表的5/8的
     * 位置
     * >show variables like 'innodb_old_blocks_pct';
     */
}
