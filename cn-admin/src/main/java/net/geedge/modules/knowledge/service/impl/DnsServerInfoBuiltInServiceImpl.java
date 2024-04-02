package net.geedge.modules.knowledge.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.DnsServerInfoBuiltInDao;
import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;
import net.geedge.modules.knowledge.service.DnsServerInfoBuiltInService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service("dnsServerInfoBuiltInService")
public class DnsServerInfoBuiltInServiceImpl extends ServiceImpl<DnsServerInfoBuiltInDao,DnsServerInfoBuiltIn> implements DnsServerInfoBuiltInService{
	private Log log = Log.get();
	
    @Override
    public Map<String, Object> queryIpEntityTag(String resource) {
        return this.baseMapper.queryIpEntityTag(resource);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns) {
		if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_dns_server_info_built_in (ip_addr,location_country,location_province,location_region,asn,\r\n"
						+ "    	dns_server_role,orgnization,operation_system,dns_software,dnssec_support,dot_support,doh_support,\r\n"
						+ "    	root_mirror_locations,create_time,update_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(DnsServerInfoBuiltIn dnsServerInfoBuiltIn : dnsServerInfoBuiltIns) {
					pstm.setString(1, dnsServerInfoBuiltIn.getIpAddr());
					pstm.setString(2, dnsServerInfoBuiltIn.getLocationCountry());
					pstm.setString(3, dnsServerInfoBuiltIn.getLocationProvince());
					pstm.setString(4, dnsServerInfoBuiltIn.getLocationRegion());
					pstm.setString(5, dnsServerInfoBuiltIn.getAsn());
					pstm.setString(6, dnsServerInfoBuiltIn.getDnsServerRole());
					pstm.setString(7, dnsServerInfoBuiltIn.getOrgnization());
					pstm.setString(8, dnsServerInfoBuiltIn.getOperationSystem());
					pstm.setString(9, dnsServerInfoBuiltIn.getDnsSoftware());
					if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getDnssecSupport())) {
						pstm.setInt(10, dnsServerInfoBuiltIn.getDnssecSupport());
					}else {
						pstm.setNull(10,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getDotSupport())) {
						pstm.setInt(11, dnsServerInfoBuiltIn.getDotSupport());
					}else {
						pstm.setNull(11,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getDohSupport())) {
						pstm.setInt(12, dnsServerInfoBuiltIn.getDohSupport());
					}else {
						pstm.setNull(12,Types.INTEGER);
					}
					pstm.setString(13, dnsServerInfoBuiltIn.getRootMirrorLocations());
					pstm.setTimestamp(14, new java.sql.Timestamp(dnsServerInfoBuiltIn.getCreateTime().getTime()));
					pstm.setTimestamp(15, new java.sql.Timestamp(dnsServerInfoBuiltIn.getUpdateTime().getTime()));
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
	public void modifyBatchJDBC(Connection conn, List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns) {
		if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_dns_server_info_built_in set ip_addr =?,location_country =?,location_province =?,location_region=?,"
						+ "asn =?,dns_server_role =?,orgnization =?,operation_system=?,dns_software=?,dnssec_support=?,dot_support=?,doh_support=?,"
						+ "root_mirror_locations =?,update_time=? where id = ?");
				for(DnsServerInfoBuiltIn dnsServerInfoBuiltIn : dnsServerInfoBuiltIns) {
					pstm.setString(1, dnsServerInfoBuiltIn.getIpAddr());
					pstm.setString(2, dnsServerInfoBuiltIn.getLocationCountry());
					pstm.setString(3, dnsServerInfoBuiltIn.getLocationProvince());
					pstm.setString(4, dnsServerInfoBuiltIn.getLocationRegion());
					pstm.setString(5, dnsServerInfoBuiltIn.getAsn());
					pstm.setString(6, dnsServerInfoBuiltIn.getDnsServerRole());
					pstm.setString(7, dnsServerInfoBuiltIn.getOrgnization());
					pstm.setString(8, dnsServerInfoBuiltIn.getOperationSystem());
					pstm.setString(9, dnsServerInfoBuiltIn.getDnsSoftware());
					if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getDnssecSupport())) {
						pstm.setInt(10, dnsServerInfoBuiltIn.getDnssecSupport());
					}else {
						pstm.setNull(10,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getDotSupport())) {
						pstm.setInt(11, dnsServerInfoBuiltIn.getDotSupport());
					}else {
						pstm.setNull(11,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getDohSupport())) {
						pstm.setInt(12, dnsServerInfoBuiltIn.getDohSupport());
					}else {
						pstm.setNull(12,Types.INTEGER);
					}
					pstm.setString(13, dnsServerInfoBuiltIn.getRootMirrorLocations());
					pstm.setTimestamp(14, new java.sql.Timestamp(dnsServerInfoBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(15,dnsServerInfoBuiltIn.getId());
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
