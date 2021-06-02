/**

 *

 *

 */

package net.geedge.modules.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.ObjectUtil;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.entity.SysDictEntity;
import net.geedge.modules.sys.service.SysDictService;


@RestController
@RequestMapping("sys/dict")
public class SysDictController extends AbstractController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @GetMapping()
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.queryPage(params);
        R re = R.ok(page);
        return re;
    }

    /**
     * 信息
     */
    @RequestMapping("/{id}")
    public R info(@PathVariable("id") Long id){
        SysDictEntity dict = sysDictService.queryInfo(id);
        return R.ok(dict);
    }

    /**
     * 保存
     */
    @PostMapping()
    public R save(@RequestBody SysDictEntity dict){
        ValidateUtils.is(dict.getType()).notNull(RCode.SYS_DICT_TYPE_ISNULL);
        ValidateUtils.is(dict.getValue()).notNull(RCode.SYS_DICT_VALUE_ISNULL);
        sysDictService.saveSysDict(dict);
        return R.ok().putData("id", dict.getId());
    }

    /**
     * 修改
     */
    @PutMapping()
    public R update(@RequestBody SysDictEntity dict){
        //校验类型
        ValidateUtils.is(dict.getType()).notNull(RCode.SYS_DICT_TYPE_ISNULL);
        ValidateUtils.is(dict.getValue()).notNull(RCode.SYS_DICT_VALUE_ISNULL);
        ValidateUtils.is(dict.getCode()).notNull(RCode.SYS_DICT_CODE_ISNULL);
        //字典名称，非必填项，为空后台自动添加value值
        if(StringUtils.isBlank(dict.getName())){
            dict.setName(dict.getValue());
        }
        if(Tool.ObjectUtil.isNotEmpty(dict.getCode())) {
			SysDictEntity one = sysDictService.getOne(new QueryWrapper<SysDictEntity>().lambda().eq(SysDictEntity::getType, dict.getType()).eq(SysDictEntity::getCode, dict.getCode()).ne(ObjectUtil.isNotEmpty(dict.getId()), SysDictEntity::getId,dict.getId()));
			if(Tool.ObjectUtil.isNotEmpty(one)) {
				throw new CNException(RCode.SYS_DICT_CODE_EXISTS);
			}
		}
        dict.setUby(getUserId());
        dict.setUtime(new Date());
        sysDictService.update(dict);
        return R.ok().putData("id", dict.getId());
    }

    /**
     * 删除
     */
    @DeleteMapping()
    public R delete(@RequestParam String ids){
    	ValidateUtils.is(ids).notNull(RCode.SYS_DICT_ID_ISNULL);
        sysDictService.delete(ids);
        return R.ok();
    }
}
