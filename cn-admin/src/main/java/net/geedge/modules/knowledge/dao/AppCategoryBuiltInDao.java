package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:42
 */
@Mapper
public interface AppCategoryBuiltInDao extends BaseMapper<AppCategoryBuiltIn> {

    List<AppCategoryBuiltIn> queryPageByKnowledgeId(IPage<AppCategoryBuiltIn> page, @Param("params") Map<String, Object> params);

    Map<String, Object> queryEntityDetail(@Param("resource") String resource);
}
