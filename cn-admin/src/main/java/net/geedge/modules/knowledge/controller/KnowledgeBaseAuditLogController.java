package net.geedge.modules.knowledge.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.service.KnowledgeBaseAuditLogService;


/**
 * 系统日志
 *

 */
@RestController
@RequestMapping("/v1/knowledgeBase/audit/log")
public class KnowledgeBaseAuditLogController {
	@Autowired
	private KnowledgeBaseAuditLogService knowledgeBaseAuditLogService;
	
	/**
	 * 列表
	 */
	@GetMapping("/{knowledgeId}")
	public R list(@PathVariable("knowledgeId")Integer knowledgeId,@RequestParam Map<String, Object> params){
		ValidateUtils.is(knowledgeId).notNull(RCode.KNOWLEDGE_ID_ISNULL);
		params.put("knowledgeId", knowledgeId);
		PageUtils page = knowledgeBaseAuditLogService.queryPage(params);
		return R.ok(page);
	}
	
}
