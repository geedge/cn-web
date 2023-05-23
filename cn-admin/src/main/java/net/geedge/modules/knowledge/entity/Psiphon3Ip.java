package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:09
 */
@Data
@TableName("cn_psiphon3_ip")
public class Psiphon3Ip {
    @TableId
    private Long id;

    private Integer isServer;

    private String addrFormat;

    private String ip1;

    private String ip2;

    private byte[] startIpBinary;

    private byte[] endIpBinary;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;
}
