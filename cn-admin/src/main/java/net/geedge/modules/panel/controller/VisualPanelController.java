package net.geedge.modules.panel.controller;

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

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualPanel;
import net.geedge.modules.panel.service.VisualPanelService;

@RestController
@RequestMapping("/visual/panel")
public class VisualPanelController {
	@Autowired
	private VisualPanelService visualPanelService;
	
	@RequestMapping("/{id}")
	public R queryInfo(@PathVariable Integer id) {
		VisualPanel panel = visualPanelService.queryInfo(id);
		return R.ok(panel);
	}
	
	@RequestMapping
	public R queryList(@RequestParam Map<String,Object> params) {
		ValidateUtils.is(params.get("type")).notNull(RCode.PANEL_TYPE_ISNULL);
		PageUtils result = visualPanelService.queryPage(params);
		return R.ok(result);
	}
	
	@PostMapping
	public R savePanel(@RequestBody VisualPanel panel) {
		Integer id = visualPanelService.saveOrUpdatePanel(panel);
		return R.ok().putData("id", id);
	}
	
	@PutMapping
	public R modifyPanel(@RequestBody VisualPanel panel) {
		ValidateUtils.is(panel.getId()).notNull(RCode.PANEL_ID_ISNULL);
		Integer id = visualPanelService.saveOrUpdatePanel(panel);
		return R.ok().putData("id", id);
	}
	
	@DeleteMapping
	public R removePane(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.PANEL_ID_ISNULL);
		visualPanelService.removePanel(ids);
		return R.ok();
	}
}
