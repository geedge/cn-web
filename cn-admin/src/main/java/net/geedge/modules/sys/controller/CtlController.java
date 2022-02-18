package net.geedge.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import net.geedge.common.annotation.SysLog;
import net.geedge.common.utils.IPUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.R;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.service.CtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  CTL相关接口
 * @Auther: lijinyang
 * @Date: 2022/02/14/13:48
 *
 */
@RestController
@RequestMapping("/ctl")
public class CtlController extends AbstractController{

    @Autowired
    private CtlService ctlService;
    /**
     * 管理员账号密码重置接口
     */
    @RequestMapping("/resetAdmin")
    @SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.CTL)
    public R resetAdmin(HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        Map<String, Object> result = null;
        if(StrUtil.equals(ipAddr, "127.0.0.1") || StrUtil.equals(ipAddr, "localhost") || StrUtil.equals(ipAddr, "0:0:0:0:0:0:0:1")) {
            result = ctlService.resetAdmin();
        }
        return R.ok(result);
    }
    /**
     * 数据库密码加密接口
     */
    @GetMapping("/aes")
    public R ase(@RequestParam String content){
        Map<String, String> result = ctlService.aes(content);
        return R.ok(result);
    }
}
