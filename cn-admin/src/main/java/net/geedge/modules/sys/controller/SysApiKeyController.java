package net.geedge.modules.sys.controller;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.sys.entity.SysApiKey;
import net.geedge.modules.sys.service.SysApiKeyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/sys/apiKey")
public class SysApiKeyController {
	
	@Autowired
	private SysApiKeyService sysApiKeyService;
	
	@GetMapping
	@SysLog(operation = OperationEnum.QUERY,type = TypeEnum.SYSTEMAPIKEY)
	public R queryCredentialList(@RequestParam Map<String,Object> params) {
		PageUtils page = sysApiKeyService.queryPage(params);
		return R.ok(page);
	}
	
	@GetMapping("/{id}")
	@SysLog(operation = OperationEnum.QUERY,type = TypeEnum.SYSTEMAPIKEY)
	public R info(@PathVariable Integer id) {
		SysApiKey apiKey = sysApiKeyService.getById(id);
		return R.ok(apiKey);
	}
	
	@PostMapping
	@SysLog(operation = OperationEnum.ADD,type = TypeEnum.SYSTEMAPIKEY)
	/*@RequiresPermissions({"sys:apiKey:save"})*/
	public R addSysApiKey(@RequestBody SysApiKey sysApiKey) {
		ValidateUtils.is(sysApiKey.getName()).notNull(RCode.SYSAPIKEY_NAME_ISNULL)
		.and(sysApiKey.getRoleId()).notNull(RCode.SYSAPIKEY_ROLEID_ISNULL);
		Integer id = sysApiKeyService.addSysApiKey(sysApiKey);
		return R.ok().putData("id", id);
	}
	
	@DeleteMapping
	@SysLog(operation = OperationEnum.DELETE,type = TypeEnum.SYSTEMAPIKEY)
	/*@RequiresPermissions({"sys:apiKey:delete"})*/
	public R removeSysApiKey(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.SYSAPIKEY_ID_ISNULL);
		sysApiKeyService.removeSysApiKeys(ids);
		return R.ok();
	}
	
}
