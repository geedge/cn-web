package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;

import java.util.Map;

public interface LinkDirectionBuiltInService extends IService<LinkDirectionBuiltIn>{

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

}
