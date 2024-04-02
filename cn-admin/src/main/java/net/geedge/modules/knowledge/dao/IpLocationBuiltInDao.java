package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.IpLocationBuiltIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:49
 */
@Mapper
public interface IpLocationBuiltInDao extends BaseMapper<IpLocationBuiltIn> {

    List<IpLocationBuiltIn> queryPage(IPage<IpLocationBuiltIn> page, @Param("params") Map<String, Object> params);

    Map<String, Object> queryEntityDetail(@Param("resource") String resource);
}
