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
import net.geedge.modules.knowledge.dao.IpTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import net.geedge.modules.knowledge.service.IpTagUserDefinedService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:54
 */
@Service("IpTagUserDefinedService")
public class IpTagUserDefinedServiceImpl extends ServiceImpl<IpTagUserDefinedDao, IpTagUserDefined> implements IpTagUserDefinedService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(IpTagUserDefined.class).getPage(params));
        List<IpTagUserDefined> ipTagUserDefineds = this.baseMapper.queryPageByKnowledgeId(page, params);
        ipTagUserDefineds.forEach(ip -> {ip.setStartIpBinary(""); ip.setEndIpBinary("");});
        page.setRecords(ipTagUserDefineds);
        return new PageUtils(page);
    }

	@Override
	public void saveBatchForBinary(List<IpTagUserDefined> ipTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(ipTagUserDefineds)) {
			this.baseMapper.insertBatchForBinary(ipTagUserDefineds);
		}
	}

	@Override
	public void modifyBatchForBinary(List<IpTagUserDefined> ipTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(ipTagUserDefineds)) {
			this.baseMapper.updateBatchForBinary(ipTagUserDefineds);
		}
	}


	@Override
	public List<Map> queryIpEntityTag(String resource) {
		return this.baseMapper.queryIpEntityTag(resource);
	}

	@Override
	public void saveBatchJDBC(Connection conn, List<IpTagUserDefined> ipTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(ipTagUserDefineds)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_ip_tag_user_defined (tag_value,addr_format,"
						+ "ip1,ip2,start_ip_binary,end_ip_binary,op_time,c_time,op_user,is_valid,knowledge_id) "
						+ "values (?,?,?,?,INET6_ATON(?),INET6_ATON(?),?,?,?,?,?)");
				for(IpTagUserDefined ipTagUserDefined : ipTagUserDefineds) {
					pstm.setString(1, ipTagUserDefined.getTagValue());
					pstm.setString(2, ipTagUserDefined.getAddrFormat());
					pstm.setString(3, ipTagUserDefined.getIp1());
					pstm.setString(4, ipTagUserDefined.getIp2());
					pstm.setString(5, ipTagUserDefined.getStartIpBinary());
					pstm.setString(6, ipTagUserDefined.getEndIpBinary());
					pstm.setTimestamp(7, new java.sql.Timestamp(ipTagUserDefined.getOpTime().getTime()));
					pstm.setTimestamp(8, new java.sql.Timestamp(ipTagUserDefined.getCTime().getTime()));
					pstm.setInt(9, ipTagUserDefined.getOpUser());
					pstm.setInt(10, ObjectUtil.isEmpty(ipTagUserDefined.getIsValid())?1:ipTagUserDefined.getIsValid());
					pstm.setInt(11, ipTagUserDefined.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<IpTagUserDefined> ipTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(ipTagUserDefineds)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_ip_tag_user_defined set tag_value =?,addr_format=?,"
						+ "ip1=?,ip2=?,start_ip_binary=INET6_ATON(?),end_ip_binary=INET6_ATON(?),op_time=?,op_user=?,"
						+ "is_valid=?,knowledge_id=? where id = ?");
				for(IpTagUserDefined ipTagUserDefined : ipTagUserDefineds) {
					pstm.setString(1, ipTagUserDefined.getTagValue());
					pstm.setString(2, ipTagUserDefined.getAddrFormat());
					pstm.setString(3, ipTagUserDefined.getIp1());
					pstm.setString(4, ipTagUserDefined.getIp2());
					pstm.setString(5, ipTagUserDefined.getStartIpBinary());
					pstm.setString(6, ipTagUserDefined.getEndIpBinary());
					pstm.setTimestamp(7, new java.sql.Timestamp(ipTagUserDefined.getOpTime().getTime()));
					pstm.setInt(8, ipTagUserDefined.getOpUser());
					pstm.setInt(9, ipTagUserDefined.getIsValid());
					pstm.setInt(10, ipTagUserDefined.getKnowledgeId());
					pstm.setLong(11, ipTagUserDefined.getId());
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
