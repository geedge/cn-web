/**
 * 2022年6月6日  
 * 增加report_category表  修改原来report_temp以及report_job结构
 */

ALTER TABLE `report_job` 
ADD COLUMN IF NOT EXISTS `uid` int(10) NOT NULL COMMENT '创建人',
ADD COLUMN IF NOT EXISTS `temp_id` int(10) NOT NULL COMMENT '模板id',
ADD COLUMN IF NOT EXISTS `category_id` int(10) NOT NULL COMMENT '模板类别id';


ALTER TABLE `report_temp` 
ADD COLUMN IF NOT EXISTS `uid` int(10) NOT NULL COMMENT '创建人id',
ADD COLUMN IF NOT EXISTS `build_in` int(1) NOT NULL DEFAULT 0 COMMENT '是否内置 0-否 1-是 默认：0',
ADD COLUMN IF NOT EXISTS `category_id` int(10) NOT NULL COMMENT '模板类别id',
ADD COLUMN IF NOT EXISTS `scheduler_start` datetime NULL COMMENT '定时开始时间',
ADD COLUMN IF NOT EXISTS `scheduler_end` datetime NULL COMMENT '定时结束时间';


DROP TABLE IF EXISTS `report_category`;
CREATE TABLE `report_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `html_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'html模板内容',
  `config` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公用配置项',
  `build_in` int(2) NOT NULL DEFAULT 0 COMMENT '是否内置 0-否 1-是 默认 0',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `uid` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `utime` datetime(0) NOT NULL COMMENT '修改时间',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;