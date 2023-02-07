SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
 `id` int(10) NOT NULL AUTO_INCREMENT,
 `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
 `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一主键，前端根据此字段判断是否显示按钮或菜单',
 `i18n` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
 `pid` int(10) NOT NULL COMMENT '父菜单ID 一级菜单为0',
 `perms` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多个用逗号分隔，后台根据此字段判断用户是否有权限访问接口',
 `type` int(1) NOT NULL COMMENT '1：菜单 2：按钮 3:tab标签页',
 `route` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'route',
 `icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'nz-icon nz-alert-add',
 `refer` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序 必要权限，如编辑按钮需要 勾选查看权限 填写内容为 sys_menu.id，多个逗号分隔 1,2',
 `sort` int(10) NOT NULL DEFAULT 1 COMMENT '排序 默认：1',
 `state` int(1) NOT NULL DEFAULT 1 COMMENT '0:停用  1:启用',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'Network Analytics', 'networkAnalytics', 'overall.networkAnalytics', 0, 'sys:user:add,sys:user:list', 1, '', 'cn-icon cn-icon-dashboard', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (2, 'Network Overview', 'networkOverview', 'networkOverview.networkOverview', 1, 'sys:user:add,sys:user:list', 1, '/panel/networkOverview', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (3, 'Network and app performance', 'networkAppPerformance', 'networkAppPerformance.networkAppPerformance', 1, 'sys:user:add,sys:user:list', 1, '/panel/networkAppPerformance', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (4, 'DNS service insights', 'dnsServiceInsights', 'overall.dnsServiceInsights', 1, 'sys:user:add,sys:user:list', 1, '/panel/dnsServiceInsights', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (5, 'Setting', 'setting', 'overall.setting', 0, NULL, 1, '', 'cn-icon cn-icon-setting2', NULL, 31, 1);
INSERT INTO `sys_menu` VALUES (6, 'User', 'user', 'overall.user', 5, NULL, 1, '/user', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (7, 'Role', 'role', 'overall.role', 5, NULL, 1, '/role', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (8, 'Operation log', 'operationLog', 'overall.operationLog', 5, NULL, 1, '/operationLog', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (9, 'Entity', 'entity', 'overall.entity', 0, NULL, 1, '/entityExplorer', 'cn-icon cn-icon-entity', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (10, 'Entity explorer', 'entityExplorer', 'overall.entityExplorer', 9, NULL, 1, '/entityExplorer', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (11, 'I18n', 'I18N', '', 5, NULL, 1, '/i18n', '', NULL, 31, 1);
INSERT INTO `sys_menu` VALUES (12, 'Detections', 'detections', 'overall.detections', 0, '', 1, '/detection/securityEvent', 'cn-icon cn-icon-detection1', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (13, 'Security events', 'securityEvents', 'entities.securityEvents', 12, NULL, 1, '/detection/securityEvent', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (14, 'Performance events', 'performanceEvents', 'overall.performanceEvents', 12, NULL, 1, '/detection/performanceEvent', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (15, 'Galaxy proxy', 'galaxyProxy', 'galaxyProxy.galaxyProxy', 5, NULL, 1, '/galaxyProxy', '', NULL, 41, 1);
INSERT INTO `sys_menu` VALUES (16, 'Crypto currency', 'cryptocurrency', 'overall.cryptocurrency', 1, NULL, 1, '/panel/cryptocurrency', '', NULL, 41, 0);
INSERT INTO `sys_menu` VALUES (17, 'Temp', 'temp', 'temp', 0, NULL, 1, '/test', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (18, 'Chart', 'chart', 'overall.chart', 5, NULL, 1, '/chart', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (19, 'Report', 'report', 'overall.report', 0, NULL, 1, '/report/builtIn', 'cn-icon cn-icon-report1', NULL, 26, 1);
INSERT INTO `sys_menu` VALUES (20, 'Built-in report', 'builtInReport', 'report.builtInReport', 19, 'report:job:add,report:job:delete,report:temp:add,report:temp:update,report:temp:delete', 1, '/report/builtIn', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (21, 'Appearance', 'appearance', 'appearance.appearance', 5, 'sys:appearance:update', 1, '/appearance', '', NULL, 28, 0);
INSERT INTO `sys_menu` VALUES (25, 'Link monitor', 'linkMonitor', 'overall.linkMonitor', 1, NULL, 1, '/panel/linkMonitor', '', NULL, 41, 1);

SET FOREIGN_KEY_CHECKS = 1;
