package net.geedge.modules.panel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

import java.util.Date;

/**
 * datasetfield接口
 * @Auther: lijinyang
 * @Date: 2021/12/02/15:57
 */
@Data
@TableName("visual_dataset_field")
public class VisualDatasetField {
    @TableId
    private Integer id;
    private String name;
    private String i18n;
    private Integer dsetid;
    private Integer type;
    private Integer stype;
    private String expr;
    private String params;
    private Integer idx;
    private Long cby;
    private Date ctime;
    private Long uby;
    private Date utime;
    private String remark;
    private Integer state;
    private Integer buildIn;

    @TableField(exist = false)
    private SysUserEntity cuser;

    @TableField(exist = false)
    private SysUserEntity uuser;

    @TableField(exist = false)
    private VisualDataset dataset;

    @TableField(exist = false)
    private VisualDatasource datasource;
}
