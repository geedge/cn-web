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
public class NetworkOverviewDimensionTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkOverviewDimensionTestParameter.json";
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
     * GET /interface/overview/dimensionCycleTrafficAnalysis success
     */
    @Test
    public void overviewDimensionCycleTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/dimensionHttpResponseDelay success
     */
    @Test
    public void overviewDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/overview/dimensionTcpLostlenPercent success
     */
    @Test
    public void overviewDimensionTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/overview/dimensionTcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
}
