package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.IpAsnBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import net.geedge.modules.knowledge.service.IpAsnBuiltInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:31
 */
@Service("IpAsnBuiltInService")
public class IpAsnBuiltInServiceImpl extends ServiceImpl<IpAsnBuiltInDao, IpAsnBuiltIn> implements IpAsnBuiltInService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(IpAsnBuiltIn.class).getPage(params));
        List<IpAsnBuiltIn> ipAsnBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(ipAsnBuiltIns);
        return new PageUtils(page);
    }


    @Override
    public Map<String, Object> queryEntityDetail(String resource) {
        Map<String, Object> data = this.baseMapper.queryEntityDetail(resource);
        return data;
    }
}
