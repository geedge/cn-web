truncate sys_menu;

INSERT INTO `sys_menu` VALUES (1, 'dashboard', 'dashboard', 'dashboard', 0, 'sys:user:add,sys:user:list', 1, '', 'cn-icon', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (2, 'trafficSummary', 'trafficSummary', 'trafficSummary', 1, 'sys:user:add,sys:user:list', 1, '/panel/trafficSummary', 'cn-icon', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (3, 'networkAppPerformance', 'networkAppPerformance', 'networkAppPerformance', 1, 'sys:user:add,sys:user:list', 1, '/panel/networkAppPerformance', 'cn-icon', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (4, 'dnsServiceInsights', 'dnsServiceInsights', 'dnsServiceInsights', 1, 'sys:user:add,sys:user:list', 1, '/panel/dnsServiceInsights', 'cn-icon', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (5, 'administrator', 'administrator', 'administrator', 0, NULL, 1, '', 'cn-icon', NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (6, 'Appearance', 'appearance', 'appearance.appearance', 5, 'sys:appearance:update', 1, '/appearance', '', NULL, 28, 1);
