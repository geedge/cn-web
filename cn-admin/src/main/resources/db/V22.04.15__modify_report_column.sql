/**
 * 2022年4月15日 
 * report job以及report temp表修改字段类型 text-longtext
 *
 */
ALTER TABLE `report_job` 
MODIFY COLUMN `html_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'html模板';

ALTER TABLE `report_temp` 
MODIFY COLUMN `html_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'html模板';