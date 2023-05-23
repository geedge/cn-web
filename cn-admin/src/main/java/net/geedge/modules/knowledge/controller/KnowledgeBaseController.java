package net.geedge.modules.knowledge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;

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
	
	@PostMapping("/import")
	public R importKnowledgeBase(@Param("file") MultipartFile file,@Param("type")String type) {
		ValidateUtils.is(file).notNull(RCode.KNOWLEDGE_FILE_ISNULL);
		ValidateUtils.is(type).notNull(RCode.KNOWLEDGE_TYPE_ISNULL);
		return knowledgeBaseService.importData(file,type);
	}

}

