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
 *     客户端定位&事件统计
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NetworkApplicationPerfirmanceClientEventTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkApplicationPerfirmanceClientEventTestParameter.json";
    }

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/trafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/trafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/tcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/tcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/httpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/sslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/trafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountryTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/trafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/tcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountryTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/tcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/httpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountryHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/sslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountrySslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/trafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/trafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/tcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/tcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/httpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/sslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/recentEvents success
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionRecentEvents_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/recentEvents", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
