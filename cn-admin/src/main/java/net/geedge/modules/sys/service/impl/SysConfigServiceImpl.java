/**

 *

 *

 */

package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;

import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.sys.dao.SysConfigDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.redis.SysConfigRedis;
import net.geedge.modules.sys.service.SysConfigService;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {


    @Autowired
    private SysConfigRedis sysConfigRedis;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		IPage<SysConfigEntity> page = this.page(
			new Query(SysConfigEntity.class).getPage(params),
			new QueryWrapper<SysConfigEntity>()
				.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
				.eq("status", 1)
		);

		return new PageUtils(page);
	}
	
	@Override
	public void saveConfig(SysConfigEntity config) {
		this.save(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.getById(id);
			sysConfigRedis.delete(config.getParamKey());
		}

		this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
			config = baseMapper.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (IllegalAccessException e) {
            throw new CNException("获取参数失败");
        } catch (InstantiationException e) {
            throw new CNException("获取参数失败");
        }
    }
    @Override
    public Map getConfigListByTypes(String[] types, String[] paramKey) {

        List<String> paramKeys = paramKey == null || paramKey.length == 0 ? new ArrayList<>() : new ArrayList<>(Arrays.asList(paramKey));

        boolean typeFlag = false;
        if(types != null && types.length > 0){
            typeFlag = true;
            for (String type : types) {
                paramKeys.addAll(Constant.SYSCONFIG_TYPEWITHKEYS_MAP.get(type));
            }
        }
        
        List<SysConfigEntity> list = this.list(new QueryWrapper<SysConfigEntity>()
                .eq("status", 1)
                .in(CollectionUtils.isNotEmpty(paramKeys), "param_key", paramKeys));
        
        Map<String, String> paramKeyWithValueMap = new HashMap<>();
        for (SysConfigEntity entity: list) {
            paramKeyWithValueMap.put(entity.getParamKey(), entity.getParamValue());
        }


        // 组织返回数据
        Map resultMap = new HashMap(), values;

        // 如果type存在 则返回此type包含的所有内容
        if(typeFlag){
            List<String> keys;
            String value;
            for (String typeName : types) {
                values = new HashMap();

                keys = Constant.SYSCONFIG_TYPEWITHKEYS_MAP.get(typeName);
                for (String str : keys) {
                    value = paramKeyWithValueMap.get(str);
                    if(StringUtils.isNotEmpty(value)){
                        values.put(str, value);
                    }
                }
               
                resultMap.put(typeName, values);
            }
        }

        // 处理 paramKey参数查询
        if(paramKey != null && paramKey.length > 0){
            String value;
            Map paramValues = new HashMap();
            for (String key : paramKey) {
                value = paramKeyWithValueMap.get(key);
                if(StringUtils.isNotEmpty(value)){
                    paramValues.put(key, value);
                }
            }
            resultMap.put("paramKey",paramValues);
        }

        return resultMap;
    }

}
