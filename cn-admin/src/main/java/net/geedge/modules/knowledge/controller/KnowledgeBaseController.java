package net.geedge.modules.knowledge.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeBaseController {
	@Autowired
	private KnowledgeBaseService knowledgeBaseService;

	@RequestMapping
	public R base(@RequestParam Map<String,Object> params) {
		PageUtils page = knowledgeBaseService.base(params);
		return R.ok(page);
	}
	
	@RequestMapping("/{id}")
	public R knowledgeInfo(@PathVariable Integer id) {
		ValidateUtils.is(id).notNull(RCode.KNOWLEDGE_ID_ISNULL);
		KnowledgeBase knowledgeBase = knowledgeBaseService.info(id);
		return R.ok(knowledgeBase);
	}
	
	@PostMapping
	public R addKnowledgeBase(@RequestBody Map<String,Object> params) {
		String id = knowledgeBaseService.add(params);
		return R.ok().putData("id", id);
	}
	
	@PutMapping
	public R modifyKnowledgeBase(@RequestBody Map<String,Object> params) {
		String id = knowledgeBaseService.modify(params);
		return R.ok().putData("id", id);
	}
	
	@DeleteMapping
	public R removeKnowledgeBase(@RequestParam("ids") String ids) {
		ValidateUtils.is(ids).notNull(RCode.KNOWLEDGE_IDS_ISNULL);
		return knowledgeBaseService.removeKnowledgeBase(ids);
	}
	
	@PostMapping("/import")
	public R importKnowledgeBase(@Param("file") MultipartFile file,@Param("type")String type) {
		ValidateUtils.is(file).notNull(RCode.KNOWLEDGE_FILE_ISNULL);
		ValidateUtils.is(type).notNull(RCode.KNOWLEDGE_TYPE_ISNULL);
		return knowledgeBaseService.importData(file,type);
	}
}
