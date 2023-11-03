package net.geedge.modules.sys.service;

import java.util.List;
import java.util.Map;

public interface BasicImportAndExportService {
	
	List<Map<String,Object>> queryDataIds(String tableName, List<String> columnVal, Integer knowledgeId, List<Map<String, String>> importDataList);
	
	void removeList(String tableName,List<Integer> deleteIds);
	
	void removeList(String tableName,Integer knowledgeId);
}
