package com.net.geedge.modules;

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
        //SysUserTest.class,
        //IpamSubnetTest.class
})
public class RunBatchUT {
}
