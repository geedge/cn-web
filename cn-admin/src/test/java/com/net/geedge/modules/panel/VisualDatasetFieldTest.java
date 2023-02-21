package com.net.geedge.modules.panel;

import com.alibaba.fastjson.JSONObject;
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

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/04/12/17:29
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class VisualDatasetFieldTest extends BaseTest<VisualDatasetField> {

    @Override
    protected String jsonIndex() {
        return "panel/VisualDatasetFieldTestParameter.json";
    }

    /**
     * GET /visual/dataset/field/{id} exist
     */
    @Test()
    public void queryVisualDatasetFieldInfo_exist() throws Exception {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/dataset/field/%s",visualDatasetField.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasetFieldInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(visualDatasetFieldInfo);
    }

    /**
     * GET /visual/dataset/field/{id} not exist
     */
    @Test()
    public void queryVisualDatasetFieldInfo_notexist() throws Exception {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/dataset/field/%s",visualDatasetField.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasetFieldInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(visualDatasetFieldInfo);
    }

    /**
     * GET /visual/dataset/field exist
     */
    @Test
    public void queryVisualDatasetFieldList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset/field", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset/field not exist
     */
    @Test
    public void queryVisualDatasetFieldList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset/field", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /visual/dataset/field exist q like name
     */
    @Test
    public void queryVisualDatasetFieldList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset/field", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset/field exist q like params
     */
    @Test
    public void queryVisualDatasetFieldList_params() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset/field", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset/field exist q like remark
     */
    @Test
    public void queryVisualDatasetFieldList_remark() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset/field", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset/field exist ids
     */
    @Test
    public void queryVisualDatasetFieldList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset/field", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /visual/dataset/field success
     */
    @Test
    public void saveVisualDatasetField_success() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /visual/dataset/field name can not be empty
     */
    @Test
    public void saveVisualDatasetField_name() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_NAME_ISNULL);
    }

    /**
     * POST /visual/dataset/field name can not duplicate
     */
    @Test
    public void saveVisualDatasetField_nameDuplicate() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_NAME_DUPLICATE);
    }

    /**
     * POST /visual/dataset/field type can not be empty
     */
    @Test
    public void saveVisualDatasetField_type() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_TYPE_ISNULL);
    }

    /**
     * POST /visual/dataset/field type error mast 1 to 4
     */
    @Test
    public void saveVisualDatasetField_typeCorrect() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_TYPE_INVALIDE);
    }

    /**
     * POST /visual/dataset/field stype can not be empty
     */
    @Test
    public void saveVisualDatasetField_stype() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_STYPE_ISNULL);
    }

    /**
     * POST /visual/dataset/field stype error mast 1 or 2
     */
    @Test
    public void saveVisualDatasetField_stypeCorrect() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_STYPE_INVALIDE);
    }

    /**
     * POST /visual/dataset/field dsetid can not be empty
     */
    @Test
    public void saveVisualDatasetField_dsetid() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_DSETID_ISNULL);
    }

    /**
     * POST /visual/dataset/field idx can not be empty
     */
    @Test
    public void saveVisualDatasetField_idx() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_IDX_ISNULL);
    }

    /**
     * POST /visual/dataset/field expr can not be empty
     */
    @Test
    public void saveVisualDatasetField_expr() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPost("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_EXPR_ISNULL);
    }

    /**
     * PUT /visual/dataset/field success
     */
    @Test
    public void updateVisualDatasetField_success() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /visual/dataset/field name can not be empty
     */
    @Test
    public void updateVisualDatasetField_name() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_NAME_ISNULL);
    }

    /**
     * PUT /visual/dataset/field type can not be empty
     */
    @Test
    public void updateVisualDatasetField_type() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_TYPE_ISNULL);
    }

    /**
     * PUT /visual/dataset/field type error 1 or 4
     */
    @Test
    public void updateVisualDatasetField_typeCorrect() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_TYPE_INVALIDE);
    }

    /**
     * PUT /visual/dataset/field stype can not be empty
     */
    @Test
    public void updateVisualDatasetField_stype() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_STYPE_ISNULL);
    }

    /**
     * PUT /visual/dataset/field stype error 1 or 2
     */
    @Test
    public void updateVisualDatasetField_stypeCorrect() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_STYPE_INVALIDE);
    }

    /**
     * PUT /visual/dataset/field dsetid can not be empty
     */
    @Test
    public void updateVisualDatasetField_dsetid() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_DSETID_ISNULL);
    }

    /**
     * PUT /visual/dataset/field idx can not be empty
     */
    @Test
    public void updateVisualDatasetField_idx() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_IDX_ISNULL);
    }

    /**
     * PUT /visual/dataset/field expr can not be empty
     */
    @Test
    public void updateVisualDatasetField_expr() {
        VisualDatasetField visualDatasetField = jsonToInParameter(testName.getMethodName(), VisualDatasetField.class);
        MvcResult result = UTUtil.performPut("/visual/dataset/field",visualDatasetField);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_EXPR_ISNULL);
    }

    /**
     * DELETE /visual/dataset/field success
     */
    @Test
    public void deleteVisualDatasetField_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/dataset/field",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /visual/dataset/field build in can not delete
     */
    @Test
    public void deleteVisualDatasetField_buildIn() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/dataset/field",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_FIELD_BUILDIN_CAN_NOT_REMOVE);
    }
}
