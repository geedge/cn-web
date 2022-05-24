package com.net.geedge.modules.galaxy;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualDatasetField;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/05/05/13:43
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class GalaxyInterfaceTest extends BaseTest<Map> {
    @Override
    protected String jsonIndex() {
        return "galaxy/GalaxyInterfaceTestParameter.json";
    }

    /**
     * GET /interface/notexist not exist
     */
    @Test
    public void proxy_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/notexist", queryParam);

        int status = result.getResponse().getStatus();
        Assert.assertEquals(status, HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * GET /interface/traffic/map success
     */
    @Test
    public void trafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/map", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/sessions success
     */
    @Test
    public void trafficSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/packets success
     */
    @Test
    public void trafficPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/throughput success
     */
    @Test
    public void trafficThroughput_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/activeClientIP success
     */
    @Test
    public void trafficActiveClientIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/activeClientIP error
     */
    @Test
    public void trafficActiveClientIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/activeServerIP success
     */
    @Test
    public void trafficActiveServerIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/activeServerIP error
     */
    @Test
    public void trafficActiveServerIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/topDomains success
     */
    @Test
    public void trafficTopDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topDomains error
     */
    @Test
    public void trafficTopDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/topApp success
     */
    @Test
    public void trafficTopApp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topApp error
     */
    @Test
    public void trafficTopApp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/topDomainCategories success
     */
    @Test
    public void trafficTopDomainCategories_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomainCategories", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topDomainCategories error
     */
    @Test
    public void trafficTopDomainCategories_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomainCategories", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/activeDomainCategories success
     */
    @Test
    public void trafficActiveDomainCategories_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeDomainCategories", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/activeDomainCategories error
     */
    @Test
    public void trafficActiveDomainCategories_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeDomainCategories", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/topDomainReputation success
     */
    @Test
    public void trafficTopDomainReputation_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomainReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topDomainReputation error
     */
    @Test
    public void trafficTopDomainReputation_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomainReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/activeDomainReputation success
     */
    @Test
    public void trafficActiveDomainReputation_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeDomainReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/activeDomainReputation error
     */
    @Test
    public void trafficActiveDomainReputation_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeDomainReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/npm/rtt success
     */
    @Test
    public void npmRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/rtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/httpResponseTime success
     */
    @Test
    public void npmHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/httpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/sslHandshakeTime success
     */
    @Test
    public void npmSslHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/sslHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/avgPacketLossRate success
     */
    @Test
    public void npmAvgPacketLossRate_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/avgPacketLossRate", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/avgRetransmissionRate success
     */
    @Test
    public void npmAvgRetransmissionRate_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/avgRetransmissionRate", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/rttMap success
     */
    @Test
    public void npmRttMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/rttMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/httpResponseTimeMap success
     */
    @Test
    public void npmHttpResponseTimeMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/httpResponseTimeMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/sslHandshakeTimeMap success
     */
    @Test
    public void npmSslHandshakeTimeMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/sslHandshakeTimeMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/packetLossMap success
     */
    @Test
    public void npmPacketLossMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/packetLossMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/packetRetransMap success
     */
    @Test
    public void npmPacketRetransMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/packetRetransMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/filter success
     */
    @Test
    public void entityIpFilter_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/filter", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/list success
     */
    @Test
    public void entityIpList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/filter success
     */
    @Test
    public void entityDomainFilter_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/filter", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/list success
     */
    @Test
    public void entityDomainList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/filter success
     */
    @Test
    public void entityAppFilter_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/filter", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/list success
     */
    @Test
    public void entityAppList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/appRrt success
     */
    @Test
    public void npmAppRrt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/appRrt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/appHttpResponseTime success
     */
    @Test
    public void npmAppHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/appHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/appSSLHandshakeTime success
     */
    @Test
    public void npmAppSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/appSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/appPacketLoss success
     */
    @Test
    public void npmAppPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/appPacketRetrans success
     */
    @Test
    public void npmAppPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/domainRrt success
     */
    @Test
    public void npmDomainRrt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/domainRrt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/domainHttpResponseTime success
     */
    @Test
    public void npmDomainHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/domainSSLHandshakeTime success
     */
    @Test
    public void npmDomainSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/domainPacketLoss success
     */
    @Test
    public void npmDomainPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/domainPacketRetrans success
     */
    @Test
    public void npmDomainPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/serverIPRrt success
     */
    @Test
    public void npmServerIPRrt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/serverIPRrt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/serverIPHttpResponseTime success
     */
    @Test
    public void npmServerIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/serverIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/serverIPSSLHandshakeTime success
     */
    @Test
    public void npmServerIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/serverIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/serverIPPacketLoss success
     */
    @Test
    public void npmServerIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/serverIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/serverIPPacketRetrans success
     */
    @Test
    public void npmServerIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/serverIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/clientIPRrt success
     */
    @Test
    public void npmClientIPRrt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/clientIPRrt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/clientIPHttpResponseTime success
     */
    @Test
    public void npmClientIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/clientIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/clientIPSSLHandshakeTime success
     */
    @Test
    public void npmClientIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/clientIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/clientIPPacketLoss success
     */
    @Test
    public void npmClientIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/clientIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/npm/clientIPPacketRetrans success
     */
    @Test
    public void npmClientIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/npm/clientIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/id success
     */
    @Test
    public void entityAppDetailId_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/id", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/id error
     */
    @Test
    public void entityAppDetailId_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/id", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/category success
     */
    @Test
    public void entityAppDetailCategory_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/category", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/category error
     */
    @Test
    public void entityAppDetailCategory_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/category", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/subcategory success
     */
    @Test
    public void entityAppDetailSubcategory_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/subcategory", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/subcategory error
     */
    @Test
    public void entityAppDetailSubcategory_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/subcategory", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/risk success
     */
    @Test
    public void entityAppDetailRisk_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/risk", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/risk error
     */
    @Test
    public void entityAppDetailRisk_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/risk", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/trafficMap success
     */
    @Test
    public void entityAppDetailTrafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/trafficMap error
     */
    @Test
    public void entityAppDetailTrafficMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/sessions success
     */
    @Test
    public void entityAppDetailSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/sessions error
     */
    @Test
    public void entityAppDetailSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/packets success
     */
    @Test
    public void entityAppDetailPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/packets error
     */
    @Test
    public void entityAppDetailPackets_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/throughput success
     */
    @Test
    public void entityAppDetailThroughput_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/throughput error
     */
    @Test
    public void entityAppDetailThroughput_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/activeClientIP success
     */
    @Test
    public void entityAppDetailActiveClientIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/activeClientIP error
     */
    @Test
    public void entityAppDetailActiveClientIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/activeServerIP success
     */
    @Test
    public void entityAppDetailActiveServerIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/activeServerIP error
     */
    @Test
    public void entityAppDetailActiveServerIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/topDomains success
     */
    @Test
    public void entityAppDetailTopDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/topDomains error
     */
    @Test
    public void entityAppDetailTopDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/appRtt success
     */
    @Test
    public void entityAppDetailAppRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/appRtt error
     */
    @Test
    public void entityAppDetailAppRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/appHttpResponseTime success
     */
    @Test
    public void entityAppDetailAppHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/appHttpResponseTime error
     */
    @Test
    public void entityAppDetailAppHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/appSSLHandshakeTime success
     */
    @Test
    public void entityAppDetailAppSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/appSSLHandshakeTime error
     */
    @Test
    public void entityAppDetailAppSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/appPacketLoss success
     */
    @Test
    public void entityAppDetailAppPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/appPacketLoss error
     */
    @Test
    public void entityAppDetailAppPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/appPacketRetrans success
     */
    @Test
    public void entityAppDetailAppPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/appPacketRetrans error
     */
    @Test
    public void entityAppDetailAppPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainRtt success
     */
    @Test
    public void entityAppDetailDomainRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainRtt error
     */
    @Test
    public void entityAppDetailDomainRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainHttpResponseTime success
     */
    @Test
    public void entityAppDetailDomainHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainHttpResponseTime error
     */
    @Test
    public void entityAppDetailDomainHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainSSLHandshakeTime success
     */
    @Test
    public void entityAppDetailDomainSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainSSLHandshakeTime error
     */
    @Test
    public void entityAppDetailDomainSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainPacketLoss success
     */
    @Test
    public void entityAppDetailDomainPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainPacketLoss error
     */
    @Test
    public void entityAppDetailDomainPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }
    /**
     * GET /interface/entity/app/detail/domainPacketRetrans success
     */
    @Test
    public void entityAppDetailDomainPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainPacketRetrans error
     */
    @Test
    public void entityAppDetailDomainPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/domainCategoryServerIpList success
     */
    @Test
    public void trafficDomainCategoryServerIpList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/domainCategoryServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/domainCategoryServerIpList error
     */
    @Test
    public void trafficDomainCategoryServerIpList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/domainCategoryServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/domainReputationServerIpList success
     */
    @Test
    public void trafficDomainReputationServerIpList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/domainReputationServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/domainReputationServerIpList error
     */
    @Test
    public void trafficDomainReputationServerIpList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/domainReputationServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIPRtt success
     */
    @Test
    public void entityAppDetailServerIPRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIPRtt error
     */
    @Test
    public void entityAppDetailServerIPRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIPHttpResponseTime success
     */
    @Test
    public void entityAppDetailServerIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIPHttpResponseTime error
     */
    @Test
    public void entityAppDetailServerIPHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIPSSLHandshakeTime success
     */
    @Test
    public void entityAppDetailServerIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIPSSLHandshakeTime error
     */
    @Test
    public void entityAppDetailServerIPSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIPPacketLoss success
     */
    @Test
    public void entityAppDetailServerIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIPPacketLoss error
     */
    @Test
    public void entityAppDetailServerIPPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIPPacketRetrans success
     */
    @Test
    public void entityAppDetailServerIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIPPacketRetrans error
     */
    @Test
    public void entityAppDetailServerIPPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIPRtt success
     */
    @Test
    public void entityAppDetailClientIPRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIPRtt error
     */
    @Test
    public void entityAppDetailClientIPRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIPHttpResponseTime success
     */
    @Test
    public void entityAppDetailClientIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIPSSLHandshakeTime error
     */
    @Test
    public void entityAppDetailClientIPHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIPSSLHandshakeTime success
     */
    @Test
    public void entityAppDetailClientIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIPSSLHandshakeTime error
     */
    @Test
    public void entityAppDetailClientIPSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIPPacketLoss success
     */
    @Test
    public void entityAppDetailClientIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIPPacketLoss error
     */
    @Test
    public void entityAppDetailClientIPPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIPPacketRetrans success
     */
    @Test
    public void entityAppDetailClientIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIPPacketRetrans error
     */
    @Test
    public void entityAppDetailClientIPPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/category success
     */
    @Test
    public void entityDomainDetailCategory_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/category", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/category error
     */
    @Test
    public void entityDomainDetailCategory_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/category", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/categoryGroup success
     */
    @Test
    public void entityDomainDetailCategoryGroup_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/categoryGroup", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/categoryGroup error
     */
    @Test
    public void entityDomainDetailCategoryGroup_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/categoryGroup", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/reputationLevel success
     */
    @Test
    public void entityDomainDetailReputationLevel_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/reputationLevel", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/reputationLevel error
     */
    @Test
    public void entityDomainDetailReputationLevel_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/reputationLevel", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/risk success
     */
    @Test
    public void entityDomainDetailRisk_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/risk", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/risk error
     */
    @Test
    public void entityDomainDetailRisk_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/risk", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/visits success
     */
    @Test
    public void entityDomainDetailVisits_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/visits", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/visits error
     */
    @Test
    public void entityDomainDetailVisits_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/visits", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/trafficMap success
     */
    @Test
    public void entityDomainDetailTrafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/trafficMap error
     */
    @Test
    public void entityDomainDetailTrafficMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/sessions success
     */
    @Test
    public void entityDomainDetailSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/sessions error
     */
    @Test
    public void entityDomainDetailSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/packets success
     */
    @Test
    public void entityDomainDetailPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/packets error
     */
    @Test
    public void entityDomainDetailPackets_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/throughput success
     */
    @Test
    public void entityDomainDetailThroughput_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/throughput error
     */
    @Test
    public void entityDomainDetailThroughput_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/activeClientIP success
     */
    @Test
    public void entityDomainDetailActiveClientIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/activeClientIP error
     */
    @Test
    public void entityDomainDetailActiveClientIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/activeServerIP success
     */
    @Test
    public void entityDomainDetailActiveServerIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/activeServerIP error
     */
    @Test
    public void entityDomainDetailActiveServerIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/topAPP success
     */
    @Test
    public void entityDomainDetailTopAPP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/topAPP error
     */
    @Test
    public void entityDomainDetailTopAPP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/domainRtt success
     */
    @Test
    public void entityDomainDetailDomainRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/domainRtt error
     */
    @Test
    public void entityDomainDetailDomainRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/domainHttpResponseTime success
     */
    @Test
    public void entityDomainDetailDomainHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/domainHttpResponseTime error
     */
    @Test
    public void entityDomainDetailDomainHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/domainSSLHandshakeTime success
     */
    @Test
    public void entityDomainDetailDomainSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/domainSSLHandshakeTime error
     */
    @Test
    public void entityDomainDetailDomainSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/domainPacketLoss success
     */
    @Test
    public void entityDomainDetailDomainPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/domainPacketLoss error
     */
    @Test
    public void entityDomainDetailDomainPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/domainPacketRetrans success
     */
    @Test
    public void entityDomainDetailDomainPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/domainPacketRetrans error
     */
    @Test
    public void entityDomainDetailDomainPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPRtt success
     */
    @Test
    public void entityDomainDetailServerIPRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPRtt error
     */
    @Test
    public void entityDomainDetailServerIPRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPHttpResponseTime success
     */
    @Test
    public void entityDomainDetailServerIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPHttpResponseTime error
     */
    @Test
    public void entityDomainDetailServerIPHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPSSLHandshakeTime success
     */
    @Test
    public void entityDomainDetailServerIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPSSLHandshakeTime error
     */
    @Test
    public void entityDomainDetailServerIPSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketLoss success
     */
    @Test
    public void entityDomainDetailServerIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketLoss error
     */
    @Test
    public void entityDomainDetailServerIPPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketRetrans success
     */
    @Test
    public void entityDomainDetailServerIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketRetrans error
     */
    @Test
    public void entityDomainDetailServerIPPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPRtt success
     */
    @Test
    public void entityDomainDetailClientIPRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPRtt error
     */
    @Test
    public void entityDomainDetailClientIPRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPHttpResponseTime success
     */
    @Test
    public void entityDomainDetailServerIPClientIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPHttpResponseTime error
     */
    @Test
    public void entityDomainDetailServerIPClientIPHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPSSLHandshakeTime success
     */
    @Test
    public void entityDomainDetailServerIPClientIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPSSLHandshakeTime error
     */
    @Test
    public void entityDomainDetailServerIPClientIPSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketLoss success
     */
    @Test
    public void entityDomainDetailServerIPClientIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketLoss error
     */
    @Test
    public void entityDomainDetailServerIPClientIPPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketRetrans success
     */
    @Test
    public void entityDomainDetailServerIPClientIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketRetrans error
     */
    @Test
    public void entityDomainDetailServerIPClientIPPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appCount success
     */
    @Test
    public void entityIpDetailAppCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appCount error
     */
    @Test
    public void entityIpDetailAppCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainCount success
     */
    @Test
    public void entityIpDetailDomainCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainCount error
     */
    @Test
    public void entityIpDetailDomainCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/clientIPCount success
     */
    @Test
    public void entityIpDetailClientIPCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/clientIPCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/clientIPCount error
     */
    @Test
    public void entityIpDetailClientIPCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/clientIPCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appCountLine success
     */
    @Test
    public void entityIpDetailAppCountLine_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appCountLine error
     */
    @Test
    public void entityIpDetailAppCountLine_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainCountLine success
     */
    @Test
    public void entityIpDetailDomainCountLine_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainCountLine error
     */
    @Test
    public void entityIpDetailDomainCountLine_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/clientIPCountLine success
     */
    @Test
    public void entityIpDetailClientIPCountLine_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/clientIPCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/clientIPCountLine error
     */
    @Test
    public void entityIpDetailClientIPCountLine_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/clientIPCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/region success
     */
    @Test
    public void entityIpDetailRegion_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/region", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/region error
     */
    @Test
    public void entityIpDetailRegion_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/region", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/asn success
     */
    @Test
    public void entityIpDetailAsn_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/asn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/asn error
     */
    @Test
    public void entityIpDetailAsn_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/asn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/country success
     */
    @Test
    public void entityIpDetailCountry_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/country", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/country error
     */
    @Test
    public void entityIpDetailCountry_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/country", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/trafficMap success
     */
    @Test
    public void entityIpDetailTrafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/trafficMap error
     */
    @Test
    public void entityIpDetailTrafficMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/sessions success
     */
    @Test
    public void entityIpDetailSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/sessions error
     */
    @Test
    public void entityIpDetailSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/packets success
     */
    @Test
    public void entityIpDetailPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/packets error
     */
    @Test
    public void entityIpDetailPackets_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/throughput success
     */
    @Test
    public void entityIpDetailThroughput_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/throughput error
     */
    @Test
    public void entityIpDetailThroughput_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/throughput", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/activeClientIP success
     */
    @Test
    public void entityIpDetailActiveClientIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/activeClientIP error
     */
    @Test
    public void entityIpDetailActiveClientIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/topDomains success
     */
    @Test
    public void entityIpDetailTopDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/topDomains error
     */
    @Test
    public void entityIpDetailTopDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/topAPP success
     */
    @Test
    public void entityIpDetailTopAPP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/topAPP error
     */
    @Test
    public void entityIpDetailTopAPP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appRtt success
     */
    @Test
    public void entityIpDetailAppRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appRtt error
     */
    @Test
    public void entityIpDetailAppRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appHttpResponseTime success
     */
    @Test
    public void entityIpDetailAppHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appHttpResponseTime error
     */
    @Test
    public void entityIpDetailAppHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appSSLHandshakeTime success
     */
    @Test
    public void entityIpDetailAppSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appSSLHandshakeTime error
     */
    @Test
    public void entityIpDetailAppSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appPacketLoss success
     */
    @Test
    public void entityIpDetailAppPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appPacketLoss error
     */
    @Test
    public void entityIpDetailAppPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appPacketRetrans success
     */
    @Test
    public void entityIpDetailAppPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appPacketRetrans error
     */
    @Test
    public void entityIpDetailAppPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainRtt success
     */
    @Test
    public void entityIpDetailDomainRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainRtt error
     */
    @Test
    public void entityIpDetailDomainRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainHttpResponseTime success
     */
    @Test
    public void entityIpDetailDomainHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainHttpResponseTime error
     */
    @Test
    public void entityIpDetailDomainHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainSSLHandshakeTime success
     */
    @Test
    public void entityIpDetailDomainSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainSSLHandshakeTime error
     */
    @Test
    public void entityIpDetailDomainSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainPacketLoss success
     */
    @Test
    public void entityIpDetailDomainPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainPacketLoss error
     */
    @Test
    public void entityIpDetailDomainPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainPacketRetrans success
     */
    @Test
    public void entityIpDetailDomainPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainPacketRetrans error
     */
    @Test
    public void entityIpDetailDomainPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/IPRtt success
     */
    @Test
    public void entityIpDetailIPRtt_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/IPRtt error
     */
    @Test
    public void entityIpDetailIPRtt_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPRtt", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/IPHttpResponseTime success
     */
    @Test
    public void entityIpDetailIPHttpResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/IPHttpResponseTime error
     */
    @Test
    public void entityIpDetailIPHttpResponseTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPHttpResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/IPSSLHandshakeTime success
     */
    @Test
    public void entityIpDetailIPSSLHandshakeTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/IPSSLHandshakeTime error
     */
    @Test
    public void entityIpDetailIPSSLHandshakeTime_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPSSLHandshakeTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/IPPacketLoss success
     */
    @Test
    public void entityIpDetailIPPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/IPPacketLoss error
     */
    @Test
    public void entityIpDetailIPPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/IPPacketRetrans success
     */
    @Test
    public void entityIpDetailIPPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/IPPacketRetrans error
     */
    @Test
    public void entityIpDetailIPPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/IPPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/filter success
     */
    @Test
    public void entityFilter_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/filter error
     */
    @Test
    public void entityFilter_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/count success
     */
    @Test
    public void entityAppDetailCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/count error
     */
    @Test
    public void entityAppDetailCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/countLine success
     */
    @Test
    public void entityAppDetailCountLine_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/countLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/countLine error
     */
    @Test
    public void entityAppDetailCountLine_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/countLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/count success
     */
    @Test
    public void entityDomainDetailCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/count error
     */
    @Test
    public void entityDomainDetailCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/countLine success
     */
    @Test
    public void entityDomainDetailCountLine_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/countLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/countLine error
     */
    @Test
    public void entityDomainDetailCountLine_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/countLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/list success
     */
    @Test
    public void entityList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/total success
     */
    @Test
    public void entityTotal_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/total", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/serverIPCountLine success
     */
    @Test
    public void entityIpDetailServerIPCountLine_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/serverIPCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/serverIPCountLine error
     */
    @Test
    public void entityIpDetailServerIPCountLine_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/serverIPCountLine", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/serverIPCount success
     */
    @Test
    public void entityIpDetailServerIPCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/serverIPCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/serverIPCount error
     */
    @Test
    public void entityIpDetailServerIPCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/serverIPCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/relationship success
     */
    @Test
    public void entityRelationship_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/relationship", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/relationship error
     */
    @Test
    public void entityRelationship_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/relationship", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/basicInfo success
     */
    @Test
    public void entityIpDetailBasicInfo_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/basicInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/basicInfo error
     */
    @Test
    public void entityIpDetailBasicInfo_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/basicInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/ports success
     */
    @Test
    public void entityIpDetailPorts_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/ports", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/ports error
     */
    @Test
    public void entityIpDetailPorts_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/ports", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domains success
     */
    @Test
    public void entityIpDetailDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domains error
     */
    @Test
    public void entityIpDetailDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/serviceOverview success
     */
    @Test
    public void entityIpDetailServiceOverview_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/serviceOverview error
     */
    @Test
    public void entityIpDetailServiceOverview_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/overallEstablishlatency success
     */
    @Test
    public void entityIpDetailOverallEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/overallEstablishlatency error
     */
    @Test
    public void entityIpDetailOverallEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/overallHttpResponseLatency success
     */
    @Test
    public void entityIpDetailOverallHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/overallHttpResponseLatency error
     */
    @Test
    public void entityIpDetailOverallHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/overallSslConLatency success
     */
    @Test
    public void entityIpDetailOverallSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/overallSslConLatency error
     */
    @Test
    public void entityIpDetailOverallSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/overallPacketLoss success
     */
    @Test
    public void entityIpDetailOverallPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/overallPacketLoss error
     */
    @Test
    public void entityIpDetailOverallPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/overallPacketRetrans success
     */
    @Test
    public void entityIpDetailOverallPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/overallPacketRetrans error
     */
    @Test
    public void entityIpDetailOverallPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/sessions_bak success
     */
    @Test
    public void entityDomainDetailSessionsBak_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/sessions_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/sessions_bak error
     */
    @Test
    public void entityDomainDetailSessionsBak_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/sessions_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/count_bak success
     */
    @Test
    public void entityDomainDetailCountBak_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/count_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/count_bak error
     */
    @Test
    public void entityDomainDetailCountBak_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/count_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/basicInfo success
     */
    @Test
    public void entityDomainDetailBasicInfo_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/basicInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/basicInfo error
     */
    @Test
    public void entityDomainDetailBasicInfo_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/basicInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/whois success
     */
    @Test
    public void entityDomainDetailWhois_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/whois", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/whois error
     */
    @Test
    public void entityDomainDetailWhois_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/whois", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/dnsRecord success
     */
    @Test
    public void entityDomainDetailDnsRecord_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/dnsRecord", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/dnsRecord error
     */
    @Test
    public void entityDomainDetailDnsRecord_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/dnsRecord", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serviceOverview success
     */
    @Test
    public void entityDomainDetailServiceOverview_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serviceOverview error
     */
    @Test
    public void entityDomainDetailServiceOverview_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainEstablishlatency success
     */
    @Test
    public void entityIpDetailDomainEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainEstablishlatency error
     */
    @Test
    public void entityIpDetailDomainEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainHttpResponseLatency success
     */
    @Test
    public void entityIpDetailDomainHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainHttpResponseLatency error
     */
    @Test
    public void entityIpDetailDomainHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/domainSslConLatency success
     */
    @Test
    public void entityIpDetailDomainSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/domainSslConLatency error
     */
    @Test
    public void entityIpDetailDomainSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appEstablishlatency success
     */
    @Test
    public void entityIpDetailAppEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appEstablishlatency error
     */
    @Test
    public void entityIpDetailAppEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appHttpResponseLatency success
     */
    @Test
    public void entityIpDetailAppHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appHttpResponseLatency error
     */
    @Test
    public void entityIpDetailAppHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/appSslConLatency success
     */
    @Test
    public void entityIpDetailAppSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/appSslConLatency error
     */
    @Test
    public void entityIpDetailAppSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/appSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/overallEstablishlatency success
     */
    @Test
    public void entityDomainDetailOverallEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/overallEstablishlatency error
     */
    @Test
    public void entityDomainDetailOverallEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/overallHttpResponseLatency success
     */
    @Test
    public void entityDomainDetailOverallHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/overallHttpResponseLatency error
     */
    @Test
    public void entityDomainDetailOverallHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/overallSslConLatency success
     */
    @Test
    public void entityDomainDetailOverallSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/overallSslConLatency error
     */
    @Test
    public void entityDomainDetailOverallSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/overallPacketLoss success
     */
    @Test
    public void entityDomainDetailOverallPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/overallPacketLoss error
     */
    @Test
    public void entityDomainDetailOverallPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/overallPacketRetrans success
     */
    @Test
    public void entityDomainDetailOverallPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/overallPacketRetrans error
     */
    @Test
    public void entityDomainDetailOverallPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIpEstablishlatency success
     */
    @Test
    public void entityDomainDetailServerIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIpEstablishlatency error
     */
    @Test
    public void entityDomainDetailServerIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIpHttpResponseLatency success
     */
    @Test
    public void entityDomainDetailServerIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIpHttpResponseLatency error
     */
    @Test
    public void entityDomainDetailServerIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIpSslConLatency success
     */
    @Test
    public void entityDomainDetailServerIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIpSslConLatency error
     */
    @Test
    public void entityDomainDetailServerIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIpEstablishlatency success
     */
    @Test
    public void entityDomainDetailClientIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIpEstablishlatency error
     */
    @Test
    public void entityDomainDetailClientIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIpHttpResponseLatency success
     */
    @Test
    public void entityDomainDetailClientIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIpHttpResponseLatency error
     */
    @Test
    public void entityDomainDetailClientIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIpSslConLatency success
     */
    @Test
    public void entityDomainDetailClientIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIpSslConLatency error
     */
    @Test
    public void entityDomainDetailClientIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketLoss_bak success
     */
    @Test
    public void entityDomainDetailServerIPPacketLossBak_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketLoss_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketLoss_bak error
     */
    @Test
    public void entityDomainDetailServerIPPacketLossBak_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketLoss_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketRetrans_bak success
     */
    @Test
    public void entityDomainDetailServerIPPacketRetransBak_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketRetrans_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/serverIPPacketRetrans_bak error
     */
    @Test
    public void entityDomainDetailServerIPPacketRetransBak_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/serverIPPacketRetrans_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketLoss_bak success
     */
    @Test
    public void entityDomainDetailClientIPPacketLossBak_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketLoss_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketLoss_bak error
     */
    @Test
    public void entityDomainDetailClientIPPacketLossBak_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketLoss_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketRetrans_bak success
     */
    @Test
    public void entityDomainDetailClientIPPacketRetransBak_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketRetrans_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/clientIPPacketRetrans_bak error
     */
    @Test
    public void entityDomainDetailClientIPPacketRetransBak_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/clientIPPacketRetrans_bak", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/overallEstablishlatency success
     */
    @Test
    public void entityAppDetailOverallEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/overallEstablishlatency error
     */
    @Test
    public void entityAppDetailOverallEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/overallHttpResponseLatency success
     */
    @Test
    public void entityAppDetailOverallHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/overallHttpResponseLatency error
     */
    @Test
    public void entityAppDetailOverallHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/overallSslConLatency success
     */
    @Test
    public void entityAppDetailOverallSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/overallSslConLatency error
     */
    @Test
    public void entityAppDetailOverallSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/overallPacketLoss success
     */
    @Test
    public void entityAppDetailOverallPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/overallPacketLoss error
     */
    @Test
    public void entityAppDetailOverallPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/overallPacketRetrans success
     */
    @Test
    public void entityAppDetailOverallPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/overallPacketRetrans error
     */
    @Test
    public void entityAppDetailOverallPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainEstablishlatency success
     */
    @Test
    public void entityAppDetailDomainEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainEstablishlatency error
     */
    @Test
    public void entityAppDetailDomainEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainHttpResponseLatency success
     */
    @Test
    public void entityAppDetailDomainHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainHttpResponseLatency error
     */
    @Test
    public void entityAppDetailDomainHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domainSslConLatency success
     */
    @Test
    public void entityAppDetailDomainSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domainSslConLatency error
     */
    @Test
    public void entityAppDetailDomainSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIpEstablishlatency success
     */
    @Test
    public void entityAppDetailServerIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIpEstablishlatency error
     */
    @Test
    public void entityAppDetailServerIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIpHttpResponseLatency success
     */
    @Test
    public void entityAppDetailServerIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIpHttpResponseLatency error
     */
    @Test
    public void entityAppDetailServerIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serverIpSslConLatency success
     */
    @Test
    public void entityAppDetailServerIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serverIpSslConLatency error
     */
    @Test
    public void entityAppDetailServerIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIpEstablishlatency success
     */
    @Test
    public void entityAppDetailClientIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIpEstablishlatency error
     */
    @Test
    public void entityAppDetailClientIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIpHttpResponseLatency success
     */
    @Test
    public void entityAppDetailClientIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIpHttpResponseLatency error
     */
    @Test
    public void entityAppDetailClientIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/clientIpSslConLatency success
     */
    @Test
    public void entityAppDetailClientIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/clientIpSslConLatency error
     */
    @Test
    public void entityAppDetailClientIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/basicInfo success
     */
    @Test
    public void entityAppDetailBasicInfo_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/basicInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/basicInfo error
     */
    @Test
    public void entityAppDetailBasicInfo_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/basicInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/domains success
     */
    @Test
    public void entityAppDetailDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/domains error
     */
    @Test
    public void entityAppDetailDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/serviceOverview success
     */
    @Test
    public void entityAppDetailServiceOverview_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/serviceOverview error
     */
    @Test
    public void entityAppDetailServiceOverview_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/ip/detail/linkVisual success
     */
    @Test
    public void entityIpDetailLinkVisual_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/ip/detail/linkVisual error
     */
    @Test
    public void entityIpDetailLinkVisual_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/ip/detail/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/app/detail/linkVisual success
     */
    @Test
    public void entityAppDetailLinkVisual_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/app/detail/linkVisual error
     */
    @Test
    public void entityAppDetailLinkVisual_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/app/detail/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/domain/detail/linkVisual success
     */
    @Test
    public void entityDomainDetailLinkVisual_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/domain/detail/linkVisual error
     */
    @Test
    public void entityDomainDetailLinkVisual_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/domain/detail/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/cryptcy/poolcount success
     */
    @Test
    public void cryptcyPoolcount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/poolcount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/machinecount success
     */
    @Test
    public void cryptcyMachinecount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/machinecount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/traffic success
     */
    @Test
    public void cryptcyTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/totalTraffic success
     */
    @Test
    public void cryptcyTotalTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/totalTraffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/eventStat success
     */
    @Test
    public void cryptcyEventStat_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/eventStat", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/eventMap success
     */
    @Test
    public void cryptcyEventMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/eventMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/activeCy success
     */
    @Test
    public void cryptcyActiveCy_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/activeCy", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/activePool success
     */
    @Test
    public void cryptcyActivePool_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/activePool", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/eventList success
     */
    @Test
    public void cryptcyEventList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/eventList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/institutions success
     */
    @Test
    public void cryptcyInstitutions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/institutions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/activeIP success
     */
    @Test
    public void cryptcyActiveIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/activeIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/index/total success
     */
    @Test
    public void entityIndexTotal_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/index/total", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/index/total error
     */
    @Test
    public void entityIndexTotal_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/index/total", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/index/new success
     */
    @Test
    public void entityIndexNew_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/index/new", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/index/new error
     */
    @Test
    public void entityIndexNew_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/index/new", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/index/active success
     */
    @Test
    public void entityIndexActive_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/index/active", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/index/active error
     */
    @Test
    public void entityIndexActive_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/index/active", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/filter/count success
     */
    @Test
    public void entityFilterCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/filter/top success
     */
    @Test
    public void entityFilterTop_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/top", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/filter/top error
     */
    @Test
    public void entityFilterTop_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/top", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/list/basic success
     */
    @Test
    public void entityListBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/list/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/list/traffic success
     */
    @Test
    public void entityListTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/list/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topAppCategories success
     */
    @Test
    public void trafficTopAppCategories_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topAppCategories", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topAppCategories error
     */
    @Test
    public void trafficTopAppCategories_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topAppCategories", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/appCategoriesList success
     */
    @Test
    public void trafficAppCategoriesList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appCategoriesList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/appCategoriesList error
     */
    @Test
    public void trafficAppCategoriesList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appCategoriesList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/appCategoryServerIpList success
     */
    @Test
    public void trafficAppCategoryServerIpList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appCategoryServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/appCategoryServerIpList error
     */
    @Test
    public void trafficAppCategoryServerIpList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appCategoryServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/topAppRisk success
     */
    @Test
    public void trafficTopAppRisk_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topAppRisk", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/topAppRisk error
     */
    @Test
    public void trafficTopAppRisk_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topAppRisk", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/appRiskList success
     */
    @Test
    public void trafficAppRiskList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appRiskList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/appRiskList error
     */
    @Test
    public void trafficAppRiskList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appRiskList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/traffic/appRiskServerIpList success
     */
    @Test
    public void trafficAppRiskServerIpList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appRiskList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/traffic/appRiskServerIpList error
     */
    @Test
    public void trafficAppRiskServerIpList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/appRiskServerIpList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/cryptcy/trafficPercentage success
     */
    @Test
    public void cryptcyTrafficPercentage_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/trafficPercentage", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/basic success
     */
    @Test
    public void entityDetailDomainBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/basic error
     */
    @Test
    public void entityDetailDomainBasic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/whois success
     */
    @Test
    public void entityDetailDomainWhois_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/whois", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/whois error
     */
    @Test
    public void entityDetailDomainWhois_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/whois", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/basic success
     */
    @Test
    public void entityDetailIpBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/basic error
     */
    @Test
    public void entityDetailIpBasic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/basic success
     */
    @Test
    public void entityDetailAppBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/basic error
     */
    @Test
    public void entityDetailAppBasic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/clientIpCount success
     */
    @Test
    public void entityDetailIpClientIpCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/clientIpCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/clientIpCount error
     */
    @Test
    public void entityDetailIpClientIpCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/clientIpCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/serverIpCount success
     */
    @Test
    public void entityDetailIpServerIpCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/serverIpCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/serverIpCount error
     */
    @Test
    public void entityDetailIpServerIpCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/serverIpCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appCount success
     */
    @Test
    public void entityDetailIpAppCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appCount error
     */
    @Test
    public void entityDetailIpAppCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainCount success
     */
    @Test
    public void entityDetailIpDomainCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainCount error
     */
    @Test
    public void entityDetailIpDomainCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/sessions success
     */
    @Test
    public void entityDetailIpSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/sessions error
     */
    @Test
    public void entityDetailIpSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/packets success
     */
    @Test
    public void entityDetailIpPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/packets error
     */
    @Test
    public void entityDetailIpPackets_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/bytes success
     */
    @Test
    public void entityDetailIpBytes_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/bytes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/bytes error
     */
    @Test
    public void entityDetailIpBytes_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/bytes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/activeClientIP success
     */
    @Test
    public void entityDetailIpActiveClientIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/activeClientIP error
     */
    @Test
    public void entityDetailIpActiveClientIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/topDomains success
     */
    @Test
    public void entityDetailIpTopDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/topDomains error
     */
    @Test
    public void entityDetailIpTopDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/topAPP success
     */
    @Test
    public void entityDetailIpTopAPP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/topAPP error
     */
    @Test
    public void entityDetailIpTopAPP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/serviceOverview success
     */
    @Test
    public void entityDetailIpServiceOverview_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/serviceOverview error
     */
    @Test
    public void entityDetailIpServiceOverview_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/overallEstablishlatency success
     */
    @Test
    public void entityDetailIpOverallEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/overallEstablishlatency error
     */
    @Test
    public void entityDetailIpOverallEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/overallHttpResponseLatency success
     */
    @Test
    public void entityDetailIpOverallHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/overallHttpResponseLatency error
     */
    @Test
    public void entityDetailIpOverallHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/overallSslConLatency success
     */
    @Test
    public void entityDetailIpOverallSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/overallSslConLatency error
     */
    @Test
    public void entityDetailIpOverallSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/overallPacketLoss success
     */
    @Test
    public void entityDetailIpOverallPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/overallPacketLoss error
     */
    @Test
    public void entityDetailIpOverallPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/overallPacketRetrans success
     */
    @Test
    public void entityDetailIpOverallPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/overallPacketRetrans error
     */
    @Test
    public void entityDetailIpOverallPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainEstablishlatency success
     */
    @Test
    public void entityDetailIpDomainEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainEstablishlatency error
     */
    @Test
    public void entityDetailIpDomainEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainHttpResponseLatency success
     */
    @Test
    public void entityDetailIpDomainHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainHttpResponseLatency error
     */
    @Test
    public void entityDetailIpDomainHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainSslConLatency success
     */
    @Test
    public void entityDetailIpDomainSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainSslConLatency error
     */
    @Test
    public void entityDetailIpDomainSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainPacketLoss success
     */
    @Test
    public void entityDetailIpDomainPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainPacketLoss error
     */
    @Test
    public void entityDetailIpDomainPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainPacketRetrans success
     */
    @Test
    public void entityDetailIpDomainPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainPacketRetrans error
     */
    @Test
    public void entityDetailIpDomainPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appEstablishlatency success
     */
    @Test
    public void entityDetailIpAppEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appEstablishlatency error
     */
    @Test
    public void entityDetailIpAppEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appHttpResponseLatency success
     */
    @Test
    public void entityDetailIpAppHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appHttpResponseLatency error
     */
    @Test
    public void entityDetailIpAppHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appSslConLatency success
     */
    @Test
    public void entityDetailIpAppSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appSslConLatency error
     */
    @Test
    public void entityDetailIpAppSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appPacketLoss success
     */
    @Test
    public void entityDetailIpAppPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appPacketLoss error
     */
    @Test
    public void entityDetailIpAppPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appPacketRetrans success
     */
    @Test
    public void entityDetailIpAppPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appPacketRetrans error
     */
    @Test
    public void entityDetailIpAppPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/linkVisual success
     */
    @Test
    public void entityDetailIpLinkVisual_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/linkVisual error
     */
    @Test
    public void entityDetailIpLinkVisual_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/trafficMap success
     */
    @Test
    public void entityDetailIpTrafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/trafficMap error
     */
    @Test
    public void entityDetailIpTrafficMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/count success
     */
    @Test
    public void entityDetailDomainCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/count error
     */
    @Test
    public void entityDetailDomainCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/sessions success
     */
    @Test
    public void entityDetailDomainSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/sessions error
     */
    @Test
    public void entityDetailDomainSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/packets success
     */
    @Test
    public void entityDetailDomainPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/packets error
     */
    @Test
    public void entityDetailDomainPackets_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/bytes success
     */
    @Test
    public void entityDetailDomainBytes_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/bytes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/bytes error
     */
    @Test
    public void entityDetailDomainBytes_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/bytes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/trafficMap success
     */
    @Test
    public void entityDetailDomainTrafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/trafficMap error
     */
    @Test
    public void entityDetailDomainTrafficMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/activeServerIP success
     */
    @Test
    public void entityDetailDomainActiveServerIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/activeServerIP error
     */
    @Test
    public void entityDetailDomainActiveServerIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/topApp success
     */
    @Test
    public void entityDetailDomainTopApp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/topApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/topApp error
     */
    @Test
    public void entityDetailDomainTopApp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/topApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/serviceOverview success
     */
    @Test
    public void entityDetailDomainServiceOverview_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/serviceOverview error
     */
    @Test
    public void entityDetailDomainServiceOverview_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/overallEstablishlatency success
     */
    @Test
    public void entityDetailDomainOverallEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/overallEstablishlatency error
     */
    @Test
    public void entityDetailDomainOverallEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/overallHttpResponseLatency success
     */
    @Test
    public void entityDetailDomainOverallHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/overallHttpResponseLatency error
     */
    @Test
    public void entityDetailDomainOverallHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/overallSslConLatency success
     */
    @Test
    public void entityDetailDomainOverallSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/overallSslConLatency error
     */
    @Test
    public void entityDetailDomainOverallSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/overallPacketLoss success
     */
    @Test
    public void entityDetailDomainOverallPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/overallPacketLoss error
     */
    @Test
    public void entityDetailDomainOverallPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/overallPacketRetrans success
     */
    @Test
    public void entityDetailDomainOverallPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/overallPacketRetrans error
     */
    @Test
    public void entityDetailDomainOverallPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpEstablishlatency success
     */
    @Test
    public void entityDetailDomainServerIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpEstablishlatency error
     */
    @Test
    public void entityDetailDomainServerIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpHttpResponseLatency success
     */
    @Test
    public void entityDetailDomainServerIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpHttpResponseLatency error
     */
    @Test
    public void entityDetailDomainServerIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpSslConLatency success
     */
    @Test
    public void entityDetailDomainServerIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpSslConLatency error
     */
    @Test
    public void entityDetailDomainServerIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpPacketLoss success
     */
    @Test
    public void entityDetailDomainServerIpPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/serverIpPacketLoss error
     */
    @Test
    public void entityDetailDomainServerIpPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/serverIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpEstablishlatency success
     */
    @Test
    public void entityDetailDomainClientIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpEstablishlatency error
     */
    @Test
    public void entityDetailDomainClientIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpSslConLatency success
     */
    @Test
    public void entityDetailDomainClientIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpSslConLatency error
     */
    @Test
    public void entityDetailDomainClientIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpPacketLoss success
     */
    @Test
    public void entityDetailDomainClientIpPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpPacketLoss error
     */
    @Test
    public void entityDetailDomainClientIpPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpPacketRetrans success
     */
    @Test
    public void entityDetailDomainClientIpPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/clientIpPacketRetrans error
     */
    @Test
    public void entityDetailDomainClientIpPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/clientIpPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/linkVisual success
     */
    @Test
    public void entityDetailDomainLinkVisual_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/linkVisual error
     */
    @Test
    public void entityDetailDomainLinkVisual_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/dnsInfo success
     */
    @Test
    public void entityDetailOverviewIpDnsInfo_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/dnsInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/count success
     */
    @Test
    public void entityDetailAppCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/count error
     */
    @Test
    public void entityDetailAppCount_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/sessions success
     */
    @Test
    public void entityDetailAppSessions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/sessions error
     */
    @Test
    public void entityDetailAppSessions_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/sessions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/packets success
     */
    @Test
    public void entityDetailAppPackets_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/packets error
     */
    @Test
    public void entityDetailAppPackets_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/packets", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/bytes success
     */
    @Test
    public void entityDetailAppBytes_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/bytes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/bytes error
     */
    @Test
    public void entityDetailAppBytes_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/bytes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/trafficMap success
     */
    @Test
    public void entityDetailAppTrafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/trafficMap error
     */
    @Test
    public void entityDetailAppTrafficMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/trafficMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/activeServerIP success
     */
    @Test
    public void entityDetailAppActiveServerIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/activeServerIP error
     */
    @Test
    public void entityDetailAppActiveServerIP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/topDomains success
     */
    @Test
    public void entityDetailAppTopDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/topDomains error
     */
    @Test
    public void entityDetailAppTopDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/serviceOverview success
     */
    @Test
    public void entityDetailAppServiceOverview_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/serviceOverview error
     */
    @Test
    public void entityDetailAppServiceOverview_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serviceOverview", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/overallEstablishlatency success
     */
    @Test
    public void entityDetailAppOverallEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/overallEstablishlatency error
     */
    @Test
    public void entityDetailAppOverallEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/overallHttpResponseLatency success
     */
    @Test
    public void entityDetailAppOverallHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/overallHttpResponseLatency error
     */
    @Test
    public void entityDetailAppOverallHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/overallSslConLatency success
     */
    @Test
    public void entityDetailAppOverallSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/overallSslConLatency error
     */
    @Test
    public void entityDetailAppOverallSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/overallPacketLoss success
     */
    @Test
    public void entityDetailAppOverallPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/overallPacketLoss error
     */
    @Test
    public void entityDetailAppOverallPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/overallPacketRetrans success
     */
    @Test
    public void entityDetailAppOverallPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/overallPacketRetrans error
     */
    @Test
    public void entityDetailAppOverallPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/overallPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/serverIpEstablishlatency success
     */
    @Test
    public void entityDetailAppServerIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/serverIpEstablishlatency error
     */
    @Test
    public void entityDetailAppServerIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/serverIpHttpResponseLatency success
     */
    @Test
    public void entityDetailAppServerIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/serverIpHttpResponseLatency error
     */
    @Test
    public void entityDetailAppServerIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/serverIpSslConLatency success
     */
    @Test
    public void entityDetailAppServerIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/serverIpSslConLatency error
     */
    @Test
    public void entityDetailAppServerIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/serverIpPacketLoss success
     */
    @Test
    public void entityDetailAppServerIpPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/serverIpPacketLoss error
     */
    @Test
    public void entityDetailAppServerIpPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/serverIpPacketRetrans success
     */
    @Test
    public void entityDetailAppServerIpPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/serverIpPacketRetrans error
     */
    @Test
    public void entityDetailAppServerIpPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/serverIpPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/clientIpEstablishlatency success
     */
    @Test
    public void entityDetailAppClientIpEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/clientIpEstablishlatency error
     */
    @Test
    public void entityDetailAppClientIpEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/clientIpHttpResponseLatency success
     */
    @Test
    public void entityDetailAppClientIpHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/clientIpHttpResponseLatency error
     */
    @Test
    public void entityDetailAppClientIpHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/clientIpSslConLatency success
     */
    @Test
    public void entityDetailAppClientIpSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/clientIpSslConLatency error
     */
    @Test
    public void entityDetailAppClientIpSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/clientIpPacketLoss success
     */
    @Test
    public void entityDetailAppClientIpPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/clientIpPacketLoss error
     */
    @Test
    public void entityDetailAppClientIpPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/clientIpPacketRetrans success
     */
    @Test
    public void entityDetailAppClientIpPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/clientIpPacketRetrans error
     */
    @Test
    public void entityDetailAppClientIpPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/clientIpPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/domainEstablishlatency success
     */
    @Test
    public void entityDetailAppDomainEstablishlatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainEstablishlatency error
     */
    @Test
    public void entityDetailAppDomainEstablishlatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainEstablishlatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/domainHttpResponseLatency success
     */
    @Test
    public void entityDetailAppDomainHttpResponseLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainHttpResponseLatency error
     */
    @Test
    public void entityDetailAppDomainHttpResponseLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainHttpResponseLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/domainSslConLatency success
     */
    @Test
    public void entityDetailAppDomainSslConLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainSslConLatency error
     */
    @Test
    public void entityDetailAppDomainSslConLatency_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainSslConLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/domainPacketLoss success
     */
    @Test
    public void entityDetailAppDomainPacketLoss_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainPacketLoss error
     */
    @Test
    public void entityDetailAppDomainPacketLoss_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainPacketLoss", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/domainPacketRetrans success
     */
    @Test
    public void entityDetailAppDomainPacketRetrans_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainPacketRetrans error
     */
    @Test
    public void entityDetailAppDomainPacketRetrans_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainPacketRetrans", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/linkVisual success
     */
    @Test
    public void entityDetailAppLinkVisual_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/linkVisual error
     */
    @Test
    public void entityDetailAppLinkVisual_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/linkVisual", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/traffic success
     */
    @Test
    public void entityDetailOverviewAppTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/traffic error
     */
    @Test
    public void entityDetailOverviewAppTraffic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/relatedServerIp success
     */
    @Test
    public void entityDetailOverviewAppRelatedServerIp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/relatedServerIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/relatedServerIp error
     */
    @Test
    public void entityDetailOverviewAppRelatedServerIp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/relatedServerIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/performanceEvent success
     */
    @Test
    public void entityDetailOverviewAppPerformanceEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/performanceEvent error
     */
    @Test
    public void entityDetailOverviewAppPerformanceEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/securityEvent success
     */
    @Test
    public void entityDetailOverviewAppSecurityEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/securityEvent error
     */
    @Test
    public void entityDetailOverviewAppSecurityEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/basic success
     */
    @Test
    public void entityDetailOverviewDomainBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/basic error
     */
    @Test
    public void entityDetailOverviewDomainBasic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/traffic success
     */
    @Test
    public void entityDetailOverviewDomainTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/traffic error
     */
    @Test
    public void entityDetailOverviewDomainTraffic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/relatedApp success
     */
    @Test
    public void entityDetailOverviewDomainRelatedApp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/relatedApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/relatedApp error
     */
    @Test
    public void entityDetailOverviewDomainRelatedApp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/relatedApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity success
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity error
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/linkIn success
     */
    @Test
    public void entityDetailOverviewDomainLinkIn_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/linkIn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/linkIn error
     */
    @Test
    public void entityDetailOverviewDomainLinkIn_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/linkIn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/linkOut success
     */
    @Test
    public void entityDetailOverviewDomainLinkOut_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/linkOut", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/linkOut error
     */
    @Test
    public void entityDetailOverviewDomainLinkOut_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/linkOut", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/performanceEvent success
     */
    @Test
    public void entityDetailOverviewDomainPerformanceEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/performanceEvent error
     */
    @Test
    public void entityDetailOverviewDomainPerformanceEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/securityEvent success
     */
    @Test
    public void entityDetailOverviewDomainSecurityEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/securityEvent error
     */
    @Test
    public void entityDetailOverviewDomainSecurityEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/traffic success
     */
    @Test
    public void entityDetailOverviewIpTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/traffic error
     */
    @Test
    public void entityDetailOverviewIpTraffic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/relatedDomain success
     */
    @Test
    public void entityDetailOverviewIpRelatedDomain_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/relatedDomain", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/relatedDomain error
     */
    @Test
    public void entityDetailOverviewIpRelatedDomain_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/relatedDomain", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/performanceEvent success
     */
    @Test
    public void entityDetailOverviewIpPerformanceEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/performanceEvent error
     */
    @Test
    public void entityDetailOverviewIpPerformanceEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/securityEvent success
     */
    @Test
    public void entityDetailOverviewIpSecurityEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/securityEvent error
     */
    @Test
    public void entityDetailOverviewIpSecurityEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/list/total success
     */
    @Test
    public void entityListTotal_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/list/total", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/countTrend success
     */
    @Test
    public void entityDetailAppCountTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/countTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/countTrend error
     */
    @Test
    public void entityDetailAppCountTrend_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/countTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/domain/countTrend success
     */
    @Test
    public void entityDetailDomainCountTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/countTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/countTrend error
     */
    @Test
    public void entityDetailDomainCountTrend_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/countTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/clientIpCountTrend success
     */
    @Test
    public void entityDetailIpClientIpCountTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/clientIpCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/clientIpCountTrend error
     */
    @Test
    public void entityDetailIpClientIpCountTrend_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/clientIpCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/serverIpCountTrend success
     */
    @Test
    public void entityDetailIpServerIpCountTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/serverIpCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/serverIpCountTrend error
     */
    @Test
    public void entityDetailIpServerIpCountTrend_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/serverIpCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/appCountTrend success
     */
    @Test
    public void entityDetailIpAppCountTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/appCountTrend error
     */
    @Test
    public void entityDetailIpAppCountTrend_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/appCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainCountTrend success
     */
    @Test
    public void entityDetailIpDomainCountTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainCountTrend error
     */
    @Test
    public void entityDetailIpDomainCountTrend_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainCountTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/linkIn success
     */
    @Test
    public void entityDetailOverviewAppLinkIn_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/linkIn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/linkIn error
     */
    @Test
    public void entityDetailOverviewAppLinkIn_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/linkIn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/linkIn success
     */
    @Test
    public void entityDetailOverviewIpLinkIn_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/linkIn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/linkIn error
     */
    @Test
    public void entityDetailOverviewIpLinkIn_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/linkIn", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/linkOut success
     */
    @Test
    public void entityDetailOverviewAppLinkOut_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/linkOut", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/linkOut error
     */
    @Test
    public void entityDetailOverviewAppLinkOut_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/linkOut", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/linkOut success
     */
    @Test
    public void entityDetailOverviewIpLinkOut_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/linkOut", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/linkOut error
     */
    @Test
    public void entityDetailOverviewIpLinkOut_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/linkOut", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity/2 success
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity2_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity/2", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity/2 error
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity2_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity/2", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity/3 success
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity3_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity/3", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity/3 error
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity3_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity/3", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity/145 success
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity145_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity/145", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/networkQuantity/145 error
     */
    @Test
    public void entityDetailOverviewDomainNetworkQuantity145_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/networkQuantity/145", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/networkQuantity/2 success
     */
    @Test
    public void entityDetailOverviewAppNetworkQuantity2_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/networkQuantity/2", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/networkQuantity/2 error
     */
    @Test
    public void entityDetailOverviewAppNetworkQuantity2_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/networkQuantity/2", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/networkQuantity/3 success
     */
    @Test
    public void entityDetailOverviewAppNetworkQuantity3_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/networkQuantity/3", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/networkQuantity/3 error
     */
    @Test
    public void entityDetailOverviewAppNetworkQuantity3_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/networkQuantity/3", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/networkQuantity/145 success
     */
    @Test
    public void entityDetailOverviewAppNetworkQuantity145_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/networkQuantity/145", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/networkQuantity/145 error
     */
    @Test
    public void entityDetailOverviewAppNetworkQuantity145_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/networkQuantity/145", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/networkQuantity/2 success
     */
    @Test
    public void entityDetailOverviewIpNetworkQuantity2_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/networkQuantity/2", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/networkQuantity/2 error
     */
    @Test
    public void entityDetailOverviewIpNetworkQuantity2_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/networkQuantity/2", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/networkQuantity/3 success
     */
    @Test
    public void entityDetailOverviewIpNetworkQuantity3_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/networkQuantity/3", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/networkQuantity/3 error
     */
    @Test
    public void entityDetailOverviewIpNetworkQuantity3_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/networkQuantity/3", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/networkQuantity/145 success
     */
    @Test
    public void entityDetailOverviewIpNetworkQuantity145_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/networkQuantity/145", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/networkQuantity/145 error
     */
    @Test
    public void entityDetailOverviewIpNetworkQuantity145_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/networkQuantity/145", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/ip/relatedApp success
     */
    @Test
    public void entityDetailOverviewIpRelatedApp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/relatedApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/relatedApp error
     */
    @Test
    public void entityDetailOverviewIpRelatedApp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/relatedApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/domain/relatedServerIp success
     */
    @Test
    public void entityDetailOverviewDomainRelatedServerIp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/relatedServerIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/domain/relatedServerIp error
     */
    @Test
    public void entityDetailOverviewDomainRelatedServerIp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/domain/relatedServerIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/overview/app/relatedDomain success
     */
    @Test
    public void entityDetailOverviewAppRelatedDomain_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/relatedDomain", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/app/relatedDomain error
     */
    @Test
    public void entityDetailOverviewAppRelatedDomain_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/app/relatedDomain", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/detection/security/filter/severity success
     */
    @Test
    public void detectionSecurityFilterSeverity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/severity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/filter/securityType success
     */
    @Test
    public void detectionSecurityFilterSecurityType_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/securityType", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/filter/victimIp success
     */
    @Test
    public void detectionSecurityFilterVictimIp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/victimIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/filter/victimLocation success
     */
    @Test
    public void detectionSecurityFilterVictimLocation_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/victimLocation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/filter/offenderIp success
     */
    @Test
    public void detectionSecurityFilterOffenderIp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/offenderIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/filter/offenderLocation success
     */
    @Test
    public void detectionSecurityFilterOffenderLocation_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/offenderLocation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/list/basic success
     */
    @Test
    public void detectionSecurityListBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/list/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/list/count success
     */
    @Test
    public void detectionSecurityListCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/list/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/detail/overview/basic success
     */
    @Test
    public void detectionSecurityDetailOverviewBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/detail/overview/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/detail/overview/event success
     */
    @Test
    public void detectionSecurityDetailOverviewEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/detail/overview/event", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/security/detail/overview/event error
     */
    @Test
    public void detectionSecurityDetailOverviewEvent_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/detail/overview/event", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/detection/security/filter/severityTrend success
     */
    @Test
    public void detectionSecurityFilterSeverityTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/security/filter/severityTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/filter/severityTrend success
     */
    @Test
    public void detectionPerformanceFilterSeverityTrend_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/filter/severityTrend", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/filter/severity success
     */
    @Test
    public void detectionPerformanceFilterSeverity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/filter/severity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/filter/eventType success
     */
    @Test
    public void detectionPerformanceFilterEventType_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/filter/eventType", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/list/basic success
     */
    @Test
    public void detectionPerformanceListBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/list/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/detail/overview/basic success
     */
    @Test
    public void detectionPerformanceDetailOverviewBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/detail/overview/basic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/list/count success
     */
    @Test
    public void detectionPerformanceListCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/list/count", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/rtdns success
     */
    @Test
    public void dnsRtdns_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/rtdns", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/tldns success
     */
    @Test
    public void dnsTldns_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/tldns", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/adns success
     */
    @Test
    public void dnsAdns_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/adns", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/oprdns success
     */
    @Test
    public void dnsOprdns_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/oprdns", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/os success
     */
    @Test
    public void dnsOs_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/os", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/software success
     */
    @Test
    public void dnsSoftware_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/software", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/org success
     */
    @Test
    public void dnsOrg_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/org", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dnssecSupport success
     */
    @Test
    public void dnsDnssecSupport_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dnssecSupport", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/ipv6Support success
     */
    @Test
    public void dnsIpv6Support_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/ipv6Support", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/encryptedProtocolSupport success
     */
    @Test
    public void dnsEncryptedProtocolSupport_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/encryptedProtocolSupport", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dohCount success
     */
    @Test
    public void dnsDohCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dohCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dotCount success
     */
    @Test
    public void dnsDotCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dotCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/map success
     */
    @Test
    public void dnsMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/map", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/map error
     */
    @Test
    public void dnsMap_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/map", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/dns/queryRate success
     */
    @Test
    public void dnsQueryRate_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/queryRate", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dohInquiries success
     */
    @Test
    public void dnsDohInquiries_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dohInquiries", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dotInquiries success
     */
    @Test
    public void dnsDotInquiries_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dotInquiries", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/activeClientIp success
     */
    @Test
    public void dnsActiveClientIp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/activeClientIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/activeClientIp error
     */
    @Test
    public void dnsActiveClientIp_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/activeClientIp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/dns/activeDomainServer success
     */
    @Test
    public void dnsActiveDomainServer_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/activeDomainServer", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/activeDomainServer error
     */
    @Test
    public void dnsActiveDomainServer_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/activeDomainServer", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/dns/resolveDomain success
     */
    @Test
    public void dnsResolveDomain_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/resolveDomain", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/resolveDomain error
     */
    @Test
    public void dnsResolveDomain_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/resolveDomain", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/dns/averageResolveLatency success
     */
    @Test
    public void dnsAverageResolveLatency_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/averageResolveLatency", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/responseFailureRate success
     */
    @Test
    public void dnsResponseFailureRate_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/responseFailureRate", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/alarmInfo success
     */
    @Test
    public void dnsAlarmInfo_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/alarmInfo", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/alarmInfoCount success
     */
    @Test
    public void dnsAlarmInfoCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/alarmInfoCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dnsCount success
     */
    @Test
    public void dnsCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dnsCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/port success
     */
    @Test
    public void entityDetailIpPort_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/port", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/port error
     */
    @Test
    public void entityDetailIpPort_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/port", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/detection/performance/detail/overview/metric success
     */
    @Test
    public void detectionPerformanceDetailOverviewMetric_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/detail/overview/metric", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/filter/activeEntity success
     */
    @Test
    public void detectionPerformanceFilterActiveEntity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/filter/activeEntity", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domains success
     */
    @Test
    public void entityDetailIpDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domains error
     */
    @Test
    public void entityDetailIpDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/ip/domainByCategory success
     */
    @Test
    public void entityDetailIpDomainByCategory_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainByCategory", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainByCategory error
     */
    @Test
    public void entityDetailIpDomainByCategory_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainByCategory", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainByReputation success
     */
    @Test
    public void entityDetailIpDomainByReputation_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainByReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/domainByReputation error
     */
    @Test
    public void entityDetailIpDomainByReputation_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/domainByReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domains success
     */
    @Test
    public void entityDetailAppDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domains error
     */
    @Test
    public void entityDetailAppDomains_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/detail/app/domainByCategory success
     */
    @Test
    public void entityDetailAppDomainByCategory_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainByCategory", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainByCategory error
     */
    @Test
    public void entityDetailAppDomainByCategory_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainByCategory", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainByReputation success
     */
    @Test
    public void entityDetailAppDomainByReputation_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainByReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/domainByReputation error
     */
    @Test
    public void entityDetailAppDomainByReputation_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/domainByReputation", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/securityEvent success
     */
    @Test
    public void entityDetailIpSecurityEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/securityEventCount success
     */
    @Test
    public void entityDetailIpSecurityEventCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/securityEventCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/performanceEvent success
     */
    @Test
    public void entityDetailIpPerformanceEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/ip/performanceEventCount success
     */
    @Test
    public void entityDetailIpPerformanceEventCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/ip/performanceEventCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/securityEvent success
     */
    @Test
    public void entityDetailAppSecurityEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/securityEventCount success
     */
    @Test
    public void entityDetailAppSecurityEventCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/securityEventCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/performanceEvent success
     */
    @Test
    public void entityDetailAppPerformanceEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/app/performanceEventCount success
     */
    @Test
    public void entityDetailAppPerformanceEventCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/app/performanceEventCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/securityEvent success
     */
    @Test
    public void entityDetailDomainSecurityEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/securityEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/securityEventCount success
     */
    @Test
    public void entityDetailDomainSecurityEventCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/securityEventCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/performanceEvent success
     */
    @Test
    public void entityDetailDomainPerformanceEvent_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/performanceEvent", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/performanceEventCount success
     */
    @Test
    public void entityDetailDomainPerformanceEventCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/performanceEventCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/overallDnsTraffic success
     */
    @Test
    public void dnsOverallDnsTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/overallDnsTraffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/dnsTraffic success
     */
    @Test
    public void dnsDnsTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/dnsTraffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/overview/ip/dnsQueryRate success
     */
    @Test
    public void entityDetailOverviewIpDnsQueryRate_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/dnsQueryRate", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/detail/overview/metric/dnsError success
     */
    @Test
    public void detectionPerformanceDetailOverviewMetricDnsError_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/detail/overview/metric/dnsError", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/detail/overview/metric/httpError success
     */
    @Test
    public void detectionPerformanceDetailOverviewMetricHttpError_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/detail/overview/metric/httpError", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/detection/performance/detail/overview/metric/highDnsResponseTime success
     */
    @Test
    public void detectionPerformanceDetailOverviewMetricHighDnsResponseTime_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/detail/overview/metric/highDnsResponseTime", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/rtMirrors success
     */
    @Test
    public void dnsRtMirrors_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/rtMirrors", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/rtMirrorsCount success
     */
    @Test
    public void dnsRtMirrorsCount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/rtMirrorsCount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/dns/fwdns success
     */
    @Test
    public void dnsFwdns_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/dns/fwdns", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/filter/count/dns/role success
     */
    @Test
    public void entityFilterCountDnsRole_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/count/dns/role", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/filter/count/dns/role error
     */
    @Test
    public void entityFilterCountDnsRole_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/count/dns/role", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * GET /interface/entity/filter/count/dns/others success
     */
    @Test
    public void entityFilterCountDnsOthers_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/count/dns/others", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/filter/count/dns/others error
     */
    @Test
    public void entityFilterCountDnsOthers_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/count/dns/others", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
