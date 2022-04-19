package net.geedge.modules.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportTempDao;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;
import net.geedge.modules.sys.entity.Timezone;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.TimezoneService;

@Service("reportTempService")
public class ReportTempServiceImpl extends ServiceImpl<ReportTempDao, ReportTemp> implements ReportTempService {
	
	private Log log = Log.get();
	
	@Autowired
	private ReportJobService reportJobService;
	
	@Autowired
	private ReportTaskService reportTaskService;
	
	@Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
	
	@Autowired
	private TimezoneService timezoneService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage page = new Query(ReportTemp.class).getPage(params);
		List<ReportTemp> data = this.baseMapper.queryPage(page,params);
		page.setRecords(data);
		return new PageUtils(page);
	}

	@Override
	public void removeReportTemps(String ids) {
		List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
		this.remove(new QueryWrapper<ReportTemp>().lambda().in(ReportTemp::getId, idList));
	}

	@Override
	public void saveOrModify(ReportTemp reportTemp){
		ValidateUtils.is(reportTemp.getName()).notNull(RCode.REPORT_TEMP_NAME_ISNULL)
				.and(reportTemp.getHtmlContent()).notNull(RCode.REPORT_TEMP_HTML_CONTENT_ISNULL)
				.and(reportTemp.getConfig()).notNull(RCode.REPORT_TEMP_CONFIG_ISNULL);
		
		// 生成唯一uuid标识
		String uuid = UUID.randomUUID().toString();
		if(ObjectUtils.isEmpty(reportTemp.getUniq())) {
			reportTemp.setUniq(uuid);
		}
		if(ObjectUtils.isEmpty(reportTemp.getCtime())) {
			reportTemp.setCtime(new Date());
		}
		reportTemp.setUtime(new Date());
		reportTemp.setState(1);
		
		this.saveOrUpdate(reportTemp);
		
		String config = reportTemp.getConfig();
		Map<String,Object> tempConfigMap = (Map<String, Object>) JSON.parse(config);
		Integer isRepeat = (Integer) tempConfigMap.get("isRepeat");
		//判断是新增定时任务还是立即执行以及定时任务的启停状态
		if(isRepeat.equals(0) && reportTemp.getState().equals(1)) {
			this.createJobTasks(reportTemp);
		}
	}

	@Override
	public void createJobTasks(ReportTemp temp) {
		
		List<ReportTask> reportTasks = new ArrayList<ReportTask>();
	    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(def);
	    try {
            // 根据uniq创建对应的job和task数据信息
	    	ReportJob reportJob = new ReportJob();
	    	reportJob.setName(StrUtil.concat(false, temp.getName(),new Date().getTime()+"","job"));
	    	reportJob.setCtime(new Date());
	    	reportJob.setUniq(temp.getUniq());
	    	reportJob.setHtmlContent(temp.getHtmlContent());
	    	String config = temp.getConfig();
	    	Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
	    	//根据temp计算查询开始以及结束时间
    		this.timeHandler(configObject);
    		List<Map<String,Object>> datasetConfig = (List<Map<String, Object>>) configObject.get("dataset");
    		reportJob.setStartTime((DateTime)configObject.get("startTime"));
    		reportJob.setEndTime((DateTime)configObject.get("endTime"));
    		reportJobService.save(reportJob);
	    	for(Map<String,Object> datasetConf : datasetConfig) {
	    		ReportTask reportTask = new ReportTask();
	    		reportTask.setJobId(reportJob.getId());
	    		reportTask.setType((String)datasetConf.get("type"));
	    		reportTask.setState(0);
	    		reportTask.setPercent(0.0);
	    		reportTask.setSource(temp.getSource());
	    		reportTask.setTempId(temp.getId());
	    		reportTask.setStartTime((DateTime)configObject.get("startTime"));
	    		reportTask.setEndTime((DateTime)configObject.get("endTime"));
	    		reportTask.setConfig(JSON.toJSONString(datasetConf));
	    		reportTask.setCtime(new Date());
	    		reportTasks.add(reportTask);
	    	}
    	    reportTaskService.saveBatch(reportTasks);
    	    dataSourceTransactionManager.commit(transactionStatus);
	    }catch(Exception e) {
	    	dataSourceTransactionManager.rollback(transactionStatus);
	    	log.error("createJobTasks error :{}",e.getMessage());
	    }
	}

	@Override
	public void timeHandler(Map<String, Object> conf) {
		JSONObject timeConfMap = (JSONObject) conf.get("timeConfig");
//		Map<String,Object> timeConfMap = (Map<String, Object>) JSON.parse(timeConf);
		String timeType = (String) timeConfMap.get("type");
		Integer offset = (Integer) timeConfMap.get("offset");
		String timeUnit = (String) timeConfMap.get("unit");
		DateTime startTime = null;
		DateTime endTime = null;
		// 获取今天日期为utc 需先转成当前时区数据 然后计算出开始结束时间
		Date today = new Date();
		Timezone sysTimezone = timezoneService.getOne(new LambdaQueryWrapper<Timezone>().eq(Timezone::getName, sysConfigService.getValue("timezone")));
		today = DateUtil.offset(today, DateField.MILLISECOND, sysTimezone.getOffset());
		switch(timeType) {
			case "yesterday":
				DateTime yesterday = DateUtil.yesterday();
				yesterday = DateUtil.offset(yesterday,DateField.MILLISECOND,sysTimezone.getOffset());
				startTime = DateUtil.beginOfDay(yesterday);
				endTime = DateUtil.endOfDay(yesterday);
				break;
			case "today":
				startTime = DateUtil.beginOfDay(today);
				endTime = DateUtil.date(today);
				break;
			case "last":
				endTime = DateUtil.date(today);
				if(timeUnit.equals("minute")) {
					startTime = DateUtil.offset(endTime, DateField.MINUTE, -offset);
				}else if(timeUnit.equals("hour")) {
					startTime = DateUtil.offset(endTime, DateField.HOUR, -offset);
				}else if(timeUnit.equals("day")) {
					startTime = DateUtil.offset(endTime, DateField.DAY_OF_YEAR, -offset);
				}else if(timeUnit.equals("week")) {
					startTime = DateUtil.offset(endTime, DateField.WEEK_OF_YEAR, -offset);
				}else if(timeUnit.equals("month")) {
					startTime = DateUtil.offset(endTime, DateField.MONTH, -offset);
				}else {
					startTime = DateUtil.offset(endTime, DateField.YEAR, -offset);
				}
				break;
			case "previous":
				if(timeUnit.equals("minute")) {
					endTime = DateUtil.truncate(today, DateField.MINUTE);
					startTime = DateUtil.offset(endTime, DateField.MINUTE, -offset);
				}else if(timeUnit.equals("hour")) {
					endTime = DateUtil.truncate(today, DateField.MINUTE);
					endTime = DateUtil.truncate(today, DateField.HOUR);
					startTime = DateUtil.offset(endTime, DateField.HOUR, -offset);
				}else if(timeUnit.equals("day")) {
					endTime = DateUtil.truncate(today, DateField.MINUTE);
					endTime = DateUtil.truncate(today, DateField.HOUR);
					endTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					startTime = DateUtil.offset(endTime, DateField.DAY_OF_YEAR, -offset);
				}else if(timeUnit.equals("week")) {
					endTime = DateUtil.truncate(today, DateField.MINUTE);
					endTime = DateUtil.truncate(today, DateField.HOUR);
					endTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					endTime = DateUtil.truncate(today, DateField.WEEK_OF_YEAR);
					startTime = DateUtil.offset(endTime, DateField.WEEK_OF_YEAR, -offset);
				}else if(timeUnit.equals("month")) {
					endTime = DateUtil.truncate(today, DateField.MINUTE);
					endTime = DateUtil.truncate(today, DateField.HOUR);
					endTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					endTime = DateUtil.truncate(today, DateField.WEEK_OF_YEAR);
					endTime = DateUtil.truncate(today, DateField.MONTH);
					startTime = DateUtil.offset(endTime, DateField.MONTH, -offset);
				}else {
					endTime = DateUtil.truncate(today, DateField.MINUTE);
					endTime = DateUtil.truncate(today, DateField.HOUR);
					endTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					endTime = DateUtil.truncate(today, DateField.MONTH);
					endTime = DateUtil.truncate(today, DateField.YEAR);
					startTime = DateUtil.offset(endTime, DateField.YEAR, -offset);
				}
				break;
			case "customize":
				String sTime = (String) timeConfMap.get("startTime");
				String eTime = (String) timeConfMap.get("endTime");
				startTime = DateUtil.parse(sTime);
				endTime = DateUtil.parse(eTime);
				break;
			default:
		}
		if(!StrUtil.equals(timeType, "customize")){
			// 将计算出来的时间 转成utc时间
			startTime = DateUtil.offset(startTime, DateField.MILLISECOND, -sysTimezone.getOffset());
			endTime = DateUtil.offset(endTime, DateField.MILLISECOND, -sysTimezone.getOffset());
			conf.put("startTime", startTime);
			conf.put("endTime",endTime);
		}
	}
	
}
