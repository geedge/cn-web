/**
 * 2023年6月27日
 * cn_ip_location_built_in表修改字段类型
 *
 */

ALTER TABLE `cn_ip_location_built_in`
MODIFY COLUMN `owner` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL;