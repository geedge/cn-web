package net.geedge.modules.knowledge.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.knowledge.entity.KnowledgeBase;


public interface KnowledgeBaseService extends IService<KnowledgeBase> {
	
	PageUtils base(Map<String,Object> params);
	
	String add(Map<String,Object> params);
	
	String modify(Map<String,Object> params);
	
	R importData(MultipartFile file,String type);
	
	R removeKnowledgeBase(String ids);
	
	KnowledgeBase info(Integer id);
}
