package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/12/29/15:32
 */
@Data
@TableName("cn_vpn_learning_ip")
public class VpnLearningIp {
    @TableId
    private Long id;

    private String addrFormat;

    private String ip1;

    private String ip2;

    private String startIpBinary;

    private String endIpBinary;

    private long pluginId;

    private String pluginName;

    private String vpnServiceName;

    private String method;

    private String confidence;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;
}
