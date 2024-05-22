package net.geedge.modules.location.controller;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.location.entity.UserSubscriber;
import net.geedge.modules.location.service.LocationIntelligenceService;
import net.geedge.modules.location.service.UserSubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
	public R addFollow(@RequestBody UserSubscriber userSubscriber) {
		ValidateUtils.is(userSubscriber.getSubscriberId()).notNull(RCode.NOT_NULL_ERROR);
		userSubscriberService.insertFollow(userSubscriber);
		return R.ok();
	}
	
	@DeleteMapping("/follow")
	public R removeFollow(@RequestParam String subscriberId) {
		ValidateUtils.is(subscriberId).notNull(RCode.NOT_NULL_ERROR);
		userSubscriberService.deleteFollow(subscriberId);
		return R.ok();
	}

	@GetMapping("/geolocation")
	public R geolocation(@RequestParam Double longitude,@RequestParam Double latitude) {
		return locationIntelligenceService.geolocation(longitude,latitude);
	}
}
