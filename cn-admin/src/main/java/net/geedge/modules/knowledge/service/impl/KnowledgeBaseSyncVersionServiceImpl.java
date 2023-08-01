package net.geedge.modules.knowledge.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.KnowledgeBaseSyncVersionDao;
import net.geedge.modules.knowledge.entity.KnowledgeBaseSyncVersion;
import net.geedge.modules.knowledge.service.KnowledgeBaseSyncVersionService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:46
 */
@Service("KnowledgeBaseSyncVersionService")
public class KnowledgeBaseSyncVersionServiceImpl extends ServiceImpl<KnowledgeBaseSyncVersionDao, KnowledgeBaseSyncVersion> implements KnowledgeBaseSyncVersionService {
    @Override
    public void syncVersion(Integer id) {
        KnowledgeBaseSyncVersion knowledgeBaseSyncVersion = getById(id);
        if (ObjectUtil.isNotEmpty(knowledgeBaseSyncVersion)) {
            Integer version = knowledgeBaseSyncVersion.getVersion();
            knowledgeBaseSyncVersion.setVersion(++version);
        } else {
            knowledgeBaseSyncVersion = new KnowledgeBaseSyncVersion();
            knowledgeBaseSyncVersion.setVersion(1);
        }
        knowledgeBaseSyncVersion.setKnowledgeId(id);
        knowledgeBaseSyncVersion.setOpTime(new Date());
        saveOrUpdate(knowledgeBaseSyncVersion);
    }
}
