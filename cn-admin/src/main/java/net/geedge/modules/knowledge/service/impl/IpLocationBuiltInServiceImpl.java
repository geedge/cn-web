package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.IpLocationBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.IpLocationBuiltIn;
import net.geedge.modules.knowledge.service.IpLocationBuiltInService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:53
 */
@Service("IpLocationBuiltInService")
public class IpLocationBuiltInServiceImpl extends ServiceImpl<IpLocationBuiltInDao, IpLocationBuiltIn> implements IpLocationBuiltInService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(IpLocationBuiltIn.class).getPage(params));
        List<IpLocationBuiltIn> ipLocationBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(ipLocationBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource) {
        Map<String, Object> data = this.baseMapper.queryEntityDetail(resource);
        return data;
    }
}
