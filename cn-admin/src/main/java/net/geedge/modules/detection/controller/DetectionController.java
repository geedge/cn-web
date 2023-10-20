package net.geedge.modules.detection.controller;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.detection.service.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lijinyang
 * @Date: 2023/10/20/15:00
 */
@RestController
@RequestMapping("/v1/detection/security")
public class DetectionController {

    @Autowired
    private DetectionService detectionService;

    @RequestMapping("/entity/detail/{entityType}")
    public R queryDetectionEntityDetail(@PathVariable(value = "entityType") String entityType, @Param("resource")String resource) {
        ValidateUtils.is(resource).notNull(RCode.KNOWLEDGE_SOURCE_ISNULL);
        return detectionService.queryDetectionEntityDetail(entityType, resource);
    }
 }
