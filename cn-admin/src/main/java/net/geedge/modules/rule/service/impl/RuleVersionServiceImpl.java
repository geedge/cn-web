package net.geedge.modules.rule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.rule.dao.RuleVersionDao;
import net.geedge.modules.rule.entity.RuleVersion;
import net.geedge.modules.rule.service.RuleVersionService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/08/02/15:24
 */
@Service("RuleVersionService")
public class RuleVersionServiceImpl extends ServiceImpl<RuleVersionDao, RuleVersion> implements RuleVersionService {
}
