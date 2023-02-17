/**
 * 2022年12月28日
 * magic-api功能相关表创建
 *
 */
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `magic_api_file`;
CREATE TABLE `magic_api_file` (
    `file_path` varchar(512) NOT NULL,
    `file_content` mediumtext,
    PRIMARY KEY (`file_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;