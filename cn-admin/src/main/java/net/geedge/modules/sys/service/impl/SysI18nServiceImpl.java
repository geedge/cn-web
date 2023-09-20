package net.geedge.modules.sys.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.utils.CommonUtil;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.dao.SysI18nDao;
import net.geedge.modules.sys.entity.SysI18nEntity;
import net.geedge.modules.sys.service.SysDictService;
import net.geedge.modules.sys.service.SysI18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysI18nService")
public class SysI18nServiceImpl extends ServiceImpl<SysI18nDao, SysI18nEntity> implements SysI18nService {

	@Autowired
	private Environment env;
	@Autowired
    private SysDictService dictService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
    	IPage<SysI18nEntity> page=this.page(new Query(SysI18nEntity.class).getPage(params));

		List<SysI18nEntity> list=this.baseMapper.selectSysI18nListWithPage(page,params);

		page.setRecords(list);
        return new PageUtils(page);
    }

    public List<SysI18nEntity> queryAll() {
    	QueryWrapper<SysI18nEntity> qw = new QueryWrapper<>();
    	List<SysI18nEntity> list = list(qw);
    	return list;
    }
    
    public Locale getLocale() {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	//从session中获取lang
    	Locale locale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    	if (locale == null) {
    		String defaultLang = env.getProperty("default.locale", "en_US");
            String[] split = defaultLang.split("_");
            locale = new Locale(split[0], split[1]);
    	}
    	return locale;
    }
    
	@Override
	public boolean check(SysI18nEntity sysI18n) {
		boolean dumpFlag = true; //记录是否重复(code+lang)，true为重复
		List<SysI18nEntity> list = list(new QueryWrapper<SysI18nEntity>().lambda()
									.eq(true, SysI18nEntity::getCode, sysI18n.getCode())
									. eq(true, SysI18nEntity::getLang, sysI18n.getLang()).ne(ObjectUtil.isNotEmpty(sysI18n.getId()),SysI18nEntity::getId, sysI18n.getId()));
		//当只有一个结果并且这个结果是自身时，或没有结果时，说明不重复
		if (ObjectUtil.isEmpty(list)) {
			dumpFlag = false;
		}
		return dumpFlag;
	}

	@Override
	public SysI18nEntity queryInfo(Long id) {
		return this.baseMapper.selectInfo(id);
	}

	@Override
	public Map selectLang(String l) {
		Map result =new HashMap();
		List<String> langs = StrUtil.split(l, StrUtil.C_COMMA);
		if(ObjectUtil.isNotEmpty(langs)) {
			for(String lang:langs) {
				List<SysI18nEntity> datas = this.list(new QueryWrapper<SysI18nEntity>().lambda().eq(SysI18nEntity::getLang, lang));
				Map<String, String> collect = datas.stream().collect(Collectors.toMap(SysI18nEntity::getCode, SysI18nEntity::getValue));
				result.put(lang, collect);
			}
		}
		return result;
	}

	@Override
	public Long addAndReturnId(SysI18nEntity sysI18n) {
		this.saveOrUpdate(sysI18n);
		SysI18nEntity i18n = this.getOne(new QueryWrapper<SysI18nEntity>().lambda()
				.eq(true, SysI18nEntity::getCode, sysI18n.getCode())
				.eq(true, SysI18nEntity::getLang, sysI18n.getLang()));
		Long id = null;
		if (ObjectUtil.isNotEmpty(i18n)) {
			id = i18n.getId();
		}
		return id;
	}
}
