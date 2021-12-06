package net.geedge.modules.panel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.panel.entity.VisualDatasetField;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/02/16:19
 */
@Mapper
public interface VisualDatasetFieldDao extends BaseMapper<VisualDatasetField> {

    List<VisualDatasetField> queryPage(IPage<VisualDatasetField> page, @Param("params")Map<String, Object> params);

    VisualDatasetField selectInfo(@Param("id")Integer id);
}
