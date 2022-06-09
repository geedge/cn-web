package net.geedge.modules.report.job;

import java.util.Date;
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
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcraft.jsch.Logger;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
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
        Map<String,Map<String,Object>> cronJobKeyMaps = new HashMap<String,Map<String,Object>>();
        Map<String,ReportTemp> singleJobKeyMaps = new HashMap<String,ReportTemp>();
        Date now = new Date();
        for(ReportTemp reportTemp : temps) {
        	String config = reportTemp.getConfig();
        	Map<String,Object> tempConfigMap = (Map<String, Object>) JSONObject.parse(config);
        	String cronExpr = (String) tempConfigMap.get("cronExpression");
        	Integer isScheduler = (Integer) tempConfigMap.get("isScheduler");
        	Integer isRepeat = (Integer) tempConfigMap.get("isRepeat");
        	if(isScheduler.equals(1)) {
	        	if(isRepeat.equals(1) && reportTemp.getState().equals(1) && (DateUtil.compare(reportTemp.getSchedulerEnd(), now) > 0 || ObjectUtil.isEmpty(reportTemp.getSchedulerEnd()))) {
	        		if(StrUtil.isEmpty(cronExpr)) {
	        			continue;
	        		}else {
	        			List<String> crons = JSONObject.parseArray(cronExpr,String.class);
	        			for(String cron : crons) {
	        				Map<String,Object> map = new HashMap<String,Object>();
	        				String hash = DigestUtil.sha1Hex(cron);
	                    	String jobKeyName = String.format("%s-%s",hash,reportTemp.getId());
	                    	map.put("cron", cron);
	                    	map.put("schedulerStart", reportTemp.getSchedulerStart());
	                    	map.put("schedulerEnd", reportTemp.getSchedulerEnd());
	                    	cronJobKeyMaps.put(jobKeyName,map);
	        			}
	        		}
	        	}else if(isRepeat.equals(0) && reportTemp.getState().equals(1)){
	        		if(!(DateUtil.compare(reportTemp.getSchedulerStart(), now) > 0)) {
	        			continue;
	        		}
	        		String jobKeyName = String.format("%s-%s",reportTemp.getSchedulerStart().getTime(),reportTemp.getId());
	        		singleJobKeyMaps.put(jobKeyName,reportTemp);
	        	}
        	}else {
        		continue;
        	}
        }
        
        try {
	        // 删除已经变化的temp 主要为cron改变
	        GroupMatcher<JobKey> groupMatcher = GroupMatcher.groupEquals(Constant.CN_REPORT_CRON_GROUP);
	        Set<JobKey> jobKeys = scheduler.getJobKeys(groupMatcher);
			for (JobKey key : jobKeys) {
				if(cronJobKeyMaps.keySet().contains(key.getName())) {
					continue;
				}
				scheduler.deleteJob(key);
			}

	        for(String jobKeyName : cronJobKeyMaps.keySet()) {
	        	Map<String,Object> map = cronJobKeyMaps.get(jobKeyName);
	    		JobKey jobKey = new JobKey(jobKeyName,Constant.CN_REPORT_CRON_GROUP);
				boolean exists = scheduler.checkExists(jobKey);
				if(!exists) {
					JobDetail jobDetail = JobBuilder.newJob(ReportHandlerJob.class).withIdentity(jobKey).storeDurably()
							.build();
					// 根据用户定时周期创建定时任务
					CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule((String)map.get("cron")).withMisfireHandlingInstructionDoNothing();
					CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(cronScheduleBuilder)
							.startAt((Date)map.get("schedulerStart")).endAt((Date)map.get("schedulerEnd")).build();
					// 创建任务
					try {
						scheduler.scheduleJob(jobDetail, cronTrigger);
					} catch (SchedulerException e) {
						log.error("create cron scheduler failed msg:{}",e.getMessage());
					}
					log.debug("register cron job startAt : {} endAt :{} cron :{}",(Date)map.get("schedulerStart"),(Date)map.get("schedulerEnd"),(String)map.get("cron"));
				}
	        }
	        
	        GroupMatcher<JobKey> groupSingleMatcher = GroupMatcher.groupEquals(Constant.CN_REPORT_SINGLE_GROUP);
	        Set<JobKey> jobSingleKeys = scheduler.getJobKeys(groupSingleMatcher);
			for (JobKey key : jobSingleKeys) {
				if(singleJobKeyMaps.keySet().contains(key.getName())) {
					continue;
				}
				scheduler.deleteJob(key);
			}
			for(String jobKeyName : singleJobKeyMaps.keySet()) {
	        	ReportTemp temp = singleJobKeyMaps.get(jobKeyName);
	    		JobKey jobKey = new JobKey(jobKeyName,Constant.CN_REPORT_SINGLE_GROUP);
				boolean exists = scheduler.checkExists(jobKey);
				if(!exists) {
					JobDetail jobDetail = JobBuilder.newJob(ReportHandlerJob.class).withIdentity(jobKey).storeDurably()
							.build();
					// 根据用户定时周期创建定时任务
					SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger().forJob(jobDetail)
					.startAt(temp.getSchedulerStart()).endAt(temp.getSchedulerEnd()).build();
					// 创建任务
					scheduler.scheduleJob(jobDetail, simpleTrigger);
					log.debug("register simple job id : {}  startAt : {} endAt :{}",temp.getId(),temp.getSchedulerStart(),temp.getSchedulerEnd());
				}
	        }
        } catch (SchedulerException e) {
			log.error("ReportRegisterJob error msg : {}",e.getMessage());
		}	
	    sw.stop();
	    log.info("ReportRegisterJob  end  excute time : {}",sw.toString());
	}
}
