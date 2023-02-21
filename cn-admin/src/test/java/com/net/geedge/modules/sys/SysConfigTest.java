package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysConfigEntity;
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
 * @Date: 2022/04/15/13:49
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysConfigTest extends BaseTest<SysConfigEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/SysConfigTestParameter.json";
    }

    /**
     * POST /sys/config success
     */
    @Test
    public void saveSysConfig_success() {
        SysConfigEntity sysConfigEntity = jsonToInParameter(testName.getMethodName(), SysConfigEntity.class);
        MvcResult result = UTUtil.performPost("/sys/config", sysConfigEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/config error
     */
    @Test
    public void saveSysConfig_error() {
        SysConfigEntity sysConfigEntity = jsonToInParameter(testName.getMethodName(), SysConfigEntity.class);
        MvcResult result = UTUtil.performPost("/sys/config", sysConfigEntity);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * PUT /sys/config success
     */
    @Test
    public void updateSysConfig_success() {
        SysConfigEntity sysConfigEntity = jsonToInParameter(testName.getMethodName(), SysConfigEntity.class);
        MvcResult result = UTUtil.performPut("/sys/config", sysConfigEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/config error
     */
    @Test
    public void updateSysConfig_error() {
        SysConfigEntity sysConfigEntity = jsonToInParameter(testName.getMethodName(), SysConfigEntity.class);
        MvcResult result = UTUtil.performPut("/sys/config", sysConfigEntity);

        UTUtil.assertEqualsRCode(result, RCode.ERROR);
    }

    /**
     * DELETE /sys/config success
     */
    @Test
    public void deleteSysConfig_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        Object ids = queryParam.get("ids");

        MvcResult result = UTUtil.performDelete("/sys/config", ids);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /sys/appearance exist
     */
    @Test
    public void querySysAppearance_exist() throws Exception {
        MvcResult result = UTUtil.performGet("/sys/appearance",null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysDictInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(sysDictInfo);
    }

    /**
     * PUT /sys/appearance success
     */
    @Test
    public void updateSysAppearance_success() {
        JSONObject queryParam = jsonToInParameter(testName.getMethodName(), JSONObject.class);
        MvcResult result = UTUtil.performPut("/sys/appearance", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /sys/timezones success
     */
    @Test
    public void querySysTimezones_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/timezones", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /sys/security success
     */
    @Test
    public void querySysSecurity_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/security", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/security success
     */
    @Test
    public void updateSysSecurity_success() {
        JSONObject queryParam = jsonToInParameter(testName.getMethodName(), JSONObject.class);
        MvcResult result = UTUtil.performPut("/sys/security", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /sys/email success
     */
    @Test
    public void querySysEmail_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/email", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/email success
     */
    @Test
    public void updateSysEmail_success() {
        JSONObject queryParam = jsonToInParameter(testName.getMethodName(), JSONObject.class);
        MvcResult result = UTUtil.performPut("/sys/email", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

}
