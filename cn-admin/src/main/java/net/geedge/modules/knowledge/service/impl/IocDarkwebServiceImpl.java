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
import net.geedge.modules.knowledge.dao.IocDarkwebDao;
import net.geedge.modules.knowledge.entity.IocDarkweb;
import net.geedge.modules.knowledge.service.IocDarkwebService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:02
 */
@Service("IocDarkwebService")
public class IocDarkwebServiceImpl extends ServiceImpl<IocDarkwebDao, IocDarkweb> implements IocDarkwebService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryList(Map<String, Object> params) {
        IPage page = this.page(new Query(IocDarkweb.class).getPage(params));
        List<IocDarkweb> iocDarkwebs = this.baseMapper.queryPage(page, params);
        page.setRecords(iocDarkwebs);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryIpEntityTag(String resource) {
        return this.baseMapper.queryIpEntityTag(resource);
    }

    @Override
    public Map<String, Object> queryDomainEntityTag(String resource, Boolean isFuzzyQuery) {
        return this.baseMapper.queryDomainEntityTag(resource, isFuzzyQuery);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<IocDarkweb> iocDarkwebs) {
		if(ObjectUtil.isNotEmpty(iocDarkwebs)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_ioc_darkweb (ioc_value,ioc_type,node_type,ipv6,isp,"
						+ "asn,operating_system,country,city,latitude,longtitude,tor_fingerprint,tor_dir_port,tor_or_port,tor_flags,tor_version,"
						+ "tor_nickname,tor_exit_policy,i2p_hash,i2p_method,i2p_version,i2p_is_floodfill,i2p_bandwidth,i2p_is_hidden,i2p_is_reachable,"
						+ "i2p_publish_time,mtproxy_port,mtproxy_secret,obfs4_port,obfs4_fingerprint,obfs4_cert,obfs4_iat_mode,snowflake_port,"
						+ "first_appear_time,last_appear_time,create_time,update_time) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(IocDarkweb iocDarkweb : iocDarkwebs) {
					pstm.setString(1, iocDarkweb.getIocValue());
					pstm.setString(2, iocDarkweb.getIocType());
					pstm.setString(3, iocDarkweb.getNodeType());
					pstm.setString(4, iocDarkweb.getIpv6());
					pstm.setString(5, iocDarkweb.getIsp());
					pstm.setString(6, iocDarkweb.getAsn());
					pstm.setString(7, iocDarkweb.getOperatingSystem());
					pstm.setString(8, iocDarkweb.getCountry());
					pstm.setString(9, iocDarkweb.getCity());
					pstm.setString(10, iocDarkweb.getLatitude());
					pstm.setString(11, iocDarkweb.getLongtitude());
					pstm.setString(12, iocDarkweb.getTorFingerprint());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getTorDirPort())) {
						pstm.setInt(13, iocDarkweb.getTorDirPort());
					}else {
						pstm.setNull(13,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(iocDarkweb.getTorOrPort())) {
						pstm.setInt(14, iocDarkweb.getTorOrPort());
					}else {
						pstm.setNull(14,Types.INTEGER);
					}
					pstm.setString(15, iocDarkweb.getTorFlags());
					pstm.setString(16, iocDarkweb.getTorVersion());
					pstm.setString(17, iocDarkweb.getTorNickname());
					pstm.setString(18, iocDarkweb.getTorExitPolicy());
					pstm.setString(19, iocDarkweb.getI2pHash());
					pstm.setString(20, iocDarkweb.getI2pMethod());
					pstm.setString(21, iocDarkweb.getI2pVersion());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getI2pIsFloodfill())) {
						pstm.setBoolean(22, iocDarkweb.getI2pIsFloodfill());
					}else {
						pstm.setNull(22,Types.BOOLEAN);
					}
					pstm.setString(23, iocDarkweb.getI2pBandwidth());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getI2pIsHidden())) {
						pstm.setBoolean(24, iocDarkweb.getI2pIsHidden());
					}else {
						pstm.setNull(24,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(iocDarkweb.getI2pIsReachable())) {
						pstm.setBoolean(25, iocDarkweb.getI2pIsReachable());
					}else {
						pstm.setNull(25,Types.BOOLEAN);
					}
					pstm.setString(26, iocDarkweb.getI2pPublishTime());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getMtproxyPort())) {
						pstm.setInt(27, iocDarkweb.getMtproxyPort());
					}else {
						pstm.setNull(27,Types.INTEGER);
					}
					pstm.setString(28, iocDarkweb.getMtproxySecret());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getObfs4Port())) {
						pstm.setInt(29, iocDarkweb.getObfs4Port());
					}else {
						pstm.setNull(29,Types.INTEGER);
					}
					pstm.setString(30, iocDarkweb.getObfs4Fingerprint());
					pstm.setString(31, iocDarkweb.getObfs4Cert());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getObfs4IatMode())) {
						pstm.setInt(32, iocDarkweb.getObfs4IatMode());
					}else {
						pstm.setNull(32,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(iocDarkweb.getSnowflakePort())) {
						pstm.setInt(33, iocDarkweb.getSnowflakePort());
					}else {
						pstm.setNull(33,Types.INTEGER);
					}
					pstm.setLong(34, iocDarkweb.getFirstAppearTime());
					pstm.setLong(35, iocDarkweb.getLastAppearTime());
					pstm.setLong(36, iocDarkweb.getCreateTime());
					pstm.setLong(37, iocDarkweb.getUpdateTime());
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
	public void modifyBatchJDBC(Connection conn, List<IocDarkweb> iocDarkwebs) {
		if(ObjectUtil.isNotEmpty(iocDarkwebs)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_ioc_darkweb set ioc_value=?,ioc_type=?,node_type=?,ipv6=?,isp=?,"
						+ "asn=?,operating_system=?,country=?,city=?,latitude=?,longtitude=?,tor_fingerprint=?,tor_dir_port=?,tor_or_port=?,tor_flags=?,tor_version=?,"
						+ "tor_nickname=?,tor_exit_policy=?,i2p_hash=?,i2p_method=?,i2p_version=?,i2p_is_floodfill=?,i2p_bandwidth=?,i2p_is_hidden=?,i2p_is_reachable=?,"
						+ "i2p_publish_time=?,mtproxy_port=?,mtproxy_secret=?,obfs4_port=?,obfs4_fingerprint=?,obfs4_cert=?,obfs4_iat_mode=?,snowflake_port=?,"
						+ "first_appear_time=?,last_appear_time=?,update_time=? where id =?");
				for(IocDarkweb iocDarkweb : iocDarkwebs) {
					pstm.setString(1, iocDarkweb.getIocValue());
					pstm.setString(2, iocDarkweb.getIocType());
					pstm.setString(3, iocDarkweb.getNodeType());
					pstm.setString(4, iocDarkweb.getIpv6());
					pstm.setString(5, iocDarkweb.getIsp());
					pstm.setString(6, iocDarkweb.getAsn());
					pstm.setString(7, iocDarkweb.getOperatingSystem());
					pstm.setString(8, iocDarkweb.getCountry());
					pstm.setString(9, iocDarkweb.getCity());
					pstm.setString(10, iocDarkweb.getLatitude());
					pstm.setString(11, iocDarkweb.getLongtitude());
					pstm.setString(12, iocDarkweb.getTorFingerprint());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getTorDirPort())) {
						pstm.setInt(13, iocDarkweb.getTorDirPort());
					}else {
						pstm.setNull(13,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(iocDarkweb.getTorOrPort())) {
						pstm.setInt(14, iocDarkweb.getTorOrPort());
					}else {
						pstm.setNull(14,Types.INTEGER);
					}
					pstm.setString(15, iocDarkweb.getTorFlags());
					pstm.setString(16, iocDarkweb.getTorVersion());
					pstm.setString(17, iocDarkweb.getTorNickname());
					pstm.setString(18, iocDarkweb.getTorExitPolicy());
					pstm.setString(19, iocDarkweb.getI2pHash());
					pstm.setString(20, iocDarkweb.getI2pMethod());
					pstm.setString(21, iocDarkweb.getI2pVersion());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getI2pIsFloodfill())) {
						pstm.setBoolean(22, iocDarkweb.getI2pIsFloodfill());
					}else {
						pstm.setNull(22,Types.BOOLEAN);
					}
					pstm.setString(23, iocDarkweb.getI2pBandwidth());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getI2pIsHidden())) {
						pstm.setBoolean(24, iocDarkweb.getI2pIsHidden());
					}else {
						pstm.setNull(24,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(iocDarkweb.getI2pIsReachable())) {
						pstm.setBoolean(25, iocDarkweb.getI2pIsReachable());
					}else {
						pstm.setNull(25,Types.BOOLEAN);
					}
					pstm.setString(26, iocDarkweb.getI2pPublishTime());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getMtproxyPort())) {
						pstm.setInt(27, iocDarkweb.getMtproxyPort());
					}else {
						pstm.setNull(27,Types.INTEGER);
					}
					pstm.setString(28, iocDarkweb.getMtproxySecret());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getObfs4Port())) {
						pstm.setInt(29, iocDarkweb.getObfs4Port());
					}else {
						pstm.setNull(29,Types.INTEGER);
					}
					pstm.setString(30, iocDarkweb.getObfs4Fingerprint());
					pstm.setString(31, iocDarkweb.getObfs4Cert());
					if(ObjectUtil.isNotEmpty(iocDarkweb.getObfs4IatMode())) {
						pstm.setInt(32, iocDarkweb.getObfs4IatMode());
					}else {
						pstm.setNull(32,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(iocDarkweb.getSnowflakePort())) {
						pstm.setInt(33, iocDarkweb.getSnowflakePort());
					}else {
						pstm.setNull(33,Types.INTEGER);
					}
					pstm.setLong(34, iocDarkweb.getFirstAppearTime());
					pstm.setLong(35, iocDarkweb.getLastAppearTime());
					pstm.setLong(36, iocDarkweb.getUpdateTime());
					pstm.setInt(37, iocDarkweb.getId());
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
	public Map<String, Object> queryDomainDetectionEntityDetail(String resource) {
		return this.baseMapper.queryDomainDetectionEntityDetail(resource);
	}

	@Override
	public Map<String, Object> queryIpDetectionEntityDetail(String resource) {
		return this.baseMapper.queryIpDetectionEntityDetail(resource);
	}
}
