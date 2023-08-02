package net.geedge.modules.rule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.rule.dao.DetectionRuleDao;
import net.geedge.modules.rule.entity.DetectionRule;
import net.geedge.modules.rule.service.DetectionRuleService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/08/02/15:19
 */
@Service("DetectionRuleService")
public class DetectionRuleServiceImpl extends ServiceImpl<DetectionRuleDao, DetectionRule> implements DetectionRuleService {
}
