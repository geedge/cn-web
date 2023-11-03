package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:46
 */
public interface AppTagUserDefinedService extends IService<AppTagUserDefined> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

    List<Map> queryAppEntityTag(String resource);
    
	void saveBatchJDBC(Connection conn,List<AppTagUserDefined> appTagUserDefineds);
    
	void modifyBatchJDBC(Connection conn,List<AppTagUserDefined> appTagUserDefineds);
}
