package net.geedge.modules.rule.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		detectionRules.stream().forEach(rule -> rule.setIsValid(1));
		this.updateBatchById(detectionRules);
		return ids;
	}

	@Override
	public DetectionRule getInfoById(Integer id) {
		return this.getById(id);
	}

	@Override
	public List<DetectionRule> getList(Map<String, Object> params) {
		if (ObjectUtil.isNotEmpty(params.get("category"))) {
			params.put("category", Arrays.asList(String.valueOf(params.get("category")).split(StringPool.COMMA)));
		}
		if (ObjectUtil.isNotEmpty(params.get("eventType"))) {
			params.put("eventType", Arrays.asList(String.valueOf(params.get("eventType")).split(StringPool.COMMA)));
		}
		IPage page = this.page(new Query(DetectionRule.class).getPage(params));
		List<DetectionRule> detectionRules = this.baseMapper.queryPage(page, params);
		return detectionRules;
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
        DetectionRule rule = this.getOne(new QueryWrapper<DetectionRule>().lambda().eq(DetectionRule::getName, detectionRule.getName())
        		.ne(ObjectUtil.isNotEmpty(detectionRule.getRuleId()),DetectionRule::getRuleId,detectionRule.getRuleId()));
        if(ObjectUtils.isNotEmpty(rule)) {
        	throw new CNException(RCode.RULE_NAME_DUPLICATE);
        } 
        
		// config 格式校验
        String configStr = (String) detectionRule.getRuleConfig();
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
	public Map queryDetection(Integer queryDetection) {
		Map<String,Object> result = new HashMap<String,Object>();
		RuleVersion ruleVersion = ruleVersionService.list().get(0);
		result.put("version", ruleVersion.getVersion());
		if(ObjectUtil.isEmpty(ruleVersion) || (ObjectUtil.isNotEmpty(queryDetection) && queryDetection >= ruleVersion.getVersion())) {
			result.put("addList", ListUtil.list(true));
			result.put("deleteList", ListUtil.list(true));
			result.put("updateList", ListUtil.list(true));
			return result;
		}
		List<DetectionRule> rules = this.list();
		if(ObjectUtil.isNotEmpty(queryDetection)) {
			List<RuleCommitHistory> histories = ruleCommitHistoryService.list(new QueryWrapper<RuleCommitHistory>().lambda().le(RuleCommitHistory::getOpVersion, queryDetection));
			List<Integer> historiesRuleId = histories.stream().map(RuleCommitHistory::getRuleId).collect(Collectors.toList());
			List<Integer> ruleIds = rules.stream().map(DetectionRule::getRuleId).collect(Collectors.toList());
			//获取增量的规则信息
			List<Integer> addRuleIds = ruleIds.stream().filter(ruleId -> !historiesRuleId.contains(ruleId)).collect(Collectors.toList());
			//判断修改、删除的规则信息
			List<Integer> drRuleIds = ruleIds.stream().filter(ruleId -> historiesRuleId.contains(ruleId)).collect(Collectors.toList());
			List<DetectionRule> addRules = rules.stream().filter(rule -> addRuleIds.contains(rule.getRuleId())).filter(rule -> rule.getIsValid() != 1).collect(Collectors.toList());
			result.put("addList", addRules);
			
			if(ObjectUtil.isNotEmpty(drRuleIds)) {
				// 删除的规则信息
				List<DetectionRule> deleteRules = rules.stream().filter(rule -> rule.getIsValid().equals(1) && drRuleIds.contains(rule.getRuleId())).collect(Collectors.toList());
				result.put("deleteList", deleteRules);
				
				// 修改的规则信息
				List<DetectionRule> updateRules = rules.stream().filter(rule -> rule.getIsValid().equals(0)&&drRuleIds.contains(rule.getRuleId())).collect(Collectors.toList());
				// 同历史操作表对比获取修改数据信息
				List<Integer> updateIds = updateRules.stream().map(DetectionRule::getRuleId).collect(Collectors.toList());
				List<RuleCommitHistory> realUpdateRules = ruleCommitHistoryService.list(new QueryWrapper<RuleCommitHistory>().lambda().in(RuleCommitHistory::getRuleId, updateIds)
						.gt(RuleCommitHistory::getOpVersion, queryDetection));
				List<Integer> realUpdateIds = realUpdateRules.stream().map(RuleCommitHistory::getRuleId).collect(Collectors.toList());
				List<DetectionRule> updateRealRules = updateRules.stream().filter(rule -> realUpdateIds.contains(rule.getRuleId())).collect(Collectors.toList());
				result.put("updateList", updateRealRules);
			}
		}else {
			List<DetectionRule> addRules = rules.stream().filter(rule -> rule.getIsValid() == 0).collect(Collectors.toList());
			result.put("addList", addRules);
		}
		return result;
	}
}
