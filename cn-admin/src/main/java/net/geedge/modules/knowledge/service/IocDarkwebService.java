package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IocDarkweb;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:01
 */
public interface IocDarkwebService extends IService<IocDarkweb> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
