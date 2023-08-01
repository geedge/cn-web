package net.geedge.modules.knowledge.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import net.geedge.modules.knowledge.dao.InternalIpBuiltInDao;
import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;
import net.geedge.modules.knowledge.service.InternalIpBuiltInService;

@Service("internalIpBuiltInService")
public class InternalIpBuiltInServiceImpl extends ServiceImpl<InternalIpBuiltInDao,InternalIpBuiltIn> implements InternalIpBuiltInService{

	@Override
	public void saveBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns) {
		if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
			this.baseMapper.insertBatchForBinary(internalIpBuiltIns);
		}
	}

	@Override
	public void modifyBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns) {
		if(ObjectUtil.isNotEmpty(internalIpBuiltIns)) {
			this.baseMapper.updateBatchForBinary(internalIpBuiltIns);
		}
	}

}
