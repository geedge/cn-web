package com.net.geedge.modules.galaxy;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

/**
 *     各维度下钻网络性能相关
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NetworkApplicationPerfirmanceDimensionDrilldownTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkApplicationPerfirmanceDimensionDrilldownTestParameter.json";
    }

    /**
     * GET /interface/application/performance/relatedSessions success
     */
    @Test
    public void applicationPerformanceRelatedSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/relatedSessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/allNetworkAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensioAllNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/allNetworkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/networkAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensioNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/networkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/tcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/tcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/httpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/sslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/tcpLostlenPercent success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/tcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/packetRetransPercent success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionPacketRetransPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/packetRetransPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
