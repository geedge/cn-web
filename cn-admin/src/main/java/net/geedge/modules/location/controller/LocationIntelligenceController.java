package net.geedge.modules.location.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.location.service.LocationIntelligenceService;
import net.geedge.modules.location.service.UserSubscriberService;

@RestController
@RequestMapping("v1/locationIntelligence")
public class LocationIntelligenceController {
	@Autowired
	private UserSubscriberService userSubscriberService;

	@Autowired
	private LocationIntelligenceService locationIntelligenceService;
	
	@GetMapping("/{type}/basic/{subscriberId}")
	public R basicInfo(@PathVariable("type") Integer type,@PathVariable("subscriberId") String subscriberId) {
		return R.ok();
	}
	
	@GetMapping("/followed/subscribers")
	public R followedList(@RequestParam Map<String,Object> params) {
		PageUtils result = userSubscriberService.getFollowedList(params);
		return R.ok(result);
	}
	
	@PostMapping("/follow")
	public R addFollow(@RequestBody Map<String,String> param) {
		String subscriberIds = param.get("subscriberIds");
		ValidateUtils.is(subscriberIds).notNull(RCode.NOT_NULL_ERROR);
		userSubscriberService.insertFollow(subscriberIds);
		return R.ok();
	}
	
	@DeleteMapping("/follow")
	public R removeFollow(@RequestParam String subscriberIds) {
		ValidateUtils.is(subscriberIds).notNull(RCode.NOT_NULL_ERROR);
		userSubscriberService.deleteFollow(subscriberIds);
		return R.ok();
	}

	@GetMapping("/geolocation")
	public R geolocation(@RequestParam Double longitude,@RequestParam Double latitude) {
		return locationIntelligenceService.geolocation(longitude,latitude);
	}
}
