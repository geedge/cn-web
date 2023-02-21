package com.net.geedge.modules.sys;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysRoleEntity;
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
 * @Date: 2022/04/18/11:12
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class CtlTest extends BaseTest<Map> {
    @Override
    protected String jsonIndex() {
        return "sys/CtlTestParameter.json";
    }

    /**
     * PUT /ctl/resetAdmin success
     */
    @Test
    public void resetAdmin_success() {
        MvcResult result = UTUtil.performPut("/ctl/resetAdmin", null);
        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }

    /**
     * GET /ctl/aes success
     */
    @Test
    public void aes_success() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/ctl/aes", queryParam);

        UTUtil.assertEqualsRCode(result, RCode.SUCCESS);
    }
}
