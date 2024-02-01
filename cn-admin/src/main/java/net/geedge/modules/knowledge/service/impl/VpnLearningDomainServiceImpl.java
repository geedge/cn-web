package net.geedge.modules.knowledge.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.VpnLearningDomainDao;
import net.geedge.modules.knowledge.entity.VpnLearningDomain;
import net.geedge.modules.knowledge.service.VpnLearningDomainService;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:37
 */
@Service("vpnLearningDomainService")
public class VpnLearningDomainServiceImpl extends ServiceImpl<VpnLearningDomainDao, VpnLearningDomain> implements VpnLearningDomainService {
	private Log log = Log.get();
    @Override
    public List<Map> queryDomainEntityTag(String resource) {
        return this.baseMapper.queryDomainEntityTag(resource);
    }

	@Override
	public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
		IPage page = this.page(new Query(VpnLearningDomain.class).getPage(params));
		List<VpnLearningDomain> vpnLearningDomains = this.baseMapper.queryPageByKnowledgeId(page, params);
		page.setRecords(vpnLearningDomains);
		return new PageUtils(page);
	}

    @Override
	public void saveBatchJDBC(Connection conn, List<VpnLearningDomain> vpnLearningDomains) {
		if(ObjectUtil.isNotEmpty(vpnLearningDomains)){
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_vpn_learning_domain (domain,plugin_id,plugin_name,op_time,"
						+ "c_time,op_user,is_valid,knowledge_id,vpn_service_name,method,confidence) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?)");
				for(VpnLearningDomain vpnLearningDomain : vpnLearningDomains) {
					pstm.setString(1, vpnLearningDomain.getDomain());
					pstm.setLong(2, vpnLearningDomain.getPluginId());
					pstm.setString(3, vpnLearningDomain.getPluginName());
					pstm.setTimestamp(4, new java.sql.Timestamp(vpnLearningDomain.getOpTime().getTime()));
					pstm.setTimestamp(5, new java.sql.Timestamp(vpnLearningDomain.getOpTime().getTime()));
					pstm.setInt(6, vpnLearningDomain.getOpUser());
					pstm.setInt(7, vpnLearningDomain.getIsValid());
					pstm.setInt(8, vpnLearningDomain.getKnowledgeId());
					pstm.setString(9, vpnLearningDomain.getVpnServiceName());
					pstm.setString(10, vpnLearningDomain.getMethod());
					pstm.setString(11, vpnLearningDomain.getConfidence());
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
	public void modifyBatchJDBC(Connection conn, List<VpnLearningDomain> vpnLearningDomains) {
		if(ObjectUtil.isNotEmpty(vpnLearningDomains)){
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_vpn_learning_domain set domain=?,plugin_id=?,plugin_name=?,op_time=?,"
						+ "op_user=?,is_valid=?,knowledge_id=?,vpn_service_name=?,method=?,confidence=? where id =? ");
				for(VpnLearningDomain vpnLearningDomain : vpnLearningDomains) {
					pstm.setString(1, vpnLearningDomain.getDomain());
					pstm.setLong(2, vpnLearningDomain.getPluginId());
					pstm.setString(3, vpnLearningDomain.getPluginName());
					pstm.setTimestamp(4, new java.sql.Timestamp(vpnLearningDomain.getOpTime().getTime()));
					pstm.setInt(5, vpnLearningDomain.getOpUser());
					pstm.setInt(6, vpnLearningDomain.getIsValid());
					pstm.setInt(7, vpnLearningDomain.getKnowledgeId());
					pstm.setString(8, vpnLearningDomain.getVpnServiceName());
					pstm.setString(9, vpnLearningDomain.getMethod());
					pstm.setString(10, vpnLearningDomain.getConfidence());
					pstm.setLong(11, vpnLearningDomain.getId());
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
