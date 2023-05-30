package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.modules.knowledge.dao.Psiphon3IpDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;
import net.geedge.modules.knowledge.service.Psiphon3IpService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:18
 */
@Service("Psiphon3Ip")
public class Psiphon3IpServiceImpl extends ServiceImpl<Psiphon3IpDao, Psiphon3Ip> implements Psiphon3IpService {

    @Override
    public PageUtils queryListByKnowledgeId(Map<String, Object> params) {
        IPage page = this.page(new Query(Psiphon3Ip.class).getPage(params));
        List<Psiphon3Ip> psiphon3Ips = this.baseMapper.queryPageByKnowledgeId(page, params);
        page.setRecords(psiphon3Ips);
        return new PageUtils(page);
    }
}
