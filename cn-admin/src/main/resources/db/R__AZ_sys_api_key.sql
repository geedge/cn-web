SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_api_key
-- ----------------------------
DROP TABLE IF EXISTS `sys_api_key`;
CREATE TABLE `sys_api_key`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称 唯一不重复',
  `token` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密钥 唯一不重复',
  `role_id` int(10) NOT NULL COMMENT '角色id',
  `uby` int(10) NOT NULL COMMENT '更新人',
  `utime` datetime(0) NOT NULL COMMENT '更新时间',
  `etime` datetime(0) NULL DEFAULT NULL COMMENT '失效时间 为空不失效',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_api_key
-- ----------------------------
INSERT INTO `sys_api_key` VALUES (1, 'ETL', '1a653ea0-d39b-4246-94b0-1ba95db4b6a7', 1, 1, '2023-10-16 10:01:21', NULL);

SET FOREIGN_KEY_CHECKS = 1;