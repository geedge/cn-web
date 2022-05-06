package net.geedge.modules.report.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;

@DisallowConcurrentExecution
public class ReportHandlerJob extends QuartzJobBean {
	private Log log = Log.get();
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ReportTempService reportTempService;
	
	@Autowired
	private ReportJobService reportJobService;
	
	@Autowired
	private ReportTaskService reportTaskService;
		
	/**
	 * 该定时任务： 根据temp数据定时生成job任务以及task数据信息
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Thread.currentThread().setName("ReportHandlerJob");
		// 检查 haMode
    	String serverId = (String) redisTemplate.opsForValue().get(Constant.SYS_HA_LOCK);
        if(!StrUtil.equals(serverId, Constant.SERVER_ID)) return;
        
        log.info("ReportHandlerJob create tasks start");
        StopWatch sw = new StopWatch();
        sw.start();        

        JobKey key = context.getJobDetail().getKey();
        String[] split = StrUtil.split(key.getName(),StrUtil.DASHED);
        Integer tempId = Integer.valueOf(split[1]);
        ReportTemp temp = reportTempService.getById(tempId);
        if(ObjectUtil.isNotEmpty(temp)) {
        	reportTempService.createJobTasks(temp,null,null);
        }else {
        	log.warn("ReportHandlerJob temp not exists,please check DB data");
        }
        
	    sw.stop();
	    log.info("ReportHandlerJob create tasks end  excute time : {}",sw.toString());
	}
}
