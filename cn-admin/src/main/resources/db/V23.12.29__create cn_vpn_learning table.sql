SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `cn_vpn_learning_ip`;
CREATE TABLE `cn_vpn_learning_ip`  (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `addr_format` varchar(128) NOT NULL,
   `ip1` varchar(40) NOT NULL COMMENT 'IP地址1(CIDR时mask值)',
   `ip2` varchar(40) NOT NULL COMMENT 'IP地址2(CIDR时mask值)',
   `start_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
   `end_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
   `plugin_id` bigint(20) NOT NULL COMMENT '插件ID，其他导入方式为0',
   `plugin_name` varchar(512) NOT NULL COMMENT '其他导入方式为\'\'',
   `vpn_service_name` varchar(256) NOT NULL,
   `method` varchar(128) NOT NULL,
   `confidence` varchar(128) NOT NULL,
   `op_time` datetime NOT NULL,
   `c_time` datetime NOT NULL,
   `op_user` int(10) NOT NULL,
   `is_valid` int(1) NULL DEFAULT 1 COMMENT '是否有效：0-无效，1-有效',
   `knowledge_id` int(11) NOT NULL COMMENT '知识库唯一标识，外键。',
   PRIMARY KEY (`id`),
   INDEX(`start_ip_binary`) USING BTREE,
   INDEX(`end_ip_binary`) USING BTREE,
   FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `cn_vpn_learning_domain`;
CREATE TABLE `cn_vpn_learning_domain`  (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `domain` varchar(512) NOT NULL,
   `plugin_id` bigint(20) NOT NULL COMMENT '插件ID，其他导入方式为0',
   `plugin_name` varchar(512) NOT NULL COMMENT '其他导入方式为\'\'',
   `op_time` datetime NOT NULL,
   `c_time` datetime NOT NULL,
   `op_user` int(10) NOT NULL,
   `is_valid` int(1) NULL DEFAULT 1 COMMENT '是否有效：0-无效，1-有效',
   `knowledge_id` int(11) NOT NULL COMMENT '知识库唯一标识，外键。',
   PRIMARY KEY (`id`),
   FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

SET FOREIGN_KEY_CHECKS = 1;