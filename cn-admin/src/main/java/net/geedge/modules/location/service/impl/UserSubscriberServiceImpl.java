package net.geedge.modules.location.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssssssss.magicapi.core.service.MagicAPIService;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.location.dao.UserSubscriberDao;
import net.geedge.modules.location.entity.SubscriberDto;
import net.geedge.modules.location.entity.UserSubscriber;
import net.geedge.modules.location.service.UserSubscriberService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service
public class UserSubscriberServiceImpl extends ServiceImpl<UserSubscriberDao, UserSubscriber> implements UserSubscriberService {
	@Autowired
	private MagicAPIService magicAPIService;
	
	@Override
	public PageUtils getFollowedList(Map<String, Object> params) {
		String sTime = (String) params.get("startTime");
		String eTime = (String) params.get("endTime");
		ValidateUtils.is(sTime).notNull(RCode.NOT_NULL_ERROR);
		ValidateUtils.is(eTime).notNull(RCode.NOT_NULL_ERROR);
		IPage<UserSubscriber> page=this.page(new Query(UserSubscriber.class).getPage(params));
		List<UserSubscriber> userSubscribers=this.baseMapper.queryPage(page,params);
		page.setRecords(userSubscribers);
		if(ObjectUtil.isNotEmpty(userSubscribers)) {
			// 通过QGW服务获取状态信息
			List<String> ids = userSubscribers.stream().map(UserSubscriber::getSubscriberId).collect(Collectors.toList());
			String subscriberIds = ids.stream().map(s -> "'" + s + "'").collect(Collectors.joining(", "));
			params.put("subscriberIds", subscriberIds);
			params.put("startTime", Long.valueOf(sTime));
			params.put("endTime", Long.valueOf(eTime));
			LinkedHashMap results = magicAPIService.execute("Get", "/v1/locationIntelligence/followed/status", params);
			Object data = results.get("data");
			if(ObjectUtil.isNotEmpty(data)) {
				List<SubscriberDto> subscriberDtos = JSON.parseArray(JSON.toJSONString(data), SubscriberDto.class);
				Map<String, SubscriberDto> idDto = subscriberDtos.stream().collect(Collectors.toMap(SubscriberDto::getSubscriberId, Function.identity()));
				userSubscribers.forEach(us -> {
					if(ObjectUtil.isNotEmpty(idDto.get(us.getSubscriberId()))) {
						us.setActive(1);
					}
				});
			}
		}
		return new PageUtils(page);
	}

	@Override
	public void insertFollow(UserSubscriber userSubscriber) {
		Long userId = ShiroUtils.getUserId();
		userSubscriber.setCtime(new Date());
		userSubscriber.setUtime(new Date());
		userSubscriber.setUserId(userId);
		this.baseMapper.insert(userSubscriber);
	}

	@Override
	public void deleteFollow(String subscriberId) {
		this.baseMapper.delete(new QueryWrapper<UserSubscriber>().lambda().eq(UserSubscriber::getSubscriberId, subscriberId));
	}
}
