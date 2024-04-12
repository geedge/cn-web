package net.geedge.modules.tag.controller;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.knowledge.service.IntelligenceIndicatorService;
import net.geedge.modules.knowledge.service.TagService;
import net.geedge.modules.knowledge.service.impl.IntelligenceIndicatorServiceImpl;
import net.geedge.modules.knowledge.service.impl.TagServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/02/17:15
 */
@RestController
@RequestMapping("/v1/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private IntelligenceIndicatorService intelligenceIndicatorService;

    @RequestMapping("/list")
    public R queryTagList(@RequestParam Map<String,Object> params) {
        PageUtils page = tagService.queryTagList(params);
        return R.ok(page);
    }

    @RequestMapping("/{name}/indicator/statistics")
    public R indicatorStatistics(@PathVariable String name) {
        return intelligenceIndicatorService.indicatorStatistics(name);
    }

    @RequestMapping("/{name}/indicator/trend")
    public R indicatorTrend(@PathVariable String name,@RequestParam Map<String,Object> params) {
        params.put("name",name);
        return intelligenceIndicatorService.indicatorTrend(params);
    }

    @PostMapping
    @SysLog(operation = OperationEnum.ADD, type = TypeEnum.TAG)
    @RequiresPermissions({"knowledge:base:add"})
    public R addTag(@RequestBody Map<String,Object> params) {
        String id = tagService.addOrModifyTag(params);
        return R.ok().put("id", id);
    }

    @PutMapping
    @SysLog(operation = OperationEnum.UPDATE, type = TypeEnum.TAG)
    @RequiresPermissions({"knowledge:base:update"})
    public R updateTag(@RequestBody Map<String,Object> params) {
        String tagId = (String) params.get("id");
        ValidateUtils.is(tagId).notNull(RCode.TAG_ID_ISNULL);
        String id = tagService.addOrModifyTag(params);
        return R.ok().put("id", id);
    }

    @PostMapping("/parser")
    @RequiresPermissions({"knowledge:base:add"})
    public R importTag(@Param("indicatorType")String indicatorType, @Param("file") MultipartFile file) {
        ValidateUtils.is(file).notNull(RCode.TAG_FILE_ISNULL);
        ValidateUtils.is(indicatorType).notNull(RCode.TAG_PARAM_NOTNULL);
        return tagService.importData(indicatorType,file);
    }

    @PostMapping("/items/batch")
    @RequiresPermissions({"knowledge:base:add"})
    public R importBatch(@Param("file") MultipartFile file,@Param("action")String action) {
        ValidateUtils.is(file).notNull(RCode.TAG_FILE_ISNULL);
        ValidateUtils.is(action).notNull(RCode.TAG_IMPORT_ACTION_ISNULL);
        ValidateUtils.is(action).regex("(overwrite|append|update)", RCode.TAG_IMPORT_ACTION_VALUE_ERROR);
        Integer id = tagService.importBatch(file, action);
        return R.ok();
    }

    @DeleteMapping()
    @SysLog(operation = OperationEnum.DELETE, type = TypeEnum.TAG)
    @RequiresPermissions({"knowledge:base:delete"})
    public R remove(@RequestParam("id") String id) {
        List<String> ids = tagService.remove(id);
        return R.ok().put("ids",ids);
    }

    @RequestMapping("/{id}")
    public R queryIndicators(@PathVariable String id, @RequestParam Map<String, Object> params) {
        Map<String,Object> data = tagService.queryIndicators(id, params);
        return R.ok(data);
    }
}
