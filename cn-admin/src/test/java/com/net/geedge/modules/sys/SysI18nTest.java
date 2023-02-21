package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualChart;
import net.geedge.modules.sys.entity.SysI18nEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/04/14/9:34
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysI18nTest extends BaseTest<SysI18nEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/SysI18nTestParameter.json";
    }

    /**
     * GET /sys/i18n/lang exist
     */
    @Test
    public void querySysI18nLang_exist() throws Exception {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n/lang", queryParam);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Map data = (Map) actualResult.get("data");

        // isExist
        Assert.assertNotNull(data);
    }

    /**
     * GET /sys/i18n/lang not exist
     */
    @Test
    public void querySysI18nLang_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n/lang", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_PARAM_ISNULL);
    }

    /**
     * GET /sys/i18n/{id} exist
     */
    @Test
    public void querySysI18nId_exist() throws Exception {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/i18n/%s",sysI18nEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysI18nInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(sysI18nInfo);
    }

    /**
     * GET /sys/i18n/{id} not exist
     */
    @Test
    public void querySysI18nId_notexist() throws Exception {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/i18n/%s",sysI18nEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysI18nInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(sysI18nInfo);
    }

    /**
     * GET /sys/i18n exist
     */
    @Test
    public void querySysI18nList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/i18n not exist
     */
    @Test
    public void querySysI18nList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /sys/i18n exist q like lang
     */
    @Test
    public void querySysI18nList_lang() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/i18n exist q like code
     */
    @Test
    public void querySysI18nList_code() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/i18n exist q like value
     */
    @Test
    public void querySysI18nList_value() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/i18n exist q like remark
     */
    @Test
    public void querySysI18nList_remark() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/i18n exist q like ids
     */
    @Test
    public void querySysI18nList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/i18n", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /sys/i18n success
     */
    @Test
    public void saveSysI18n_success() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPost("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/i18n lang can not be null
     */
    @Test
    public void saveSysI18n_checkLang() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPost("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_LANG_ISNULL);
    }

    /**
     * POST /sys/i18n code can not be null
     */
    @Test
    public void saveSysI18n_checkCode() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPost("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_CODE_ISNULL);
    }

    /**
     * POST /sys/i18n value can not be null
     */
    @Test
    public void saveSysI18n_checkValue() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPost("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_VALUE_ISNULL);
    }

    /**
     * PUT /sys/i18n success
     */
    @Test
    public void updateSysI18n_success() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPut("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/i18n id can not be null
     */
    @Test
    public void updateSysI18n_checkId() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPut("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_ID_ISNULL);
    }

    /**
     * PUT /sys/i18n lang can not be null
     */
    @Test
    public void updateSysI18n_checkLang() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPut("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_LANG_ISNULL);
    }

    /**
     * PUT /sys/i18n code can not be null
     */
    @Test
    public void updateSysI18n_checkCode() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPut("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_CODE_ISNULL);
    }

    /**
     * PUT /sys/i18n value can not be null
     */
    @Test
    public void updateSysI18n_checkValue() {
        SysI18nEntity sysI18nEntity = jsonToInParameter(testName.getMethodName(), SysI18nEntity.class);
        MvcResult result = UTUtil.performPut("/sys/i18n",sysI18nEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_VALUE_ISNULL);
    }

    /**
     * DELETE /sys/i18n success
     */
    @Test
    public void deleteSysI18n_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/i18n",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /sys/i18n ids can not be null
     */
    @Test
    public void deleteSysI18n_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/i18n",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_I18N_ID_ISNULL);
    }
}
