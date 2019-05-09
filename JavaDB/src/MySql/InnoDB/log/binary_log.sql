--bin log是mysql应用层所产生的日志，和数据库引擎无关
--bin log主要用作：
--1.恢复：
--2.复制：集群中从数据库的同步

--bin log格式：
--STATEMENT 记录了逻辑sql语句
--ROW 记录了表行的更改情况
--MIXED 混合模式