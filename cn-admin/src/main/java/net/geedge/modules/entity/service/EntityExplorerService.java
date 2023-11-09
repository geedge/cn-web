package net.geedge.modules.entity.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/07/04/14:23
 */
public interface EntityExplorerService {

    String parseExpression(String resource, String dataName);

    Boolean isAssociationQuery(String resource, String dataName);

    Map<String, Object> getAssociationQueryParam(String resource);

    String getBasicQueryFilter(String resource);

    List<String> getPrimayForFilter(String resource, String dataName);

    String formatHasFilter(String resource);

    List<String> gatTagsForFilter(String resource);
}
