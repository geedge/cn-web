package net.geedge.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.report.entity.ReportTemp;

public interface ReportTempService extends IService<ReportTemp>{

	PageUtils queryPage(Map<String,Object> params);
	
	void removeReportTemps(String ids);
	
	void saveOrModify(ReportTemp reportTemp);
}
