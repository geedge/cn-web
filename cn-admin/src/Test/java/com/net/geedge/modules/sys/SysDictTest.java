package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysDictEntity;
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
 * @Date: 2022/04/14/13:39
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysDictTest extends BaseTest<SysDictEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/SysDictTestParameter.json";
    }

    /**
     * GET /sys/dict/{id} exist
     */
    @Test
    public void querySysDictInfo_exist() throws Exception {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/i18n/%s",sysDictEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysDictInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(sysDictInfo);
    }

    /**
     * GET /sys/dict/{id} not exist
     */
    @Test
    public void querySysDictInfo_notexist() throws Exception {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/i18n/%s",sysDictEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysDictInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(sysDictInfo);
    }

    /**
     * GET /sys/dict exist
     */
    @Test
    public void querySysDictList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict not exist
     */
    @Test
    public void querySysDictList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /sys/dict exist q like name
     */
    @Test
    public void querySysDictList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict exist q like code
     */
    @Test
    public void querySysDictList_code() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict exist q like value
     */
    @Test
    public void querySysDictList_value() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict exist q like remark
     */
    @Test
    public void querySysDictList_remark() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict exist ids
     */
    @Test
    public void querySysDictList_checkIds() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict exist value
     */
    @Test
    public void querySysDictList_checkValue() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/dict exist type
     */
    @Test
    public void querySysDictList_checkType() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/dict", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /sys/dict success
     */
    @Test
    public void saveSysDictList_success() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPost("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/dict type can not be null
     */
    @Test
    public void saveSysDictList_checkType() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPost("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_TYPE_ISNULL);
    }

    /**
     * POST /sys/dict value can not be null
     */
    @Test
    public void saveSysDictList_checkValue() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPost("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_VALUE_ISNULL);
    }

    /**
     * POST /sys/dict code can not duplicate
     */
    @Test
    public void saveSysDictList_checkCode() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPost("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_CODE_ISNULL);
    }

    /**
     * PUT /sys/dict success
     */
    @Test
    public void updateSysDictList_success() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPut("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/dict type can not be null
     */
    @Test
    public void updateSysDictList_checkType() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPut("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_TYPE_ISNULL);
    }

    /**
     * PUT /sys/dict value can not be null
     */
    @Test
    public void updateSysDictList_checkValue() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPut("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_VALUE_ISNULL);
    }

    /**
     * PUT /sys/dict code can not be null
     */
    @Test
    public void updateSysDictList_checkCode() {
        SysDictEntity sysDictEntity = jsonToInParameter(testName.getMethodName(), SysDictEntity.class);
        MvcResult result = UTUtil.performPut("/sys/dict", sysDictEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_CODE_ISNULL);
    }

    /**
     * DELETE /sys/dict success
     */
    @Test
    public void deleteSysDictList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/dict", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /sys/dict id can not be null
     */
    @Test
    public void deleteSysDictList_checkId() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/dict", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_DICT_ID_ISNULL);
    }
}
