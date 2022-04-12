package net.geedge.modules.report.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.report.entity.ReportJob;

public interface ReportJobService extends IService<ReportJob>{
	
	PageUtils queryPage(Map<String,Object> params);
	
	ReportJob queryInfo(Integer id);
	
	void removeReportJobs(String ids);
	
	String queryView(Integer id);
	
	void downloadPdf(Integer jobId,HttpServletResponse response) throws Exception;
	
	void batchDownloadPdf(List<String> jobIds,HttpServletResponse response) throws Exception;
	
	void upload(String fileName) throws FileNotFoundException;
}
