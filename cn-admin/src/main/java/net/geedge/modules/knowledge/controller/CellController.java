package net.geedge.modules.knowledge.controller;

import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.knowledge.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/cell")
public class CellController {

    @Autowired
    private CellService cellService;

    @RequestMapping("/list")
    public R queryCellList(@RequestParam Map<String,Object> params) {
        PageUtils page = cellService.queryCellList(params);
        return R.ok(page);
    }
}
