package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.FqdnIcpBuiltIn;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:08
 */
public interface FqdnIcpBuiltInService extends IService<FqdnIcpBuiltIn> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

    Map<String, Object> queryEntityDetail(String resource, Boolean isFuzzyQuery);
    
    void saveBatchJDBC(Connection conn, List<FqdnIcpBuiltIn> fqdnIcpBuiltIns);
    
    void modifyBatchJDBC(Connection conn, List<FqdnIcpBuiltIn> fqdnIcpBuiltIns);
}
