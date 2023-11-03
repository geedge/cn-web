package net.geedge.modules.knowledge.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
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
import net.geedge.modules.knowledge.dao.FqdnIcpBuiltInDao;
import net.geedge.modules.knowledge.entity.FqdnIcpBuiltIn;
import net.geedge.modules.knowledge.service.FqdnIcpBuiltInService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:08
 */
@Service("FqdnIcpBuiltInService")
public class FqdnIcpBuiltInServiceImpl extends ServiceImpl<FqdnIcpBuiltInDao, FqdnIcpBuiltIn> implements FqdnIcpBuiltInService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(FqdnIcpBuiltIn.class).getPage(params));
        List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(fqdnIcpBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource, Boolean isFuzzyQuery) {
        return this.baseMapper.queryEntityDetail(resource, isFuzzyQuery);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<FqdnIcpBuiltIn> fqdnIcpBuiltIns) {
		if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_fqdn_icp_built_in (fqdn,source,query_success,is_accessible,"
						+ "status_code,match_pattern,icp_owner,icp_company_name,icp_company_type,icp_main_page,icp_site_license,icp_site_name,"
						+ "icp_verify_time,create_time,update_time,knowledge_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(FqdnIcpBuiltIn fqdnIcpBuiltIn : fqdnIcpBuiltIns) {
					pstm.setString(1, fqdnIcpBuiltIn.getFqdn());
					pstm.setString(2, fqdnIcpBuiltIn.getSource());
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getQuerySuccess())) {
						pstm.setBoolean(3, fqdnIcpBuiltIn.getQuerySuccess());
					}else {
						pstm.setNull(3,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getIsAccessible())) {
						pstm.setInt(4, fqdnIcpBuiltIn.getIsAccessible());
					}else {
						pstm.setNull(4,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getStatusCode())) {
						pstm.setInt(5, fqdnIcpBuiltIn.getStatusCode());
					}else {
						pstm.setNull(5,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getMatchPattern())) {
						pstm.setInt(6, fqdnIcpBuiltIn.getMatchPattern());
					}else {
						pstm.setNull(6,Types.INTEGER);
					}
					pstm.setString(7, fqdnIcpBuiltIn.getIcpOwner());
					pstm.setString(8, fqdnIcpBuiltIn.getIcpCompanyName());
					pstm.setString(9, fqdnIcpBuiltIn.getIcpCompanyType());
					pstm.setString(10, fqdnIcpBuiltIn.getIcpMainPage());
					pstm.setString(11, fqdnIcpBuiltIn.getIcpSiteLicense());
					pstm.setString(12, fqdnIcpBuiltIn.getIcpSiteName());
					pstm.setString(13, fqdnIcpBuiltIn.getIcpVerifyTime());
					pstm.setTimestamp(14, new java.sql.Timestamp(fqdnIcpBuiltIn.getCreateTime().getTime()));
					pstm.setTimestamp(15, new java.sql.Timestamp(fqdnIcpBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(16, fqdnIcpBuiltIn.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<FqdnIcpBuiltIn> fqdnIcpBuiltIns) {
		if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_fqdn_icp_built_in set fqdn=?,source=?,query_success=?,is_accessible=?,"
						+ "status_code=?,match_pattern=?,icp_owner=?,icp_company_name=?,icp_company_type=?,icp_main_page=?,icp_site_license=?,icp_site_name=?,"
						+ "icp_verify_time=?,update_time=?,knowledge_id=? where id =?");
				for(FqdnIcpBuiltIn fqdnIcpBuiltIn : fqdnIcpBuiltIns) {
					pstm.setString(1, fqdnIcpBuiltIn.getFqdn());
					pstm.setString(2, fqdnIcpBuiltIn.getSource());
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getQuerySuccess())) {
						pstm.setBoolean(3, fqdnIcpBuiltIn.getQuerySuccess());
					}else {
						pstm.setNull(3,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getIsAccessible())) {
						pstm.setInt(4, fqdnIcpBuiltIn.getIsAccessible());
					}else {
						pstm.setNull(4,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getStatusCode())) {
						pstm.setInt(5, fqdnIcpBuiltIn.getStatusCode());
					}else {
						pstm.setNull(5,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getMatchPattern())) {
						pstm.setInt(6, fqdnIcpBuiltIn.getMatchPattern());
					}else {
						pstm.setNull(6,Types.INTEGER);
					}
					pstm.setString(7, fqdnIcpBuiltIn.getIcpOwner());
					pstm.setString(8, fqdnIcpBuiltIn.getIcpCompanyName());
					pstm.setString(9, fqdnIcpBuiltIn.getIcpCompanyType());
					pstm.setString(10, fqdnIcpBuiltIn.getIcpMainPage());
					pstm.setString(11, fqdnIcpBuiltIn.getIcpSiteLicense());
					pstm.setString(12, fqdnIcpBuiltIn.getIcpSiteName());
					pstm.setString(13, fqdnIcpBuiltIn.getIcpVerifyTime());
					pstm.setTimestamp(14, new java.sql.Timestamp(fqdnIcpBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(15, fqdnIcpBuiltIn.getKnowledgeId());
					pstm.setInt(16, fqdnIcpBuiltIn.getId());
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
