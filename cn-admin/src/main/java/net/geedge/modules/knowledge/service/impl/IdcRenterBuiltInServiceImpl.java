package net.geedge.modules.knowledge.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.modules.knowledge.dao.IdcRenterBuiltInDao;
import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;
import net.geedge.modules.knowledge.service.IdcRenterBuiltInService;

@Service("idcRenterBuiltInService")
public class IdcRenterBuiltInServiceImpl extends ServiceImpl<IdcRenterBuiltInDao,IdcRenterBuiltIn> implements IdcRenterBuiltInService{
	private Log log = Log.get();
	
	@Override
	public void saveBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns) {
		if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
			this.baseMapper.insertBatchForBinary(idcRenterBuiltIns);
		}
	}

	@Override
	public void modifyBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns) {
		if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
			this.baseMapper.updateBatchForBinary(idcRenterBuiltIns);
		}
	}

	@Override
	public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
		IPage page = this.page(new Query(IdcRenterBuiltIn.class).getPage(params));
		List<IdcRenterBuiltIn> idcRenterBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
		page.setRecords(idcRenterBuiltIns);
		return new PageUtils(page);
	}

	@Override
	public void saveBatchJDBC(Connection conn, List<IdcRenterBuiltIn> idcRenterBuiltIns) {
		if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_idc_renter_built_in (addr_format,ip1,ip2,start_ip_binary,"
						+ "    	end_ip_binary,idc_renter,knowledge_id)"
						+ "    	values (?,?,?,INET6_ATON(?),INET6_ATON(?),?,?)");
				for(IdcRenterBuiltIn idcRenterBuiltIn : idcRenterBuiltIns) {
					pstm.setString(1, idcRenterBuiltIn.getAddrFormat());
					pstm.setString(2, idcRenterBuiltIn.getIp1());
					pstm.setString(3, idcRenterBuiltIn.getIp2());
					pstm.setString(4, idcRenterBuiltIn.getStartIpBinary());
					pstm.setString(5, idcRenterBuiltIn.getEndIpBinary());
					pstm.setString(6, idcRenterBuiltIn.getIdcRenter());
					pstm.setInt(7, idcRenterBuiltIn.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<IdcRenterBuiltIn> idcRenterBuiltIns) {
		if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_idc_renter_built_in set addr_format=?,ip1=?,ip2=?,start_ip_binary=INET6_ATON(?),"
						+ "end_ip_binary=INET6_ATON(?),idc_renter=?,knowledge_id=? where id =?");
				for(IdcRenterBuiltIn idcRenterBuiltIn : idcRenterBuiltIns) {
					pstm.setString(1, idcRenterBuiltIn.getAddrFormat());
					pstm.setString(2, idcRenterBuiltIn.getIp1());
					pstm.setString(3, idcRenterBuiltIn.getIp2());
					pstm.setString(4, idcRenterBuiltIn.getStartIpBinary());
					pstm.setString(5, idcRenterBuiltIn.getEndIpBinary());
					pstm.setString(6, idcRenterBuiltIn.getIdcRenter());
					pstm.setInt(7, idcRenterBuiltIn.getKnowledgeId());
					pstm.setInt(8, idcRenterBuiltIn.getId());
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
