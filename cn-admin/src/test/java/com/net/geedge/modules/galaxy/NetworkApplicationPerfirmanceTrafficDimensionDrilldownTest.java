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
 *     各维度下钻流量曲线图&流量统计
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NetworkApplicationPerfirmanceTrafficDimensionDrilldownTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkApplicationPerfirmanceTrafficDimensionDrilldownTestParameter.json";
    }

    /**
     * GET /interface/application/performance/overview/drilldown/drilldown/dimension/trafficGraph success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDrilldownDimensionTrafficGraph_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/drilldown/dimension/trafficGraph", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimensionTrafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimensionCycleTrafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimensionTcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleDimensionTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionTcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimensionHttpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimensionSslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleDimensionSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionSslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
