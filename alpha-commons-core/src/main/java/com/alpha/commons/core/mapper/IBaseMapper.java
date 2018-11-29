package com.alpha.commons.core.mapper;

import com.alpha.commons.core.sql.dto.DataRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;


public interface IBaseMapper {
    /**
     * 插入单条
     *
     * @param params
     */
    @Insert("${sql}")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    public Integer insert(Map<String, Object> params);

    @Update("${sql}")
    public int update(Map<String, Object> params);

    @Delete("${sql}")
    public int delete(Map<String, Object> params);

    @Select("${sql}")
    public List<DataRecord> select(Map<String, Object> params);

    @Select("${sql}")
    public <T> T selectOne(Map<String, Object> params);

    @Select("${sql}")
    public DataRecord selectObject(Map<String, Object> params);

    /**
     * 批量插入
     *
     * @param params
     * @return
     */
    @Insert("${sql}")
    public int insertBatch(Map<String, Object> params);

}
