package net.geedge.modules.knowledge.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.Constant;
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
	@Value("${knowledgeRemoveTimeout:60000}")
	private Integer knowledgeRemoveTimeout;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageUtils base(Map<String, Object> params) {
		String name = (String) params.get("tagName");
		String type = (String) params.get("tagType");
		String isFull = (String) params.get("isFull");
		String pageNo = (String) params.get("pageNo");
		String pageSize = (String) params.get("pageSize");
		int size = -1;
		int no = 0;
		if(StrUtil.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);
		}
		if(StrUtil.isNotBlank(pageNo)) {
			no = Integer.parseInt(pageNo);
		}
		String galaxyUrl = sysConfigService.getValue("galaxy_url");
		String url = galaxyUrl+"knowledge_base/v1";
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET,null,Object.class,new HashMap());
		List<KnowledgeBase> result = new ArrayList<KnowledgeBase>(); 
		if(response.getStatusCodeValue()==200) {
			List<Map<String,Object>> handlerData = new ArrayList<Map<String,Object>>();
			HashMap body = (HashMap) response.getBody();
			handlerData = (List<Map<String,Object>>) body.get("data");
			// 先以type过滤一次数据信息
			if(StrUtil.isNotBlank(type)) {
				handlerData = handlerData.stream().filter(o -> StrUtil.equals((String)o.get("type"), type)).collect(Collectors.toList());
			}
			if(StrUtil.isBlank(isFull) || Integer.parseInt(isFull) == 0) {
				// 是否展示所有数据 默认只展示leatest数据
				handlerData = handlerData.stream().filter(o -> StrUtil.equals((String)o.get("version"),"latest")).collect(Collectors.toList());
			}
			if(StrUtil.isNotBlank(name)) {
				handlerData = handlerData.stream().filter(o -> StrUtil.contains((String)o.get("name"),name)).collect(Collectors.toList());
			}
			
			//自定义分页
			
			Map<String, Map<String, Object>> collect = handlerData.stream().collect(Collectors.toMap(each -> (String)each.get("id"), Function.identity()));
			Set<String> ids = collect.keySet();
			result = knowledgeBaseService.getBaseMapper().selectList(new LambdaQueryWrapper<KnowledgeBase>().in(KnowledgeBase::getKnowledgeId, ids));
			for(KnowledgeBase r : result) {
				Map<String, Object> map = collect.get(r.getKnowledgeId());
				r.setFormat((String)map.get("format"));
				r.setVersion((String)map.get("version"));
				r.setPath((String)map.get("version"));
			}
		}
		IPage<KnowledgeBase> page=this.page(new Query(KnowledgeBase.class).getPage(params));
		page.setRecords(result);
		return new PageUtils(page);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String add(Map<String, Object> params) {
		String id = null;
		Map<String, Object> handlerParam = this.handlerParam(params);
		String tagName = (String) handlerParam.get("tagName");
		String tagType = (String) handlerParam.get("tagType");
		String remark = (String) handlerParam.get("remark");
		ValidateUtils.is(tagName).notNull(RCode.NOT_NULL_ERROR).and(tagType).notNull(RCode.NOT_NULL_ERROR);
		tagType = tagType +"_user_defined";
		File file = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".csv");
		List<List<String>> datas = (List<List<String>>) handlerParam.get("handlerData");
		try {
		    CsvWriter writer = CsvUtil.getWriter(file, CharsetUtil.CHARSET_UTF_8);
		    datas.forEach(data -> {
		        writer.write(data.toArray(new String[] {}));
		    });
		    // 组装参数请求第三方接口
		    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		    map.add("file", new FileSystemResource(file));
		    map.add("name", tagName);
		    map.add("format", "csv");
		    map.add("type", tagType);
		    String galaxyUrl = sysConfigService.getValue("galaxy_url");
			String url = galaxyUrl+"knowledge_base/v1";
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    HttpEntity entity = new HttpEntity(map,headers);
		    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,new HashMap());
		    if(response.getStatusCodeValue()==200) {
		    	Map body = (Map) response.getBody();
		    	Map data = (Map) body.get("data");
		    	id = (String)data.get("id");
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
		    }
		} finally {
		    if(file != null && file.exists()){
		        file.delete();
		    }
		}
		return id;
	}
	
	@Override
	public String modify(Map<String, Object> params) {
		String knowledgeId = (String) params.get("id");
		KnowledgeBase knowledgeBase = this.getBaseMapper().selectOne(new LambdaQueryWrapper<KnowledgeBase>().eq(KnowledgeBase::getKnowledgeId, knowledgeId));
		if(ObjectUtil.isEmpty(knowledgeBase)) {
			throw new CNException(RCode.NOT_NULL_ERROR);
		}
		Map<String, Object> handlerParam = this.handlerParam(params);
		File file = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".csv");
		List<List<String>> datas = (List<List<String>>) handlerParam.get("handlerData");
		try {
		    CsvWriter writer = CsvUtil.getWriter(file, CharsetUtil.CHARSET_UTF_8);
		    datas.forEach(data -> {
		        writer.write(data.toArray(new String[] {}));
		    });
		    // 组装参数请求第三方接口
		    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		    map.add("file", new FileSystemResource(file));
		    map.add("id", knowledgeId);
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
		    }
		} finally {
		    if(file != null && file.exists()){
		        file.delete();
		    }
		}
		return knowledgeId;
	}
	
	private Map<String,Object> handlerParam(Map<String, Object> params) {
		String type = (String) params.get("tagType");
		List<Map<String,Object>> datas = (List<Map<String,Object>>) params.get("data");
		ValidateUtils.is(datas).notNull(RCode.NOT_NULL_ERROR);
		if(StrUtil.isNotBlank(type)) {
			if(StrUtil.equalsAny(type, "ip","domain","app")) {
				params.put("type", type+"_user_defined");
			}
		}
		List<List<String>> handlerDatas = new ArrayList<List<String>>();
		for(Map m : datas) {
		    List<String> itemList = (List<String>) m.get("itemList");
		    for(String item : itemList) {
		    	List<String> handlerList = new ArrayList<String>();
		    	handlerList.add(item);
		    	handlerList.add((String)m.get("tagValue"));
		    	handlerDatas.add(handlerList);
		    }
		}
		params.put("handlerData", handlerDatas);
		return params;
	}
	
	@Override
	public R removeKnowledgeBase(String ids) {
		try {
			List<String> failIds = new ArrayList<String>();
			String galaxyUrl = sysConfigService.getValue("galaxy_url");
			String url = galaxyUrl+"knowledge_base/v1";
			List<CompletableFuture<Map<String, Object>>> futureList = ListUtil.list(false);
			List<String> idList = Arrays.asList(ids.split(StrUtil.COMMA));
			for(String id : idList) {
				CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
					Map<String,Object> result =new HashMap<String,Object>();
					String requestUrl = url+"?id="+id;
					ResponseEntity response = restTemplate.exchange(requestUrl, HttpMethod.DELETE,null,Object.class);
					if(response.getStatusCodeValue()!=200) {
						result.put("id", id);
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
				this.getBaseMapper().delete(new LambdaQueryWrapper<KnowledgeBase>().in(KnowledgeBase::getKnowledgeId, idList));
				return R.ok();
			}else {
				List<String> successIds = idList.stream().filter(id -> !failIds.contains(id)).collect(Collectors.toList());
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
		try{
			tempFile = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".csv");
			FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile);
			CsvData datas = CsvUtil.getReader().read(ResourceUtil.getReader(tempFile.getPath(), CharsetUtil.CHARSET_GBK));
			
			Map<String,Object> result = new HashMap<String,Object>();
			List<Map<String,Object>> handlerData = new ArrayList<Map<String,Object>>();

		    for(CsvRow data : datas.getRows()){
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
