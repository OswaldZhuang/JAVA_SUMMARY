-- 幻读问题
-- 在同一个事务下，两次相同的读操作所返回的结果不一致（结果指的是查询的行数）
-- InnoDB的事务隔离级别为REPEATABLE READ（可重复读）
-- 采用Next Key Locking算法：
-- 在某个事务进行查询的时候，innodb的加锁方式为对where后面的条件加锁，
-- 比如 select * from students where age > 13;那么，在该事务提交之前，所有要对age > 13的写入事务都会阻塞

--比如一张表的索引值为10，11，13，20
--那么next key lock下锁住的区间为(-无穷，10] (10, 11] (11, 13] (13, 20] (20, +无穷]

--但是当查询的索引是唯一属性时，next key lock会被优化成为record lock