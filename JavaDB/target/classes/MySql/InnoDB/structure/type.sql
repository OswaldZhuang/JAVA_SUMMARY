-- mysql数据库的数据类型及其长度

-- 整数类型：
-- BIT（1 byte）
-- BOOL（1 byte）
-- TINY INT（1 byte）
-- SMALL INT（2 byte）
-- MEDIUM INT（3 byte）
-- INT（4 byte）
-- BIG INT（8 byte）

-- 浮点类型（存在精度损失，近似值）：
-- FLOAT（4 byte，8位精度）
-- DOUBLE（8 byte，16位精度）

-- 定点数
-- DECIMAL（16 byte，不存在精度损失，精确值）

-- 字符串
-- CHAR（固定长度，255个字符）
-- VARCHAR（固定长度，最多65535个字符）
-- TINYTEXT（可变长度）
-- TEXT（可变长度）
-- MEDIUMTEXT（可变长度）
-- LONGTEXT（可变长度）


-- 时间类型
-- DATE（年月日）
-- TIME（时分秒）
-- DATETIME（年月日，时分秒）
-- TIMESTAMP（自动存储修改时间）

-- 二进制类型
-- TINYBLOB
-- BLOB
-- MEDIUMBLOB
-- LONGBLOB