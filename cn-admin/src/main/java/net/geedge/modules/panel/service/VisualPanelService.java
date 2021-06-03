package net.geedge.modules.panel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.panel.entity.VisualPanel;


public interface VisualPanelService extends IService<VisualPanel> {
	
	PageUtils queryPage(Map<String,Object> params);
	
	VisualPanel queryInfo(Integer id);
	
	Integer saveOrUpdatePanel(VisualPanel panel);
	
	void removePanel(String ids);
}
