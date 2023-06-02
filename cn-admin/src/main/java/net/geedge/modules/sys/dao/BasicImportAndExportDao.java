package net.geedge.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
@Repository
public interface BasicImportAndExportDao extends BaseMapper{

	/**
     * 指定表名、字段名、字段值
     *
     * @param tableName
     * @param params
     * @return
     */
    List<Object> selectList(@Param("tableName") String tableName, @Param("params") Map<String,String> columnVal);
    
    /**
     * 指定表名 删除id集合
     * @param tableName
     * @param deleteIds
     */
    void deleteList(@Param("tableName") String tableName,  @Param("deleteIds") List<Integer> deleteIds);
}
