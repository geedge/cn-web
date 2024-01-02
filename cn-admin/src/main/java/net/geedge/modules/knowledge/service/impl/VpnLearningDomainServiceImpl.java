package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.VpnLearningDomainDao;
import net.geedge.modules.knowledge.entity.VpnLearningDomain;
import net.geedge.modules.knowledge.service.VpnLearningDomainService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:37
 */
@Service("VpnLearningDomainService")
public class VpnLearningDomainServiceImpl extends ServiceImpl<VpnLearningDomainDao, VpnLearningDomain> implements VpnLearningDomainService {

    @Override
    public Map<String, Object> queryDomainEntityTag(String resource) {
        return this.baseMapper.queryDomainEntityTag(resource);
    }
}
