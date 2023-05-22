package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.DomainTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.DomainTagUserDefined;
import net.geedge.modules.knowledge.service.DomainTagUserDefinedService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:52
 */
@Service("DomainTagUserDefinedService")
public class DomainTagUserDefinedServiceImpl extends ServiceImpl<DomainTagUserDefinedDao, DomainTagUserDefined> implements DomainTagUserDefinedService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(DomainTagUserDefined.class).getPage(params));
        List<DomainTagUserDefined> domainTagUserDefineds = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(domainTagUserDefineds);
        return new PageUtils(page);
    }
}
