package net.geedge.modules.rule.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.rule.entity.DetectionRule;

public interface DetectionRuleService extends IService<DetectionRule>{
	
	DetectionRule getInfoById(Integer id);
	
	PageUtils getList(Map<String,Object> params);
	
	List<Integer> removeRules(List<Integer> ids);
	
	DetectionRule saveDetectionRule(DetectionRule detectionRule);
	
	DetectionRule modifyDetectionRule(DetectionRule detectionRule);
	
	Map<String,Object> queryDetection(Integer queryDetection);
	
	Map<String,Object> statistics();
	
	void handlerRuleData(List<DetectionRule> detectionRules);
}
