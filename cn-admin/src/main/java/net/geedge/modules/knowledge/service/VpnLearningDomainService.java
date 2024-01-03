package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.knowledge.entity.VpnLearningDomain;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:36
 */
public interface VpnLearningDomainService extends IService<VpnLearningDomain> {

    List<Map> queryDomainEntityTag(String resource);
}
