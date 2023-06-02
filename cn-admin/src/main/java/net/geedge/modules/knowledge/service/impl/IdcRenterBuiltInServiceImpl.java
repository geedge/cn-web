package net.geedge.modules.knowledge.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import net.geedge.modules.knowledge.dao.IdcRenterBuiltInDao;
import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;
import net.geedge.modules.knowledge.service.IdcRenterBuiltInService;

@Service("idcRenterBuiltInService")
public class IdcRenterBuiltInServiceImpl extends ServiceImpl<IdcRenterBuiltInDao,IdcRenterBuiltIn> implements IdcRenterBuiltInService{

	@Override
	public void saveBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns) {
		if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
			this.baseMapper.insertBatchForBinary(idcRenterBuiltIns);
		}
	}

	@Override
	public void modifyBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns) {
		if(ObjectUtil.isNotEmpty(idcRenterBuiltIns)) {
			this.baseMapper.updateBatchForBinary(idcRenterBuiltIns);
		}
	}

}
