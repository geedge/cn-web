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
public class NetworkOverviewTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkOverviewTestParameter.json";
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
     * GET /interface/overview/applicationTrafficAnalysis success
     */
    @Test
    public void overviewApplicationTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/applicationTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/appCompanyTrafficAnalysis success
     */
    @Test
    public void overviewAppCompanyTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/appCompanyTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/eventSeverity success
     */
    @Test
    public void overviewEventSeverity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/eventSeverity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/ddosEventAnalysis success
     */
    @Test
    public void overviewDdosEventAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/ddosEventAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
}
