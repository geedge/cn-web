package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.geedge.modules.knowledge.entity.VpnLearningIp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/9:38
 */
@Mapper
public interface VpnLearningIpDao extends BaseMapper<VpnLearningIp> {

    List<Map> queryIpEntityTag(@Param("resource") String resource);
}
