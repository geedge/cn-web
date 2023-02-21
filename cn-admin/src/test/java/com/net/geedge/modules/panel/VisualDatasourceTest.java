package com.net.geedge.modules.panel;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualDataset;
import net.geedge.modules.panel.entity.VisualDatasource;
import org.junit.Assert;
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
 * @Date: 2022/04/13/15:35
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class VisualDatasourceTest extends BaseTest<VisualDatasource> {
    @Override
    protected String jsonIndex() {
        return "panel/VisualDatasourceTestParameter.json";
    }

    /**
     * GET /visual/datasource/{id} exist
     */
    @Test()
    public void queryVisualDatasourceInfo_exist() throws Exception {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/datasource/%s",visualDatasource.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasourceInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(visualDatasourceInfo);
    }

    /**
     * GET /visual/datasource/{id} not exist
     */
    @Test()
    public void queryVisualDatasourceInfo_notexist() throws Exception {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/datasource/%s",visualDatasource.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasourceInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(visualDatasourceInfo);
    }

    /**
     * GET /visual/datasource exist
     */
    @Test
    public void queryVisualDatasourceList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/datasource", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/datasource not exist
     */
    @Test
    public void queryVisualDatasourceList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/datasource", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /visual/datasource exist q like name
     */
    @Test
    public void queryVisualDatasourceList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/datasource", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/datasource exist q like params
     */
    @Test
    public void queryVisualDatasourceList_params() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/datasource", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/datasource exist q like remark
     */
    @Test
    public void queryVisualDatasourceList_remark() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/datasource", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/datasource ids
     */
    @Test
    public void queryVisualDatasourceList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/datasource", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /visual/datasource success
     */
    @Test
    public void saveVisualDatasource_success() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPost("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /visual/datasource name can not be empty
     */
    @Test
    public void saveVisualDatasource_checkName() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPost("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_NAME_ISNULL);
    }

    /**
     * POST /visual/datasource name can not duplicate
     */
    @Test
    public void saveVisualDatasource_checkNameDuplicate() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPost("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_NAME_DUPLICATE);
    }

    /**
     * POST /visual/datasource type can not be empty
     */
    @Test
    public void saveVisualDatasource_checkType() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPost("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_TYPE_ISNULL);
    }

    /**
     * POST /visual/datasource type error mast 1 or 2
     */
    @Test
    public void saveVisualDatasource_checkTypeCorrect() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPost("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_TYPE_INVALIDE);
    }

    /**
     * PUT /visual/datasource success
     */
    @Test
    public void updateVisualDatasource_success() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPut("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /visual/datasource id can not be null
     */
    @Test
    public void updateVisualDatasource_checkId() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPut("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_ID_ISNULL);
    }

    /**
     * PUT /visual/datasource name can not be null
     */
    @Test
    public void updateVisualDatasource_checkName() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPut("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_NAME_ISNULL);
    }

    /**
     * PUT /visual/datasource name can not duplicate
     */
    @Test
    public void updateVisualDatasource_checkNameDuplicate() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPut("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_NAME_DUPLICATE);
    }

    /**
     * PUT /visual/datasource type can not be empty
     */
    @Test
    public void updateVisualDatasource_checkType() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPut("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_TYPE_ISNULL);
    }

    /**
     * PUT /visual/datasource type error mast 1 or 2
     */
    @Test
    public void updateVisualDatasource_checkTypeCorrect() {
        VisualDatasource visualDatasource = jsonToInParameter(testName.getMethodName(), VisualDatasource.class);
        MvcResult result = UTUtil.performPut("/visual/datasource",visualDatasource);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_TYPE_INVALIDE);
    }

    /**
     * DELETE  /visual/datasource  success
     */
    @Test
    public void deleteVisualDatasource_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/datasource",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE  /visual/datasource  build in can not delete
     */
    @Test
    public void deleteVisualDatasource_buildIn() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/datasource",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.DATASOURCE_BUILDIN_CAN_NOT_REMOVE);
    }
}
