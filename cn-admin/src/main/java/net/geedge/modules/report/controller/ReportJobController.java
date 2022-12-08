package net.geedge.modules.report.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/report/job")
public class ReportJobController extends AbstractController{
	@Autowired
	private ReportJobService reportJobService;
	
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = reportJobService.queryPage(params);
		return R.ok(page);
	}
	
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id){
		ReportJob reportJob = reportJobService.queryInfo(id);
		return R.ok(reportJob);
	}
	
	@DeleteMapping
	@SysLog(operation = OperationEnum.DELETE, type = TypeEnum.REPORTJOB)
	@RequiresPermissions({"report:job:delete"})
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.REPORT_JOB_ID_ISNULL);
		reportJobService.removeReportJobs(ids);
		return R.ok();
	}
	
	@GetMapping("/view")
	public void reportView(@RequestParam Integer id,HttpServletResponse response) {
		String htmlContent = reportJobService.queryView(id);
		try {
			ServletOutputStream out = response.getOutputStream();
			out.write(htmlContent.getBytes("UTF-8"));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@GetMapping("/downloadPdf")
	public R downloadPdf(@RequestParam Integer id,HttpServletResponse response) {
		try {
			reportJobService.downloadPdf(id,response);
			return null;
		}catch(Exception e) {
			return R.error(999,e.getMessage());
		}
	}
	
	@GetMapping("/batchDownloadPdf")
	public R batchDownloadPdf(@RequestParam String ids,HttpServletResponse response) {
		ValidateUtils.is(ids).notNull(RCode.REPORT_JOB_ID_ISNULL);
		try {
			reportJobService.batchDownloadPdf(Arrays.asList(ids.split(",")),response);
			return null;
		}catch(Exception e) {
			return R.error(999,e.getMessage());
		}
	}
	
	@PutMapping("/restart")
	public R restartJob(@RequestBody Map<String,Object> param) {
		reportJobService.restartJob(param);
		return R.ok();
	}
	
	@PutMapping("/reupload")
	public R reuploadJob(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.REPORT_JOB_ID_ISNULL);
		reportJobService.reuploadJob(ids);
		return R.ok();
	}
}
