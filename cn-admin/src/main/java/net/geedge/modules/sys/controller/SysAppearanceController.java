package net.geedge.modules.sys.controller;

import cn.hutool.json.JSONObject;
import net.geedge.common.annotation.SysLog;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.R;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.service.SysAppearanceService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    //@RequiresGuest
    public R queryAppearance(){
        JSONObject appearance = sysAppearanceService.queryAppearance();
        return R.ok(appearance);
    }
    /**a
     * 系统外观更改
     */
    @PutMapping
    @RequiresPermissions({"sys:appearance:update"})
    @SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.SYSTEM)
    public R updateAppearance(@RequestBody JSONObject appearance){
        boolean isUpdate = sysAppearanceService.updateAppearance(appearance);
        if(isUpdate != true){
            return R.error();
        }
        return R.ok();
    }
}
