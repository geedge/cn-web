package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.VpnLearningIpDao;
import net.geedge.modules.knowledge.entity.VpnLearningIp;
import net.geedge.modules.knowledge.service.VpnLearningIpService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/9:46
 */
@Service("VpnLearningIpService")
public class VpnLearningIpServiceImpl extends ServiceImpl<VpnLearningIpDao, VpnLearningIp> implements VpnLearningIpService {

    @Override
    public List<Map> queryIpEntityTag(String resource) {
        return this.baseMapper.queryIpEntityTag(resource);
    }
}
