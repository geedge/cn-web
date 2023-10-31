package net.geedge.modules.rule.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Constant.RuleType;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.service.KnowledgeBaseService;
import net.geedge.modules.rule.dao.DetectionRuleDao;
import net.geedge.modules.rule.entity.DetectionRule;
import net.geedge.modules.rule.entity.RuleCommitHistory;
import net.geedge.modules.rule.entity.RuleVersion;
import net.geedge.modules.rule.service.DetectionRuleService;
import net.geedge.modules.rule.service.RuleCommitHistoryService;
import net.geedge.modules.rule.service.RuleVersionService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service("detectionRuleService")
public class DetectionRuleServiceImpl extends ServiceImpl<DetectionRuleDao, DetectionRule> implements DetectionRuleService {
	
	@Autowired
	private RuleCommitHistoryService ruleCommitHistoryService;
	
	@Autowired
	private RuleVersionService  ruleVersionService;
	
	@Autowired
	private KnowledgeBaseService knowledgeBaseService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Integer> removeRules(List<Integer> ids) {
		// 内置数据不可删除
		List<DetectionRule> detectionRules = this.baseMapper.selectBatchIds(ids);
		List<Integer> builtInIds = detectionRules.stream().filter(detectionRule-> detectionRule.getIsBuiltIn()
				.equals(1)).map(DetectionRule::getRuleId).collect(Collectors.toList());
		if(ObjectUtils.isNotEmpty(builtInIds)) {
			throw new CNException(RCode.RULE_BUILTIN_ERROR);
		}
		// 判断传入数据是否都存在 不存在返回错误提示
		if(detectionRules.size()!=ids.size()) {
			throw new CNException(RCode.RULE_NOT_EXIST);
		}
		RuleVersion ruleVersion = ruleVersionService.list().get(0);
		// 历史记录表数据填充
		List<RuleCommitHistory> ruleCommitHistories = new ArrayList<RuleCommitHistory>();
		for(DetectionRule rule : detectionRules) {
			Integer version = ruleVersion.getVersion();
			ruleVersion.setVersion(version+1);
			ruleVersion.setOpTime(new Date());
			RuleCommitHistory ruleCommitHistory = new RuleCommitHistory();
			ruleCommitHistory.setOpUser(ShiroUtils.getUserId().intValue());
			ruleCommitHistory.setOpTime(new Date());
			ruleCommitHistory.setOpType(OperationEnum.DELETE.getValue());
			ruleCommitHistory.setOpVersion(version+1);
			ruleCommitHistory.setRuleId(rule.getRuleId());
			ruleCommitHistories.add(ruleCommitHistory);
		}
		ruleVersionService.update(ruleVersion,null);
		ruleCommitHistoryService.saveBatch(ruleCommitHistories);
		detectionRules.stream().forEach(rule -> rule.setIsValid(0));
		this.updateBatchById(detectionRules);
		return ids;
	}

	@Override
	public DetectionRule getInfoById(Integer id) {
		DetectionRule rule = this.getById(id);
		List<DetectionRule> rules = new ArrayList<DetectionRule>();
		rules.add(rule);
		handlerRuleData(rules);
		return rule;
	}

	@Override
	public PageUtils getList(Map<String, Object> params) {
		if (ObjectUtil.isNotEmpty(params.get("category"))) {
			params.put("category", Arrays.asList(String.valueOf(params.get("category")).split(StringPool.COMMA)));
		}
		if (ObjectUtil.isNotEmpty(params.get("eventType"))) {
			params.put("eventType", Arrays.asList(String.valueOf(params.get("eventType")).split(StringPool.COMMA)));
		}
		IPage page = this.page(new Query(DetectionRule.class).getPage(params));
		List<DetectionRule> detectionRules = this.baseMapper.queryPage(page, params);
		handlerRuleData(detectionRules);
		page.setRecords(detectionRules);
		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DetectionRule saveDetectionRule(DetectionRule detectionRule) {
		this.validateDetectionRule(detectionRule);
		detectionRule.setCTime(new Date());
		detectionRule.setCUser(ShiroUtils.getUserId().intValue());
		detectionRule.setOpTime(new Date());
		detectionRule.setOpUser(ShiroUtils.getUserId().intValue());
		this.save(detectionRule);
		
		//创建版本表数据
		List<RuleVersion> versions = ruleVersionService.list();	
		RuleVersion ruleVersion;
		if(ObjectUtils.isEmpty(versions)) {
			ruleVersion = new RuleVersion();
			ruleVersion.setVersion(1);
			ruleVersion.setOpTime(new Date());
			ruleVersionService.save(ruleVersion);
		}else {
			ruleVersion = versions.get(0);
			Integer version = ruleVersion.getVersion();
			ruleVersion.setVersion(version+1);
			ruleVersion.setOpTime(new Date());
			ruleVersionService.update(ruleVersion,null);
		}
		
		//创建记录表数据
		RuleCommitHistory ruleCommitHistory = new RuleCommitHistory();
		ruleCommitHistory.setOpVersion(1);
		ruleCommitHistory.setOpTime(new Date());
		ruleCommitHistory.setOpUser(ShiroUtils.getUserId().intValue());
		ruleCommitHistory.setOpType(OperationEnum.ADD.getValue());
		ruleCommitHistory.setRuleId(detectionRule.getRuleId());
		ruleCommitHistory.setOpVersion(ruleVersion.getVersion());
		ruleCommitHistoryService.save(ruleCommitHistory);
		return detectionRule;
	}

	private void validateDetectionRule(DetectionRule detectionRule) {
		if(ObjectUtil.isEmpty(detectionRule.getRuleType())) {
			throw new CNException(RCode.RULE_TYPE_ISNULL);
		}
		if(ObjectUtil.isEmpty(detectionRule.getName())) {
			throw new CNException(RCode.RULE_NAME_ISNULL);
		}
		if(ObjectUtil.isEmpty(detectionRule.getCategory())) {
			throw new CNException(RCode.RULE_CATEGORY_ISNULL);
		}
		if(ObjectUtil.isEmpty(detectionRule.getEventType())) {
			throw new CNException(RCode.RULE_EVENT_TYPE_ISNULL);
		}
		if(ObjectUtil.isEmpty(detectionRule.getRuleTrigger())) {
			throw new CNException(RCode.RULE_TRIGGER_ISNULL);
		}
		
		List<String> ruleTypes = Arrays.asList(Constant.RuleType.values()).stream().map(RuleType::getValue).collect(Collectors.toList());
        if (!ruleTypes.contains(detectionRule.getRuleType())) {
            throw new CNException(RCode.RULE_TYPE_INVALID);
        }
        // rule name 不能重复
        DetectionRule rule = this.getOne(new QueryWrapper<DetectionRule>().lambda().eq(DetectionRule::getName, detectionRule.getName()).eq(DetectionRule::getIsValid, 1)
        		.ne(ObjectUtil.isNotEmpty(detectionRule.getRuleId()),DetectionRule::getRuleId,detectionRule.getRuleId()));
        if(ObjectUtils.isNotEmpty(rule)) {
        	throw new CNException(RCode.RULE_NAME_DUPLICATE);
        } 
        
		// config 格式校验
        String configStr = detectionRule.getRuleConfig();
        if (StrUtil.isNotEmpty(configStr)) {
            Map configMap;
            try {
                configMap = JSONObject.parseObject(configStr, Map.class);
            } catch (JSONException e) {
                throw new CNException(RCode.RULE_CONFIG_FORMAT_ERROR);
            }
            String dataSource = (String) configMap.get("dataSource");
            if(StrUtil.isEmpty(dataSource)) {
            	throw new CNException(RCode.RULE_CONFIG_DB_ISNULL);
            }
            Integer knowledgeId = (Integer) configMap.get("knowledgeId");
            KnowledgeBase knowledgeBase = knowledgeBaseService.getById(knowledgeId);
            if(ObjectUtil.isEmpty(knowledgeBase)) {
            	throw new CNException(RCode.KNOWLEDGE_BASE_ITEM_NOT_EXIST);
            }
        }
        
        // trigger 格式校验
        String ruleTrigger = detectionRule.getRuleTrigger();
        if (StrUtil.isNotEmpty(ruleTrigger)) {
            Map triggerMap;
            try {
            	triggerMap = JSONObject.parseObject(ruleTrigger, Map.class);
            } catch (JSONException e) {
                throw new CNException(RCode.RULE_TRIGGER_FORMAT_ERROR);
            }
            String interval = (String) triggerMap.get("interval");
            if(StrUtil.isEmpty(interval)) {
            	throw new CNException(RCode.RULE_TRIGGER_INTERVAL_ISNULL);
            }
            try {
            	java.time.Duration.parse(interval);
            }catch(Exception e) {
            	throw new CNException(RCode.RULE_TRIGGER_INTERVAL_FORMAT_ERROR);
            }
            String resetInterval = (String) triggerMap.get("resetInterval");
            if(StrUtil.isEmpty(resetInterval)) {
            	throw new CNException(RCode.RULE_TRIGGER_RESET_INTERVAL_ISNULL);
            }
            try {
            	java.time.Duration.parse(resetInterval);
            }catch(Exception e) {
            	throw new CNException(RCode.RULE_TRIGGER_RESET_INTERVAL_FORMAT_ERROR);
            }
        }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DetectionRule modifyDetectionRule(DetectionRule detectionRule) {
		this.validateDetectionRule(detectionRule);
		detectionRule.setOpTime(new Date());
		detectionRule.setOpUser(ShiroUtils.getUserId().intValue());
		this.updateById(detectionRule);
		
		//创建版本表数据
		RuleVersion ruleVersion = ruleVersionService.list().get(0);	
		ruleVersion.setOpTime(new Date());
		Integer version = ruleVersion.getVersion();
		ruleVersion.setVersion(version+1);
		ruleVersionService.update(ruleVersion, null);
		
		//创建记录表数据
		RuleCommitHistory ruleCommitHistory = new RuleCommitHistory();
		ruleCommitHistory.setOpVersion(version+1);
		ruleCommitHistory.setOpTime(new Date());
		ruleCommitHistory.setOpUser(ShiroUtils.getUserId().intValue());
		ruleCommitHistory.setOpType(OperationEnum.UPDATE.getValue());
		ruleCommitHistory.setRuleId(detectionRule.getRuleId());
		ruleCommitHistoryService.save(ruleCommitHistory);
		return detectionRule;
	}

	@Override
	public Map<String,Object> queryDetection(Integer queryDetection) {
		Map<String,Object> result = new HashMap<String,Object>();
		RuleVersion ruleVersion = ruleVersionService.list().get(0);
		result.put("version", ruleVersion.getVersion());
		result.put("addList", ListUtil.list(true));
		result.put("deleteIds", ListUtil.list(true));
		result.put("updateList", ListUtil.list(true));
		if(ObjectUtil.isEmpty(ruleVersion) || (ObjectUtil.isNotEmpty(queryDetection) && queryDetection >= ruleVersion.getVersion())) {
			return result;
		}
		if(ObjectUtil.isNotEmpty(queryDetection)) {
			// 查询比当前版本打的操作记录
			List<RuleCommitHistory> changeLists = ruleCommitHistoryService.list(new QueryWrapper<RuleCommitHistory>().lambda()
					.gt(RuleCommitHistory::getOpVersion, queryDetection));
			// 对操作数据进行分类 
			Map<String, List<RuleCommitHistory>> typeLists = changeLists.stream().collect(Collectors.groupingBy(RuleCommitHistory::getOpType));
			List<RuleCommitHistory> addRuleHistories = typeLists.get(OperationEnum.ADD.getValue());
			List<RuleCommitHistory> updateRuleHistories = typeLists.get(OperationEnum.UPDATE.getValue());
			List<RuleCommitHistory> deleteRuleHistories = typeLists.get(OperationEnum.DELETE.getValue());
			if(ObjectUtil.isEmpty(deleteRuleHistories)) {
				deleteRuleHistories = ListUtil.list(true);
			}
			if(ObjectUtil.isEmpty(addRuleHistories)) {
				addRuleHistories = ListUtil.list(true);
			}
			if(ObjectUtil.isEmpty(updateRuleHistories)) {
				updateRuleHistories = ListUtil.list(true);
			}
			// 删除规则的ID信息
			List<Integer> deleteIds = deleteRuleHistories.stream().map(RuleCommitHistory::getRuleId).collect(Collectors.toList());
			result.put("deleteIds", deleteIds);
			List<Integer> addIds = addRuleHistories.stream().map(RuleCommitHistory::getRuleId).collect(Collectors.toList());
			List<Integer> updateIds = updateRuleHistories.stream().map(RuleCommitHistory::getRuleId).collect(Collectors.toList());
			// 修改操作仅限修改
			List<Integer> handlerUpdateIds = updateIds.stream().filter(uId -> !addIds.contains(uId)).filter(uId -> !deleteIds.contains(uId)).collect(Collectors.toList());
			// 新增去除已删除
			List<Integer> handlerAddIds = addIds.stream().filter(aId -> !deleteIds.contains(aId)).collect(Collectors.toList());
			if(ObjectUtil.isNotEmpty(handlerUpdateIds)) {
				List<DetectionRule> updateRules = this.baseMapper.selectBatchIds(handlerUpdateIds);
				handlerRuleData(updateRules);
				result.put("updateList", updateRules);
			}
			if(ObjectUtil.isNotEmpty(handlerAddIds)) {
				List<DetectionRule> addRules = this.baseMapper.selectBatchIds(handlerAddIds);
				handlerRuleData(addRules);
				result.put("addList", addRules);
			}
		}else {
			// 未传入版本信息时 返回所有未被删除规则数据
			List<DetectionRule> rules = this.list();
			List<DetectionRule> addRules = rules.stream().filter(rule -> rule.getIsValid() == 1).collect(Collectors.toList());
			handlerRuleData(addRules);
			result.put("addList", addRules);
		}
		return result;
	}

	@Override
	public Map<String, Object> statistics() {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("statusList", new ArrayList<Map<String,Object>>());
		result.put("eventTypeList", new ArrayList<Map<String,Object>>());
		result.put("categoryList", new ArrayList<Map<String,Object>>());
		List<DetectionRule> rules = this.list(new QueryWrapper<DetectionRule>().lambda().eq(DetectionRule::getIsValid, 1));
		if(ObjectUtil.isNotEmpty(rules)) {
			// 各状态以及个数的统计信息
			Map<Integer, List<DetectionRule>> statusRules = rules.stream().collect(Collectors.groupingBy(DetectionRule::getStatus));
			for(Integer status : statusRules.keySet()) {
				Map<String,Object> sr = new HashMap<String,Object>();
				sr.put("status", status);
				sr.put("count", statusRules.get(status).size());
				((List<Map<String,Object>>)result.get("statusList")).add(sr);
			}
			// 各类型以及个数的统计信息
			Map<String, List<DetectionRule>> typeRules = rules.stream().collect(Collectors.groupingBy(DetectionRule::getEventType));
			for(String type : typeRules.keySet()) {
				Map<String,Object> tr = new HashMap<String,Object>();
				tr.put("name", type);
				tr.put("count", typeRules.get(type).size());
				((List<Map<String,Object>>)result.get("eventTypeList")).add(tr);
			}
			// 各分类以及个数的统计信息
			Map<String, List<DetectionRule>> categoryRules = rules.stream().collect(Collectors.groupingBy(DetectionRule::getCategory));
			for(String category : categoryRules.keySet()) {
				Map<String,Object> cr = new HashMap<String,Object>();
				cr.put("name", category);
				cr.put("count", categoryRules.get(category).size());
				((List<Map<String,Object>>)result.get("categoryList")).add(cr);
			}
		}
		return result;
	}

	@Override
	public void handlerRuleData(List<DetectionRule> detectionRules) {
		Set<Integer> knowledgeBaseIds = new HashSet<Integer>();
		detectionRules.stream().forEach(rule -> {
			String ruleConfig = rule.getRuleConfig();
			Map<String,Object> ruleConfigObj = (Map<String, Object>) JSONObject.parse(ruleConfig);
			rule.setRuleConfigObj(ruleConfigObj);
			String ruleTrigger = rule.getRuleTrigger();
			Map<String,Object> ruleTriggerObj = (Map<String, Object>) JSONObject.parse(ruleTrigger);
			rule.setRuleTriggerObj(ruleTriggerObj);
			Integer knowledgeId = (Integer) ruleConfigObj.get("knowledgeId");
			knowledgeBaseIds.add(knowledgeId);
			rule.setKnowledgeId(knowledgeId);
		});
		if(ObjectUtil.isNotEmpty(knowledgeBaseIds)) {
			Collection<KnowledgeBase> knowledgeBases = knowledgeBaseService.listByIds(knowledgeBaseIds);
			Map<Integer, KnowledgeBase> idKnowledge = knowledgeBases.stream().collect(Collectors.toMap(KnowledgeBase::getKnowledgeId, Function.identity()));
			detectionRules.stream().forEach(rule -> {
				Map<String,Object> ruleConfigObj = (Map<String, Object>) rule.getRuleConfigObj();
				ruleConfigObj.put("knowledgeBase", idKnowledge.get(rule.getKnowledgeId()));
			});
		}
	}
}
