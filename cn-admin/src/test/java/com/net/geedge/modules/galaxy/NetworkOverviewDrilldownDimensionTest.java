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
 * NetworkOverview
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NetworkOverviewDrilldownDimensionTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkOverviewDrilldownDimensionTestParameter.json";
    }

    /**
     * GET /interface/overview/drilldown/dimensionTrafficAnalysis success
     */
    @Test
    public void overviewDrilldownDimensionTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/overview/drilldown/dimensionCycleTrafficAnalysis success
     */
    @Test
    public void overviewDrilldownDimensionCycleTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/overview/drilldown/dimensionTcpPktRetranPercent success
     */
    @Test
    public void overviewDrilldownDimensionTcpPktRetranPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionTcpPktRetranPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/overview/drilldown/dimensionHttpResponseDelay success
     */
    @Test
    public void overviewDrilldownDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/overview/drilldown/dimensionSslConDelay success
     */
    @Test
    public void overviewDrilldownDimensionSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionSslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
