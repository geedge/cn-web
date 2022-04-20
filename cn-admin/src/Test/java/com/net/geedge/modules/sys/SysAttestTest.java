package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysRoleEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/04/15/17:52
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysAttestTest extends BaseTest<Map> {
    @Override
    protected String jsonIndex() {
        return "sys/SysAttestTestParameter.json";
    }

    /**
     * GET /sys/log exist
     */
    @Test
    public void querySysLogList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/log", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/log not exist
     */
    @Test
    public void querySysLogList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/log", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * POST /sys/login success
     */
    @Test
    public void sysLogin_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPost("/sys/login", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/login error
     */
    @Test
    public void sysLogin_error() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPost("/sys/login", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_LOGIN_USERPWD);
    }

    /**
     * POST /sys/user/permissions success
     */
    /*@Test
    public void sysUserPermissions_success() {
        MvcResult result = UTUtil.performPost("/sys/user/permissions", null);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET /logout success
     */
    @Test
    public void logout_success() {
        Map<String,Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/logout", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
