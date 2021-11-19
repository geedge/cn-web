package net.geedge.modules.sys.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import net.geedge.common.utils.JSONUtil;
import net.geedge.common.utils.R;
import net.geedge.modules.sys.service.SysAppearanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 系统外观设置
 *
 * @author lijinyang
 * @date 2021/11/17
 * */
@RestController
@RequestMapping("/sys/appearance")
public class SysAppearanceController extends AbstractController{
    @Autowired
    private SysAppearanceService sysAppearanceService;
    /**
     * 系统外观查询
     */
    @GetMapping
    public R queryAppearance(){
        String appearance = sysAppearanceService.queryAppearance();
        JSON parse = JSONUtil.parse(appearance);
        return R.ok().put("date",parse);
    }
    /**
     * 系统外观更改
     */
    @PutMapping
    public R updateAppearance(@RequestBody JSONObject appearance){
        String jsonStr = JSONUtil.toJsonStr(appearance);
        boolean isUpdate = sysAppearanceService.updateAppearance(jsonStr);
        if(isUpdate != true){
            return R.error();
        }
        return R.ok();
    }
}
