package net.geedge.modules.sys.controller;

import net.geedge.common.utils.R;
import net.geedge.modules.sys.entity.Timezone;
import net.geedge.modules.sys.service.TimezoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys/timezone")
public class TimezoneController {

    @Autowired
    private TimezoneService timezoneService;

    @GetMapping
    public R timezone(@RequestParam(required = false) String name){
        List<Timezone> list = timezoneService.queryTimezone(name);
        return R.ok().putData("list",list);
    }
}
