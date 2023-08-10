package net.geedge.modules.knowledge.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.KnowledgeBaseSyncVersionDao;
import net.geedge.modules.knowledge.entity.KnowledgeBaseSyncVersion;
import net.geedge.modules.knowledge.service.KnowledgeBaseSyncVersionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:46
 */
@Service("KnowledgeBaseSyncVersionService")
public class KnowledgeBaseSyncVersionServiceImpl extends ServiceImpl<KnowledgeBaseSyncVersionDao, KnowledgeBaseSyncVersion> implements KnowledgeBaseSyncVersionService {
    @Override
    public KnowledgeBaseSyncVersion syncVersion(Integer id) {
        KnowledgeBaseSyncVersion knowledgeBaseSyncVersion = getSyncVersion(id);
        saveOrUpdate(knowledgeBaseSyncVersion);
        return knowledgeBaseSyncVersion;
    }

    @Override
    public List<KnowledgeBaseSyncVersion> syncVersionBatch(List<Integer> knowledgeIds) {
        List<KnowledgeBaseSyncVersion> knowledgeBaseSyncVersionList = new ArrayList<>();
        for (Integer id : knowledgeIds) {
            KnowledgeBaseSyncVersion knowledgeBaseSyncVersion = getSyncVersion(id);
            knowledgeBaseSyncVersionList.add(knowledgeBaseSyncVersion);
        }
        saveOrUpdateBatch(knowledgeBaseSyncVersionList);
        return knowledgeBaseSyncVersionList;
    }

    private KnowledgeBaseSyncVersion getSyncVersion(Integer id){
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
        return knowledgeBaseSyncVersion;
    }
}
