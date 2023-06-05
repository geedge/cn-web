package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:25
 */
@Data
@TableName("cn_ip_asn_built_in")
public class IpAsnBuiltIn {
    @TableId
    private Integer asnId;

    private Integer addrType;
    
    private String addrFormat;

    private String ip1;

    private String ip2;

    private String startIpBinary;

    private String endIpBinary;

    private String asn;

    private String organization;

    private Integer isValid;

    private Date opTime;

    private Integer opUser;

    private Integer knowledgeId;
}
