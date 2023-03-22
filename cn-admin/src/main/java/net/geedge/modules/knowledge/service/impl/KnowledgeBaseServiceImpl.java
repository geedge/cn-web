package net.geedge.modules.knowledge.service.impl;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
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
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.JSONUtil;
import net.geedge.common.utils.KnowledgeCheckUtil;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool.ListUtil;
import net.geedge.modules.knowledge.dao.KnowledgeBaseDao;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;
@Service("knowledgeBaseService")
public class KnowledgeBaseServiceImpl extends ServiceImpl<KnowledgeBaseDao, KnowledgeBase> implements KnowledgeBaseService {

	private Log log = Log.get();
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private KnowledgeBaseService knowledgeBaseService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysUserService sysUserService;
	@Value("${knowledgeRemoveTimeout:60000}")
	private Integer knowledgeRemoveTimeout;
	
	@Override
	public PageUtils base(Map<String, Object> params) {
		String q = (String) params.get("q");
		String type = (String) params.get("tagType");
		if(StrUtil.isNotBlank(type)) {
			params.put("tagType", type + "_user_defined");
		}
		IPage<KnowledgeBase> page=this.page(new Query(KnowledgeBase.class).getPage(params));
		List<KnowledgeBase> knowledgeBases=this.baseMapper.queryPage(page,params);
		knowledgeBases.stream().forEach(knowledgeBase -> knowledgeBase.setTagType(StrUtil.replace(knowledgeBase.getTagType(), "_user_defined", "")));
		String galaxyUrl = sysConfigService.getValue("galaxy_url");
		String url = galaxyUrl+"knowledge_base/v1";
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET,null,Object.class,new HashMap());
		List<Map<String,Object>> handlerData = new ArrayList<Map<String,Object>>();
		if(response.getStatusCodeValue()==200) {
			HashMap body = (HashMap) response.getBody();
			handlerData = (List<Map<String,Object>>) body.get("data");
			log.debug("Get knowledgebases info : {}",JSONUtil.toJsonStr(handlerData));
			handlerData = handlerData.stream().filter(o -> StrUtil.equals((String)o.get("version"),"latest")).collect(Collectors.toList());
			Map<Object, Map<String, Object>> secondDatas = handlerData.stream().collect(Collectors.toMap(data->data.get("id"), Function.identity()));
			// 组装本地数据库数据与galaxy接口数据信息 默认获取文件的最新版本latest
			for(KnowledgeBase r : knowledgeBases) {
				Map<String, Object> map = secondDatas.get(r.getKnowledgeId());
				if(ObjectUtil.isNotEmpty(map)) {
					r.setFormat((String)map.get("format"));
					r.setVersion((String)map.get("version"));
					r.setPath((String)map.get("path"));
				}else {
					log.debug("local data error knowledge id : {}",r.getKnowledgeId());
				}
			}
		}else{
			log.debug("Get knowledgebases error code : {}",response.getStatusCodeValue());
			log.debug("Get knowledgebases error msg : {}",response.getBody());
		}
		page.setRecords(knowledgeBases);
		return new PageUtils(page);
	}
	
	@Override
	public KnowledgeBase info(Integer id) {
		KnowledgeBase knowledgeBase = this.baseMapper.queryInfo(id);
		if(ObjectUtils.isNotEmpty(knowledgeBase)) {
			knowledgeBase.setTagType(StrUtil.replace(knowledgeBase.getTagType(), "_user_defined", ""));
			String galaxyUrl = sysConfigService.getValue("galaxy_url");
			String url = galaxyUrl+"knowledge_base/v1";
			ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET,null,Object.class,new HashMap());
			List<Map<String,Object>> handlerData = new ArrayList<Map<String,Object>>();
			if(response.getStatusCodeValue()==200) {
				HashMap body = (HashMap) response.getBody();
				handlerData = (List<Map<String,Object>>) body.get("data");
				log.debug("Get knowledgebases info : {}",JSONUtil.toJsonStr(handlerData));
				handlerData = handlerData.stream().filter(o -> StrUtil.equals((String)o.get("version"),"latest")).collect(Collectors.toList());
				Map<Object, Map<String, Object>> secondDatas = handlerData.stream().collect(Collectors.toMap(data->data.get("id"), Function.identity()));
				// 组装本地数据库数据与galaxy接口数据信息 默认获取文件的最新版本latest
				Map<String, Object> map = secondDatas.get(knowledgeBase.getKnowledgeId());
				if(ObjectUtil.isNotEmpty(map)) {
					knowledgeBase.setFormat((String)map.get("format"));
					knowledgeBase.setVersion((String)map.get("version"));
					knowledgeBase.setPath((String)map.get("path"));
				}else {
					log.debug("local data error knowledge id : {}",knowledgeBase.getKnowledgeId());
				}
			}else{
				log.debug("Get knowledgebases error code : {}",response.getStatusCodeValue());
				log.debug("Get knowledgebases error msg : {}",response.getBody());
			}
			
			// 获取到path之后 下载文件组装数据
			if(StrUtil.isNotBlank(knowledgeBase.getPath())) {
				String path = knowledgeBase.getPath();
				URI uri = URI.create(path);
			    ResponseEntity<byte[]> resp = restTemplate.getForEntity(uri, byte[].class);
				if(resp.getStatusCodeValue()==200) {
					byte[] bytes = (byte[]) resp.getBody();
					Map<String,Object> datas = (Map<String, Object>) JSON.parse(StrUtil.str(bytes, CharsetUtil.UTF_8));
					knowledgeBase.setData(datas.get("data"));
				}else{
					log.debug("Get knowledgebase data error code : {}",response.getStatusCodeValue());
					log.debug("Get knowledgebase data error msg : {}",response.getBody());
				}
			}
		}
		return knowledgeBase;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String add(Map<String, Object> params) {
		String id = null;
		Map<String, Object> handlerParam = this.handlerParam(params);
		String tagName = (String) handlerParam.get("tagName");
		String tagType = (String) handlerParam.get("tagType");
		String remark = (String) handlerParam.get("remark");
		ValidateUtils.is(tagName).notNull(RCode.KNOWLEDGE_NAME_ISNULL).and(tagType).notNull(RCode.KNOWLEDGE_TYPE_ISNULL);
		tagType = tagType +"_user_defined";
		File file = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".txt");
		try {
			Map data = (Map) params.get("result");
		    FileUtil.writeBytes(JSON.toJSONString(data).getBytes(), file);
		    // 组装参数请求第三方接口
		    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		    map.add("file", new FileSystemResource(file));
		    map.add("name", tagName);
		    map.add("format", "txt");
		    map.add("type", tagType);
		    String galaxyUrl = sysConfigService.getValue("galaxy_url");
			String url = galaxyUrl+"knowledge_base/v1";
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    HttpEntity entity = new HttpEntity(map,headers);
		    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,new HashMap());
		    if(response.getStatusCodeValue()==200) {
		    	Map body = (Map) response.getBody();
		    	Map respData = (Map) body.get("data");
		    	id = (String)respData.get("id");
		    	// 保存数据到数据库表中
		    	KnowledgeBase knowledgeBase = new KnowledgeBase();
		    	knowledgeBase.setCby(ShiroUtils.getUserId());
		    	knowledgeBase.setCtime(new Date());
		    	knowledgeBase.setUby(ShiroUtils.getUserId());
		    	knowledgeBase.setUtime(new Date());
		    	knowledgeBase.setKnowledgeId(id);
		    	knowledgeBase.setRemark(remark);
		    	knowledgeBase.setTagName(tagName);
		    	knowledgeBase.setTagType(tagType);
		    	this.getBaseMapper().insert(knowledgeBase);
		    }else{
				log.debug("Get knowledgebases error code : {}",response.getStatusCodeValue());
				log.debug("Get knowledgebases error msg : {}",response.getBody());
			}
		} finally {
		    if(file != null && file.exists()){
		        file.delete();
		    }
		}
		return id;
	}
	
	@Override
	public Integer modify(Map<String, Object> params) {
		Integer id = (Integer) params.get("id");
		KnowledgeBase knowledgeBase = this.getBaseMapper().selectOne(new LambdaQueryWrapper<KnowledgeBase>().eq(KnowledgeBase::getId, id));
		if(ObjectUtil.isEmpty(knowledgeBase)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
		}
		// 不允许修改name和type 从库里进行赋值
		params.put("tagName", knowledgeBase.getTagName());
		params.put("tagType", knowledgeBase.getTagType());
		Map<String, Object> handlerParam = this.handlerParam(params);
		File file = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".txt");
		Map data = (Map) handlerParam.get("result");
		try {
		    FileUtil.writeBytes(JSON.toJSONString(data).getBytes(), file);
		    // 组装参数请求第三方接口
		    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		    map.add("file", new FileSystemResource(file));
		    map.add("id", knowledgeBase.getKnowledgeId());
		    String galaxyUrl = sysConfigService.getValue("galaxy_url");
			String url = galaxyUrl+"knowledge_base/v1";
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    HttpEntity entity = new HttpEntity(map,headers);
		    ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT,entity,Object.class,new HashMap());
		    if(response.getStatusCodeValue()==200) {
		    	Map body = (Map) response.getBody();
		    	// 保存数据到数据库表中
		    	knowledgeBase.setUby(ShiroUtils.getUserId());
		    	knowledgeBase.setUtime(new Date());
		    	this.getBaseMapper().updateById(knowledgeBase);
		    }else{
				log.debug("Get knowledgebases error code : {}",response.getStatusCodeValue());
				log.debug("Get knowledgebases error msg : {}",response.getBody());
			}
		} finally {
		    if(file != null && file.exists()){
		        file.delete();
		    }
		}
		return id;
	}
	
	private Map<String,Object> handlerParam(Map<String, Object> params) {
		Integer id = (Integer) params.get("id");
		String name = (String) params.get("tagName");
		String type = (String) params.get("tagType");
		List<Map<String,Object>> datas = (List<Map<String,Object>>) params.get("data");
		ValidateUtils.is(datas).notNull(RCode.KNOWLEDGE_BASE_PARAM_NOTNULL);
		if(StrUtil.isNotBlank(type)) {
			if(StrUtil.equalsAny(type, "ip","domain","app")) {
				params.put("type", type+"_user_defined");
			}
		}
		//校验名称和类型不能重复 仅新增时进行校验
		if(ObjectUtil.isEmpty(id)) {
			KnowledgeBase knowledgeBase = this.getBaseMapper().selectOne(new LambdaQueryWrapper<KnowledgeBase>().eq(KnowledgeBase::getTagName, name).eq(KnowledgeBase::getTagType, type+"_user_defined"));
			if(ObjectUtil.isNotEmpty(knowledgeBase)) {
				throw new CNException(RCode.KNOWLEDGE_BASE_PARAM_DUPLICATE);
			}
		}
		
		//组装为最后需要的数据格式
		Map result = new HashMap<String,Object>();
		result.put("tagName", name);
		result.put("tagType", type);
		result.put("data",datas);
		params.put("result", result);
		return params;
	}
	
	@Override
	public R removeKnowledgeBase(String ids) {
		List<String> idList = Arrays.asList(ids.split(StrUtil.COMMA));
		// 根据id信息进行查询对应的knowledgeId
		List<KnowledgeBase> knowledgeBases = this.baseMapper.selectBatchIds(idList);
		List<String> knowledgeIds = knowledgeBases.stream().map(KnowledgeBase::getKnowledgeId).collect(Collectors.toList());
		if(ObjectUtil.isEmpty(knowledgeIds)) {
			throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
		}
		try {
			List<String> failIds = new ArrayList<String>();
			String galaxyUrl = sysConfigService.getValue("galaxy_url");
			String url = galaxyUrl+"knowledge_base/v1";
			List<CompletableFuture<Map<String, Object>>> futureList = ListUtil.list(false);
			for(String id : knowledgeIds) {
				CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
					Map<String,Object> result =new HashMap<String,Object>();
					String requestUrl = url+"?id="+id;
					ResponseEntity response = restTemplate.exchange(requestUrl, HttpMethod.DELETE,null,Object.class);
					if(response.getStatusCodeValue()!=200) {
						result.put("id", id);
					}else {
						Map map = (Map) response.getBody();
						Integer status = (Integer)map.get("status");
						if(status!=200) {
							result.put("id", id);
						}
					}
	    			return result;
				});
	    		futureList.add(future);
			}
			// 等待所有结果返回
			CompletableFuture.allOf((CompletableFuture<?>[]) futureList.toArray(new CompletableFuture[0]))
						.get(knowledgeRemoveTimeout, TimeUnit.MILLISECONDS);
			
			futureList.forEach(future -> {
				Map<String, Object> result = future.getNow(null);
				if(ObjectUtil.isNotEmpty(result.get("id"))) {
					failIds.add((String)result.get("id"));
				}
			});
			// 将删除成功的id信息从数据库中删除
			if(ObjectUtils.isEmpty(failIds)) {
				this.getBaseMapper().delete(new LambdaQueryWrapper<KnowledgeBase>().in(KnowledgeBase::getKnowledgeId, knowledgeIds));
				return R.ok();
			}else {
				List<String> successIds = knowledgeIds.stream().filter(knowledgeId -> !failIds.contains(knowledgeId)).collect(Collectors.toList());
				this.getBaseMapper().delete(new LambdaQueryWrapper<KnowledgeBase>().in(KnowledgeBase::getKnowledgeId, successIds));
				return R.error(400,JSON.toJSONString(failIds));
			}
		}catch(Exception e) {
			return R.error(400,e.getMessage());
		}
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
		    	if(StrUtil.equals(type, "ip")) {
		    		format = KnowledgeCheckUtil.validIpFormat(row);
		    	}else if(StrUtil.equals(type, "domain")){
		    		format = KnowledgeCheckUtil.validateFqdn(row);
		    	}else {
		    		format = KnowledgeCheckUtil.validateApp(row);
		    	}
		        Map<String,Object> res = new HashMap<String,Object>();
		        res.put("tagItem", data.get(0));
		        res.put("tagValue", data.get(1));
		        if(format) {
		        	res.put("status",1);
			        res.put("msg","success");
		        }else {
		        	res.put("status",0);
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
}
