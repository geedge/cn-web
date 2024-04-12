ALTER TABLE `cn_intelligence_indicator`
DROP INDEX `type`,
ADD UNIQUE INDEX(`type`, `source_id`, `ip1`, `ip2`, `domain`, `tags`) USING BTREE;