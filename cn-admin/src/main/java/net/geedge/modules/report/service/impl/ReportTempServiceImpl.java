package net.geedge.modules.report.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import freemarker.template.Template;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.FreeMarkerUtil;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportTempDao;
import net.geedge.modules.report.entity.ReportCategory;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportCategoryService;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.entity.Timezone;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.service.TimezoneService;
import net.geedge.modules.sys.shiro.ShiroUtils;

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
	
	@Autowired
	private ReportCategoryService reportCategoryService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage page = new Query(ReportTemp.class).getPage(params);
		List<ReportTemp> data = this.baseMapper.queryPage(page,params);
		for(ReportTemp reportTemp : data) {
			String config = reportTemp.getConfig();
			Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
			configObject.put("dataset", null);
			reportTemp.setConfig(JSON.toJSONString(configObject));
		}
		page.setRecords(data);
		return new PageUtils(page);
	}
	
	@Override
	public ReportTemp queryInfo(Integer id) {
		ReportTemp reportTemp = this.baseMapper.selectById(id);
		String config = reportTemp.getConfig();
		Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
		configObject.put("dataset", null);
		reportTemp.setConfig(JSON.toJSONString(configObject));
		SysUserEntity user = sysUserService.getById(reportTemp.getUid());
		reportTemp.setSysUser(user);
		return reportTemp;
	}
	

	@Override
	public void removeReportTemps(String ids) {
		List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
		// 判断是否为内置
		Collection<ReportTemp> temps = this.listByIds(idList);
		for(ReportTemp temp : temps) {
			if(temp.getBuildIn().equals(1)) {
				throw new CNException(RCode.REPORT_BUILD_IN_ERROR);
			}
		}
		this.remove(new QueryWrapper<ReportTemp>().lambda().in(ReportTemp::getId, idList));
	}

	@Override
	public void saveOrModify(ReportTemp reportTemp){
		ValidateUtils.is(reportTemp.getName()).notNull(RCode.REPORT_TEMP_NAME_ISNULL);
		
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
		reportTemp.setUid(ObjectUtil.isEmpty(ShiroUtils.getUserId()) ? -1 : ShiroUtils.getUserId());
		// 处理dataset
		ReportCategory category = reportCategoryService.getById(reportTemp.getCategoryId());
		String config = reportTemp.getConfig();
		Map<String,Object> tempConfigMap = (Map<String, Object>) JSON.parse(config);
		Map<String,Object> categoryConfig = (Map<String, Object>)JSON.parse(category.getConfig());
		tempConfigMap.put("dataset",categoryConfig.get("dataset"));
		reportTemp.setConfig(JSON.toJSONString(tempConfigMap));
		reportTemp.setHtmlContent(category.getHtmlContent());
		Integer isScheduler = (Integer) tempConfigMap.get("isScheduler");
		if(isScheduler.equals(1)) {
			// 处理cron表达式
			this.cronHandler(reportTemp);
		}
		this.saveOrUpdate(reportTemp);
		//判断是新增定时任务还是立即执行以及定时任务的启停状态
		if(isScheduler.equals(0) && reportTemp.getState().equals(1)) {
			this.createJobTasks(reportTemp,null,null);
		}
	}

	public void cronHandler(ReportTemp reportTemp) {
		List<String> cronList = new ArrayList<String>();
		String config = reportTemp.getConfig();
		Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
		JSONObject schedulerConfig = (JSONObject) configObject.get("schedulerConfig");
		String schedulerType = (String) schedulerConfig.get("type");
		Date schedulerStart = reportTemp.getSchedulerStart();
		int hour = DateUtil.date(schedulerStart).hour(true);
		int minute = DateUtil.date(schedulerStart).minute();
		int second = DateUtil.date(schedulerStart).second();
		String basicScheduler = StrUtil.str(second+" "+minute+" "+hour+" ");
		Integer interval = (Integer) schedulerConfig.get("interval");
		if(ObjectUtil.isEmpty(interval)) {
			interval = 1;
		}
		switch(schedulerType) {
			case "day":
				String day ="*";
				if(interval != 1) {
					day = StrUtil.str(day+"/"+interval);
				}
				String dayCron = StrUtil.str(basicScheduler+day+" "+"* "+"?");
				cronList.add(dayCron);
				break;
			case "week":
				String week = "*";
				String weekCron = StrUtil.str(basicScheduler+"? "+"* "+"");
				JSONArray jsonWeeks = (JSONArray) schedulerConfig.get("weekDates");
				List<Integer> weeks = new ArrayList<Integer>();
				if(ObjectUtil.isNotEmpty(jsonWeeks)) {
					weeks = JSONArray.parseArray(jsonWeeks.toString(),Integer.class);
				}
				if(ObjectUtil.isNotEmpty(weeks)) {
					for(int i = 0; i < weeks.size(); i++){
	                    if(i == 0){
	                    	weekCron = StrUtil.str(weekCron+weeks.get(i));
	                    } else{
	                    	weekCron = StrUtil.str(weekCron+","+weeks.get(i));
	                    }
	                }
				}else {
					weekCron = StrUtil.str(basicScheduler+"? "+"* "+week);
				}
				cronList.add(weekCron);
				break;
			case "month":
				String month = "*";
				String monthWeek = "*";
				String monthDay = "*";
				JSONArray jsonMonthWeekDates = (JSONArray) schedulerConfig.get("monthWeekDates");
				JSONArray jsonMonths = (JSONArray) schedulerConfig.get("months");
				JSONArray jsonMonthDates = (JSONArray) schedulerConfig.get("monthDates");
				JSONArray jsonWeekDates = (JSONArray) schedulerConfig.get("weekDates");
				List<Integer> monthWeekDates = new ArrayList<Integer>();
				List<Integer> months = new ArrayList<Integer>();
				List<Integer> monthDates = new ArrayList<Integer>();
				List<Integer> weekDates = new ArrayList<Integer>();
				if(ObjectUtil.isNotEmpty(jsonMonthWeekDates)) {
					monthWeekDates = JSONArray.parseArray(jsonMonthWeekDates.toString(),Integer.class);
				}
				if(ObjectUtil.isNotEmpty(jsonMonths)) {
					months = JSONArray.parseArray(jsonMonths.toString(),Integer.class);
				}
				if(ObjectUtil.isNotEmpty(jsonMonthDates)) {
					monthDates = JSONArray.parseArray(jsonMonthDates.toString(),Integer.class);
				}
				if(ObjectUtil.isNotEmpty(jsonWeekDates)) {
					weekDates = JSONArray.parseArray(jsonWeekDates.toString(),Integer.class);
				}
				if(ObjectUtil.isNotEmpty(months)) {
					month = StrUtil.join(",", months);
				}
				if(ObjectUtil.isEmpty(monthWeekDates)) {
					
					if(ObjectUtil.isNotEmpty(monthDates)) {
						monthWeek = "? ";
						monthDay = StrUtil.join(",", monthDates);
					}
					if(interval != 1) {
						month = StrUtil.str(month+"/");
					}
					if(monthWeek.equals("*") && monthDay.equals("*")) {
						monthDay = "?";
					}
					String monthCron = StrUtil.str(basicScheduler+monthDay+" "+month+" "+monthWeek);
					cronList.add(monthCron);
				}else {
					monthDay = "?";
					if(ObjectUtil.isEmpty(weekDates)) {
						Collections.addAll(weekDates, 1,2,3,4,5,6,7);
					}
					for(Integer weekDate : weekDates) {
						for(Integer monthWeekDate : monthWeekDates) {
							if(monthWeekDate <=0 || monthWeekDate > 5 ) {
								throw new CNException(RCode.REPORT_CRON_MONTH_WEEK_DATE_ERROR);
							}
							if(monthWeekDate == 5) {
								monthWeek = StrUtil.str(weekDate+"L");
							}else {
								monthWeek = StrUtil.str(weekDate+"#"+monthWeekDate);
							}
							String monthCron = StrUtil.str(basicScheduler+monthDay+" "+month+" "+monthWeek);
							cronList.add(monthCron);
						}
					}
				}
				break;
			default:
				Date startTime = reportTemp.getSchedulerStart();
				DateTime endtime = DateUtil.offset(startTime, DateField.SECOND, 10);
				reportTemp.setSchedulerEnd(endtime);
		}
		if(ObjectUtil.isNotEmpty(cronList)) {
			configObject.put("cronExpression", JSON.toJSONString(cronList));
		}
		reportTemp.setConfig(JSON.toJSONString(configObject));
	}
	
	@Override
	public void createJobTasks(ReportTemp temp,Date startTime,Date endTime) {
		
		List<ReportTask> reportTasks = new ArrayList<ReportTask>();
	    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(def);
	    try {
            // 根据uniq创建对应的job和task数据信息
	    	ReportJob reportJob = new ReportJob();
	    	reportJob.setName(StrUtil.concat(false, temp.getName(),DateUtil.format(new Date(), "yyyyMMdd")));
	    	reportJob.setCtime(new Date());
	    	reportJob.setUniq(temp.getUniq());
	    	reportJob.setHtmlContent(temp.getHtmlContent());
	    	reportJob.setUid(1L);
	    	reportJob.setTempId(temp.getId());
	    	reportJob.setCategoryId(temp.getCategoryId());
	    	String config = temp.getConfig();
	    	Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
	    	//根据temp计算查询开始以及结束时间
    		this.timeHandler(configObject);
    		List<Map<String,Object>> datasetConfig = (List<Map<String, Object>>) configObject.get("dataset");
    		if(ObjectUtil.isNotEmpty(startTime)) {
    			configObject.put("startTime", startTime);
    		}
    		if(ObjectUtil.isNotEmpty(endTime)) {
    			configObject.put("endTime", endTime);
    		}
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
			case "this":
				if(timeUnit.equals("minute")) {
					startTime = DateUtil.truncate(today, DateField.MINUTE);
					endTime = DateUtil.offset(startTime, DateField.MINUTE, 1);
				}else if(timeUnit.equals("hour")) {
					startTime = DateUtil.truncate(today, DateField.MINUTE);
					startTime = DateUtil.truncate(today, DateField.HOUR);
					endTime = DateUtil.offset(startTime, DateField.HOUR, 1);
				}else if(timeUnit.equals("day")) {
					startTime = DateUtil.truncate(today, DateField.MINUTE);
					startTime = DateUtil.truncate(today, DateField.HOUR);
					startTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					endTime = DateUtil.offset(startTime, DateField.DAY_OF_YEAR, 1);
				}else if(timeUnit.equals("week")) {
					startTime = DateUtil.truncate(today, DateField.MINUTE);
					startTime = DateUtil.truncate(today, DateField.HOUR);
					startTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					startTime = DateUtil.truncate(today, DateField.WEEK_OF_YEAR);
					startTime = DateUtil.offset(startTime, DateField.DAY_OF_YEAR, 1);
					endTime = DateUtil.offset(startTime, DateField.WEEK_OF_YEAR, 1);
				}else if(timeUnit.equals("month")) {
					startTime = DateUtil.truncate(today, DateField.MINUTE);
					startTime = DateUtil.truncate(today, DateField.HOUR);
					startTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					startTime = DateUtil.truncate(today, DateField.WEEK_OF_YEAR);
					startTime = DateUtil.truncate(today, DateField.MONTH);
					endTime = DateUtil.offset(startTime, DateField.MONTH, 1);
				}else {
					startTime = DateUtil.truncate(today, DateField.MINUTE);
					startTime = DateUtil.truncate(today, DateField.HOUR);
					startTime = DateUtil.truncate(today, DateField.DAY_OF_YEAR);
					startTime = DateUtil.truncate(today, DateField.MONTH);
					startTime = DateUtil.truncate(today, DateField.YEAR);
					endTime = DateUtil.offset(startTime, DateField.YEAR, 1);
				}
				break;
			case "customize":
				String sTime = (String) conf.get("startTime");
				String eTime = (String) conf.get("endTime");
				startTime = DateUtil.parse(sTime);
				endTime = DateUtil.parse(eTime);
				break;
			default:
		}
		if(!StrUtil.equals(timeType, "customize")){
			// 将计算出来的时间 转成utc时间
			startTime = DateUtil.offset(startTime, DateField.MILLISECOND, -sysTimezone.getOffset());
			endTime = DateUtil.offset(endTime, DateField.MILLISECOND, -sysTimezone.getOffset());
		}
		conf.put("startTime", startTime);
		conf.put("endTime",endTime);
	}

}
