package net.geedge.modules.report.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import cn.hutool.core.date.DateUtil;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;
import net.geedge.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/report/temp")
public class ReportTempController extends AbstractController{
	
	@Autowired
	private ReportTempService reportTempService;
	
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = reportTempService.queryPage(params);
		return R.ok(page);
	}
	
	@PostMapping
	public R save(@RequestBody ReportTemp reportTemp) {
		reportTempService.saveOrModify(reportTemp);
		return R.ok();
	}
	
	@PutMapping
	public R update(@RequestBody ReportTemp reportTemp) {
		ValidateUtils.is(reportTemp.getId()).notNull(RCode.REPORT_TEMP_ID_ISNULL);
		reportTempService.saveOrModify(reportTemp);
		return R.ok();
	}
	
	@DeleteMapping
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.REPORT_TEMP_ID_ISNULL);
		reportTempService.removeReportTemps(ids);
		return R.ok();
	}
	
	@PostMapping("/createJob")
	public R createJob(@RequestParam Integer id,@RequestParam String startTime,@RequestParam String endTime) {
		ValidateUtils.is(id).notNull(RCode.REPORT_TEMP_ID_ISNULL);
		ReportTemp temp = reportTempService.getById(id);
		if(ObjectUtils.isNotEmpty(temp)) {
			reportTempService.createJobTasks(temp,DateUtil.parse(startTime),DateUtil.parse(endTime));
		}else {
			return R.error(RCode.REPORT_TEMP_NOT_EXISTS);
		}
		return R.ok();
	}
}
