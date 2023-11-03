package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.IpAsnBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import net.geedge.modules.knowledge.service.IpAsnBuiltInService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:31
 */
@Service("IpAsnBuiltInService")
public class IpAsnBuiltInServiceImpl extends ServiceImpl<IpAsnBuiltInDao, IpAsnBuiltIn> implements IpAsnBuiltInService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(IpAsnBuiltIn.class).getPage(params));
        List<IpAsnBuiltIn> ipAsnBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(ipAsnBuiltIns);
        return new PageUtils(page);
    }


    @Override
    public Map<String, Object> queryEntityDetail(String resource) {
        return this.baseMapper.queryEntityDetail(resource);
    }
    
    @Override
	public void saveBatchForBinary(List<IpAsnBuiltIn> ipAsnBuiltIns) {
		if(ObjectUtil.isNotEmpty(ipAsnBuiltIns)) {
			this.baseMapper.insertBatchForBinary(ipAsnBuiltIns);
		}
	}

	@Override
	public void modifyBatchForBinary(List<IpAsnBuiltIn> ipAsnBuiltIns) {
		if(ObjectUtil.isNotEmpty(ipAsnBuiltIns)) {
			this.baseMapper.updateBatchForBinary(ipAsnBuiltIns);
		}
	}


	@Override
	public void saveBatchJDBC(Connection conn, List<IpAsnBuiltIn> ipAsnBuiltIns) {
		if(ObjectUtil.isNotEmpty(ipAsnBuiltIns)){
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_ip_asn_built_in (addr_type,addr_format,ip1,ip2,"
						+ "start_ip_binary,end_ip_binary,asn,organization,is_valid,op_time,op_user,knowledge_id) "
						+ "values (?,?,?,?,INET6_ATON(?),INET6_ATON(?),?,?,?,?,?,?)");
				for(IpAsnBuiltIn ipAsnBuiltIn : ipAsnBuiltIns) {
					pstm.setInt(1, ipAsnBuiltIn.getAddrType());
					pstm.setString(2, ipAsnBuiltIn.getAddrFormat());
					pstm.setString(3, ipAsnBuiltIn.getIp1());
					pstm.setString(4, ipAsnBuiltIn.getIp2());
					pstm.setString(5, ipAsnBuiltIn.getStartIpBinary());
					pstm.setString(6, ipAsnBuiltIn.getEndIpBinary());
					pstm.setString(7, ipAsnBuiltIn.getAsn());
					pstm.setString(8, ipAsnBuiltIn.getOrganization());
					pstm.setInt(9, ipAsnBuiltIn.getIsValid());
					pstm.setTimestamp(10, new java.sql.Timestamp(ipAsnBuiltIn.getOpTime().getTime()));
					pstm.setInt(11, ipAsnBuiltIn.getOpUser());
					pstm.setInt(12, ipAsnBuiltIn.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<IpAsnBuiltIn> ipAsnBuiltIns) {
		if(ObjectUtil.isNotEmpty(ipAsnBuiltIns)){
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_ip_asn_built_in set addr_type=?,addr_format=?,ip1=?,ip2=?,"
						+ "start_ip_binary=INET6_ATON(?),end_ip_binary=INET6_ATON(?),asn=?,organization=?,is_valid=?,op_time=?,"
						+ "op_user=?,knowledge_id=? where id =? ");
				for(IpAsnBuiltIn ipAsnBuiltIn : ipAsnBuiltIns) {
					pstm.setInt(1, ipAsnBuiltIn.getAddrType());
					pstm.setString(2, ipAsnBuiltIn.getAddrFormat());
					pstm.setString(3, ipAsnBuiltIn.getIp1());
					pstm.setString(4, ipAsnBuiltIn.getIp2());
					pstm.setString(5, ipAsnBuiltIn.getStartIpBinary());
					pstm.setString(6, ipAsnBuiltIn.getEndIpBinary());
					pstm.setString(7, ipAsnBuiltIn.getAsn());
					pstm.setString(8, ipAsnBuiltIn.getOrganization());
					pstm.setInt(9, ipAsnBuiltIn.getIsValid());
					pstm.setTimestamp(10, new java.sql.Timestamp(ipAsnBuiltIn.getOpTime().getTime()));
					pstm.setInt(11, ipAsnBuiltIn.getOpUser());
					pstm.setInt(12, ipAsnBuiltIn.getKnowledgeId());
					pstm.setInt(13, ipAsnBuiltIn.getId());
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
