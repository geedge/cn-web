package net.geedge.modules.knowledge.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.CharsetDetector;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.*;
import net.geedge.modules.knowledge.dao.TagDao;
import net.geedge.modules.knowledge.entity.*;
import net.geedge.modules.knowledge.service.*;
import net.geedge.modules.sys.service.BasicImportAndExportService;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/03/11:30
 */
@Service("TagService")
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
    private Log log = Log.get();
    @Autowired
    public IntelligenceIndicatorService intelligenceIndicatorService;

    @Autowired
    public KnowledgeBaseService knowledgeBaseService;

    @Autowired
    public KnowledgeBaseSyncVersionService knowledgeBaseSyncVersionService;

    @Autowired
    public KnowledgeBaseAuditLogService knowledgeBaseAuditLogService;

    @Autowired
    public SysConfigService sysConfigService;

    @Autowired
    public BasicImportAndExportService basicImportAndExportService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public PageUtils queryTagList(Map<String, Object> params) {
        preHandleParams(params);
        IPage page = this.page(new Query(Tag.class).getPage(params));
        List<Tag> list = this.baseMapper.queryPage(page, params);
        page.setRecords(timeHandle(list));
        return new PageUtils(page);
    }

    private List<Map<String,Object>> timeHandle(List<Tag> list) {
        List<Map<String,Object>> result = Tool.CollectionUtil.newArrayList();
        list.stream().forEach(tag -> {
            Map<String, Object> map = modifyTagTime(tag);
            result.add(map);
        });
        return result;
    }

    private void preHandleParams(Map<String, Object> params) {
        if(ObjectUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize","20");
        }
        if (ObjectUtil.isNotEmpty(params.get("category"))) {
            params.put("category", Arrays.asList(String.valueOf(params.get("category")).split(StringPool.COMMA)));
        } else {
            params.put("category", new ArrayList<>());
        }
        if (ObjectUtil.isNotEmpty(params.get("intent"))) {
            params.put("intent", Arrays.asList(String.valueOf(params.get("intent")).split(StringPool.COMMA)));
        } else {
            params.put("intent", new ArrayList<>());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrModifyTag(Map<String,Object> params) {
        Map<String, Object> result = validateParam(params);
        Tag tag =(Tag) result.get("tag");
        this.saveOrUpdate(tag);
        List<Integer> deleteItemIds = (List<Integer>) params.get("deleteItemIds");
        List<IntelligenceIndicator> intelligenceIndicators = (List<IntelligenceIndicator>) result.get(Constant.INTELLIGENCE_INDICATOR);
        if(ObjectUtil.isNotEmpty(intelligenceIndicators)) {
            // 将数据分为新增和更新
            Map<Boolean, List<IntelligenceIndicator>> map = intelligenceIndicators.stream().collect(Collectors.groupingBy(o-> ObjectUtil.isNotEmpty(o.getId())));
            List<IntelligenceIndicator> inserts = map.get(false);
            List<IntelligenceIndicator> updates = map.get(true);
            intelligenceIndicatorService.saveBatchForBinary(inserts);
            intelligenceIndicatorService.modifyBatchForBinary(updates);
        }
        if(ObjectUtil.isNotEmpty(deleteItemIds)) {
            intelligenceIndicatorService.removeByIds(deleteItemIds);
        }
        syncVersion();
        return tag.getId();
    }

    private Map<String,Object> validateParam(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        String id =(String) params.get("id");
        String name =(String) params.get("name");
        String category =(String) params.get("category");
        String intent =(String) params.get("intent");
        String indicatorType =(String) params.get("indicatorType");
        String description =(String) params.get("description");
        List<Tag> queryByName = this.baseMapper.selectList(new QueryWrapper<Tag>().lambda()
                .ne(Tag::getName, name)
                .eq(Tag::getId,id));
        if(ObjectUtil.isNotEmpty(queryByName)) {
            throw new CNException(RCode.TAG_NAME_NOT_MODIFIED);
        }
        Tag tag = null;
        if(ObjectUtil.isEmpty(id)){
            tag = new Tag();
            tag.setId(UUID.randomUUID().toString());
            tag.setName(name);
            tag.setIndicatorType(indicatorType);
            tag.setIsBuiltIn(0);
            tag.setCreatedTime(new Date());
        } else {
            tag = this.getById(id);
        }
        if(ObjectUtil.isEmpty(tag)){
            throw new CNException(RCode.TAG_NOT_EXIST);
        }
        tag.setCategory(category);
        tag.setIntent(intent);
        tag.setDescription(description);
        tag.setUpdatedTime(new Date());
        result.put("tag",tag);
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
            List<Map> maps = JSONUtil.toList(JSONUtil.toJsonStr(datas), Map.class);
            List<IntelligenceIndicator> intelligenceIndicators = new ArrayList<>();
            maps.stream().forEach(data->{
                IntelligenceIndicator intelligenceIndicator = new IntelligenceIndicator();
                if(ObjectUtil.isEmpty(data.get("id"))){
                    intelligenceIndicator.setFirstSeenTime(new Date());
                    intelligenceIndicator.setCreatedTime(new Date());
                } else {
                    intelligenceIndicator.setId(Long.valueOf(String.valueOf(data.get("id"))));
                }
                if(data.get("entityType").equals("IP")) {
                    String[] ipData = KnowledgeCheckUtil.ipToData((String) data.get("entityValue"));
                    // 计算ip binary
                    Map<String, String> ipHandler = KnowledgeCheckUtil.ipBinaryHandler(ipData[0],ipData[1],ipData[2]);
                    intelligenceIndicator.setIpAddrFormat(ipData[0]);
                    intelligenceIndicator.setIp1(ipData[1]);
                    intelligenceIndicator.setIp2(ipData[2]);
                    intelligenceIndicator.setStartIpBinary(ipHandler.get("start_ip_binary"));
                    intelligenceIndicator.setEndIpBinary(ipHandler.get("end_ip_binary"));
                } else {
                    intelligenceIndicator.setDomain((String) data.get("entityValue"));
                }
                intelligenceIndicator.setSourceId(Constant.SOURCE_ID);
                intelligenceIndicator.setSourceName(Constant.SOURCE_NAME);
                intelligenceIndicator.setType((String) data.get("entityType"));
                intelligenceIndicator.setTags(name);
                intelligenceIndicator.setLastSeenTime(new Date());
                intelligenceIndicator.setUpdatedTime(new Date());
                intelligenceIndicators.add(intelligenceIndicator);
            });
            result.put(Constant.INTELLIGENCE_INDICATOR, intelligenceIndicators);
        }
        return result;
    }

    @Override
    public R importData(String indicatorType,MultipartFile file) {
        File tempFile = null;
        if(!StrUtil.endWith(file.getOriginalFilename(), ".csv")) {
            throw new CNException(RCode.KNOWLEDGE_FILE_TYPE_ERROR);
        }
        String knowledgeImportConfigJson = sysConfigService.getValue("knowledge_import_config_json");
        HashMap knowledgeConfigs = parseObject(knowledgeImportConfigJson, HashMap.class);
        Map<String,Object> sourceConfig = (Map<String, Object>) knowledgeConfigs.get(Constant.INTELLIGENCE_INDICATOR_PARSER);
        List<String> headerInfos = StrUtil.split((String)sourceConfig.get("headers"), StrUtil.C_COMMA);
        if(ObjectUtils.isEmpty(headerInfos)) {
            throw new CNException(RCode.EXCELFILE_HEADER_TEMPLATE_ERROR);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> handlerData = new ArrayList<Map<String,Object>>();
        List<CsvRow> csvRows = null;
        try{
            tempFile = FileUtil.file(Constant.TMP_ROOT_PATH + File.separator + new Date().getTime() + ".csv");
            FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile);
            CsvData datas = CsvUtil.getReader().read(ResourceUtil.getReader(tempFile.getPath(), CharsetDetector.detect(tempFile)));
            csvRows = (List<CsvRow>)datas.getRows();
        }catch(Exception e) {
            return R.error(400,e.getMessage());
        }finally {
            if(ObjectUtil.isNotEmpty(tempFile) && tempFile.exists()) {
                tempFile.delete();
            }
        }
        if(ObjectUtil.isEmpty(csvRows) || csvRows.size() < 2) {
            throw new CNException(RCode.EXCELFILE_EMPTY_ERROR);
        }
        CsvRow csvRow = csvRows.get(0);
        List<String> headerInfo = csvRow.getRawList();
        // 表头校验
        if (!CSVUtils.validateCSVHeader(headerInfo, headerInfos)) {
            throw new CNException(RCode.EXCELFILE_HEADER_TEMPLATE_ERROR);
        }
        //去除表头
        csvRows.remove(0);
        //csv文件去重
        Set<CsvRow> rowsSet = new TreeSet<CsvRow>(Comparator.comparing(row -> row.get(0).trim() + "_____" + row.get(1).trim()));
        rowsSet.addAll(csvRows);

        for(CsvRow data : rowsSet) {
            // 此处进行数据校验
            String entityType = data.get(0).trim();
            String entityValue = data.get(1).trim();
            boolean typeFormat = validateType(entityType,indicatorType);
            boolean valueFormat = validateValue(entityType,entityValue);
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("entityType",entityType);
            res.put("entityValue",entityValue);
            if(!typeFormat) {
                res.put("isValid",0);
                res.put("errorAttribute","entityType");
            }else if(!valueFormat) {
                res.put("isValid",0);
                res.put("errorAttribute","entityValue");
            } else {
                res.put("isValid",1);
            }
            handlerData.add(res);
        }
        result.put("list",handlerData);
        return R.ok(result);
    }

    private boolean validateType(String entityType, String indicatorType) {
        boolean typeFormat = true;
        if(StrUtil.isEmpty(entityType)){
            typeFormat = false;
        }
        if (!indicatorType.contains(entityType)) {
            typeFormat = false;
        }
        return typeFormat;
    }

    private boolean validateValue(String entityType, String entityValue) {
        boolean valueFormat = true;
        if(StrUtil.equals(entityType, "IP")) {
            valueFormat = KnowledgeCheckUtil.validIpFormat(entityValue);
        }else if(StrUtil.equals(entityType, "Domain")){
            valueFormat = KnowledgeCheckUtil.validateFqdn(entityValue);
        }
        return valueFormat;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> remove(String id) {
        // 内置数据不可删除
        List<String> tagIds = StrUtil.split(id, StrUtil.C_COMMA);
        List<Tag> tags = this.baseMapper.selectBatchIds(tagIds);
        List<String> builtInIds = tags.stream().filter(tag-> tag.getIsBuiltIn()
                .equals(1)).map(Tag::getId).collect(Collectors.toList());
        if(ObjectUtils.isNotEmpty(builtInIds)) {
            throw new CNException(RCode.TAG_BUILTIN_ERROR);
        }
        // 判断传入数据是否都存在 不存在返回错误提示
        if(tags.size()!=tagIds.size()) {
            throw new CNException(RCode.TAG_NOT_EXIST);
        }
        List<String> ids = tags.stream().map(Tag::getId).collect(Collectors.toList());
        List<String> names = tags.stream().map(Tag::getName).collect(Collectors.toList());
        syncVersion();
        this.remove(new QueryWrapper<Tag>().lambda().in(Tag::getId,ids));
        intelligenceIndicatorService.remove(new QueryWrapper<IntelligenceIndicator>().lambda().in(IntelligenceIndicator::getTags,names));
        return ids;
    }

    private void syncVersion(){
        KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().lambda()
                .eq(KnowledgeBase::getSource, Constant.INTELLIGENCE_INDICATOR));
        if(ObjectUtil.isEmpty(knowledgeBase)){
            throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
        }
        knowledgeBaseService.syncVersion(knowledgeBase);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importBatch(MultipartFile file,String action){
        List<Integer> deleteDataIds = Lists.newArrayList();
        List<Map<String, String>> saveOrUpdateList = Lists.newArrayList();
        //覆盖操作时 将对应表中数据删除清空
        if (StrUtil.equals(action, "overwrite")) {
            basicImportAndExportService.removeList(Constant.INTELLIGENCE_INDICATOR);
        }
        this.validAndBuildDatasets(file, action, saveOrUpdateList);
        KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().lambda().eq(KnowledgeBase::getSource, Constant.INTELLIGENCE_INDICATOR));
        if(ObjectUtil.isEmpty(knowledgeBase)) {
            throw new CNException(RCode.KNOWLEDGE_BASE_NOT_EXIST);
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
        log.setOpType(OperationEnum.UPDATE.getValue());
        log.setCommitVersion(syncVersion.getVersion());
        knowledgeBaseAuditLogService.save(log);
        knowledgeBaseService.updateById(knowledgeBase);
        return knowledgeBase.getKnowledgeId();
    }

    private void validAndBuildDatasets(MultipartFile file, String action, List<Map<String, String>> importDataList) {
        // 根据source获取对应的表头信息
        String knowledgeImportConfigJson = sysConfigService.getValue("knowledge_import_config_json");
        HashMap knowledgeConfigs = parseObject(knowledgeImportConfigJson, HashMap.class);
        Map<String,Object> sourceConfig = (Map<String, Object>) knowledgeConfigs.get(Constant.INTELLIGENCE_INDICATOR);
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
            throw new CNException(RCode.TAG_PARAM_NOTNULL);
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
                CsvData data = reader2.read();
                //校验tag是否存在
                if(validateTags(data)){
                    throw new CNException(RCode.TAG_NOT_EXIST);
                }
                data.getRows().stream().forEach(row -> {
                    // 当前行号
                    Map<String, String> tmpMap = Tool.MapUtil.newHashMap();
                    Map<String, String> fieldMap = row.getFieldMap();
                    long originalLineNumber = row.getOriginalLineNumber();
                    for(String headerName : fieldMap.keySet()) {
                        String value = fieldMap.get(headerName);
                        if(headerName.indexOf("\uFEFF")>= 0) {
                            headerName = headerName.substring(1);
                        }
                        Map<String, Object> info = configs.get(headerName);
                        List<String> validateVal = (List<String>) info.get("validateVal");
                        if(ObjectUtil.isNotEmpty(validateVal) && !headerName.equals("ip_addr_format")) {
                            boolean isPass = false;
                            for(String str : validateVal) {
                                if(str.equals(value)){
                                    isPass = true;
                                }
                            }
                            if(!isPass) {
                                log.error("tag import data error , line is {},columnName is {}", originalLineNumber, headerName);
                                throw new CNException(RCode.IMPORT_ERROR);
                            }
                        }
                        tmpMap.put(headerName, value);
                    }
                    if("IP".equals(tmpMap.get("type"))) {
                        Map<String, String> ipHandlerResult = KnowledgeCheckUtil.ipBinaryHandler(tmpMap.get("ip_addr_format"),tmpMap.get("ip1"),tmpMap.get("ip2"));
                        tmpMap.put("start_ip_binary",ipHandlerResult.get("start_ip_binary"));
                        tmpMap.put("end_ip_binary",ipHandlerResult.get("end_ip_binary"));
                    }
                    if (ObjectUtil.isNotEmpty(tmpMap)) {
                        importDataList.add(tmpMap);
                    }
                    if(importDataList.size() == 100) {
                        if (StrUtil.equals(action, "update")) {
                            this.updateDataHandler(importDataList,validateColumns);
                        }
                        saveOrUpdateBatch(importDataList,conn);
                        importDataList.clear();
                    }
                });
                if(ObjectUtil.isNotEmpty(importDataList)) {
                    if (StrUtil.equals(action, "update")) {
                        this.updateDataHandler(importDataList,validateColumns);
                    }
                    saveOrUpdateBatch(importDataList,conn);
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

    private void updateDataHandler(List<Map<String, String>> importDataList,List<String> condition) {
        // 更新操作 更新最早创建数据、删除其余冗余数据
        List<Map<String, Object>> results = basicImportAndExportService.queryDataIds(Constant.INTELLIGENCE_INDICATOR, condition, importDataList);
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
            List<Long> deleteIds = Tool.CollectionUtil.newArrayList();
            results = results.stream().filter(o -> !conId.values().contains(o.get("id"))).collect(Collectors.toList());
            results.stream().forEach(o -> {deleteIds.add((Long)o.get("id"));});
            if(ObjectUtil.isNotEmpty(deleteIds)) {
                basicImportAndExportService.removeLongList(Constant.INTELLIGENCE_INDICATOR,deleteIds);
            }
        }
    }

    private boolean validateTags(CsvData data){
        Set<String> tagNames = new TreeSet<>();
        int tagsIndex = data.getHeader().indexOf("tags");
        data.getRows().stream().forEach(row->{
            List<String> split = StrUtil.split(row.get(tagsIndex), StrUtil.COMMA);
            for (String str:split) {
                tagNames.add(str);
            }
        });
        List<Tag> tags = this.baseMapper.selectList(new QueryWrapper<Tag>().lambda().in(Tag::getName, tagNames));
        if(tags.size() == tagNames.size()){
            return false;
        }
        return true;
    }

    private void saveOrUpdateBatch(List importDataList, Connection conn) {
        List<IntelligenceIndicator> intelligenceIndicators = JSONUtil.toList(JSONUtil.toJsonStr(importDataList),IntelligenceIndicator.class);
        intelligenceIndicators.stream().forEach(data->{
            if(ObjectUtil.isEmpty(data.getId())) {
                data.setCreatedTime(new Date());
                if(ObjectUtil.isEmpty(data.getFirstSeenTime())) {
                    data.setFirstSeenTime(new Date());
                }
            }
            if(ObjectUtil.isEmpty(data.getLastSeenTime())) {
                data.setLastSeenTime(new Date());
            }
            data.setUpdatedTime(new Date());
        });
        Map<Boolean, List<IntelligenceIndicator>> intelligenceIndicatorMap = intelligenceIndicators.stream().collect(Collectors.groupingBy(intelligenceIndicator-> ObjectUtil.isNotEmpty(intelligenceIndicator.getId())));
        List<IntelligenceIndicator> updateIntelligenceIndicators = intelligenceIndicatorMap.get(true);
        List<IntelligenceIndicator> insertIntelligenceIndicators = intelligenceIndicatorMap.get(false);
        intelligenceIndicatorService.saveBatchJDBC(conn,insertIntelligenceIndicators);
        intelligenceIndicatorService.modifyBatchJDBC(conn,updateIntelligenceIndicators);
    }

    @Override
    public Map<String,Object> queryIndicators(String id, Map<String, Object> params) {
        Map<String,Object> result = Tool.MapUtil.newHashMap();
        Tag tag = this.baseMapper.selectOne(new QueryWrapper<Tag>().lambda().eq(Tag::getId,id));
        if(ObjectUtil.isEmpty(tag)) {
            throw new CNException(RCode.TAG_NOT_EXIST);
        }
        IPage page = this.page(new Query(IntelligenceIndicator.class).getPage(params));
        params.put("name",tag.getName());
        List<IntelligenceIndicator> list = intelligenceIndicatorService.queryByTag(page, params);
        page.setRecords(list);
        PageUtils pageUtils = new PageUtils(page);
        List<Map<String,Object>> itemList = Tool.CollectionUtil.newArrayList();
        list.stream().forEach(o -> {
            Map<String,Object> map = Tool.MapUtil.newHashMap();
            String entityValue = createEntityValue(o);
            map.put("id",o.getId());
            map.put("entityType",o.getType());
            map.put("entityValue",entityValue);
            itemList.add(map);
        });
        result.put("pageNo",pageUtils.getpageNo());
        result.put("pageSize",pageUtils.getPageSize());
        result.put("total",pageUtils.gettotal());
        result.putAll(modifyTagTime(tag));
        result.put("itemList",itemList);
        return result;
    }

    private Map<String,Object> modifyTagTime(Tag tag) {
        Map<String, Object> map = Tool.BeanUtil.beanToMap(tag);
        if(ObjectUtil.isNotEmpty(map.get("updatedTime")) && ObjectUtil.isNotEmpty(map.get("createdTime"))) {
            Date updateTime = (Date) map.get("updatedTime");
            Date createTime = (Date) map.get("createdTime");
            map.put("updatedTime", updateTime.getTime() / 1000);
            map.put("createdTime", createTime.getTime() / 1000);
        }
        return map;
    }

    private String createEntityValue(IntelligenceIndicator intelligenceIndicator) {
        String entityValue = null;
        if("IP".equals(intelligenceIndicator.getType())) {
            if(StrUtil.equals(intelligenceIndicator.getIpAddrFormat(),Constant.IpFormatType.SINGLE.getValue())){
                entityValue = intelligenceIndicator.getIp1();
            } else if(StrUtil.equals(intelligenceIndicator.getIpAddrFormat(),Constant.IpFormatType.RANGE.getValue())){
                entityValue = intelligenceIndicator.getIp1() + "-" + intelligenceIndicator.getIp2();
            } else {
                entityValue = intelligenceIndicator.getIp1() + "/" + intelligenceIndicator.getIp2();
            }
        } else {
            entityValue = intelligenceIndicator.getDomain();
        }
        return entityValue;
    }

    @Override
    public List<Map<String,Object>> queryDomainEntityTags(String resource) {
        List<Map<String,Object>> result = Tool.CollectionUtil.newArrayList();
        List<String> tagNames = intelligenceIndicatorService.queryDomainEntityTags(resource);
        if(ObjectUtil.isNotEmpty(tagNames)) {
            result = this.baseMapper.queryTagList(tagNames);
        }
        return result;
    }

    @Override
    public List<Map<String,Object>> queryIpEntityTags(String resource) {
        List<Map<String,Object>> result = Tool.CollectionUtil.newArrayList();
        List<String> tagNames = intelligenceIndicatorService.queryIpEntityTags(resource);
        if(ObjectUtil.isNotEmpty(tagNames)) {
            result = this.baseMapper.queryTagList(tagNames);
        }
        return result;
    }
}
