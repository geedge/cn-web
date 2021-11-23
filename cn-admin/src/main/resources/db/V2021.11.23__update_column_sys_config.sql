
/**
 * 2021年11月23日
 * sys_config ckey字段添加唯一索引
 */

ALTER TABLE `sys_config` 
ADD UNIQUE INDEX `c_key`(`ckey`) USING BTREE;