package net.geedge.modules.panel.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.dao.VisualChartDao;
import net.geedge.modules.panel.entity.VisualChart;
import net.geedge.modules.panel.entity.VisualPanel;
import net.geedge.modules.panel.service.VisualChartService;
import net.geedge.modules.panel.service.VisualPanelService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service("visualChartService")
public class VisualChartServiceImpl extends ServiceImpl<VisualChartDao, VisualChart> implements VisualChartService {

	private Log log = Log.get();
	@Autowired
	private VisualPanelService visualPanelService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		ValidateUtils.is(params.get("panelId")).notNull(RCode.PANEL_ID_ISNULL);
		IPage<VisualChart> page=this.page(new Query(VisualChart.class).getPage(params));
		List<VisualChart> charts=this.baseMapper.queryPage(page,params);
		charts = this.getChartTreeList(charts,0);
		page.setRecords(charts);
		return new PageUtils(page);
	}

	@Override
	public VisualChart queryInfo(Integer id) {
		return this.baseMapper.selectInfo(id);
	}

	@Override
	public Integer saveOrUpdateChart(VisualChart chart) {
		validateChart(chart);
		if(ObjectUtil.isEmpty(chart.getId())) {
			chart.setCby(ShiroUtils.getUserId());
			chart.setCtime(new Date());
		}
		chart.setUby(ShiroUtils.getUserId());
		chart.setUtime(new Date());
		this.saveOrUpdate(chart);
		return chart.getId();
	}

	@Override
	public void removeChart(String ids) {
		// 内置panel不删除
		validateBuildInCharts(Arrays.asList(ids.split(",")));

		// 删除panel
		this.removeByIds(Arrays.asList(ids.split(",")));
	}

	@Override
	public void layout(List<VisualChart> charts) {
		this.updateBatchById(charts);
	}

	@Override
	public PageUtils queryPageByQ(Map<String, Object> params) {
		IPage<VisualChart> page=this.page(new Query(VisualChart.class).getPage(params));
		List<VisualChart> charts=this.baseMapper.queryPageByQ(page,params);
		//组合树形结构
		//charts = this.getChartTreeList(charts,0);
		page.setRecords(charts);
		return new PageUtils(page);
	}


	/**
     * 校验是否是内置chart
     *
     * @param chartIds
     */
    private void validateBuildInCharts(List<String> chartIds) {
        // 内置charts
        List<VisualChart> list = this.list(new QueryWrapper<VisualChart>().lambda().eq(VisualChart::getBuildIn, 1).in(VisualChart::getId, chartIds));
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> chartNames = list.stream().map(VisualChart::getName).collect(Collectors.toList());
            throw new CNException("These chart is built-in and cannot be deleted, charts info: " + chartNames.toString(), RCode.CHART_BUILDIN_CAN_NOT_REMOVE.getCode());
        }
    }

	/**
	 * 校验 chart 信息
	 * @param chart
	 */
	public void validateChart(VisualChart chart) {
		Integer panelId = chart.getPanelId();
		ValidateUtils.is(panelId).notNull(RCode.PANEL_ID_ISNULL)
		.and(chart.getX()).notNull(RCode.CHART_X_ISNULL)
		.and(chart.getY()).notNull(RCode.CHART_Y_ISNULL)
		.and(chart.getW()).notNull(RCode.CHART_W_ISNULL)
		.and(chart.getH()).notNull(RCode.CHART_H_ISNULL)
		.and(chart.getName()).notNull(RCode.CHART_NAME_ISNULL)
		.and(chart.getType()).notNull(RCode.CHART_TYPE_ISNULL);
		// 校验panel是否存在
		validatePanel(panelId);
	}

	/**
     * 校验 panel 是否存在
     *
     * @param panelId
     */
    public void validatePanel(Integer panelId) {
    	if(panelId.equals(0)) {
    		return;
    	}
        VisualPanel panel = visualPanelService.getById(panelId);
        if (panel == null) {
            throw new CNException(RCode.CHART_PANEL_ISNULL);
        }
    }

    /**
     * 递归获取树结构
     *
     * @param chartList
     * @param parentId
     * @return
     */
    private List<VisualChart> getChartTreeList(List<VisualChart> chartList, Integer parentId) {
        List<VisualChart> resultList = new ArrayList<>();
        for (VisualChart chart : chartList) {
            Integer id = chart.getId();
            Integer pid = chart.getPid();
            if(parentId.equals(pid)) {
            	chart.setChildren(this.getChartTreeList(chartList, id));
                resultList.add(chart);
            }
        }
        return resultList;
    }
}
