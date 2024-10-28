package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.knowledge.entity.IntelligenceIndicator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/02/17:11
 */
@Mapper
public interface IntelligenceIndicatorDao extends BaseMapper<IntelligenceIndicator> {

    List<Map<String, Object>> indicatorTrend(@Param("params") Map<String, Object> params);

    void insertBatchForBinary(@Param("intelligenceIndicators")List<IntelligenceIndicator> intelligenceIndicators);

    void updateBatchForBinary(@Param("intelligenceIndicators")List<IntelligenceIndicator> intelligenceIndicators);

    List<IntelligenceIndicator> queryByTag(IPage page, @Param("params") Map<String,Object> params);

    List<String> queryDomainEntityTags(@Param("resource") String resource);

    List<String> queryIpEntityTags(@Param("resource") String resource);

    List<String> querySubscriberEntityTags(@Param("resource") String resource);

    List<IntelligenceIndicator> queryList(IPage<IntelligenceIndicator> page, @Param("params") Map<String, Object> params);
}
