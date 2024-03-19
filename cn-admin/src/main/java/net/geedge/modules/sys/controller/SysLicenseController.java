package net.geedge.modules.sys.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import Aladdin.HaspStatus;
import net.geedge.common.annotation.SysLog;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.entity.LicenseEntity;
import net.geedge.modules.sys.service.LicenseService;
import net.geedge.modules.sys.service.SysConfigService;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.log.Log;

@RestController
@RequestMapping("/sys/license")
public class SysLicenseController extends AbstractController {
	private static final Log log = Log.get();

	@Autowired
	private LicenseService licenseService;

	/**
	 * 私钥存储路径，gen生成证书时使用
	 */
	@Value("${license.privatekey.path:license/license.key}")
	private String prikeyPath;

	@Value("${license.fileName:license.xml}")
	private String licenseFileName;

	@Value("${compileType:release}")
	private String compileType;
	
	@Autowired
	private SysConfigService sysConfigService;
	/**
	 * 获取license
	 */
	@GetMapping("/detail")
	public R getLicense() {
		Map<String, Object> license = licenseService.getLicenseDetail();
		return R.ok().putData("license", license);
	}

	@GetMapping("/download")
	public R getLicenseToken(HttpServletResponse response)
			throws IOException, DocumentException {
			licenseService.getLicenseC2V(response);
			return null;
	}
	
	/**
	 * 获取license state
	 */
	@GetMapping("/status")
	public R getLicenseState() {
		if(Tool.StrUtil.equalsIgnoreCase(compileType, "debug")) {
			return R.ok().putData("status", 0);
		}
		Integer status = licenseService.verify();
		if(status != 0) {
			throw new CNException("License error,code:"+status,999);
		}
		return R.ok().putData("status", status);
	}
	/**
		 * @throws IOException 上传license @throws
		 */
	
	@PostMapping("/upload")
	public R uploadLicense(@RequestParam(name = "file", required = false) MultipartFile file) throws Exception {
		ValidateUtils.is(file).notNull(RCode.LICENSE_FILE_IS_NULL);
		byte[] bytes = file.getBytes();
		Boolean flag = licenseService.uploadLicense(StrUtil.str(bytes, "utf-8"));
		if (flag) {
			return R.ok().putData("status", HaspStatus.HASP_STATUS_OK);
		}
		throw new CNException(RCode.LICENSE_FILE_INVALID);
	}
	
	@PostMapping("/gen")
	public void gen(@RequestParam(name = "files", required = true) MultipartFile[] files,@RequestParam Map<String,Object> params,HttpServletResponse response) throws IORuntimeException, UnsupportedEncodingException, IOException {
		ValidateUtils.is(params.get("supportID")).notNull(RCode.LICENSE_SUPPORT_ID_ISNULL)
		.and(params.get("organization")).notNull(RCode.LICENSE_ORGANIZATION_ISNULL)
		.and(params.get("type")).notNull(RCode.LICENSE_TYPE_ISNULL);
		if(ObjectUtil.isEmpty(files)) {
			throw new CNException(RCode.LICENSE_FILE_IS_NULL);
		}
		
		String license = licenseService.gen(files,params);
		
		if(StrUtil.isEmpty(license)) {
			return;
		}else {
			Tool.ResponseUtil.downloadFile(response, String.format("%s-%s-license-issue.xml",  sysConfigService.getValue("license_product_name"), params.get("supportID")),
					license.getBytes(Constant.DEFAULT_CHARTSET_NAME));
		}
	}
}
