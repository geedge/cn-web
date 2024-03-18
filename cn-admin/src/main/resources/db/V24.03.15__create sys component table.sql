SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_component`;

CREATE TABLE `sys_component` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `serverid` varchar(128) NOT NULL,
  `component` varchar(64) NOT NULL DEFAULT '' COMMENT '组件名称',
  `host` varchar(64) NOT NULL COMMENT 'ip地址',
  `port` int(10) NOT NULL COMMENT '端口号',
  `protocol` varchar(64) NOT NULL COMMENT 'http/https',
  `utime` bigint(20) NOT NULL COMMENT '最后更新时间',
  `hostname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主机名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;