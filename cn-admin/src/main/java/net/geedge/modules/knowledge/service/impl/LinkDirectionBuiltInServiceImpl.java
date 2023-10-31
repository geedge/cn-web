package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.modules.knowledge.dao.LinkDirectionBuiltInDao;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import net.geedge.modules.knowledge.service.LinkDirectionBuiltInService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service("linkDirectionBuiltInService")
public class LinkDirectionBuiltInServiceImpl extends ServiceImpl<LinkDirectionBuiltInDao,LinkDirectionBuiltIn> implements LinkDirectionBuiltInService{
	private Log log = Log.get();
	
    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(LinkDirectionBuiltIn.class).getPage(params));
        List<LinkDirectionBuiltIn> linkDirectionBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(linkDirectionBuiltIns);
        return new PageUtils(page);
    }

	@Override
	public void saveBatchJDBC(Connection conn, List<LinkDirectionBuiltIn> linkDirectionBuiltIns) {
		if(ObjectUtil.isNotEmpty(linkDirectionBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("insert into cn_link_direction_built_in (interface_name,direction,bandwidth,peer_province,peer_city,"
						+ "link_id,knowledge_id) values (?,?,?,?,?,?,?)");
				for(LinkDirectionBuiltIn linkDirectionBuiltIn : linkDirectionBuiltIns) {
					pstm.setString(1, linkDirectionBuiltIn.getInterfaceName());
					pstm.setInt(2, linkDirectionBuiltIn.getDirection());
					pstm.setLong(3, linkDirectionBuiltIn.getBandwidth());
					pstm.setString(4, linkDirectionBuiltIn.getPeerProvince());
					pstm.setString(5, linkDirectionBuiltIn.getPeerCity());
					pstm.setInt(6, linkDirectionBuiltIn.getLinkId());
					pstm.setInt(7, linkDirectionBuiltIn.getKnowledgeId());
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
	public void modifyBatchJDBC(Connection conn, List<LinkDirectionBuiltIn> linkDirectionBuiltIns) {
		if(ObjectUtil.isNotEmpty(linkDirectionBuiltIns)) {
			try {
				PreparedStatement pstm = conn.prepareStatement("update cn_link_direction_built_in set interface_name =? ,direction =?,bandwidth =?,peer_province =?,peer_city=?,"
						+ "link_id=?,knowledge_id=? where id =?");
				for(LinkDirectionBuiltIn linkDirectionBuiltIn : linkDirectionBuiltIns) {
					pstm.setString(1, linkDirectionBuiltIn.getInterfaceName());
					pstm.setInt(2, linkDirectionBuiltIn.getDirection());
					pstm.setLong(3, linkDirectionBuiltIn.getBandwidth());
					pstm.setString(4, linkDirectionBuiltIn.getPeerProvince());
					pstm.setString(5, linkDirectionBuiltIn.getPeerCity());
					pstm.setInt(6, linkDirectionBuiltIn.getLinkId());
					pstm.setInt(7, linkDirectionBuiltIn.getKnowledgeId());
					pstm.setInt(8, linkDirectionBuiltIn.getId());
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
