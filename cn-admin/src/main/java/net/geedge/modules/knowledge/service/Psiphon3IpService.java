package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:16
 */
public interface Psiphon3IpService extends IService<Psiphon3Ip> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
