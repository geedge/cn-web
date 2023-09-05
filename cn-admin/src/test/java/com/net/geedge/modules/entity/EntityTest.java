package com.net.geedge.modules.entity;

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
 * @Date: 2023/08/04/9:37
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class EntityTest  extends BaseTest<Map> {
    @Override
    protected String jsonIndex() {
        return "entity/EntityTestParameter.json";
    }

    /**
     * GET /v1/entity/detail/basic/{entityType} success
     */
    @Test
    public void entityDetailBasic_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/basic/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/basic/{entityType} error
     */
    @Test
    public void entityDetailBasic_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/basic/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/kb/intelligence/tag/{entityType} success
     */
    @Test
    public void entityDetailKbIntelligenceTag_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/kb/intelligence/tag/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/kb/intelligence/tag/{entityType} error
     */
    @Test
    public void entityDetailKbIntelligenceTag_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/kb/intelligence/tag/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/traffic/throughput/{entityType} success
     */
    @Test
    public void entityDetailTrafficThroughput_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/traffic/throughput/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/traffic/throughput/{entityType} error
     */
    @Test
    public void entityDetailTrafficThroughput_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/traffic/throughput/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/kb/intelligence/list/{entityType} success
     */
    @Test
    public void entityDetailKbIntelligenceList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/kb/intelligence/list/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/kb/intelligence/list/{entityType} error
     */
    @Test
    public void entityDetailKbIntelligenceList_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/kb/intelligence/list/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/event/security/{entityType} success
     */
    @Test
    public void entityDetailEventSecurity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/event/security/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/event/security/{entityType} error
     */
    @Test
    public void entityDetailEventSecurity_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/event/security/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/event/performance/{entityType} success
     */
    @Test
    public void entityDetailEventPerformance_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/event/performance/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/event/performance/{entityType} error
     */
    @Test
    public void entityDetailEventPerformance_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/entity/detail/event/performance/%s", queryParam.get("entityType")), queryParam);

        UTUtil.assertNotEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/ip/relate/ports success
     */
    @Test
    public void entityDetailIpRelatePorts_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/ip/relate/ports", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/ip/relate/domains success
     */
    @Test
    public void entityDetailIpRelateDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/ip/relate/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/ip/relate/apps success
     */
    @Test
    public void entityDetailIpRelateApps_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/ip/relate/apps", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/app/relate/ports success
     */
    @Test
    public void entityDetailAppRelatePorts_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/app/relate/ports", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/app/relate/domains success
     */
    @Test
    public void entityDetailAppRelateDomains_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/app/relate/domains", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/app/relate/ips success
     */
    @Test
    public void entityDetailAppRelateIps_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/app/relate/ips", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/domain/relate/ports success
     */
    @Test
    public void entityDetailDomainRelatePorts_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/domain/relate/ports", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/domain/relate/apps success
     */
    @Test
    public void entityDetailDomainRelateApps_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/domain/relate/apps", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/domain/relate/fqdns success
     */
    @Test
    public void entityDetailDomainRelateFqdns_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/domain/relate/fqdns", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/entity/detail/domain/relate/ips success
     */
    @Test
    public void entityDetailDomainRelateIps_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/entity/detail/domain/relate/ips", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
