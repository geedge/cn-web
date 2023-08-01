package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:50
 */
@Mapper
public interface IpTagUserDefinedDao extends BaseMapper<IpTagUserDefined> {

    List<IpTagUserDefined> queryPageByKnowledgeId(IPage<IpTagUserDefined> page, @Param("params") Map<String, Object> params);
    
    void insertBatchForBinary(@Param("ipTagUserDefineds")List<IpTagUserDefined> ipTagUserDefineds);
    
    void updateBatchForBinary(@Param("ipTagUserDefineds")List<IpTagUserDefined> ipTagUserDefineds);

    List<Map> queryIpEntityTag(@Param("resource") String resource);
}
