package net.geedge.modules.rule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.rule.dao.RuleCommitHistoryDao;
import net.geedge.modules.rule.entity.RuleCommitHistory;
import net.geedge.modules.rule.service.RuleCommitHistoryService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/08/02/15:22
 */
@Service("RuleCommitHistoryService")
public class RuleCommitHistoryServiceImpl extends ServiceImpl<RuleCommitHistoryDao, RuleCommitHistory> implements RuleCommitHistoryService {
}
