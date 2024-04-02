package net.geedge.modules.knowledge.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.InternalIpBuiltInDao;
import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;
import net.geedge.modules.knowledge.service.InternalIpBuiltInService;

@Service("internalIpBuiltInService")
public class InternalIpBuiltInServiceImpl extends ServiceImpl<InternalIpBuiltInDao,InternalIpBuiltIn> implements InternalIpBuiltInService{
	private Log log = Log.get();
	
	@Override
	public PageUtils queryList(Map<String, Object> params) {
		IPage page = this.page(new Query(InternalIpBuiltIn.class).getPage(params));
		List<InternalIpBuiltIn> internalIpBuiltIns = this.baseMapper.queryPage(page, params);
		page.setRecords(internalIpBuiltIns);
		return new PageUtils(page);
	}
	
	@Override
	public void saveBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns) {
		if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
			this.baseMapper.insertBatchForBinary(internalIpBuiltIns);
		}
	}

	@Override
	public void modifyBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns) {
		if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
			this.baseMapper.updateBatchForBinary(internalIpBuiltIns);
		}
	}


	@Override
	public void saveBatchJDBC(Connection conn, List<InternalIpBuiltIn> internalIpBuiltIns) {
		if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_internal_ip_built_in "
						+ "(addr_format,ip1,ip2,start_ip_binary,end_ip_binary) values (?,?,?,INET6_ATON(?),INET6_ATON(?),?)");
				for(InternalIpBuiltIn insertInternalIpBuiltIn : internalIpBuiltIns) {
					pstm.setString(1, insertInternalIpBuiltIn.getAddrFormat());
					pstm.setString(2, insertInternalIpBuiltIn.getIp1());
					pstm.setString(3, insertInternalIpBuiltIn.getIp2());
					pstm.setString(4, insertInternalIpBuiltIn.getStartIpBinary());
					pstm.setString(5, insertInternalIpBuiltIn.getEndIpBinary());
					pstm.addBatch();
				}
				pstm.executeBatch();
				pstm.clearBatch();
			} catch (SQLException e) {
				log.error(e.getMessage());
				throw new CNException(RCode.IMPORT_ERROR);
			}
		}
	}

	@Override
	public void modifyBatchJDBC(Connection conn, List<InternalIpBuiltIn> internalIpBuiltIns) {
		if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_internal_ip_built_in "
						+ " set addr_format = ?,ip1=?,ip2=?,start_ip_binary=INET6_ATON(?),end_ip_binary=INET6_ATON(?) where id=?");
				for(InternalIpBuiltIn insertInternalIpBuiltIn : internalIpBuiltIns) {
					pstm.setString(1, insertInternalIpBuiltIn.getAddrFormat());
					pstm.setString(2, insertInternalIpBuiltIn.getIp1());
					pstm.setString(3, insertInternalIpBuiltIn.getIp2());
					pstm.setString(4, insertInternalIpBuiltIn.getStartIpBinary());
					pstm.setString(5, insertInternalIpBuiltIn.getEndIpBinary());
					pstm.setInt(6, insertInternalIpBuiltIn.getId());
					pstm.addBatch();
				}
				pstm.executeBatch();
				pstm.clearBatch();
			} catch (SQLException e) {
				log.error(e.getMessage());
				throw new CNException(RCode.IMPORT_ERROR);
			}
		}
	}

}
