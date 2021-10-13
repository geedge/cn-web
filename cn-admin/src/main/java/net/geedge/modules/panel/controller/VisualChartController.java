package net.geedge.modules.panel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.hutool.core.util.ObjectUtil;
import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.panel.entity.VisualChart;
import net.geedge.modules.panel.service.VisualChartService;

@RestController
@RequestMapping("/visual/chart")
public class VisualChartController {
	@Autowired
	private VisualChartService visualChartService;

	@RequestMapping("/{id}")
	public R queryInfo(@PathVariable Integer id) {
		VisualChart visualChart = visualChartService.queryInfo(id);
		return R.ok(visualChart);
	}

	@RequestMapping
	@SysLog(operation =OperationEnum.QUERY,type = TypeEnum.VISUALCHART)
	public R queryList(@RequestParam Map<String,Object> params) {
		PageUtils result = visualChartService.queryPage(params);
		return R.ok(result);
	}

	@PostMapping
	@SysLog(operation =OperationEnum.ADD,type = TypeEnum.VISUALCHART)
	public R saveChart(@RequestBody VisualChart chart) {
		Integer id = visualChartService.saveOrUpdateChart(chart);
		return R.ok().putData("id", id);
	}

	@PutMapping
	@SysLog(operation =OperationEnum.UPDATE,type = TypeEnum.VISUALCHART)
	public R modifyChart(@RequestBody VisualChart chart) {
		Integer id = visualChartService.saveOrUpdateChart(chart);
		return R.ok().putData("id", id);
	}

	@DeleteMapping
	@SysLog(operation =OperationEnum.DELETE,type = TypeEnum.VISUALCHART)
	public R removeCharts(@RequestParam String ids) {
		visualChartService.removeChart(ids);
		return R.ok();
	}

	@PutMapping("/layout")
	public R layout(@RequestBody List<VisualChart> charts) {
		ValidateUtils.is(charts).notNull(RCode.NOT_NULL_ERROR);
		for(VisualChart chart : charts) {
			ValidateUtils.is(chart.getX()).notNull(RCode.CHART_X_ISNULL)
			.and(chart.getY()).notNull(RCode.CHART_Y_ISNULL)
			.and(chart.getW()).notNull(RCode.CHART_W_ISNULL)
			.and(chart.getH()).notNull(RCode.CHART_H_ISNULL)
			.and(chart.getId()).notNull(RCode.CHART_ID_ISNULL);
			if(ObjectUtil.isEmpty(chart.getPid())) {
				chart.setPid(0);
			}
		}
		visualChartService.layout(charts);
		return R.ok();
	}

	@GetMapping("list")
	public R queryChartList(@RequestParam Map<String,Object> params) {
		PageUtils chartListByPage = visualChartService.queryPageByQ(params);
		return R.ok(chartListByPage);
	}
}
