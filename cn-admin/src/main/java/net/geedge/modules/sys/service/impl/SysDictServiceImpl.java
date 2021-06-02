/**

 *

 *

 */

package net.geedge.modules.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.utils.CommonUtil;
import net.geedge.common.utils.*;
import net.geedge.modules.sys.dao.SysDictDao;
import net.geedge.modules.sys.entity.SysDictEntity;
import net.geedge.modules.sys.service.SysDictService;
import net.geedge.modules.sys.service.SysI18nService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {

	@Autowired
	private SysI18nService i18nService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
		IPage<SysDictEntity> page=this.page(new Query(SysDictEntity.class).getPage(params));

		List<SysDictEntity> list=this.baseMapper.querySysDictListWithPage(page,params);

		page.setRecords(list);
        return new PageUtils(page);
    }

	@Override
	public List<SysDictEntity> queryList(Map<String, Object> params) {
		List<SysDictEntity> list = this.baseMapper.querySysDictList(params);
		return list;
	}
	
	@Override
	public void delete(String ids) {
		if(Tool.ObjectUtil.isEmpty(ids)) {
			return;
		}
    	String[] idArr=ids.split(",");
    	ArrayList<Integer> delIds=new ArrayList<Integer>();
    	for(String idStr:idArr){
    		try {
    			Integer id = Integer.parseInt(idStr);
    			delIds.add(id);
			}catch (NumberFormatException e){
    			throw new CNException(RCode.SYS_DICT_ID_INVALIDE);
			}
		}

    	this.removeByIds(delIds);
    	
	}

	@Override
	public boolean save(SysDictEntity dict) {
		super.save(dict);
		return true;
	}
	
	@Override
	public boolean update(SysDictEntity dict) {
		SysDictEntity old =null;
    	if(dict.getId()!=null){
    		old = getById(dict.getId());
		}else{
    		old = this.baseMapper.selectOne(
    				new QueryWrapper<SysDictEntity>().lambda()
					.eq(SysDictEntity::getType,dict.getType())
					.eq(SysDictEntity::getCode,dict.getCode())
				  );
		}
		if(old == null){
			throw new CNException(RCode.SYS_ERROR);
		}

		dict.setId(old.getId());

		boolean langUpdate = false;
		//若更新了语言，刷新语言缓存
		if (dict.getType().equals(Constant.DICT_NAME_LANG) && !old.getValue().equals(dict.getValue())) {
			langUpdate = true;
		}

		updateById(dict);
		return true;
	}
	/**
	 *  name type 下 value 值不能重复
	 * @param id
	 * @param name   字典名称
	 * @param type   字典类型
	 * @param value  字典值
	 * @return
	 */
	@Override
	public boolean dictValueCheck(Long id, String name, String type, String value) {
		List<SysDictEntity> list = this.list(new QueryWrapper<SysDictEntity>().lambda().eq(SysDictEntity::getName, name).eq(SysDictEntity::getType, type).eq(SysDictEntity::getValue, value));

		boolean flag = false;
		if (list == null || list.size() == 0 || (list != null && list.size() == 1 && list.get(0).getId().equals(id))) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void checkSysDicValue(String type, String value, RCode errCode) {
		List<SysDictEntity> list = this.list(new QueryWrapper<SysDictEntity>().lambda().eq(SysDictEntity::getType, type).eq(SysDictEntity::getValue, value).eq(SysDictEntity::getState, 0));
		if(list==null ||list.size()<1){
			throw new CNException(errCode);
		}
	}
	@Override
	public void checkSysDicCode(String type, Integer code, RCode errCode) {
		List<SysDictEntity> list = this.list(new QueryWrapper<SysDictEntity>().lambda().eq(SysDictEntity::getType, type).eq(SysDictEntity::getCode, code).eq(SysDictEntity::getState, 0));
		if(list==null ||list.size()<1){
			throw new CNException(errCode);
		}
	}

	@Override
	public List<SysDictEntity> querySysDicByType(String type) {
		List<SysDictEntity> list = this.list(new QueryWrapper<SysDictEntity>().lambda().eq(SysDictEntity::getType, type).eq(SysDictEntity::getState, 0));
		return list;
	}

	@Override
	public void saveSysDict(SysDictEntity dict) {
		//字典名称，非必填项，为空后台自动添加value值
		if(StringUtils.isBlank(dict.getName())){
			dict.setName(dict.getValue());
		}
		//字典码，类型相同不能有相同code
		if(Tool.ObjectUtil.isNotEmpty(dict.getCode())) {
			SysDictEntity one = this.getOne(new QueryWrapper<SysDictEntity>().lambda().eq(SysDictEntity::getType, dict.getType()).eq(SysDictEntity::getCode, dict.getCode()).ne(ObjectUtil.isNotEmpty(dict.getId()), SysDictEntity::getId,dict.getId()));
			if(Tool.ObjectUtil.isNotEmpty(one)) {
				throw new CNException(RCode.SYS_DICT_CODE_EXISTS);
			}
		}else {
			throw new CNException(RCode.SYS_DICT_CODE_ISNULL);
		}
	
		Long userId = ShiroUtils.getUserId();
		dict.setUby(userId);
		dict.setUtime(new Date());
		this.save(dict);
	}

	@Override
	public SysDictEntity queryInfo(Long id) {
		return this.baseMapper.selectInfo(id);
	}
}
