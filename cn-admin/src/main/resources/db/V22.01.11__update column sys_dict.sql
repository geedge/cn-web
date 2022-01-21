/**
 * 2022年1月11日
 * sys_dict value字段数据类型varcher(256)修改为longtext
 *
 */

ALTER TABLE `sys_dict` MODIFY `value` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值	';