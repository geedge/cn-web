package net.geedge.modules.knowledge.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.Tool;
import net.geedge.modules.knowledge.dao.CellDao;
import net.geedge.modules.knowledge.entity.Cell;
import net.geedge.modules.knowledge.service.CellService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("CellService")
public class CellServiceImpl extends ServiceImpl<CellDao, Cell> implements CellService {

    @Override
    public PageUtils queryCellList(Map<String, Object> params) {
        String ids = (String)params.get("ids");
        if(Tool.StrUtil.isNotBlank(ids)){
            params.put("ids",Arrays.asList(ids.split(",")));
        }
        String cellIds = (String)params.get("cellIds");
        if(Tool.StrUtil.isNotBlank(cellIds)){
            params.put("cellIds",Arrays.asList(cellIds.split(",")));
        }
        String nodebIds = (String)params.get("nodebIds");
        if(Tool.StrUtil.isNotBlank(nodebIds)){
            params.put("nodebIds", Arrays.asList(nodebIds.split(",")));
        }
        IPage page = this.page(new Query(Cell.class).getPage(params));
        List<Cell> list = this.baseMapper.queryCellList(page, params);
        page.setRecords(list);
        return new PageUtils(page);
    }

}
