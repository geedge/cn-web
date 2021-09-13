package net.geedge.modules.galaxy.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/galaxy/setting")
public class GalaxyProxyController {
    @Autowired
    private GalaxyProxyService galaxyProxyService;

    @RequestMapping("/{id}")
    public R queryInfo(@PathVariable Integer id) {
        ValidateUtils.is(id).notNull(RCode.GALAXY_PROXY_ID_ISNULL);
        GalaxyProxy galaxyProxy = galaxyProxyService.queryInfo(id);
        return R.ok(galaxyProxy);
    }

    @RequestMapping
    public R queryList(@RequestParam Map<String, Object> params) {
        PageUtils result = galaxyProxyService.queryPage(params);
        return R.ok(result);
    }

    @PostMapping
    @SysLog(operation = OperationEnum.ADD, type = TypeEnum.SETTING)
    public R save(@RequestBody GalaxyProxy galaxyProxy) {
        Integer id = galaxyProxyService.saveOrUpdateGalaxyProxy(galaxyProxy);
        return R.ok().putData("id", id);
    }

    @PutMapping
    @SysLog(operation = OperationEnum.UPDATE, type = TypeEnum.SETTING)
    public R modify(@RequestBody GalaxyProxy galaxyProxy) {
        ValidateUtils.is(galaxyProxy.getId()).notNull(RCode.GALAXY_PROXY_ID_ISNULL);
        Integer id = galaxyProxyService.saveOrUpdateGalaxyProxy(galaxyProxy);
        return R.ok().putData("id", id);
    }

    @DeleteMapping
    @SysLog(operation = OperationEnum.DELETE, type = TypeEnum.SETTING)
    public R remove(@RequestParam String ids) {
        ValidateUtils.is(ids).notNull(RCode.GALAXY_PROXY_ID_ISNULL);
        galaxyProxyService.removeGalaxyProxy(ids);
        return R.ok();
    }

    @PutMapping(path = "clearCache")
    public R clearCache() {
        Tool.FreeMarkerUtil.clearTemlateCache();
        return R.ok();
    }

    @PostMapping("/import")
    @SysLog(operation = OperationEnum.IMPORT, type = TypeEnum.SETTING)
    public R importFile(@RequestParam("file") MultipartFile file) throws IOException {
        galaxyProxyService.importFile(file);
        return R.ok();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @SysLog(operation = OperationEnum.EXPORT, type = TypeEnum.SETTING)
    public void exportFile(HttpServletResponse response) throws Exception {
        galaxyProxyService.exportFile(response);
    }
}
