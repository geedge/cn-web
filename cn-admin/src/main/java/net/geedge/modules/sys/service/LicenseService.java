package net.geedge.modules.sys.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;

public interface LicenseService {

	/**
	 * 获取 license
	 * 
	 * @return
	 */
	Map<String,Object> getLicenseDetail();
	
	Boolean uploadLicense(String v2c) throws DocumentException ;

	/**
	 * 证书校验
	 * @return
	 */
	Integer verify();

	void getLicenseC2V(HttpServletResponse response) throws DocumentException, IOException;
	
	void validateDigestValue(String v2c) throws Exception;
	
	/**
	 * 生成v2c文件
	 */
	String gen(MultipartFile[] files,Map<String,Object> params);

	String getLicense();
}
