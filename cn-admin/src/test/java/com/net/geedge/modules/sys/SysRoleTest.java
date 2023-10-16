package com.net.geedge.modules.sys;

import com.alibaba.fastjson.JSONObject;
import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysDictEntity;
import net.geedge.modules.sys.entity.SysRoleEntity;
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
 * @Date: 2022/04/15/9:32
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysRoleTest extends BaseTest<SysRoleEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/SysRoleTestParameter.json";
    }

    /**
     * GET /sys/role exist
     */
    @Test
    public void querySysRoleList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/role", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/role not exist
     */
    @Test
    public void querySysRoleList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/role", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * GET /sys/role exist q like name
     */
    @Test
    public void querySysRoleList_name() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/role", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/role exist ids
     */
    @Test
    public void querySysRoleList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/role", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/role exist name
     */
    @Test
    public void querySysRoleList_checkName() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/role", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/role/{id} exist
     */
    @Test
    public void querySysRoleInfo_exist() throws Exception {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/role/menu/%s",sysRoleEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysRoleInfo = actualResult.get("data");

        // isExist
        Assert.assertNotNull(sysRoleInfo);
    }

    /**
     * GET /sys/role/{id} not exist
     */
    @Test
    public void querySysRoleInfo_notexist() throws Exception {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performGet(String.format("/sys/role/menu/%s",sysRoleEntity.getId()),null);

        String responseStr = result.getResponse().getContentAsString();
        Assert.assertNotNull("No success response", responseStr);

        Map<String, Object> actualResult = JSONObject.parseObject(responseStr, Map.class);
        Object sysRoleInfo = actualResult.get("data");

        // isExist
        Assert.assertNull(sysRoleInfo);
    }

    /**
     * POST /sys/role success
     */
    @Test
    public void saveSysRole_success() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPost("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/role name can not be empty
     */
    @Test
    public void saveSysRole_checkName() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPost("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_NAME_ISNULL);
    }

    /**
     * POST /sys/role name can not duplicate
     */
    @Test
    public void saveSysRole_checkNameDuplicate() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPost("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_NAME_DUPLICATE);
    }

    /**
     * POST /sys/role menuIds can not be empty
     */
    @Test
    public void saveSysRole_checkMenuIds() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPost("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_MENUID_ISNULL);
    }

    /**
     * PUT /sys/role success
     */
    @Test
    public void updateSysRole_success() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPut("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/role id can not be null
     */
    @Test
    public void updateSysRole_checkId() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPut("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_ID_ISNULL);
    }

    /**
     * PUT /sys/role name can not be null
     */
    @Test
    public void updateSysRole_checkName() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPut("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_NAME_ISNULL);
    }

    /**
     * PUT /sys/role name can not duplicate
     */
    @Test
    public void updateSysRole_checkNameDuplicate() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPut("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_NAME_DUPLICATE);
    }

    /**
     * PUT /sys/role menuIds can not be empty
     */
    @Test
    public void updateSysRole_checkMenuIds() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPut("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_MENUID_ISNULL);
    }

    /**
     * PUT /sys/role Menu is not exsits
     */
    @Test
    public void updateSysRole_menuIds() {
        SysRoleEntity sysRoleEntity = jsonToInParameter(testName.getMethodName(), SysRoleEntity.class);
        MvcResult result = UTUtil.performPut("/sys/role", sysRoleEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_ISNULL);
    }

    /**
     * DELETE /sys/role success
     */
    @Test
    public void deleteSysRole_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/role", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /sys/role ids
     */
    @Test
    public void deleteSysRole_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/role", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_ROLE_ID_ISNULL);
    }
}
