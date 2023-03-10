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
 * @Auther: lijinyang
 * @Date: 2023/02/24/14:16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class LinkTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/LinkTestParameter.json";
    }

    /**
     * GET /interface/link/overview/bigramAnalysis success
     */
    @Test
    public void linkOverviewBigramAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/bigramAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/bigramAnalysis error
     */
    @Test
    public void linkOverviewBigramAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/bigramAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/bigramNextHopAnalysis success
     */
    @Test
    public void linkOverviewBigramNextHopAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/bigramNextHopAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/bigramNextHopAnalysis error
     */
    @Test
    public void linkOverviewBigramNextHopAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/bigramNextHopAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/nextHopAnalysis success
     */
    @Test
    public void linkOverviewNextHopAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/nextHopAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/nextHopAnalysis error
     */
    @Test
    public void linkOverviewNextHopAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/nextHopAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/analysis success
     */
    @Test
    public void linkOverviewAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/analysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/analysis error
     */
    @Test
    public void linkOverviewAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/analysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/quadrupleEgressAnalysis success
     */
    @Test
    public void linkOverviewQuadrupleEgressAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/quadrupleEgressAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/quadrupleEgressAnalysis error
     */
    @Test
    public void linkOverviewQuadrupleEgressAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/quadrupleEgressAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/quadrupleIngressAnalysis success
     */
    @Test
    public void linkOverviewQuadrupleIngressAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/quadrupleIngressAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/quadrupleIngressAnalysis error
     */
    @Test
    public void linkOverviewQuadrupleIngressAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/quadrupleIngressAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/quadrupleEgressAnalysis success
     */
    @Test
    public void linkOverviewDrilldownQuadrupleEgressAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/quadrupleEgressAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/quadrupleEgressAnalysis error
     */
    @Test
    public void linkOverviewDrilldownQuadrupleEgressAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/quadrupleEgressAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/quadrupleIngressAnalysis success
     */
    @Test
    public void linkOverviewDrilldownQuadrupleIngressAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/quadrupleIngressAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/quadrupleIngressAnalysis error
     */
    @Test
    public void linkOverviewDrilldownQuadrupleIngressAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/quadrupleIngressAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/networkAnalysis success
     */
    @Test
    public void linkOverviewDrilldownNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/networkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/networkAnalysis error
     */
    @Test
    public void linkOverviewDrilldownNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/networkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/totalTrafficAnalysis success
     */
    @Test
    public void linkOverviewDrilldownTotalTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/totalTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/totalTrafficAnalysis error
     */
    @Test
    public void linkOverviewDrilldownTotalTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/totalTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/analysis success
     */
    @Test
    public void linkOverviewDrilldownDimensionAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/analysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/analysis error
     */
    @Test
    public void linkOverviewDrilldownDimensionAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/analysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/cycleAnalysis success
     */
    @Test
    public void linkOverviewDrilldownDimensionCycleAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/cycleAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/cycleAnalysis error
     */
    @Test
    public void linkOverviewDrilldownDimensionCycleAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/cycleAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/networkAnalysis success
     */
    @Test
    public void linkOverviewDrilldownDimensionNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/networkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/networkAnalysis error
     */
    @Test
    public void linkOverviewDrilldownDimensionNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/networkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/httpResponseDelay success
     */
    @Test
    public void linkOverviewDrilldownDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/httpResponseDelay error
     */
    @Test
    public void linkOverviewDrilldownDimensionHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/sslConDelay success
     */
    @Test
    public void linkOverviewDrilldownDimensionSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/link/overview/drilldown/dimension/sslConDelay error
     */
    @Test
    public void linkOverviewDrilldownDimensionSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/link/overview/drilldown/dimension/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }
}
