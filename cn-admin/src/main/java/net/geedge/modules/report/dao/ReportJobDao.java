package net.geedge.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.report.entity.ReportJob;

@Mapper
public interface ReportJobDao extends BaseMapper<ReportJob>{

	List<ReportJob> queryPage(IPage page,Map<String,Object> params);
	
	ReportJob selectInfoById(@Param("id")Integer id);
	
	void deleteResult(@Param("ids")List<String> ids,@Param("type")Integer type,@Param("overview")Integer overview);
}
