package net.geedge.modules.report.controller;

import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.entity.ReportCategory;
import net.geedge.modules.report.service.ReportCategoryService;
import net.geedge.modules.sys.controller.AbstractController;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;

@RestController
@RequestMapping("/report/category")
public class ReportCategoryController extends AbstractController{
	
	@Autowired
	private ReportCategoryService reportCategoryService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = reportCategoryService.queryPage(params);
		return R.ok(page);
	}
	
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id) {
		ReportCategory reportCategory = reportCategoryService.queryInfo(id);
		SysUserEntity user = sysUserService.getById(reportCategory.getUid());
		reportCategory.setSysUser(user);
		return R.ok(reportCategory);
	}
	
	@DeleteMapping
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.REPORT_CATEGORY_ID_ISNULL);
		reportCategoryService.removeReportCategorys(ids);
		return R.ok();
	}
	
}
