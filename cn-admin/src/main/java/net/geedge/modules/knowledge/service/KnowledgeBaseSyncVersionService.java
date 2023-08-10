package net.geedge.modules.knowledge.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.knowledge.entity.KnowledgeBaseSyncVersion;

import java.util.List;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:45
 */
public interface KnowledgeBaseSyncVersionService extends IService<KnowledgeBaseSyncVersion> {
    /**
     * Desc: 版本同步
     *
     * @param knowledgeId
     * @return
     * @created by wWei
     * @date 2023/6/14 18:34
     */
	KnowledgeBaseSyncVersion syncVersion(Integer knowledgeId);

    List<KnowledgeBaseSyncVersion> syncVersionBatch(List<Integer> knowledgeIds);
}
