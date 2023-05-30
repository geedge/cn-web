package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:29
 */
public interface IpAsnBuiltInService extends IService<IpAsnBuiltIn> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
