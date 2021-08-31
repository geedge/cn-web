package net.geedge.modules.galaxy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;

@RestController
@RequestMapping("/galaxy/setting")
public class GalaxyProxyController {
	@Autowired
	private GalaxyProxyService galaxyProxyService;
	
	@RequestMapping("/{id}")
	public R queryInfo(@PathVariable Integer id) {
		ValidateUtils.is(id).notNull(RCode.GALAXY_PROXY_ID_ISNULL);
		GalaxyProxy galaxyProxy = galaxyProxyService.queryInfo(id);
		return R.ok(galaxyProxy);
	}
	
	@RequestMapping
	public R queryList(@RequestParam Map<String,Object> params) {
		PageUtils result = galaxyProxyService.queryPage(params);
		return R.ok(result);
	}
	
	@PostMapping
	@SysLog(operation = OperationEnum.ADD,type = TypeEnum.SETTING)
	public R save(@RequestBody GalaxyProxy galaxyProxy) {
		Integer id = galaxyProxyService.saveOrUpdateGalaxyProxy(galaxyProxy);
		return R.ok().putData("id", id);
	}
	
	@PutMapping
	@SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.SETTING)
	public R modify(@RequestBody GalaxyProxy galaxyProxy) {
		ValidateUtils.is(galaxyProxy.getId()).notNull(RCode.GALAXY_PROXY_ID_ISNULL);
		Integer id = galaxyProxyService.saveOrUpdateGalaxyProxy(galaxyProxy);
		return R.ok().putData("id", id);
	}
	
	@DeleteMapping
	@SysLog(operation = OperationEnum.DELETE,type = TypeEnum.SETTING)
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.GALAXY_PROXY_ID_ISNULL);
		galaxyProxyService.removeGalaxyProxy(ids);
		return R.ok();
	}
	
	@PutMapping(path = "clearCache")
	public R clearCache() {
		Tool.FreeMarkerUtil.clearTemlateCache();
		return R.ok();
	}
	
}
