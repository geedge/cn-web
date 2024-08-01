package net.geedge.modules.knowledge.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:29
 */
public interface IpAsnBuiltInService extends IService<IpAsnBuiltIn> {

    PageUtils queryList(Map<String,Object> params);

    Map<String, Object> queryEntityDetail(String resource,boolean isIpv6);
    
	void modifyBatchForBinary(List<IpAsnBuiltIn> ipAsnBuiltIns);

	void saveBatchForBinary(List<IpAsnBuiltIn> ipAsnBuiltIns);
	
	void saveBatchJDBC(Connection conn,List<IpAsnBuiltIn> ipAsnBuiltIns);
	
	void modifyBatchJDBC(Connection conn,List<IpAsnBuiltIn> ipAsnBuiltIns);
}
