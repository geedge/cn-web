package net.geedge.modules.panel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.panel.entity.VisualDatasetField;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/02/16:48
 */
public interface VisualDatasetFieldService extends IService<VisualDatasetField> {

    PageUtils queryPage(Map<String, Object> params);

    VisualDatasetField queryInfo(Integer id);

    Integer saveOrUpdateDatasetField(VisualDatasetField datasetField);

    void removeDatasetField(String ids);
}
