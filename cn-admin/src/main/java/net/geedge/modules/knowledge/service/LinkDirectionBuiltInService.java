package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface LinkDirectionBuiltInService extends IService<LinkDirectionBuiltIn>{

    PageUtils queryList(Map<String,Object> params);

    void saveBatchJDBC(Connection conn,List<LinkDirectionBuiltIn> linkDirectionBuiltIns);
    
    void modifyBatchJDBC(Connection conn,List<LinkDirectionBuiltIn> linkDirectionBuiltIns);
}
