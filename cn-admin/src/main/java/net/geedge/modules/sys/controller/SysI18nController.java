package net.geedge.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.entity.SysI18nEntity;
import net.geedge.modules.sys.service.SysDictService;
import net.geedge.modules.sys.service.SysI18nService;
import net.geedge.modules.sys.shiro.ShiroUtils;


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
    	ValidateUtils.is(id).notNull(RCode.SYS_I18N_ID_ISNULL);
    	SysI18nEntity byId = sysI18nService.queryInfo(id);
    	return R.ok(byId);
    }
    
    /**
     * 保存
     */
    @PostMapping
    @SysLog(operation =OperationEnum.ADD,type = TypeEnum.I18N)
    public R save(@RequestBody SysI18nEntity sysI18n){
    	ValidateUtils.is(sysI18n.getCode()).notNull(RCode.SYS_I18N_CODE_ISNULL)
    				 .and(sysI18n.getLang()).notNull(RCode.SYS_I18N_LANG_ISNULL)
    				 .and(sysI18n.getValue()).notNull(RCode.SYS_I18N_VALUE_ISNULL)
    				 .and(sysI18n.getName()).notNull(RCode.SYS_I18N_NAME_ISNULL);
    	if (sysI18nService.check(sysI18n)) { //检查是否重复，true为重复
			throw new CNException(RCode.SYS_I18N_DUPLICATE);
    	} else {
    		sysI18n.setUtime(new Date());
    		sysI18n.setUby(ShiroUtils.getUserId());
    		sysI18nService.save(sysI18n);
    		return R.ok();
    	}
    }

	/**
     * 修改
     */
    @PutMapping
    @SysLog(operation =OperationEnum.UPDATE,type = TypeEnum.I18N)
    public R update(@RequestBody SysI18nEntity sysI18n){
    	ValidateUtils.is(sysI18n.getCode()).notNull(RCode.SYS_I18N_CODE_ISNULL)
					 .and(sysI18n.getLang()).notNull(RCode.SYS_I18N_LANG_ISNULL)
					 .and(sysI18n.getValue()).notNull(RCode.SYS_I18N_VALUE_ISNULL)
					 .and(sysI18n.getId()).notNull(RCode.SYS_I18N_ID_ISNULL)
					 .and(sysI18n.getName()).notNull(RCode.SYS_I18N_NAME_ISNULL);
    	if (sysI18nService.check(sysI18n)) { //检查是否重复，true为重复
			throw new CNException(RCode.SYS_I18N_DUPLICATE);
    	} else {
	    	sysI18n.setUtime(new Date());
	    	sysI18n.setUby(ShiroUtils.getUserId());
			sysI18nService.updateById(sysI18n);
			return R.ok();
    	}
    }

    /**
     * 删除
     */
    @DeleteMapping
    @SysLog(operation =OperationEnum.DELETE,type = TypeEnum.I18N)
    public R delete(@RequestParam String ids){
    	ValidateUtils.is(ids).notNull(RCode.SYS_I18N_ID_ISNULL);
    	sysI18nService.removeByIds(Arrays.asList(ids.split(",")));
		return R.ok();
    }

    @GetMapping("/lang")
    public R lang(@RequestParam String l) {
    	ValidateUtils.is(l).notNull(RCode.SYS_I18N_PARAM_ISNULL);
    	Map result = sysI18nService.selectLang(l);
    	return R.ok(result);
    }
}
