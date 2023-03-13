package com.net.geedge.modules.galaxy;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualChart;
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
 * @Date: 2023/02/23/15:02
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class TrafficTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/TrafficTestParameter.json";
    }

    /**
     * GET /interface/traffic/map success
     */
    /*@Test
    public void trafficMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/map", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

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
    /*@Test
    public void trafficActiveClientIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeClientIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

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
    /*@Test
    public void trafficActiveServerIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/activeServerIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

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
    /*@Test
    public void trafficTopDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topDomains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

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
    /*@Test
    public void trafficTopApp_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/traffic/topApp", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

}
