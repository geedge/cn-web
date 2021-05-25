package net.geedge.modules.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.sys.entity.SysApiKey;

public interface SysApiKeyService extends IService<SysApiKey>{
	
	PageUtils queryPage(Map<String,Object> params);
	
	Integer addSysApiKey(SysApiKey sysApiKey);
	
	void removeSysApiKeys(String ids);
}
