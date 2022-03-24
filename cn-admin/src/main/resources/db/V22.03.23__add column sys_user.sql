/**
 * 2022年3月23日
 * sys_user 增加theme、lang字段
 *
 */

ALTER TABLE `sys_user` ADD `lang` VARCHAR(8) COMMENT 'en/zh' AFTER `pin`;
ALTER TABLE `sys_user` ADD `theme` VARCHAR(16) COMMENT 'light/dart等' AFTER `lang`;