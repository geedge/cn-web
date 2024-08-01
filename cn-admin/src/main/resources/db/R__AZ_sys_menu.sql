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
INSERT INTO `sys_menu` VALUES (1, 'Network Analytics', 'networkAnalytics', 'overall.networkAnalytics', 0, NULL, 1, 'redirect:/panel', 'cn-icon cn-icon-dashboard', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (2, 'Network Overview', 'networkOverview', 'networkOverview.networkOverview', 1, NULL, 1, '/panel/networkOverview', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (3, 'Network and app performance', 'networkAppPerformance', 'networkAppPerformance.networkAppPerformance', 1, NULL, 1, '/panel/networkAppPerformance', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (4, 'DNS service insights', 'dnsServiceInsights', 'overall.dnsServiceInsights', 1, NULL, 1, '/panel/dnsServiceInsights', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (5, 'Entity', 'entity', 'overall.entity', 0, NULL, 1, '/entity', 'cn-icon cn-icon-entity', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (6, 'Entity detail', 'entityDetail', 'overall.detail', 5, NULL, 1, '/entity/detail', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (7, 'Entity graph', 'entityGraph', 'entities.graph', 5, NULL, 1, '/entity/graph', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (8, 'Detections', 'detections', 'overall.detections', 0, '', 1, 'redirect:/detection', 'cn-icon cn-icon-detection1', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (9, 'Security events', 'securityEvents', 'entities.securityEvents', 8, NULL, 1, '/detection/securityEvent', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (10, 'Performance events', 'performanceEvents', 'overall.performanceEvents', 8, NULL, 1, '/detection/performanceEvent', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (11, 'Detection policy', 'detectionPolicy', 'overall.policies', 8, NULL, 1, '/detection/policy', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (12, 'Create detection policy', 'createDetectionPolicy', 'overall.create', 11, 'detection:rule:add', 1, '/detection/policy/create', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (13, 'Edit detection policy', 'editDetectionPolicy', 'overall.edit', 11, 'detection:rule:update', 1, '/detection/policy/edit', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (14, 'Delete detection policy', 'deleteDetectionPolicy', 'overall.delete', 11, 'detection:rule:delete', 2, '', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (15, 'Report', 'report', 'overall.report', 0, NULL, 1, '/report', 'cn-icon cn-icon-report1', NULL, 31, 1);
INSERT INTO `sys_menu` VALUES (16, 'Create report', 'createReport', 'overall.create', 15, 'report:job:add,report:temp:add', 2, '', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (17, 'Edit report', 'editReport', 'overall.edit', 15, 'report:job:update,report:temp:update', 2, '', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (18, 'Delete report', 'deleteReport', 'overall.delete', 15, 'report:job:delete,report:temp:delete', 2, '', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (19, 'Knowledge base', 'knowledgeBase', 'overall.knowledgeBase', 39, NULL, 1, '/system/knowledgeBase', 'cn-icon cn-icon-knowledge-base', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (20, 'Edit built-in knowledge base', 'editBuiltInKnowledgeBase', 'overall.edit', 19, 'knowledge:base:add', 2, '', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (21, 'Tag', 'tag', 'overall.tag', 0, NULL, 1, '/tag', 'cn-icon cn-icon-tag-fill', NULL, 36, 1);
INSERT INTO `sys_menu` VALUES (22, 'Create tag', 'createTag', 'overall.create', 21, 'knowledge:base:add', 1, '/tag/create', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (23, 'Edit tag', 'editTag', 'overall.edit', 21, 'knowledge:base:update', 1, '/tag/edit', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (24, 'Delete tag', 'deleteTag', 'overall.delete', 21, 'knowledge:base:delete', 2, '', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (25, 'Administration', 'administration', 'overall.administration', 0, NULL, 1, 'redirect:/administration', 'cn-icon cn-icon-administrations', NULL, 51, 1);
INSERT INTO `sys_menu` VALUES (26, 'User', 'user', 'overall.user', 25, NULL, 1, '/administration/user', 'cn-icon cn-icon-user2', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (27, 'Create user', 'createUser', 'overall.create', 26, 'sys:user:add', 2, '', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (28, 'Edit user', 'editUser', 'overall.edit', 26, 'sys:user:update', 2, '', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (29, 'Delete user', 'deleteUser', 'overall.delete', 26, 'sys:user:delete', 2, '', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (30, 'Role', 'role', 'overall.role', 25, NULL, 1, '/administration/role', 'cn-icon cn-icon-role2', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (31, 'Create role', 'createRole', 'overall.create', 30, 'sys:role:add', 2, '', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (32, 'Edit role', 'editRole', 'overall.edit', 30, 'sys:role:update', 2, '', '', NULL, 11, 1);
INSERT INTO `sys_menu` VALUES (33, 'Delete role', 'deleteRole', 'overall.delete', 30, 'sys:role:delete', 2, '', '', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (34, 'Operation log', 'operationLog', 'overall.operationLog', 25, NULL, 1, '/administration/operationLog', 'cn-icon cn-icon-operation-log', NULL, 21, 1);
INSERT INTO `sys_menu` VALUES (35, 'Appearance', 'appearance', 'appearance.appearance', 25, NULL, 1, '/administration/appearance', 'cn-icon cn-icon-preview', NULL, 31, 1);
INSERT INTO `sys_menu` VALUES (36, 'Edit appearence', 'editAppearence', 'overall.edit', 35, 'sys:config:update', 2, '', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (37, 'I18n', 'I18N', 'I18N', 0, NULL, 1, '/i18n', 'cn-icon cn-icon-i18n', NULL, 91, 1);
INSERT INTO `sys_menu` VALUES (38, 'Link monitor', 'linkMonitor', 'overall.linkMonitor', 1, NULL, 1, '/panel/linkMonitor', '', NULL, 31, 1);
INSERT INTO `sys_menu` VALUES (39, 'System', 'system', 'overall.system', 0, NULL, 1, 'redirect:/system', 'cn-icon cn-icon-system', NULL, 61, 1);
INSERT INTO `sys_menu` VALUES (40, 'Plugin', 'plugin', 'plugin.pluginManagement', 39, NULL, 1, '/system/plugin', 'cn-icon cn-icon-plugin', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (41, 'License', 'license', 'overall.license', 25, NULL, 1, '/administration/license', 'cn-icon cn-icon-license', NULL, 26, 1);
INSERT INTO `sys_menu` VALUES (42, 'Edit license', 'editLicense', 'overall.edit', 41, NULL, 2, '', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (43, 'Location Intelligence', 'locationIntelligence', 'overall.locationIntelligence', 0, '', 1, 'redirect:/location', '', NULL, 2, 1);
INSERT INTO `sys_menu` VALUES (44, 'Location Map', 'locationMap', 'location.locationMap', 43, '', 1, '/location/map', '', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (45, 'Trace Tracking', 'traceTracking', 'location.traceTracking', 43, '', 1, '/location/tracking', '', NULL, 11, 1);

SET FOREIGN_KEY_CHECKS = 1;
