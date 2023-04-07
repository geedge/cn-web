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
 *     Network Applications Performance Overview
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NetworkApplicationPerfirmanceOverviewTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NetworkApplicationPerfirmanceOverviewTestParameter.json";
    }

    /**
     * GET /interface/application/performance/overview/tcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/tcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/httpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/httpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/sslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/sslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/tcpLostlenPercent success
     */
    @Test
    public void applicationPerformanceOverviewTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/tcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/packetRetransPercent success
     */
    @Test
    public void applicationPerformanceOverviewPacketRetransPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/packetRetransPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/totalTrafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewTotalTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/totalNetworkAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewTotalNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalNetworkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/totalHttpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewTotalHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/totalSslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewTotalSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalSslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appTrafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewAppTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appNetworkAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewAppNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appNetworkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appTcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewAppTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appTcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appHttpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewAppHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appSslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewAppSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appSslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appTcpLostlenPercent success
     */
    @Test
    public void applicationPerformanceOverviewAppTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appTcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/appPacketRetransPercent success
     */
    @Test
    public void applicationPerformanceOverviewAppPacketRetransPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appPacketRetransPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/dimensionTrafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDimensionTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/dimensionCycleTrafficAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDimensionCycleTrafficAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/dimensionNetworkAnalysis success
     */
    @Test
    public void applicationPerformanceOverviewDimensionNetworkAnalysis_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionNetworkAnalysis", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/overview/dimensionTcpSessionDelay success
     */
    @Test
    public void applicationPerformanceOverviewDimensionTcpSessionDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionTcpSessionDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/dimensionHttpResponseDelay success
     */
    @Test
    public void applicationPerformanceOverviewDimensionHttpResponseDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/application/performance/overview/dimensionSslConDelay success
     */
    @Test
    public void applicationPerformanceOverviewDimensionSslConDelay_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionSslConDelay", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/dimensionTcpLostlenPercent success
     */
    @Test
    public void applicationPerformanceOverviewDimensionTcpLostlenPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionTcpLostlenPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
    
    /**
     * GET /interface/application/performance/overview/dimensionPacketRetransPercent success
     */
    @Test
    public void applicationPerformanceOverviewDimensionPacketRetransPercent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionPacketRetransPercent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/relatedSessions error
     */
    @Test
    public void applicationPerformanceRelatedSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/relatedSessions", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/tcpLostlenPercent error
     */
    @Test
    public void applicationPerformanceLocationTcpLostlenPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/tcpLostlenPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/networkMap error
     */
    @Test
    public void applicationPerformanceLocationNetworkMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/networkMap", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/httpResponseLatency error
     */
    @Test
    public void applicationPerformanceLocationHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/httpResponseLatency", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/packetsLoss error
     */
    @Test
    public void applicationPerformanceLocationPacketsLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/packetsLoss", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

    /**
     * GET /interface/application/performance/location/packetsRetrains error
     */
    @Test
    public void applicationPerformanceLocationPacketsRetrains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/packetsRetrains", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/sslHandshakeLatency error
     */
    @Test
    public void applicationPerformanceLocationSslHandshakeLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/sslHandshakeLatency", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/tcpConnectionEstablishLatency error
     */
    @Test
    public void applicationPerformanceLocationTcpConnectionEstablishLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/tcpConnectionEstablishLatency", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/thoughput error
     */
    @Test
    public void applicationPerformanceLocationThoughput_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/thoughput", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/packetRetransPercent error
     */
    @Test
    public void applicationPerformanceLocationPacketRetransPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/packetRetransPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/httpResponseDelay error
     */
    @Test
    public void applicationPerformanceLocationHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/sslConDelay error
     */
    @Test
    public void applicationPerformanceLocationSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/location/tcpSessionDelay error
     */
    @Test
    public void applicationPerformanceLocationTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/location/tcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/dimensionTrafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDimensionTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/dimensionTcpLostlenPercent error
     */
    @Test
    public void applicationPerformanceOverviewDimensionTcpLostlenPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionTcpLostlenPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/dimensionCycleTrafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDimensionCycleTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }


    /**
     * GET /interface/application/performance/overview/dimensionNetworkAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDimensionNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionNetworkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/dimensionPacketRetransPercent error
     */
    @Test
    public void applicationPerformanceOverviewDimensionPacketRetransPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionPacketRetransPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }


    /**
     * GET /interface/application/performance/overview/dimensionHttpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewDimensionHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/dimensionSslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewDimensionSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionSslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/dimensionTcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewDimensionTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/dimensionTcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/tcpLostlenPercent error
     */
    @Test
    public void applicationPerformanceOverviewTcpLostlenPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/tcpLostlenPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/totalTrafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewTotalTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/totalNetworkAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewTotalNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalNetworkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/totalHttpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewTotalHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalHttpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/totalSslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewTotalSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/totalSslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/packetRetransPercent error
     */
    @Test
    public void applicationPerformanceOverviewPacketRetransPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/packetRetransPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appTrafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewAppTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appTcpLostlenPercent error
     */
    @Test
    public void applicationPerformanceOverviewAppTcpLostlenPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appTcpLostlenPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appNetworkAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewAppNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appNetworkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appPacketRetransPercent error
     */
    @Test
    public void applicationPerformanceOverviewAppPacketRetransPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appPacketRetransPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appHttpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewAppHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appHttpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appSslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewAppSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appSslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/appTcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewAppTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/appTcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/httpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/sslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }



    /**
     * GET /interface/application/performance/overview/tcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/tcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimensionTrafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimensionCycleTrafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionCycleTrafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimensionHttpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleDimensionHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionHttpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

    /**
     * GET /interface/application/performance/overview/drilldown/dimensionSslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleDimensionSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionSslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimensionTcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionCycleDimensionTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimensionTcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/drilldown/dimension/trafficGraph error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDrilldownDimensionTrafficGraph_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/drilldown/dimension/trafficGraph", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/recentEvents error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionRecentEvents_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/recentEvents", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/tcpLostlenPercent error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionTcpLostlenPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/tcpLostlenPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/networkAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensioNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/networkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/allNetworkAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensioAllNetworkAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/allNetworkAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

    /**
     * GET /interface/application/performance/overview/drilldown/dimension/packetRetransPercent error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionPacketRetransPercent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/packetRetransPercent", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/httpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/sslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/tcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/tcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/trafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountryTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/trafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/httpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountryHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/sslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountrySslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/country/tcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsCountryTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/country/tcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/trafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/trafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/httpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/sslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/province/tcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsProvinceTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/province/tcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/trafficAnalysis error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldTrafficAnalysis_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/trafficAnalysis", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/httpResponseDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldHttpResponseDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/httpResponseDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/sslConDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldSslConDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/sslConDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/application/performance/overview/drilldown/dimension/clientLocations/world/tcpSessionDelay error
     */
    @Test
    public void applicationPerformanceOverviewDrilldownDimensionClientLocationsWorldTcpSessionDelay_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/application/performance/overview/drilldown/dimension/clientLocations/world/tcpSessionDelay", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }
}
