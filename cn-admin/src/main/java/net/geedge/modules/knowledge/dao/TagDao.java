package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/02/17:10
 */
@Mapper
public interface TagDao extends BaseMapper<Tag> {

    List<KnowledgeBase> queryPage(IPage<KnowledgeBase> page, @Param("params") Map<String, Object> params);

}
