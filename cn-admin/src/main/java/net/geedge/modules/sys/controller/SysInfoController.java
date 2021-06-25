package net.geedge.modules.sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.utils.R;
import net.geedge.modules.sys.service.SysInfoService;

/**
 * 系统基本信息
 */

@RestController
public class SysInfoController {

    @Autowired
    private SysInfoService sysInfoService;

    @GetMapping("/healthy")
    public R healthy() {
        return R.ok();
    }

    @GetMapping("/about")
    public R about() {
        Map<?, ?> aboutInfo = sysInfoService.getAboutInfo();
        return R.ok(aboutInfo);
    }

    @GetMapping("/gitLog")
    public R getVersionInfo() {
        return R.ok(sysInfoService.getGitVersionInfo());
    }
}
