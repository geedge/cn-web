package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysUserEntity;
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
 * @Date: 2022/04/14/15:00
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysUserTest extends BaseTest<SysUserEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/SysUserTestParameter.json";
    }

    /**
     * GET /sys/user/{id} exist
     */
    @Test
    public void queryUserInfo_exist() throws Exception {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/i18n/%s",sysUserEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysUserInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(sysUserInfo);
    }

    /**
     * GET /sys/user/{id} not exist
     */
    @Test
    public void queryUserInfo_notexist() throws Exception {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/i18n/%s",sysUserEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysUserInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(sysUserInfo);
    }

    /**
     * GET /sys/user exist
     */
    @Test
    public void querySysUserList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/user not exist
     */
    @Test
    public void querySysUserList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /sys/user exist p like name
     */
    @Test
    public void querySysUserList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/user exist p like username
     */
    @Test
    public void querySysUserList_username() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/user exist p like email
     */
    @Test
    public void querySysUserList_email() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/user exist p like mobile
     */
    @Test
    public void querySysUserList_mobile() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/user exist ids
     */
    @Test
    public void querySysUserList_checkIds() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/user exist name
     */
    @Test
    public void querySysUserList_checkName() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/user", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * POST /sys/user success
     */
    @Test
    public void saveSysUser_success() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPost("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/user name can not be null
     */
    @Test
    public void saveSysUser_checkName() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPost("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_USER_NAME_ISNULL);
    }

    /**
     * POST /sys/user username can not be null
     */
    @Test
    public void saveSysUser_checkUsername() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPost("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_USER_USERNAME_ISNULL);
    }

    /**
     * POST /sys/user pin can not be null
     */
    @Test
    public void saveSysUser_checkPin() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPost("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_USER_PASSWORD_ISNULL);
    }

    /**
     * POST /sys/user roleIds can not be null
     */
    @Test
    public void saveSysUser_checkRoleIds() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPost("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_ID_ISNULL);
    }

    /**
     * PUT /sys/user success
     */
    @Test
    public void updateSysUser_success() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPut("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/user id can not be null
     */
    @Test
    public void updateSysUser_checkId() {
        SysUserEntity sysUserEntity = jsonToInParameter(testName.getMethodName(), SysUserEntity.class);
        MvcResult result = UTUtil.performPut("/sys/user", sysUserEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_USER_ID_ISNULL);
    }

    /**
     * DELETE /sys/user success
     */
    @Test
    public void deleteSysUser_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/user", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /sys/user ids can not be null
     */
    @Test
    public void deleteSysUser_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/user", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_USER_ID_ISNULL);
    }

    /**
     * PUT /sys/user/pin success
     */
    @Test
    public void updateSysUserPin_success() {
        Map<String,Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPut("/sys/user/pin", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/user/pin user oldpwd error
     */
    @Test
    public void updateSysUserPin_checkOldpwd() {
        Map<String,Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performPut("/sys/user/pin", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_USER_OLDPWD);

    }
}
