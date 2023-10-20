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
import net.geedge.modules.knowledge.dao.FqdnWhoIsBuiltInDao;
import net.geedge.modules.knowledge.entity.FqdnWhoIsBuiltIn;
import net.geedge.modules.knowledge.service.FqdnWhoIsBuiltInService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:59
 */
@Service("FqdnWhoIsBuiltInService")
public class FqdnWhoIsBuiltInServiceImpl extends ServiceImpl<FqdnWhoIsBuiltInDao, FqdnWhoIsBuiltIn> implements FqdnWhoIsBuiltInService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(FqdnWhoIsBuiltIn.class).getPage(params));
        List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(fqdnWhoIsBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource, Boolean isFuzzyQuery) {
        return this.baseMapper.queryEntityDetail(resource, isFuzzyQuery);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns) {
		if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_fqdn_who_is_built_in (fqdn,source,query_success,"
						+ "status_code,match_pattern,whois_domain,whois_update_date,whois_create_date,whois_expire_date,whois_email,whois_ns,"
						+ "whois_registrar_name,whois_registrant_org,whois_registrant_name,whois_registrant_street,whois_registrant_city,"
						+ "whois_registrant_state,whois_registrant_postcode,whois_registrant_country,whois_registrant_phone,create_time,"
						+ "update_time,knowledge_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(FqdnWhoIsBuiltIn fqdnWhoIsBuiltIn : fqdnWhoIsBuiltIns) {
					pstm.setString(1, fqdnWhoIsBuiltIn.getFqdn());
					pstm.setString(2, fqdnWhoIsBuiltIn.getSource());
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getQuerySuccess())) {
						pstm.setBoolean(3, fqdnWhoIsBuiltIn.getQuerySuccess());
					}else {
						pstm.setNull(3,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getStatusCode())) {
						pstm.setInt(4, fqdnWhoIsBuiltIn.getStatusCode());
					}else {
						pstm.setNull(4,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getMatchPattern())) {
						pstm.setInt(5, fqdnWhoIsBuiltIn.getMatchPattern());
					}else {
						pstm.setNull(5,Types.INTEGER);
					}
					pstm.setString(6, fqdnWhoIsBuiltIn.getWhoisDomain());
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getWhoisUpdateDate())) {
						pstm.setTimestamp(7, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getWhoisUpdateDate().getTime()));
					}else {
						pstm.setNull(7,Types.DATE);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getWhoisCreateDate())) {
						pstm.setTimestamp(8, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getWhoisCreateDate().getTime()));
					}else {
						pstm.setNull(8,Types.DATE);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getWhoisExpireDate())) {
						pstm.setTimestamp(9, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getWhoisExpireDate().getTime()));
					}else {
						pstm.setNull(9,Types.DATE);
					}
					pstm.setString(10, fqdnWhoIsBuiltIn.getWhoisEmail());
					pstm.setString(11, fqdnWhoIsBuiltIn.getWhoisNs());
					pstm.setString(12, fqdnWhoIsBuiltIn.getWhoisRegistrarName());
					pstm.setString(13, fqdnWhoIsBuiltIn.getWhoisRegistrantOrg());
					pstm.setString(14, fqdnWhoIsBuiltIn.getWhoisRegistrantName());
					pstm.setString(15, fqdnWhoIsBuiltIn.getWhoisRegistrantStreet());
					pstm.setString(16, fqdnWhoIsBuiltIn.getWhoisRegistrantCity());
					pstm.setString(17, fqdnWhoIsBuiltIn.getWhoisRegistrantState());
					pstm.setString(18, fqdnWhoIsBuiltIn.getWhoisRegistrantPostcode());
					pstm.setString(19, fqdnWhoIsBuiltIn.getWhoisRegistrantCountry());
					pstm.setString(20, fqdnWhoIsBuiltIn.getWhoisRegistrantPhone());
					pstm.setTimestamp(21, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getCreateTime().getTime()));
					pstm.setTimestamp(22, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(23, fqdnWhoIsBuiltIn.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns) {
		if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_fqdn_who_is_built_in set fqdn =? ,source=?,query_success=?,"
						+ "status_code=?,match_pattern=?,whois_domain=?,whois_update_date=?,whois_create_date=?,whois_expire_date=?,whois_email=?,whois_ns=?,"
						+ "whois_registrar_name=?,whois_registrant_org=?,whois_registrant_name=?,whois_registrant_street=?,whois_registrant_city=?,"
						+ "whois_registrant_state=?,whois_registrant_postcode=?,whois_registrant_country=?,whois_registrant_phone=?,"
						+ "update_time=?,knowledge_id=? where id = ?");
				for(FqdnWhoIsBuiltIn fqdnWhoIsBuiltIn : fqdnWhoIsBuiltIns) {
					pstm.setString(1, fqdnWhoIsBuiltIn.getFqdn());
					pstm.setString(2, fqdnWhoIsBuiltIn.getSource());
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getQuerySuccess())) {
						pstm.setBoolean(3, fqdnWhoIsBuiltIn.getQuerySuccess());
					}else {
						pstm.setNull(3,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getStatusCode())) {
						pstm.setInt(4, fqdnWhoIsBuiltIn.getStatusCode());
					}else {
						pstm.setNull(4,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getMatchPattern())) {
						pstm.setInt(5, fqdnWhoIsBuiltIn.getMatchPattern());
					}else {
						pstm.setNull(5,Types.INTEGER);
					}
					pstm.setString(6, fqdnWhoIsBuiltIn.getWhoisDomain());
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getWhoisUpdateDate())) {
						pstm.setTimestamp(7, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getWhoisUpdateDate().getTime()));
					}else {
						pstm.setNull(7,Types.DATE);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getWhoisCreateDate())) {
						pstm.setTimestamp(8, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getWhoisCreateDate().getTime()));
					}else {
						pstm.setNull(8,Types.DATE);
					}
					if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getWhoisExpireDate())) {
						pstm.setTimestamp(9, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getWhoisExpireDate().getTime()));
					}else {
						pstm.setNull(9,Types.DATE);
					}
					pstm.setString(10, fqdnWhoIsBuiltIn.getWhoisEmail());
					pstm.setString(11, fqdnWhoIsBuiltIn.getWhoisNs());
					pstm.setString(12, fqdnWhoIsBuiltIn.getWhoisRegistrarName());
					pstm.setString(13, fqdnWhoIsBuiltIn.getWhoisRegistrantOrg());
					pstm.setString(14, fqdnWhoIsBuiltIn.getWhoisRegistrantName());
					pstm.setString(15, fqdnWhoIsBuiltIn.getWhoisRegistrantStreet());
					pstm.setString(16, fqdnWhoIsBuiltIn.getWhoisRegistrantCity());
					pstm.setString(17, fqdnWhoIsBuiltIn.getWhoisRegistrantState());
					pstm.setString(18, fqdnWhoIsBuiltIn.getWhoisRegistrantPostcode());
					pstm.setString(19, fqdnWhoIsBuiltIn.getWhoisRegistrantCountry());
					pstm.setString(20, fqdnWhoIsBuiltIn.getWhoisRegistrantPhone());
					pstm.setTimestamp(21, new java.sql.Timestamp(fqdnWhoIsBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(22, fqdnWhoIsBuiltIn.getKnowledgeId());
					pstm.setInt(23, fqdnWhoIsBuiltIn.getId());
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
