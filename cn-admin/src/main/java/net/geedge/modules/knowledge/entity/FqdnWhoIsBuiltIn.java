package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:46
 */
@Data
@TableName("cn_fqdn_who_is_built_in")
public class FqdnWhoIsBuiltIn {
    @TableId
    private Integer id;

    private String fqdn;

    private String source;

    private Boolean querySuccess;

    private Integer statusCode;

    private Integer matchPattern;

    private String whoisDomain;

    private Date whoisUpdateDate;

    private Date whoisCreateDate;

    private Date whoisExpireDate;

    private String whoisEmail;

    private String whoisNs;

    private String whoisRegistrarName;

    private String whoisRegistrantOrg;

    private String whoisRegistrantName;

    private String whoisRegistrantStreet;

    private String whoisRegistrantCity;

    private String whoisRegistrantState;

    private String whoisRegistrantPostcode;

    private String whoisRegistrantCountry;

    private String whoisRegistrantPhone;

    private Date createTime;

    private Date updateTime;
}
