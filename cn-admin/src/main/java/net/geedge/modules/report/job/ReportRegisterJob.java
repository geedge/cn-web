package net.geedge.modules.report.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;

@DisallowConcurrentExecution
public class ReportRegisterJob extends QuartzJobBean {
	private Log log = Log.get();
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ReportTempService reportTempService;
	
	@Autowired
	private ReportJobService reportJobService;
	
	@Autowired
	private ReportTaskService reportTaskService;
	
	private Scheduler scheduler;
			
	/**
	 * 该定时任务： 根据temp数据定时生成job任务以及task数据信息
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Thread.currentThread().setName("ReportRegisterJob");
		scheduler = context.getScheduler();
		// 检查 haMode
    	String serverId = (String) redisTemplate.opsForValue().get(Constant.SYS_HA_LOCK);
        if(!StrUtil.equals(serverId, Constant.SERVER_ID)) return;
        
        log.info("ReportRegisterJob start");
        StopWatch sw = new StopWatch();
        sw.start();        

        List<ReportTemp> temps = reportTempService.list(new QueryWrapper<ReportTemp>().lambda().eq(ReportTemp::getState, 1));
        Map<String,String> jobKeyMaps = new HashMap<String,String>();
        for(ReportTemp reportTemp : temps) {
        	String config = reportTemp.getConfig();
        	Map<String,Object> tempConfigMap = (Map<String, Object>) JSONObject.parse(config);
        	String cronExpr = (String) tempConfigMap.get("cronExpression");
        	Integer isRepeat = (Integer) tempConfigMap.get("isRepeat");
        	if(isRepeat.equals(1) && reportTemp.getState().equals(1)) {
        		String hash = DigestUtil.sha1Hex(cronExpr);
            	String jobKeyName = String.format("%s-%s",hash,reportTemp.getId());
            	jobKeyMaps.put(jobKeyName,cronExpr);
        	}
        }
        
        try {
	        // 删除已经变化的temp 主要为cron改变
	        GroupMatcher<JobKey> groupMatcher = GroupMatcher.groupEquals(Constant.CN_REPORT_CRON_GROUP);
	        Set<JobKey> jobKeys = scheduler.getJobKeys(groupMatcher);
			for (JobKey key : jobKeys) {
				if(jobKeyMaps.keySet().contains(key.getName())) {
					continue;
				}
				scheduler.deleteJob(key);
			}
	        
	        for(String jobKeyName : jobKeyMaps.keySet()) {
	        	String cronExpr = jobKeyMaps.get(jobKeyName);
	    		JobKey jobKey = new JobKey(jobKeyName,Constant.CN_REPORT_CRON_GROUP);
				boolean exists = scheduler.checkExists(jobKey);
				if(!exists) {
					JobDetail jobDetail = JobBuilder.newJob(ReportHandlerJob.class).withIdentity(jobKey).storeDurably()
							.build();
					// 根据用户定时周期创建定时任务
					CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpr);
					CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(cronScheduleBuilder)
							.build();
					// 创建任务
					scheduler.scheduleJob(jobDetail, cronTrigger);
				}
	        }
        } catch (SchedulerException e) {
			log.error("ReportRegisterJob error msg : {}",e.getMessage());
		}	
	    sw.stop();
	    log.info("ReportRegisterJob  end  excute time : {}",sw.toString());
	}
}
