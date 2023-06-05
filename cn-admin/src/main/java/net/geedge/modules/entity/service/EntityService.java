package net.geedge.modules.entity.service;


import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/31/14:13
 */
public interface EntityService {

    Map<String, Object> queryEntityDetail(String entityType, String resource);

    Map<String, Object> queryEntityTag(String entityType, String resource);

    Map<String, Object> queryEntityList(String entityType, Map<String,Object> params);
}
