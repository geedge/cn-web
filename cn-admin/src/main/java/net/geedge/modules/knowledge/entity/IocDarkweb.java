package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:29
 */
@Data
@TableName("cn_ioc_darkweb")
public class IocDarkweb {
    @TableId
    private Integer id;

    private String iocValue;

    private String iocType;

    private String nodeType;

    private String ipv6;

    private String isp;

    private String asn;

    private String operatingSystem;

    private String country;

    private String city;

    private String latitude;

    private String longtitude;

    private String torFingerprint;

    private Integer torDirPort;

    private Integer torOrPort;

    private String torFlags;

    private String torVersion;

    private String torNickname;

    private String torExitPolicy;

    private String i2pHash;

    private String i2pMethod;

    private String i2pVersion;

    private Boolean i2pIsFloodfill;

    private String i2pBandwidth;

    private Boolean i2pIsHidden;

    private Boolean i2pIsReachable;

    private String i2pPublishTime;

    private Integer mtproxyPort;

    private String mtproxySecret;

    private Integer obfs4Port;

    private String obfs4Fingerprint;

    private String obfs4Cert;

    private Integer obfs4IatMode;

    private Integer snowflakePort;

    private Long firstAppearTime;

    private Long lastAppearTime;

    private Long createTime;

    private Long update_time;

    private Integer knowledgeId;
}
