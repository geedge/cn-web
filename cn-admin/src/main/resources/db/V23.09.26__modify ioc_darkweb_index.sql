ALTER TABLE `cn_ioc_darkweb`
    ADD INDEX `type_value`(`ioc_type`(255), `ioc_value`(255)) USING BTREE;

ALTER TABLE `cn_ioc_malware`
    ADD INDEX `type_value`(`ioc_type`(255), `ioc_value`(512)) USING BTREE;