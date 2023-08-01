package net.geedge.modules.knowledge.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:15
 */
@Mapper
public interface Psiphon3IpDao extends BaseMapper<Psiphon3Ip> {

    List<Psiphon3Ip> queryPageByKnowledgeId(IPage<Psiphon3Ip> page, @Param("params") Map<String, Object> params);
    
    void insertBatchForBinary(@Param("psiphon3Ips")List<Psiphon3Ip> psiphon3Ips);
    
    void updateBatchForBinary(@Param("psiphon3Ips")List<Psiphon3Ip> psiphon3Ips);

    Map<String, Object> queryIpEntityTag(@Param("resource") String resource);
}
