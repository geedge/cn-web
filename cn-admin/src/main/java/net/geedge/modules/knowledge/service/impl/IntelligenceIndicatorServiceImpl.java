package net.geedge.modules.knowledge.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.knowledge.dao.IntelligenceIndicatorDao;
import net.geedge.modules.knowledge.entity.IntelligenceIndicator;
import net.geedge.modules.knowledge.entity.Tag;
import net.geedge.modules.knowledge.service.IntelligenceIndicatorService;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        params.put("startTime",cn.hutool.core.date.DateUtil.date(startTime*1000));
        params.put("endTime",cn.hutool.core.date.DateUtil.date(endTime*1000));
        params.put("step", step);
        List<Map<String, Object>> list = this.baseMapper.indicatorTrend(params);
        List<Map<String, Object>> result = sumHandler(list, params);
        Map<String,Object> data = Tool.MapUtil.newHashMap();
        data.put("resultType","table");
        data.put("result",result);
        return R.ok(data);
    }

    private List<Map<String, Object>> sumHandler(List<Map<String, Object>> data, Map<String,Object> params) {
        String name = (String)params.get("name");
        DateTime startTime = (DateTime)params.get("startTime");
        DateTime endTime = (DateTime)params.get("endTime");
        Integer ipSum = this.baseMapper.selectCount(new QueryWrapper<IntelligenceIndicator>().eq("tags",name).eq("type","IP").between("created_time",startTime,endTime));
        Integer domainSum = this.baseMapper.selectCount(new QueryWrapper<IntelligenceIndicator>().eq("tags",name).eq("type","Domain").between("created_time",startTime,endTime));
        Map<String, Object> last = Tool.MapUtil.newHashMap();
        last.put("statTime",endTime.getTime()/1000);
        last.put("ipIndicatorCount",ipSum);
        last.put("domainIndicatorCount",domainSum);
        List<Map<String, Object>> reversed = data.stream().sorted((m1,m2) -> ((Long) m2.get("statTime")).compareTo((Long) m1.get("statTime"))).collect(Collectors.toList());
        List<Map<String, Object>> result = Tool.CollectionUtil.newArrayList();
        result.add(last);
        for (Map<String, Object> map:reversed) {
            Map<String, Object> resultMap = Tool.MapUtil.newHashMap();
            resultMap.put("statTime",map.get("statTime"));
            resultMap.put("ipIndicatorCount",ipSum-Integer.valueOf(map.get("ipIndicatorCount").toString()));
            resultMap.put("domainIndicatorCount",domainSum-Integer.valueOf(map.get("domainIndicatorCount").toString()));
            ipSum-=Integer.valueOf(map.get("ipIndicatorCount").toString());
            domainSum-=Integer.valueOf(map.get("domainIndicatorCount").toString());
            result.add(resultMap);
        }
        result = result.stream().sorted(Comparator.comparing(m -> ((Long) m.get("statTime")))).collect(Collectors.toList());
        return result;
    }

    @Override
    public void saveBatchJDBC(Connection conn, List<IntelligenceIndicator> intelligenceIndicators) {
        if(ObjectUtil.isNotEmpty(intelligenceIndicators)) {
            try {
                PreparedStatement pstm = conn.prepareStatement("insert into cn_intelligence_indicator (type, source_id, source_name, " +
                        "ip_addr_format, ip1, ip2, start_ip_binary, end_ip_binary, domain, method, confidence, tags, description, first_seen_time, last_seen_time, " +
                        "created_time, updated_time) "
                        + "values (?,?,?,?,?,?,INET6_ATON(?),INET6_ATON(?),?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                        "type=VALUES(type),source_id=VALUES(source_id),ip1=VALUES(ip1),ip2=VALUES(ip2),domain=VALUES(domain),tags=VALUES(tags)");
                for(IntelligenceIndicator intelligenceIndicator : intelligenceIndicators) {
                    pstm.setString(1, intelligenceIndicator.getType());
                    pstm.setString(2, intelligenceIndicator.getSourceId());
                    pstm.setString(3, intelligenceIndicator.getSourceName());
                    pstm.setString(4, intelligenceIndicator.getIpAddrFormat());
                    if(intelligenceIndicator.getIp1()!=null) {
                        pstm.setString(5, intelligenceIndicator.getIp1());
                    } else {
                        pstm.setString(5, StrUtil.EMPTY);
                    }
                    if(intelligenceIndicator.getIp2()!=null) {
                        pstm.setString(6, intelligenceIndicator.getIp2());
                    } else {
                        pstm.setString(6, StrUtil.EMPTY);
                    }
                    pstm.setString(7, intelligenceIndicator.getStartIpBinary());
                    pstm.setString(8, intelligenceIndicator.getEndIpBinary());
                    if(intelligenceIndicator.getDomain()!=null) {
                        pstm.setString(9, intelligenceIndicator.getDomain());
                    } else {
                        pstm.setString(9, StrUtil.EMPTY);
                    }
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
                    if(intelligenceIndicator.getIp1()!=null) {
                        pstm.setString(5, intelligenceIndicator.getIp1());
                    } else {
                        pstm.setString(5, StrUtil.EMPTY);
                    }
                    if(intelligenceIndicator.getIp2()!=null) {
                        pstm.setString(6, intelligenceIndicator.getIp2());
                    } else {
                        pstm.setString(6, StrUtil.EMPTY);
                    }
                    pstm.setString(7, intelligenceIndicator.getStartIpBinary());
                    pstm.setString(8, intelligenceIndicator.getEndIpBinary());
                    if(intelligenceIndicator.getDomain()!=null) {
                        pstm.setString(9, intelligenceIndicator.getDomain());
                    } else {
                        pstm.setString(9, StrUtil.EMPTY);
                    }
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
    public List<IntelligenceIndicator> queryPage(IPage page, Map<String,Object> params) {
        return this.baseMapper.queryPage(page, params);
    }

    @Override
    public List<String> queryDomainEntityTags(String resource) {
        return this.baseMapper.queryDomainEntityTags(resource);
    }

    @Override
    public List<String> queryIpEntityTags(String resource) {
        return this.baseMapper.queryIpEntityTags(resource);
    }
}
