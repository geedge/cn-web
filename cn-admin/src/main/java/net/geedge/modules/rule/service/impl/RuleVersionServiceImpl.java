package net.geedge.modules.rule.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.rule.dao.RuleVersionDao;
import net.geedge.modules.rule.entity.RuleVersion;
import net.geedge.modules.rule.service.RuleVersionService;

@Service("ruleVersionService")
public class RuleVersionServiceImpl extends ServiceImpl<RuleVersionDao, RuleVersion> implements RuleVersionService {

}
