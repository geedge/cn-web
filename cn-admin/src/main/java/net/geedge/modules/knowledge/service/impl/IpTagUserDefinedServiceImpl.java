package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.IpTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;
import net.geedge.modules.knowledge.service.IpTagUserDefinedService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:54
 */
@Service("IpTagUserDefinedService")
public class IpTagUserDefinedServiceImpl extends ServiceImpl<IpTagUserDefinedDao, IpTagUserDefined> implements IpTagUserDefinedService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(IpTagUserDefined.class).getPage(params));
        List<IpTagUserDefined> ipTagUserDefineds = this.baseMapper.queryPageByKnowledgeId(page, params);
        ipTagUserDefineds.forEach(ip -> {ip.setStartIpBinary(new byte[]{}); ip.setEndIpBinary(new byte[]{});});
        page.setRecords(ipTagUserDefineds);
        return new PageUtils(page);
    }

	@Override
	public void saveBatch(List<IpTagUserDefined> ipTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(ipTagUserDefineds)) {
			this.baseMapper.insertBatch(ipTagUserDefineds);
		}
	}

	@Override
	public void modifyBatch(List<IpTagUserDefined> ipTagUserDefineds) {
		if(ObjectUtil.isNotEmpty(ipTagUserDefineds)) {
			this.baseMapper.updateBatch(ipTagUserDefineds);
		}
	}
}
