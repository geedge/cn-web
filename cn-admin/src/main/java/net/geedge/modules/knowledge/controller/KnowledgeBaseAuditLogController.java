package net.geedge.modules.knowledge.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
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
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = knowledgeBaseAuditLogService.queryPage(params);
		return R.ok(page);
	}
	
}
