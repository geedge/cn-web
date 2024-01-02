package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.geedge.modules.knowledge.entity.VpnLearningDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:35
 */
@Mapper
public interface VpnLearningDomainDao extends BaseMapper<VpnLearningDomain> {

    Map<String, Object> queryDomainEntityTag(@Param("resource") String resource);
}
