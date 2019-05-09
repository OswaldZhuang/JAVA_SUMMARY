
-- 全文检索
--全文索引用于将数据库中整本书或整篇文章中的任意内容高效的找出来
--全文索引使用倒排索引实现
--倒排索引实现：
--在辅助表中存储（单词，单词所在文档ID）
--或者（单词，（单词所在文档的ID，在文档中的位置））（innodb实现）
--为了提高性能，innodb会维护FTS Index Cache（全文检索索引缓存），这是红黑树结构


--mysql全文检索语法：
select xx, xx ... from tab
match (col1, col2 ...)
against (expr [search_modifier])
--search_modifier:
--IN NATURAL LANGUAGE MODE
--IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION
--IN BOOLEAN MODE
--WITH QUERY EXPANSION