package net.geedge.modules.report.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/report/job")
public class ReportJobController extends AbstractController{
	@Autowired
	private ReportJobService reportJobService;
	
	@GetMapping
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = reportJobService.queryPage(params);
		return R.ok(page);
	}
	
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id){
		ReportJob reportJob = reportJobService.queryInfo(id);
		return R.ok(reportJob);
	}
	
	@DeleteMapping
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.REPORT_JOB_ID_ISNULL);
		reportJobService.removeReportJobs(ids);
		return R.ok();
	}
	
	@PostMapping("/view")
	public void reportView(Integer id,HttpServletResponse response) {
		String htmlContent = reportJobService.queryView(id);
		try {
			ServletOutputStream out = response.getOutputStream();
			out.write(htmlContent.getBytes("UTF-8"));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@PostMapping("/downloadPdf")
	public R downloadPdf(Integer id,HttpServletResponse response) {
		try {
			reportJobService.downloadPdf(id,response);
			return null;
		}catch(Exception e) {
			return R.error(999,e.getMessage());
		}
	}
}
