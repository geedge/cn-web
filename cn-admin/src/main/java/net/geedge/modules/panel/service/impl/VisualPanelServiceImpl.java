package net.geedge.modules.panel.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.dao.VisualPanelDao;
import net.geedge.modules.panel.entity.VisualChart;
import net.geedge.modules.panel.entity.VisualPanel;
import net.geedge.modules.panel.service.VisualChartService;
import net.geedge.modules.panel.service.VisualPanelService;
import net.geedge.modules.sys.shiro.ShiroUtils;


@Service("visualPanelService")
public class VisualPanelServiceImpl extends ServiceImpl<VisualPanelDao, VisualPanel> implements VisualPanelService {

	private Log log = Log.get();

	@Autowired
	private VisualChartService visualChartService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
		IPage<VisualPanel> page=this.page(new Query(VisualPanel.class).getPage(params));

		List<VisualPanel> list=this.baseMapper.queryPage(page,params);
		page.setRecords(list);
		return new PageUtils(page);
	}

	@Override
	public VisualPanel queryInfo(Integer id) {
		return this.baseMapper.selectInfo(id);
	}

	@Override
	public Integer saveOrUpdatePanel(VisualPanel panel) {
		validatePanel(panel);
		if(ObjectUtil.isEmpty(panel.getId())) {
			panel.setCby(ShiroUtils.getUserId());
			panel.setCtime(new Date());
		}
		panel.setUby(ShiroUtils.getUserId());
		panel.setUtime(new Date());
		this.saveOrUpdate(panel);
		return panel.getId();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removePanel(String ids) {
		// 内置panel不删除
		validateBuildInPanels(Arrays.asList(ids.split(",")));
		
		// 删除panel
		this.removeByIds(Arrays.asList(ids.split(",")));
		
		// 删除关联chart
        List<VisualChart> charts = visualChartService.list(new QueryWrapper<VisualChart>().lambda().in(VisualChart::getPanelId, Arrays.asList(ids.split(","))));
        if(ObjectUtil.isNotEmpty(charts)) {
        	String chartIds = charts.stream().map(chart -> chart.getId().toString()).collect(Collectors.joining(","));
        	visualChartService.removeChart(chartIds);
        }
	}
	
	/**
     * 校验 panel 相关参数是否正确
     *
     * @param panel
     */
    public void validatePanel(VisualPanel panel) {
        String name = panel.getName();
        ValidateUtils.is(name).notNull(RCode.PANEL_NAME_ISNULL)
        .and(panel.getType()).notNull(RCode.PANEL_TYPE_ISNULL).max(6, RCode.PANEL_TYPE_INVALIDE).min(1, RCode.PANEL_TYPE_INVALIDE);

        // 校验name是否重复
        List<VisualPanel> list = this.list(new QueryWrapper<VisualPanel>().lambda().eq(VisualPanel::getName, panel.getName()).ne(ObjectUtil.isNotEmpty(panel.getId()),VisualPanel::getId,panel.getId()));
        boolean flag = false;
        //当只有一个结果并且这个结果是自身时，或没有结果时，说明名称不重复
        if (ObjectUtil.isNotEmpty(list)) {
            flag = true;
        }
        if (flag) {
            throw new CNException(RCode.PANEL_NAME_DUPLICATE);
        }
    }
	
	/**
     * 校验内置 panel 不可删除
     * @param panels
     * @param removeIds
     */
    private void validateBuildInPanels(List<String> removeIdList) {
        // 内置 panel 不能删除
        List<VisualPanel> list = this.list(new QueryWrapper<VisualPanel>().lambda().in(VisualPanel::getId, removeIdList).eq(VisualPanel::getBuildIn, 1));
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> panelNames = list.stream().map(VisualPanel::getName).collect(Collectors.toList());
            throw new CNException("These panel is built-in and cannot be deleted, panels info: " + panelNames.toString(), RCode.PANEL_BUILDIN_CAN_NOT_REMOVE.getCode());
        }
    }
    
}
