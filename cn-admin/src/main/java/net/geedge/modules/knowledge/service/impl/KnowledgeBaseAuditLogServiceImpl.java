package net.geedge.modules.knowledge.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.KnowledgeBaseAuditLogDao;
import net.geedge.modules.knowledge.entity.KnowledgeBaseAuditLog;
import net.geedge.modules.knowledge.service.KnowledgeBaseAuditLogService;
import net.geedge.modules.sys.service.SysUserService;

@Service("knowledgeBaseAuditLogService")
public class KnowledgeBaseAuditLogServiceImpl extends ServiceImpl<KnowledgeBaseAuditLogDao, KnowledgeBaseAuditLog> implements KnowledgeBaseAuditLogService {
	private SysUserService sysUserService;
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
        IPage page = new Query(KnowledgeBaseAuditLog.class).getPage(params);
        List<KnowledgeBaseAuditLog> knowledgeBaseAuditLogs = this.baseMapper.queryList(page, params);
        page.setRecords(knowledgeBaseAuditLogs);

        return new PageUtils(page);
	}
	
}
