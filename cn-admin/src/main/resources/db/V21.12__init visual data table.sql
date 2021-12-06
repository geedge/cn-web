/*
* 21.12 init visual data table sql
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for visual_datasource
-- ----------------------------
DROP TABLE IF EXISTS `visual_datasource`;
CREATE TABLE `visual_datasource`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `i18n` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '国际化code 默认使用 i18n 翻译显示名称，为空时 直接显示name',
  `type` int(10) NOT NULL COMMENT '类型 可选值：1：galaxy 2：rdb',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数 默认：{}',
  `cby` int(10) NOT NULL COMMENT '创建人  sys_user.id',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `uby` int(10) NOT NULL COMMENT '更新人 sys_user.id',
  `utime` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `state` int(1) NOT NULL DEFAULT 1 COMMENT '状态 可选值：0：隐藏 1：显示',
  `build_in` int(1) NOT NULL DEFAULT 0 COMMENT '是否内置 可选值：0：非内置 1：内置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of visual_datasource
-- ----------------------------

-- ----------------------------
-- Table structure for visual_dataset
-- ----------------------------
DROP TABLE IF EXISTS `visual_dataset`;
CREATE TABLE `visual_dataset`  (
   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
   `i18n` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '国际化code 默认使用 i18n 翻译显示名称，为空时 直接显示name',
   `dsid` int(10) NOT NULL COMMENT '数据源id 关联 visual_datasource.id',
   `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模式 1：直连 2：定时同步',
   `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数 默认：{}',
   `cby` int(10) NOT NULL COMMENT '创建人 sys_user.id',
   `ctime` datetime NOT NULL COMMENT '创建时间',
   `uby` int(10) NOT NULL COMMENT '更新人 sys_user.id',
   `utime` datetime NOT NULL COMMENT '更新时间',
   `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
   `state` int(1) NOT NULL DEFAULT 1 COMMENT '状态 可选值：0：隐藏 1：显示',
   `build_in` int(1) NOT NULL DEFAULT 0 COMMENT '是否内置 可选值：0：非内置 1：内置',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of visual_dataset
-- ----------------------------

-- ----------------------------
-- Table structure for visual_dataset_field
-- ----------------------------
DROP TABLE IF EXISTS `visual_dataset_field`;
CREATE TABLE `visual_dataset_field`  (
 `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
 `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
 `i18n` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '国际化code 默认使用 i18n 翻译显示名称，为空时 直接显示name',
 `dsetid` int(10) NOT NULL COMMENT '数据集id 关联 visual_dataset.id',
 `type` int(10) NOT NULL COMMENT '字段类型 1：number 2：text 3：datetime 4：timestamp',
 `stype` int(10) NOT NULL COMMENT '统计类型 1：group 2：select',
 `expr` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表达式',
 `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数 默认：{}',
 `idx` int(10) NOT NULL DEFAULT 0 COMMENT '顺序',
 `cby` int(10) NOT NULL COMMENT '创建人 sys_user.id',
 `ctime` datetime NOT NULL COMMENT '创建时间 ',
 `uby` int(10) NOT NULL COMMENT '更新人 sys_user.id',
 `utime` datetime NOT NULL COMMENT '更新时间',
 `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
 `state` int(1) NOT NULL DEFAULT 1 COMMENT '状态 可选值：0：隐藏 1：显示',
 `build_in` int(1) NOT NULL DEFAULT 0 COMMENT '是否内置 可选值：0：非内置 1：内置',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of visual_dataset_field
-- ----------------------------