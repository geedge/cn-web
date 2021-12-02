package net.geedge.modules.panel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.panel.entity.VisualDataset;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/02/11:07
 */
public interface VisualDatasetService extends IService<VisualDataset> {

    PageUtils queryPage(Map<String, Object> params);

    VisualDataset queryInfo(Integer id);

    Integer saveOrUpdateDataset(VisualDataset dataset);

    void removeDataset(String ids);
}
