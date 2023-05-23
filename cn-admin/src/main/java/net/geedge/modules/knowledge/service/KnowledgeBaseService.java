package net.geedge.modules.knowledge.service;


import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.R;
import net.geedge.modules.knowledge.entity.KnowledgeBase;


public interface KnowledgeBaseService extends IService<KnowledgeBase> {
	
	Object remove(String ids);
	
	R importData(MultipartFile file,String type);
}
