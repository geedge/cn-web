package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.FqdnCategoryBuiltIn;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:18
 */
public interface FqdnCategoryBuiltInService extends IService<FqdnCategoryBuiltIn> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

    Map<String, Object> queryEntityDetail(String resource, Boolean isFuzzyQuery);
}
