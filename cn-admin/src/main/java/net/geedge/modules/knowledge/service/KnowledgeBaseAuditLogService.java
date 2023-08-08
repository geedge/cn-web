package net.geedge.modules.knowledge.service;


import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.KnowledgeBaseAuditLog;


public interface KnowledgeBaseAuditLogService extends IService<KnowledgeBaseAuditLog> {
	
	PageUtils queryPage(Map<String, Object> params);
	
}
