ALTER TABLE `cn_intelligence_indicator`
ADD COLUMN `subscriber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL AFTER `domain`,
MODIFY COLUMN `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP\r\nDomain' AFTER `id`;

DROP TABLE IF EXISTS `cn_cell`;
CREATE TABLE `cn_cell` (
   `id` varchar(64) NOT NULL COMMENT '主键',
   `cell_id` varchar(64) NOT NULL COMMENT '小区ID',
   `nodeb_id` int(11) NOT NULL COMMENT '基站ID',
   `azimuth` int(11) DEFAULT NULL COMMENT 'cell的方位角',
   `technology` varchar(64) DEFAULT NULL COMMENT '技术(例如4g，3g)',
   `lac_tac` int(11) DEFAULT NULL COMMENT 'LAC/TAC，位置区码或跟踪区码',
   `mcc` int(11) DEFAULT NULL COMMENT '移动国家代码',
   `mnc` int(11) DEFAULT NULL COMMENT '移动网络代码',
   `latitude` double DEFAULT NULL COMMENT '纬度',
   `longitude` double DEFAULT NULL COMMENT '经度',
   `coverage_radius` int(11) DEFAULT NULL COMMENT '信号覆盖半径(米)',
   `operator` varchar(64) DEFAULT NULL COMMENT '运营商',
   `vendor` varchar(64) DEFAULT NULL COMMENT '供应商',
   `fdd_spectrum` varchar(64) DEFAULT NULL COMMENT '频谱',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;