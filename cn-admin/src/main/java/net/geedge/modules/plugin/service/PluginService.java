package net.geedge.modules.plugin.service;

import net.geedge.common.utils.R;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/14:31
 */
public interface PluginService {

    void intelligenceLearningStatus(Integer id, String type);

    R intelligenceLearningList(Map<String,Object> params);
}
