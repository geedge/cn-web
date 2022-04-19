package com.net.geedge.modules.sys;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysDictEntity;
import net.geedge.modules.sys.entity.SysMenuEntity;
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
 * @Date: 2022/04/14/17:16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysMenuTest extends BaseTest<SysMenuEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/SysMenuTestParameter.json";
    }

    /**
     * GET /sys/menu exist
     */
    @Test
    public void querySysMenuList_exist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/menu", queryParam);

        UTUtil.assertToSizeByFlag(result, true);
    }

    /**
     * GET /sys/menu not exist
     */
    @Test
    public void querySysMenuList_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/sys/menu", queryParam);

        UTUtil.assertToSizeByFlag(result, false);
    }

    /**
     * POST /sys/menu success
     */
    @Test
    public void saveSysMenuList_success() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * POST /sys/menu name can not be null
     */
    @Test
    public void saveSysMenuList_name() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_NAME_ISNULL);
    }

    /**
     * POST /sys/menu code can not be null
     */
    @Test
    public void saveSysMenuList_code() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_CODE_ISNULL);
    }

    /**
     * POST /sys/menu code can not duplicate
     */
    @Test
    public void saveSysMenuList_codeDuplicate() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_CODE_DUPLICATE);
    }

    /**
     * POST /sys/menu i18n can not be null
     */
    @Test
    public void saveSysMenuList_i18n() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_I18N_ISNULL);
    }

    /**
     * POST /sys/menu type can not be null
     */
    @Test
    public void saveSysMenuList_type() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_TYPE_ISNULL);
    }

    /**
     * POST /sys/menu type error 1 to 3
     */
    @Test
    public void saveSysMenuList_typeCorrect() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPost("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_TYPE_INVALIDE);
    }

    /**
     * PUT /sys/menu success
     */
    @Test
    public void updateSysMenuList_success() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT /sys/menu id can not be null
     */
    @Test
    public void updateSysMenuList_id() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_ID_ISNULL);
    }

    /**
     * PUT /sys/menu name can not be null
     */
    @Test
    public void updateSysMenuList_name() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_NAME_ISNULL);
    }

    /**
     * PUT /sys/menu code can not be null
     */
    @Test
    public void updateSysMenuList_code() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_CODE_ISNULL);
    }

    /**
     * PUT /sys/menu code can not duplicate
     */
    @Test
    public void updateSysMenuList_codeDuplicate() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_CODE_DUPLICATE);
    }

    /**
     * PUT /sys/menu i18n can not duplicate
     */
    @Test
    public void updateSysMenuList_i18n() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_I18N_ISNULL);
    }

    /**
     * PUT /sys/menu type can not duplicate
     */
    @Test
    public void updateSysMenuList_type() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_TYPE_ISNULL);
    }

    /**
     * PUT /sys/menu type error 1 to 3
     */
    @Test
    public void updateSysMenuList_typeCorrect() {
        SysMenuEntity sysMenuEntity = jsonToInParameter(testName.getMethodName(), SysMenuEntity.class);
        MvcResult result = UTUtil.performPut("/sys/menu", sysMenuEntity);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_TYPE_INVALIDE);
    }

    /**
     * DELETE /sys/menu success
     */
    @Test
    public void deleteSysMenuList_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/menu", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * DELETE /sys/menu ids
     */
    @Test
    public void deleteSysMenuList_ids() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performDelete("/sys/menu", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SYS_MENU_ID_ISNULL);
    }
}
