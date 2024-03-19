package net.geedge.modules.sys.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import Aladdin.HaspStatus;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.LicenseUtil;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.entity.LicenseEntity;
import net.geedge.modules.sys.entity.Signature;
import net.geedge.modules.sys.entity.SysComponent;
import net.geedge.modules.sys.service.LicenseService;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysComponentService;

@Service
public class LicenseServiceImpl implements LicenseService {
	private static final Log log = Log.get();

	@Autowired
	private SysConfigService sysConfigService;

	@Value("${license.config.key:license}")
	private String licenseConfigKey;
	@Value("${license.token.key:license_token}")
	private String licenseTokenKey;
	@Value("${license.api.port:8088}")
	private int licenseApiPort;
	@Value("${licenseTimeout:30000}")
	private Integer licenseTimeout;
	
	/**
	 * 系统版本sys_config 表key
	 */
	@Value("${system.version.key:system_version}")
	private String systemVersionKey;

	@Value("${vendorCode}")
	private String vendorCode;

	@Value("${fetureId}")
	private Integer fetureId;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SysComponentService sysComponentService;

	/**
	 * 获取license 详情
	 */
	@Override
	public Map<String,Object> getLicenseDetail() {
		// 随机获取license api服务
		List<SysComponent> list = sysComponentService.list();
		if(ObjectUtil.isEmpty(list)) {
			log.error("license api url is empty , please check config");
			return new HashMap();
		}
		String licenseUrl = list.get(0).getHost();
		Map param = new HashMap();
		param.put("type", Constant.LICENSE_QUERY);
		param.put("vendorCode", vendorCode);
		param.put("featureId", 30000);
		ResponseEntity<Object> response = restTemplate.exchange("http://"+licenseUrl+":"+licenseApiPort+"/licenseOperation", HttpMethod.POST, 
				new HttpEntity<Object>(param), Object.class, new HashMap<String,Object>());
		Map result = (Map) response.getBody();
		String licenseInfo = null;
		if(ObjectUtil.isNotEmpty(result) && ObjectUtil.isNotEmpty(result.get("data"))) {
			licenseInfo = (String) result.get("data");
		}
		if(Tool.StrUtil.isBlank(licenseInfo)) {
			throw new CNException(RCode.LICENSE_FILE_IS_EMPTY);
		}
		Map<String, Object> licenseMap = Tool.XmlUtil.xmlToMap(licenseInfo);
		
		String v2c = getLicense();
		LicenseEntity license = (LicenseEntity) LicenseUtil.convertXmlStrToObject(LicenseEntity.class, v2c);
		if(ObjectUtil.isNotEmpty(license)) {
			licenseMap.put("comment", license.getComment());
			licenseMap.put("supportID", license.getSupportID());
			licenseMap.put("organization", license.getOrganization());
			licenseMap.put("type", license.getType());
		}
		return licenseMap;
	}

	/**
	 * 获取所有 license
	 */
	@Override
	public String getLicense() {
		String licenseConfig = sysConfigService.getValue(licenseConfigKey);
		return licenseConfig;
	}

	/**
	 * 获取 license 指纹
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws IORuntimeException 
	 */
	@Override
	public void getLicenseC2V(HttpServletResponse response) throws DocumentException, IOException {
		Map param = new HashMap();
		param.put("type", Constant.LICENSE_C2V);
		param.put("vendorCode", vendorCode);
		param.put("featureId", 30000);
		List<SysComponent> comList = sysComponentService.list();
		List<CompletableFuture<Map<String, Object>>> futureList = Tool.ListUtil.list(false);
		for (int i = 0, j = comList.size(); i < j; i++) {
			SysComponent com = comList.get(i);
			String host = "http://" + com.getHost() + ":" + licenseApiPort+"/licenseOperation";
			CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
				ResponseEntity<Object> res = restTemplate.exchange(host, HttpMethod.POST, 
						new HttpEntity<Object>(param), Object.class, new HashMap<String,Object>());
				Map result = (Map) res.getBody();
				log.info("host: {},result: {}", host, Tool.JSONUtil.toJsonStr(res));
				return result;
			});
			futureList.add(future);
		}
		// 等待所有结果返回
		try {
			CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
					.get(licenseTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.warn(e);
		}
		List<String> haspResult = new ArrayList<String>();
		futureList.forEach(future -> {
			Map<String, Object> result = future.getNow(null);
			if (Tool.MapUtil.isNotEmpty(result)) {
				Integer code = Tool.MapUtil.get(result, "code", Integer.class);
				String data = Tool.MapUtil.get(result, "data", String.class);
				if (Tool.NumberUtil.equals(code, 200)) {
					haspResult.add(data);
				}
			}
		});
		
		//排序组装hasps内容  计算摘要值
		List<String> sortHasp = haspResult.stream().sorted().collect(Collectors.toList());
		
		Document document = DocumentHelper.createDocument();
		Element licenseEle = document.addElement("license");
		Element hasps = licenseEle.addElement("hasps");
		
		for(String hasp : haspResult) {
			Document haspDoc = DocumentHelper.parseText(hasp);
			Element haspInfo = haspDoc.getRootElement();
			hasps.add(haspInfo);
		}
		String haspsStr = hasps.asXML();
		
		licenseEle.remove(hasps);
		String licenseToken = sysConfigService.getValue("license_token");
		LicenseEntity licenseEntity = new LicenseEntity();
		try {
			String licenseStr = this.getLicense(); // 已签发 license 信息
			if(StrUtil.isNotEmpty(licenseStr)) {
				LicenseEntity oldLicense = (LicenseEntity) LicenseUtil.convertXmlStrToObject(LicenseEntity.class, licenseStr);
				licenseEntity.setComment(oldLicense.getComment());
				licenseEntity.setType(oldLicense.getType());
				licenseEntity.setOrganization(oldLicense.getOrganization());
			}
		} catch (Exception e) {
			log.warn(e);
		}
		licenseEntity.setSupportID(licenseToken);
		licenseEntity.setHasps(haspsStr);
		String digest = licenseEntity.genLicenseDigest();
		
		Signature signature = new Signature();
		signature.setDigestMethod("sha1");
		signature.setDigestValue(digest);
		licenseEntity.setSignature(signature);
		
		String licenseXml = LicenseUtil.convertObjectToXmlStr(licenseEntity);
		
		Document licDoc = DocumentHelper.parseText(licenseXml);
		Element lic = licDoc.getRootElement();
		Element licHasps = lic.element("hasps");
		if(ObjectUtil.isNotEmpty(licHasps)) {
			lic.remove(licHasps);
		}
		lic.add(hasps);
		// 多个 c2v组织成一个 license 文件
		String license = licDoc.asXML();
		String licenseProductName = sysConfigService.getValue("license_product_name");
		Tool.ResponseUtil.downloadFile(response, String.format("%s-%s-license-apply.xml", licenseProductName, licenseToken),
				license.getBytes(Constant.DEFAULT_CHARTSET_NAME));
	}

	/**
	 * 验证 license
	 */
	@Override
	public Integer verify() {
		List<SysComponent> list = sysComponentService.list();
		if(ObjectUtil.isEmpty(list)) {
			log.error("license api url is empty , please check config");
			return null;
		}
		String licenseUrl = list.get(0).getHost();
		Map param = new HashMap();
		param.put("type", Constant.LICENSE_VERIFY);
		param.put("vendorCode", vendorCode);
		param.put("featureId", 30000);
		List<CompletableFuture<Integer>> futureList = Tool.ListUtil.list(false);
		for (int i = 0, j = list.size(); i < j; i++) {
			String host = list.get(i).getHost();
			CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
				ResponseEntity<Object> response = restTemplate.exchange("http://"+host+":"+licenseApiPort+"/licenseOperation", HttpMethod.POST, 
						new HttpEntity<Object>(param), Object.class, new HashMap<String,Object>());
				Map result = (Map) response.getBody();
				Integer status = null;
				if(ObjectUtil.isNotEmpty(result) && ObjectUtil.isNotEmpty(result.get("data"))) {
					status = (Integer) result.get("data");
				}
				return status;
			});
			futureList.add(future);
		}
		// 等待所有结果返回
		try {
			CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
					.get(licenseTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.warn(e);
		}
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("status", 0);
		futureList.forEach(future -> {
			Integer result = future.getNow(null);
			if(result!=0) {
				map.put("status",result);
			}
		});
		return map.get("status");
	}

	@Override
	public void validateDigestValue(String v2c) throws DocumentException {
		org.dom4j.Document doc = DocumentHelper.parseText(v2c);
		Element rootElement = doc.getRootElement();
		Element hasps = rootElement.element("hasps");
		String haspsStr = hasps.asXML();
		LicenseEntity entity = (LicenseEntity) LicenseUtil.convertXmlStrToObject(LicenseEntity.class, v2c);
		String digestValue = entity.getSignature().getDigestValue();
		entity.setHasps(haspsStr);
		String genLicenseDigest = entity.genLicenseDigest();
		log.debug("File digestValue : %s , figure out genLicenseDigest : %s", digestValue,genLicenseDigest);
		if(!StrUtil.equals(digestValue, genLicenseDigest)) {
			throw new CNException(RCode.LICENSE_DIGESTVALUE_INVALID);
		}
	}

	@Override
	public String gen(MultipartFile[] files, Map<String, Object> params) {
		String supportID = (String) params.get("supportID");
		String organization = (String) params.get("organization");
		String type = (String) params.get("type");
		String comment = (String) params.get("comment");
		List<String> haspInfos = new ArrayList<String>();
		String license = null;
		try {
			// 提取v2c文件中的hasp info数据
			for(MultipartFile file : files) {
				
					byte[] bytes = file.getBytes();
					String fileLicense = Tool.StrUtil.str(bytes, "utf-8");
					org.dom4j.Document document = DocumentHelper.parseText(fileLicense);
			    	Element ele = document.getRootElement();
			    	if(StrUtil.equals(ele.getName(), "hasp_info")) {
		    			haspInfos.add(ele.asXML());
		    		}
			    	List<Element> elements = ele.elements();
			    	for(Element e : elements) {
			    		if(StrUtil.equals(e.getName(), "hasp_info")) {
			    			haspInfos.add(e.asXML());
			    		}
			    		List<org.dom4j.Node> nodes = e.content();
			    		for(org.dom4j.Node node : nodes) {
			    			if(StrUtil.equals(node.getName(), "hasp_info")) {
			        			haspInfos.add(node.asXML());
			        		}
			    		}
			    	}
			}
			
			if(ObjectUtil.isEmpty(haspInfos)) {
				log.debug("license gen haspInfos empty");
				return null;
			}
			// 程序组装v2c文件 返回
			List<String> sortHasp = haspInfos.stream().sorted().collect(Collectors.toList());
			
			org.dom4j.Document document = DocumentHelper.createDocument();
			Element licenseEle = document.addElement("license");
			Element hasps = licenseEle.addElement("hasps");
	
			for(String hasp : haspInfos) {
				org.dom4j.Document haspDoc = DocumentHelper.parseText(hasp);
				org.dom4j.Element haspInfo = haspDoc.getRootElement();
				hasps.add(haspInfo);
			}
			String haspsStr = hasps.asXML();
	
			licenseEle.remove(hasps);
			LicenseEntity licenseEntity = new LicenseEntity();
			licenseEntity.setSupportID(supportID);
			licenseEntity.setOrganization(organization);
			licenseEntity.setType(type);
			licenseEntity.setComment(comment);
			licenseEntity.setHasps(haspsStr);
			String digest = licenseEntity.genLicenseDigest();
	
			Signature signature = new Signature();
			signature.setDigestMethod("sha1");
			signature.setDigestValue(digest);
			licenseEntity.setSignature(signature);
	
			String licenseXml = LicenseUtil.convertObjectToXmlStr(licenseEntity);
	
			org.dom4j.Document licDoc = DocumentHelper.parseText(licenseXml);
			org.dom4j.Element lic = licDoc.getRootElement();
			Element licHasps = lic.element("hasps");
			if(ObjectUtil.isNotEmpty(licHasps)) {
				lic.remove(licHasps);
			}
			lic.add(hasps);
			license = licDoc.asXML();
		} catch (Exception e) {
			log.error("license  gen error msg : %s",e.getMessage());
			e.printStackTrace();
		}
		return license;
	}

	@Override
	public Boolean uploadLicense(String v2c) throws DocumentException {
		// 保存前先校验摘要值
		this.validateDigestValue(v2c);
		// 保存到数据库中
		sysConfigService.updateValueByKey("license", v2c);
		try {
			Map param = new HashMap();
			param.put("type", Constant.LICENSE_UPDATE);
			param.put("vendorCode", vendorCode);
			param.put("featureId", 30000);
			org.dom4j.Document document = DocumentHelper.parseText(v2c);
			Element hasps = document.getRootElement().element("hasps");
			if(ObjectUtil.isEmpty(hasps)) {
				log.error("license hasps error, check file content");
				return false;
			}
			List<SysComponent> comList = sysComponentService.list();
			List<CompletableFuture<Boolean>> futureList = Tool.ListUtil.list(false);
			for (int m = 0, n = comList.size(); m < n; m++) {
				SysComponent com = comList.get(m);
				String host = "http://" + com.getHost() + ":" + licenseApiPort+"/licenseOperation";
				List<org.dom4j.Node> nodeList = hasps.content();
				CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
					for(int i = 0,j = nodeList.size();i<j;i++) {
						org.dom4j.Node node = nodeList.get(i);
						String v2cHandler = node.asXML();
						param.put("param", v2cHandler);
							ResponseEntity<Object> response = restTemplate.exchange(host, HttpMethod.POST, 
									new HttpEntity<Object>(param), Object.class, new HashMap<String,Object>());
							Map res = (Map) response.getBody();
							int status = (int)res.get("data");
							if(status == HaspStatus.HASP_STATUS_OK || status == HaspStatus.HASP_UPDATE_ALREADY_ADDED) {
								return true;
							}
					}
					return false;
				});
				futureList.add(future);
			}
			// 等待所有结果返回
			try {
				CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
						.get(licenseTimeout, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				log.warn(e);
			}
			List<Boolean> flags = new ArrayList<Boolean>();
			futureList.forEach(future -> {
				Boolean result = future.getNow(null);
				flags.add(result);
			});
			log.info("license upload result data :{}",JSON.toJSON(flags));
			if(flags.contains(false)) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e) {
			log.error("installLicense error msg : %s",e.getMessage());
			return false;
		}
	}
}
