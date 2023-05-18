package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:30
 */
@Data
@TableName("cn_domain_tag_user_defined")
public class IpLocationBuiltIn {
    @TableId
    private Long locationId;

    private Long geonameId;

    private Integer addrType;

    private String startIp;

    private String endIp;

    private byte[] startIpBinary;

    private byte[] endIpBinary;

    private double latitude;

    private double longitude;

    private double coords;

    private String language;

    private String continentAbbr;

    private String continentFull;

    private String countryAbbr;

    private String countryFull;

    private String provinceAbbr;

    private String provinceFull;

    private String cityFull;

    private String subdivisionAddr;

    private String timeZone;

    private Integer isValid;

    private Date opTime;

    private Integer opUser;

    private Integer knowledgeId;

}
