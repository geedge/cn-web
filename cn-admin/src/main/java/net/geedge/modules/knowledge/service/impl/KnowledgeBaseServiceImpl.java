package net.geedge.modules.knowledge.service.impl;


import static net.geedge.common.utils.Constant.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.CSVUtils;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.IPUtils;
import net.geedge.common.utils.JSONUtil;
import net.geedge.common.utils.KnowledgeCheckUtil;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
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
import net.geedge.modules.knowledge.service.KnowledgeBaseService;
import net.geedge.modules.knowledge.service.KnowledgeBaseSyncVersionService;
import net.geedge.modules.knowledge.service.LinkDirectionBuiltInService;
import net.geedge.modules.knowledge.service.Psiphon3IpService;
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

	@Override
	public Object remove(String knowledgeIds) {
		// 内置数据不可删除
		List<String> knowlegeIds = StrUtil.split(knowledgeIds, StrUtil.C_COMMA);
		List<KnowledgeBase> knowledgeBases = this.baseMapper.selectBatchIds(knowlegeIds);
		List<Integer> builtInIds = knowledgeBases.stream().filter(knowledgeBase-> knowledgeBase.getIsBuiltIn()
				.equals(1)).map(KnowledgeBase::getKnowledgeId).collect(Collectors.toList());
		if(ObjectUtils.isNotEmpty(builtInIds)) {
			throw new CNException(RCode.KNOWLEDGE_BUILTIN_ERROR);
		}

		// 删除版本数据信息
		knowledgeBaseSyncVersionService.remove(new QueryWrapper<KnowledgeBaseSyncVersion>().lambda().in(KnowledgeBaseSyncVersion::getKnowledgeId, knowlegeIds));
		// 根据类型进行分组 删除对应表中条目数据
		Map<String, List<Integer>> sourceKnowledgeBaseIds = knowledgeBases.stream().collect(Collectors.groupingBy(KnowledgeBase::getSource,Collectors.mapping(KnowledgeBase::getKnowledgeId, Collectors.toList())));
		for(String source : sourceKnowledgeBaseIds.keySet()) {
			// 根据source 去对应的表删除数据
			this.baseMapper.deleteBySource(source,sourceKnowledgeBaseIds.get(source));
		}
		this.remove(new QueryWrapper<KnowledgeBase>().lambda().in(KnowledgeBase::getKnowledgeId,knowlegeIds));
		return knowlegeIds;
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
			CsvData datas = CsvUtil.getReader().read(ResourceUtil.getReader(tempFile.getPath(), CharsetUtil.CHARSET_UTF_8));
			
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
		        		ip1 = StrUtil.split(s, StrUtil.DASHED)[0];
		        		ip2 = StrUtil.split(s, StrUtil.DASHED)[1];
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
		List<Map<String, Object>> list = knowledgeBaseToMap(knowledgeBases);
		list.stream().forEach(m -> mapDateToTimestamp(m));
		page.setRecords(list);
		return new PageUtils(page);
	}

	private List<Map<String, Object>> knowledgeBaseToMap(List<KnowledgeBase> knowledgeBases) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (KnowledgeBase knowledgeBase: knowledgeBases) {
			Map<String, Object> knowledgeMap = JSONObject.parseObject(JSONObject.toJSONString(knowledgeBase));
			list.add(knowledgeMap);
		}
		return list;
	}

	@Override
	public R enableKnowledgeBase(Map<String,Object> params) {
		validateEnableParams(params);
		List<Map<String,Object>> list = (List)params.get("list");
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
			this.getBaseMapper().updateById(knowledgeBase);
			knowledgeBaseSyncVersionService.syncVersion(knowledgeId);
		}
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
		if (ObjectUtil.isNotEmpty(params.get("orderBy"))) {
			params.put("orderBy", StrUtil.toUnderlineCase((String)params.get("orderBy")));
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
			default:
				break;
		}
		return pageUtils;
	}

	private Map<String, Object> addKnowledgeBaseMessage(PageUtils pageUtils, KnowledgeBase knowledgeBase){
		Map<String, Object> pageMap = JSONUtil.parseObj(JSONUtil.toJsonStr(pageUtils));
		Map<String, Object> knowledge = JSONUtil.parseObj(JSONUtil.toJsonStr(knowledgeBase));
		mapDateToTimestamp(knowledge);
		pageMap.putAll(knowledge);
		return pageMap;
	}

	private void mapDateToTimestamp(Map<String, Object> map) {
		if (ObjectUtil.isNotEmpty(map.get("opTime"))) {
			map.put("opTime", (Long)map.get("opTime")/1000);
		}
		if (ObjectUtil.isNotEmpty(map.get("cTime"))) {
			map.put("cTime", (Long)map.get("cTime")/1000);
		}
	}

	private Map<String, Object> updateItemList(Map<String, Object> pageMap) {
		List<Map<String, Object>> list = (List<Map<String, Object>>)pageMap.get("list");
		list.stream().forEach(m -> mapDateToTimestamp(m));
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
		if(ObjectUtil.isNotEmpty(knowledgeBaseSyncVersion)) {
			Integer version = knowledgeBaseSyncVersion.getVersion();
			knowledgeBaseSyncVersion.setVersion(version+1);
		}else {
			knowledgeBaseSyncVersion = new KnowledgeBaseSyncVersion();
			knowledgeBaseSyncVersion.setVersion(1);
		}
		knowledgeBaseSyncVersion.setKnowledgeId(knowledgeBase.getKnowledgeId());
		knowledgeBaseSyncVersion.setOpTime(new Date());
		knowledgeBaseSyncVersionService.saveOrUpdate(knowledgeBaseSyncVersion);
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
	public Integer importBatch(MultipartFile file, Integer knowledgeId, String action) {
		KnowledgeBase knowledgeBase = this.baseMapper.selectById(knowledgeId);
		if (ObjectUtil.isEmpty(knowledgeBase)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
		}
		List<Integer> deleteDataIds = Lists.newArrayList();
		List<Map<String, String>> saveOrUpdateList = Lists.newArrayList();
		this.validAndBuildDatasets(file, knowledgeBase, action, saveOrUpdateList, deleteDataIds);
		//更新操作时 删除多余重复数据
		if (StrUtil.equals(action, "update") && !deleteDataIds.isEmpty()) {
			basicImportAndExportService.removeList(knowledgeBase.getSource(), deleteDataIds);
		}
		//覆盖操作时 将对应表中数据删除清空
		if (StrUtil.equals(action, "overwrite")) {
			basicImportAndExportService.removeList(knowledgeBase.getSource(), knowledgeBase.getKnowledgeId());
		}
		this.saveOrUpdateBatch(saveOrUpdateList, knowledgeBase);
		knowledgeBaseSyncVersionService.syncVersion(knowledgeBase.getKnowledgeId());
		return knowledgeId;
	}

	@Override
	public void validAndBuildDatasets(MultipartFile file, KnowledgeBase knowledgeBase, String action, List<Map<String, String>> importDataList, List<Integer> deleteDataIds) {
		// 根据source获取对应的表头信息
		String knowledgeImportConfigJson = sysConfigService.getValue("knowledge_import_config_json");
		HashMap knowledgeConfigs = JSONObject.parseObject(knowledgeImportConfigJson, HashMap.class);
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

        switch (fileType) {
            case "csv": {
                CsvData csvData = CSVUtils.getCsvDataFromMultipartFile(file);
                List<CsvRow> rows = csvData.getRows();
                CsvRow headers = rows.get(0);
                // 表头校验
                if (!CSVUtils.validateCSVHeader(headers, headerInfos)) {
                    throw new CNException(RCode.EXCELFILE_HEADER_TEMPLATE_ERROR);
                }
                // 表头以及下标信息整理
                Map<Integer,String> indexHeader = new HashMap<Integer,String>();
                for(int i = 0;i<headers.size();i++) {
                	indexHeader.put(i, headers.get(i));
                }
                for (int i = 1; i < rows.size(); i++) {
                	boolean ipHandler = false;
                    CsvRow csvRow = rows.get(i);
                    Map<String, String> tmpMap = Tool.MapUtil.newHashMap();
                    for (int j = 0; j < headerInfos.size(); j++) {
                        String cellStr = StrUtil.trim(csvRow.get(j));
                        if ((StrUtil.startWith(cellStr, "[") && StrUtil.endWith(cellStr, "]")) ||
                                (StrUtil.startWith(cellStr, "{") && StrUtil.endWith(cellStr, "}"))) { // csv文件中json格式双引号会变成两个
                            cellStr = StrUtil.replace(cellStr, "\"\"", "\"");
                        }
						String valName = indexHeader.get(j);
						Map<String, Object> info = configs.get(valName);
						String checkType = (String) info.get("checkType");
						List<String> validateVal = (List<String>) info.get("validateVal");
						if(StrUtil.equals(valName, "addr_format")) {
							ipHandler = true;
						}
						if(StrUtil.equals(checkType, "in")) {
							if(!validateVal.contains(cellStr)) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",i,valName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}else if(StrUtil.equals(checkType, "range")) {
							if(!(Integer.valueOf(validateVal.get(0))<=Integer.valueOf(cellStr) && Integer.valueOf(validateVal.get(1))>=Integer.valueOf(cellStr))) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",i,valName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}else if(StrUtil.equals(checkType, "ip")) {
							boolean validIpFormat = KnowledgeCheckUtil.validIpFormat(cellStr);
							if(!validIpFormat) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",i,valName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}else if(StrUtil.equals(checkType, "notNull")) {
							if(ObjectUtil.isEmpty(cellStr)) {
								log.error("knowledgeBase import data error , line is {},columnName is {}",i,valName);
								throw new CNException(RCode.IMPORT_ERROR);
							}
						}
                        tmpMap.put(valName, cellStr);
                    }
                    // 进行ip1 ip2相关数据内容处理
                    if(ipHandler) {
                    	String addrFormat = tmpMap.get("addr_format");
                    	String ip1 = tmpMap.get("ip1");
                    	String ip2 = tmpMap.get("ip2");
                    	Map<String, String> ipHandlerResult = ipHandler(addrFormat, ip1, ip2);
                    	tmpMap.putAll(ipHandlerResult);
                    }
                    // 根据配置校验数据是否重复
					List<String> validateColumns = StrUtil.split(validateColumn, StrUtil.C_COMMA);
					if(ObjectUtil.isEmpty(validateColumns)) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_NOTNULL);
					}
					HashMap<String, String> checkDataMap = Tool.MapUtil.newHashMap();
					for (String validateCol : validateColumns) {
						checkDataMap.put(validateCol, tmpMap.get(validateCol));
					}
					// 更新操作 更新最早创建数据、删除其余冗余数据
					if (StrUtil.equals(action, "update")) {
						List<Integer> dbDataIds = basicImportAndExportService.queryDataIds(knowledgeBase.getSource(), checkDataMap, knowledgeBase.getKnowledgeId(), null);
						if (ObjectUtil.isNotEmpty(dbDataIds)) {
							tmpMap.put("id", dbDataIds.get(0).toString());
							if (dbDataIds.size() > 1) {
								deleteDataIds.addAll(dbDataIds.subList(1, dbDataIds.size()));
							}
						}
					}
					if (ObjectUtil.isNotEmpty(tmpMap)) {
						importDataList.add(tmpMap);
					}
				}
				break;
			}
            default: {
                throw new CNException(RCode.EXCELFILE_TYPE_ERROR);
            }
        }
	}

	private void saveOrUpdateBatch(List importDataList, KnowledgeBase knowledgeBase) {
		String source = knowledgeBase.getSource();
		// 根据类型不同 生成对应实体 保存入库
		switch(source) {
			case IP_TAG_USER_DEFINED:
				List<IpTagUserDefined> ipTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpTagUserDefined.class);
				ipTagUserDefineds.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IpTagUserDefined>> ipTagUserDefinedMap = ipTagUserDefineds.stream().collect(Collectors.groupingBy(ipTagUserDefined-> ObjectUtil.isNotEmpty(ipTagUserDefined.getId())));
				List<IpTagUserDefined> updateIps = ipTagUserDefinedMap.get(true);
				List<IpTagUserDefined> insertIps = ipTagUserDefinedMap.get(false);
				ipTagUserDefinedService.saveBatchForBinary(insertIps);
				ipTagUserDefinedService.modifyBatchForBinary(updateIps);
				break;
			case APP_TAG_USER_DEFINED:
				List<AppTagUserDefined> appTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),AppTagUserDefined.class);
				appTagUserDefineds.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				appTagUserDefinedService.saveOrUpdateBatch(appTagUserDefineds);
				break;
			case DOMAIN_TAG_USER_DEFINED:
				List<DomainTagUserDefined> domainTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),DomainTagUserDefined.class);
				domainTagUserDefineds.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				domainTagUserDefinedService.saveOrUpdateBatch(domainTagUserDefineds);
				break;
			case IOC_DARKWEB:
				List<IocDarkweb> iocDarkwebs = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IocDarkweb.class);
				iocDarkwebs.stream().forEach(data->{
					data.setCreateTime(new Date().getTime());
					data.setUpdateTime(new Date().getTime());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				iocDarkwebService.saveOrUpdateBatch(iocDarkwebs);
				break;
			case IOC_MALWARE:
				List<IocMalware> iocMalwares = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IocMalware.class);
				iocMalwares.stream().forEach(data->{
					data.setCreateTime(new Date().getTime());
					data.setUpdateTime(new Date().getTime());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				iocMalwareService.saveOrUpdateBatch(iocMalwares);
				break;
			case APP_CATEGORY_BUILT_IN:
				List<AppCategoryBuiltIn> appCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),AppCategoryBuiltIn.class);
				appCategoryBuiltIns.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				appCategoryBuiltInService.saveOrUpdateBatch(appCategoryBuiltIns);
				break;
			case FQDN_ICP_BUILT_IN:
				List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnIcpBuiltIn.class);
				fqdnIcpBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				fqdnIcpBuiltInService.saveOrUpdateBatch(fqdnIcpBuiltIns);
				break;
			case FQDN_WHO_IS_BUILT_IN:
				List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnWhoIsBuiltIn.class);
				fqdnWhoIsBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				fqdnWhoIsBuiltInService.saveOrUpdateBatch(fqdnWhoIsBuiltIns);
				break;
			case FQDN_CATEGORY_BUILT_IN:
				List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnCategoryBuiltIn.class);
				fqdnCategoryBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				fqdnCategoryBuiltInService.saveOrUpdateBatch(fqdnCategoryBuiltIns);
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
				ipAsnBuiltInService.saveBatchForBinary(insertIpAsnBuiltIns);
				ipAsnBuiltInService.modifyBatchForBinary(updateIpAsnBuiltIns);
				break;
			case IP_LOCATION_BUILT_IN:
				List<IpLocationBuiltIn> ipLocationBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpLocationBuiltIn.class);
				ipLocationBuiltIns.stream().forEach(data->{
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				ipLocationBuiltInService.saveOrUpdateBatch(ipLocationBuiltIns);
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
				psiphon3IpService.saveBatchForBinary(insertPsiphon3Ips);
				psiphon3IpService.modifyBatchForBinary(updatePsiphon3Ips);
				break;
			case IDC_RENTER_BUILT_IN:
				List<IdcRenterBuiltIn> idcRenterBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IdcRenterBuiltIn.class);
				idcRenterBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<IdcRenterBuiltIn>> idcRenterBuiltInMap = idcRenterBuiltIns.stream().collect(Collectors.groupingBy(idcRenterBuiltIn-> ObjectUtil.isNotEmpty(idcRenterBuiltIn.getId())));
				List<IdcRenterBuiltIn> updateIdcRenterBuiltIns = idcRenterBuiltInMap.get(true);
				List<IdcRenterBuiltIn> insertIdcRenterBuiltIns = idcRenterBuiltInMap.get(false);
				idcRenterBuiltInService.saveBatchForBinary(insertIdcRenterBuiltIns);
				idcRenterBuiltInService.modifyBatchForBinary(updateIdcRenterBuiltIns);
				break;
			case INTERNAL_IP_BUILT_IN:
				List<InternalIpBuiltIn> internalIpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),InternalIpBuiltIn.class);
				internalIpBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				Map<Boolean, List<InternalIpBuiltIn>> internalIpBuiltInMap = internalIpBuiltIns.stream().collect(Collectors.groupingBy(internalIpBuiltIn-> ObjectUtil.isNotEmpty(internalIpBuiltIn.getId())));
				List<InternalIpBuiltIn> updateInternalIpBuiltIns = internalIpBuiltInMap.get(true);
				List<InternalIpBuiltIn> insertInternalIpBuiltIns = internalIpBuiltInMap.get(false);
				internalIpBuiltInService.saveBatchForBinary(insertInternalIpBuiltIns);
				internalIpBuiltInService.modifyBatchForBinary(updateInternalIpBuiltIns);
				break;
			case LINK_DIRECTION_BUILT_IN:
				List<LinkDirectionBuiltIn> linkDirectionBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),LinkDirectionBuiltIn.class);
				linkDirectionBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				linkDirectionBuiltInService.saveOrUpdateBatch(linkDirectionBuiltIns);
				break;
			case DNS_SERVER_INFO_BUILT_IN:
				List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),DnsServerInfoBuiltIn.class);
				dnsServerInfoBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				dnsServerInfoBuiltInService.saveOrUpdateBatch(dnsServerInfoBuiltIns);
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
}
