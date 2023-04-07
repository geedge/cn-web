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
 *   Location
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NetworkApplicationPerfirmanceLocationTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkApplicationPerfirmanceLocationTestParameter.json";
    }

    /**
     * GET /interface/application/performance/location/networkMap success
     */
    @Test
    public void applicationPerformanceLocationNetworkMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/networkMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/tcpSessionDelay success
     */
    @Test
    public void applicationPerformanceLocationTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/tcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/httpResponseDelay success
     */
    @Test
    public void applicationPerformanceLocationHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/sslConDelay success
     */
    @Test
    public void applicationPerformanceLocationSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/tcpLostlenPercent success
     */
    @Test
    public void applicationPerformanceLocationTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/tcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/packetRetransPercent success
     */
    @Test
    public void applicationPerformanceLocationPacketRetransPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/packetRetransPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/thoughput success
     */
    @Test
    public void applicationPerformanceLocationThoughput_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/thoughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/tcpConnectionEstablishLatency success
     */
    @Test
    public void applicationPerformanceLocationTcpConnectionEstablishLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/tcpConnectionEstablishLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/httpResponseLatency success
     */
    @Test
    public void applicationPerformanceLocationHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/httpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/location/sslHandshakeLatency success
     */
    @Test
    public void applicationPerformanceLocationSslHandshakeLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/sslHandshakeLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/location/packetsLoss success
     */
    @Test
    public void applicationPerformanceLocationPacketsLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/packetsLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/location/packetsRetrains success
     */
    @Test
    public void applicationPerformanceLocationPacketsRetrains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/packetsRetrains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
