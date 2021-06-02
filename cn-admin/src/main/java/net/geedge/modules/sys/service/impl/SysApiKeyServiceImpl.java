package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysApiKeyDao;
import net.geedge.modules.sys.entity.SysApiKey;
import net.geedge.modules.sys.service.SysApiKeyService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service
public class SysApiKeyServiceImpl extends ServiceImpl<SysApiKeyDao,SysApiKey> implements SysApiKeyService{
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage result = new Query(SysApiKey.class).getPage(params);
		if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
		List<SysApiKey> rows = this.baseMapper.queryList(result, params);
		result.setRecords(rows);
		return new PageUtils(result);
	}

	@Override
	public Integer addSysApiKey(SysApiKey sysApiKey) {
		// 校验名称不重复
		List<SysApiKey> sysApiKeys = this.list(new QueryWrapper<SysApiKey>().lambda().eq(SysApiKey::getName, sysApiKey.getName()));
		if(Tool.ObjectUtil.isNotEmpty(sysApiKeys)) {
			throw new CNException(RCode.SYSAPIKEY_NAME_DUPLICATE);
		}
		
		// 生成唯一uuid
		String uuid = UUID.randomUUID().toString();
		
		sysApiKey.setToken(uuid);
		sysApiKey.setUby(ShiroUtils.getUserId().intValue());
		sysApiKey.setUtime(new Date());
		this.save(sysApiKey);
		return sysApiKey.getId();
	}

	@Override
	public void removeSysApiKeys(String ids) {
		List<SysApiKey> sysApiKeys = this.list(new QueryWrapper<SysApiKey>().lambda().in(SysApiKey::getId, Arrays.asList(ids.split(","))));
		List<String> tokens = new ArrayList<String>();
		for(SysApiKey sysApiKey : sysApiKeys) {
			tokens.add(StrUtil.str(ShiroUtils.REDIS_KEYPREFIX+sysApiKey.getToken()));
		}
		ShiroUtils.removeRedisCacheByToken(tokens);
		this.removeByIds(Arrays.asList(ids.split(",")));
	}

}
