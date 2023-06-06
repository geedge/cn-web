package com.net.geedge.modules.knowledge;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/24/9:54
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class KnowledgeBaseTest extends BaseTest<KnowledgeBase> {
    @Override
    protected String jsonIndex() {
        return "knowledge/KnowledgeBaseTestParameter.json";
    }

    /**
     * GET /v1/knowledgeBase/statistics success
     */
    @Test
    public void knowledgeBaseStatistics_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/knowledgeBase/statistics", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/knowledgeBase/list success
     */
    @Test
    public void knowledgeBaseList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/v1/knowledgeBase/list", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/knowledgeBase/{id} success
     */
    @Test
    public void knowledgeBaseItem_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/knowledgeBase/%s", queryParam.get("knowledgeId")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /v1/knowledgeBase/{id} not exist
     */
    @Test
    public void knowledgeBaseItem_notExist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet(String.format("/v1/knowledgeBase/%s", queryParam.get("knowledgeId")), queryParam);

        UTUtil.assertEqualsRCode(result, RCode.KNOWLEDGE_BASE_NOT_EXIST);
    }

    /**
     * PATCH /v1/knowledgeBase/status success
     */
    @Test
    public void knowledgeBaseStatus_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPacth("/v1/knowledgeBase/status", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PATCH /v1/knowledgeBase/status params invalid
     */
    @Test
    public void knowledgeBaseStatus_paramsInvalid() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPacth("/v1/knowledgeBase/status", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.KNOWLEDGE_BASE_ENABLE_PARAMS_INVALID);
    }

    /**
     * DELETE /v1/knowledgeBase success
     */
    @Test
    public void deleteKnowledgeBase_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/v1/knowledgeBase", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /v1/knowledgeBase error
     */
    @Test
    public void deleteKnowledgeBase_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/v1/knowledgeBase", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * POST /v1/knowledgeBase success
     */
    @Test
    public void addKnowledgeBase_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPost("/v1/knowledgeBase", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /v1/knowledgeBase error
     */
    @Test
    public void addKnowledgeBase_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPost("/v1/knowledgeBase", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * PUT /v1/knowledgeBase success
     */
    @Test
    public void modifyKnowledgeBase_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPut("/v1/knowledgeBase", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /v1/knowledgeBase id is null
     */
    @Test
    public void modifyKnowledgeBase_idIsNull() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPut("/v1/knowledgeBase", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.KNOWLEDGE_ID_ISNULL);
    }

    /**
     * POST /v1/knowledgeBase/parser success
     */
    @Test
    public void parserKnowledgeBase_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("contentType", MediaType.MULTIPART_FORM_DATA_VALUE);
        MvcResult result = UTUtil.performMultipart("/v1/knowledgeBase/parser", "/doc/knowledge/IP移动网类别标签.csv", "file", queryParam, requestHeader);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /v1/knowledgeBase/parser source is null
     */
    @Test
    public void parserKnowledgeBase_sourceIsNull() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("contentType", MediaType.MULTIPART_FORM_DATA_VALUE);
        MvcResult result = UTUtil.performMultipart("/v1/knowledgeBase/parser", "/doc/knowledge/IP移动网类别标签.csv", "file", queryParam, requestHeader);

        UTUtil.assertEqualsRCode(result, RCode.KNOWLEDGE_SOURCE_ISNULL);
    }
}
