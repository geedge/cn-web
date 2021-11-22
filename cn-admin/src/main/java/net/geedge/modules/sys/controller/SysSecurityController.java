package net.geedge.modules.sys.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import net.geedge.common.annotation.SysLog;
import net.geedge.common.utils.JSONUtil;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.R;
import net.geedge.common.utils.TypeEnum;
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
        JSONObject security = sysSecurityService.querySecurity();
        return R.ok(security);
    }
    /**
     * 系统安全设定修改
     *
     */
    @PutMapping
    @SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.SYSTEM)
    public R updateSecurity(@RequestBody JSONObject security){
        boolean isUpdate = sysSecurityService.updateSecurity(security);
        if(isUpdate != true){
            return R.error();
        }
        return R.ok();
    }
}
