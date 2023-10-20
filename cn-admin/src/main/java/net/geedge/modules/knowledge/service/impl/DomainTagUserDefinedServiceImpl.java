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
import net.geedge.modules.knowledge.dao.DomainTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.DomainTagUserDefined;
import net.geedge.modules.knowledge.service.DomainTagUserDefinedService;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:52
 */
@Service("DomainTagUserDefinedService")
public class DomainTagUserDefinedServiceImpl extends ServiceImpl<DomainTagUserDefinedDao, DomainTagUserDefined> implements DomainTagUserDefinedService {
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(DomainTagUserDefined.class).getPage(params));
        List<DomainTagUserDefined> domainTagUserDefineds = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(domainTagUserDefineds);
        return new PageUtils(page);
    }


    @Override
    public List<Map> queryDomainEntityTag(String resource) {
        return this.baseMapper.queryDomainEntityTag(resource);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<DomainTagUserDefined> domainTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(domainTagUserDefineds)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_domain_tag_user_defined (tag_value,domain,"
						+ "op_time,c_time,op_user,is_valid,knowledge_id) values (?,?,?,?,?,?,?)");
				for(DomainTagUserDefined domainTagUserDefined : domainTagUserDefineds) {
					pstm.setString(1, domainTagUserDefined.getTagValue());
					pstm.setString(2, domainTagUserDefined.getDomain());
					pstm.setTimestamp(3, new java.sql.Timestamp(domainTagUserDefined.getOpTime().getTime()));
					pstm.setTimestamp(4, new java.sql.Timestamp(domainTagUserDefined.getCTime().getTime()));
					pstm.setInt(5, domainTagUserDefined.getOpUser());
					pstm.setInt(6, ObjectUtil.isEmpty(domainTagUserDefined.getIsValid())?1:domainTagUserDefined.getIsValid());
					pstm.setInt(7, domainTagUserDefined.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<DomainTagUserDefined> domainTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(domainTagUserDefineds)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_domain_tag_user_defined set tag_value=?,domain=?,"
						+ "op_time=?,op_user=?,is_valid=?,knowledge_id=? where id =?");
				for(DomainTagUserDefined domainTagUserDefined : domainTagUserDefineds) {
					pstm.setString(1, domainTagUserDefined.getTagValue());
					pstm.setString(2, domainTagUserDefined.getDomain());
					pstm.setTimestamp(3, new java.sql.Timestamp(domainTagUserDefined.getOpTime().getTime()));
					pstm.setInt(4, domainTagUserDefined.getOpUser());
					pstm.setInt(5, domainTagUserDefined.getIsValid());
					pstm.setInt(6, domainTagUserDefined.getKnowledgeId());
					pstm.setLong(7, domainTagUserDefined.getId());
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
