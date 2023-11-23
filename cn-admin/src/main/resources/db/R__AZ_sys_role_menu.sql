/**
 *    2021年11月8日 修改sys_role中关于admin和common角色的描述
 *    对sys_role_menu表内置角色权限数据导入
 *    先清空sys_role_menu表中admin和common相关数据 再导入
 */
 
delete from sys_role_menu where role_id in (1,2);

INSERT INTO `sys_role_menu` VALUES (null, 1, 1);
INSERT INTO `sys_role_menu` VALUES (null, 1, 2);
INSERT INTO `sys_role_menu` VALUES (null, 1, 3);
INSERT INTO `sys_role_menu` VALUES (null, 1, 4);
INSERT INTO `sys_role_menu` VALUES (null, 1, 5);
INSERT INTO `sys_role_menu` VALUES (null, 1, 6);
INSERT INTO `sys_role_menu` VALUES (null, 1, 7);
INSERT INTO `sys_role_menu` VALUES (null, 1, 8);
INSERT INTO `sys_role_menu` VALUES (null, 1, 9);
INSERT INTO `sys_role_menu` VALUES (null, 1, 10);
INSERT INTO `sys_role_menu` VALUES (null, 1, 11);
INSERT INTO `sys_role_menu` VALUES (null, 1, 12);
INSERT INTO `sys_role_menu` VALUES (null, 1, 13);
INSERT INTO `sys_role_menu` VALUES (null, 1, 14);
INSERT INTO `sys_role_menu` VALUES (null, 1, 15);
INSERT INTO `sys_role_menu` VALUES (null, 1, 16);
INSERT INTO `sys_role_menu` VALUES (null, 1, 17);
INSERT INTO `sys_role_menu` VALUES (null, 1, 18);
INSERT INTO `sys_role_menu` VALUES (null, 1, 19);
INSERT INTO `sys_role_menu` VALUES (null, 1, 20);
INSERT INTO `sys_role_menu` VALUES (null, 1, 21);
INSERT INTO `sys_role_menu` VALUES (null, 1, 22);
INSERT INTO `sys_role_menu` VALUES (null, 1, 23);
INSERT INTO `sys_role_menu` VALUES (null, 1, 24);
INSERT INTO `sys_role_menu` VALUES (null, 1, 25);
INSERT INTO `sys_role_menu` VALUES (null, 1, 26);
INSERT INTO `sys_role_menu` VALUES (null, 1, 27);
INSERT INTO `sys_role_menu` VALUES (null, 1, 28);
INSERT INTO `sys_role_menu` VALUES (null, 1, 29);
INSERT INTO `sys_role_menu` VALUES (null, 1, 30);
INSERT INTO `sys_role_menu` VALUES (null, 1, 31);
INSERT INTO `sys_role_menu` VALUES (null, 1, 32);
INSERT INTO `sys_role_menu` VALUES (null, 1, 33);
INSERT INTO `sys_role_menu` VALUES (null, 1, 34);
INSERT INTO `sys_role_menu` VALUES (null, 1, 35);
INSERT INTO `sys_role_menu` VALUES (null, 1, 36);
INSERT INTO `sys_role_menu` VALUES (null, 1, 37);
INSERT INTO `sys_role_menu` VALUES (null, 1, 38);
INSERT INTO `sys_role_menu` VALUES (null, 2, 2);
INSERT INTO `sys_role_menu` VALUES (null, 2, 3);
INSERT INTO `sys_role_menu` VALUES (null, 2, 4);
INSERT INTO `sys_role_menu` VALUES (null, 2, 38);
INSERT INTO `sys_role_menu` VALUES (null, 2, 6);
INSERT INTO `sys_role_menu` VALUES (null, 2, 7);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9);
INSERT INTO `sys_role_menu` VALUES (null, 2, 10);