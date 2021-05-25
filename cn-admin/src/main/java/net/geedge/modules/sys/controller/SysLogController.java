package net.geedge.modules.sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.sys.service.SysLogService;


/**
 * 系统日志
 *

 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);
		return R.ok(page);
	}
	
}
