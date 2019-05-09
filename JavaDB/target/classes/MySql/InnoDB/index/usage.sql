--单列索引
    --普通索引
    --唯一索引：值必须唯一，但是允许为空值
    CREATE UNIQUE INDEX <index_name> ON `<table_name>`(`column_name`);
    --主键索引：特殊的唯一索引，但是不允许为空值，实际上就是primary key

--组合索引
CREATE INDEX <index_name> ON `<table_name>`(`column_name1`,`column_name2`,`column_name3`);
--查询时遵循"最左前缀"，上述相当于创建了(`column_name1`,`column_name2`,`column_name3`)，(`column_name1`,`column_name2`)
--和(`column_name1`)三个索引

--EXPLAIN <sql_statement> 以查看sql的执行计划