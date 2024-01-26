package net.geedge.modules.plugin.controller;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.enums.IntelligenceLearningStatus;
import net.geedge.modules.plugin.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/14:30
 */
@RestController
@RequestMapping("/v1/plugin")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @PostMapping("/intelligence-learning/{id}/{type}")
    public R intelligenceLearningStatus(@PathVariable("id")Integer id, @PathVariable(value = "type") IntelligenceLearningStatus type){
        pluginService.intelligenceLearningStatus(id, type.getValue());
        return R.ok();
    }

    @RequestMapping("/intelligence-learning/list")
    public R intelligenceLearningStatus(@RequestParam Map<String,Object> params){
        Integer jobGroup = Integer.parseInt((String)params.get("jobGroup"));
        ValidateUtils.is(jobGroup).notNull(RCode.PLUGIN_LEARNING_JOB_GROUP_ISNULL);
        return pluginService.intelligenceLearningList(params);
    }
}
