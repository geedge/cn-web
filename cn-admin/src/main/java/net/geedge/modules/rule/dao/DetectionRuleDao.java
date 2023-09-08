package net.geedge.modules.rule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.rule.entity.DetectionRule;


@Mapper
public interface DetectionRuleDao extends BaseMapper<DetectionRule> {

    List<DetectionRule> queryPage(IPage<DetectionRule> page,  @Param("params")Map<String, Object> params);
}
