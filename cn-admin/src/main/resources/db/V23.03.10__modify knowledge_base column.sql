/**
 * 2023年3月10日
 * 修改知识库字段字符集规则 区分大小写
 */
ALTER TABLE `knowledge_base` 
MODIFY COLUMN `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称' ,
MODIFY COLUMN `tag_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '实体类型，ip、domain、app';