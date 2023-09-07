package net.geedge.modules.rule.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.rule.dao.RuleCommitHistoryDao;
import net.geedge.modules.rule.entity.RuleCommitHistory;
import net.geedge.modules.rule.service.RuleCommitHistoryService;

@Service("ruleCommitHistoryService")
public class RuleCommitHistoryServiceImpl extends ServiceImpl<RuleCommitHistoryDao, RuleCommitHistory> implements RuleCommitHistoryService {

}
