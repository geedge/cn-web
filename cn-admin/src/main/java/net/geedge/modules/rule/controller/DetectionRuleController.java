package net.geedge.modules.rule.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.rule.entity.DetectionRule;
import net.geedge.modules.rule.service.DetectionRuleService;

@RestController
@RequestMapping("/v1/rule/detection")
public class DetectionRuleController {
	@Autowired
	private DetectionRuleService detectionRuleService;
	
	@GetMapping
	public R detectionRuleList(@RequestParam Map<String,Object> params) {
		List<DetectionRule> detectionRules = detectionRuleService.getList(params);
		return R.ok(detectionRules);
	}
	
	@GetMapping("/{ruleId}")
	public R detectionRuleInfo(@PathVariable Integer ruleId) {
		DetectionRule detectionRule = detectionRuleService.getInfoById(ruleId);
		return R.ok(detectionRule);
	}
	
	@DeleteMapping
	public R removeRule(@RequestParam Integer... ruleIds) {
		ValidateUtils.is(ruleIds).notNull(RCode.RULE_ID_ISNULL);
		List<Integer> removeRuleIds = detectionRuleService.removeRules(Arrays.asList(ruleIds));
		return R.ok(removeRuleIds);
	}
}
