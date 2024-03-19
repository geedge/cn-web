package net.geedge.modules.sys.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import net.geedge.common.utils.Constant;
import net.geedge.modules.sys.dao.SysComponentDao;
import net.geedge.modules.sys.entity.SysComponent;
import net.geedge.modules.sys.service.SysComponentService;

@Service
public class SysComponentServiceImpl extends ServiceImpl<SysComponentDao, SysComponent> implements SysComponentService {

	@Autowired
	private SysComponentDao componentDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateComponent(SysComponent component) {
		// 先删除 后添加
 		componentDao.deleteById(component.getServerid());
		componentDao.insert(component);
	}
}
