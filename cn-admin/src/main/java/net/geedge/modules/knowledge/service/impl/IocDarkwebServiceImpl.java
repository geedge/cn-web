package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.IocDarkwebDao;
import net.geedge.modules.knowledge.entity.IocDarkweb;
import net.geedge.modules.knowledge.service.IocDarkwebService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:02
 */
@Service("IocDarkwebService")
public class IocDarkwebServiceImpl extends ServiceImpl<IocDarkwebDao, IocDarkweb> implements IocDarkwebService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(IocDarkweb.class).getPage(params));
        List<IocDarkweb> iocDarkwebs = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(iocDarkwebs);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryIpEntityTag(String resource) {
        return this.baseMapper.queryIpEntityTag(resource);
    }

    @Override
    public Map<String, Object> queryDomainEntityTag(String resource, Boolean isFuzzyQuery) {
        return this.baseMapper.queryDomainEntityTag(resource, isFuzzyQuery);
    }

}
