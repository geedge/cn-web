package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.knowledge.entity.VpnLearningIp;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/9:43
 */
public interface VpnLearningIpService extends IService<VpnLearningIp> {

    Map<String, Object> queryIpEntityTag(String resource);
}
