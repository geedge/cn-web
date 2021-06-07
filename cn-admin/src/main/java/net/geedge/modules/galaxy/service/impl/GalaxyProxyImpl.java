package net.geedge.modules.galaxy.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.galaxy.dao.GalaxyProxyDao;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;
import net.geedge.modules.panel.entity.VisualPanel;

@Service("galaxyProxyService")
public class GalaxyProxyImpl extends ServiceImpl<GalaxyProxyDao, GalaxyProxy> implements GalaxyProxyService {
	
	private Log log = Log.get();

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<GalaxyProxy> page=this.page(new Query(GalaxyProxy.class).getPage(params));
		List<GalaxyProxy> list=this.baseMapper.queryPage(page,params);
		page.setRecords(list);
		return new PageUtils(page);
	}

	@Override
	public GalaxyProxy queryInfo(Integer id) {
		return this.baseMapper.selectInfo(id);
	}

	@Override
	public Integer saveOrUpdateGalaxyProxy(GalaxyProxy galaxyProxy) {
		ValidateGalaxyProxy(galaxyProxy);
		if(ObjectUtil.isEmpty(galaxyProxy.getId())) {
			galaxyProxy.setCtime(new Date());
		}
		galaxyProxy.setUtime(new Date());
		this.saveOrUpdate(galaxyProxy);
		return galaxyProxy.getId();
	}

	@Override
	public void removeGalaxyProxy(String ids) {
		// 删除
		this.removeByIds(Arrays.asList(ids.split(",")));
	}
	
	public void ValidateGalaxyProxy(GalaxyProxy galaxyProxy) {
		ValidateUtils.is(galaxyProxy.getName()).notNull(RCode.GALAXY_PROXY_NAME_ISNULL)
		.and(galaxyProxy.getMethod()).notNull(RCode.GALAXY_PROXY_METHOD_ISNULL)
		.and(galaxyProxy.getTargetUrl()).notNull(RCode.GALAXY_PROXY_TARGET_URL_ISNULL)
		.and(galaxyProxy.getTargetParam()).notNull(RCode.GALAXY_PROXY_TARGET_PARAM_ISNULL)
		.and(galaxyProxy.getTargetHeader()).notNull(RCode.GALAXY_PROXY_TARGET_HEADER_ISNULL)
		.and(galaxyProxy.getPreHandle()).notNull(RCode.GALAXY_PROXY_PRE_HANDLE_ISNULL)
		.and(galaxyProxy.getPostHandle()).notNull(RCode.GALAXY_PROXY_POST_HANDLE_ISNULL);
	}
}
