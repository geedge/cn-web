package net.geedge.modules.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("sys_login_session")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysLoginSession implements Serializable {

	private static final long serialVersionUID = -4920240029180869932L;

	/**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String sessionId;

    /**
     * 用户 id
     */
    private String sessionData;

    /**
     * 最后活跃时间
     */
    private Long lasetActiveTs;
}
