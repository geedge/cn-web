/**

 *

 *

 */

package net.geedge.modules.sys.controller;

import java.util.Map;
import java.util.regex.Pattern;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONObject;
import net.geedge.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import net.geedge.common.validator.ValidatorUtils;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysConfigService;

/**
 * 系统配置信息
 *
 * 
 */
@RestController
@RequestMapping("/sys")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@GetMapping("/config")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = sysConfigService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 配置信息
	 */
	@GetMapping("/config/{id}")
	public R info(@PathVariable("id") Long id) {
		SysConfigEntity config = sysConfigService.getById(id);
		return R.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@SysLog
	@PostMapping("/config")
	public R save(@RequestBody SysConfigEntity config) {
		ValidatorUtils.validateEntity(config);
		sysConfigService.saveConfig(config);
		return R.ok();
	}

	/**
	 * 修改配置
	 */
	@SysLog
	@PutMapping("/config")
	public R update(@RequestBody SysConfigEntity config) {
		ValidatorUtils.validateEntity(config);
		sysConfigService.update(config);
		return R.ok().putData("id", config.getId());
	}

	/**
	 * 删除配置
	 */
	@SysLog
	@DeleteMapping("/config")
	public R delete(@RequestBody Long[] ids) {
		sysConfigService.deleteBatch(ids);
		return R.ok();
	}

	/**
	 * 系统外观查询
	 *
	 */
	@GetMapping("/appearance")
	public R queryAppearance() {
		Map<String, String> appearance = sysConfigService.queryAppearance();
		return R.ok(appearance);
	}

	/**
	 * 系统外观更改
	 */
	@PutMapping("/appearance")
	@SysLog(operation = OperationEnum.UPDATE, type = TypeEnum.SYSTEM)
	public R updateAppearance(@RequestBody Map<String, String> params) {
		sysConfigService.saveBasic(params);
		return R.ok();
	}

	/**
	 * 根据key名查询
	 *
	 */
	@GetMapping("/{key}")
	public R queryByKey(@PathVariable("key") String ckey) {
		SysConfigEntity config = sysConfigService.queryByKey(ckey);
		return R.ok(config);
	}

	/**
	 * 根据key名批量修改
	 *
	 */
	@PutMapping("/batchModify")
	@SysLog(operation = OperationEnum.UPDATE, type = TypeEnum.SYSTEM)
	public R updateSecurity(@RequestBody Map<String,String> params) {
		sysConfigService.updateByKey(params);
		return R.ok();
	}

	/**
	 * 系统邮箱配置查询
	 *
	 */
	/*
	@GetMapping("/email")
	public R queryEmail(){
	String paramKey = "email";
	JSONObject email = sysConfigService.queryConfig(paramKey);
	email.remove("authPin");
	return R.ok(email);
	}
	*//**
		 * 系统邮箱配置修改
		 *
		 */
	/*
	@PutMapping("/email")
	@SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.SYSTEM)
	public R updateEmail(@RequestBody JSONObject email){
	String paramKey = "email";
	int isUpdate = sysConfigService.updateConfig(paramKey, email);
	if(isUpdate == 0){
		return R.error();
	}
	return R.ok();
	}
	*//**
		 * 系统邮箱配置测试
		 *
		 *//*
			@PutMapping("/email/test")
			@SysLog
			public R testEmail(@RequestBody JSONObject email){
			boolean isSuccess = sysConfigService.testEmail(email);
			if(!isSuccess){
				return R.error();
			}
			return R.ok();
			}*/
}
