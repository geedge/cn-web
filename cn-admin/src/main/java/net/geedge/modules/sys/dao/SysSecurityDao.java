package net.geedge.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.geedge.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

/**
 * 系统安全设定
 *
 * @author lijinyang
 * @date 2021/11/22
 * */
@Mapper
public interface SysSecurityDao extends BaseMapper<SysConfigEntity> {
    /**
     * 系统安全设定查询
     *
     */
    String querySecurity(String paramKey);
    /**
     * 系统安全设定修改
     *
     */
    boolean updateSecurity(@Param("paramValue")String security, @Param("paramKey")String paramKey);
}
