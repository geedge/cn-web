package net.geedge.modules.report.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.json.JSONString;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportCategoryDao;
import net.geedge.modules.report.entity.ReportCategory;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportCategoryService;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;

@Service("reportCategoryService")
public class ReportCategoryServiceImpl extends ServiceImpl<ReportCategoryDao, ReportCategory> implements ReportCategoryService {
	
	private Log log = Log.get();
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage page = new Query(ReportTemp.class).getPage(params);
		List<ReportCategory> data = this.baseMapper.queryPage(page,params);
		for(ReportCategory reportCategory : data) {
			String config = reportCategory.getConfig();
			Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
			configObject.put("dataset", null);
			reportCategory.setConfig(JSON.toJSONString(configObject));
		}
		page.setRecords(data);
		return new PageUtils(page);
	}
	
	@Override
	public ReportCategory queryInfo(Integer id) {
		ReportCategory reportCategory = this.baseMapper.selectById(id);
		String config = reportCategory.getConfig();
		Map<String,Object> configObject = (Map<String, Object>) JSONObject.parse(config);
		configObject.put("dataset", null);
		reportCategory.setConfig(JSON.toJSONString(configObject));
		SysUserEntity user = sysUserService.getById(reportCategory.getUid());
		reportCategory.setSysUser(user);
		return reportCategory;
	}
	
	@Override
	public void removeReportCategorys(String ids) {
		List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
		// 判读是否为内置
		Collection<ReportCategory> categorys = this.listByIds(idList);
		for(ReportCategory category : categorys) {
			if(category.getBuildIn().equals(1)) {
				throw new CNException(RCode.REPORT_BUILD_IN_ERROR);
			}
		}
		this.remove(new QueryWrapper<ReportCategory>().lambda().in(ReportCategory::getId, idList));
	}
}
