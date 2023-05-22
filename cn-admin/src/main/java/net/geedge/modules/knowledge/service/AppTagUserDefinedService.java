package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:46
 */
public interface AppTagUserDefinedService extends IService<AppTagUserDefined> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
