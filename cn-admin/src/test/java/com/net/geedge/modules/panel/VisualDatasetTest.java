package com.net.geedge.modules.panel;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualDataset;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/03/04/15:22
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class VisualDatasetTest extends BaseTest<VisualDataset> {
    @Override
    protected String jsonIndex() {
        return "panel/VisualDatasetTestParameter.json";
    }

    /**
     * GET /visual/dataset/{id} exist
     */
    @Test()
    public void queryVisualDatasetInfo_exist() throws Exception {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/dataset/%s",visualDataset.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasetInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(visualDatasetInfo);
    }

    /**
     * GET /visual/dataset/{id} not exist
     */
    @Test()
    public void queryVisualDatasetInfo_notexist() throws Exception {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/dataset/%s",visualDataset.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasetInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(visualDatasetInfo);
    }

    /**
     * GET /visual/dataset exist
     */
    @Test
    public void queryVisualDatasetList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset not exist
     */
    @Test
    public void queryVisualDatasetList_notexist() {
        Map queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /visual/dataset exist q like name
     */
    @Test
    public void queryVisualDatasetList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset exist ids
     */
    @Test
    public void queryVisualDatasetList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/dataset exist q like remark
     */
    @Test
    public void queryVisualDatasetList_remark() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/dataset", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /visual/dataset success
     */
    @Test
    public void saveVisualDataset_success() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST  /visual/dataset  name can not be empty
     */
    @Test
    public void saveVisualDataset_checkName() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_NAME_ISNULL);
    }

    /**
     * POST  /visual/dataset  name can not duplicate
     */
    @Test
    public void saveVisualDataset_checkNameDuplicate() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_NAME_DUPLICATE);
    }

    /**
     * POST  /visual/dataset  type can not be empty
     */
    @Test
    public void saveVisualDataset_checkType() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_TYPE_ISNULL);
    }

    /**
     * POST  /visual/dataset  type error must 1 or 2
     */
    @Test
    public void saveVisualDataset_checkTypeCorrect() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_TYPE_INVALIDE);
    }

    /**
     * PUT  /visual/dataset  success
     */
    @Test
    public void updateVisualDataset_success() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPut("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT  /visual/dataset  id can not be empty
     */
    @Test
    public void updateVisualDataset_checkId() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPut("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_ID_ISNULL);
    }

    /**
     * PUT  /visual/dataset  name can not be empty
     */
    @Test
    public void updateVisualDataset_checkName() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_NAME_ISNULL);
    }

    /**
     * PUT  /visual/dataset  name can not be duplicate
     */
    @Test
    public void updateVisualDataset_checkNameDuplicate() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_NAME_DUPLICATE);
    }

    /**
     * PUT  /visual/dataset  type can not be empty
     */
    @Test
    public void updateVisualDataset_checkType() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_TYPE_ISNULL);
    }

    /**
     * PUT  /visual/dataset  type error must 1 or 2
     */
    @Test
    public void updateVisualDataset_checkTypeCorrect() {
        VisualDataset visualDataset = jsonToInParameter(testName.getMethodName(), VisualDataset.class);
        MvcResult result = UTUtil.performPost("/visual/dataset",visualDataset);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_TYPE_INVALIDE);
    }

    /**
     * DELETE  /visual/dataset  success
     */
    @Test
    public void deleteVisualDataset_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/dataset",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE  /visual/dataset  id can not be empty
     */
    @Test
    public void deleteVisualDataset_checkIds() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/dataset",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_ID_ISNULL);
    }

    /**
     * DELETE  /visual/dataset  build in can not delete
     */
    @Test
    public void deleteVisualDataset_buildIn() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/dataset",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.DATASET_BUILDIN_CAN_NOT_REMOVE);
    }

}
