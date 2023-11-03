ALTER TABLE `cn_knowledge_base_audit_log` 
MODIFY COLUMN `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL AFTER `op_type`;