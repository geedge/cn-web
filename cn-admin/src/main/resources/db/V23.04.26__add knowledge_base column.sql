/**
 * 2023年4月26日
 * 知识库字段增加 built_in enable字段
 * 同时初始化已有数据
 */
ALTER TABLE `knowledge_base` 
ADD COLUMN `built_in` int(10) NULL DEFAULT 0 COMMENT '是否内置 0-不内置 1-内置 默认 0',
ADD COLUMN `enable` int(10) NULL DEFAULT 1 COMMENT '是否启用 0-不启用 1-启用 默认 1';