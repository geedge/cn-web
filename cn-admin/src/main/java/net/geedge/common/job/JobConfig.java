package net.geedge.common.job;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import cn.hutool.log.Log;

/**
 * JobConfig 统一管理 job config
 *
 */
@Configuration
public class JobConfig {

    private static final Log log = Log.get();

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    private static final String JOB_NAME = "CNJOB";
    
    @PostConstruct
    public void init() throws SchedulerException {
    }
    
    /**
     * 根据 jobName jobDetail jobCronExpression  创建任务，如果存在则删除再创建
     * @param jobName
     * @param jobDetail
     * @param jobCronExpression
     * @param scheduler
     * @throws Exception
     */
    public static void createCronScheduleJob(String jobName, JobDetail jobDetail, String jobCronExpression, Scheduler scheduler) throws SchedulerException {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobCronExpression);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(cronScheduleBuilder)
                .build();

        // job如果存在则删除
        JobKey jobKey = new JobKey(JOB_NAME + jobName);
        boolean b = scheduler.checkExists(jobKey);
        if (b) {
            scheduler.deleteJob(jobKey);
        }
        // 创建任务
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

}
