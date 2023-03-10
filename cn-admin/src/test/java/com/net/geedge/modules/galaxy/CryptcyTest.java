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
 * @Date: 2023/02/23/15:26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class CryptcyTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/CryptcyTestParameter.json";
    }

    /**
     * GET /interface/cryptcy/poolcount success
     */
    /*@Test
    public void cryptcyPoolcount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/poolcount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/machinecount success
     */
    /*@Test
    public void cryptcyMachinecount_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/machinecount", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/traffic success
     */
    /*@Test
    public void cryptcyTraffic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/traffic", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

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
     * GET /interface/cryptcy/totalTraffic error
     */
    @Test
    public void cryptcyTotalTraffic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/totalTraffic", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /interface/cryptcy/eventStat success
     */
    /*@Test
    public void cryptcyEventStat_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/eventStat", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/eventMap success
     */
    /*@Test
    public void cryptcyEventMap_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/eventMap", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/activeCy success
     */
    /*@Test
    public void cryptcyActiveCy_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/activeCy", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/activePool success
     */
    /*@Test
    public void cryptcyActivePool_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/activePool", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/eventList success
     */
    /*@Test
    public void cryptcyEventList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/eventList", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/institutions success
     */
    /*@Test
    public void cryptcyInstitutions_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/institutions", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/activeIP success
     */
    /*@Test
    public void cryptcyActiveIP_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/activeIP", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/trafficPercentage success
     */
    /*@Test
    public void cryptcyTrafficPercentage_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/trafficPercentage", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /interface/cryptcy/trafficPercentage error
     */
    /*@Test
    public void cryptcyTrafficPercentage_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/cryptcy/trafficPercentage", queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }*/
}
