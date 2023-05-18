package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:25
 */
@Data
@TableName("cn_domain_tag_user_defined")
public class DomainTagUserDefined {
    @TableId
    private Long id;

    private String tagValue;

    private String domain;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;

}
