package net.geedge.modules.location.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.location.entity.UserSubscriber;

@Mapper
public interface UserSubscriberDao extends BaseMapper<UserSubscriber>{
	
	List<UserSubscriber> queryPage(IPage<UserSubscriber> page, @Param("params")Map<String, Object> params);
	
}
