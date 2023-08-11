package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.FqdnWhoIsBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnWhoIsBuiltIn;
import net.geedge.modules.knowledge.service.FqdnWhoIsBuiltInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:59
 */
@Service("FqdnWhoIsBuiltInService")
public class FqdnWhoIsBuiltInServiceImpl extends ServiceImpl<FqdnWhoIsBuiltInDao, FqdnWhoIsBuiltIn> implements FqdnWhoIsBuiltInService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(FqdnWhoIsBuiltIn.class).getPage(params));
        List<FqdnWhoIsBuiltIn> fqdnWhoIsBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(fqdnWhoIsBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource, Boolean isFuzzyQuery) {
        return this.baseMapper.queryEntityDetail(resource, isFuzzyQuery);
    }
}
