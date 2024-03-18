package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.modules.sys.entity.SysComponent;

public interface SysComponentService extends IService<SysComponent>{

	void updateComponent(SysComponent component);	
}
