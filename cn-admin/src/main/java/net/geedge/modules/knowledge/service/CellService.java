package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.Cell;
import java.util.Map;


public interface CellService extends IService<Cell> {

    PageUtils queryCellList(Map<String,Object> params);

}
