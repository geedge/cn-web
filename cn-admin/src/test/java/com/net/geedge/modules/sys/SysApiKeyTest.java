package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysApiKey;
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
 * @Date: 2022/04/13/17:06
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysApiKeyTest extends BaseTest<SysApiKey> {
    @Override
    protected String jsonIndex() {
        return "sys/SysApiKeyTestParameter.json";
    }

    /**
     * GET /sys/apiKey exist
     */
    @Test
    public void querySysApiKeyList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/apiKey", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/apiKey not exist
     */
    @Test
    public void querySysApiKeyList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/apiKey", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /sys/apiKey exist q like name
     */
    @Test
    public void querySysApiKeyList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/apiKey", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/apiKey exist q like token
     */
    @Test
    public void querySysApiKeyList_token() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/apiKey", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/apiKey exist ids
     */
    @Test
    public void querySysApiKeyList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/apiKey", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /sys/apiKey success
     */
    @Test
    public void saveSysApiKey_success() {
        SysApiKey sysApiKey = jsonToInParameter(testName.getMethodName(), SysApiKey.class);
        MvcResult result = UTUtil.performPost("/sys/apiKey",sysApiKey);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/apiKey name can not be null
     */
    @Test
    public void saveSysApiKey_checkName() {
        SysApiKey sysApiKey = jsonToInParameter(testName.getMethodName(), SysApiKey.class);
        MvcResult result = UTUtil.performPost("/sys/apiKey",sysApiKey);

        UTUtil.assertEqualsRCode(result, RCode.SYSAPIKEY_NAME_ISNULL);
    }

    /**
     * POST /sys/apiKey name can not duplicate
     */
    @Test
    public void saveSysApiKey_checkNameDuplicate() {
        SysApiKey sysApiKey = jsonToInParameter(testName.getMethodName(), SysApiKey.class);
        MvcResult result = UTUtil.performPost("/sys/apiKey",sysApiKey);

        UTUtil.assertEqualsRCode(result, RCode.SYSAPIKEY_NAME_DUPLICATE);
    }

    /**
     * POST /sys/apiKey roleId can not be null
     */
    @Test
    public void saveSysApiKey_checkRoleId() {
        SysApiKey sysApiKey = jsonToInParameter(testName.getMethodName(), SysApiKey.class);
        MvcResult result = UTUtil.performPost("/sys/apiKey",sysApiKey);

        UTUtil.assertEqualsRCode(result, RCode.SYSAPIKEY_ROLEID_ISNULL);
    }

    /**
     * DELETE  /sys/apiKey  success
     */
    @Test
    public void deleteSysApiKey_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/apiKey",queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
