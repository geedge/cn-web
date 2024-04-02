package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:09
 */
@Data
@TableName("cn_fqdn_category_built_in")
public class FqdnCategoryBuiltIn {
    @TableId
    private Integer id;

    private String fqdn;

    private String source;

    private Boolean querySuccess;

    private Integer matchPattern;

    private Integer reputationScore;

    private String reputationLevel;

    private Integer categoryId;

    private String categoryName;

    private String categoryGroup;

    private Integer categoryConf;

    private Boolean isA1Cat;

    private Integer statusCode;

    private String submitUser;

    private Date createTime;

    private Date updateTime;
}
