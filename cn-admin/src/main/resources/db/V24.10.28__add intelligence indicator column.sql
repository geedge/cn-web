ALTER TABLE `cn_intelligence_indicator`
ADD COLUMN `subscriber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL AFTER `domain`;