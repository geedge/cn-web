/**
 * 2022年4月26日 
 * report temp表修改字段类型 text-longtext
 *
 */
ALTER TABLE `report_temp` 
MODIFY COLUMN `config` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置项';