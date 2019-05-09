--innodb的关键特性

--insert buffer 插入缓冲
--对于辅助索引的插入或更新操作，并不是直接插入到辅助索引页中，
--而是首先判断需要插入的索引页是否在缓冲池中，如果存在，那么直接插入
--如果不存在，则先放入到一个insert buffer对象中（实际上是B+树），然后再以一定频率去和索引页进行合并操作
--辅助索引必须满足：不是唯一的

--double write 双写
--adaptive hash index 自适应hash索引
--async io 异步io
--flush neighbor page 刷新临接页