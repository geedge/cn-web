/**
 *    2021年11月8日 修改sys_role中关于admin和common角色的描述
 *    对sys_role_menu表内置角色权限数据导入
 *    先清空sys_role_menu表中admin和common相关数据 再导入
 */
 
delete from sys_role_menu where role_id in (1,2);

INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 1);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,2, 2);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 2);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 3);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 4);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 5);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 6);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 7);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null,1, 8);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 11);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 9);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 10);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 12);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 13);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 14);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 15);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 18);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 20);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 21);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 26);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 27);
INSERT INTO `sys_role_menu`(`id`, `role_id`, `menu_id`) VALUES (null, 1, 23);


