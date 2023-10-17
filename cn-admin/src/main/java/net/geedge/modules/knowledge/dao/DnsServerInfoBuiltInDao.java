package net.geedge.modules.knowledge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface DnsServerInfoBuiltInDao extends BaseMapper<DnsServerInfoBuiltIn>{

    Map<String, Object> queryIpEntityTag(@Param("resource") String resource);
}
