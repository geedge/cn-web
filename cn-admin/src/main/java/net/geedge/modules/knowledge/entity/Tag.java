package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/02/16:48
 */
@Data
@TableName("tag")
public class Tag {
    @TableId(type = IdType.INPUT)
    private String id;

    private String category;

    private String name;

    private String intent;

    private String indicatorType;

    private String description;

    private Integer isBuiltIn;

    private Date createdTime;

    private Date updatedTime;
}
