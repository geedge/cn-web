package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnIcpBuiltIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:07
 */
@Mapper
public interface FqdnIcpBuiltInDao extends BaseMapper<FqdnIcpBuiltIn> {

    List<FqdnIcpBuiltIn> queryPageByKnowledgeId(IPage<FqdnIcpBuiltIn> page, @Param("params") Map<String, Object> params);

    Map<String, Object> queryEntityDetail(@Param("resource") String resource, @Param("isFuzzyQuery")Boolean isFuzzyQuery);
}
