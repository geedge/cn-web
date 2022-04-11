/**
 * 2022年4月11日 
 * report job表增加开始时间和结束时间
 *
 */
ALTER TABLE `report_job` 
ADD COLUMN IF NOT EXISTS `start_time` datetime(0) NOT NULL COMMENT '开始时间' AFTER `upload`,
ADD COLUMN IF NOT EXISTS `end_time` datetime(0) NOT NULL COMMENT '结束时间' AFTER `start_time`;