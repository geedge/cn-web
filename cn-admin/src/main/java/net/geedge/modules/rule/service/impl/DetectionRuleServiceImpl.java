package net.geedge.modules.rule.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.rule.dao.DetectionRuleDao;
import net.geedge.modules.rule.entity.DetectionRule;
import net.geedge.modules.rule.service.DetectionRuleService;

@Service("detectionRuleService")
public class DetectionRuleServiceImpl extends ServiceImpl<DetectionRuleDao, DetectionRule> implements DetectionRuleService {

}
