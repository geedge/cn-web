package net.geedge.modules.panel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.panel.entity.VisualPanel;

@Mapper
public interface VisualPanelDao extends BaseMapper<VisualPanel> {
	
	List<VisualPanel> queryPage(IPage<VisualPanel> page, @Param("params")Map<String, Object> params);
	
	VisualPanel selectInfo(@Param("id")Integer id);
}
