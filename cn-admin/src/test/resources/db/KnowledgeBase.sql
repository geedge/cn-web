SET FOREIGN_KEY_CHECKS=0;
truncate cn_knowledge_base;

INSERT INTO `cn_knowledge_base` (`knowledge_id`, `name`, `category`, `op_user`, `op_time`, `c_time`, `is_built_in`, `source`, `description`, `status`, `is_published`) VALUES (100001, '内置IP定位库', 'websketch', 1, '2023-05-19 18:27:40', '2023-05-19 18:27:40', 0, 'cn_ip_location_built_in', NULL, 1, 1);
INSERT INTO `cn_knowledge_base` (`knowledge_id`, `name`, `category`, `op_user`, `op_time`, `c_time`, `is_built_in`, `source`, `description`, `status`, `is_published`) VALUES (100002, 'IP移动网类别标签', 'user_defined', 1, '2023-05-24 08:10:19', '2023-03-06 03:17:04', 1, 'cn_ip_tag_user_defined', NULL, 1, 1);
INSERT INTO `cn_knowledge_base` (`knowledge_id`, `name`, `category`, `op_user`, `op_time`, `c_time`, `is_built_in`, `source`, `description`, `status`, `is_published`) VALUES (100003, '测试编辑', 'user_defined', 1, '2023-05-23 08:38:13', '2023-03-22 10:09:38', 1, 'cn_app_tag_user_defined', NULL, 0, 1);
INSERT INTO `cn_knowledge_base` (`knowledge_id`, `name`, `category`, `op_user`, `op_time`, `c_time`, `is_built_in`, `source`, `description`, `status`, `is_published`) VALUES (100004, 'TestEdit', 'user_defined', 1, '2023-05-23 06:43:31', '2023-03-24 08:31:08', 0, 'cn_domain_tag_user_defined', NULL, 0, 0);
SET FOREIGN_KEY_CHECKS=1;