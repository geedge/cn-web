package com.net.geedge.modules.panel;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualPanel;
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
 * @Date: 2022/04/12/11:38
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class VisualPanelTest extends BaseTest<VisualPanel> {
    @Override
    protected String jsonIndex() {
        return "panel/VisualPanelTestParameter.json";
    }

    /**
     * GET /visual/panel/{id} exist
     */
    @Test()
    public void queryVisualPanelInfo_exist() throws Exception {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/panel/%s",visualPanel.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualPanelInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(visualPanelInfo);
    }

    /**
     * GET /visual/panel/{id} not exist
     */
    @Test()
    public void queryVisualPanelInfo_notexist() throws Exception {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/panel/%s",visualPanel.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualPanelInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(visualPanelInfo);
    }

    /**
     * GET /visual/panel exist
     */
    @Test
    public void queryVisualPanelList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/panel", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/panel not exist
     */
    @Test
    public void queryVisualPanelList_notexist() {
        Map queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/panel", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /visual/panel exist q
     */
    @Test
    public void queryVisualPanelList_q() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/panel", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/panel exist name
     */
    @Test
    public void queryVisualPanelList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/panel", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/panel exist ids
     */
    @Test
    public void queryVisualPanelList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/panel", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/panel type can not be empty
     */
    @Test
    public void queryVisualPanelList_checkType() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/panel", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_TYPE_ISNULL);
    }

    /**
     * POST /visual/panel success
     */
    @Test
    public void saveVisualPanel_success() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPost("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /visual/panel name can not be empty
     */
    @Test
    public void saveVisualPanel_checkName() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPost("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_NAME_ISNULL);
    }

    /**
     * POST /visual/panel name can not duplicate
     */
    @Test
    public void saveVisualPanel_checkNameDuplicate() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPost("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_NAME_DUPLICATE);
    }

    /**
     * POST /visual/panel type can not be empty
     */
    @Test
    public void saveVisualPanel_checkType() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPost("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_TYPE_ISNULL);
    }

    /**
     * POST /visual/panel type error must gt 0
     */
    @Test
    public void saveVisualPanel_checkTypeCorrect() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPost("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_TYPE_INVALIDE);
    }

    /**
     * PUT  /visual/panel  success
     */
    @Test
    public void updateVisualPanel_success() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPut("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT  /visual/panel  id can not be empty
     */
    @Test
    public void updateVisualPanel_checkId() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPut("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_ID_ISNULL);
    }

    /**
     * PUT  /visual/panel  name can not be empty
     */
    @Test
    public void updateVisualPanel_checkName() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPut("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_NAME_ISNULL);
    }

    /**
     * PUT  /visual/panel  name can not duplicate
     */
    @Test
    public void updateVisualPanel_checkNameDuplicate() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPut("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_NAME_DUPLICATE);
    }

    /**
     * PUT  /visual/panel  type can not empty
     */
    @Test
    public void updateVisualPanel_checkType() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPut("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_TYPE_ISNULL);
    }

    /**
     * PUT  /visual/panel  type error must gt 0
     */
    @Test
    public void updateVisualPanel_checkTypeCorrect() {
        VisualPanel visualPanel = jsonToInParameter(testName.getMethodName(), VisualPanel.class);
        MvcResult result = UTUtil.performPut("/visual/panel",visualPanel);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_TYPE_INVALIDE);
    }

    /**
     * DELETE  /visual/panel  success
     */
    @Test
    public void deleteVisualPanel_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/panel",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE  /visual/panel  id can not be empty
     */
    @Test
    public void deleteVisualPanel_checkId() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/panel",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_ID_ISNULL);
    }

    /**
     * DELETE  /visual/panel  build in can not delete
     */
    @Test
    public void deleteVisualPanel_buildIn() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/panel",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_BUILDIN_CAN_NOT_REMOVE);
    }
}
