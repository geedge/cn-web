package net.geedge.modules.location.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.location.entity.UserSubscriber;

public interface UserSubscriberService extends IService<UserSubscriber>{
	
	PageUtils getFollowedList(Map<String,Object> params);
	
	void insertFollow(UserSubscriber userSubscriber);
	
	void deleteFollow(String subscriberId);
}
