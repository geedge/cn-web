package net.geedge.modules.report.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.report.dao.ReportTempDao;
import net.geedge.modules.report.entity.ReportJob;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.entity.ReportTemp;
import net.geedge.modules.report.service.ReportJobService;
import net.geedge.modules.report.service.ReportTaskService;
import net.geedge.modules.report.service.ReportTempService;

@Service("reportTempService")
public class ReportTempServiceImpl extends ServiceImpl<ReportTempDao, ReportTemp> implements ReportTempService {
	@Autowired
	private ReportJobService reportJobService;
	
	@Autowired
	private ReportTaskService reportTaskService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage page = new Query(ReportTemp.class).getPage(params);
		List<ReportTemp> data = this.baseMapper.queryPage(page,params);
		page.setRecords(data);
		return new PageUtils(page);
	}

	@Override
	public void removeReportTemps(String ids) {
		List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
		this.remove(new QueryWrapper<ReportTemp>().lambda().in(ReportTemp::getId, idList));
	}

	@Override
	public void saveOrModify(ReportTemp reportTemp) {
		ValidateUtils.is(reportTemp.getName()).notNull(RCode.REPORT_TEMP_NAME_ISNULL)
				.and(reportTemp.getHtmlContent()).notNull(RCode.REPORT_TEMP_HTML_CONTENT_ISNULL)
				.and(reportTemp.getConfig()).notNull(RCode.REPORT_TEMP_CONFIG_ISNULL);
		
		// 生成唯一uuid标识
		String uuid = UUID.randomUUID().toString();
		if(ObjectUtils.isEmpty(reportTemp.getUniq())) {
			reportTemp.setUniq(uuid);
		}
		if(ObjectUtils.isEmpty(reportTemp.getCtime())) {
			reportTemp.setCtime(new Date());
		}
		reportTemp.setUtime(new Date());
		reportTemp.setState(1);
		
		//批量保存 job 以及 task 信息
		this.saveOrUpdate(reportTemp);
		
	}
}
