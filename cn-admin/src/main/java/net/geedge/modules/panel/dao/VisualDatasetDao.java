package net.geedge.modules.panel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.panel.entity.VisualDataset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/02/9:50
 */
@Mapper
public interface VisualDatasetDao extends BaseMapper<VisualDataset> {

    List<VisualDataset> queryPage(IPage<VisualDataset> page, @Param("params")Map<String, Object> params);

    VisualDataset selectInfo(@Param("id")Integer id);
}
