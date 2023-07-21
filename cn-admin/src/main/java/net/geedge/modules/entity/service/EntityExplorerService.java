package net.geedge.modules.entity.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/07/04/14:23
 */
public interface EntityExplorerService {

    String parseExpression(String resource);

    List<Map<String, Object>> gatTrinoResponseResult(String sql);

    Boolean isAssociationQuery(String resource);

    Map<String, Object> getAssociationQueryParam(String resource);

    String getBasicQueryFilter(String resource);
}
