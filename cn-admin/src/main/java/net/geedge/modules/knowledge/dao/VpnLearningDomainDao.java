package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import net.geedge.modules.knowledge.entity.VpnLearningDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:35
 */
@Mapper
public interface VpnLearningDomainDao extends BaseMapper<VpnLearningDomain> {

    List<Map> queryDomainEntityTag(@Param("resource") String resource);

    List<VpnLearningDomain> queryPageByKnowledgeId(IPage<VpnLearningDomain> page, @Param("params") Map<String, Object> params);
}
