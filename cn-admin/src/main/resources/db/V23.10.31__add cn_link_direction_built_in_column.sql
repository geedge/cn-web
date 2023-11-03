ALTER TABLE `cn_link_direction_built_in`
ADD COLUMN IF NOT EXISTS `bandwidth` bigint(20) NOT NULL AFTER `direction`;