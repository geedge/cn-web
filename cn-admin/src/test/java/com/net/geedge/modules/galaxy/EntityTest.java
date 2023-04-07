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
 *   Entity
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class EntityTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/EntityTestParameter.json";
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/entity/detail/domain/topAPP success
     */
    @Test
    public void entityDetailDomainTopAPP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/topAPP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/entity/detail/domain/topAPP error
     */
    @Test
    public void entityDetailDomainTopAPP_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/domain/topAPP", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/entity/detail/overview/ip/dnsQueryRate success
     */
    @Test
    public void entityDetailOverviewIpDnsQueryRate_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/detail/overview/ip/dnsQueryRate", queryParam);

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
     * GET /interface/entity/filter/count/dns/others success
     */
    @Test
    public void entityFilterCountDnsOthers_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/entity/filter/count/dns/others", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

}
