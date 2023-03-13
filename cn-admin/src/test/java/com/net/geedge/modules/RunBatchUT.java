package com.net.geedge.modules;

import com.net.geedge.modules.galaxy.*;
import com.net.geedge.modules.panel.*;
import com.net.geedge.modules.sys.*;
import org.junit.runner.RunWith;

/**
 * @Auther: lijinyang
 * @Date: 2022/03/04/14:14
 * 批量运行测试类，在 @SuiteClasses 中加入需要批量运行的 UT Class 即可
 * 例： @org.junit.runners.Suite.SuiteClasses({
 *         A.class,B.class,C.class
 *       })
 */
@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        CtlTest.class,
        LicenseTest.class,
        SysApiKeyTest.class,
        SysAttestTest.class,
        SysConfigTest.class,
        SysDictTest.class,
        SysI18nTest.class,
        SysMenuTest.class,
        SysRoleTest.class,
        SysUserTest.class,
        ApplicationTest.class,
        CryptcyTest.class,
        DetectionTest.class,
        DnsTest.class,
        EntityTest.class,
        GalaxyInterfaceTest.class,
        LinkTest.class,
        OverviewTest.class,
        TrafficTest.class,
        VisualChartTest.class,
        VisualDatasetFieldTest.class,
        VisualDatasetTest.class,
        VisualDatasourceTest.class,
        VisualPanelTest.class
})
public class RunBatchUT {
}
