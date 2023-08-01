package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IpLocationBuiltIn;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:48
 */
public interface IpLocationBuiltInService extends IService<IpLocationBuiltIn> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

    Map<String, Object> queryEntityDetail(String resource);
}
