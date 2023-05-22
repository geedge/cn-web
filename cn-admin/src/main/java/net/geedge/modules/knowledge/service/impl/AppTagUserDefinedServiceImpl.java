package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.AppTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.service.AppTagUserDefinedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:50
 */
@Service("AppTagUserDefinedService")
public class AppTagUserDefinedServiceImpl extends ServiceImpl<AppTagUserDefinedDao, AppTagUserDefined> implements AppTagUserDefinedService {


    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(AppTagUserDefined.class).getPage(params));
        List<AppTagUserDefined> appTagUserDefineds = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(appTagUserDefineds);
        return new PageUtils(page);
    }
}
