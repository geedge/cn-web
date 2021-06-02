package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.sys.entity.SysI18nEntity;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * i18n信息表
 *
 * @author song
 * @email 
 * @date 2019-08-05 14:35:21
 */
public interface SysI18nService extends IService<SysI18nEntity> {

    PageUtils queryPage(Map<String, Object> params);

	boolean check(SysI18nEntity sysI18n);

	List<SysI18nEntity> queryAll();
		
	Locale getLocale();
	
	SysI18nEntity queryInfo(Long id);
	
	Map selectLang(String l);
	
}

