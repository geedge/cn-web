package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.IocDarkweb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:56
 */
@Mapper
public interface IocDarkwebDao extends BaseMapper<IocDarkweb> {

    List<IocDarkweb> queryPageByKnowledgeId(IPage<IocDarkweb> page, @Param("params") Map<String, Object> params);

    Map<String, Object> queryIpEntityTag(@Param("resource") String resource);

    Map<String, Object> queryDomainEntityTag(@Param("resource") String resource);
}
