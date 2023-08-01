package net.geedge.modules.sys.service;

import java.util.List;
import java.util.Map;

public interface BasicImportAndExportService {
	
	List<Integer> queryDataIds(String tableName, Map<String,String> columnVal, Integer knowledgeId, Long id);
	
	void removeList(String tableName,List<Integer> deleteIds);
	
	void removeList(String tableName,Integer knowledgeId);
}
