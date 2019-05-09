--innodb中的行级锁分为：
--共享锁（shared）：持有该锁的事务可以读取数据，其他事务对同一行加共享锁的时候不会被阻塞
select * from xxx where ... lock in share mode
--排它锁（exclusive）：持有该锁的事务可以删除或更新数据，其他事务对同一行加任意锁都会被阻塞
select ... for update

--意向锁
--指的是将锁定的对象分为多个层次，库->表->页->行
--innodb的意向锁即为表锁，分为IS（意向共享锁），IX（意向排它锁）

--一致性非锁定读
--指通过行多版本控制的方式来读取数据
--如果行数据正在被delete或者update，那么读取操作不会等待行上的锁的释放
--相反的，而是去读取行的一个快照数据（即改行之前版本的数据，通过undo实现）
--这是innndb的默认读取方式（默认的事务隔离级别情况下，即repeatable read）
--但是需要注意的是read commited和repeatable read的读取的快照数据是不一样的
--read commited会读取最新的快照数据，而repeatable read会读取事务开始时的数据
--每行记录有多个版本（即历史数据），这被称之为MVCC（多版本并发控制,通过undo log实现）（乐观锁实现）

--一致性锁定读
--即读取操作之前会加锁
--共享锁和排他锁即一致性锁定读


--innodb锁的算法：
--1.record lock：单个行记录上的锁
--2.gap lock：间隙锁，锁定范围，但是不包含记录本身
--3.next-key lock：锁定一个范围和记录本身

--死锁
--两个或以上事务相互等待对方持有的资源的情况


--通过查询information_schema库下面的的innodb_locks, innodb_lock_waits, innodb_trx
--可以监控数据库的锁信息