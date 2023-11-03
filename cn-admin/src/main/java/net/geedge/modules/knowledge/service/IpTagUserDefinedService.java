package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:47
 */
public interface IpTagUserDefinedService extends IService<IpTagUserDefined> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
    
    void saveBatchForBinary(List<IpTagUserDefined> ipTagUserDefineds);
    
    void modifyBatchForBinary(List<IpTagUserDefined> ipTagUserDefineds);

    List<Map> queryIpEntityTag(String resource);
    
	void saveBatchJDBC(Connection conn,List<IpTagUserDefined> ipTagUserDefineds);
    
	void modifyBatchJDBC(Connection conn,List<IpTagUserDefined> ipTagUserDefineds);
}
