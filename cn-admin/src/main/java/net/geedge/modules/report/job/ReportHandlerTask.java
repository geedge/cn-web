package net.geedge.modules.report.job;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import freemarker.template.Template;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.FreeMarkerUtil;
import net.geedge.common.utils.Tool.ListUtil;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportResult;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportResultService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;
import net.geedge.modules.sys.service.SysConfigService;

@DisallowConcurrentExecution
public class ReportHandlerTask extends QuartzJobBean {
	private Log log = Log.get();
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ReportTaskService reportTaskService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private ReportResultService reportResultService;
	
	@Autowired
	private ReportJobService reportJobService;
	
	@Autowired
	private ReportTempService reportTempService;
	
	@Autowired
	private RestTemplate restTemplate;
		
	@Value("${cn.reportTaskTimeout:30000}")
	private Integer reportTaskTimeout;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Thread.currentThread().setName("ReportHandlerTask");
		// 检查 haMode
    	String serverId = (String) redisTemplate.opsForValue().get(Constant.SYS_HA_LOCK);
        if(!StrUtil.equals(serverId, Constant.SERVER_ID)) return;
        
        log.info("ReportHandlerTask start");
        
        StopWatch sw = new StopWatch();
        sw.start();   
        
        List<ReportTask> tasks = reportTaskService.list(new QueryWrapper<ReportTask>().lambda().lt(ReportTask::getState, 3).orderByAsc(ReportTask::getState));
        
        Map<Integer, List<ReportTask>> stateTasks = tasks.stream().collect(Collectors.groupingBy(ReportTask::getState));
        
        /**
         * 根据延迟时间过滤掉不符合条件的任务数据
         */
        List<ReportTask> unsubmit = stateTasks.get(0);
        String delayTime = sysConfigService.getValue("delay_time");
        if(StrUtil.isNotBlank(delayTime) && Integer.valueOf(delayTime) > 0 && ObjectUtil.isNotEmpty(unsubmit)) {
        	DateTime dateTime = DateUtil.offset(new Date(), DateField.MINUTE, -Integer.valueOf(delayTime));
            List<ReportTask> collects = unsubmit.stream().filter(task -> task.getEndTime().getTime() < dateTime.getTime()).collect(Collectors.toList());
            stateTasks.put(0, collects);
        }
        
        // 获取远程服务接口地址信息
        String rpcHost = sysConfigService.getValue("galaxy_url");
        if(ObjectUtils.isEmpty(rpcHost)) {
        	log.warn("rpc config error  please check sysconfig data with key galaxy_url");
        	return;
        }
        
        this.createRpcJob(rpcHost,stateTasks);
        
		this.modifyJobPercent(rpcHost, stateTasks);
		
		this.getResult(rpcHost,stateTasks);
		
		this.modifyJobState();

		this.s3Upload();
		
        sw.stop();
        log.info("ReportHandlerTask  end  excute time : {}",sw.toString());
	}

	/**
     * 将状态为0的报表任务进行创建
     */
	public void createRpcJob(String rpcHost,Map<Integer, List<ReportTask>> stateTasks) {
		if(ObjectUtil.isNotEmpty(stateTasks.get(0))) {			
        	//http://ip:port/admin/query/jobs
        	String createJobPath = StrUtil.str(rpcHost+"admin/query/jobs");
        	List<ReportTask> initTasks = stateTasks.get(0);
        	Set<Integer> tempIds = initTasks.stream().map(ReportTask::getTempId).collect(Collectors.toSet());
        	Collection<ReportTemp> temps = reportTempService.listByIds(tempIds);
        	Map<Integer, ReportTemp> idTemp = temps.stream().collect(Collectors.toMap(ReportTemp::getId, Function.identity()));
        	List<CompletableFuture<Map<String, Object>>> futureList = ListUtil.list(false);
        	try {
	        	for(ReportTask task : initTasks) {
	        		Map<String,Object> data = new HashMap<String,Object>();
	        		String source = task.getSource();
	        		String config = task.getConfig();
	        		Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
	        		data.put("startTime", task.getStartTime().getTime()/1000);
	        		data.put("endTime", task.getEndTime().getTime()/1000);
	        		long timeDiff = task.getEndTime().getTime()/1000 - task.getStartTime().getTime()/1000;
	        		data.put("timeDiff",timeDiff);
	        		ReportTemp temp = idTemp.get(task.getTempId());
	        		Map<String,Object> tempConfigObject = (Map<String, Object>) JSONObject.parse(temp.getConfig());
	        		List<Map> queryParam = (List<Map>) tempConfigObject.get("queryParam");
	        		if(ObjectUtil.isNotEmpty(queryParam)) {
		        		Map<String,Object> mapping = new HashMap<String,Object>();
		        		for(Map<String,Object> m : queryParam) {
		        			mapping.put((String)m.get("key"), m.get("value"));
		        		}
		        		if(ObjectUtil.isNotEmpty(mapping)) {
		        			data.putAll(mapping);       
		        		}
	        		}
	        		String querySql = (String) configObject.get("querySql");
	        		Template template = FreeMarkerUtil.stringToTemplate("createJob"+task.getId(), querySql);
	        		StringWriter writer = new StringWriter();
	    			template.process(data, writer);
	    			writer.flush();
	    			querySql = writer.toString();
	        		Map<String,String> param = new HashMap<String,String>();
	        		param.put("query.type", "report");
	        		param.put("query.data_source", source);
	        		param.put("custom.report.sql", querySql);
	        		
	        		CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
	        			ResponseEntity<Object> response = restTemplate.exchange(createJobPath, HttpMethod.POST, new HttpEntity<Object>(param), Object.class, new HashMap<String,Object>());
		        		Map result = (Map) response.getBody();
	        			result.put("taskId", task.getId());
	        			return result;
	    			});
	        		futureList.add(future);
	        	}
	        	
	        	Map<Integer, ReportTask> idTasks = initTasks.stream().collect(Collectors.toMap(ReportTask::getId, Function.identity()));
				// 等待所有结果返回
				CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
							.get(reportTaskTimeout, TimeUnit.MILLISECONDS);
				
				futureList.forEach(future -> {
					Map<String, Object> result = future.getNow(null);
					Integer status = (Integer) result.get("status");
        			if(status == 201) {
        				List<Map<String,Object>> resultData = (List<Map<String, Object>>) result.get("data");
        				Integer taskId = (Integer) result.get("taskId");
        				String jobId = (String) resultData.get(0).get("jobId");
        				ReportTask task = idTasks.get(taskId);
        				if(ObjectUtil.isNotEmpty(task)) {
        					task.setState(1);
        					task.setFailedMsg("");
        					task.setCredential(jobId);
        				}
        			}else {
        				String failedMsg = (String) result.get("message");
        				List<Map<String,Object>> resultData = (List<Map<String, Object>>) result.get("data");
        				Integer taskId = (Integer) result.get("taskId");
        				ReportTask task = idTasks.get(taskId);
        				task.setState(4);
        				task.setFailedMsg(failedMsg);
        				log.debug("create job failed please check config");
        			}
				});
				
				// 将注册成功的任务状态进行修改 同时更新字段信息
				reportTaskService.updateBatchById(initTasks);
        	} catch (Exception e) {
				log.error("createRpcJob error msg : {}",e.getMessage());
			}
        }
	}
	
	/**
     * 将状态为1的报表进行状态查询 及时更新进度
     */
	public void modifyJobPercent(String rpcHost,Map<Integer, List<ReportTask>> stateTasks) {
		if(ObjectUtil.isNotEmpty(stateTasks.get(1))) {
        	List<ReportTask> modifyTasks = stateTasks.get(1);
        	List<CompletableFuture<Map<String, Object>>> futureList = ListUtil.list(false);
        	for(ReportTask task : modifyTasks) {
        		//http://ip:port/admin/query/jobs/[jobId]/status
            	String modifyJobPath = StrUtil.str(rpcHost+"admin/query/jobs/"+task.getCredential()+"/status");
            	CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
            		ResponseEntity<Map> response = restTemplate.getForEntity(modifyJobPath,Map.class);
	        		Map result = (Map) response.getBody();
        			result.put("taskId", task.getId());
        			return result;
    			});
        		futureList.add(future);
        	}
			
			// 等待所有结果返回
			try {
				CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
						.get(reportTaskTimeout, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				log.error("modifyJobPercent error msg {}",e.getMessage());
			}
			
        	Map<Integer, ReportTask> idTasks = modifyTasks.stream().collect(Collectors.toMap(ReportTask::getId, Function.identity()));
			
			futureList.forEach(future -> {
				Map<String, Object> result = future.getNow(null);
				Integer status = (Integer) result.get("status");
    			if(status == 200) {
    				List<Map<String,Object>> resultData = (List<Map<String, Object>>) result.get("data");
    				Integer taskId = (Integer) result.get("taskId");
    				ReportTask task = idTasks.get(taskId);
    				if(ObjectUtil.isNotEmpty(task)) { 
    					boolean isDone = (boolean) resultData.get(0).get("is_done");
    					boolean isCanceled = (boolean) resultData.get(0).get("is_canceled");
    					boolean isFailed = (boolean) resultData.get(0).get("is_failed");
    					Double doneProgress = (Double) resultData.get(0).get("done_progress");
    					task.setPercent(doneProgress.doubleValue());
    					if(isDone && !isFailed && !isCanceled) {
    						task.setState(2);
    						task.setFailedMsg("");
    					}
    					if(isCanceled) {
    						task.setState(4);
    						task.setFailedMsg("Canceled");
    					}
    					if(isFailed) {
    						String failedMsg = (String) result.get("message");
    						task.setState(4);
    						task.setFailedMsg(failedMsg);
    					}
    				}
    			}else {
    				log.debug("modifyJobPercent error code : {}",status);
    			}
			});
			
			// 将更新任务状态以及完成度
			reportTaskService.updateBatchById(modifyTasks);
        }
	}
	
	/**
     * 获取状态为2的报表结果数据 将结果进行处理提交到数据库中 
     */
	public void getResult(String rpcHost,Map<Integer, List<ReportTask>> stateTasks) {
		if(ObjectUtil.isNotEmpty(stateTasks.get(2))) {
        	List<ReportTask> resultTasks = stateTasks.get(2);
        	List<CompletableFuture<Map<String, Object>>> futureList = ListUtil.list(false);
        	for(ReportTask task : resultTasks) {
        		CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
    				// http://ip:port/admin/query/jobs/[jobId]/report
                	String resultJobPath = StrUtil.str(rpcHost+"admin/query/jobs/"+task.getCredential()+"/report");
                	ResponseEntity<Map> response = restTemplate.getForEntity(resultJobPath,Map.class);
	        		Map result = (Map) response.getBody();
        			result.put("taskId", task.getId());
        			return result;
    			});
        		futureList.add(future);
        	}
			
			// 等待所有结果返回
			try {
				CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
						.get(reportTaskTimeout, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				log.error(e);
			}
			
			Map<Integer, ReportTask> idTasks = resultTasks.stream().collect(Collectors.toMap(ReportTask::getId, Function.identity()));
			List<ReportResult> results = new ArrayList<ReportResult>();
			futureList.forEach(future -> {
				Map<String, Object> result = future.getNow(null);
				log.debug("getResult result info : {}",JSON.toJSON(result));
				Integer status = (Integer) result.get("status");
    			if(status == 200) {
    				List<Map<String,Object>> resultData = (List<Map<String, Object>>) result.get("data");
    				Integer taskId = (Integer) result.get("taskId");
    				ReportTask task = idTasks.get(taskId);
    				if(ObjectUtil.isNotEmpty(task)) {
    					String res = this.resultHandler(resultData, task.getConfig());
    					ReportResult rr = new ReportResult();
    					rr.setCtime(new Date());
    					rr.setResult(res);
    					rr.setTaskId(task.getId());
						results.add(rr);
    					task.setState(3);
    				}
    			}else {
    				log.debug("getResult error code : {}",status);
    			}
			});
			
			// 批量保存结果信息
			if(ObjectUtil.isNotEmpty(results)) {
				reportResultService.saveBatch(results);
			}
			reportTaskService.updateBatchById(resultTasks);
        }
	}
	
	/**
	 * 结果处理
	 * @param resultData
	 * @param config
	 * @return
	 */
	public String resultHandler(List<Map<String,Object>> resultData,String config) {
		Map<String,String> configObject = (Map<String, String>) JSONObject.parse(config);
		String alias = configObject.get("valueAlias");
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(alias, resultData);
		return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 *  判断job任务是否正常完成 
	 */
	public void modifyJobState() {
		List<ReportJob> reportJobs = reportJobService.list(new QueryWrapper<ReportJob>().lambda().eq(ReportJob::getState, 0));
        Map<Integer, ReportJob> idJobs = reportJobs.stream().collect(Collectors.toMap(ReportJob::getId, Function.identity()));
        List<Integer> jobIds = reportJobs.stream().map(ReportJob::getId).collect(Collectors.toList());
        List<ReportTask> unfinishTasks = reportTaskService.list(new QueryWrapper<ReportTask>().lambda().in(ReportTask::getJobId, jobIds).lt(ReportTask::getState, 3));
        List<Integer> unfinshJobs = unfinishTasks.stream().map(ReportTask::getJobId).collect(Collectors.toList());
        List<Integer> finishJobs = jobIds.stream().filter(item -> !unfinshJobs.contains(item)).collect(Collectors.toList());
        List<ReportTask> failTasks = reportTaskService.list(new QueryWrapper<ReportTask>().lambda().in(ReportTask::getJobId, jobIds).eq(ReportTask::getState, 4));
        List<Integer> failJobs = failTasks.stream().map(ReportTask::getJobId).collect(Collectors.toList());
        String s3Enable = sysConfigService.getValue("s3_enable");
        for(Integer jobId : finishJobs) {
        	ReportJob reportJob = idJobs.get(jobId);
        	if(failJobs.contains(jobId)) {
        		reportJob.setState(2);
        	}else {
        		reportJob.setState(1);
        	}
    		if(Integer.valueOf(s3Enable)==0) {
    			reportJob.setFtime(new Date());
    		}
        }
        // 批量修改任务状态信息
        if(ObjectUtil.isNotEmpty(reportJobs)) {
        	reportJobService.updateBatchById(reportJobs);
        }        
	}
	/**
     * 判断是否上传到s3服务器
     */
	public void s3Upload() {
		String s3Enable = sysConfigService.getValue("s3_enable");
        List<ReportJob> finishJobs = reportJobService.list(new QueryWrapper<ReportJob>().lambda().eq(ReportJob::getState, 1).eq(ReportJob::getUpload, 0));
        for(ReportJob reportJob : finishJobs) {
        	try {
        		if(Integer.valueOf(s3Enable)==1) {
        			String html = reportJobService.queryView(reportJob.getId());
					if(StrUtil.isEmpty(html)) {
						log.error("s3Upload get html content error");
						continue;
					}
					String fileName = reportJob.getName()+reportJob.getCtime().getTime()+".pdf";
					boolean exist = FileUtil.exist(Constant.TEMP_PATH+File.separator+fileName);
					if(!exist) {
						log.error("s3upload file not existsm file name :{}",fileName);
						continue;
					}
	            	reportJobService.upload(fileName);
	            	reportJob.setUpload(1);
	            	reportJob.setFtime(new Date());
	            }
        	}catch(Exception e) {
        		log.error("s3Upload error  msg : {}",e.getMessage());
        	}
        }
        
        // 批量修改任务状态信息
        if(ObjectUtil.isNotEmpty(finishJobs)) {
        	reportJobService.updateBatchById(finishJobs);
        }        
	}
}
