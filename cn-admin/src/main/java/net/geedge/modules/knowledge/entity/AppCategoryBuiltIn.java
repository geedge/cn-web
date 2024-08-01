package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:34
 */
@Data
@TableName("cn_app_category_built_in")
public class AppCategoryBuiltIn {
    @TableId
    private Long id;

    private String appName;

    private String appId;

    private String appCategory;

    private String appSubcategory;

    private Integer appRisk;

    private String appDescription;

    private String appLongname;

    private String appTechnology;

    private Integer appIsProtocol;

    private String appCompany;

    private String appCompanyCategory;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;
}
