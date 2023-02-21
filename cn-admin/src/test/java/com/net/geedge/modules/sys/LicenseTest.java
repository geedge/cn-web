package com.net.geedge.modules.sys;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.LicenseEntity;
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
 * @Date: 2022/04/18/11:38
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class LicenseTest extends BaseTest<LicenseEntity> {
    @Override
    protected String jsonIndex() {
        return "sys/LicenseTestParameter.json";
    }

    /**
     * GET  /sys/license/state success
     */
    @Test
    public void sysLicenseState_success() {
        MvcResult result = UTUtil.performGet("/sys/license/state", null);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET  /sys/license success
     */
    @Test
    public void sysLicenseList_success() {
        MvcResult result = UTUtil.performGet("/sys/license", null);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * PUT   /sys/license/upload success
     */
    /*@Test
    public void sysLicenseUpload_success() {
        MvcResult result = UTUtil.performMultipart("/sys/license/upload", "license/license.xml","file",null,null);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/

    /**
     * GET   /sys/license/download success
     */
    @Test
    public void sysLicenseDownload_success() {
        MvcResult result = UTUtil.performGet("/sys/license/download", null);

        Assert.assertNotNull(result);
    }

    /**
     * POST /sys/license/gen success
     */
    /*@Test
    public void sysLicenseGen_success() {
        LicenseEntity licenseEntity = jsonToInParameter(testName.getMethodName(), LicenseEntity.class);
        MvcResult result = UTUtil.performPost("/sys/license/gen", licenseEntity);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }*/
}
