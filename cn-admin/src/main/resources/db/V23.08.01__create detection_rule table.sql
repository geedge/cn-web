/**
 * 2023年8月1日
 * 检测规则库表创建
 *
 */
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for detection_rule
-- ----------------------------
DROP TABLE IF EXISTS `detection_rule`;
CREATE TABLE `detection_rule`  (
    `rule_id` int(11) NOT NULL AUTO_INCREMENT,
    `rule_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `category` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `event_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `status` int(1) NOT NULL DEFAULT 1,
    `rule_config` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `trigger` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `c_user` int(10) NOT NULL,
    `c_time` datetime NOT NULL,
    `op_user` int(10) NOT NULL,
    `op_time` datetime NOT NULL,
    PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `rule_commit_history`;
CREATE TABLE `rule_commit_history`  (
    `op_seq` int(11) NOT NULL AUTO_INCREMENT,
    `rule_id` int(11) NOT NULL,
    `op_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `op_table` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `op_version` int(11) NOT NULL,
    `op_user` int(10) NOT NULL,
    `op_time` datetime NOT NULL,
    PRIMARY KEY (`op_seq`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `rule_version`;
CREATE TABLE `rule_version`  (
    `rule_id` int(11) NOT NULL,
    `version` int(10) NOT NULL,
    `op_time` datetime NOT NULL,
    PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
