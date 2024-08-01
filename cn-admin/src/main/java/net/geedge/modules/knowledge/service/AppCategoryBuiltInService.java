package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;

import java.sql.Connection;
import java.util.List;
import java.util.Map;


/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:43
 */
public interface AppCategoryBuiltInService extends IService<AppCategoryBuiltIn> {

    PageUtils queryList(Map<String,Object> params);

    Map<String, Object> queryEntityDetail(String resource);
    
    void saveBatchJDBC(Connection conn,List<AppCategoryBuiltIn> appCategoryBuiltIns);
    
    void modifyBatchJDBC(Connection conn,List<AppCategoryBuiltIn> appCategoryBuiltIns);
}
