package net.geedge.modules.sys.controller;

import net.geedge.common.config.I18nConfig;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.smartvalidate.utils.CommonUtil;
import net.geedge.common.utils.*;
import net.geedge.modules.sys.entity.SysI18nEntity;
import net.geedge.modules.sys.service.SysDictService;
import net.geedge.modules.sys.service.SysI18nService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * i18n
 * @author song
 * @date 2019-08-05 14:35:21
 */
@RestController
@RequestMapping("sys/i18n")
public class SysI18nController {
	
    @Autowired
    private SysI18nService sysI18nService;
    @Autowired
    private SysDictService dictService;
    @Autowired
    private I18nConfig i18nConfig;
    
    /**
     * 列表
     */
    @GetMapping
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysI18nService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    public R detail(@PathVariable Long id) {
    	ValidateUtils.is(id).notNull();
    	SysI18nEntity byId = sysI18nService.getById(id);
    	return R.ok(byId);
    }
    
    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody SysI18nEntity sysI18n){
    	ValidateUtils.is(sysI18n.getCode()).notNull()
    				 .and(sysI18n.getLang()).notNull()
    				 .and(sysI18n.getValue()).notNull();
    	if (!checkLang(sysI18n.getLang())) { //检查lang是否合法
    		throw new CNException(RCode.SYS_I18N_NOSUCHLANG);
    	}
    	if (sysI18nService.check(sysI18n)) { //检查是否重复，true为重复
			throw new CNException(RCode.SYS_I18N_DUPLICATE);
    	} else {
    		sysI18n.setOperator(ShiroUtils.getUserId());
    		sysI18n.setOpTime(new Date());
    		sysI18nService.save(sysI18n);
    		//刷新缓存
    		i18nConfig.reload();
    		return R.ok();
    	}
    }

	/**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody SysI18nEntity sysI18n){
    	ValidateUtils.is(sysI18n.getCode()).notNull()
					 .and(sysI18n.getLang()).notNull()
					 .and(sysI18n.getValue()).notNull()
					 .and(sysI18n.getId()).notNull();
    	if (!checkLang(sysI18n.getLang())) { //检查lang是否合法
			throw new CNException(RCode.SYS_I18N_NOSUCHLANG);
    	}
    	if (sysI18nService.check(sysI18n)) { //检查是否重复，true为重复
			throw new CNException(RCode.SYS_I18N_DUPLICATE);
    	} else {
	    	sysI18n.setOperator(ShiroUtils.getUserId());
	    	sysI18n.setOpTime(new Date());
			sysI18nService.updateById(sysI18n);
			//刷新缓存
			i18nConfig.reload();
			return R.ok();
    	}
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Long[] ids){
    	ValidateUtils.is(ids).notNull();
    	sysI18nService.removeByIds(Arrays.asList(ids));
    	//刷新缓存
    	i18nConfig.reload();
		return R.ok();
    }
    
    /**
     * 校验语种是否合法
     * @param lang
     * @return true:合法  false:不合法
     */
    private boolean checkLang(String lang) {
		if (CommonUtil.isNull(Constant.langList)) {
			dictService.updateLangCache();
		}
		for (Map<String, String> l : Constant.langList) {
			if (l.get("value").equals(lang)) {
				return true;
			}
		}
		return false;
	}
}
