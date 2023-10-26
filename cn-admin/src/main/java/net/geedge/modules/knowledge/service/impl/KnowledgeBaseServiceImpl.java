package net.geedge.modules.knowledge.service.impl;


import static com.alibaba.fastjson.JSON.parseObject;
import static net.geedge.common.utils.Constant.APP_CATEGORY_BUILT_IN;
import static net.geedge.common.utils.Constant.APP_TAG_USER_DEFINED;
import static net.geedge.common.utils.Constant.DNS_SERVER_INFO_BUILT_IN;
import static net.geedge.common.utils.Constant.DOMAIN_TAG_USER_DEFINED;
import static net.geedge.common.utils.Constant.FQDN_CATEGORY_BUILT_IN;
import static net.geedge.common.utils.Constant.FQDN_ICP_BUILT_IN;
import static net.geedge.common.utils.Constant.FQDN_WHO_IS_BUILT_IN;
import static net.geedge.common.utils.Constant.IDC_RENTER_BUILT_IN;
import static net.geedge.common.utils.Constant.INTERNAL_IP_BUILT_IN;
import static net.geedge.common.utils.Constant.IOC_DARKWEB;
import static net.geedge.common.utils.Constant.IOC_MALWARE;
import static net.geedge.common.utils.Constant.IP_ASN_BUILT_IN;
import static net.geedge.common.utils.Constant.IP_LOCATION_BUILT_IN;
import static net.geedge.common.utils.Constant.IP_TAG_USER_DEFINED;
import static net.geedge.common.utils.Constant.LINK_DIRECTION_BUILT_IN;
import static net.geedge.common.utils.Constant.PSIPHON3_IP;
import static net.geedge.common.utils.RCode.KNOWLEDGE_BASE_NOT_EXIST;
import static net.geedge.common.utils.RCode.KNOWLEDGE_SOURCE_ERROR;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.geedge.common.utils.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.sql.Connection;

import cn.hutool.Hutool;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.CharsetDetector;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import freemarker.template.utility.DateUtil;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.Constant.RuleType;
import net.geedge.modules.knowledge.dao.DnsServerInfoBuiltInDao;
import net.geedge.modules.knowledge.dao.IpTagUserDefinedDao;
import net.geedge.modules.knowledge.dao.KnowledgeBaseDao;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;
import net.geedge.modules.knowledge.entity.DomainTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnCategoryBuiltIn;
import net.geedge.modules.knowledge.entity.FqdnIcpBuiltIn;
import net.geedge.modules.knowledge.entity.FqdnWhoIsBuiltIn;
import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;
import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;
import net.geedge.modules.knowledge.entity.IocDarkweb;
import net.geedge.modules.knowledge.entity.IocMalware;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import net.geedge.modules.knowledge.entity.IpLocationBuiltIn;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.entity.KnowledgeBaseAuditLog;
import net.geedge.modules.knowledge.entity.KnowledgeBaseSyncVersion;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;
import net.geedge.modules.knowledge.service.AppCategoryBuiltInService;
import net.geedge.modules.knowledge.service.AppTagUserDefinedService;
import net.geedge.modules.knowledge.service.DnsServerInfoBuiltInService;
import net.geedge.modules.knowledge.service.DomainTagUserDefinedService;
import net.geedge.modules.knowledge.service.FqdnCategoryBuiltInService;
import net.geedge.modules.knowledge.service.FqdnIcpBuiltInService;
import net.geedge.modules.knowledge.service.FqdnWhoIsBuiltInService;
import net.geedge.modules.knowledge.service.IdcRenterBuiltInService;
import net.geedge.modules.knowledge.service.InternalIpBuiltInService;
import net.geedge.modules.knowledge.service.IocDarkwebService;
import net.geedge.modules.knowledge.service.IocMalwareService;
import net.geedge.modules.knowledge.service.IpAsnBuiltInService;
import net.geedge.modules.knowledge.service.IpLocationBuiltInService;
import net.geedge.modules.knowledge.service.IpTagUserDefinedService;
import net.geedge.modules.knowledge.service.KnowledgeBaseAuditLogService;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;
import net.geedge.modules.knowledge.service.KnowledgeBaseSyncVersionService;
import net.geedge.modules.knowledge.service.LinkDirectionBuiltInService;
import net.geedge.modules.knowledge.service.Psiphon3IpService;
import net.geedge.modules.rule.entity.DetectionRule;
import net.geedge.modules.rule.service.DetectionRuleService;
import net.geedge.modules.sys.service.BasicImportAndExportService;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service("knowledgeBaseService")
public class KnowledgeBaseServiceImpl extends ServiceImpl<KnowledgeBaseDao, KnowledgeBase> implements KnowledgeBaseService {
	private Log log = Log.get();
	@Autowired
	private KnowledgeBaseSyncVersionService knowledgeBaseSyncVersionService;
	
	@Autowired
	private DomainTagUserDefinedService domainTagUserDefinedService;
	
	@Autowired
	private IpTagUserDefinedService ipTagUserDefinedService;
	
	@Autowired
	private AppTagUserDefinedService appTagUserDefinedService;

	@Autowired
	private IocDarkwebService iocDarkwebService;

	@Autowired
	private IocMalwareService iocMalwareService;

	@Autowired
	private Psiphon3IpService psiphon3IpService;

	@Autowired
	private AppCategoryBuiltInService appCategoryBuiltInService;

	@Autowired
	private FqdnIcpBuiltInService fqdnIcpBuiltInService;

	@Autowired
	private FqdnWhoIsBuiltInService fqdnWhoIsBuiltInService;

	@Autowired
	private FqdnCategoryBuiltInService fqdnCategoryBuiltInService;

	@Autowired
	private IpAsnBuiltInService ipAsnBuiltInService;

	@Autowired
	private IpLocationBuiltInService ipLocationBuiltInService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private BasicImportAndExportService basicImportAndExportService;
	
	@Autowired
	private LinkDirectionBuiltInService linkDirectionBuiltInService;
	
	@Autowired
	private DnsServerInfoBuiltInService dnsServerInfoBuiltInService;
	
	@Autowired
	private IdcRenterBuiltInService idcRenterBuiltInService;
	
	@Autowired
	private InternalIpBuiltInService internalIpBuiltInService;
	
	@Autowired
	private KnowledgeBaseAuditLogService knowledgeBaseAuditLogService;
	
	@Autowired
	private DetectionRuleService detectionRuleService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Integer> remove(String knowledgeIds) {
		// 内置数据不可删除
		List<String> knowlegeIds = StrUtil.split(knowledgeIds, StrUtil.C_COMMA);
		List<KnowledgeBase> knowledgeBases = this.baseMapper.selectBatchIds(knowlegeIds);
		List<Integer> builtInIds = knowledgeBases.stream().filter(knowledgeBase-> knowledgeBase.getIsBuiltIn()
				.equals(1)).map(KnowledgeBase::getKnowledgeId).collect(Collectors.toList());
		this.getRuleInfo(knowledgeBases);
		knowledgeBases.stream().forEach(base -> {
			if(ObjectUtil.isNotEmpty(base.getRefRule())) {
				throw new CNException(RCode.KNOWLEDGE_RULE_USE_ERROR);
			}
		});
		if(ObjectUtils.isNotEmpty(builtInIds)) {
			throw new CNException(RCode.KNOWLEDGE_BUILTIN_ERROR);
		}
		// 判断传入数据是否都存在 不存在返回错误提示
		if(knowledgeBases.size()!=knowlegeIds.size()) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
		}
		List<Integer> ids = knowledgeBases.stream().map(KnowledgeBase::getKnowledgeId).collect(Collectors.toList());
		// 删除版本数据信息
		List<KnowledgeBaseSyncVersion> syncVersions = knowledgeBaseSyncVersionService.list(new QueryWrapper<KnowledgeBaseSyncVersion>().lambda().in(KnowledgeBaseSyncVersion::getKnowledgeId, knowlegeIds));
		// 审计日志表数据填充
		List<KnowledgeBaseAuditLog> knowledgeBaseAuditLogs = new ArrayList<KnowledgeBaseAuditLog>();
		for(KnowledgeBaseSyncVersion syncVersion : syncVersions) {
			Integer version = syncVersion.getVersion();
			syncVersion.setVersion(version+1);
			syncVersion.setOpTime(new Date());
			KnowledgeBaseAuditLog log = new KnowledgeBaseAuditLog();
			log.setCommitVersion(version+1);
			log.setDescription("Deleted");
			log.setIsPublished(0);
			log.setKnowledgeId(syncVersion.getKnowledgeId());
			log.setStatus(0);
			log.setOpTime(new Date());
			log.setOpType(OperationEnum.DELETE.getValue());
			log.setOpUser(ShiroUtils.getUserId().intValue());
			log.setApiKeyId(ShiroUtils.getUserEntity().getApiKeyId());
			knowledgeBaseAuditLogs.add(log);
		}
		knowledgeBaseSyncVersionService.updateBatchById(syncVersions);
		// 根据类型进行分组 删除对应表中条目数据
		Map<String, List<Integer>> sourceKnowledgeBaseIds = knowledgeBases.stream().collect(Collectors.groupingBy(KnowledgeBase::getSource,Collectors.mapping(KnowledgeBase::getKnowledgeId, Collectors.toList())));
		for(String source : sourceKnowledgeBaseIds.keySet()) {
			// 根据source 去对应的表删除数据
			this.baseMapper.deleteBySource(source,sourceKnowledgeBaseIds.get(source));
		}
		knowledgeBaseAuditLogService.saveBatch(knowledgeBaseAuditLogs);
		this.remove(new QueryWrapper<KnowledgeBase>().lambda().in(KnowledgeBase::getKnowledgeId,knowlegeIds));
		return ids;
	}

	@Override
	public R importData(MultipartFile file,String source) {
		File tempFile = null;
		if(!StrUtil.endWith(file.getOriginalFilename(), ".csv")) {
			throw new CNException(RCode.KNOWLEDGE_FILE_TYPE_ERROR);
		}
		try{
			tempFile = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".csv");
			FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile);
			CsvData datas = CsvUtil.getReader().read(ResourceUtil.getReader(tempFile.getPath(), CharsetDetector.detect(tempFile)));
			
			Map<String,Object> result = new HashMap<String,Object>();
			List<Map<String,Object>> handlerData = new ArrayList<Map<String,Object>>();

			List<CsvRow> csvRows = (List<CsvRow>)datas.getRows();
			//去除表头
			csvRows.remove(0);
			//csv文件去重
			Set<CsvRow> rowsSet = new TreeSet<CsvRow>(Comparator.comparing(row -> row.get(0).trim() + "_____" + row.get(1).trim()));
			rowsSet.addAll(csvRows);

		    for(CsvRow data : rowsSet){
		        // 此处进行数据校验
		    	boolean format = true;
		    	String row = data.get(0).trim();
		    	String tag = data.get(1).trim();
		    	// 标签不能为空
		    	if(StrUtil.isEmpty(tag)){
		    		format = false;
		    	}
		    	if(format) {
		    		if(StrUtil.equals(source, Constant.IP_TAG_USER_DEFINED)) {
			    		format = KnowledgeCheckUtil.validIpFormat(row);
			    	}else if(StrUtil.equals(source, Constant.DOMAIN_TAG_USER_DEFINED)){
			    		format = KnowledgeCheckUtil.validateFqdn(row);
			    	}else {
			    		format = KnowledgeCheckUtil.validateApp(row);
			    	}
		    	}
		        Map<String,Object> res = new HashMap<String,Object>();
		        if(StrUtil.equals(source, Constant.IP_TAG_USER_DEFINED)) {
		        	String s = data.get(0);
		        	String ipType = "";
		        	String ip1="";
		        	String ip2="";
		        	if(StrUtil.contains(s, StrUtil.C_SLASH)) {
		        		ipType = Constant.IpFormatType.CIDR.getValue();
		        		ip1 = StrUtil.split(s, StrUtil.C_SLASH).get(0);
		        		ip2 = StrUtil.split(s, StrUtil.C_SLASH).get(1);
		        	}else if(StrUtil.contains(s, StrUtil.DASHED)) {
		        		ipType = Constant.IpFormatType.RANGE.getValue();
		        		ip1 = StrUtil.split(s, StrUtil.DASHED).get(0);
		        		ip2 = StrUtil.split(s, StrUtil.DASHED).get(1);
		        	}else {
		        		ipType = Constant.IpFormatType.SINGLE.getValue();
		        		ip1 = s;
		        		ip2 = s;
		        	}
		        	res.put("addrFormat",ipType);
		        	res.put("ip1", ip1);
		        	res.put("ip2", ip2);
		        }else if(StrUtil.equals(source, Constant.DOMAIN_TAG_USER_DEFINED)){
		        	res.put("domain", data.get(0));
		        }else {
		        	res.put("appName", data.get(0));
		        }
		        res.put("tagValue", data.get(1));
		        if(format) {
		        	res.put("isValid",1);
			        res.put("msg","success");
		        }else {
		        	res.put("isValid",0);
			        res.put("msg","Data format error, please check");
		        }
		        handlerData.add(res);
		    }
		    result.put("data",handlerData);
		    return R.ok(result);
		}catch(Exception e) {
		    return R.error(400,e.getMessage());
		}finally {
			if(ObjectUtil.isNotEmpty(tempFile) && tempFile.exists()) {
				tempFile.delete();
			}
		}
	}

	@Override
	public PageUtils queryKnowledgeBaseList(Map<String, Object> params) {
		preHandleParams(params);
		IPage page = this.page(new Query(KnowledgeBase.class).getPage(params));
		List<KnowledgeBase> knowledgeBases = this.baseMapper.queryPage(page, params);
		//获取知识库关联规则相关条目
		if(ObjectUtils.isNotEmpty(knowledgeBases)) {
			getRuleInfo(knowledgeBases);
		}
		page.setRecords(knowledgeBases);
		return new PageUtils(page);
	}

	private void getRuleInfo(List<KnowledgeBase> knowledgeBases) {
		List<DetectionRule> rules = detectionRuleService.list(new QueryWrapper<DetectionRule>().lambda().eq(DetectionRule::getRuleType,RuleType.INDICATOR_MATHCH.getValue()));
		Map<Integer, KnowledgeBase> idKnowledge = knowledgeBases.stream().collect(Collectors.toMap(KnowledgeBase::getKnowledgeId, Function.identity()));
		if(ObjectUtils.isNotEmpty(rules)) {
			for(DetectionRule rule : rules) {
				String ruleConfig = rule.getRuleConfig();
				Map<String,Object> ruleConfigObj = (Map<String, Object>) JSONObject.parse(ruleConfig);
				Integer knowledgeId = (Integer) ruleConfigObj.get("knowledgeId");
				KnowledgeBase knowledge = idKnowledge.get(knowledgeId);
				if(ObjectUtil.isNotEmpty(knowledge)) {
					List<DetectionRule> refRule = knowledge.getRefRule();
					if(ObjectUtil.isEmpty(refRule)) {
						refRule = new ArrayList<DetectionRule>();
					}
					refRule.add(rule);
					knowledge.setRefRule(refRule);
				}
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R enableKnowledgeBase(Map<String,Object> params) {
		validateEnableParams(params);
		List<Map<String,Object>> list = (List)params.get("list");
		List<KnowledgeBase> KnowledgeBases = new ArrayList<>();
		List<Integer> knowledgeIds = new ArrayList<>();
		List<KnowledgeBaseAuditLog> knowledgeBaseAuditLogs = new ArrayList<KnowledgeBaseAuditLog>();
		for (Map<String,Object> param: list) {
			Integer knowledgeId = (Integer) param.get("knowledgeId");
			Integer isPublished = (Integer) param.get("isPublished");
			Integer status = (Integer) param.get("status");
			KnowledgeBase knowledgeBase = this.getBaseMapper().selectOne(new LambdaQueryWrapper<KnowledgeBase>().eq(KnowledgeBase::getKnowledgeId, knowledgeId));
			if(ObjectUtil.isEmpty(knowledgeBase)) {
				log.debug("local data not exist : {}",knowledgeId);
				throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
			}
			knowledgeBase.setOpUser(ShiroUtils.getUserId());
			knowledgeBase.setOpTime(new Date());
			knowledgeBase.setIsPublished(isPublished);
			knowledgeBase.setStatus(status);
			KnowledgeBases.add(knowledgeBase);
			knowledgeIds.add(knowledgeId);
		}
		this.saveOrUpdateBatch(KnowledgeBases);
		List<KnowledgeBaseSyncVersion> syncVersionBatchs = knowledgeBaseSyncVersionService.syncVersionBatch(knowledgeIds);
		// 记录审计日志
		Map<Integer, KnowledgeBase> idKnowledgeBase = KnowledgeBases.stream().collect(Collectors.toMap(KnowledgeBase::getKnowledgeId, Function.identity()));
		Map<Integer, KnowledgeBaseSyncVersion> idVersion = syncVersionBatchs.stream().collect(Collectors.toMap(KnowledgeBaseSyncVersion::getKnowledgeId, Function.identity()));
		for(Integer knowledgeId : knowledgeIds) {
			KnowledgeBaseAuditLog log = new KnowledgeBaseAuditLog();
			KnowledgeBase knowledgeBase = idKnowledgeBase.get(knowledgeId);
			log.setCommitVersion(idVersion.get(knowledgeId).getVersion());
			log.setDescription(knowledgeBase.getDescription());
			log.setIsPublished(knowledgeBase.getIsPublished());
			log.setKnowledgeId(knowledgeId);
			log.setOpTime(new Date());
			log.setOpType(OperationEnum.UPDATE.getValue());
			log.setOpUser(ShiroUtils.getUserId().intValue());
			log.setApiKeyId(ShiroUtils.getUserEntity().getApiKeyId());
			log.setStatus(knowledgeBase.getStatus());
			knowledgeBaseAuditLogs.add(log);
		}
		knowledgeBaseAuditLogService.saveOrUpdateBatch(knowledgeBaseAuditLogs);
		return R.ok();
	}

	private void validateEnableParams(Map<String,Object> params) {
		if (ObjectUtil.isEmpty(params) || ObjectUtil.isEmpty(params.get("list"))) {
			throw new CNException(RCode.KNOWLEDGE_BASE_ENABLE_PARAMS_INVALID);
		}
	}


	@Override
	public Map<String, Object> statisticsKnowledgeBase(Map<String, Object> params) {
		preHandleParams(params);
		List<KnowledgeBase> knowledgeBases = this.getBaseMapper().queryList(params);
		Set<String> categories = getNonRepetitiveCategory(knowledgeBases);
		List<Map> categoryList = new ArrayList<>();
		for (String category : categories) {
			Map<String, Object> categoryMap = statisticsCategory(knowledgeBases, category);
			categoryList.add(categoryMap);
		}
		List<Map> statusList = statisticsStatus(knowledgeBases);
		Map<String, Object> data = new HashMap<>();
		data.put("categoryList", categoryList);
		data.put("statusList", statusList);
		return data;
	}

	private void preHandleParams(Map<String, Object> params) {
		if (ObjectUtil.isNotEmpty(params.get("category"))) {
			params.put("category", Arrays.asList(String.valueOf(params.get("category")).split(StringPool.COMMA)));
		} else {
			params.put("category", new ArrayList<>());
		}
		if (ObjectUtil.isNotEmpty(params.get("source"))) {
			params.put("source", Arrays.asList(String.valueOf(params.get("source")).split(StringPool.COMMA)));
		} else {
			params.put("source", new ArrayList<>());
		}
		if (ObjectUtil.isNotEmpty(params.get("status"))) {
			params.put("status", Arrays.asList(String.valueOf(params.get("status")).split(StringPool.COMMA)));
		} else {
			params.put("status", new ArrayList<>());
		}
	}

	private Map<String,Object> statisticsCategory(List<KnowledgeBase> knowledgeBases, String category) {
		List<KnowledgeBase> knowledge = knowledgeBases.stream().filter(knowledgeBase -> knowledgeBase.getCategory().equals(category)).collect(Collectors.toList());
		long knowledgeCount = knowledgeBases.stream().filter(knowledgeBase -> knowledgeBase.getCategory().equals(category)).count();
		Set<String> sources = getNonRepetitiveSource(knowledge);
		Map<String, Object> categoryMap = new HashMap<>();
		categoryMap.put("name", category);
		categoryMap.put("count", knowledgeCount);
		List<Map> sourceList = new ArrayList<>();
		for (String source : sources) {
			Map<String, Object> sourceMap = statisticsSource(knowledge, source);
			sourceList.add(sourceMap);
		}
		categoryMap.put("sourceList",sourceList);
		return categoryMap;
	}

	private Map<String,Object> statisticsSource(List<KnowledgeBase> knowledge, String source) {
		long knowledgeCount = knowledge.stream().filter(knowledgeBase -> knowledgeBase.getSource().equals(source)).count();
		Map<String, Object> sourceMap = new HashMap<>();
		sourceMap.put("name", source);
		sourceMap.put("count", knowledgeCount);
		return sourceMap;
	}

	private List<Map> statisticsStatus(List<KnowledgeBase> knowledgeBases) {
		List<Map> statusList = new ArrayList<>();
		long disableCount = knowledgeBases.stream().filter(knowledgeBase -> knowledgeBase.getStatus() == 0).count();
		Map<String, Object> disable = new HashMap<>();
		disable.put("status", 0);
		disable.put("count", disableCount);
		long enableCount = knowledgeBases.stream().filter(knowledgeBase -> knowledgeBase.getStatus() == 1).count();
		Map<String, Object> enable = new HashMap<>();
		enable.put("status", 1);
		enable.put("count", enableCount);
		statusList.add(disable);
		statusList.add(enable);
		return statusList;
	}

	private Set<String> getNonRepetitiveCategory(List<KnowledgeBase> knowledgeBases) {
		Set<String> category = new TreeSet<>();
		for (KnowledgeBase knowledgeBase : knowledgeBases){
			category.add(knowledgeBase.getCategory());
		}
		return category;
	}

	private Set<String> getNonRepetitiveSource(List<KnowledgeBase> knowledgeBases){
		Set<String> source = new TreeSet<>();
		for (KnowledgeBase knowledgeBase : knowledgeBases){
			source.add(knowledgeBase.getSource());
		}
		return source;
	}

	@Override
	public Map<String, Object> queryKnowledgeBaseItem(Integer knowledgeId, Map<String, Object> params) {
		params.put("knowledgeId",knowledgeId);
		KnowledgeBase knowledgeBase = this.getBaseMapper().queryInfo(knowledgeId);
		validateKnowledgeBase(knowledgeBase);
		String source = knowledgeBase.getSource();
		PageUtils pageUtils = createItemPages(source, params);
		Map<String, Object> pageMap = addKnowledgeBaseMessage(pageUtils, knowledgeBase);
		pageMap = updateItemList(pageMap);
		return pageMap;
	}

	private PageUtils createItemPages(String source, Map<String, Object> params) {
		PageUtils pageUtils = null;
		switch (source){
			case APP_TAG_USER_DEFINED:
				pageUtils = appTagUserDefinedService.queryListByKnowledgeId(params);
				break;
			case DOMAIN_TAG_USER_DEFINED:
				pageUtils = domainTagUserDefinedService.queryListByKnowledgeId(params);
				break;
			case IP_TAG_USER_DEFINED:
				pageUtils = ipTagUserDefinedService.queryListByKnowledgeId(params);
				break;
			case IOC_DARKWEB:
				pageUtils = iocDarkwebService.queryListByKnowledgeId(params);
				break;
			case IOC_MALWARE:
				pageUtils = iocMalwareService.queryListByKnowledgeId(params);
				break;
			case PSIPHON3_IP:
				pageUtils = psiphon3IpService.queryListByKnowledgeId(params);
				break;
			case APP_CATEGORY_BUILT_IN:
				pageUtils = appCategoryBuiltInService.queryListByKnowledgeId(params);
				break;
			case FQDN_ICP_BUILT_IN:
				pageUtils = fqdnIcpBuiltInService.queryListByKnowledgeId(params);
				break;
			case FQDN_WHO_IS_BUILT_IN:
				pageUtils = fqdnWhoIsBuiltInService.queryListByKnowledgeId(params);
				break;
			case FQDN_CATEGORY_BUILT_IN:
				pageUtils = fqdnCategoryBuiltInService.queryListByKnowledgeId(params);
				break;
			case IP_ASN_BUILT_IN:
				pageUtils = ipAsnBuiltInService.queryListByKnowledgeId(params);
				break;
			case IP_LOCATION_BUILT_IN:
				pageUtils = ipLocationBuiltInService.queryListByKnowledgeId(params);
				break;
			case IDC_RENTER_BUILT_IN:
				pageUtils = idcRenterBuiltInService.queryListByKnowledgeId(params);
				break;
			case INTERNAL_IP_BUILT_IN:
				pageUtils = internalIpBuiltInService.queryListByKnowledgeId(params);
				break;
			case LINK_DIRECTION_BUILT_IN:
				pageUtils = linkDirectionBuiltInService.queryListByKnowledgeId(params);
				break;
			default:
				throw new CNException(KNOWLEDGE_SOURCE_ERROR);
		}
		return pageUtils;
	}

	private Map<String, Object> addKnowledgeBaseMessage(PageUtils pageUtils, KnowledgeBase knowledgeBase){
		Map<String, Object> pageMap = JSONUtil.parseObj(JSONUtil.toJsonStr(pageUtils));
		Map<String, Object> knowledge = JSONUtil.parseObj(JSONUtil.toJsonStr(knowledgeBase));
		pageMap.putAll(knowledge);
		return pageMap;
	}

	private Map<String, Object> updateItemList(Map<String, Object> pageMap) {
		List<Map<String, Object>> list = (List<Map<String, Object>>)pageMap.get("list");
		pageMap.put("itemList",pageMap.get("list"));
		pageMap.remove("list");
		return pageMap;
	}

	private void validateKnowledgeBase(KnowledgeBase knowledgeBase) {
		if(ObjectUtil.isEmpty(knowledgeBase)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer addOrModifyKnowledgeBase(Map<String, Object> params) {
		String source = (String) params.get("source");
		Integer knowledgeId = (Integer) params.get("knowledgeId");
		Map<String, Object> result = validateParam(params);
		
		KnowledgeBase knowledgeBase = (KnowledgeBase) result.get("knowledgeBase");
		source = knowledgeBase.getSource();
		this.saveOrUpdate(knowledgeBase);
		List<Integer> deleteItemIds = (List<Integer>) params.get("deleteItemIds");
		switch(source) {
			case IP_TAG_USER_DEFINED:
				List<IpTagUserDefined> ips = (List<IpTagUserDefined>) result.get(source);
				if(ObjectUtil.isNotEmpty(ips)) {
					ips.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					// 将ip数据分为新增和更新
					Map<Boolean, List<IpTagUserDefined>> ipMap = ips.stream().collect(Collectors.groupingBy(ip-> ObjectUtil.isNotEmpty(ip.getId())));
					List<IpTagUserDefined> updateIps = ipMap.get(true);
					List<IpTagUserDefined> insertIps = ipMap.get(false);
					ipTagUserDefinedService.saveBatchForBinary(insertIps);
					ipTagUserDefinedService.modifyBatchForBinary(updateIps);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					ipTagUserDefinedService.removeByIds(deleteItemIds);
				}
				break;
			case APP_TAG_USER_DEFINED:
				List<AppTagUserDefined> apps = (List<AppTagUserDefined>) result.get(source);
				if(ObjectUtil.isNotEmpty(apps)) {
					apps.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					appTagUserDefinedService.saveOrUpdateBatch(apps);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					appTagUserDefinedService.removeByIds(deleteItemIds);
				}
				break;
			case DOMAIN_TAG_USER_DEFINED:
				List<DomainTagUserDefined> domains = (List<DomainTagUserDefined>) result.get(source);
				if(ObjectUtil.isNotEmpty(domains)) {
					domains.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					domainTagUserDefinedService.saveOrUpdateBatch(domains);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					domainTagUserDefinedService.removeByIds(deleteItemIds);
				}
				break;
			case IOC_DARKWEB:
				List<IocDarkweb> iocDarkwebs = (List<IocDarkweb>) result.get(source);
				if(ObjectUtil.isNotEmpty(iocDarkwebs)) {
					iocDarkwebs.stream().forEach(iocDarkweb-> iocDarkweb.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					iocDarkwebService.saveOrUpdateBatch(iocDarkwebs);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					iocDarkwebService.removeByIds(deleteItemIds);
				}
				break;
			case IOC_MALWARE:
				List<IocMalware> iocMalwares = (List<IocMalware>) result.get(source);
				if(ObjectUtil.isNotEmpty(iocMalwares)) {
					iocMalwares.stream().forEach(iocMalware-> iocMalware.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					iocMalwareService.saveOrUpdateBatch(iocMalwares);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					iocMalwareService.removeByIds(deleteItemIds);
				}
				break;
			case APP_CATEGORY_BUILT_IN:
				List<AppCategoryBuiltIn> appCategoryBuiltIns = (List<AppCategoryBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(appCategoryBuiltIns)) {
					appCategoryBuiltIns.stream().forEach(appCategoryBuiltIn-> appCategoryBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					appCategoryBuiltInService.saveOrUpdateBatch(appCategoryBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					appCategoryBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case FQDN_ICP_BUILT_IN:
				List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = (List<FqdnIcpBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(fqdnIcpBuiltIns)) {
					fqdnIcpBuiltIns.stream().forEach(fqdnIcpBuiltIn-> fqdnIcpBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					fqdnIcpBuiltInService.saveOrUpdateBatch(fqdnIcpBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					fqdnIcpBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case FQDN_WHO_IS_BUILT_IN:
				List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = (List<FqdnWhoIsBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIns)) {
					fqdnWhoIsBuiltIns.stream().forEach(fqdnWhoIsBuiltIn-> fqdnWhoIsBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					fqdnWhoIsBuiltInService.saveOrUpdateBatch(fqdnWhoIsBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					fqdnWhoIsBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case FQDN_CATEGORY_BUILT_IN:
				List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = (List<FqdnCategoryBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(fqdnCategoryBuiltIns)) {
					fqdnCategoryBuiltIns.stream().forEach(fqdnCategoryBuiltIn-> fqdnCategoryBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					fqdnCategoryBuiltInService.saveOrUpdateBatch(fqdnCategoryBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					fqdnCategoryBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case IP_ASN_BUILT_IN:
				List<IpAsnBuiltIn> ipAsnBuiltIns = (List<IpAsnBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(ipAsnBuiltIns)) {
					ipAsnBuiltIns.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					// 将ip数据分为新增和更新
					Map<Boolean, List<IpAsnBuiltIn>> ipMap = ipAsnBuiltIns.stream().collect(Collectors.groupingBy(ip-> ObjectUtil.isNotEmpty(ip.getId())));
					List<IpAsnBuiltIn> updateIps = ipMap.get(true);
					List<IpAsnBuiltIn> insertIps = ipMap.get(false);
					ipAsnBuiltInService.saveBatchForBinary(insertIps);
					ipAsnBuiltInService.modifyBatchForBinary(updateIps);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					ipAsnBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case IP_LOCATION_BUILT_IN:
				List<IpLocationBuiltIn> ipLocationBuiltIns = (List<IpLocationBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(ipLocationBuiltIns)) {
					ipLocationBuiltIns.stream().forEach(ipLocationBuiltIn-> ipLocationBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					ipLocationBuiltInService.saveOrUpdateBatch(ipLocationBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					ipLocationBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case PSIPHON3_IP:
				List<Psiphon3Ip> psiphon3Ips = (List<Psiphon3Ip>) result.get(source);
				if(ObjectUtil.isNotEmpty(psiphon3Ips)) {
					psiphon3Ips.stream().forEach(ps3Ip-> ps3Ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					// 将ip数据分为新增和更新
					Map<Boolean, List<Psiphon3Ip>> ps3IpMap = psiphon3Ips.stream().collect(Collectors.groupingBy(ps3Ip-> ObjectUtil.isNotEmpty(ps3Ip.getId())));
					List<Psiphon3Ip> updateIps = ps3IpMap.get(true);
					List<Psiphon3Ip> insertIps = ps3IpMap.get(false);
					psiphon3IpService.saveBatchForBinary(insertIps);
					psiphon3IpService.modifyBatchForBinary(updateIps);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					psiphon3IpService.removeByIds(deleteItemIds);
				}
				break;
			case IDC_RENTER_BUILT_IN:
				List<IdcRenterBuiltIn> idcRenterBuiltIns = (List<IdcRenterBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
					idcRenterBuiltIns.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					// 将ip数据分为新增和更新
					Map<Boolean, List<IdcRenterBuiltIn>> ipMap = idcRenterBuiltIns.stream().collect(Collectors.groupingBy(ip-> ObjectUtil.isNotEmpty(ip.getId())));
					List<IdcRenterBuiltIn> updateIps = ipMap.get(true);
					List<IdcRenterBuiltIn> insertIps = ipMap.get(false);
					idcRenterBuiltInService.saveBatchForBinary(insertIps);
					idcRenterBuiltInService.modifyBatchForBinary(updateIps);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					idcRenterBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case INTERNAL_IP_BUILT_IN:
				List<InternalIpBuiltIn> internalIpBuiltIns = (List<InternalIpBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
					internalIpBuiltIns.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					// 将ip数据分为新增和更新
					Map<Boolean, List<InternalIpBuiltIn>> ipMap = internalIpBuiltIns.stream().collect(Collectors.groupingBy(ip-> ObjectUtil.isNotEmpty(ip.getId())));
					List<InternalIpBuiltIn> updateIps = ipMap.get(true);
					List<InternalIpBuiltIn> insertIps = ipMap.get(false);
					internalIpBuiltInService.saveBatchForBinary(insertIps);
					internalIpBuiltInService.modifyBatchForBinary(updateIps);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					internalIpBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case LINK_DIRECTION_BUILT_IN:
				List<LinkDirectionBuiltIn> linkDirectionBuiltIns = (List<LinkDirectionBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(linkDirectionBuiltIns)) {
					linkDirectionBuiltIns.stream().forEach(linkDirectionBuiltIn-> linkDirectionBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					linkDirectionBuiltInService.saveOrUpdateBatch(linkDirectionBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					linkDirectionBuiltInService.removeByIds(deleteItemIds);
				}
				break;
			case DNS_SERVER_INFO_BUILT_IN:
				List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns = (List<DnsServerInfoBuiltIn>) result.get(source);
				if(ObjectUtil.isNotEmpty(dnsServerInfoBuiltIns)) {
					dnsServerInfoBuiltIns.stream().forEach(dnsServerInfoBuiltIn-> dnsServerInfoBuiltIn.setKnowledgeId(knowledgeBase.getKnowledgeId()));
					dnsServerInfoBuiltInService.saveOrUpdateBatch(dnsServerInfoBuiltIns);
				}
				if(ObjectUtil.isNotEmpty(deleteItemIds)) {
					dnsServerInfoBuiltInService.removeByIds(deleteItemIds);
				}
				break;
		}
		KnowledgeBaseSyncVersion knowledgeBaseSyncVersion = knowledgeBaseSyncVersionService.getById(knowledgeBase.getKnowledgeId());
		KnowledgeBaseAuditLog knowledgeBaseAuditLog = new KnowledgeBaseAuditLog();
		knowledgeBaseAuditLog.setDescription(knowledgeBase.getDescription());
		knowledgeBaseAuditLog.setIsPublished(knowledgeBase.getIsPublished());
		knowledgeBaseAuditLog.setKnowledgeId(knowledgeBase.getKnowledgeId());
		knowledgeBaseAuditLog.setOpTime(new Date());
		knowledgeBaseAuditLog.setOpUser(ShiroUtils.getUserId().intValue());
		knowledgeBaseAuditLog.setApiKeyId(ShiroUtils.getUserEntity().getApiKeyId());
		knowledgeBaseAuditLog.setStatus(knowledgeBase.getStatus());
		knowledgeBaseAuditLog.setColor(knowledgeBase.getColor());
		if(ObjectUtil.isNotEmpty(knowledgeBaseSyncVersion)) {
			Integer version = knowledgeBaseSyncVersion.getVersion();
			knowledgeBaseSyncVersion.setVersion(version+1);
			knowledgeBaseAuditLog.setCommitVersion(version+1);
			knowledgeBaseAuditLog.setOpType(OperationEnum.UPDATE.getValue());
		}else {
			knowledgeBaseSyncVersion = new KnowledgeBaseSyncVersion();
			knowledgeBaseSyncVersion.setVersion(1);
			knowledgeBaseAuditLog.setCommitVersion(1);
			knowledgeBaseAuditLog.setOpType(OperationEnum.ADD.getValue());
		}
		knowledgeBaseSyncVersion.setKnowledgeId(knowledgeBase.getKnowledgeId());
		knowledgeBaseSyncVersion.setOpTime(new Date());
		knowledgeBaseSyncVersionService.saveOrUpdate(knowledgeBaseSyncVersion);
		knowledgeBaseAuditLogService.saveOrUpdate(knowledgeBaseAuditLog);
		return knowledgeBase.getKnowledgeId();
	}
	
	@Override
	public Map<String,Object> validateParam(Map<String, Object> params) {
		Map<String,Object> result = new HashMap<String,Object>(); 
		Integer knowledgeId = (Integer) params.get("knowledgeId");
		String name = (String) params.get("name");
		String source = (String) params.get("source");
		String category = (String) params.get("category");
		String description = (String) params.get("description");
		Integer status = (Integer) params.get("status");
		String color = (String) params.get("color");
		List<KnowledgeBase> queryByName = this.baseMapper.selectList(new QueryWrapper<KnowledgeBase>().lambda()
				.eq(KnowledgeBase::getName, name)
				.ne(ObjectUtil.isNotEmpty(knowledgeId),KnowledgeBase::getKnowledgeId,knowledgeId));
		if(ObjectUtil.isNotEmpty(queryByName)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NAME_DUPLICATE);
		}
		KnowledgeBase knowledgeBase = null;
		if(ObjectUtil.isEmpty(knowledgeId)){
			if(StrUtil.isEmpty(source)) {
				throw new CNException(RCode.KNOWLEDGE_SOURCE_ISNULL);
			}
			knowledgeBase = new KnowledgeBase();
			knowledgeBase.setSource(source);
			knowledgeBase.setCTime(new Date());
			knowledgeBase.setIsBuiltIn(0);
			knowledgeBase.setIsPublished(1);
			knowledgeBase.setKnowledgeId(knowledgeId);
		}else {
			knowledgeBase = this.getById(knowledgeId);
			source = knowledgeBase.getSource();
		}
		knowledgeBase.setName(name);
		knowledgeBase.setCategory(category);
		knowledgeBase.setDescription(description);
		knowledgeBase.setOpTime(new Date());
		knowledgeBase.setOpUser(ShiroUtils.getUserId());
		knowledgeBase.setStatus(status==null ? 1 : status);
		knowledgeBase.setColor(color);
		result.put("knowledgeBase", knowledgeBase);		
		List<Map<String,Object>> addDatas  = (List<Map<String, Object>>) params.get("addItemList");
		List<Map<String,Object>> modifyDatas  = (List<Map<String, Object>>) params.get("updateItemList");
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		if(ObjectUtil.isNotEmpty(addDatas)) {
			datas.addAll(addDatas);
		}
		if(ObjectUtil.isNotEmpty(modifyDatas)) {
			datas.addAll(modifyDatas);
		}
		if(ObjectUtil.isNotEmpty(datas)) {
			switch(source) {
				case IP_TAG_USER_DEFINED:
					List<IpTagUserDefined> ipTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(datas),IpTagUserDefined.class);
					ipTagUserDefineds.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCTime(new Date());
						}
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
						// 计算ip binary
						Map<String, String> ipHandler = ipHandler(data.getAddrFormat(),data.getIp1(),data.getIp2());
						data.setStartIpBinary(ipHandler.get("start_ip_binary"));
						data.setEndIpBinary(ipHandler.get("end_ip_binary"));
					});
					result.put(source, ipTagUserDefineds);
					break;
				case APP_TAG_USER_DEFINED:
					List<AppTagUserDefined> appTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(datas),AppTagUserDefined.class);
					appTagUserDefineds.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCTime(new Date());
						}
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
					});
					result.put(source, appTagUserDefineds);
					break;
				case DOMAIN_TAG_USER_DEFINED:
					List<DomainTagUserDefined> domainTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(datas),DomainTagUserDefined.class);
					domainTagUserDefineds.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCTime(new Date());
						}
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
					});
					result.put(source, domainTagUserDefineds);
					break;
				case IOC_DARKWEB:
					List<IocDarkweb> iocDarkwebs = JSONUtil.toList(JSONUtil.toJsonStr(datas),IocDarkweb.class);
					iocDarkwebs.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCreateTime(new Date().getTime());
						}
						data.setUpdateTime(new Date().getTime());
					});
					result.put(source,iocDarkwebs);
					break;
				case IOC_MALWARE:
					List<IocMalware> iocMalwares = JSONUtil.toList(JSONUtil.toJsonStr(datas),IocMalware.class);
					iocMalwares.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCreateTime(new Date().getTime());
						}
						data.setUpdateTime(new Date().getTime());
					});
					result.put(source, iocMalwares);
					break;
				case APP_CATEGORY_BUILT_IN:
					List<AppCategoryBuiltIn> appCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),AppCategoryBuiltIn.class);
					appCategoryBuiltIns.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCTime(new Date());
						}
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
					});
					result.put(source, appCategoryBuiltIns);
					break;
				case FQDN_ICP_BUILT_IN:
					List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),FqdnIcpBuiltIn.class);
					fqdnIcpBuiltIns.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCreateTime(new Date());
						}
						data.setUpdateTime(new Date());
					});
					result.put(source,fqdnIcpBuiltIns);
					break;
				case FQDN_WHO_IS_BUILT_IN:
					List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),FqdnWhoIsBuiltIn.class);
					fqdnWhoIsBuiltIns.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCreateTime(new Date());
						}
						data.setUpdateTime(new Date());
					});
					result.put(source, fqdnWhoIsBuiltIns);
					break;
				case FQDN_CATEGORY_BUILT_IN:
					List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),FqdnCategoryBuiltIn.class);
					fqdnCategoryBuiltIns.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCreateTime(new Date());
						}
						data.setUpdateTime(new Date());
					});
					result.put(source, fqdnCategoryBuiltIns);
					break;
				case IP_ASN_BUILT_IN:
					List<IpAsnBuiltIn> ipAsnBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),IpAsnBuiltIn.class);
					ipAsnBuiltIns.stream().forEach(data->{
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
						// 计算ip binary
						Map<String, String> ipHandler = ipHandler(data.getAddrFormat(),data.getIp1(),data.getIp2());
						data.setStartIpBinary(ipHandler.get("start_ip_binary"));
						data.setEndIpBinary(ipHandler.get("end_ip_binary"));
					});
					result.put(source, ipAsnBuiltIns);
					break;
				case IP_LOCATION_BUILT_IN:
					List<IpLocationBuiltIn> ipLocationBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),IpLocationBuiltIn.class);
					ipLocationBuiltIns.stream().forEach(data->{
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
					});
					result.put(source, ipLocationBuiltIns);
					break;
				case PSIPHON3_IP:
					List<Psiphon3Ip> psiphon3Ips = JSONUtil.toList(JSONUtil.toJsonStr(datas),Psiphon3Ip.class);
					psiphon3Ips.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCTime(new Date());
						}
						data.setOpUser(ShiroUtils.getUserId().intValue());
						data.setOpTime(new Date());
						// 计算ip binary
						Map<String, String> ipHandler = ipHandler(data.getAddrFormat(),data.getIp1(),data.getIp2());
						data.setStartIpBinary(ipHandler.get("start_ip_binary"));
						data.setEndIpBinary(ipHandler.get("end_ip_binary"));
					});
					result.put(source, psiphon3Ips);
					break;
				case IDC_RENTER_BUILT_IN:
					List<IdcRenterBuiltIn> idcRenterBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),IdcRenterBuiltIn.class);
					idcRenterBuiltIns.stream().forEach(data->{
						// 计算ip binary
						Map<String, String> ipHandler = ipHandler(data.getAddrFormat(),data.getIp1(),data.getIp2());
						data.setStartIpBinary(ipHandler.get("start_ip_binary"));
						data.setEndIpBinary(ipHandler.get("end_ip_binary"));
					});
					result.put(source, idcRenterBuiltIns);
					break;
				case INTERNAL_IP_BUILT_IN:
					List<InternalIpBuiltIn> internalIpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),InternalIpBuiltIn.class);
					internalIpBuiltIns.stream().forEach(data->{
						// 计算ip binary
						Map<String, String> ipHandler = ipHandler(data.getAddrFormat(),data.getIp1(),data.getIp2());
						data.setStartIpBinary(ipHandler.get("start_ip_binary"));
						data.setEndIpBinary(ipHandler.get("end_ip_binary"));
					});
					result.put(source, internalIpBuiltIns);
					break;
				case LINK_DIRECTION_BUILT_IN:
					List<LinkDirectionBuiltIn> linkDirectionBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),LinkDirectionBuiltIn.class);
					result.put(source, linkDirectionBuiltIns);
					break;
				case DNS_SERVER_INFO_BUILT_IN:
					List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(datas),DnsServerInfoBuiltIn.class);
					dnsServerInfoBuiltIns.stream().forEach(data->{
						if(ObjectUtil.isEmpty(data.getId())) {
							data.setCreateTime(new Date());
						}
						data.setUpdateTime(new Date());
					});
					result.put(source, dnsServerInfoBuiltIns);
					break;
				default:{
					throw new CNException(RCode.KNOWLEDGE_SOURCE_ERROR);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer importBatch(MultipartFile file, Integer knowledgeId, String action, Integer status, String color, String description) {
		KnowledgeBase knowledgeBase = this.baseMapper.selectById(knowledgeId);
		// 参数不为null 空串也默认为有值 进行置空处理 null代表无任何操作
		if(color!=null) {
			knowledgeBase.setColor(color);
		}
		if(description!=null) {
			knowledgeBase.setDescription(description);
		}
		if(ObjectUtil.isNotEmpty(status)) {
			knowledgeBase.setStatus(status);
		}
		if (ObjectUtil.isEmpty(knowledgeBase)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
		}
		List<Integer> deleteDataIds = Lists.newArrayList();
		List<Map<String, String>> saveOrUpdateList = Lists.newArrayList();
		//覆盖操作时 将对应表中数据删除清空
		if (StrUtil.equals(action, "overwrite")) {
			basicImportAndExportService.removeList(knowledgeBase.getSource(), knowledgeBase.getKnowledgeId());
		}
		this.validAndBuildDatasets(file, knowledgeBase, action, saveOrUpdateList, deleteDataIds);
		//更新操作时 删除多余重复数据
		if (StrUtil.equals(action, "update") && !deleteDataIds.isEmpty()) {
			basicImportAndExportService.removeList(knowledgeBase.getSource(), deleteDataIds);
		}
		KnowledgeBaseSyncVersion syncVersion = knowledgeBaseSyncVersionService.syncVersion(knowledgeBase.getKnowledgeId());
		KnowledgeBaseAuditLog log = new KnowledgeBaseAuditLog();
		log.setDescription(knowledgeBase.getDescription());
		log.setIsPublished(knowledgeBase.getIsPublished());
		log.setKnowledgeId(knowledgeBase.getKnowledgeId());
		log.setOpTime(new Date());
		log.setOpUser(ShiroUtils.getUserId().intValue());
		log.setApiKeyId(ShiroUtils.getUserEntity().getApiKeyId());
		log.setStatus(knowledgeBase.getStatus());
		log.setColor(knowledgeBase.getColor());
		log.setOpType(OperationEnum.UPDATE.getValue());		
		log.setCommitVersion(syncVersion.getVersion());
		knowledgeBaseAuditLogService.save(log);
		this.updateById(knowledgeBase);
		return knowledgeId;
	}

	@Override
	public void validAndBuildDatasets(MultipartFile file, KnowledgeBase knowledgeBase, String action, List<Map<String, String>> importDataList, List<Integer> deleteDataIds) {
		// 根据source获取对应的表头信息
		String knowledgeImportConfigJson = sysConfigService.getValue("knowledge_import_config_json");
		HashMap knowledgeConfigs = parseObject(knowledgeImportConfigJson, HashMap.class);
		Map<String,Object> sourceConfig = (Map<String, Object>) knowledgeConfigs.get(knowledgeBase.getSource());
		List<String> headerInfos = StrUtil.split((String)sourceConfig.get("headers"), StrUtil.C_COMMA);
		if(ObjectUtils.isEmpty(headerInfos)) {
			throw new CNException(RCode.EXCELFILE_HEADER_TEMPLATE_ERROR);
		}
		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		List<Map<String,Object>> infos = (List<Map<String, Object>>) sourceConfig.get("infos");
		// 将系统配置表头和验证相关整理成map
		Map<String,Map<String,Object>> configs = new HashMap<>();
		for(Map m : infos) {
			configs.put((String)m.get("name"), m);
		}
		String validateColumn = (String)sourceConfig.get("validateColumn");
		// 根据配置校验数据是否重复
		List<String> validateColumns = StrUtil.split(validateColumn, StrUtil.C_COMMA);
		if(ObjectUtil.isEmpty(validateColumns)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_NOTNULL);
		}
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		Connection conn = openSession.getConnection();
        switch (fileType) {
            case "csv": {
            	File tempCsvFile = new File(Constant.TEMP_PATH + File.separator + "csv_" + UUID.randomUUID());
            	try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), tempCsvFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
    		    CsvReader reader = CsvUtil.getReader(ResourceUtil.getReader(tempCsvFile.getPath(), CharsetDetector.detect(tempCsvFile)));
    		    reader.setContainsHeader(true);
    		    Set<String> header = reader.stream().findFirst().get().getFieldMap().keySet();
    		    ArrayList<String> headerInfo = ListUtil.toList(header);
    		    // 表头校验
                if (!CSVUtils.validateCSVHeader(headerInfo, headerInfos)) {
                    throw new CNException(RCode.EXCELFILE_HEADER_TEMPLATE_ERROR);
                }
                CsvReader reader2 = CsvUtil.getReader(ResourceUtil.getReader(tempCsvFile.getPath(), CharsetDetector.detect(tempCsvFile)));
    		    reader2.setContainsHeader(true);
    			reader2.stream().forEach(row -> {
    				// 当前行号
    				Map<String, String> tmpMap = Tool.MapUtil.newHashMap();
    				long originalLineNumber = row.getOriginalLineNumber();
    				boolean ipHandler = false;
    				Map<String, String> fieldMap = row.getFieldMap();
    				for(String headerName : fieldMap.keySet()) {
    					String value = fieldMap.get(headerName);
    					if(headerName.indexOf("\uFEFF")>= 0) {
    						headerName = headerName.substring(1);
    					}
    					Map<String, Object> info = configs.get(headerName);
						String checkType = (String) info.get("checkType");
						List<String> validateVal = (List<String>) info.get("validateVal");
						if(StrUtil.equals(headerName, "addr_format")) {
							ipHandler = true;
						}
						if(StrUtil.equals(checkType, "in")) {
							if(!validateVal.contains(value)) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",originalLineNumber,headerName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}else if(StrUtil.equals(checkType, "range")) {
							if(!(Integer.valueOf(validateVal.get(0))<=Integer.valueOf(value) && Integer.valueOf(validateVal.get(1))>=Integer.valueOf(value))) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",originalLineNumber,headerName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}else if(StrUtil.equals(checkType, "ip")) {
							boolean validIpFormat = KnowledgeCheckUtil.validIpFormat(value);
							if(!validIpFormat) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",originalLineNumber,headerName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}else if(StrUtil.equals(checkType, "notNull")) {
							if(ObjectUtil.isEmpty(value)) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",originalLineNumber,headerName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}
                        tmpMap.put(headerName, value);
    				}
    				// 进行ip1 ip2相关数据内容处理
                    if(ipHandler) {
                    	String addrFormat = tmpMap.get("addr_format");
                    	String ip1 = tmpMap.get("ip1");
                    	String ip2 = tmpMap.get("ip2");
                    	Map<String, String> ipHandlerResult = ipHandler(addrFormat, ip1, ip2);
                    	tmpMap.putAll(ipHandlerResult);
                    }
					if (ObjectUtil.isNotEmpty(tmpMap)) {
						importDataList.add(tmpMap);
					}
					if(importDataList.size() == 50) {
						if (StrUtil.equals(action, "update")) {
							this.updateDataHandler(importDataList,knowledgeBase,validateColumns);
						}
						saveOrUpdateBatch(importDataList,knowledgeBase,conn);
						importDataList.clear();
					}
    			});     
    			if(ObjectUtil.isNotEmpty(importDataList)) {
    				if (StrUtil.equals(action, "update")) {
						this.updateDataHandler(importDataList,knowledgeBase,validateColumns);
					}
    				saveOrUpdateBatch(importDataList,knowledgeBase,conn);
    				importDataList.clear();
    			}
    			if(FileUtil.exist(tempCsvFile)) {
    				tempCsvFile.delete();
    			}
				break;
			}
            default: {
                throw new CNException(RCode.KNOWLEDGE_EXCELFILE_TYPE_ERROR);
            }
        }
	}

	private void updateDataHandler(List<Map<String, String>> importDataList,KnowledgeBase knowledgeBase,List<String> condition) {
		// 更新操作 更新最早创建数据、删除其余冗余数据
		List<Map<String, Object>> results = basicImportAndExportService.queryDataIds(knowledgeBase.getSource(), condition, knowledgeBase.getKnowledgeId(), importDataList);
		if(ObjectUtil.isNotEmpty(results)) {
			Map<String,Object> conId = new HashMap<String,Object>();
			results.stream().forEach(result->{
				String key = "";
				for(String con : condition) {
					key += result.get(con).toString();
				}
				conId.put(key,result.get("id"));
			});
			for(Map<String,String> data : importDataList) {
				String val = "";
				for(String con : condition) {
					val += data.get(con).toString();
				}
				Object id = conId.get(val);
				data.put("id",ObjectUtil.isNotEmpty(id)?id.toString():null);
			}
		}
	}
	
	private void saveOrUpdateBatch(List importDataList, KnowledgeBase knowledgeBase,Connection conn) {
		String source = knowledgeBase.getSource();
		// 根据类型不同 生成对应实体 保存入库
		switch(source) {
			case IP_TAG_USER_DEFINED:
				List<IpTagUserDefined> ipTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpTagUserDefined.class);
				ipTagUserDefineds.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCTime(new Date());
					}
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IpTagUserDefined>> ipTagUserDefinedMap = ipTagUserDefineds.stream().collect(Collectors.groupingBy(ipTagUserDefined-> ObjectUtil.isNotEmpty(ipTagUserDefined.getId())));
				List<IpTagUserDefined> updateIps = ipTagUserDefinedMap.get(true);
				List<IpTagUserDefined> insertIps = ipTagUserDefinedMap.get(false);
				ipTagUserDefinedService.saveBatchJDBC(conn,insertIps);
				ipTagUserDefinedService.modifyBatchJDBC(conn,updateIps);
				break;
			case APP_TAG_USER_DEFINED:
				List<AppTagUserDefined> appTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),AppTagUserDefined.class);
				appTagUserDefineds.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCTime(new Date());
					}
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<AppTagUserDefined>> appTagUserDefinedMap = appTagUserDefineds.stream().collect(Collectors.groupingBy(appTagUserDefined-> ObjectUtil.isNotEmpty(appTagUserDefined.getId())));
				List<AppTagUserDefined> updateAppTagUserDefineds = appTagUserDefinedMap.get(true);
				List<AppTagUserDefined> insertAppTagUserDefineds = appTagUserDefinedMap.get(false);
				appTagUserDefinedService.saveBatchJDBC(conn,insertAppTagUserDefineds);
				appTagUserDefinedService.modifyBatchJDBC(conn,updateAppTagUserDefineds);
				break;
			case DOMAIN_TAG_USER_DEFINED:
				List<DomainTagUserDefined> domainTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),DomainTagUserDefined.class);
				domainTagUserDefineds.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCTime(new Date());
					}
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<DomainTagUserDefined>> domainTagUserDefinedMap = domainTagUserDefineds.stream().collect(Collectors.groupingBy(domainTagUserDefined-> ObjectUtil.isNotEmpty(domainTagUserDefined.getId())));
				List<DomainTagUserDefined> updateDomainTagUserDefineds = domainTagUserDefinedMap.get(true);
				List<DomainTagUserDefined> insertDomainTagUserDefineds = domainTagUserDefinedMap.get(false);
				domainTagUserDefinedService.saveBatchJDBC(conn,insertDomainTagUserDefineds);
				domainTagUserDefinedService.modifyBatchJDBC(conn,updateDomainTagUserDefineds);
				break;
			case IOC_DARKWEB:
				List<IocDarkweb> iocDarkwebs = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IocDarkweb.class);
				iocDarkwebs.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCreateTime(new Date().getTime());
					}
					data.setUpdateTime(new Date().getTime());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IocDarkweb>> iocDarkwebMap = iocDarkwebs.stream().collect(Collectors.groupingBy(iocDarkweb-> ObjectUtil.isNotEmpty(iocDarkweb.getId())));
				List<IocDarkweb> updateIocDarkwebs = iocDarkwebMap.get(true);
				List<IocDarkweb> insertIocDarkwebs = iocDarkwebMap.get(false);
				iocDarkwebService.saveBatchJDBC(conn,insertIocDarkwebs);
				iocDarkwebService.modifyBatchJDBC(conn,updateIocDarkwebs);
				break;
			case IOC_MALWARE:
				List<IocMalware> iocMalwares = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IocMalware.class);
				iocMalwares.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCreateTime(new Date().getTime());
					}
					data.setUpdateTime(new Date().getTime());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IocMalware>> iocMalwareMap = iocMalwares.stream().collect(Collectors.groupingBy(iocMalware-> ObjectUtil.isNotEmpty(iocMalware.getId())));
				List<IocMalware> updateIocMalwares = iocMalwareMap.get(true);
				List<IocMalware> insertIocMalwares = iocMalwareMap.get(false);
				iocMalwareService.saveBatchJDBC(conn,insertIocMalwares);
				iocMalwareService.modifyBatchJDBC(conn,updateIocMalwares);
				break;
			case APP_CATEGORY_BUILT_IN:
				List<AppCategoryBuiltIn> appCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),AppCategoryBuiltIn.class);
				appCategoryBuiltIns.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCTime(new Date());
					}
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<AppCategoryBuiltIn>> appCategoryBuiltInMap = appCategoryBuiltIns.stream().collect(Collectors.groupingBy(appCategoryBuiltIn-> ObjectUtil.isNotEmpty(appCategoryBuiltIn.getId())));
				List<AppCategoryBuiltIn> updateAppCategoryBuiltIns = appCategoryBuiltInMap.get(true);
				List<AppCategoryBuiltIn> insertAppCategoryBuiltIns = appCategoryBuiltInMap.get(false);
				appCategoryBuiltInService.saveBatchJDBC(conn,insertAppCategoryBuiltIns);
				appCategoryBuiltInService.modifyBatchJDBC(conn,updateAppCategoryBuiltIns);
				break;
			case FQDN_ICP_BUILT_IN:
				List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnIcpBuiltIn.class);
				fqdnIcpBuiltIns.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCreateTime(new Date());
					}
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<FqdnIcpBuiltIn>> fqdnIcpBuiltInMap = fqdnIcpBuiltIns.stream().collect(Collectors.groupingBy(fqdnIcpBuiltIn-> ObjectUtil.isNotEmpty(fqdnIcpBuiltIn.getId())));
				List<FqdnIcpBuiltIn> updateFqdnIcpBuiltIns = fqdnIcpBuiltInMap.get(true);
				List<FqdnIcpBuiltIn> insertFqdnIcpBuiltIns = fqdnIcpBuiltInMap.get(false);
				fqdnIcpBuiltInService.saveBatchJDBC(conn,insertFqdnIcpBuiltIns);
				fqdnIcpBuiltInService.modifyBatchJDBC(conn,updateFqdnIcpBuiltIns);
				break;
			case FQDN_WHO_IS_BUILT_IN:
				List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnWhoIsBuiltIn.class);
				fqdnWhoIsBuiltIns.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCreateTime(new Date());
					}
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<FqdnWhoIsBuiltIn>> fqdnWhoIsBuiltInMap = fqdnWhoIsBuiltIns.stream().collect(Collectors.groupingBy(fqdnWhoIsBuiltIn-> ObjectUtil.isNotEmpty(fqdnWhoIsBuiltIn.getId())));
				List<FqdnWhoIsBuiltIn> updateFqdnWhoIsBuiltIns = fqdnWhoIsBuiltInMap.get(true);
				List<FqdnWhoIsBuiltIn> insertFqdnWhoIsBuiltIns = fqdnWhoIsBuiltInMap.get(false);
				fqdnWhoIsBuiltInService.saveBatchJDBC(conn,insertFqdnWhoIsBuiltIns);
				fqdnWhoIsBuiltInService.modifyBatchJDBC(conn,updateFqdnWhoIsBuiltIns);
				break;
			case FQDN_CATEGORY_BUILT_IN:
				List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnCategoryBuiltIn.class);
				fqdnCategoryBuiltIns.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCreateTime(new Date());
					}
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<FqdnCategoryBuiltIn>> fqdnCategoryBuiltInMap = fqdnCategoryBuiltIns.stream().collect(Collectors.groupingBy(fqdnCategoryBuiltIn-> ObjectUtil.isNotEmpty(fqdnCategoryBuiltIn.getId())));
				List<FqdnCategoryBuiltIn> updateFqdnCategoryBuiltIns = fqdnCategoryBuiltInMap.get(true);
				List<FqdnCategoryBuiltIn> insertFqdnCategoryBuiltIns = fqdnCategoryBuiltInMap.get(false);
				fqdnCategoryBuiltInService.saveBatchJDBC(conn,insertFqdnCategoryBuiltIns);
				fqdnCategoryBuiltInService.modifyBatchJDBC(conn,updateFqdnCategoryBuiltIns);
				break;
			case IP_ASN_BUILT_IN:
				List<IpAsnBuiltIn> ipAsnBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpAsnBuiltIn.class);
				ipAsnBuiltIns.stream().forEach(data->{
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IpAsnBuiltIn>> ipAsnBuiltInMap = ipAsnBuiltIns.stream().collect(Collectors.groupingBy(ipAsnBuiltIn-> ObjectUtil.isNotEmpty(ipAsnBuiltIn.getId())));
				List<IpAsnBuiltIn> updateIpAsnBuiltIns = ipAsnBuiltInMap.get(true);
				List<IpAsnBuiltIn> insertIpAsnBuiltIns = ipAsnBuiltInMap.get(false);
				ipAsnBuiltInService.saveBatchJDBC(conn,insertIpAsnBuiltIns);
				ipAsnBuiltInService.modifyBatchJDBC(conn,updateIpAsnBuiltIns);
				break;
			case IP_LOCATION_BUILT_IN:
				List<IpLocationBuiltIn> ipLocationBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpLocationBuiltIn.class);
				ipLocationBuiltIns.stream().forEach(data->{
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IpLocationBuiltIn>> ipLocationBuiltInMap = ipLocationBuiltIns.stream().collect(Collectors.groupingBy(ipLocationBuiltIn-> ObjectUtil.isNotEmpty(ipLocationBuiltIn.getId())));
				List<IpLocationBuiltIn> updateIpLocationBuiltIns = ipLocationBuiltInMap.get(true);
				List<IpLocationBuiltIn> insertIpLocationBuiltIns = ipLocationBuiltInMap.get(false);
				ipLocationBuiltInService.saveBatchJDBC(conn,insertIpLocationBuiltIns);
				ipLocationBuiltInService.modifyBatchJDBC(conn,updateIpLocationBuiltIns);
				break;
			case PSIPHON3_IP:
				List<Psiphon3Ip> insertPsiphon3Ips = Lists.newArrayList();
				List<Psiphon3Ip> updatePsiphon3Ips = Lists.newArrayList();
				List<Psiphon3Ip> psiphon3Ips = JSONUtil.toList(JSONUtil.toJsonStr(importDataList), Psiphon3Ip.class);
				psiphon3Ips.stream().forEach(data -> {
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
					if (ObjectUtil.isEmpty(data.getId())) {
						data.setCTime(new Date());
						insertPsiphon3Ips.add(data);
					} else {
						updatePsiphon3Ips.add(data);
					}
				});
				psiphon3IpService.saveBatchJDBC(conn,insertPsiphon3Ips);
				psiphon3IpService.modifyBatchJDBC(conn,updatePsiphon3Ips);
				break;
			case IDC_RENTER_BUILT_IN:
				List<IdcRenterBuiltIn> idcRenterBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IdcRenterBuiltIn.class);
				idcRenterBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IdcRenterBuiltIn>> idcRenterBuiltInMap = idcRenterBuiltIns.stream().collect(Collectors.groupingBy(idcRenterBuiltIn-> ObjectUtil.isNotEmpty(idcRenterBuiltIn.getId())));
				List<IdcRenterBuiltIn> updateIdcRenterBuiltIns = idcRenterBuiltInMap.get(true);
				List<IdcRenterBuiltIn> insertIdcRenterBuiltIns = idcRenterBuiltInMap.get(false);
				idcRenterBuiltInService.saveBatchJDBC(conn,insertIdcRenterBuiltIns);
				idcRenterBuiltInService.modifyBatchJDBC(conn,updateIdcRenterBuiltIns);
				break;
			case INTERNAL_IP_BUILT_IN:
				List<InternalIpBuiltIn> internalIpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),InternalIpBuiltIn.class);
				internalIpBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<InternalIpBuiltIn>> internalIpBuiltInMap = internalIpBuiltIns.stream().collect(Collectors.groupingBy(internalIpBuiltIn-> ObjectUtil.isNotEmpty(internalIpBuiltIn.getId())));
				List<InternalIpBuiltIn> updateInternalIpBuiltIns = internalIpBuiltInMap.get(true);
				List<InternalIpBuiltIn> insertInternalIpBuiltIns = internalIpBuiltInMap.get(false);
				internalIpBuiltInService.saveBatchJDBC(conn,insertInternalIpBuiltIns);
				internalIpBuiltInService.modifyBatchJDBC(conn,updateInternalIpBuiltIns);
				break;
			case LINK_DIRECTION_BUILT_IN:
				List<LinkDirectionBuiltIn> linkDirectionBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),LinkDirectionBuiltIn.class);
				linkDirectionBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<LinkDirectionBuiltIn>> linkDirectionBuiltInMap = linkDirectionBuiltIns.stream().collect(Collectors.groupingBy(linkDirectionBuiltIn-> ObjectUtil.isNotEmpty(linkDirectionBuiltIn.getId())));
				List<LinkDirectionBuiltIn> updateLinkDirectionBuiltInIns = linkDirectionBuiltInMap.get(true);
				List<LinkDirectionBuiltIn> insertLinkDirectionBuiltInIns = linkDirectionBuiltInMap.get(false);
				linkDirectionBuiltInService.saveBatchJDBC(conn,insertLinkDirectionBuiltInIns);
				linkDirectionBuiltInService.modifyBatchJDBC(conn,updateLinkDirectionBuiltInIns);
				break;
			case DNS_SERVER_INFO_BUILT_IN:
				List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),DnsServerInfoBuiltIn.class);
				dnsServerInfoBuiltIns.stream().forEach(data->{
					if(ObjectUtil.isEmpty(data.getId())) {
						data.setCreateTime(new Date());
					}
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<DnsServerInfoBuiltIn>> dnsServerInfoBuiltInMap = dnsServerInfoBuiltIns.stream().collect(Collectors.groupingBy(dnsServerInfoBuiltIn-> ObjectUtil.isNotEmpty(dnsServerInfoBuiltIn.getId())));
				List<DnsServerInfoBuiltIn> updateDnsServerInfoBuiltIns = dnsServerInfoBuiltInMap.get(true);
				List<DnsServerInfoBuiltIn> insertDnsServerInfoBuiltIns = dnsServerInfoBuiltInMap.get(false);
				dnsServerInfoBuiltInService.saveBatchJDBC(conn,insertDnsServerInfoBuiltIns);
				dnsServerInfoBuiltInService.modifyBatchJDBC(conn, updateDnsServerInfoBuiltIns);
				break;
			default:{
				throw new CNException(RCode.KNOWLEDGE_SOURCE_ERROR);
			}
		}
	}
	
	/**
	 * ip相关处理 校验ip格式 返回值包含 计算起始ip 末尾ip
	 * @param ipFormat
	 * @param ip1
	 * @param ip2
	 * @return
	 */
	private Map<String,String> ipHandler(String ipFormat,String ip1,String ip2){
		Map<String,String> result = Tool.MapUtil.newHashMap();
		boolean validIpFormat = KnowledgeCheckUtil.validIpFormat(ip1);
    	if(!validIpFormat) {
    		throw new CNException(RCode.IMPORT_ERROR);
    	}
    	if(StrUtil.equals(ipFormat, Constant.IpFormatType.SINGLE.getValue())) {
    		result.put("ip2",ip1);
    		result.put("start_ip_binary",ip1);
    		result.put("end_ip_binary",ip1);
		}else if(StrUtil.equals(ipFormat, Constant.IpFormatType.RANGE.getValue())){
			validIpFormat = KnowledgeCheckUtil.validIpFormat(ip1+"-"+ip2);
        	if(!validIpFormat) {
        		throw new CNException(RCode.IMPORT_ERROR);
        	}
        	result.put("ip2",ip2);
        	result.put("start_ip_binary",ip1);
        	result.put("end_ip_binary",ip2);
		}else {
			validIpFormat = KnowledgeCheckUtil.validIpFormat(ip1+"/"+ip2);
        	if(!validIpFormat) {
        		throw new CNException(RCode.IMPORT_ERROR);
        	}
        	result.put("ip2",ip2);
			boolean iPv4Address = KnowledgeCheckUtil.isIPv4Address(ip1);
			Map<String, String> ipsStartEnd = null;
			String startIp = "";
			String endIp = "";
			if(iPv4Address) {
				ipsStartEnd = IPUtils.getIpsV4ByCidr(ip1+"/"+ip2);
				startIp = ipsStartEnd.get("startIpStr");
				endIp = ipsStartEnd.get("endIpStr");
			}else {
				ipsStartEnd = IPUtils.getIpsV6ByCidr(ip1+"/"+ip2);
				startIp = ipsStartEnd.get("startIpStr");
				endIp = ipsStartEnd.get("endIpStr");
			}
			result.put("start_ip_binary",startIp);
			result.put("end_ip_binary",endIp);
		}
		return result;
	}

	@Override
	public void intelligenceLearningStatus(String type, Integer knowledgeId) {
		String value = sysConfigService.getValue("schedule_task");
		Map<String, Object> scheduleTask = JSONObject.parseObject(value);
		Map<String, Object> config = (Map<String, Object>)scheduleTask.get("config");
		String url = (String)config.get("url");
		String path = url+"/xxl-job-admin/jobinfo/"+type;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", getCookie(config));
		List<Map<String, Object>> jobIdMapping = (List<Map<String, Object>>)scheduleTask.get("jobIdMapping");
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("id",getJobId(jobIdMapping,knowledgeId));
		ResponseEntity<HashMap> response = restTemplate.postForEntity(path,new HttpEntity<MultiValueMap>(param, requestHeaders),HashMap.class);
		Map<String, Object> body = response.getBody();
		int code = response.getStatusCodeValue();
		if((Integer)body.get("code") != 200){
			if(ObjectUtil.isNotEmpty(body.get("msg"))) {
				throw new CNException((String) body.get("msg"), code);
			}
			throw new CNException(RCode.ERROR);
		}
	}

	private Integer getJobId(List<Map<String, Object>> jobIdMapping, Integer knowledgeId){
		Integer jobId = null;
		if(ObjectUtil.isNotEmpty(jobIdMapping)){
			for(Map<String, Object> map : jobIdMapping){
				if(ObjectUtil.isNotEmpty(map.get("knowledgeId")) && (Integer)map.get("knowledgeId") == knowledgeId){
					jobId = (Integer)map.get("jobId");
				}
			}
		}
		return jobId;
	}

	private String getCookie(Map<String, Object> config){
		String url = (String)config.get("url");
		String userName = (String)config.get("userName");
		String pin = (String)config.get("pin");
		pin = Tool.AesUtil.decrypt(pin, Constant.AES_SECRET_KEY);
		String loginPath = url+"/xxl-job-admin/login";
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("userName",userName);
		param.add("pin",pin);
		ResponseEntity<HashMap> response = restTemplate.postForEntity(loginPath,new HttpEntity<MultiValueMap>(param),HashMap.class);
		Map<String, Object> body = response.getBody();
		if((Integer) body.get("code") != 200){
			throw new CNException(RCode.ERROR);
		}
		HttpHeaders headers = response.getHeaders();
		return headers.get("Set-Cookie").get(0);
	}

	@Override
	public Map<String,Object> timedistribution(Integer knowledgeBaseId, String type, Map<String, Object> params) {
		if(ObjectUtil.isEmpty(params.get("startTime")) || ObjectUtil.isEmpty(params.get("endTime"))){
			throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_NOTNULL);
		}
		Long startTime = Long.valueOf((String)params.get("startTime"));
		Long endTime = Long.valueOf((String)params.get("endTime"));
		// 计算粒度
		Integer step = this.handlerStep(startTime, endTime);
		params.put("startTime",cn.hutool.core.date.DateUtil.date(startTime*1000));
		params.put("endTime",cn.hutool.core.date.DateUtil.date(endTime*1000));
		params.put("step", step);
		params.put("knowledgeBaseId",knowledgeBaseId);
		params.put("type", type);
		List<Map<String, Object>> datas = this.baseMapper.timedistribution(params);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("resultType", "table");
		result.put("result", datas);
		
		return result;
	}
	
	private Integer handlerStep(Long startTime,Long endTime) {
		Integer step = 1;
		Long diffTime = endTime-startTime;
		if(diffTime <= 86400) {
			step = 1;
		}else if(diffTime > 86400 && diffTime <= 2 * 86400) {
			step = 2;
		}else if(diffTime > 2 * 86400 && diffTime <= 7 * 86400) {
			step = 6;
		}else if(diffTime > 7 * 86400 && diffTime <= 15 * 86400) {
			step = 12;
		}else if(diffTime > 15 * 86400 && diffTime <= 30 * 86400) {
			step = 24;
		}
		return step;
	}
}
