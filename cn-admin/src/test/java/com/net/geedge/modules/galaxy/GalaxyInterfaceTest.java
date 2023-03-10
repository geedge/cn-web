package com.net.geedge.modules.galaxy;

import com.net.geedge.modules.BaseTest;
import com.net.geedge.modules.UTUtil;
import net.geedge.AdminApplication;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.entity.VisualDatasetField;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/05/05/13:43
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class GalaxyInterfaceTest extends BaseTest<Map> {
    @Override
    protected String jsonIndex() {
        return "galaxy/GalaxyInterfaceTestParameter.json";
    }

    /**
     * GET /interface/notexist not exist
     */
    @Test
    public void proxy_notexist() {
        Map<String, Object> queryParam = jsonToInParameter(testName.getMethodName(), Map.class);
        MvcResult result = UTUtil.performGet("/interface/notexist", queryParam);

        int status = result.getResponse().getStatus();
        Assert.assertEquals(status, HttpServletResponse.SC_NOT_FOUND);
    }

}
