package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.FqdnIcpBuiltInDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.FqdnIcpBuiltIn;
import net.geedge.modules.knowledge.service.FqdnIcpBuiltInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:08
 */
@Service("FqdnIcpBuiltInService")
public class FqdnIcpBuiltInServiceImpl extends ServiceImpl<FqdnIcpBuiltInDao, FqdnIcpBuiltIn> implements FqdnIcpBuiltInService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(FqdnIcpBuiltIn.class).getPage(params));
        List<FqdnIcpBuiltIn> fqdnIcpBuiltIns = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(fqdnIcpBuiltIns);
        return new PageUtils(page);
    }
}
