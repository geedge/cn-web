package net.geedge.modules.knowledge.service.impl;


import static net.geedge.modules.knowledge.constant.KnowledgeBaseConstant.APP_TAG_USER_DEFINED;
import static net.geedge.modules.knowledge.constant.KnowledgeBaseConstant.DOMAIN_TAG_USER_DEFINED;
import static net.geedge.modules.knowledge.constant.KnowledgeBaseConstant.IP_TAG_USER_DEFINED;

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

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.IPUtils;
import net.geedge.common.utils.KnowledgeCheckUtil;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.dao.KnowledgeBaseDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.DomainTagUserDefined;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.entity.KnowledgeBaseSyncVersion;
import net.geedge.modules.knowledge.service.AppTagUserDefinedService;
import net.geedge.modules.knowledge.service.DomainTagUserDefinedService;
import net.geedge.modules.knowledge.service.IpTagUserDefinedService;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;
import net.geedge.modules.knowledge.service.KnowledgeBaseSyncVersionService;
import net.geedge.modules.sys.service.SysUserService;
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
	private SysUserService sysUserService;

	@Override
	public Object remove(String ids) {
		// 内置数据不可删除
		List<String> knowlegeIds = StrUtil.split(ids, StrUtil.C_COMMA);
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
			if(StrUtil.contains(source, "ip")) {
				ipTagUserDefinedService.remove(new QueryWrapper<IpTagUserDefined>().lambda().in(IpTagUserDefined::getKnowledgeId, sourceKnowledgeBaseIds.get(source)));
			}else if(StrUtil.contains(source, "app")){
				appTagUserDefinedService.remove(new QueryWrapper<AppTagUserDefined>().lambda().in(AppTagUserDefined::getKnowledgeId, sourceKnowledgeBaseIds.get(source)));
			}else {
				domainTagUserDefinedService.remove(new QueryWrapper<DomainTagUserDefined>().lambda().in(DomainTagUserDefined::getKnowledgeId, sourceKnowledgeBaseIds.get(source)));
			}
		}
		this.remove(new QueryWrapper<KnowledgeBase>().lambda().in(KnowledgeBase::getKnowledgeId,knowlegeIds));
		return knowlegeIds;
	}

	@Override
	public R importData(MultipartFile file,String type) {
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
		    		if(StrUtil.equals(type, "ip")) {
			    		format = KnowledgeCheckUtil.validIpFormat(row);
			    	}else if(StrUtil.equals(type, "domain")){
			    		format = KnowledgeCheckUtil.validateFqdn(row);
			    	}else {
			    		format = KnowledgeCheckUtil.validateApp(row);
			    	}
		    	}
		        Map<String,Object> res = new HashMap<String,Object>();
		        if(StrUtil.equals(type, "ip")) {
		        	String s = data.get(0);
		        	Map<String,Object> ip = new HashMap<String,Object>();
		        	String ipType = "";
		        	String ip1="";
		        	String ip2="";
		        	if(StrUtil.contains(s, StrUtil.C_SLASH)) {
		        		ipType="CIDR";
		        		ip1 = StrUtil.split(s, StrUtil.C_SLASH).get(0);
		        		ip2 = StrUtil.split(s, StrUtil.C_SLASH).get(1);
		        	}else if(StrUtil.contains(s, StrUtil.DASHED)) {
		        		ipType="RANGE";
		        		ip1 = StrUtil.split(s, StrUtil.DASHED)[0];
		        		ip2 = StrUtil.split(s, StrUtil.DASHED)[1];
		        	}else {
		        		ipType="Single";
		        		ip1 = s;
		        	}
		        	ip.put("addrFormat",ipType);
		        	ip.put("ip1", ip1);
		        	ip.put("ip2", ip2);
		        	res.put("tagNames", ip);
		        }else {
		        	res.put("tagName", data.get(0));
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
		page.setRecords(knowledgeBases);
		return new PageUtils(page);
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
		long categoryCount = knowledgeBases.stream().filter(knowledgeBase -> knowledgeBase.getCategory().equals(category)).count();
		Set<String> sources = getNonRepetitiveSource(knowledge);
		Map<String, Object> categoryMap = new HashMap<>();
		categoryMap.put("category", category);
		categoryMap.put("count", categoryCount);
		List<Map> sourceList = new ArrayList<>();
		for (String source : sources) {
			Map<String, Object> sourceMap = statisticsSource(knowledge, source);
			sourceList.add(sourceMap);
		}
		categoryMap.put("sourceList",sourceList);
		return categoryMap;
	}

	private Map<String,Object> statisticsSource(List<KnowledgeBase> knowledge, String source) {
		long sourceCount = knowledge.stream().filter(knowledgeBase -> knowledgeBase.getSource().equals(source)).count();
		Map<String, Object> sourceMap = new HashMap<>();
		sourceMap.put("source", source);
		sourceMap.put("count", sourceCount);
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
	public PageUtils queryKnowledgeBaseItem(Integer knowledgeId, Map<String, Object> params) {
		params.put("knowledgeId",knowledgeId);
		KnowledgeBase knowledgeBase = this.getBaseMapper().queryInfo(knowledgeId);
		String source = knowledgeBase.getSource();
		PageUtils pageUtils = createUserDefinedItemPages(source, params);
		if(ObjectUtil.isEmpty(pageUtils)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_ITEM_NOT_EXIST);
		}
		return pageUtils;
	}

	private PageUtils createUserDefinedItemPages(String source, Map<String, Object> params) {
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
			default:
				break;
		}
		return pageUtils;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer addOrModifyKnowledgeBase(Map<String, Object> params) {
		String type = (String) params.get("type");
		Integer knowledgeId = (Integer) params.get("knowledgeId");
		Map<String, Object> result = validateParam(params);
		
		KnowledgeBase knowledgeBase = (KnowledgeBase) result.get("knowledgeBase");
		this.saveOrUpdate(knowledgeBase);
		if(StrUtil.equals("ip", type)) {
			List<IpTagUserDefined> ips = (List<IpTagUserDefined>) result.get("ip");
			if(ObjectUtil.isEmpty(knowledgeId)) {
				ips.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
			}
			ipTagUserDefinedService.saveOrUpdateBatch(ips);
		}else if(StrUtil.equals("domain", type)){
			List<DomainTagUserDefined> domains = (List<DomainTagUserDefined>) result.get("domain");
			if(ObjectUtil.isEmpty(knowledgeId)) {
				domains.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
			}
			domainTagUserDefinedService.saveOrUpdateBatch(domains);
		}else {
			List<AppTagUserDefined> apps = (List<AppTagUserDefined>) result.get("app");
			if(ObjectUtil.isEmpty(knowledgeId)) {
				apps.stream().forEach(ip-> ip.setKnowledgeId(knowledgeBase.getKnowledgeId()));
			}
			appTagUserDefinedService.saveOrUpdateBatch(apps);
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
		
		String handleType = "addItemList";
		Integer knowledgeId = (Integer) params.get("knowledgeId");
		if(ObjectUtil.isNotEmpty(knowledgeId)) {
			handleType = "updateItemList";
		}
		String name = (String) params.get("name");
		String type = (String) params.get("type");
		String category = (String) params.get("category");
		String description = (String) params.get("description");
		List<KnowledgeBase> queryByName = this.baseMapper.selectList(new QueryWrapper<KnowledgeBase>().lambda()
				.eq(KnowledgeBase::getName, name)
				.ne(ObjectUtil.isNotEmpty(knowledgeId),KnowledgeBase::getKnowledgeId,knowledgeId));
		if(ObjectUtil.isNotEmpty(queryByName)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NAME_DUPLICATE);
		}
		String source = "";
		List<Map<String,Object>> datas = (List<Map<String, Object>>) params.get(handleType);
		if(ObjectUtil.isNotEmpty(datas)) {
			if(StrUtil.equals("ip", type)) {
				source = "cn_ip_tag_user_defined";
				List<IpTagUserDefined> ipTagUserDefineds = new ArrayList<IpTagUserDefined>();
				for(Map data: datas) {
					Integer id = (Integer) data.get("id");
					Map<String,Object> tagNames = (Map<String, Object>) data.get("tagNames");
					String tagValue = (String) data.get("tagValue");
					Integer isValid = (Integer) data.get("isValid");
					String addrFormat = (String) tagNames.get("addrFormat");
					String ip1 = (String) tagNames.get("ip1");
					String ip2 = "";
					boolean ipFormat = true;
					if(!StrUtil.equals(addrFormat, "Single")) {
						ip2 = (String) tagNames.get("ip2");
					}
					// 校验ip格式
					if(StrUtil.equals(addrFormat, "Single")) {
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1);
					}else if(StrUtil.equals(addrFormat, "Range")){
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1+"-"+ip2);
					}else {
						ipFormat = KnowledgeCheckUtil.validIpFormat(ip1+"/"+ip2);
					}
					if(!ipFormat) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					IpTagUserDefined ipTagUserDefined = new IpTagUserDefined();
					if(ObjectUtil.isEmpty(knowledgeId)) {
						ipTagUserDefined.setCTime(new Date());
					}
					ipTagUserDefined.setIsValid(isValid);
					ipTagUserDefined.setAddrFormat(addrFormat);
					ipTagUserDefined.setKnowledgeId(knowledgeId);
					ipTagUserDefined.setOpTime(new Date());
					ipTagUserDefined.setOpUser(ShiroUtils.getUserId().intValue());
					ipTagUserDefined.setId(id==null?null:id.longValue());
					ipTagUserDefined.setTagNames(tagNames);
					ipTagUserDefined.setIpFirst(ip1);
					ipTagUserDefined.setTagValue(tagValue);
					if(StrUtil.equals(addrFormat, "Single")) {
						/*ipTagUserDefined.setIpSecond(ip1);
						String ip1Binary = getIpBinary(ip1);
						ipTagUserDefined.setStartIpBinary(Tool.HexUtil.decodeHex(ip1Binary));
						ipTagUserDefined.setEndIpBinary(Tool.HexUtil.decodeHex(ip1Binary));*/
					}else if(StrUtil.equals(addrFormat, "Range")){
						/*ipTagUserDefined.setIpSecond(ip2);
						String ip1Binary = getIpBinary(ip1);
						ipTagUserDefined.setStartIpBinary(Tool.HexUtil.decodeHex(ip1Binary));
						String ip2Binary = getIpBinary(ip2);
						ipTagUserDefined.setEndIpBinary(Tool.HexUtil.decodeHex(ip2Binary));*/
					}else {
						ipTagUserDefined.setIpSecond(ip2);
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
						/*String ip1Binary = getIpBinary(startIp);
						ipTagUserDefined.setStartIpBinary(Tool.HexUtil.decodeHex(ip1Binary));
						String ip2Binary = getIpBinary(endIp);
						ipTagUserDefined.setEndIpBinary(Tool.HexUtil.decodeHex(ip2Binary));*/
					}
					ipTagUserDefineds.add(ipTagUserDefined);
				}
				result.put("ip", ipTagUserDefineds);
			}else if(StrUtil.equals("domain", type)){
				source = "cn_domain_tag_user_defined";
				List<DomainTagUserDefined> domainTagUserDefineds = new ArrayList<DomainTagUserDefined>();
				for(Map data : datas) {
					Integer id = (Integer) data.get("id");
					String tagName = (String) data.get("tagName");
					String tagValue = (String) data.get("tagValue");
					Integer isValid = (Integer) data.get("isValid");
					boolean flag = KnowledgeCheckUtil.validateFqdn(tagName);
					if(!flag) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					DomainTagUserDefined domainTagUserDefined = new DomainTagUserDefined();
					if(ObjectUtil.isEmpty(knowledgeId)) {
						domainTagUserDefined.setCTime(new Date());
					}
					domainTagUserDefined.setId(id==null?null:id.longValue());
					domainTagUserDefined.setIsValid(isValid);
					domainTagUserDefined.setOpTime(new Date());
					domainTagUserDefined.setKnowledgeId(knowledgeId);
					domainTagUserDefined.setOpUser(ShiroUtils.getUserId().intValue());
					domainTagUserDefined.setTagName(tagName);
					domainTagUserDefined.setTagValue(tagValue);
					domainTagUserDefineds.add(domainTagUserDefined);
				}
				result.put("domain", domainTagUserDefineds);
			}else if(StrUtil.equals("app", type)) {
				source = "cn_app_tag_user_defined";
				List<AppTagUserDefined> appTagUserDefineds = new ArrayList<AppTagUserDefined>();
				for(Map data : datas) {
					Integer id = (Integer) data.get("id");
					String tagName = (String) data.get("tagName");
					String tagValue = (String) data.get("tagValue");
					Integer isValid = (Integer) data.get("isValid");
					boolean flag = KnowledgeCheckUtil.validateApp(tagName);
					if(!flag) {
						throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_FORMAT_ERROR);
					}
					AppTagUserDefined appTagUserDefined = new AppTagUserDefined();
					if(ObjectUtil.isEmpty(knowledgeId)) {
						appTagUserDefined.setCTime(new Date());
					}
					appTagUserDefined.setId(id==null?null:id.longValue());
					appTagUserDefined.setIsValid(isValid);
					appTagUserDefined.setOpTime(new Date());
					appTagUserDefined.setKnowledgeId(knowledgeId);
					appTagUserDefined.setOpUser(ShiroUtils.getUserId().intValue());
					appTagUserDefined.setTagName(tagName);
					appTagUserDefined.setTagValue(tagValue);
					appTagUserDefineds.add(appTagUserDefined);
				}
				result.put("app", appTagUserDefineds);
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
		knowledgeBase.setStatus(1);
		knowledgeBase.setSource(source);
		result.put("knowledgeBase", knowledgeBase);
		return result;
	}
	
	/*private String getIpBinary(String ip){
		IPAddress address;
		try {
			IPAddressString ipAddress = new IPAddressString(ip);
			address = ipAddress.toAddress();
		} catch (Exception e) {
			throw new CNException("Parsing ip address error");
		}
		return address.toBinaryString();
	}*/
}
