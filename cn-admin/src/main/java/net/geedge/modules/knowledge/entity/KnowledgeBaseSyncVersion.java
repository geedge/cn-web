package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/10:18
 */
@Data
@TableName("cn_knowledge_base_sync_version")
public class KnowledgeBaseSyncVersion {
    @TableId
    private Integer knowledgeId;

    private Integer version;

    private Date opTime;

}
