package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.knowledge.entity.IntelligenceIndicator;
import net.geedge.modules.knowledge.entity.IocMalware;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/03/10:38
 */
public interface IntelligenceIndicatorService extends IService<IntelligenceIndicator> {

    R indicatorStatistics(String name);

    R indicatorTrend(Map<String,Object> params);

    void modifyBatchForBinary(List<IntelligenceIndicator> intelligenceIndicators);

    void saveBatchForBinary(List<IntelligenceIndicator> intelligenceIndicators);

    void saveBatchJDBC(Connection conn, List<IntelligenceIndicator> intelligenceIndicators);

    void modifyBatchJDBC(Connection conn,List<IntelligenceIndicator> intelligenceIndicators);

    List<IntelligenceIndicator> queryByTag(IPage page, Map<String,Object> params);

    List<String> queryDomainEntityTags(String resource);

    List<String> queryIpEntityTags(String resource);

    List<String> querySubscriberEntityTags(String resource);

    PageUtils queryList(Map<String,Object> params);
}
