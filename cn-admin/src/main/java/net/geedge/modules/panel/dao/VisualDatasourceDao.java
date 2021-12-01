package net.geedge.modules.panel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.panel.entity.VisualDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @Auther: lijinyang
 * @Date: 2021/12/01/15:43
 */
@Mapper
public interface VisualDatasourceDao extends BaseMapper<VisualDatasource> {

    List<VisualDatasource> queryPage(IPage<VisualDatasource> page, @Param("params") Map<String, Object> params);

    VisualDatasource selectInfo(@Param("id")Integer id);
}
