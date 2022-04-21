package net.geedge.common.job;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import cn.hutool.log.Log;
import net.geedge.modules.report.job.ReportHandlerJob;
import net.geedge.modules.report.job.ReportHandlerTask;
import net.geedge.modules.report.job.ReportRegisterJob;

/**
 * JobConfig 统一管理 job config
 *
 */
@Configuration
public class JobConfig {

    private static final Log log = Log.get();

    @Value("${cn.haJobCron:0/5 * * * * ?}")
    private String haJobCron;
    
    @Value("${cn.reportRegisterCron:0/10 * * * * ? *}")
	private String reportRegisterJobCron;
    
    @Value("${cn.reportTaskCron:0 0/5 * * * ? *}")
    private String reportHandlerTaskCron;
    
	@Autowired
	private Scheduler scheduler;
    
	/*@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;*/
    private static final String JOB_NAME = "CNJOB";
    private String haJobName = "HA_JOB";
    private String reportHandlerTaskName = "REPORT_HANDLER_TASK";
    private String reportRegisterJobName = "REPORT_REGISTER_JOB";
    
    @Bean
	public JobDetail reportRegisterJobDetail() {
	    return JobBuilder.newJob(ReportRegisterJob.class).withIdentity(JOB_NAME + reportRegisterJobName).storeDurably().build();
	}
    
    @Bean
    public JobDetail reportHandlerTaskDetail() {
        return JobBuilder.newJob(ReportHandlerTask.class).withIdentity(JOB_NAME + reportHandlerTaskName).storeDurably().build();
    }
    
    @Bean
    public JobDetail haJobDetail() {
        return JobBuilder.newJob(HaJob.class).withIdentity(JOB_NAME + haJobName).storeDurably().build();
    }
    
    @PostConstruct
    public void init() throws SchedulerException {
    	
    	// 动态创建定时任务
    	createCronScheduleJob(reportRegisterJobName, reportRegisterJobDetail(), StringUtils.isEmpty(reportRegisterJobCron) ? "0/10 * * * * ? *" : reportRegisterJobCron);
    	
        // HA Job 如果配置文件没有配置定时周期  默认为5s执行一次
        createCronScheduleJob(haJobName, haJobDetail(), StringUtils.isEmpty(haJobCron) ? "0/5 * * * * ?" : haJobCron);
        
        // 报表任务执行相关定时任务
        createCronScheduleJob(reportHandlerTaskName, reportHandlerTaskDetail(), StringUtils.isEmpty(reportHandlerTaskCron) ? "0 0/1 * * * ? *" : reportHandlerTaskCron);

    }
    
    /**
     * 根据 jobName jobDetail jobCronExpression  创建任务，如果存在则删除再创建
     * @param jobName
     * @param jobDetail
     * @param jobCronExpression
     * @param scheduler
     * @throws Exception
     
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
    }*/

    /**
     * 根据 jobName jobDetail jobCronExpression  创建任务，如果存在则删除再创建
     * @param jobName
     * @param jobDetail
     * @param jobCronExpression
     * @throws Exception
     */
    public void createCronScheduleJob(String jobName, JobDetail jobDetail, String jobCronExpression) throws SchedulerException {
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
