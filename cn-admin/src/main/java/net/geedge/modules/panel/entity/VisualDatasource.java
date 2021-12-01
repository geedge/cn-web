package net.geedge.modules.panel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

import java.util.Date;
import java.util.List;

/**
 * datasource接口
 * @Auther: lijinyang
 * @Date: 2021/12/01/15:36
 */
@Data
@TableName("visual_datasource")
public class VisualDatasource {
    @TableId
    private Integer id;
    private String name;
    private String i18n;
    private Integer type;
    private String params;
    private Long cby;
    private Date ctime;
    private Long uby;
    private Date utime;
    private String remark;
    private Integer state;
    private Integer buildIn;

    @TableField(exist = false)
    private SysUserEntity uuser;

    @TableField(exist = false)
    private SysUserEntity cuser;
}
