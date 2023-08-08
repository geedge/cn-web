/**
 * 2023年8月1日
 * 增加知识库审计日志表
 *
 */
DROP TABLE IF EXISTS `cn_knowledge_base_audit_log`;
CREATE TABLE `cn_knowledge_base_audit_log` (
	`log_id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`knowledge_id` INT ( 11 ) DEFAULT NULL,
	`op_type` VARCHAR ( 255 ) DEFAULT NULL COMMENT 'add：新增\r\nupdate：更新\r\ndelete：删除\r\nimport：导入',
	`description` VARCHAR ( 255 ) DEFAULT NULL,
	`commit_version` INT ( 11 ) DEFAULT NULL,
	`status` INT ( 1 ) DEFAULT NULL,
	`is_published` INT ( 1 ) DEFAULT NULL,
	`op_user` INT ( 10 ) DEFAULT NULL,
	`op_time` datetime DEFAULT NULL,
PRIMARY KEY ( `log_id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;