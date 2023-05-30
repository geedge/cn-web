package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.AppCategoryBuiltInDao;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.service.AppCategoryBuiltInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:44
 */
@Service("AppCategoryBuiltInService")
public class AppCategoryBuiltInServiceImpl extends ServiceImpl<AppCategoryBuiltInDao, AppCategoryBuiltIn> implements AppCategoryBuiltInService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(AppCategoryBuiltIn.class).getPage(params));
        List<AppCategoryBuiltIn> appCategoryBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(appCategoryBuiltIns);
        return new PageUtils(page);
    }
}
