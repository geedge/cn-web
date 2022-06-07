package net.geedge.modules.report.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.report.entity.ReportCategory;

public interface ReportCategoryService extends IService<ReportCategory>{

	PageUtils queryPage(Map<String,Object> params);
	
	void removeReportCategorys(String ids);
	
	ReportCategory queryInfo(Integer id);
	
}
