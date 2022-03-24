package net.geedge.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.report.entity.ReportTemp;

@Mapper
public interface ReportTempDao extends BaseMapper<ReportTemp>{

	List<ReportTemp> queryPage(IPage page, Map<String,Object> params);
	
}
