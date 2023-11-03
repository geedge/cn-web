package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.FqdnCategoryBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnCategoryBuiltIn;
import net.geedge.modules.knowledge.service.FqdnCategoryBuiltInService;
import org.springframework.stereotype.Service;
import net.geedge.common.exception.CNException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:18
 */
@Service("FqdnCategoryBuiltInService")
public class FqdnCategoryBuiltInServiceImpl extends ServiceImpl<FqdnCategoryBuiltInDao, FqdnCategoryBuiltIn> implements FqdnCategoryBuiltInService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(FqdnCategoryBuiltIn.class).getPage(params));
        List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(fqdnCategoryBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource,Boolean isFuzzyQuery) {
        return this.baseMapper.queryEntityDetail(resource,isFuzzyQuery);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns) {
		if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_fqdn_category_built_in (fqdn,source,query_success,"
						+ "match_pattern,reputation_score,reputation_level,category_id,category_name,category_group,category_conf,"
						+ "is_a1_cat,status_code,submit_user,create_time,update_time,knowledge_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(FqdnCategoryBuiltIn fqdnCategoryBuiltIn : fqdnCategoryBuiltIns) {
					pstm.setString(1, fqdnCategoryBuiltIn.getFqdn());
					pstm.setString(2, fqdnCategoryBuiltIn.getSource());
					pstm.setBoolean(3, fqdnCategoryBuiltIn.getQuerySuccess());
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getMatchPattern())) {
						pstm.setInt(4, fqdnCategoryBuiltIn.getMatchPattern());
					}else {
						pstm.setNull(4,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getReputationScore())) {
						pstm.setInt(5, fqdnCategoryBuiltIn.getReputationScore());
					}else {
						pstm.setNull(5,Types.INTEGER);
					}
					pstm.setString(6, fqdnCategoryBuiltIn.getReputationLevel());
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getCategoryId())) {
						pstm.setInt(7, fqdnCategoryBuiltIn.getCategoryId());
					}else {
						pstm.setNull(7,Types.INTEGER);
					}
					pstm.setString(8, fqdnCategoryBuiltIn.getCategoryName());
					pstm.setString(9, fqdnCategoryBuiltIn.getCategoryGroup());
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getCategoryConf())) {
						pstm.setInt(10, fqdnCategoryBuiltIn.getCategoryConf());
					}else {
						pstm.setNull(10,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getIsA1Cat())) {
						pstm.setBoolean(11, fqdnCategoryBuiltIn.getIsA1Cat());
					}else {
						pstm.setNull(11,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getStatusCode())) {
						pstm.setInt(12, fqdnCategoryBuiltIn.getStatusCode());
					}else {
						pstm.setNull(12,Types.INTEGER);
					}
					pstm.setString(13, fqdnCategoryBuiltIn.getSubmitUser());
					pstm.setTimestamp(14, new java.sql.Timestamp(fqdnCategoryBuiltIn.getCreateTime().getTime()));
					pstm.setTimestamp(15, new java.sql.Timestamp(fqdnCategoryBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(16, fqdnCategoryBuiltIn.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns) {
		if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_fqdn_category_built_in set fqdn=?,source=?,query_success=?,"
						+ "match_pattern=?,reputation_score=?,reputation_level=?,category_id=?,category_name=?,category_group=?,category_conf=?,"
						+ "is_a1_cat=?,status_code=?,submit_user=?,update_time=?,knowledge_id=? where id = ?");
				for(FqdnCategoryBuiltIn fqdnCategoryBuiltIn : fqdnCategoryBuiltIns) {
					pstm.setString(1, fqdnCategoryBuiltIn.getFqdn());
					pstm.setString(2, fqdnCategoryBuiltIn.getSource());
					pstm.setBoolean(3, fqdnCategoryBuiltIn.getQuerySuccess());
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getMatchPattern())) {
						pstm.setInt(4, fqdnCategoryBuiltIn.getMatchPattern());
					}else {
						pstm.setNull(4,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getReputationScore())) {
						pstm.setInt(5, fqdnCategoryBuiltIn.getReputationScore());
					}else {
						pstm.setNull(5,Types.INTEGER);
					}
					pstm.setString(6, fqdnCategoryBuiltIn.getReputationLevel());
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getCategoryId())) {
						pstm.setInt(7, fqdnCategoryBuiltIn.getCategoryId());
					}else {
						pstm.setNull(7,Types.INTEGER);
					}
					pstm.setString(8, fqdnCategoryBuiltIn.getCategoryName());
					pstm.setString(9, fqdnCategoryBuiltIn.getCategoryGroup());
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getCategoryConf())) {
						pstm.setInt(10, fqdnCategoryBuiltIn.getCategoryConf());
					}else {
						pstm.setNull(10,Types.INTEGER);
					}
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getIsA1Cat())) {
						pstm.setBoolean(11, fqdnCategoryBuiltIn.getIsA1Cat());
					}else {
						pstm.setNull(11,Types.BOOLEAN);
					}
					if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getStatusCode())) {
						pstm.setInt(12, fqdnCategoryBuiltIn.getStatusCode());
					}else {
						pstm.setNull(12,Types.INTEGER);
					}
					pstm.setString(13, fqdnCategoryBuiltIn.getSubmitUser());
					pstm.setTimestamp(14, new java.sql.Timestamp(fqdnCategoryBuiltIn.getUpdateTime().getTime()));
					pstm.setInt(15, fqdnCategoryBuiltIn.getKnowledgeId());
					pstm.setInt(16, fqdnCategoryBuiltIn.getId());
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
