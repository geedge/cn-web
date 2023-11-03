package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IocDarkweb;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:01
 */
public interface IocDarkwebService extends IService<IocDarkweb> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);

    Map<String, Object> queryIpEntityTag(String resource);

    Map<String, Object> queryDomainEntityTag(String resource, Boolean isFuzzyQuery);	
	
	void saveBatchJDBC(Connection conn,List<IocDarkweb> iocDarkwebs);
    
	void modifyBatchJDBC(Connection conn,List<IocDarkweb> iocDarkwebs);

    Map<String, Object> queryDomainDetectionEntityDetail(String resource);

    Map<String, Object> queryIpDetectionEntityDetail(String resource);
}
