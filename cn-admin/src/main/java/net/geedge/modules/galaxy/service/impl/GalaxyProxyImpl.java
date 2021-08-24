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
import net.geedge.common.utils.Tool;
import net.geedge.modules.galaxy.dao.GalaxyProxyDao;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;

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
		//清空缓存
		Tool.FreeMarkerUtil.clearTemlateCache();
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
		.and(galaxyProxy.getTargetUrl()).notNull(RCode.GALAXY_PROXY_TARGET_URL_ISNULL);
		
		//设置默认值
		if(Tool.StrUtil.isBlank(galaxyProxy.getTargetParam())) {
			galaxyProxy.setTargetParam(Tool.StrUtil.EMPTY);
		}
		if(Tool.StrUtil.isBlank(galaxyProxy.getTargetHeader())) {
			galaxyProxy.setTargetHeader(Tool.StrUtil.EMPTY_JSON);
		}
		if(Tool.StrUtil.isBlank(galaxyProxy.getPreHandle())) {
			galaxyProxy.setPreHandle(Tool.StrUtil.EMPTY);
		}
		if(Tool.StrUtil.isBlank(galaxyProxy.getPostHandle())) {
			galaxyProxy.setPostHandle(Tool.StrUtil.EMPTY);
		}
	}
}
