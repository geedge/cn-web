package net.geedge.modules.panel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.panel.entity.VisualDatasource;

import java.util.Map;

/**
 *
 * @Auther: lijinyang
 * @Date: 2021/12/01/15:58
 */
public interface VisualDatasourceService extends IService<VisualDatasource> {

    PageUtils queryPage(Map<String,Object> params);

    VisualDatasource queryInfo(Integer id);

    Integer saveOrUpdateDatasource(VisualDatasource datasource);

    void removeDatasource(String ids);
}
