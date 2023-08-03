package net.geedge.modules.rule.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/08/02/15:08
 */
@Data
@TableName("detection_rule")
public class DetectionRule {
    @TableId
    private Integer ruleId;

    private String ruleType;

    private String name;

    private String category;

    private String eventType;

    private String description;

    private Integer status;

    private String ruleConfig;

    private String trigger;

    private Integer cUser;

    private Date cTime;

    private Integer opUser;

    private Date opTime;
}
