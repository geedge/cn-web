package net.geedge.modules.knowledge.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
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

	@Override
	public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
		IPage page = this.page(new Query(InternalIpBuiltIn.class).getPage(params));
		List<InternalIpBuiltIn> internalIpBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
		page.setRecords(internalIpBuiltIns);
		return new PageUtils(page);
	}

}
