package net.geedge.modules.sys.service;

import java.util.List;
import java.util.Map;

public interface BasicImportAndExportService {
	
	List queryList(String tableName,Map<String,String> columnVal);
	
	void removeList(String tableName,List<Integer> deleteIds);
}
