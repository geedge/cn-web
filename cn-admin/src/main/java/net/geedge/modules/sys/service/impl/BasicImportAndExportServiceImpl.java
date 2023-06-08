package net.geedge.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.geedge.modules.sys.dao.BasicImportAndExportDao;
import net.geedge.modules.sys.service.BasicImportAndExportService;

@Service("basicImportAndExportService")
public class BasicImportAndExportServiceImpl implements BasicImportAndExportService{
	
	@Autowired
	private BasicImportAndExportDao basicImportAndExportDao;
	
	@Override
	public Long queryDataId(String tableName, Map<String, String> columnVal,Integer knowledgeId) {
		return basicImportAndExportDao.selectDataId(tableName,columnVal,knowledgeId);
	}

	@Override
	public void removeList(String tableName, List<Integer> deleteIds) {
		basicImportAndExportDao.deleteList(tableName,deleteIds);
	}

	@Override
	public void removeList(String tableName, Integer knowledgeId) {
		basicImportAndExportDao.deleteListByKnowledgeId(tableName,knowledgeId);
	}

}
