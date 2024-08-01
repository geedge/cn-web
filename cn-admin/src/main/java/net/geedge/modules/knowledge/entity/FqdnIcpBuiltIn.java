package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:50
 */
@Data
@TableName("cn_fqdn_icp_built_in")
public class FqdnIcpBuiltIn {
    @TableId
    private Integer id;

    private String fqdn;

    private String source;

    private Boolean querySuccess;

    private Integer isAccessible;

    private Integer statusCode;

    private Integer matchPattern;

    private String icpOwner;

    private String icpCompanyName;

    private String icpCompanyType;

    private String icpMainPage;

    private String icpSiteLicense;

    private String icpSiteName;

    private String icpVerifyTime;

    private Date createTime;

    private Date updateTime;
}
