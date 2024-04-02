package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.Psiphon3IpDao;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;
import net.geedge.modules.knowledge.service.Psiphon3IpService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:18
 */
@Service("Psiphon3Ip")
public class Psiphon3IpServiceImpl extends ServiceImpl<Psiphon3IpDao, Psiphon3Ip> implements Psiphon3IpService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(Psiphon3Ip.class).getPage(params));
        List<Psiphon3Ip> psiphon3Ips = this.baseMapper.queryPageByKnowledgeId(page, params);
		psiphon3Ips.forEach(ip -> {ip.setStartIpBinary(""); ip.setEndIpBinary("");});
        page.setRecords(psiphon3Ips);
        return new PageUtils(page);
    }

	@Override
	public void saveBatchForBinary(List<Psiphon3Ip> psiphon3Ips) {
		if(ObjectUtil.isNotEmpty(psiphon3Ips)) {
			this.baseMapper.insertBatchForBinary(psiphon3Ips);
		}
	}

	@Override
	public void modifyBatchForBinary(List<Psiphon3Ip> psiphon3Ips) {
		if(ObjectUtil.isNotEmpty(psiphon3Ips)) {
			this.baseMapper.updateBatchForBinary(psiphon3Ips);
		}
	}

	@Override
	public Map<String, Object> queryIpEntityTag(String resource) {
		return this.baseMapper.queryIpEntityTag(resource);
	}

	@Override
	public void saveBatchJDBC(Connection conn, List<Psiphon3Ip> psiphon3Ips) {
		if(ObjectUtil.isNotEmpty(psiphon3Ips)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_psiphon3_ip (addr_format,ip1,ip2,start_ip_binary,"
						+ "end_ip_binary,op_time,c_time,op_user,is_valid,knowledge_id,type,method,confidence,confidence_level)"
						+ "values (?,?,?,INET6_ATON(?),INET6_ATON(?),?,?,?,?,?,?,?,?,?)");
				for(Psiphon3Ip psiphon3Ip : psiphon3Ips) {
					pstm.setString(1, psiphon3Ip.getAddrFormat());
					pstm.setString(2, psiphon3Ip.getIp1());
					pstm.setString(3, psiphon3Ip.getIp2());
					pstm.setString(4, psiphon3Ip.getStartIpBinary());
					pstm.setString(5, psiphon3Ip.getEndIpBinary());
					pstm.setTimestamp(6, new java.sql.Timestamp(psiphon3Ip.getOpTime().getTime()));
					pstm.setTimestamp(7, new java.sql.Timestamp(psiphon3Ip.getCTime().getTime()));
					pstm.setInt(8, psiphon3Ip.getOpUser());
					pstm.setInt(9, ObjectUtil.isEmpty(psiphon3Ip.getIsValid())?1:psiphon3Ip.getIsValid());
					pstm.setInt(10, psiphon3Ip.getKnowledgeId());
					pstm.setInt(11, psiphon3Ip.getType());
					pstm.setString(12, psiphon3Ip.getMethod());
					pstm.setInt(13, psiphon3Ip.getConfidence());
					pstm.setString(14, psiphon3Ip.getConfidenceLevel());
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
	public void modifyBatchJDBC(Connection conn, List<Psiphon3Ip> psiphon3Ips) {
		if(ObjectUtil.isNotEmpty(psiphon3Ips)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_psiphon3_ip set addr_format=?,ip1=?,ip2=?,start_ip_binary=INET6_ATON(?),"
						+ "end_ip_binary=INET6_ATON(?),op_time=?,"
						+ "op_user=?,is_valid=?,knowledge_id=?,type=?,method=?,confidence=?,confidence_level=? where id =?");
				for(Psiphon3Ip psiphon3Ip : psiphon3Ips) {
					pstm.setString(1, psiphon3Ip.getAddrFormat());
					pstm.setString(2, psiphon3Ip.getIp1());
					pstm.setString(3, psiphon3Ip.getIp2());
					pstm.setString(4, psiphon3Ip.getStartIpBinary());
					pstm.setString(5, psiphon3Ip.getEndIpBinary());
					pstm.setTimestamp(6, new java.sql.Timestamp(psiphon3Ip.getOpTime().getTime()));
					pstm.setInt(7, psiphon3Ip.getOpUser());
					pstm.setInt(8, psiphon3Ip.getIsValid());
					pstm.setInt(9, psiphon3Ip.getKnowledgeId());
					pstm.setInt(10, psiphon3Ip.getType());
					pstm.setString(11, psiphon3Ip.getMethod());
					pstm.setInt(12, psiphon3Ip.getConfidence());
					pstm.setString(13, psiphon3Ip.getConfidenceLevel());
					pstm.setLong(14, psiphon3Ip.getId());
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
