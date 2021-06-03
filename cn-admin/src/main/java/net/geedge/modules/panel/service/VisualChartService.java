package net.geedge.modules.panel.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.panel.entity.VisualChart;


public interface VisualChartService extends IService<VisualChart> {
	
	PageUtils queryPage(Map<String,Object> params);
	
	VisualChart queryInfo(Integer id);
	
	Integer saveOrUpdateChart(VisualChart chart);
	
	void removeChart(String ids);
	
	void layout(List<VisualChart> charts);
}
