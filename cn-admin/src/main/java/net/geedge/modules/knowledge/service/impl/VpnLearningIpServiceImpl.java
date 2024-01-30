package net.geedge.modules.knowledge.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.VpnLearningIpDao;
import net.geedge.modules.knowledge.entity.VpnLearningIp;
import net.geedge.modules.knowledge.service.VpnLearningIpService;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/9:46
 */
@Service("VpnLearningIpService")
public class VpnLearningIpServiceImpl extends ServiceImpl<VpnLearningIpDao, VpnLearningIp> implements VpnLearningIpService {
	private Log log = Log.get();
	
    @Override
    public List<Map> queryIpEntityTag(String resource) {
        return this.baseMapper.queryIpEntityTag(resource);
    }
    
    @Override
	public void saveBatchJDBC(Connection conn, List<VpnLearningIp> vpnLearningIps) {
		if(ObjectUtil.isNotEmpty(vpnLearningIps)){
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_vpn_learning_ip (addr_format,ip1,ip2,"
						+ "start_ip_binary,end_ip_binary,plugin_id,plugin_name,vpn_service_name,method,confidence,"
						+ "op_time,c_time,op_user,is_valid,knowledge_id) "
						+ "values (?,?,?,INET6_ATON(?),INET6_ATON(?),?,?,?,?,?,?,?,?,?,?)");
				for(VpnLearningIp vpnLearningIp : vpnLearningIps) {
					pstm.setString(1, vpnLearningIp.getAddrFormat());
					pstm.setString(2, vpnLearningIp.getIp1());
					pstm.setString(3, vpnLearningIp.getIp2());
					pstm.setString(4, vpnLearningIp.getStartIpBinary());
					pstm.setString(5, vpnLearningIp.getEndIpBinary());
					pstm.setLong(6, vpnLearningIp.getPluginId());
					pstm.setString(7, vpnLearningIp.getPluginName());
					pstm.setString(8, vpnLearningIp.getVpnServiceName());
					pstm.setString(9, vpnLearningIp.getMethod());
					pstm.setString(10, vpnLearningIp.getConfidence());
					pstm.setTimestamp(11, new java.sql.Timestamp(vpnLearningIp.getOpTime().getTime()));
					pstm.setTimestamp(12, new java.sql.Timestamp(vpnLearningIp.getCTime().getTime()));
					pstm.setInt(13, vpnLearningIp.getOpUser());
					pstm.setInt(14, vpnLearningIp.getIsValid());
					pstm.setInt(15, vpnLearningIp.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<VpnLearningIp> vpnLearningIps) {
		if(ObjectUtil.isNotEmpty(vpnLearningIps)){
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_vpn_learning_ip set addr_format=?,ip1=?,ip2=?,"
						+ "start_ip_binary=INET6_ATON(?),end_ip_binary=INET6_ATON(?),plugin_id=?,plugin_name=?,vpn_service_name=?,method=?,confidence=?,"
						+ "op_time=?,op_user=?,is_valid=?,knowledge_id=? where id =? ");
				for(VpnLearningIp vpnLearningIp : vpnLearningIps) {
					pstm.setString(1, vpnLearningIp.getAddrFormat());
					pstm.setString(2, vpnLearningIp.getIp1());
					pstm.setString(3, vpnLearningIp.getIp2());
					pstm.setString(4, vpnLearningIp.getStartIpBinary());
					pstm.setString(5, vpnLearningIp.getEndIpBinary());
					pstm.setLong(6, vpnLearningIp.getPluginId());
					pstm.setString(7, vpnLearningIp.getPluginName());
					pstm.setString(8, vpnLearningIp.getVpnServiceName());
					pstm.setString(9, vpnLearningIp.getMethod());
					pstm.setString(10, vpnLearningIp.getConfidence());
					pstm.setTimestamp(11, new java.sql.Timestamp(vpnLearningIp.getOpTime().getTime()));
					pstm.setInt(12, vpnLearningIp.getOpUser());
					pstm.setInt(13, vpnLearningIp.getIsValid());
					pstm.setInt(14, vpnLearningIp.getKnowledgeId());
					pstm.setLong(15, vpnLearningIp.getId());
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
