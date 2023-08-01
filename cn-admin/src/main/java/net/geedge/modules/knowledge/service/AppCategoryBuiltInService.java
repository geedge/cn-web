package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;

import java.util.Map;


/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:43
 */
public interface AppCategoryBuiltInService extends IService<AppCategoryBuiltIn> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

    Map<String, Object> queryEntityDetail(String resource);
}
