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
 * @Date: 2023/02/23/15:30
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class DetectionTest extends BaseTest<Map> {

    @Override
    protected String jsonIndex() {
        return "galaxy/DetectionTestParameter.json";
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

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
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
     * GET /interface/detection/performance/filter/activeEntity success
     */
    @Test
    public void detectionPerformanceFilterActiveEntity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/detection/performance/filter/activeEntity", queryParam);

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

}
