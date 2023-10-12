package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.knowledge.dao.LinkDirectionBuiltInDao;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import net.geedge.modules.knowledge.service.LinkDirectionBuiltInService;

import java.util.List;
import java.util.Map;

@Service("linkDirectionBuiltInService")
public class LinkDirectionBuiltInServiceImpl extends ServiceImpl<LinkDirectionBuiltInDao,LinkDirectionBuiltIn> implements LinkDirectionBuiltInService{

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(LinkDirectionBuiltIn.class).getPage(params));
        List<LinkDirectionBuiltIn> linkDirectionBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(linkDirectionBuiltIns);
        return new PageUtils(page);
    }
}
