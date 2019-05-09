--innodb的默认事务隔离级别为repeatable read

--事务可分为：
--扁平事务
--带有保存点的扁平事务
--链事务
--嵌套事务
--分布式事务


--事务的实现
--隔离性通过锁实现
--原子性和持久性通过redo log（重做日志）实现
--一致性通过undo log实现

--redo log
--在innodb中，当事务提交时，必须先将该事务的所有日志写入到redo log后才能进行commit操作
--（*对于redo log的写入是不断进行的）
--redo log是物理格式的日志，记录的是对每个页的修改，是幂等的
--redo log以block（块）的方式进行保存，每个block为512字节

--undo log
--undo log用于回滚操作和mvcc，undo log会产生redo log
--undo log只是将数据库逻辑地恢复到原来地样子
--innodb的每个rollback segment中有1024个undo log segment
--undo log分为'insert undo log'和'update undo log'，前者在事务提交后可以直接删除