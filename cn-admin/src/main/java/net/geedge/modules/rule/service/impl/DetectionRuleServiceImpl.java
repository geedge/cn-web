package net.geedge.modules.rule.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
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
	
	@Override
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
		List<RuleVersion> ruleVersions = ruleVersionService.list(new QueryWrapper<RuleVersion>().lambda().in(RuleVersion::getRuleId, ids));
		// 历史记录表数据填充
		List<RuleCommitHistory> ruleCommitHistories = new ArrayList<RuleCommitHistory>();
		for(RuleVersion ruleVersion : ruleVersions) {
			Integer version = ruleVersion.getVersion();
			ruleVersion.setVersion(version+1);
			ruleVersion.setOpTime(new Date());
			RuleCommitHistory ruleCommitHistory = new RuleCommitHistory();
			ruleCommitHistory.setOpUser(ShiroUtils.getUserId().intValue());
			ruleCommitHistory.setOpTime(new Date());
			ruleCommitHistory.setOpType(OperationEnum.DELETE.getValue());
			ruleCommitHistory.setOpVersion(version+1);
			ruleCommitHistory.setRuleId(ruleVersion.getRuleId());
			ruleCommitHistories.add(ruleCommitHistory);
		}
		ruleVersionService.updateBatchById(ruleVersions);
		ruleCommitHistoryService.saveBatch(ruleCommitHistories);
		this.remove(new QueryWrapper<DetectionRule>().lambda().in(DetectionRule::getRuleId,ids));
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
			params.put("source", Arrays.asList(String.valueOf(params.get("source")).split(StringPool.COMMA)));
		}
		IPage page = this.page(new Query(DetectionRule.class).getPage(params));
		List<DetectionRule> detectionRules = this.baseMapper.queryPage(page, params);
		return detectionRules;
	}

}
