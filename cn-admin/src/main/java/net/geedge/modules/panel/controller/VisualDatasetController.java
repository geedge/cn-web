package net.geedge.modules.panel.controller;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.panel.entity.VisualDataset;
import net.geedge.modules.panel.service.VisualDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * VisualDatasource接口
 *
 * @Auther: lijinyang
 * @Date: 2021/12/02/13:54
 */
@RestController
@RequestMapping("/visual/dataset")
public class VisualDatasetController {
    @Autowired
    private VisualDatasetService visualDatasetService;

    //VisualDataset详情
    @RequestMapping("/{id}")
    public R queryInfo(@PathVariable Integer id) {
        VisualDataset visualDataset = visualDatasetService.queryInfo(id);
        return R.ok(visualDataset);
    }

    //查询VisualDataset
    @RequestMapping
    @SysLog(operation = OperationEnum.QUERY,type = TypeEnum.VISUALDATASET)
    public R queryList(@RequestParam Map<String,Object> params) {
        PageUtils result = visualDatasetService.queryPage(params);
        return R.ok(result);
    }

    //新增VisualDataset
    @PostMapping
    @SysLog(operation = OperationEnum.ADD,type = TypeEnum.VISUALDATASET)
    public R saveDataset(@RequestBody VisualDataset dataset) {
        Integer id = visualDatasetService.saveOrUpdateDataset(dataset);
        return R.ok().putData("id",id);
    }

    //更改VisualDataset
    @PutMapping
    @SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.VISUALDATASET)
    public R modifyDataset(@RequestBody VisualDataset dataset) {
        ValidateUtils.is(dataset.getId()).notNull(RCode.DATASET_ID_ISNULL);
        Integer id = visualDatasetService.saveOrUpdateDataset(dataset);
        return R.ok().putData("id",id);
    }

    //删除VisualDataset
    @DeleteMapping
    @SysLog(operation = OperationEnum.DELETE,type = TypeEnum.VISUALDATASET)
    public R removeDataset(@RequestParam String ids) {
        ValidateUtils.is(ids).notNull(RCode.DATASET_ID_ISNULL);
        visualDatasetService.removeDataset(ids);
        return R.ok();
    }
}
