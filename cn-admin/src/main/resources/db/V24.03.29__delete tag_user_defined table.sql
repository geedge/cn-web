/**
 * 2024年3月29日
 * CN-1595 API 删除过期知识库及字段
 *
 */
/*删除废弃知识库表*/
DROP TABLE IF EXISTS `cn_ip_tag_user_defined`;
DROP TABLE IF EXISTS `cn_app_tag_user_defined`;
DROP TABLE IF EXISTS `cn_domain_tag_user_defined`;
DROP TABLE IF EXISTS `cn_vpn_learning_ip`;
DROP TABLE IF EXISTS `cn_vpn_learning_domain`;
/*删除color字段*/
ALTER TABLE `cn_knowledge_base` DROP COLUMN IF EXISTS `color`;
ALTER TABLE `cn_knowledge_base_audit_log` DROP COLUMN IF EXISTS `color`;
/*删除WebSketch各条目表中knowledge_id字段*/
ALTER TABLE `cn_ioc_darkweb` DROP FOREIGN KEY IF EXISTS `fk_ioc_knowledge_id`;
ALTER TABLE `cn_ioc_darkweb` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_ioc_malware` DROP FOREIGN KEY IF EXISTS `fk_ioc_malware_knowledge_id`;
ALTER TABLE `cn_ioc_malware` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_app_category_built_in` DROP FOREIGN KEY IF EXISTS `fk_app_category_knowledge_id`;
ALTER TABLE `cn_app_category_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_fqdn_icp_built_in` DROP FOREIGN KEY IF EXISTS `fk_fqdn_icp_knowledge_id`;
ALTER TABLE `cn_fqdn_icp_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_fqdn_who_is_built_in` DROP FOREIGN KEY IF EXISTS `fk_fqdn_who_is_knowledge_id`;
ALTER TABLE `cn_fqdn_who_is_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_fqdn_category_built_in` DROP FOREIGN KEY IF EXISTS `fk_fqdn_category_knowledge_id`;
ALTER TABLE `cn_fqdn_category_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_ip_location_built_in` DROP FOREIGN KEY IF EXISTS `fk_ip_location_knowledge_id`;
ALTER TABLE `cn_ip_location_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_ip_asn_built_in` DROP FOREIGN KEY IF EXISTS `fk_ip_asn_knowledge_id`;
ALTER TABLE `cn_ip_asn_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_idc_renter_built_in` DROP FOREIGN KEY IF EXISTS `fk_idc_renter_knowledge_id`;
ALTER TABLE `cn_idc_renter_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_internal_ip_built_in` DROP FOREIGN KEY IF EXISTS `fk_internal_ip_knowledge_id`;
ALTER TABLE `cn_internal_ip_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_link_direction_built_in` DROP FOREIGN KEY IF EXISTS `fk_link_direction_knowledge_id`;
ALTER TABLE `cn_link_direction_built_in` DROP COLUMN IF EXISTS `knowledge_id`;
ALTER TABLE `cn_dns_server_info_built_in` DROP FOREIGN KEY IF EXISTS `fk_dns_server_knowledge_id`;
ALTER TABLE `cn_dns_server_info_built_in` DROP COLUMN IF EXISTS `knowledge_id`;