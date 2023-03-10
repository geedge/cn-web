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
 * @Date: 2023/02/23/15:34
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class DnsTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/DnsTestParameter.json";
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

}
