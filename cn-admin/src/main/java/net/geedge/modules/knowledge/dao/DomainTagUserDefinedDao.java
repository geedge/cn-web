package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.DomainTagUserDefined;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:49
 */
@Mapper
public interface DomainTagUserDefinedDao extends BaseMapper<DomainTagUserDefined> {

    List<DomainTagUserDefined> queryPageByKnowledgeId(IPage<DomainTagUserDefined> page, @Param("params") Map<String, Object> params);
}
