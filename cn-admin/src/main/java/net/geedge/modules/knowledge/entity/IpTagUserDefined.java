package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:27
 */
@Data
@TableName("cn_ip_tag_user_defined")
public class IpTagUserDefined {
    @TableId
    private Long id;

    private String tagValue;

    @TableField(exist = false)
    private Object tagNames;

    private byte[] startIpBinary;

    private byte[] endIpBinary;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;
}
