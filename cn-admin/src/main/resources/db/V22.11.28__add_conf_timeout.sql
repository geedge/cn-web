/**
 * 2022年11月28日  
 * sys_config表新增session timeout配置
 */

REPLACE INTO `sys_config` VALUES (null, 'session_timeout', '50', 1, NULL);
 