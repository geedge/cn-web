package net.geedge.modules.rule.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.modules.rule.entity.DetectionRule;

public interface DetectionRuleService extends IService<DetectionRule>{
	
	DetectionRule getInfoById(Integer id);
	
	List<DetectionRule> getList(Map<String,Object> params);
	
	List<Integer> removeRules(List<Integer> ids);
}
