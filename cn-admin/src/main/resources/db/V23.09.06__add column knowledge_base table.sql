/**
 * 2023年9月6日
 * 增加color字段
 *
 */

ALTER TABLE `cn_knowledge_base`
ADD COLUMN `color` varchar(128) NULL AFTER `status`;

ALTER TABLE `cn_knowledge_base_audit_log`
ADD COLUMN `color` varchar(128) NULL AFTER `op_time`;