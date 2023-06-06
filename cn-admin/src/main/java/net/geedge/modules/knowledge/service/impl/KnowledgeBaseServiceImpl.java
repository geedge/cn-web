package net.geedge.modules.knowledge.service.impl;


import static net.geedge.common.utils.Constant.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
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
import cn.hutool.core.map.MapUtil;
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
import net.geedge.common.utils.ImportValidateUtil;
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
			CsvData datas = CsvUtil.getReader().read(ResourceUtil.getReader(tempFile.getPath(), CharsetUtil.CHARSET_GBK));
			
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
			syncVersion(knowledgeId);
		}
		return R.ok();
	}

	private void validateEnableParams(Map<String,Object> params) {
		if (ObjectUtil.isEmpty(params) || ObjectUtil.isEmpty(params.get("list"))) {
			throw new CNException(RCode.KNOWLEDGE_BASE_ENABLE_PARAMS_INVALID);
		}
	}

	private void syncVersion(Integer knowledgeId) {
		KnowledgeBaseSyncVersion knowledgeBaseSyncVersion = knowledgeBaseSyncVersionService.getBaseMapper().selectOne(new LambdaQueryWrapper<KnowledgeBaseSyncVersion>().eq(KnowledgeBaseSyncVersion::getKnowledgeId, knowledgeId));
		if(ObjectUtil.isEmpty(knowledgeBaseSyncVersion)) {
			log.debug("local data not exist : {}",knowledgeId);
			throw new CNException(RCode.KNOWLEDGE_BASE_SYNC_VERSION_NOT_EXIST);
		}
		knowledgeBaseSyncVersion.setVersion(knowledgeBaseSyncVersion.getVersion() + 1);
		knowledgeBaseSyncVersion.setOpTime(new Date());
		knowledgeBaseSyncVersionService.getBaseMapper().updateById(knowledgeBaseSyncVersion);
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
		}
		if (ObjectUtil.isNotEmpty(params.get("source"))) {
			params.put("source", Arrays.asList(String.valueOf(params.get("source")).split(StringPool.COMMA)));
		}
		if (ObjectUtil.isNotEmpty(params.get("status"))) {
			params.put("status", Arrays.asList(String.valueOf(params.get("status")).split(StringPool.COMMA)));
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
		validateKnowledgeBaseItem(pageUtils);
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

	private void validateKnowledgeBaseItem(PageUtils pageUtils) {
		if(ObjectUtil.isEmpty(pageUtils) || ObjectUtil.isEmpty(pageUtils.getList())) {
			throw new CNException(RCode.KNOWLEDGE_BASE_ITEM_NOT_EXIST);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer addOrModifyKnowledgeBase(Map<String, Object> params) {
		String source = (String) params.get("source");
		Integer knowledgeId = (Integer) params.get("knowledgeId");
		Map<String, Object> result = validateParam(params);
		
		KnowledgeBase knowledgeBase = (KnowledgeBase) result.get("knowledgeBase");
		this.saveOrUpdate(knowledgeBase);
		List<Integer> deleteItemIds = (List<Integer>) params.get("deleteItemIds");
		if(StrUtil.equals(Constant.IP_TAG_USER_DEFINED, source)) {
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
		}else if(StrUtil.equals(Constant.DOMAIN_TAG_USER_DEFINED, source)){
			List<DomainTagUserDefined> domains = (List<DomainTagUserDefined>) result.get(source);
			if(ObjectUtil.isNotEmpty(domains)) {
				domains.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
				domainTagUserDefinedService.saveOrUpdateBatch(domains);
			}
			if(ObjectUtil.isNotEmpty(deleteItemIds)) {
				domainTagUserDefinedService.removeByIds(deleteItemIds);
			}
		}else if(StrUtil.equals(Constant.APP_TAG_USER_DEFINED, source)){
			List<AppTagUserDefined> apps = (List<AppTagUserDefined>) result.get(source);
			if(ObjectUtil.isNotEmpty(apps)) {
				apps.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
				appTagUserDefinedService.saveOrUpdateBatch(apps);
			}
			if(ObjectUtil.isNotEmpty(deleteItemIds)) {
				appTagUserDefinedService.removeByIds(deleteItemIds);
			}
		}else if(StrUtil.equals(Constant.PSIPHON3_IP, source)){
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
			if(StrUtil.equals(Constant.IP_TAG_USER_DEFINED, source)) {
				List<IpTagUserDefined> ipTagUserDefineds = new ArrayList<IpTagUserDefined>();
				for(Map data: datas) {
					Integer id = (Integer) data.get("id");
					String tagValue = (String) data.get("tagValue");
					Integer isValid = (Integer) data.get("isValid");
					String addrFormat = (String) data.get("addrFormat");
					String ip1 = (String) data.get("ip1");
					String ip2 = "";
					boolean ipFormat = true;
					if(!StrUtil.equals(addrFormat, Constant.IpFormatType.SINGLE.getValue())) {
						ip2 = (String) data.get("ip2");
					}
					// 校验ip格式
					if(StrUtil.equals(addrFormat, Constant.IpFormatType.SINGLE.getValue())) {
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1);
					}else if(StrUtil.equals(addrFormat, Constant.IpFormatType.RANGE.getValue())){
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1+"-"+ip2);
					}else {
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1+"/"+ip2);
					}
					if(!ipFormat) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					IpTagUserDefined ipTagUserDefined = new IpTagUserDefined();
					if(ObjectUtil.isEmpty(ipTagUserDefined.getCTime())) {
						ipTagUserDefined.setCTime(new Date());
					}
					ipTagUserDefined.setIsValid(isValid==null?1:isValid);
					ipTagUserDefined.setAddrFormat(addrFormat);
					ipTagUserDefined.setKnowledgeId(knowledgeId);
					ipTagUserDefined.setOpTime(new Date());
					ipTagUserDefined.setOpUser(ShiroUtils.getUserId().intValue());
					ipTagUserDefined.setId(id==null?null:id.longValue());
					ipTagUserDefined.setIp1(ip1);
					ipTagUserDefined.setTagValue(tagValue);
					if(StrUtil.equals(addrFormat, Constant.IpFormatType.SINGLE.getValue())) {
						ipTagUserDefined.setIp2(ip1);
						ipTagUserDefined.setStartIpBinary(ip1.getBytes());
						ipTagUserDefined.setEndIpBinary(ip1.getBytes());
					}else if(StrUtil.equals(addrFormat, Constant.IpFormatType.RANGE.getValue())){
						ipTagUserDefined.setIp2(ip2);
						ipTagUserDefined.setStartIpBinary(ip1.getBytes());
						ipTagUserDefined.setEndIpBinary(ip2.getBytes());
					}else {
						ipTagUserDefined.setIp2(ip2);
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
						ipTagUserDefined.setStartIpBinary(startIp.getBytes());
						ipTagUserDefined.setEndIpBinary(endIp.getBytes());
					}
					ipTagUserDefineds.add(ipTagUserDefined);
				}
				result.put(source, ipTagUserDefineds);
			}else if(StrUtil.equals(Constant.DOMAIN_TAG_USER_DEFINED, source)){
				List<DomainTagUserDefined> domainTagUserDefineds = new ArrayList<DomainTagUserDefined>();
				for(Map data : datas) {
					Integer id = (Integer) data.get("id");
					String tagName = (String) data.get("domain");
					String tagValue = (String) data.get("tagValue");
					Integer isValid = (Integer) data.get("isValid");
					boolean flag = KnowledgeCheckUtil.validateFqdn(tagName);
					if(!flag) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					DomainTagUserDefined domainTagUserDefined = new DomainTagUserDefined();
					if(ObjectUtil.isEmpty(domainTagUserDefined.getCTime())) {
						domainTagUserDefined.setCTime(new Date());
					}
					domainTagUserDefined.setId(id==null?null:id.longValue());
					domainTagUserDefined.setIsValid(isValid==null?1:isValid);
					domainTagUserDefined.setOpTime(new Date());
					domainTagUserDefined.setKnowledgeId(knowledgeId);
					domainTagUserDefined.setOpUser(ShiroUtils.getUserId().intValue());
					domainTagUserDefined.setDomain(tagName);
					domainTagUserDefined.setTagValue(tagValue);
					domainTagUserDefineds.add(domainTagUserDefined);
				}
				result.put(source, domainTagUserDefineds);
			}else if(StrUtil.equals(Constant.APP_TAG_USER_DEFINED, source)) {
				List<AppTagUserDefined> appTagUserDefineds = new ArrayList<AppTagUserDefined>();
				for(Map data : datas) {
					Integer id = (Integer) data.get("id");
					String tagName = (String) data.get("appName");
					String tagValue = (String) data.get("tagValue");
					Integer isValid = (Integer) data.get("isValid");
					boolean flag = KnowledgeCheckUtil.validateApp(tagName);
					if(!flag) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					AppTagUserDefined appTagUserDefined = new AppTagUserDefined();
					if(ObjectUtil.isEmpty(appTagUserDefined.getCTime())) {
						appTagUserDefined.setCTime(new Date());
					}
					appTagUserDefined.setId(id==null?null:id.longValue());
					appTagUserDefined.setIsValid(isValid==null?1:isValid);
					appTagUserDefined.setOpTime(new Date());
					appTagUserDefined.setKnowledgeId(knowledgeId);
					appTagUserDefined.setOpUser(ShiroUtils.getUserId().intValue());
					appTagUserDefined.setAppName(tagName);
					appTagUserDefined.setTagValue(tagValue);
					appTagUserDefineds.add(appTagUserDefined);
				}
				result.put(source, appTagUserDefineds);
			}else if(StrUtil.equals(Constant.PSIPHON3_IP, source)){
				List<Psiphon3Ip> psiphon3Ips = new ArrayList<Psiphon3Ip>();
				for(Map data : datas) {
					Integer id = (Integer) data.get("id");
					Integer isValid = (Integer) data.get("isValid");
					String addrFormat = (String) data.get("addrFormat");
					Integer confidence = (Integer) data.get("confidence");
					String confidenceLevel = (String) data.get("confidence_level");
					String method = (String) data.get("method");
					Integer type = (Integer) data.get("type");
					String ip1 = (String) data.get("ip1");
					String ip2 = "";
					boolean ipFormat = true;
					if(!StrUtil.equals(addrFormat, Constant.IpFormatType.SINGLE.getValue())) {
						ip2 = (String) data.get("ip2");
					}
					// 校验ip格式
					if(StrUtil.equals(addrFormat, Constant.IpFormatType.SINGLE.getValue())) {
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1);
					}else if(StrUtil.equals(addrFormat, Constant.IpFormatType.RANGE.getValue())){
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1+"-"+ip2);
					}else {
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1+"/"+ip2);
					}
					if(!ipFormat) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					Psiphon3Ip psiphon3Ip = new Psiphon3Ip();
					if(ObjectUtil.isEmpty(psiphon3Ip.getCTime())) {
						psiphon3Ip.setCTime(new Date());
					}
					psiphon3Ip.setIsValid(isValid==null?1:isValid);
					psiphon3Ip.setAddrFormat(addrFormat);
					psiphon3Ip.setKnowledgeId(knowledgeId);
					psiphon3Ip.setOpTime(new Date());
					psiphon3Ip.setOpUser(ShiroUtils.getUserId().intValue());
					psiphon3Ip.setId(id==null?null:id.longValue());
					psiphon3Ip.setIp1(ip1);
					psiphon3Ip.setMethod(method);
					psiphon3Ip.setConfidence(confidence);
					psiphon3Ip.setConfidenceLevel(confidenceLevel);
					psiphon3Ip.setType(type);
					if(StrUtil.equals(addrFormat, Constant.IpFormatType.SINGLE.getValue())) {
						psiphon3Ip.setIp2(ip1);
						psiphon3Ip.setStartIpBinary(ip1);
						psiphon3Ip.setEndIpBinary(ip1);
					}else if(StrUtil.equals(addrFormat, Constant.IpFormatType.RANGE.getValue())){
						psiphon3Ip.setIp2(ip2);
						psiphon3Ip.setStartIpBinary(ip1);
						psiphon3Ip.setEndIpBinary(ip2);
					}else {
						psiphon3Ip.setIp2(ip2);
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
						psiphon3Ip.setStartIpBinary(startIp);
						psiphon3Ip.setEndIpBinary(endIp);
					}
					psiphon3Ips.add(psiphon3Ip);
				}
				result.put(source, psiphon3Ips);
			}
		}
		KnowledgeBase knowledgeBase = new KnowledgeBase();
		knowledgeBase.setCategory(category);
		if(ObjectUtil.isEmpty(knowledgeId)) {
			knowledgeBase.setCTime(new Date());
		}
		knowledgeBase.setDescription(description);
		knowledgeBase.setIsBuiltIn(0);
		knowledgeBase.setIsPublished(1);
		knowledgeBase.setKnowledgeId(knowledgeId);
		knowledgeBase.setName(name);
		knowledgeBase.setOpTime(new Date());
		knowledgeBase.setOpUser(ShiroUtils.getUserId());
		knowledgeBase.setStatus(status==null ? 1 : status);
		knowledgeBase.setSource(source);
		result.put("knowledgeBase", knowledgeBase);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer importBatch(MultipartFile file, Integer knowledgeId, String action) {
		Map params = new HashMap();
		KnowledgeBase knowledgeBase = this.baseMapper.selectById(knowledgeId);
		if(ObjectUtil.isEmpty(knowledgeBase)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_ITEM_NOT_EXIST);
		}
		params.put("knowledgeBase", knowledgeBase);
		// 根据source获取对应的表头信息
		String knowledgeImportConfigJson = sysConfigService.getValue("knowledge_import_config_json");
		HashMap knowledgeConfigs = JSONObject.parseObject(knowledgeImportConfigJson, HashMap.class);
		Map<String,Object> sourceConfig = (Map<String, Object>) knowledgeConfigs.get(knowledgeBase.getSource());
		params.put("sourceConfig", sourceConfig);
		params.put("source", knowledgeBase.getSource());
		params.put("action", action);
		String headerStr = (String) sourceConfig.get("headers");
		List<String> sysHeaders = StrUtil.split(headerStr, StrUtil.C_COMMA);
		if(ObjectUtils.isEmpty(sysHeaders)) {
			throw new CNException(RCode.EXCELFILE_HEADER_TEMPLATE_ERROR);
		}
		List<Map<Integer, String>> importDataList = this.getImportDataFromMultipartFile(file, sysHeaders,params);
		this.saveDatasFromImportDataList(importDataList,params);
		return knowledgeId;
	}

	@Override
	public List<Map<String, String>> getImportDataFromMultipartFile(
			MultipartFile file, List<String> headerInfos,Map<String,Object> params) {
		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		String source = (String) params.get("source");
		String action = (String) params.get("action");
		Map<String,Object> sourceConfig = (Map<String, Object>) params.get("sourceConfig");
		List<Map<String,Object>> infos = (List<Map<String, Object>>) sourceConfig.get("infos");
		// 将系统配置表头和验证相关整理成map
		Map<String,Map<String,Object>> configs = new HashMap<String,Map<String,Object>>();
		for(Map m : infos) {
			configs.put((String)m.get("name"), m);
		}
		String validateColumn = (String)sourceConfig.get("validateColumn");
        List<Map<String, String>> importDataList = new ArrayList<>();
        List<Integer> deleteIds = new ArrayList<Integer>();
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
                    HashMap<String, String> checkDataMap = Tool.MapUtil.newHashMap();
                    for(String validateCol : validateColumns) {
                    	checkDataMap.put(validateCol, tmpMap.get(validateCol));
                    }
                    List<Map> dbData = basicImportAndExportService.queryList(source, checkDataMap);
                    if(StrUtil.equals(action, "overwrite")) {
                    	dbData.stream().forEach(d->deleteIds.add((Integer)d.get("id")));
                    }
                    if(ObjectUtil.isNotEmpty(tmpMap)) {
                    	importDataList.add(tmpMap);
                    }
                }
                break;
            }
            default: {
                throw new CNException(RCode.EXCELFILE_TYPE_ERROR);
            }
        }
        if(ObjectUtil.isNotEmpty(deleteIds)) {
        	basicImportAndExportService.removeList(source,deleteIds);
        }
        return importDataList;
	}
	
	private void saveDatasFromImportDataList(List importDataList,Map<String,Object> params) {
		KnowledgeBase knowledgeBase = (KnowledgeBase) params.get("knowledgeBase");
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
				ipTagUserDefinedService.saveBatchForBinary(ipTagUserDefineds);
				break;
			case APP_TAG_USER_DEFINED:
				List<AppTagUserDefined> appTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),AppTagUserDefined.class);
				appTagUserDefineds.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				appTagUserDefinedService.saveBatch(appTagUserDefineds);
				break;
			case DOMAIN_TAG_USER_DEFINED:
				List<DomainTagUserDefined> domainTagUserDefineds = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),DomainTagUserDefined.class);
				domainTagUserDefineds.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				domainTagUserDefinedService.saveBatch(domainTagUserDefineds);
				break;
			case IOC_DARKWEB:
				List<IocDarkweb> iocDarkwebs = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IocDarkweb.class);
				iocDarkwebs.stream().forEach(data->{
					data.setCreateTime(new Date().getTime());
					data.setUpdateTime(new Date().getTime());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				iocDarkwebService.saveBatch(iocDarkwebs);
				break;
			case IOC_MALWARE:
				List<IocMalware> iocMalwares = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IocMalware.class);
				iocMalwares.stream().forEach(data->{
					data.setCreateTime(new Date().getTime());
					data.setUpdateTime(new Date().getTime());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				iocMalwareService.saveBatch(iocMalwares);
				break;
			case APP_CATEGORY_BUILT_IN:
				List<AppCategoryBuiltIn> appCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),AppCategoryBuiltIn.class);
				appCategoryBuiltIns.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				appCategoryBuiltInService.saveBatch(appCategoryBuiltIns);
				break;
			case FQDN_ICP_BUILT_IN:
				List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnIcpBuiltIn.class);
				fqdnIcpBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				fqdnIcpBuiltInService.saveBatch(fqdnIcpBuiltIns);
				break;
			case FQDN_WHO_IS_BUILT_IN:
				List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnWhoIsBuiltIn.class);
				fqdnWhoIsBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				fqdnWhoIsBuiltInService.saveBatch(fqdnWhoIsBuiltIns);
				break;
			case FQDN_CATEGORY_BUILT_IN:
				List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),FqdnCategoryBuiltIn.class);
				fqdnCategoryBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				fqdnCategoryBuiltInService.saveBatch(fqdnCategoryBuiltIns);
				break;
			case IP_ASN_BUILT_IN:
				List<IpAsnBuiltIn> ipAsnBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpAsnBuiltIn.class);
				ipAsnBuiltIns.stream().forEach(data->{
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				ipAsnBuiltInService.saveBatchForBinary(ipAsnBuiltIns);
				break;
			case IP_LOCATION_BUILT_IN:
				List<IpLocationBuiltIn> ipLocationBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IpLocationBuiltIn.class);
				ipLocationBuiltIns.stream().forEach(data->{
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				ipLocationBuiltInService.saveBatch(ipLocationBuiltIns);
				break;
			case PSIPHON3_IP:
				List<Psiphon3Ip> psiphon3Ips = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),Psiphon3Ip.class);
				psiphon3Ips.stream().forEach(data->{
					data.setCTime(new Date());
					data.setOpUser(ShiroUtils.getUserId().intValue());
					data.setOpTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				psiphon3IpService.saveBatchForBinary(psiphon3Ips);
				break;
			case IDC_RENTER_BUILT_IN:
				List<IdcRenterBuiltIn> idcRenterBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IdcRenterBuiltIn.class);
				idcRenterBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				idcRenterBuiltInService.saveBatchForBinary(idcRenterBuiltIns);
				break;
			case INTERNAL_IP_BUILT_IN:
				List<InternalIpBuiltIn> internalIpBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),InternalIpBuiltIn.class);
				internalIpBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				internalIpBuiltInService.saveBatchForBinary(internalIpBuiltIns);
				break;
			case LINK_DIRECTION_BUILT_IN:
				List<LinkDirectionBuiltIn> linkDirectionBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),LinkDirectionBuiltIn.class);
				linkDirectionBuiltIns.stream().forEach(data->{
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				linkDirectionBuiltInService.saveBatch(linkDirectionBuiltIns);
				break;
			case DNS_SERVER_INFO_BUILT_IN:
				List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),DnsServerInfoBuiltIn.class);
				dnsServerInfoBuiltIns.stream().forEach(data->{
					data.setCreateTime(new Date());
					data.setUpdateTime(new Date());
					data.setKnowledgeId(knowledgeBase.getKnowledgeId());
				});
				dnsServerInfoBuiltInService.saveBatch(dnsServerInfoBuiltIns);
				break;
			default:{
				throw new CNException(RCode.KNOWLEDGE_SOURCE_ERROR);
			}
		}
		// 创建或更新knowledgeBase version记录
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
			validIpFormat = KnowledgeCheckUtil.validIpFormat(ip2);
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
