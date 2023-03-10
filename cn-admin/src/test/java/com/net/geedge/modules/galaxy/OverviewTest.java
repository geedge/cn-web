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
 * @Date: 2023/02/24/10:32
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class OverviewTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/OverviewTestParameter.json";
    }

    /**
     * GET /interface/overview/appCompanyTrafficAnalysis success
     */
    @Test
    public void overviewAppCompanyTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/appCompanyTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/appCompanyTrafficAnalysis error
     */
    @Test
    public void overviewAppCompanyTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/appCompanyTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/appCompanyCycleTrafficTotal success
     */
    @Test
    public void overviewAppCompanyCycleTrafficTotal_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/appCompanyCycleTrafficTotal", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/appCompanyCycleTrafficTotal error
     */
    @Test
    public void overviewAppCompanyCycleTrafficTotal_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/appCompanyCycleTrafficTotal", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionTrafficAnalysis success
     */
    @Test
    public void overviewDimensionTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionTrafficAnalysis error
     */
    @Test
    public void overviewDimensionTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionTcpLostlenPercent success
     */
    @Test
    public void overviewDimensionTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionTcpLostlenPercent error
     */
    @Test
    public void overviewDimensionTcpLostlenPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTcpLostlenPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionCycleTrafficAnalysis success
     */
    @Test
    public void overviewDimensionCycleTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionCycleTrafficAnalysis error
     */
    @Test
    public void overviewDimensionCycleTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/eventType success
     */
    @Test
    public void overviewEventType_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/eventType", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/eventType error
     */
    @Test
    public void overviewEventType_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/eventType", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/eventSeverity success
     */
    @Test
    public void overviewEventSeverity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/eventSeverity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/eventSeverity error
     */
    @Test
    public void overviewEventSeverity_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/eventSeverity", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/applicationTrafficAnalysis success
     */
    @Test
    public void overviewApplicationTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/applicationTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/applicationTrafficAnalysis error
     */
    @Test
    public void overviewApplicationTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/applicationTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/applicationCycleTrafficTotal success
     */
    @Test
    public void overviewApplicationCycleTrafficTotal_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/applicationCycleTrafficTotal", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/applicationCycleTrafficTotal error
     */
    @Test
    public void overviewApplicationCycleTrafficTotal_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/applicationCycleTrafficTotal", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/totalTrafficAnalysis success
     */
    @Test
    public void overviewTotalTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/totalTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/totalTrafficAnalysis error
     */
    @Test
    public void overviewTotalTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/totalTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionPacketRetransPercent success
     */
    @Test
    public void overviewDimensionPacketRetransPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionPacketRetransPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionPacketRetransPercent error
     */
    @Test
    public void overviewDimensionPacketRetransPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionPacketRetransPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/ddosEventAnalysis success
     */
    @Test
    public void overviewDdosEventAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/ddosEventAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/ddosEventAnalysis error
     */
    @Test
    public void overviewDdosEventAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/ddosEventAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionHttpResponseDelay success
     */
    @Test
    public void overviewDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionHttpResponseDelay error
     */
    @Test
    public void overviewDimensionHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionSslConDelay success
     */
    @Test
    public void overviewDimensionSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionSslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionSslConDelay error
     */
    @Test
    public void overviewDimensionSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionSslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionTcpSessionDelay success
     */
    @Test
    public void overviewDimensionTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/dimensionTcpSessionDelay error
     */
    @Test
    public void overviewDimensionTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/drilldown/list success
     */
    @Test
    public void overviewDrilldownList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/drilldown/list error
     */
    @Test
    public void overviewDrilldownList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/list", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/drilldown/dimensionTrafficAnalysis error
     */
    @Test
    public void overviewDrilldownDimensionTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/drilldown/dimensionCycleTrafficAnalysis error
     */
    @Test
    public void overviewDrilldownDimensionCycleTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/drilldown/dimensionHttpResponseDelay error
     */
    @Test
    public void overviewDrilldownDimensionHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

    /**
     * GET /interface/overview/drilldown/dimensionSslConDelay error
     */
    @Test
    public void overviewDrilldownDimensionSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionSslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/drilldown/dimensionTcpPktRetranPercent error
     */
    @Test
    public void overviewDrilldownDimensionTcpPktRetranPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/drilldown/dimensionTcpPktRetranPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/event/dimensionEvents success
     */
    @Test
    public void overviewEventDimensionEvents_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/event/dimensionEvents", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/event/dimensionEvents error
     */
    @Test
    public void overviewEventDimensionEvents_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/event/dimensionEvents", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/event/recentEvents success
     */
    @Test
    public void overviewEventRecentEvents_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/event/recentEvents", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/event/recentEvents error
     */
    @Test
    public void overviewEventRecentEvents_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/event/recentEvents", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/event/list success
     */
    @Test
    public void overviewEventList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/event/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/overview/event/list error
     */
    @Test
    public void overviewEventList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/event/list", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }
}
