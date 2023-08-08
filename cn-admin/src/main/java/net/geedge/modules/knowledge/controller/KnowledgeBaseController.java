package net.geedge.modules.knowledge.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;

@RestController
@RequestMapping("/v1/knowledgeBase")
public class KnowledgeBaseController {
	@Autowired
	private KnowledgeBaseService knowledgeBaseService;

	@DeleteMapping
	public R remove(@RequestParam("knowledgeIds") String knowledgeIds) {
		ValidateUtils.is(knowledgeIds).notNull(RCode.KNOWLEDGE_IDS_ISNULL);
		List<Integer> result = knowledgeBaseService.remove(knowledgeIds);
		return R.ok(result);
	}
	
	@PostMapping("/parser")
	public R importKnowledgeBase(@Param("file") MultipartFile file,@Param("source")String source) {
		ValidateUtils.is(file).notNull(RCode.KNOWLEDGE_FILE_ISNULL);
		ValidateUtils.is(source).notNull(RCode.KNOWLEDGE_SOURCE_ISNULL);
		return knowledgeBaseService.importData(file,source);
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
		Map<String, Object> data = knowledgeBaseService.queryKnowledgeBaseItem(knowledgeId, params);
		return R.ok(data);
	}
	
	@PostMapping
	public R addKnowledgeBase(@RequestBody Map<String,Object> params) {
		Integer id = knowledgeBaseService.addOrModifyKnowledgeBase(params);
		return R.ok().put("knowledgeId", id);
	}
	
	@PutMapping
	public R modifyKnowledgeBase(@RequestBody Map<String,Object> params) {
		Integer knowledgeId = (Integer) params.get("knowledgeId");
		ValidateUtils.is(knowledgeId).notNull(RCode.KNOWLEDGE_ID_ISNULL);
		Integer id = knowledgeBaseService.addOrModifyKnowledgeBase(params);
		return R.ok().put("knowledgeId", id);
	}
	
	@PostMapping("/items/batch")
	public R importBatch(@Param("file") MultipartFile file,@Param("knowledgeId")Integer knowledgeId,@Param("action")String action
			,@Param("status")Integer status,@Param("description")String description) {
		ValidateUtils.is(file).notNull(RCode.KNOWLEDGE_FILE_ISNULL);
		ValidateUtils.is(knowledgeId).notNull(RCode.KNOWLEDGE_ID_ISNULL);
		ValidateUtils.is(action).notNull(RCode.KNOWLEDGE_IMPORT_ACTION_ISNULL);
		ValidateUtils.is(action).regex("(overwrite|append|update)", RCode.KNOWLEDGE_IMPORT_ACTION_VALUE_ERROR);
		knowledgeBaseService.importBatch(file,knowledgeId,action,status,description);
		return R.ok().putData("knowledgeId",knowledgeId);
	}
}

