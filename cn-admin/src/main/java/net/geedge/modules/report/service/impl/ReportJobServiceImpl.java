package net.geedge.modules.report.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import freemarker.template.Template;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.FreeMarkerUtil;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportJobDao;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportResult;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportResultService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;
import net.geedge.modules.sys.service.SysConfigService;

@Service("reportJobService")
public class ReportJobServiceImpl extends ServiceImpl<ReportJobDao, ReportJob> implements ReportJobService {
	private Log log = Log.get();
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private ReportTaskService reportTaskService;
	
	@Autowired
	private ReportResultService reportResultService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage page = new Query(ReportJob.class).getPage(params);
		List<ReportJob> data = this.baseMapper.queryPage(page,params);
		page.setRecords(data);
		return new PageUtils(page);
	}

	@Override
	public ReportJob queryInfo(Integer id) {
		return this.baseMapper.selectInfoById(id);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeReportJobs(String ids) {
		List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
		//删除job以及对应的task 结果数据信息
		List<ReportTask> tasks = reportTaskService.list(new QueryWrapper<ReportTask>().lambda().in(ReportTask::getJobId, idList));
		if(ObjectUtils.isNotEmpty(tasks)) {
			List<Integer> taskIds = tasks.stream().map(ReportTask::getId).collect(Collectors.toList());
			reportResultService.remove(new QueryWrapper<ReportResult>().lambda().in(ReportResult::getTaskId, taskIds));
			reportTaskService.removeByIds(taskIds);
		}
		this.remove(new QueryWrapper<ReportJob>().lambda().in(ReportJob::getId, idList));
	}

	@Override
	public String queryView(Integer id) {
		String html = null;
		// 获取该报表下的界面模板以及数据
		List<ReportTask> tasks = reportTaskService.list(new QueryWrapper<ReportTask>().lambda().eq(ReportTask::getJobId,id).eq(ReportTask::getState, 3));
		List<Integer> taskIds = tasks.stream().map(ReportTask::getId).collect(Collectors.toList());
		List<ReportResult> results = reportResultService.list(new QueryWrapper<ReportResult>().lambda().in(ReportResult::getTaskId, taskIds));
		if(ObjectUtil.isNotEmpty(results)) {
			html = this.renderHtml(id,tasks,results);
		}
		return html;
	}
	
	public String renderHtml(Integer jobId,List<ReportTask> reportTasks,List<ReportResult> reportResults) {
		String driverEnable = sysConfigService.getValue("driver_enable");
		String html = "";
		try {
			// 获取html模板信息
			ReportJob reportJob = this.getById(jobId);
			html = reportJob.getHtmlContent();
			Map<Integer, ReportResult> taskIdResult = reportResults.stream().collect(Collectors.toMap(ReportResult::getTaskId, Function.identity()));
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("startTime", reportTasks.get(0).getStartTime().getTime()/1000);
			data.put("endTime", reportTasks.get(0).getEndTime().getTime()/1000);
			for(ReportTask reportTask : reportTasks) {
				ReportResult reportResult = taskIdResult.get(reportTask.getId());
				String result = reportResult.getResult();
				Map<String,Object> r = (Map<String, Object>) JSONObject.parse(result);
				data.putAll(r);
			}
			Template template = FreeMarkerUtil.stringToTemplate("Task"+reportJob.getName(), html);
			StringWriter writer = new StringWriter();
			template.process(data, writer);
			writer.flush();
			html = writer.toString();
			// 判断是否开启引擎
			if(StrUtil.isNotBlank(driverEnable) && StrUtil.equals(driverEnable, "1")) {
				System.setProperty("webdriver.chrome.driver", Constant.CONFIG_PATH + File.separator + "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				// 后台运行，不弹出浏览器
				options.addArguments("--headless");
				options.addArguments("--window-size=1920x1080");
				ChromeDriver driver = new ChromeDriver(options);
				driver.navigate().to("data:text/html;charset=utf-8," + html);
				html = (String) driver.executeScript("return document.documentElement.innerHTML;");
			}
		}catch(Exception e) {
			html="";
			log.error("render report html error : {}",e.getMessage());
		}
		return html;
	}

	@Override
	public void downloadPdf(Integer jobId, HttpServletResponse response) throws Exception{
		ReportJob job = this.getBaseMapper().selectById(jobId);
		String s3Enable = sysConfigService.getValue("s3_enable");
		String s3Path = sysConfigService.getValue("s3_path");
		String s3BucketName = sysConfigService.getValue("s3_bucket_name");
		if(Integer.valueOf(s3Enable)==1 && job.getState()==1 && job.getUpload()==1) {
			if(StrUtil.isEmpty(s3Path)) {
				log.warn("s3 path can not be empty");
				return;
			}else {
				if(!s3Path.endsWith(StrUtil.SLASH)) {
					s3Path = StrUtil.str(s3Path+StrUtil.SLASH);
				}
			}
			String path = StrUtil.str(s3Path+s3BucketName+StrUtil.C_SLASH+job.getName()+".pdf");
			RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			CloseableHttpClient createDefault = HttpClients.createDefault();
			HttpGet request = new HttpGet(path);
			request.setConfig(config);
			HttpResponse execute = createDefault.execute(request);
			int statusCode = execute.getStatusLine().getStatusCode();
			HttpEntity entity = execute.getEntity();
			if(statusCode == 200) {
				InputStream content = entity.getContent();
				FastByteArrayOutputStream read = IoUtil.read(content);
	            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode(job.getName()+".pdf", "UTF-8"));
				read.writeTo(response.getOutputStream());
			}else {
				String errorMsg = EntityUtils.toString(entity);
				throw new CNException(errorMsg,statusCode);
			}
		}else if(job.getState()==1){
			String htmlContent = this.queryView(jobId);
			ConverterProperties converterProperties=new ConverterProperties();
	        FontProvider fontProvider=new DefaultFontProvider();
	        FontProgram fontProgram = FontProgramFactory.createFont(Constant.CONFIG_PATH+File.separator+"simsun.ttc,1");
	        fontProvider.addFont(fontProgram);
	        converterProperties.setFontProvider(fontProvider);
	        HtmlConverter.convertToPdf(htmlContent,response.getOutputStream(),converterProperties);
		}else {
			log.warn(RCode.REPORT_NOT_FINSHED.getMsg());
			throw new CNException(RCode.REPORT_NOT_FINSHED);
		}
	}

	@Override
	public void upload(String fileName) {
		String s3Path = sysConfigService.getValue("s3_path");
		String accessKey = sysConfigService.getValue("s3_accessKey");
		String secretKey = sysConfigService.getValue("s3_secretKey");
		String bucketName = sysConfigService.getValue("s3_bucket_name");
		// 创建AmazonS3Client实例
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Path, "us-east-1"))
				.withChunkedEncodingDisabled(true).build();
		// 判断桶是否存在 不存在则创建桶
		// 获取桶列表
		boolean exist = false;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket bucket : buckets){
		    // 获取桶名
		    String bn = bucket.getName();
		    if(StrUtil.equals(bn, bucketName)) {
		    	exist = true;
		    }
		}
		if(!exist) {
			// 创建创建桶请求对象，提供存储桶名称。
			CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
			// 设置桶权限，可以不设置，默认为PublicReadWrite，权限分为CannedAccessControlList.PublicReadWrite和CannedAccessControlList.Private
			createBucketRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
			// 创建桶，如果存储桶已存在会创建失败
			s3.createBucket(createBucketRequest);
		}
		
		// 创建ObjectMetadata对象，可以设置S3请求头信息
		ObjectMetadata metadata = new ObjectMetadata();
		// 设置content-type，对应于文件的类型，最好也设置
		metadata.setContentType("");
		// 设置长度，一般要设置，不设置会有警告
		File file = new File(Constant.TEMP_PATH + File.separator + fileName);
		metadata.setContentLength(file.length());
		// 上传文件元数据信息,指定元信息
		metadata.setHeader("x-hos-meta-message", "message");
		// 创建对应的流，以下示例为文件流
		BufferedInputStream inputStream = FileUtil.getInputStream(file);
		// 以流方式上传对象，指定桶名、对象名、输入流、ObjectMetadata
		s3.putObject(bucketName, fileName, inputStream, metadata);
		// 关闭流
		IoUtil.close(inputStream);
			
		// 关闭AmazonS3Client
		s3.shutdown();
	}
}
