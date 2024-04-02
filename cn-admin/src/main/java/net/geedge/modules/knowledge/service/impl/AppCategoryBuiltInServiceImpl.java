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
import net.geedge.modules.knowledge.dao.AppCategoryBuiltInDao;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;
import net.geedge.modules.knowledge.service.AppCategoryBuiltInService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:44
 */
@Service("AppCategoryBuiltInService")
public class AppCategoryBuiltInServiceImpl extends ServiceImpl<AppCategoryBuiltInDao, AppCategoryBuiltIn> implements AppCategoryBuiltInService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryList(Map<String, Object> params) {
        IPage page = this.page(new Query(AppCategoryBuiltIn.class).getPage(params));
        List<AppCategoryBuiltIn> appCategoryBuiltIns = this.baseMapper.queryPage(page, params);
        page.setRecords(appCategoryBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource) {
        return this.baseMapper.queryEntityDetail(resource);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<AppCategoryBuiltIn> appCategoryBuiltIns) {
		if(ObjectUtil.isNotEmpty(appCategoryBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_app_category_built_in (app_name,app_id,app_category,"
						+ "app_subcategory,app_risk,app_description,app_longname,app_technology,app_is_protocol,app_company,"
						+ "app_company_category,op_time,c_time,op_user,is_valid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(AppCategoryBuiltIn appCategoryBuiltIn : appCategoryBuiltIns) {
					pstm.setString(1, appCategoryBuiltIn.getAppName());
					pstm.setString(2, appCategoryBuiltIn.getAppId());
					pstm.setString(3, appCategoryBuiltIn.getAppCategory());
					pstm.setString(4, appCategoryBuiltIn.getAppSubcategory());
					if(ObjectUtil.isNotEmpty(appCategoryBuiltIn.getAppRisk())) {
						pstm.setInt(5, appCategoryBuiltIn.getAppRisk());
					}else {
						pstm.setNull(5, Types.INTEGER);
					}
					pstm.setString(6, appCategoryBuiltIn.getAppDescription());
					pstm.setString(7, appCategoryBuiltIn.getAppLongname());
					pstm.setString(8, appCategoryBuiltIn.getAppTechnology());
					if(ObjectUtil.isNotEmpty(appCategoryBuiltIn.getAppIsProtocol())) {
						pstm.setInt(9, appCategoryBuiltIn.getAppIsProtocol());
					}else {
						pstm.setNull(9, Types.INTEGER);
					}
					pstm.setString(10, appCategoryBuiltIn.getAppCompany());
					pstm.setString(11, appCategoryBuiltIn.getAppCompanyCategory());
					pstm.setTimestamp(12, new java.sql.Timestamp(appCategoryBuiltIn.getOpTime().getTime()));
					pstm.setTimestamp(13, new java.sql.Timestamp(appCategoryBuiltIn.getCTime().getTime()));
					pstm.setInt(14, appCategoryBuiltIn.getOpUser());
					pstm.setInt(15, appCategoryBuiltIn.getIsValid());
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
	public void modifyBatchJDBC(Connection conn, List<AppCategoryBuiltIn> appCategoryBuiltIns) {
		if(ObjectUtil.isNotEmpty(appCategoryBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_app_category_built_in set app_name =?,app_id=?,app_category=?,"
						+ "app_subcategory=?,app_risk=?,app_description=?,app_longname=?,app_technology=?,app_is_protocol=?,app_company=?,"
						+ "app_company_category=?,op_time=?,op_user=?,is_valid=? where id =?");
				for(AppCategoryBuiltIn appCategoryBuiltIn : appCategoryBuiltIns) {
					pstm.setString(1, appCategoryBuiltIn.getAppName());
					pstm.setString(2, appCategoryBuiltIn.getAppId());
					pstm.setString(3, appCategoryBuiltIn.getAppCategory());
					pstm.setString(4, appCategoryBuiltIn.getAppSubcategory());
					if(ObjectUtil.isNotEmpty(appCategoryBuiltIn.getAppRisk())) {
						pstm.setInt(5, appCategoryBuiltIn.getAppRisk());
					}else {
						pstm.setNull(5, Types.INTEGER);
					}
					pstm.setString(6, appCategoryBuiltIn.getAppDescription());
					pstm.setString(7, appCategoryBuiltIn.getAppLongname());
					pstm.setString(8, appCategoryBuiltIn.getAppTechnology());
					if(ObjectUtil.isNotEmpty(appCategoryBuiltIn.getAppIsProtocol())) {
						pstm.setInt(9, appCategoryBuiltIn.getAppIsProtocol());
					}else {
						pstm.setNull(9, Types.INTEGER);
					}
					pstm.setString(10, appCategoryBuiltIn.getAppCompany());
					pstm.setString(11, appCategoryBuiltIn.getAppCompanyCategory());
					pstm.setTimestamp(12, new java.sql.Timestamp(appCategoryBuiltIn.getOpTime().getTime()));
					pstm.setInt(13, appCategoryBuiltIn.getOpUser());
					pstm.setInt(14, appCategoryBuiltIn.getIsValid());
					pstm.setLong(15, appCategoryBuiltIn.getId());
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
