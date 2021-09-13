package net.geedge.modules.galaxy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.galaxy.entity.GalaxyProxy;

@Mapper
public interface GalaxyProxyDao extends BaseMapper<GalaxyProxy> {

    List<GalaxyProxy> queryPage(IPage<GalaxyProxy> page, @Param("params") Map<String, Object> params);

    GalaxyProxy selectInfo(@Param("id") Integer id);

    void insertGalaxyProxyList(List<GalaxyProxy> galaxyProxyList);

    void deleteTable();

}
