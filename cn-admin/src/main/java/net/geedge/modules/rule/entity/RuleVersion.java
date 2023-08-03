package net.geedge.modules.rule.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/08/02/15:13
 */
@Data
@TableName("rule_version")
public class RuleVersion {
    @TableId
    private Integer ruleId;

    private Integer version;

    private Date opTime;
}
