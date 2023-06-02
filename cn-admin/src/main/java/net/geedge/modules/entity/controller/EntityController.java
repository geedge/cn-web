package net.geedge.modules.entity.controller;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.entity.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/31/14:10
 */
@RestController
@RequestMapping("/v1/entity/detail")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @RequestMapping("/basic/{entityType}")
    public R queryEntityDetail(@PathVariable(value = "entityType") String entityType, @Param("resource")String resource) {
        ValidateUtils.is(resource).notNull(RCode.KNOWLEDGE_SOURCE_ISNULL);
        Map<String, Object> data = entityService.queryEntityDetail(entityType, resource);
        return R.ok(data);
    }

    @RequestMapping("/kb/intelligence/tag/{entityType}")
    public R queryEntityTag(@PathVariable(value = "entityType") String entityType, @Param("resource")String resource) {
        ValidateUtils.is(resource).notNull(RCode.KNOWLEDGE_SOURCE_ISNULL);
        Map<String, Object> data = entityService.queryEntityTag(entityType, resource);
        return R.ok(data);
    }
}
