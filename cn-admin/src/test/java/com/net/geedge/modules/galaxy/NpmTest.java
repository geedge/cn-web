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
 * @Date: 2023/02/23/15:08
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NpmTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/NpmTestParameter.json";
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

}
