package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:28
 */
@Mapper
public interface IpAsnBuiltInDao extends BaseMapper<IpAsnBuiltIn> {

    List<IpAsnBuiltIn> queryPageByKnowledgeId(IPage<IpAsnBuiltIn> page, @Param("params") Map<String, Object> params);

    Map<String, Object> queryEntityDetail(@Param("resource") String resource, @Param("isIpv6") boolean isIpv6);
    
    void insertBatchForBinary(@Param("ipAsnBuiltIns")List<IpAsnBuiltIn> ipAsnBuiltIns);
    
    void updateBatchForBinary(@Param("ipAsnBuiltIns")List<IpAsnBuiltIn> ipAsnBuiltIns);
}
