package net.geedge.modules.report.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONString;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportCategoryDao;
import net.geedge.modules.report.entity.ReportCategory;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportCategoryService;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTempService;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;

@Service("reportCategoryService")
public class ReportCategoryServiceImpl extends ServiceImpl<ReportCategoryDao, ReportCategory> implements ReportCategoryService {
	
	private Log log = Log.get();
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private ReportTempService reportTempService;
	
	@Autowired
	private ReportJobService reportJobService;
	
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

	/**
	 * 报告类别进行配置同步 将temp以及job内容进行更新
	 * @param param
	 * @return
	 */
	@Override
	public void configSync(Map<String, Object> param) {
		String ids = (String) param.get("ids");
		if(StrUtil.isEmpty(ids)) {
			return;
		}
		List<String> categoryIds = Arrays.asList(ids.split(StringPool.COMMA));
		List<ReportCategory> categories = this.baseMapper.selectBatchIds(categoryIds);
		if(ObjectUtil.isEmpty(categories)) {
			log.debug("configSync: categories is empty");
			return;
		}
		List<ReportJob> handlerJobs = new ArrayList<ReportJob>();
		List<ReportTemp> handlerTemps = new ArrayList<ReportTemp>();
		for(ReportCategory category: categories) {
			// 对应category下的temp sql语句  html模板进行配置同步  不影响用户定义的temp定时相关的配置
			List<ReportTemp> temps = reportTempService.list(new QueryWrapper<ReportTemp>().lambda().eq(ReportTemp::getCategoryId, category.getId()));
			for(ReportTemp temp : temps) {
				temp.setHtmlContent(category.getHtmlContent());
				String config = temp.getConfig();
				Map<String,Object> tempConfigMap = (Map<String, Object>) JSONObject.parse(config);
				Map<String,Object> categoryConfig = (Map<String, Object>)JSON.parse(category.getConfig());
				tempConfigMap.put("dataset",categoryConfig.get("dataset"));
				temp.setConfig(JSON.toJSONString(tempConfigMap));
				//将对应job html内容进行同步
				List<ReportJob> jobs = reportJobService.list(new QueryWrapper<ReportJob>().lambda().eq(ReportJob::getUniq, temp.getUniq()));
				jobs.stream().forEach(job-> {
					job.setName(StrUtil.concat(false, temp.getName(),DateUtil.format(new Date(), "yyyyMMdd")));
					job.setHtmlContent(category.getHtmlContent());
					job.setUpload(0);
					job.setState(0);
				});
				if(ObjectUtil.isNotEmpty(jobs)) {
					handlerJobs.addAll(jobs);
				}
			}
			if(ObjectUtil.isNotEmpty(temps)) {
				handlerTemps.addAll(temps);
			}
		}
		if(ObjectUtil.isNotEmpty(handlerTemps)) {
			reportTempService.updateBatchById(handlerTemps);
		}
		if(ObjectUtil.isNotEmpty(handlerJobs)) {
			reportJobService.updateBatchById(handlerJobs);
		}
	}
}
