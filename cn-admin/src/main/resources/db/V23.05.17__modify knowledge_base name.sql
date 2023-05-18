/**
 * 2023年5月17日
 * 知识库表名 字段内容变更
 * 同时新增知识库其它表信息
 */
ALTER TABLE `knowledge_base`
DROP COLUMN `knowledge_id`,
CHANGE COLUMN `id` `knowledge_id` int(11) NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`knowledge_id`) USING BTREE,
DROP COLUMN `remark`,
DROP COLUMN `ctime`,
DROP COLUMN `enable`,
CHANGE COLUMN `tag_name` `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
CHANGE COLUMN `uby` `op_user` int(10) NULL DEFAULT NULL COMMENT '修改人id' AFTER `cby`,
CHANGE COLUMN `utime` `op_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间' AFTER `op_user`,
CHANGE COLUMN `built_in` `is_built_in` int(1) NULL DEFAULT 0 COMMENT '是否内置 0-不内置 1-内置 默认 0',
ADD COLUMN `category` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'websketch\r\nai_tagging\r\nuser_defined' AFTER `name`,
ADD COLUMN `is_published` int(1) NULL,
ADD COLUMN `source` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL AFTER `is_built_in`,
ADD COLUMN `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL AFTER `source`,
ADD COLUMN `status` int(1) NULL AFTER `description`;

update knowledge_base set category = "user_define",status=0,is_published=0; 
update knowledge_base set source = "cn_ip_tag_user_defined" where tag_type like "%ip%";
update knowledge_base set source = "cn_domain_tag_user_defined" where tag_type like "%domain%";
update knowledge_base set source = "cn_app_tag_user_defined" where tag_type like "%app%";

ALTER TABLE `knowledge_base` 
DROP COLUMN `tag_type`;

rename table knowledge_base to cn_knowledge_base;

CREATE TABLE `cn_app_tag_user_defined` (
  `tag_value` varchar(128) NOT NULL,
  `app_name` varchar(128) NOT NULL,
  `op_time` datetime NOT NULL,
  `c_time` datetime NOT NULL,
  `op_user` int(10) NOT NULL,
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效：0-无效，1-有效\r\n',
  `knowledge_id` int(11) NOT NULL COMMENT '知识库唯一标识\r\n',
  PRIMARY KEY (`knowledge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `cn_domain_tag_user_defined` (
  `knowledge_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_value` varchar(128) NOT NULL,
  `domain` varchar(256) NOT NULL,
  `op_time` datetime NOT NULL,
  `c_time` datetime NOT NULL,
  `op_user` int(10) NOT NULL,
  `is_valid` int(1) DEFAULT 1,
  PRIMARY KEY (`knowledge_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `cn_ip_location_built_in` (
  `location_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `geoname_id` bigint(20) NOT NULL COMMENT '国家城市唯一标识',
  `addr_type` int(10) NOT NULL COMMENT 'IPV4=4,IPV6=6\r\n\r\n',
  `start_ip` varchar(40) NOT NULL COMMENT 'IP段的开始IP',
  `end_ip` varchar(40) NOT NULL COMMENT 'IP段的结束IP\r\n\r\n',
  `start_ip_binary` varbinary(16) NOT NULL COMMENT '起始网络字节序，HEX(INET6_ATON(start_ip))',
  `end_ip_binary` varbinary(16) NOT NULL COMMENT '结束网络字节序，HEX(INET6_ATON(end_ip))',
  `latitude` double NOT NULL COMMENT '纬度',
  `longitude` double NOT NULL COMMENT '经度',
  `coords` double NOT NULL COMMENT '坐标',
  `language` varchar(40) NOT NULL COMMENT '语言',
  `continent_abbr` varchar(256) DEFAULT NULL COMMENT '洲简称 ',
  `continent_full` varchar(256) DEFAULT NULL COMMENT '洲全称 ',
  `country_abbr` varchar(256) DEFAULT NULL COMMENT '国家/地区简称',
  `country_full` varchar(256) NOT NULL COMMENT '国家/地区全称	',
  `province_abbr` varchar(256) DEFAULT NULL COMMENT '省份简称	',
  `province_full` varchar(256) DEFAULT NULL COMMENT '省份全称	',
  `city_full` varchar(256) NOT NULL COMMENT '城市全称	',
  `subdivision_addr` varchar(2000) DEFAULT NULL COMMENT '四级以上地址	',
  `time_zone` varchar(128) DEFAULT NULL COMMENT '时区',
  `is_valid` int(1) NOT NULL COMMENT '0无效，1有效',
  `op_time` datetime NOT NULL,
  `op_user` int(10) NOT NULL,
  `knowledge_id` int(11) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `cn_ip_tag_user_defined` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_value` varchar(128) NOT NULL,
  `add_format` varchar(128) NOT NULL COMMENT 'Single range CIRD',
  `ip_first` varchar(40) NOT NULL,
  `ip_second` varchar(40) NOT NULL COMMENT 'IP地址2(CIDR时mask值)',
  `start_ip_binary` varbinary(16) NOT NULL COMMENT '起始网络字节序，HEX(INET6_ATON(start_ip))',
  `end_ip_binary` varbinary(16) NOT NULL COMMENT '结束网络字节序，HEX(INET6_ATON(end_ip))\r\n',
  `op_time` datetime NOT NULL,
  `c_time` datetime NOT NULL,
  `op_user` int(10) NOT NULL,
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效：0-无效，1-有效\r\n',
  `knowledge_id` int(11) NOT NULL COMMENT '知识库唯一标识\r\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `cn_knowledge_base_sync_version` (
  `knowledge_id` int(11) NOT NULL COMMENT '知识库唯一标识',
  `version` int(11) NOT NULL COMMENT '版本号，自增，用于描述知识库修改状态\r\n',
  `op_time` datetime NOT NULL,
  PRIMARY KEY (`knowledge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
