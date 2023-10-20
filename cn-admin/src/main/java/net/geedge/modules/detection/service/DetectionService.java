package net.geedge.modules.detection.service;

import net.geedge.common.utils.R;

/**
 * @Auther: lijinyang
 * @Date: 2023/10/20/15:04
 */
public interface DetectionService {

    R queryDetectionEntityDetail(String entityType, String resource);
}
