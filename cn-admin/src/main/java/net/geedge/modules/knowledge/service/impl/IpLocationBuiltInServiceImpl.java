package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.IpLocationBuiltInDao;
import net.geedge.modules.knowledge.entity.IpLocationBuiltIn;
import net.geedge.modules.knowledge.service.IpLocationBuiltInService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:53
 */
@Service("IpLocationBuiltInService")
public class IpLocationBuiltInServiceImpl extends ServiceImpl<IpLocationBuiltInDao, IpLocationBuiltIn> implements IpLocationBuiltInService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryList(Map<String, Object> params) {
        IPage page = this.page(new Query(IpLocationBuiltIn.class).getPage(params));
        List<IpLocationBuiltIn> ipLocationBuiltIns = this.baseMapper.queryPage(page, params);
        page.setRecords(ipLocationBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource) {
        return this.baseMapper.queryEntityDetail(resource);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<IpLocationBuiltIn> ipLocationBuiltIns) {
		if(ObjectUtil.isNotEmpty(ipLocationBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_ip_location_built_in (addr_type,start_ip,end_ip,minip,"
						+ "maxip,continent,areacode,country,province,city,lngwgs,latwgs,owner,isp,asnumber,timezone,"
						+ "is_valid,op_time,op_user) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(IpLocationBuiltIn ipLocationBuiltIn : ipLocationBuiltIns) {
					pstm.setInt(1, ipLocationBuiltIn.getAddrType());
					pstm.setString(2, ipLocationBuiltIn.getStartIp());
					pstm.setString(3, ipLocationBuiltIn.getEndIp());
					pstm.setString(4, ipLocationBuiltIn.getMinip());
					pstm.setString(5, ipLocationBuiltIn.getMaxip());
					pstm.setString(6, ipLocationBuiltIn.getContinent());
					pstm.setString(7, ipLocationBuiltIn.getAreacode());
					pstm.setString(8, ipLocationBuiltIn.getCountry());
					pstm.setString(9, ipLocationBuiltIn.getProvince());
					pstm.setString(10, ipLocationBuiltIn.getCity());
					if(ObjectUtil.isNotEmpty(ipLocationBuiltIn.getLngwgs())) {
						pstm.setDouble(11, ipLocationBuiltIn.getLngwgs());
					}else {
						pstm.setNull(11,Types.DOUBLE);
					}
					if(ObjectUtil.isNotEmpty(ipLocationBuiltIn.getLatwgs())) {
						pstm.setDouble(12, ipLocationBuiltIn.getLatwgs());
					}else {
						pstm.setNull(12,Types.DOUBLE);
					}
					pstm.setString(13, ipLocationBuiltIn.getOwner());
					pstm.setString(14, ipLocationBuiltIn.getIsp());
					pstm.setString(15, ipLocationBuiltIn.getAsnumber());
					pstm.setString(16, ipLocationBuiltIn.getTimezone());
					pstm.setInt(17, ObjectUtil.isEmpty(ipLocationBuiltIn.getIsValid())?1:ipLocationBuiltIn.getIsValid());
					pstm.setTimestamp(18, new java.sql.Timestamp(ipLocationBuiltIn.getOpTime().getTime()));
					pstm.setInt(19, ipLocationBuiltIn.getOpUser());
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
	public void modifyBatchJDBC(Connection conn, List<IpLocationBuiltIn> ipLocationBuiltIns) {
		if(ObjectUtil.isNotEmpty(ipLocationBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_ip_location_built_in set addr_type=?,start_ip=?,end_ip=?,minip=?,"
						+ "maxip=?,continent=?,areacode=?,country=?,province=?,city=?,lngwgs=?,latwgs=?,owner=?,isp=?,asnumber=?,timezone=?,"
						+ "is_valid=?,op_time=?,op_user=? where id = ?");
				for(IpLocationBuiltIn ipLocationBuiltIn : ipLocationBuiltIns) {
					pstm.setInt(1, ipLocationBuiltIn.getAddrType());
					pstm.setString(2, ipLocationBuiltIn.getStartIp());
					pstm.setString(3, ipLocationBuiltIn.getEndIp());
					pstm.setString(4, ipLocationBuiltIn.getMinip());
					pstm.setString(5, ipLocationBuiltIn.getMaxip());
					pstm.setString(6, ipLocationBuiltIn.getContinent());
					pstm.setString(7, ipLocationBuiltIn.getAreacode());
					pstm.setString(8, ipLocationBuiltIn.getCountry());
					pstm.setString(9, ipLocationBuiltIn.getProvince());
					pstm.setString(10, ipLocationBuiltIn.getCity());
					if(ObjectUtil.isNotEmpty(ipLocationBuiltIn.getLngwgs())) {
						pstm.setDouble(11, ipLocationBuiltIn.getLngwgs());
					}else {
						pstm.setNull(11,Types.DOUBLE);
					}
					if(ObjectUtil.isNotEmpty(ipLocationBuiltIn.getLatwgs())) {
						pstm.setDouble(12, ipLocationBuiltIn.getLatwgs());
					}else {
						pstm.setNull(12,Types.DOUBLE);
					}
					pstm.setString(13, ipLocationBuiltIn.getOwner());
					pstm.setString(14, ipLocationBuiltIn.getIsp());
					pstm.setString(15, ipLocationBuiltIn.getAsnumber());
					pstm.setString(16, ipLocationBuiltIn.getTimezone());
					pstm.setInt(17, ipLocationBuiltIn.getIsValid());
					pstm.setTimestamp(18, new java.sql.Timestamp(ipLocationBuiltIn.getOpTime().getTime()));
					pstm.setInt(19, ipLocationBuiltIn.getOpUser());
					pstm.setLong(20, ipLocationBuiltIn.getId());
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
