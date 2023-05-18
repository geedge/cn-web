package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.KnowledgeBaseSyncVersionDao;
import net.geedge.modules.knowledge.entity.KnowledgeBaseSyncVersion;
import net.geedge.modules.knowledge.service.KnowledgeBaseSyncVersionService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:46
 */
@Service("KnowledgeBaseSyncVersionService")
public class KnowledgeBaseSyncVersionServiceImpl extends ServiceImpl<KnowledgeBaseSyncVersionDao, KnowledgeBaseSyncVersion> implements KnowledgeBaseSyncVersionService {
}
