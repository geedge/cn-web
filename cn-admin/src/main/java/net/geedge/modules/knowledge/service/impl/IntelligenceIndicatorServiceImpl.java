package net.geedge.modules.knowledge.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.*;
import net.geedge.modules.knowledge.dao.IntelligenceIndicatorDao;
import net.geedge.modules.knowledge.entity.IntelligenceIndicator;
import net.geedge.modules.knowledge.service.IntelligenceIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Auther: lijinyang
 * @Date: 2024/04/03/10:41
 */
@Service("IntelligenceIndicatorService")
public class IntelligenceIndicatorServiceImpl extends ServiceImpl<IntelligenceIndicatorDao, IntelligenceIndicator> implements IntelligenceIndicatorService {

    private Log log = Log.get();

    @Autowired
    private KnowledgeBaseServiceImpl knowledgeBaseServiceImpl;

    @Override
    public void saveBatchForBinary(List<IntelligenceIndicator> intelligenceIndicators) {
        if(ObjectUtil.isNotEmpty(intelligenceIndicators)) {
            this.baseMapper.insertBatchForBinary(intelligenceIndicators);
        }
    }

    @Override
    public void modifyBatchForBinary(List<IntelligenceIndicator> intelligenceIndicators) {
        if(ObjectUtil.isNotEmpty(intelligenceIndicators)) {
            this.baseMapper.updateBatchForBinary(intelligenceIndicators);
        }
    }
    @Override
    public R indicatorStatistics(String name) {
        Map<String,Object> data = new HashMap<>();
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> ipStatistics = new HashMap<>();
        Map<String,Object> domainStatistics = new HashMap<>();
        Integer ipCount = this.baseMapper.selectCount(new QueryWrapper<IntelligenceIndicator>().eq("tags",name).eq("type","IP"));
        ipStatistics.put("type","IP");
        ipStatistics.put("count",ipCount);
        result.add(ipStatistics);
        Integer domainCount = this.baseMapper.selectCount(new QueryWrapper<IntelligenceIndicator>().eq("tags",name).eq("type","Domain"));
        domainStatistics.put("type","Domain");
        domainStatistics.put("count",domainCount);
        result.add(domainStatistics);
        data.put("result",result);
        return R.ok(data);
    }

    @Override
    public R indicatorTrend(Map<String,Object> params) {
        if(ObjectUtil.isEmpty(params.get("startTime")) || ObjectUtil.isEmpty(params.get("endTime"))){
            long endTime = System.currentTimeMillis() / 1000;
            long startTime = endTime - 24 * 3600;
            params.put("startTime",Long.toString(startTime));
            params.put("endTime",Long.toString(endTime));
        }
        Long startTime = Long.valueOf((String)params.get("startTime"));
        Long endTime = Long.valueOf((String)params.get("endTime"));
        // 计算粒度
        Integer step = knowledgeBaseServiceImpl.handlerStep(startTime, endTime);
        params.put("startTime", cn.hutool.core.date.DateUtil.date(startTime*1000));
        params.put("endTime", cn.hutool.core.date.DateUtil.date(endTime*1000));
        params.put("step", step);
        List<Map<String, Object>> list = this.baseMapper.indicatorTrend(params);
        List<Map<String, Object>> result = sumHandler(list, params);
        Map<String,Object> data = Tool.MapUtil.newHashMap();
        data.put("resultType","table");
        data.put("result",result);
        return R.ok(data);
    }

    private List<Map<String, Object>> sumHandler(List<Map<String, Object>> data, Map<String,Object> params) {
        List<Map<String, Object>> sort = data.stream().sorted((m1,m2) -> ((Timestamp) m1.get("statTime")).compareTo((Timestamp) m2.get("statTime"))).collect(Collectors.toList());
        List<Map<String, Object>> result = Tool.CollectionUtil.newArrayList();
        Integer ipSum = this.baseMapper.selectCount(new QueryWrapper<IntelligenceIndicator>().lambda().eq(IntelligenceIndicator::getType, "IP").eq(IntelligenceIndicator::getTags, params.get("name")).lt(IntelligenceIndicator::getCreatedTime, params.get("startTime")));
        Integer domainSum = this.baseMapper.selectCount(new QueryWrapper<IntelligenceIndicator>().lambda().eq(IntelligenceIndicator::getType, "Domain").eq(IntelligenceIndicator::getTags, params.get("name")).lt(IntelligenceIndicator::getCreatedTime, params.get("startTime")));
        for (Map<String, Object> map:sort) {
            Map<String, Object> resultMap = Tool.MapUtil.newHashMap();
            Date statTime = (Date)map.get("statTime");
            Date startTime = (Date)params.get("startTime");
            long diff = startTime.getTime()/1000-statTime.getTime()/1000;
            if(diff >= (Integer)params.get("step")*3600) {
                continue;
            }
            resultMap.put("statTime",statTime.getTime()/1000);
            resultMap.put("ipCount",ipSum+Integer.valueOf(map.get("ipCount").toString()));
            resultMap.put("domainCount",domainSum+Integer.valueOf(map.get("domainCount").toString()));
            ipSum+=Integer.valueOf(map.get("ipCount").toString());
            domainSum+=Integer.valueOf(map.get("domainCount").toString());
            result.add(resultMap);
        }
        return result;
    }

    @Override
    public void saveBatchJDBC(Connection conn, List<IntelligenceIndicator> intelligenceIndicators) {
        if(ObjectUtil.isNotEmpty(intelligenceIndicators)) {
            try {
                PreparedStatement pstm = conn.prepareStatement("insert into cn_intelligence_indicator (type, source_id, source_name, " +
                        "ip_addr_format, ip1, ip2, start_ip_binary, end_ip_binary, domain, method, confidence, tags, description, first_seen_time, last_seen_time, " +
                        "created_time, updated_time) "
                        + "values (?,?,?,?,?,?,INET6_ATON(?),INET6_ATON(?),?,?,?,?,?,?,?,?,?)");
                for(IntelligenceIndicator intelligenceIndicator : intelligenceIndicators) {
                    pstm.setString(1, intelligenceIndicator.getType());
                    pstm.setString(2, intelligenceIndicator.getSourceId());
                    pstm.setString(3, intelligenceIndicator.getSourceName());
                    pstm.setString(4, intelligenceIndicator.getIpAddrFormat());
                    pstm.setString(5, intelligenceIndicator.getIp1());
                    pstm.setString(6, intelligenceIndicator.getIp2());
                    pstm.setString(7, intelligenceIndicator.getStartIpBinary());
                    pstm.setString(8, intelligenceIndicator.getEndIpBinary());
                    pstm.setString(9, intelligenceIndicator.getDomain());
                    pstm.setString(10, intelligenceIndicator.getMethod());
                    pstm.setString(11, intelligenceIndicator.getConfidence());
                    pstm.setString(12, intelligenceIndicator.getTags());
                    pstm.setString(13, intelligenceIndicator.getDescription());
                    pstm.setTimestamp(14, new java.sql.Timestamp(intelligenceIndicator.getFirstSeenTime().getTime()));
                    pstm.setTimestamp(15, new java.sql.Timestamp(intelligenceIndicator.getLastSeenTime().getTime()));
                    pstm.setTimestamp(16, new java.sql.Timestamp(intelligenceIndicator.getCreatedTime().getTime()));
                    pstm.setTimestamp(17, new java.sql.Timestamp(intelligenceIndicator.getUpdatedTime().getTime()));
                    pstm.addBatch();
                }
                pstm.executeBatch();
                pstm.clearBatch();
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new CNException(RCode.IMPORT_ERROR);
            }
        }
    }

    @Override
    public void modifyBatchJDBC(Connection conn, List<IntelligenceIndicator> intelligenceIndicators) {
        if(ObjectUtil.isNotEmpty(intelligenceIndicators)) {
            try {
                PreparedStatement pstm = conn.prepareStatement("update cn_intelligence_indicator set type=?, source_id=?, source_name=?, " +
                        "ip_addr_format=?, ip1=?, ip2=?, start_ip_binary=INET6_ATON(?), end_ip_binary=INET6_ATON(?), domain=?, method=?, confidence=?, tags=?, description=?, " +
                        "last_seen_time=?, updated_time=? where id =?");
                for(IntelligenceIndicator intelligenceIndicator : intelligenceIndicators) {
                    pstm.setString(1, intelligenceIndicator.getType());
                    pstm.setString(2, intelligenceIndicator.getSourceId());
                    pstm.setString(3, intelligenceIndicator.getSourceName());
                    pstm.setString(4, intelligenceIndicator.getIpAddrFormat());
                    pstm.setString(5, intelligenceIndicator.getIp1());
                    pstm.setString(6, intelligenceIndicator.getIp2());
                    pstm.setString(7, intelligenceIndicator.getStartIpBinary());
                    pstm.setString(8, intelligenceIndicator.getEndIpBinary());
                    pstm.setString(9, intelligenceIndicator.getDomain());
                    pstm.setString(10, intelligenceIndicator.getMethod());
                    pstm.setString(11, intelligenceIndicator.getConfidence());
                    pstm.setString(12, intelligenceIndicator.getTags());
                    pstm.setString(13, intelligenceIndicator.getDescription());
                    pstm.setTimestamp(14, new java.sql.Timestamp(intelligenceIndicator.getLastSeenTime().getTime()));
                    pstm.setTimestamp(15, new java.sql.Timestamp(intelligenceIndicator.getUpdatedTime().getTime()));
                    pstm.setLong(16, intelligenceIndicator.getId());
                    pstm.addBatch();
                }
                pstm.executeBatch();
                pstm.clearBatch();
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new CNException(RCode.IMPORT_ERROR);
            }
        }
    }

    @Override
    public List<IntelligenceIndicator> queryByTag(IPage page, Map<String,Object> params) {
        return this.baseMapper.queryByTag(page, params);
    }

    @Override
    public List<String> queryDomainEntityTags(String resource) {
        return this.baseMapper.queryDomainEntityTags(resource);
    }

    @Override
    public List<String> queryIpEntityTags(String resource) {
        return this.baseMapper.queryIpEntityTags(resource);
    }

    @Override
    public List<String> querySubscriberEntityTags(String resource) {
        return this.baseMapper.querySubscriberEntityTags(resource);
    }

    @Override
    public PageUtils queryList(Map<String, Object> params) {
        IPage page = this.page(new Query(IntelligenceIndicator.class).getPage(params));
        List<IntelligenceIndicator> intelligenceIndicators = this.baseMapper.queryList(page, params);
        page.setRecords(intelligenceIndicators);
        return new PageUtils(page);
    }
}
