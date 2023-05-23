package net.geedge.modules.knowledge.controller;


import net.geedge.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;

import java.util.Map;

@RestController
@RequestMapping("/v1/knowledgeBase")
public class KnowledgeBaseController {
	@Autowired
	private KnowledgeBaseService knowledgeBaseService;

	@DeleteMapping
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.KNOWLEDGE_IDS_ISNULL);
		Object result = knowledgeBaseService.remove(ids);
		return R.ok(result);
	}
	
	@PostMapping("/parser")
	public R importKnowledgeBase(@Param("file") MultipartFile file,@Param("type")String type) {
		ValidateUtils.is(file).notNull(RCode.KNOWLEDGE_FILE_ISNULL);
		ValidateUtils.is(type).notNull(RCode.KNOWLEDGE_TYPE_ISNULL);
		return knowledgeBaseService.importData(file,type);
	}

	@PatchMapping("/status")
	@Transactional(rollbackFor = Exception.class)
	public R enableKnowledgeBase(@RequestBody Map<String,Object> params) {
		return knowledgeBaseService.enableKnowledgeBase(params);
	}

	@RequestMapping("/list")
	public R queryKnowledgeBaseList(@RequestParam Map<String,Object> params) {
		PageUtils page = knowledgeBaseService.queryKnowledgeBaseList(params);
		return R.ok(page);
	}

	@RequestMapping("/statistics")
	public R statisticsKnowledgeBase(@RequestParam Map<String,Object> params) {
		Map<String,Object> data = knowledgeBaseService.statisticsKnowledgeBase(params);
		return R.ok(data);
	}

	@RequestMapping("/{id}")
	public R queryKnowledgeBaseItem(@PathVariable(value = "id") Integer knowledgeId,@RequestParam Map<String,Object> params) {
		PageUtils page = knowledgeBaseService.queryKnowledgeBaseItem(knowledgeId, params);
		return R.ok(page);
	}
}

