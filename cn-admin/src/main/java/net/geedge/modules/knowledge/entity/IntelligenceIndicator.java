package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/02/16:52
 */
@Data
@TableName("cn_intelligence_indicator")
public class IntelligenceIndicator {
    @TableId
    private Long id;

    private String type;

    private String sourceId;

    private String sourceName;

    private String ipAddrFormat;

    private String ip1;

    private String ip2;

    private String startIpBinary;

    private String endIpBinary;

    private String domain;

    private String subscriber;

    private String method;

    private String confidence;

    private String tags;

    private String description;

    private Date firstSeenTime;

    private Date lastSeenTime;

    private Date createdTime;

    private Date updatedTime;
}
