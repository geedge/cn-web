-- ----------------------------
-- config of sys_config
-- ----------------------------
REPLACE INTO `sys_config` VALUES (null, 'appearance', '{"favicon":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgA","dateFormat":"yyyy-MM-dd HH:mm:ss","timezone":"Asia/Shanghai","bgImage":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgA","name":"网络叙事者","logo":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgA","theme":"light","lang":"zh"}', 1, '');
REPLACE INTO `sys_config` VALUES (null, 'security', '{"pinPolicy":{"num":1,"lower":1,"upper":1,"length":6,"spec":1,"valid":30,"lockTime":30,"enable":1,"retry":1}}', 1, '');
REPLACE INTO `sys_config` VALUES (null, 'email', '{"enable": 1,"host": "192.168.1.1","port": 25,"timeoue": 10,"authAccount": "zs@126.com","authPin": "111","sendAccount": "zs@126.com","securityType": "NONE"}', 1, '');
REPLACE INTO `sys_config` VALUES (null, 's3_enable', '0', 1, NULL);
REPLACE INTO `sys_config` VALUES (null, 's3_path', '', 1, NULL);
REPLACE INTO `sys_config` VALUES (null, 's3_accessKey', 'default', 1, NULL);
REPLACE INTO `sys_config` VALUES (null, 's3_secretKey', 'default', 1, NULL);
REPLACE INTO `sys_config` VALUES (null, 's3_bucket_name', 'report-hos-bucket', 1, NULL);
REPLACE INTO `sys_config` VALUES (null, 'driver_enable', '0', 1, NULL);
