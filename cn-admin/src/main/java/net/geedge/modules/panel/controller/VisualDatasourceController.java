package net.geedge.modules.panel.controller;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.panel.entity.VisualDatasource;
import net.geedge.modules.panel.service.VisualDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * VisualDatasource接口
 *
 * @Auther: lijinyang
 * @Date: 2021/12/01/16:34
 */
@RestController
@RequestMapping("/visual/datasource")
public class VisualDatasourceController {
    @Autowired
    private VisualDatasourceService visualDatasourceService;

    //VisualDatasource详情
    @RequestMapping("/{id}")
    public R queryInfo(@PathVariable Integer id) {
        VisualDatasource visualDatasource = visualDatasourceService.queryInfo(id);
        return R.ok(visualDatasource);
    }

    //查询VisualDatasource
    @RequestMapping
    public R queryList(@RequestParam Map<String,Object> params) {
        PageUtils result = visualDatasourceService.queryPage(params);
        return R.ok(result);
    }

    //新增VisualDatasource
    @PostMapping
    @SysLog(operation =OperationEnum.ADD,type = TypeEnum.VISUALDATASOURCE)
    public R saveDatasource(@RequestBody VisualDatasource datasource) {
        Integer id = visualDatasourceService.saveOrUpdateDatasource(datasource);
        return R.ok().putData("id", id);
    }

    //更改VisualDatasource
    @PutMapping
    @SysLog(operation =OperationEnum.UPDATE,type = TypeEnum.VISUALDATASOURCE)
    public R modifyDatasource(@RequestBody VisualDatasource datasource) {
        ValidateUtils.is(datasource.getId()).notNull(RCode.DATASOURCE_ID_ISNULL);
        Integer id = visualDatasourceService.saveOrUpdateDatasource(datasource);
        return R.ok().putData("id", id);
    }

    //删除VisualDatasource
    @DeleteMapping
    @SysLog(operation =OperationEnum.DELETE,type = TypeEnum.VISUALDATASOURCE)
    public R removeDatasource(@RequestParam String ids) {
        ValidateUtils.is(ids).notNull(RCode.DATASOURCE_ID_ISNULL);
        visualDatasourceService.removeDatasource(ids);
        return R.ok();
    }
}
