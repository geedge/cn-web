package net.geedge.modules.panel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.panel.entity.VisualChart;

@Mapper
public interface VisualChartDao extends BaseMapper<VisualChart> {

	List<VisualChart> queryPage(IPage<VisualChart> page, @Param("params")Map<String, Object> params);
	
	VisualChart selectInfo(@Param("id")Integer id);
	
	List<VisualChart> queryList(@Param("param")Map<String, Object> param);
}
