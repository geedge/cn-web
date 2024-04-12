SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
    `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'UUID',
    `category` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `intent` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Malicious - 恶意 \r\nBenign - 良性 \r\nUnknow - 未知',
    `indicator_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP\r\nDomain',
    `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `is_built_in` int(1) NULL DEFAULT 0 COMMENT '是否内置：0-非内置，1-内置',
    `created_time` datetime NOT NULL,
    `updated_time` datetime NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `cn_intelligence_indicator`;
CREATE TABLE `cn_intelligence_indicator`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP\r\nDomain',
    `source_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指标来源UUID',
    `source_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指标来源名称',
    `ip_addr_format` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Single\r\nRange\r\nCIDR',
    `ip1` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `ip2` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `start_ip_binary` varbinary(16) NULL DEFAULT NULL COMMENT '索引字段，起始网络字节序，INET6_ATON(start_ip)',
    `end_ip_binary` varbinary(16) NULL DEFAULT NULL COMMENT '索引字段，结束网络字节序，INET6_ATON(end_ip)',
    `domain` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `method` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT 'passive_ml\r\npassive_sig\r\nacitve_probe\r\nthird_api',
    `confidence` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT 'confirmed (80-100)\r\nsuspect (70-80)\r\ntentative (50-70)',
    `tags` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签字符串列表，英文逗号分割',
    `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `first_seen_time` datetime NOT NULL COMMENT '默认与创建时间一致。第三方库导入需使用自带首次发现时间戳。',
    `last_seen_time` datetime NOT NULL COMMENT '默认与最后更新时间一致。第三方库导入需使用自带最近发现时间戳。',
    `created_time` datetime NOT NULL,
    `updated_time` datetime NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `start_ip_binary`(`start_ip_binary`) USING BTREE,
    INDEX `end_ip_binary`(`end_ip_binary`) USING BTREE,
    UNIQUE INDEX `type`(`type`, `source_id`, `ip1`, `ip2`, `domain`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;