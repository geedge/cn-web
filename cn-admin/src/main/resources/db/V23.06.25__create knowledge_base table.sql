/**
 * 2023年6月25日
 * 整合知识库表创建
 *
 */
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cn_knowledge_base
-- ----------------------------
DROP TABLE IF EXISTS `cn_knowledge_base`;
CREATE TABLE `cn_knowledge_base`  (
    `knowledge_id` int NOT NULL AUTO_INCREMENT COMMENT '知识库唯一标识',
    `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `category` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `source` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_built_in` int NULL DEFAULT 0 COMMENT '是否内置：0-非内置，1-内置',
    `is_published` int NULL DEFAULT 0 COMMENT '是否发布：0-否，1-是',
    `status` int NOT NULL DEFAULT 0 COMMENT '状态：0-禁用，1-启用',
    PRIMARY KEY (`knowledge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_knowledge_base_sync_version`;
CREATE TABLE `cn_knowledge_base_sync_version`  (
    `knowledge_id` int NOT NULL AUTO_INCREMENT COMMENT '知识库唯一标识',
    `version` int NOT NULL COMMENT '版本号，自增，用于描述知识库修改状态',
    `op_time` datetime NOT NULL,
    PRIMARY KEY (`knowledge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_app_category_built_in`;
CREATE TABLE `cn_app_category_built_in`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `app_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `app_id` bigint NOT NULL,
    `app_category` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `app_subcategory` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `app_risk` int NULL DEFAULT NULL COMMENT '1-5，值越高风险越大',
    `app_description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `app_longname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `app_technology` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `app_is_protocol` int NULL DEFAULT NULL,
    `app_company` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `app_company_category` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_valid` int NOT NULL DEFAULT 1 COMMENT '是否有效：0-无效，1-有效',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `app_name`(`app_name`) USING BTREE COMMENT '索引字段\r\n索引字段\r\n',
    INDEX `fk_app_category_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_app_category_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_app_tag_user_defined`;
CREATE TABLE `cn_app_tag_user_defined`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `tag_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `app_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_valid` int NULL DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `app_name`(`app_name`) USING BTREE,
    INDEX `fk_app_tag_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_app_tag_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_app_tag_user_defined`;
CREATE TABLE `cn_app_tag_user_defined`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `tag_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `app_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_valid` int NULL DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `app_name`(`app_name`) USING BTREE,
    INDEX `fk_app_tag_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_app_tag_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_domain_tag_user_defined`;
CREATE TABLE `cn_domain_tag_user_defined`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `tag_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `domain` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_valid` int NULL DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_domain_tag_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_domain_tag_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_fqdn_category_built_in`;
CREATE TABLE `cn_fqdn_category_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT,
    `fqdn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目前数据来源包括两种\r\n\r\nbrightcloud\r\n\r\nurl_clf（此来源仅提供类别及类别组信息）',
    `query_success` tinyint(1) NULL DEFAULT NULL COMMENT 'websketch查询方提交的域名，由于网络等原因查询brightcloud失败',
    `match_pattern` int NULL DEFAULT NULL COMMENT '1-右匹配\r\n\r\n2-全匹配',
    `reputation_score` int NULL DEFAULT NULL COMMENT '相关因素：\r\n\r\nthreat history of pages on the domain\r\n\r\nage of observance\r\n\r\nuser traffic\r\n\r\ngeo-location\r\n\r\nassociated networks\r\n\r\nInternal and external links\r\n\r\ncontextual and behavioral trends\r\n\r\n分值：0-100',
    `reputation_level` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按评分化为五个类别，每20分一个档：\r\nHigh Risk, Suspicious, Moderate Risk, Low Risk, Trustworthy.',
    `category_id` int NULL DEFAULT NULL COMMENT '分类体系：体系图，类别说明文档   \r\n\r\n注：分为6个category',
    `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `category_group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'group，group下细分78种类别（编号1-82，缺少70，72，73，77四个编号）',
    `category_conf` int NULL DEFAULT NULL,
    `is_a1_cat` tinyint(1) NULL DEFAULT NULL COMMENT '1-查询的url下的所有路径均被判断为同一个类别，否则为0',
    `status_code` int NULL DEFAULT NULL,
    `submit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段空时为default用户',
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fqdn`(`fqdn`) USING BTREE,
    INDEX `fk_fqdn_category_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_fqdn_category_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_fqdn_icp_built_in`;
CREATE TABLE `cn_fqdn_icp_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `fqdn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'chinaz',
    `query_success` tinyint(1) NULL DEFAULT NULL COMMENT 'websketch查询方提交的域名，由于网络等原因查询失败',
    `is_accessible` int NULL DEFAULT NULL,
    `status_code` int NULL DEFAULT NULL COMMENT '状态码',
    `match_pattern` int NULL DEFAULT NULL COMMENT '1-右匹配\r\n\r\n2-全匹配',
    `icp_owner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icp_company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icp_company_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icp_main_page` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icp_site_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icp_site_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icp_verify_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fqdn`(`fqdn`) USING BTREE,
    INDEX `fk_fqdn_icp_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_fqdn_icp_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_fqdn_who_is_built_in`;
CREATE TABLE `cn_fqdn_who_is_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT,
    `fqdn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'chinaz\r\n\r\nbrightcloud',
    `query_success` tinyint(1) NULL DEFAULT NULL COMMENT 'websketch查询方提交的域名，由于网络等原因查询失败',
    `status_code` int NULL DEFAULT NULL COMMENT '状态码',
    `match_pattern` int NULL DEFAULT NULL COMMENT '1-右匹配\r\n\r\n2-全匹配',
    `whois_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_update_date` datetime NULL DEFAULT NULL,
    `whois_create_date` datetime NULL DEFAULT NULL,
    `whois_expire_date` datetime NULL DEFAULT NULL,
    `whois_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_ns` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrar_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_org` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_street` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_postcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `whois_registrant_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fqbn`(`fqdn`) USING BTREE,
    INDEX `fk_fqdn_who_is_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_fqdn_who_is_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_idc_renter_built_in`;
CREATE TABLE `cn_idc_renter_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `addr_format` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip1` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip2` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Single时，值为ip1\r\nRange时，为IP段的结束IP\r\nCIDR时，mask值',
    `start_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
    `end_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
    `idc_renter` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `start_ip_binary`(`start_ip_binary`) USING BTREE,
    INDEX `end_ip_binary`(`end_ip_binary`) USING BTREE,
    INDEX `fk_idc_renter_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_idc_renter_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_internal_ip_built_in`;
CREATE TABLE `cn_internal_ip_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `addr_format` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip1` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip2` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Single时，值为ip1\r\nRange时，为IP段的结束IP\r\nCIDR时，mask值',
    `start_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
    `end_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `start_ip_binary`(`start_ip_binary`) USING BTREE,
    INDEX `end_ip_binary`(`end_ip_binary`) USING BTREE,
    INDEX `fk_internal_ip_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_internal_ip_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_ioc_darkweb`;
CREATE TABLE `cn_ioc_darkweb`  (
    `id` int NOT NULL AUTO_INCREMENT,
    `ioc_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `ioc_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `node_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ipv6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ioc_type为tor、i2p和snowflake时有值',
    `isp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `asn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应字段as_number',
    `operating_system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ioc_type为tor时有值，对应字段host_system',
    `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `longtitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tor_fingerprint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tor_dir_port` int NULL DEFAULT NULL,
    `tor_or_port` int NULL DEFAULT NULL,
    `tor_flags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tor_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tor_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tor_exit_policy` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `i2p_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `i2p_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `i2p_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `i2p_is_floodfill` tinyint NULL DEFAULT NULL,
    `i2p_bandwidth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `i2p_is_hidden` tinyint NULL DEFAULT NULL,
    `i2p_is_reachable` tinyint NULL DEFAULT NULL,
    `i2p_publish_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `mtproxy_port` int NULL DEFAULT NULL,
    `mtproxy_secret` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `obfs4_port` int NULL DEFAULT NULL,
    `obfs4_fingerprint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `obfs4_cert` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `obfs4_iat_mode` int NULL DEFAULT NULL,
    `snowflake_port` int NULL DEFAULT NULL,
    `first_appear_time` bigint NOT NULL,
    `last_appear_time` bigint NOT NULL,
    `create_time` bigint NOT NULL,
    `update_time` bigint NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_ioc_darkweb_knowledge_id`(`knowledge_id`) USING BTREE,
    INDEX `ioc_value`(`ioc_value`) USING BTREE,
    CONSTRAINT `fk_ioc_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_ioc_malware`;
CREATE TABLE `cn_ioc_malware`  (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `ioc_value` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '取IOC文件中ioc_value字段，索引字段',
    `ioc_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '根据IOC文件判断，IOC文件中ioc_value为\r\nip:port时，填写为ip\r\ndomain时，填写为domain\r\nurl时，填写为url',
    `threat_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '根据IOC文件判断，ioc文件中threat_type字段为\r\nbotnet_cc时，填写为command and control\r\npayload时，填写为payload delivery （载荷投递）',
    `malware_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取IOC文件中malware_printable字段',
    `malware_alias` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取IOC文件中malware_alias字段',
    `mitre_attack_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '根据malware_name匹配malware属性文件software表格中name，取description字段',
    `mitre_attack_platforms` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '根据malware_name匹配malware属性文件software表格中name，取platform字段',
    `mitre_attack_techniques` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '根据malware_name匹配malware属性文件techniques used表格中source name字段，取(多行)target name和target id字段拼接，格式为：[name1(id1), name2(id2), name3(id3)]',
    `mitre_attack_groups` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '根据malware_name匹配malware属性文件associated groups表格中target name字段，取(多行)source name和source id字段拼接，格式为：[name1(id1), name2(id2), name3(id3)]',
    `confidence_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取IOC文件confidence_level字段',
    `reference` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取IOC文件reference字段',
    `source` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IOC文件来源\r\nabush.ch',
    `first_report_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取IOC文件first_seen_utc字段',
    `last_seen_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取IOC文件last_seen_utc字段',
    `create_time` bigint NOT NULL,
    `update_time` bigint NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_ioc_malware_knowledge_id`(`knowledge_id`) USING BTREE,
    INDEX `ioc_value`(`ioc_value`) USING BTREE,
    CONSTRAINT `fk_ioc_malware_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_ip_asn_built_in`;
CREATE TABLE `cn_ip_asn_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT,
    `addr_type` int NOT NULL COMMENT '地址类型',
    `addr_format` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip1` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip2` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Single时，值为ip1\r\nRange时，为IP段的结束IP\r\nCIDR时，mask值',
    `start_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
    `end_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
    `asn` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `organization` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `is_valid` int NOT NULL,
    `op_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `start_ip_binary`(`start_ip_binary`) USING BTREE,
    INDEX `end_ip_binary`(`end_ip_binary`) USING BTREE,
    INDEX `fk_ip_asn_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_ip_asn_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_ip_location_built_in`;
CREATE TABLE `cn_ip_location_built_in`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `addr_type` int NOT NULL,
    `start_ip` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `end_ip` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `minip` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `maxip` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `continent` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `areacode` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `country` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `province` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `city` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `lngwgs` double NULL DEFAULT NULL,
    `latwgs` double NULL DEFAULT NULL,
    `owner` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `isp` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `asnumber` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `timezone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `is_valid` int NOT NULL COMMENT '0无效，1有效',
    `op_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `minip`(`minip`) USING BTREE,
    INDEX `maxip`(`maxip`) USING BTREE,
    INDEX `fk_ip_location_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_ip_location_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_ip_tag_user_defined`;
CREATE TABLE `cn_ip_tag_user_defined`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `tag_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `addr_format` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip1` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip2` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Single时，值为ip1\r\nRange时，为IP段的结束IP\r\nCIDR时，mask值',
    `start_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
    `end_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_valid` int NULL DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `start_ip_binary`(`start_ip_binary`) USING BTREE,
    INDEX `end_ip_binary`(`end_ip_binary`) USING BTREE,
    INDEX `fk_ip_tag_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_ip_tag_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_link_direction_built_in`;
CREATE TABLE `cn_link_direction_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `interface_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `direction` int NOT NULL,
    `peer_province` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `peer_city` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `link_id` int NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_link_direction_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_link_direction_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_psiphon3_ip`;
CREATE TABLE `cn_psiphon3_ip`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `addr_format` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip1` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ip2` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP地址2(CIDR时mask值)',
    `start_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
    `end_ip_binary` varbinary(16) NOT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
    `type` int NOT NULL,
    `method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `confidence` int NOT NULL COMMENT '0~100范围内整数',
    `confidence_level` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `op_time` datetime NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int NOT NULL,
    `is_valid` int NULL DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `start_ip_binary`(`start_ip_binary`) USING BTREE,
    INDEX `end_ip_binary`(`end_ip_binary`) USING BTREE,
    INDEX `fk_psiphon3_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_psiphon3_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cn_dns_server_info_built_in`;
CREATE TABLE `cn_dns_server_info_built_in`  (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `ip_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '索引字段',
    `location_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `location_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `location_region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `asn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `dns_server_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `orgnization` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `operation_system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `dns_software` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `dnssec_support` tinyint(1) NULL DEFAULT 0,
    `dot_support` tinyint(1) NULL DEFAULT 0,
    `doh_support` tinyint(1) NULL DEFAULT 0,
    `root_mirror_locations` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    `knowledge_id` int NOT NULL COMMENT '知识库唯一标识，外键。',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `ip_addr`(`ip_addr`) USING BTREE,
    INDEX `fk_dns_server_knowledge_id`(`knowledge_id`) USING BTREE,
    CONSTRAINT `fk_dns_server_knowledge_id` FOREIGN KEY (`knowledge_id`) REFERENCES `cn_knowledge_base` (`knowledge_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;


