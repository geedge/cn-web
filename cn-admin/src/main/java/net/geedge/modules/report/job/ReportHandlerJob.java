package net.geedge.modules.report.job;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.modules.election.dao.LeaderElectionDao;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;

@DisallowConcurrentExecution
public class ReportHandlerJob extends QuartzJobBean {
	private Log log = Log.get();
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
		LeaderElectionDao dao = LeaderElectionDao.getInstance();
        boolean isLeader = dao.isLeader(Constant.SYS_HA_LOCK, Constant.SERVER_ID);
        log.info("[LoginSessionCleanJob] [Get HA_LOCK] [isLeader: {}]", isLeader);
        if (BooleanUtil.negate(isLeader)) return;
        
        log.info("ReportHandlerJob create tasks start");
        StopWatch sw = new StopWatch();
        sw.start();        

        JobKey key = context.getJobDetail().getKey();
        List<String> split = StrUtil.split(key.getName(),StrUtil.DASHED);
        Integer tempId = Integer.valueOf(split.get(1));
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
