package net.geedge.modules.rule.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/08/02/15:12
 */
@Data
@TableName("rule_commit_history")
public class RuleCommitHistory {
    @TableId
    private Integer opSeq;

    private Integer ruleId;

    private String opType;

    private Integer opVersion;

    private Integer opUser;

    private Date opTime;
}
