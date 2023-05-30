package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnCategoryBuiltIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:17
 */
@Mapper
public interface FqdnCategoryBuiltInDao extends BaseMapper<FqdnCategoryBuiltIn> {

    List<FqdnCategoryBuiltIn> queryPageByKnowledgeId(IPage<FqdnCategoryBuiltIn> page, @Param("params") Map<String, Object> params);
}
