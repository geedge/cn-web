package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

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

    private String startIpBinary;

    private String endIpBinary;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;

    @TableField(exist = false)
    private SysUserEntity user;
    private String ip1;
    private String ip2;
    
    private String addrFormat;
}
