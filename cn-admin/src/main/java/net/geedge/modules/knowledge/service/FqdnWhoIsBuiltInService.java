package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.FqdnWhoIsBuiltIn;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:59
 */
public interface FqdnWhoIsBuiltInService extends IService<FqdnWhoIsBuiltIn> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
