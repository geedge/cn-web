package net.geedge.modules.report.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportCategoryDao;
import net.geedge.modules.report.entity.ReportCategory;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportCategoryService;

@Service("reportCategoryService")
public class ReportCategoryServiceImpl extends ServiceImpl<ReportCategoryDao, ReportCategory> implements ReportCategoryService {
	
	private Log log = Log.get();
	
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage page = new Query(ReportTemp.class).getPage(params);
		List<ReportCategory> data = this.baseMapper.queryPage(page,params);
		page.setRecords(data);
		return new PageUtils(page);
	}
	
	@Override
	public ReportCategory queryInfo(Integer id) {
		ReportCategory reportCategory = this.baseMapper.selectById(id);
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
