package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnWhoIsBuiltIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:58
 */
@Mapper
public interface FqdnWhoIsBuiltInDao extends BaseMapper<FqdnWhoIsBuiltIn> {

    List<FqdnWhoIsBuiltIn> queryPageByKnowledgeId(IPage<FqdnWhoIsBuiltIn> page, @Param("params") Map<String, Object> params);
}
