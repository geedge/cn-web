/**

 *

 *

 */

package net.geedge.modules.sys.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.validator.ValidatorUtils;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysConfigService;

/**
 * 系统配置信息
 *

 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigService.queryPage(params);
		return R.ok().put("page", page);
	}


	/**
	 * 配置信息
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.getById(id);
		return R.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@SysLog
	@PostMapping()
	public R save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		sysConfigService.saveConfig(config);
		return R.ok();
	}

	/**
	 * 修改配置
	 */
	@SysLog
	@PutMapping()
	public R update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		sysConfigService.update(config);
		return R.ok().putData("id", config.getId());
	}

	/**
	 * 删除配置
	 */
	@SysLog
	@DeleteMapping()
	public R delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		return R.ok();
	}

}
