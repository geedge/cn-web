package net.geedge.modules.entity.service;


import net.geedge.common.utils.R;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/31/14:13
 */
public interface EntityService {

    R queryEntityDetail(String entityType, String resource);

    R queryEntityTag(String entityType, String resource);

    R queryEntityList(String entityType, Map<String,Object> params);
}
