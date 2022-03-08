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
}
