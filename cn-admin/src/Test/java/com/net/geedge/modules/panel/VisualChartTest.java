package com.net.geedge.modules.panel;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualChart;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/04/12/14:59
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class VisualChartTest extends BaseTest<VisualChart> {

    @Override
    protected String jsonIndex() {
        return "panel/VisualChartTestParameter.json";
    }

    /**
     * GET /visual/chart/{id} exist
     */
    @Test()
    public void queryVisualChartInfo_exist() throws Exception {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/chart/%s",visualChart.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasetInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(visualDatasetInfo);
    }

    /**
     * GET /visual/chart/{id} not exist
     */
    @Test()
    public void queryVisualChartInfo_notexist() throws Exception {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performGet(String.format("/visual/chart/%s",visualChart.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object visualDatasetInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(visualDatasetInfo);
    }

    /**
     * GET /visual/chart exist
     */
    @Test
    public void queryVisualChartByPanelId_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/chart not exist
     */
    @Test
    public void queryVisualChartByPanelId_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /visual/chart panelId can not be empty
     */
    @Test
    public void queryVisualChartByPanelId_checkPanelId() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_ID_ISNULL);
    }

    /**
     * GET /visual/chart/list exist
     */
    @Test
    public void queryVisualChartList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart/list", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/chart/list not exist
     */
    @Test
    public void queryVisualChartList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart/list", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /visual/chart/list exist q like name
     */
    @Test
    public void queryVisualChartList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart/list", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /visual/chart/list exist q like params
     */
    @Test
    public void queryVisualChartList_params() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/visual/chart/list", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /visual/chart success
     */
    @Test
    public void saveVisualChart_success() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /visual/chart name can not be empty
     */
    @Test
    public void saveVisualChart_checkName() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_NAME_ISNULL);
    }

    /**
     * POST /visual/chart type can not be empty
     */
    @Test
    public void saveVisualChart_checkType() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_TYPE_ISNULL);
    }

    /**
     * POST /visual/chart x can not be empty
     */
    @Test
    public void saveVisualChart_checkX() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_X_ISNULL);
    }

    /**
     * POST /visual/chart y can not be empty
     */
    @Test
    public void saveVisualChart_checkY() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_Y_ISNULL);
    }

    /**
     * POST /visual/chart w can not be empty
     */
    @Test
    public void saveVisualChart_checkW() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_W_ISNULL);
    }

    /**
     * POST /visual/chart h can not be empty
     */
    @Test
    public void saveVisualChart_checkH() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_H_ISNULL);
    }

    /**
     * POST /visual/chart panelId can not be empty
     */
    @Test
    public void saveVisualChart_checkPanelId() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_ID_ISNULL);
    }

    /**
     * PUT /visual/chart success
     */
    @Test
    public void updateVisualChart_success() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPost("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /visual/chart id can not be empty
     */
    @Test
    public void updateVisualChart_checkId() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_ID_ISNULL);
    }

    /**
     * PUT /visual/chart name can not be empty
     */
    @Test
    public void updateVisualChart_checkName() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_NAME_ISNULL);
    }

    /**
     * PUT /visual/chart type can not be empty
     */
    @Test
    public void updateVisualChart_checkType() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_TYPE_ISNULL);
    }

    /**
     * PUT /visual/chart x can not be empty
     */
    @Test
    public void updateVisualChart_checkX() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_X_ISNULL);
    }

    /**
     * PUT /visual/chart y can not be empty
     */
    @Test
    public void updateVisualChart_checkY() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_Y_ISNULL);
    }

    /**
     * PUT /visual/chart w can not be empty
     */
    @Test
    public void updateVisualChart_checkW() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_W_ISNULL);
    }

    /**
     * PUT /visual/chart h can not be empty
     */
    @Test
    public void updateVisualChart_checkH() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.CHART_H_ISNULL);
    }

    /**
     * PUT /visual/chart panelId can not be empty
     */
    @Test
    public void updateVisualChart_checkPanelId() {
        VisualChart visualChart = jsonToInParameter(testName.getMethodName(), VisualChart.class);
        MvcResult result = UTUtil.performPut("/visual/chart",visualChart);

        UTUtil.assertEqualsRCode(result, RCode.PANEL_ID_ISNULL);
    }

    /**
     * DELETE /visual/chart success
     */
    @Test
    public void deleteVisualChart_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/chart",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /visual/chart build in can not delete
     */
    @Test
    public void deleteVisualChart_buildIn() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/visual/chart",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.CHART_BUILDIN_CAN_NOT_REMOVE);
    }

    /**
     * PUT /visual/chart/layout success
     */
    @Test
    public void updateVisualChartLayout_success() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /visual/chart/layout charts can not be null
     */
    @Test
    public void updateVisualChartLayout_chsrts() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.NOT_NULL_ERROR);
    }

    /**
     * PUT /visual/chart/layout id can not be null
     */
    @Test
    public void updateVisualChartLayout_id() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.CHART_ID_ISNULL);
    }

    /**
     * PUT /visual/chart/layout x can not be null
     */
    @Test
    public void updateVisualChartLayout_x() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.CHART_X_ISNULL);
    }

    /**
     * PUT /visual/chart/layout y can not be null
     */
    @Test
    public void updateVisualChartLayout_y() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.CHART_Y_ISNULL);
    }

    /**
     * PUT /visual/chart/layout w can not be null
     */
    @Test
    public void updateVisualChartLayout_w() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.CHART_W_ISNULL);
    }

    /**
     * PUT /visual/chart/layout h can not be null
     */
    @Test
    public void updateVisualChartLayout_h() {
        List<VisualChart> charts = (List<VisualChart>)jsonToInParameter(testName.getMethodName(), List.class);
        MvcResult result = UTUtil.performPut("/visual/chart/layout",charts);

        UTUtil.assertEqualsRCode(result, RCode.CHART_H_ISNULL);
    }
}
