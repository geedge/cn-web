package net.geedge.modules.entity.controller;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.entity.service.EntityGraphRelationService;
import net.geedge.modules.entity.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lijinyang
 * @Date: 2023/06/28/10:13
 */
@RestController
@RequestMapping("/v1/entity/graph/relation")
public class EntityGraphRelationController {

    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityGraphRelationService entityGraphRelationService;

    @RequestMapping("/basic/{entityType}")
    public R queryEntityDetail(@PathVariable(value = "entityType") String entityType, @Param("resource")String resource) {
        ValidateUtils.is(resource).notNull(RCode.KNOWLEDGE_SOURCE_ISNULL);
        return entityService.queryEntityDetail(entityType, resource);
    }

    @RequestMapping("/kb/intelligence/tag/{entityType}")
    public R queryEntityTag(@PathVariable(value = "entityType") String entityType, @Param("resource")String resource) {
        ValidateUtils.is(resource).notNull(RCode.KNOWLEDGE_SOURCE_ISNULL);
        return entityService.queryEntityTag(entityType, resource);
    }


}
