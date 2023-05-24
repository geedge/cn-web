package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:19
 */
@Data
@TableName("cn_app_tag_user_defined")
public class AppTagUserDefined {
    @TableId
    private Long id;

    private String tagValue;
    @TableField(value = "app_name")
    private String tagName;

    private Date opTime;

    private Date cTime;

    private Integer opUser;

    private Integer isValid;

    private Integer knowledgeId;

    @TableField(exist = false)
    private SysUserEntity user;
}
