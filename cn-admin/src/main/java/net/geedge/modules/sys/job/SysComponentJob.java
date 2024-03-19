package net.geedge.modules.sys.job;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Tool.NetUtil;
import net.geedge.modules.sys.entity.SysComponent;
import net.geedge.modules.sys.service.SysComponentService;
import net.geedge.modules.sys.service.SysConfigService;

/**
 * 系统组件 状态检查线程 1、更新当前组件的状态 a、当设置了host时，直接使用 host b、没有设置host时，通过socket 连接 数据库
 * 获取local address 2、主进程 a、清除超过两个周期没有更新的记录
 * 是否在sys_component 存在，不存在更新
 * 
 */
@DisallowConcurrentExecution
public class SysComponentJob extends QuartzJobBean {
	private static final Log log = Log.get();

	@Autowired
	private SysComponentService sysComponentService;
	
	@Value("${server.port}")
	private Integer port;
	@Value("${database.host}")
	private String databaseHost;
	@Value("${database.port}")
	private String databasePort;
	
	@Override
	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Thread.currentThread().setName("SysComponentJob");
		// 获取本机ip
		String localHost = NetUtil.getLocalIPBySocket(databaseHost, Integer.valueOf(databasePort));
		String protocol = "http";
		// 正确得到本机ip，更新 sys_component 表
		if (StrUtil.isNotBlank(localHost)) {
			SysComponent component = new SysComponent();
			component.setHost(localHost);
			component.setPort(port);
			component.setProtocol(protocol);
			component.setComponent(Constant.CN_WEB_COMPONENT_NAME);
			String hostName = StrUtil.EMPTY;
			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				log.warn("Failed to get hostname, host: {}", localHost);
			}
			component.setHostname(hostName);
			SysComponent sysComponent = sysComponentService.getOne(Wrappers.lambdaQuery(component));
			component.setServerid(Constant.SERVER_ID);
			component.setUtime(System.currentTimeMillis());
			if(sysComponent != null) {
				component.setId(sysComponent.getId());
			}
			sysComponentService.saveOrUpdate(component);
		}
		// 删除超过3min没有更新的component
		long ltime = System.currentTimeMillis() - 3 * 60 * 1000;
		sysComponentService.remove(Wrappers.lambdaQuery(new SysComponent()).lt(SysComponent::getUtime, ltime));
	}
}
