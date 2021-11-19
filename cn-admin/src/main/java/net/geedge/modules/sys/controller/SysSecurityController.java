package net.geedge.modules.sys.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import net.geedge.common.utils.JSONUtil;
import net.geedge.common.utils.R;
import net.geedge.modules.sys.service.SysSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统安全设定
 *
 * @author lijinyang
 * @date 2021/11/22
 * */
@RestController
@RequestMapping("/sys/security")
public class SysSecurityController extends AbstractController{
    @Autowired
    private SysSecurityService sysSecurityService;
    /**
     * 系统安全设定查询
     *
     */
    @GetMapping
    public R querySecurity(){
        String security = sysSecurityService.querySecurity();
        JSON parse = JSONUtil.parse(security);
        return R.ok().put("date", parse);
    }
    /**
     * 系统安全设定修改
     *
     */
    @PutMapping
    public R updateSecurity(@RequestBody JSONObject security){
        String jsonStr = JSONUtil.toJsonStr(security);
        boolean isUpdate = sysSecurityService.updateSecurity(jsonStr);
        if(isUpdate != true){
            return R.error();
        }
        return R.ok();
    }
}
