package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.FqdnCategoryBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnCategoryBuiltIn;
import net.geedge.modules.knowledge.service.FqdnCategoryBuiltInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:18
 */
@Service("FqdnCategoryBuiltInService")
public class FqdnCategoryBuiltInServiceImpl extends ServiceImpl<FqdnCategoryBuiltInDao, FqdnCategoryBuiltIn> implements FqdnCategoryBuiltInService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(FqdnCategoryBuiltIn.class).getPage(params));
        List<FqdnCategoryBuiltIn> fqdnCategoryBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(fqdnCategoryBuiltIns);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryEntityDetail(String resource) {
        Map<String, Object> data = this.baseMapper.queryEntityDetail(resource);
        return data;
    }
}
