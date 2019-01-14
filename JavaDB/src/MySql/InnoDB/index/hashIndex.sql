-- InnoDB使用hash算法来对字典进行查找

-- 自适应hash索引，由数据库自己创建并使用，外界无法干预，hash索引只能够搜索等值查询，对于范围查找无能为力
-- 比如 SELECT * FROM TABLE WHERE INDEX_COL = 'XXX'; 可通过innodb_adaptive_hash_index来禁用或者启用，默认是启用的