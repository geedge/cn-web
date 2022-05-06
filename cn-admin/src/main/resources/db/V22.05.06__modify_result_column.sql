/**
 * 2022年5月6日 
 * report result表增加唯一 用以防止结果重复
 *
 */
ALTER TABLE `report_result` 
ADD UNIQUE INDEX IF NOT EXISTS `uniq_task_id`(`task_id`);