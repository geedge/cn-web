ALTER TABLE `cn_ioc_darkweb`
    ADD INDEX IF NOT EXISTS `type_value`(`ioc_type`(255), `ioc_value`(255)) USING BTREE;

ALTER TABLE `cn_ioc_malware`
    ADD INDEX IF NOT EXISTS `type_value`(`ioc_type`(255), `ioc_value`(512)) USING BTREE;