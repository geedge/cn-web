truncate visual_chart;

INSERT INTO `visual_chart` VALUES (1, '会话数', 'overall.sessions', 1, 0, 101, 20, 1, 10, 5, '{\"url\":\"/interface/traffic/sessions?startTime={{startTime}}&endTime={{endTime}}\",\"unitType\":\"number\"}', 1, '2021-07-30 19:22:16', 1, '2021-10-15 08:28:41', '', 1, 0, 1);
INSERT INTO `visual_chart` VALUES (2, '流量统计', 'trafficSummary.trafficSummary', 1, 0, 803, 0, 0, 30, 1, '', 1, '2021-07-30 19:22:16', 1, '2021-07-30 19:22:16', '', 1, 0, 0);
INSERT INTO `visual_chart` VALUES (3, '活跃列表', 'trafficSummary.activeList', 1, 0, 803, 0, 16, 30, 1, '', 1, '2021-07-30 19:22:16', 1, '2021-07-30 19:22:16', '', 1, 0, 0);
INSERT INTO `visual_chart` VALUES (4, '域名分析统计', 'trafficSummary.domainStatistics', 1, 0, 803, 0, 35, 30, 1, '', 1, '2021-07-30 19:22:16', 1, '2021-07-30 19:22:16', '', 1, 0, 0);
INSERT INTO `visual_chart` VALUES (5, '包数', 'overall.packets', 1, 0, 101, 20, 6, 10, 5, '{\"url\":\"/interface/traffic/packets?startTime={{startTime}}&endTime={{endTime}}\",\"unitType\":\"number\"}', 1, '2021-07-30 19:22:16', 1, '2021-07-30 19:22:16', '', 1, 0, 0);
