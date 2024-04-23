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
     * @param
     * @return
     */
    List<Map<String,Object>> selectDataId(@Param("tableName") String tableName, @Param("params") List<String> columnVal,@Param("datas") List<Map<String, String>> importDataList);
    
    /**
     * 指定表名 删除id集合
     * @param tableName
     * @param deleteIds
     */
    void deleteList(@Param("tableName") String tableName,  @Param("deleteIds") List<Integer> deleteIds);

    /**
     * 指定表名 删除id集合
     * @param tableName
     * @param deleteIds
     */
    void deleteLongList(@Param("tableName") String tableName,  @Param("deleteIds") List<Long> deleteIds);
    
    /**
     * 根据knowledgeId进行数据删除
     * @param tableName
     * @param knowledgeId
     */
    void deleteListByKnowledgeId(@Param("tableName") String tableName,@Param("knowledgeId") Integer knowledgeId);

    /**
     * 清空指定表数据
     * @param tableName
     */
    void deleteTable(@Param("tableName") String tableName);
}
