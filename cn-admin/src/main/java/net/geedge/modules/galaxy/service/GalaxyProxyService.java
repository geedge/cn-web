package net.geedge.modules.galaxy.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.galaxy.entity.GalaxyProxy;

public interface GalaxyProxyService extends IService<GalaxyProxy>{
	
	PageUtils queryPage(Map<String,Object> params);
	
	GalaxyProxy queryInfo(Integer id);
	
	Integer saveOrUpdateGalaxyProxy(GalaxyProxy galaxyProxy);
	
	void removeGalaxyProxy(String ids);
}
