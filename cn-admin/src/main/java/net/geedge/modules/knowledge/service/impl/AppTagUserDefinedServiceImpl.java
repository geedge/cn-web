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
import net.geedge.modules.knowledge.dao.AppTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.service.AppTagUserDefinedService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:50
 */
@Service("AppTagUserDefinedService")
public class AppTagUserDefinedServiceImpl extends ServiceImpl<AppTagUserDefinedDao, AppTagUserDefined> implements AppTagUserDefinedService {
	private Log log = Log.get();

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(AppTagUserDefined.class).getPage(params));
        List<AppTagUserDefined> appTagUserDefineds = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(appTagUserDefineds);
        return new PageUtils(page);
    }

    @Override
    public List<Map> queryAppEntityTag(String resource) {
        return this.baseMapper.queryAppEntityTag(resource);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<AppTagUserDefined> appTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(appTagUserDefineds)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_app_tag_user_defined (tag_value,app_name,"
						+ "    	op_time,c_time,op_user,is_valid,knowledge_id) values (?,?,?,?,?,?,?)");
				for(AppTagUserDefined appTagUserDefined : appTagUserDefineds) {
					pstm.setString(1, appTagUserDefined.getTagValue());
					pstm.setString(2, appTagUserDefined.getAppName());
					pstm.setTimestamp(3, new java.sql.Timestamp(appTagUserDefined.getOpTime().getTime()));
					pstm.setTimestamp(4, new java.sql.Timestamp(appTagUserDefined.getCTime().getTime()));
					pstm.setInt(5, appTagUserDefined.getOpUser());
					pstm.setInt(6, ObjectUtil.isEmpty(appTagUserDefined.getIsValid())?1:appTagUserDefined.getIsValid());
					pstm.setInt(7, appTagUserDefined.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<AppTagUserDefined> appTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(appTagUserDefineds)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_app_tag_user_defined set tag_value=?,app_name=?,"
						+ "    	op_time=?,op_user=?,is_valid=?,knowledge_id=? where id =?");
				for(AppTagUserDefined appTagUserDefined : appTagUserDefineds) {
					pstm.setString(1, appTagUserDefined.getTagValue());
					pstm.setString(2, appTagUserDefined.getAppName());
					pstm.setTimestamp(3, new java.sql.Timestamp(appTagUserDefined.getOpTime().getTime()));
					pstm.setInt(4, appTagUserDefined.getOpUser());
					pstm.setInt(5, appTagUserDefined.getIsValid());
					pstm.setInt(6, appTagUserDefined.getKnowledgeId());
					pstm.setLong(7, appTagUserDefined.getId());
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
