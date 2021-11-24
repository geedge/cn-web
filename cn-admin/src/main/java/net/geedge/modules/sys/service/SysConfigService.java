/**

 *

 *

 */

package net.geedge.modules.sys.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.Map;

/**
 * 系统配置信息
 *

 */
public interface SysConfigService extends IService<SysConfigEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
	/**
	 * 保存配置信息
	 */
	void saveConfig(SysConfigEntity config);
	
	/**
	 * 更新配置信息
	 */
	void update(SysConfigEntity config);
	
	/**
	 * 根据key，更新value
	 */
	void updateValueByKey(String key, String value);
	
	/**
	 * 删除配置信息
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 */
	String getValue(String key);

	/**
	 * 查询系统相关value
	 */
	JSONObject queryConfig(String paramKey);
	/**
	 * 更新系统相关
	 */
	int updateConfig(String paramKey, JSONObject paramValue);
	/**
	 * 测试邮箱
	 */
	boolean testEmail(JSONObject email);
	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
	<T> T getConfigObject(String key, Class<T> clazz);

	/*Map getConfigListByTypes(String[] types, String[] paramKey);*/

}
