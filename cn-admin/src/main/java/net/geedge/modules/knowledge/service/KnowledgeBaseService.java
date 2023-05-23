package net.geedge.modules.knowledge.service;


import net.geedge.common.utils.PageUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.R;
import net.geedge.modules.knowledge.entity.KnowledgeBase;

import java.util.Map;


public interface KnowledgeBaseService extends IService<KnowledgeBase> {
	
	Object remove(String ids);
	
	R importData(MultipartFile file,String type);

    PageUtils queryKnowledgeBaseList(Map<String,Object> params);

    R enableKnowledgeBase(Map<String,Object> params);

    Map<String,Object> statisticsKnowledgeBase(Map<String,Object> params);

    PageUtils queryKnowledgeBaseItem(Integer knowledgeId, Map<String,Object> params);
}
