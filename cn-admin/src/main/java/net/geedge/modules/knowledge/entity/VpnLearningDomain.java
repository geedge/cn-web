package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:16
 */
@Data
@TableName("cn_vpn_learning_domain")
public class VpnLearningDomain {
    @TableId
    private Long id;

    private String domain;

    private Long pluginId;

    private String pluginName;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;
}
