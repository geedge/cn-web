/**
 * 2022年5月6日 
 * report result表增加唯一 用以防止结果重复
 * report job表增加报告完成时间 初始化时间
 */
ALTER TABLE `report_result` 
ADD UNIQUE INDEX IF NOT EXISTS `uniq_task_id`(`task_id`);

ALTER TABLE `report_job` 
ADD COLUMN IF NOT EXISTS  `ftime` datetime COMMENT '完成时间';

update report_job set ftime=now() where ftime = "0000-00-00 00:00:00";