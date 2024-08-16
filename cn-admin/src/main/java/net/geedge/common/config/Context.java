package net.geedge.common.config;

import java.util.Map;

import cn.hutool.core.map.MapUtil;
import net.geedge.modules.detection.entity.Event;

public class Context {
	
	// 本地存储Event缓存数据
	public final static Map<String,Event> DETECTTION_EVENT_CACHE = MapUtil.newConcurrentHashMap();
	
}
