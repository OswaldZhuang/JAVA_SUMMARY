-- 慢查询日志
-- 查询时间超过某个阈值的sql会被记录到慢查询日志当中，该阈值可以通过long_query_time来设定，默认为10
-- >mysqldumpslow xxx-slow.log
-- 但是默认是不会开启慢查询的，需要手动开启，其命令如下：
-- >set global slow_query_log='ON';