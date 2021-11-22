package net.geedge.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.geedge.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;


/**
 * 系统外观设置
 *
 * @author lijinyang
 * @date 2021/11/17
 * */
@Mapper
public interface SysAppearanceDao extends BaseMapper<SysConfigEntity> {

    /**
     * 系统外观查询
     */
    String queryAppearance(String paramKey);
    /**
     * 系统外观更改
     */
    boolean updateAppearance(@Param("paramValue") String appearance, @Param("paramKey") String paramKey);
}
