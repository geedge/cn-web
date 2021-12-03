package net.geedge.modules.panel.controller;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.panel.entity.VisualDatasetField;
import net.geedge.modules.panel.service.VisualDatasetFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * VisualDatasetField接口
 * @Auther: lijinyang
 * @Date: 2021/12/02/17:53
 */
@RestController
@RequestMapping("/visual/dataset/field")
public class VisualDatasetFieldController {
    @Autowired
    private VisualDatasetFieldService visualDatasetFieldService;

    //VisualDatasetField详情
    @RequestMapping("/{id}")
    public R queryInfo(@PathVariable Integer id) {
        VisualDatasetField visualDatasetField = visualDatasetFieldService.queryInfo(id);
        return R.ok(visualDatasetField);
    }

    //查询VisualDatasetField
    @RequestMapping
    @SysLog(operation = OperationEnum.QUERY,type = TypeEnum.VISUALDATASETFIELD)
    public R queryList(@RequestParam Map<String,Object> params) {
        PageUtils result = visualDatasetFieldService.queryPage(params);
        return R.ok(result);
    }

    //新增VisualDatasetField
    @PostMapping
    @SysLog(operation = OperationEnum.ADD,type = TypeEnum.VISUALDATASETFIELD)
    public R saveDatasetField(@RequestBody VisualDatasetField datasetField) {
        Integer id = visualDatasetFieldService.saveOrUpdateDatasetField(datasetField);
        return R.ok().putData("id",id);
    }

    //更改VisualDatasetField
    @PutMapping
    @SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.VISUALDATASETFIELD)
    public R modifyDatasetField(@RequestBody VisualDatasetField datasetField) {
        ValidateUtils.is(datasetField.getId()).notNull(RCode.DATASET_FIELD_ID_ISNULL);
        Integer id = visualDatasetFieldService.saveOrUpdateDatasetField(datasetField);
        return R.ok().putData("id",id);
    }

    //删除VisualDatasetField
    @DeleteMapping
    @SysLog(operation = OperationEnum.DELETE,type = TypeEnum.VISUALDATASETFIELD)
    public R removeDatasetField(@RequestParam String ids) {
        ValidateUtils.is(ids).notNull(RCode.DATASET_FIELD_ID_ISNULL);
        visualDatasetFieldService.removeDatasetField(ids);
        return R.ok();
    }


}
