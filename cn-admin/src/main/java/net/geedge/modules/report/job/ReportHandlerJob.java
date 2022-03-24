package net.geedge.modules.report.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
	
	@Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
	
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
        List<ReportTemp> temps = reportTempService.list(new QueryWrapper<ReportTemp>().lambda().eq(ReportTemp::getState, 1));
        
	    List<ReportTask> reportTasks = new ArrayList<ReportTask>();
	    
	    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(def);
	    try {
            // 根据uniq创建对应的job和task数据信息
    	    for(ReportTemp temp : temps) {
    	    	ReportJob reportJob = new ReportJob();
    	    	reportJob.setName(StrUtil.concat(false, temp.getName(),DateUtil.format(DateUtil.yesterday(), "yyyyMMdd"),"job"));
    	    	reportJob.setCtime(new Date());
    	    	reportJob.setUniq(temp.getUniq());
    	    	reportJob.setHtmlContent(temp.getHtmlContent());
    	    	reportJobService.save(reportJob);
    	    	
    	    	String config = temp.getConfig();
    	    	List<Map<String,Object>> configObject = (List<Map<String, Object>>) JSONObject.parse(config);
    	    	for(Map<String,Object> conf : configObject) {
    	    		ReportTask reportTask = new ReportTask();
    	    		reportTask.setJobId(reportJob.getId());
    	    		reportTask.setType((String)conf.get("type"));
    	    		reportTask.setState(0);
    	    		reportTask.setPercent(0.0);
    	    		reportTask.setSource(temp.getSource());
    	    		reportTask.setTempId(temp.getId());
    	    		//根据temp计算查询开始以及结束时间
    	    		this.taskTimeSqlHandler(conf,reportTask);
    	    		reportTask.setConfig(JSON.toJSONString(conf));
    	    		reportTask.setCtime(new Date());
    	    		reportTasks.add(reportTask);
    	    	}
    	    }
    	    reportTaskService.saveBatch(reportTasks);
    	    dataSourceTransactionManager.commit(transactionStatus);
	    }catch(Exception e) {
	    	log.error("ReportHandlerJob error msg : {}",e.getMessage());
	    	dataSourceTransactionManager.rollback(transactionStatus);
	    }
	    
	    
	    sw.stop();
	    log.info("ReportHandlerJob create tasks end  excute time : {}",sw.toString());
	}
	
	public void taskTimeSqlHandler(Map<String,Object> conf,ReportTask reportTask) {
		Integer isRepeat = (Integer) conf.get("isRepeat");
		String startTime = (String) conf.get("startTime");
		String endTime = (String) conf.get("endTime");
		if(isRepeat == 1) {
			DateTime st = DateUtil.parse(startTime).offset(DateField.DAY_OF_YEAR, -1);
			DateTime et = DateUtil.parse(endTime).offset(DateField.DAY_OF_YEAR, -1);
			conf.put("startTime", st);
			conf.put("endTime", st);
			reportTask.setStartTime(st);
			reportTask.setEndTime(et);
		}else{
			reportTask.setStartTime(DateUtil.parse(startTime));
			reportTask.setEndTime(DateUtil.parse(endTime));
		}
	}
}
