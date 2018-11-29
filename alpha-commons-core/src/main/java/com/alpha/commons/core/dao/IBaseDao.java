package com.alpha.commons.core.dao;


import com.alpha.commons.core.sql.DataSet;
import com.alpha.commons.core.sql.SQLQuery;
import com.alpha.commons.core.sql.SQLUpdate;
import com.alpha.commons.core.sql.dto.DataRecord;

import java.util.List;
import java.util.Map;


public interface IBaseDao<T, K> {

    /**
     * 插入数据
     *
     * @param entity 要插入的实体类
     */
    Long insert(T entity);

    /**
     * @param entity       要插入数据库的实体类
     * @param insertWithPK 插入时，是否插入主键，如果是数据库自动生成则设置成false. 默认为false
     */
    Long insert(T entity, Boolean insertWithPK);

    void insert(List<T> entitys);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(List<T> list);

    /**
     * 需要修改的对象
     * 根据entity的Id 修改不为null的属性
     *
     * @param entity 数据对象
     * @return
     */
    int update(T entity);

    int updateEntity(T entity);

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    int delete(K id);

    /**
     * 查询
     *
     * @param sqlQuery 查询工具类
     * @return
     */
    List<T> select(SQLQuery sqlQuery);

    /**
     * 查询分页
     *
     * @param sqlQuery 查询工具类
     * @return
     */
    DataSet<T> selectLimit(SQLQuery sqlQuery);

    /**
     * 查询分页
     *
     * @param offset 从第几条开始显示
     * @param limit  显示几条
     * @return
     */
    DataSet<T> selectLimit(int offset, int limit);

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    T get(K id);

    /**
     * 查询分页
     *
     * @param sqlQuery 查询工具类
     * @return DataRecord
     * @author xiaojin
     * @date 2014-12-5
     */
    DataSet<DataRecord> selectLimitDataRecord(SQLQuery sqlQuery);

    /**
     * 查询
     *
     * @param sqlQuery
     * @return DataRecord
     * @author xiaojin
     * @date 2014-12-5
     */
    List<DataRecord> selectDataRecord(SQLQuery sqlQuery);

    /**
     * 查询单个对象
     *
     * @param sqlQuery
     * @return
     */
    T get(SQLQuery sqlQuery);

    /**
     * 根据xml配置的statement进行sql插入操作
     *
     * @param statement
     * @param parameters
     * @return
     */
    Long insertByStatement(String statement, Map<String, Object> parameters);

    /**
     * 根据xml配置的statement进行sql修改操作
     *
     * @param statement
     * @param parameters
     * @return
     */
    int updateByStatement(String statement, Map<String, Object> parameters);

    /**
     * 根据xml配置的statement进行sql删除操作
     *
     * @param statement
     * @param parameters
     * @return
     */
    int deleteByStatement(String statement, Map<String, Object> parameters);

    /**
     * 根据xml配置的statement进行查询
     *
     * @param statement
     * @param parameters
     * @return DataRecord集合
     */
    List<DataRecord> selectForList(String statement, Map<String, Object> parameters);

    /**
     * 根据xml配置的statement进行查询
     *
     * @param statement
     * @param parameters
     * @return 对象集合
     */
    List<T> selectForListObject(String statement, Map<String, Object> parameters);

    /**
     * 根据xml配置的statement，查询单条记录
     *
     * @param statement
     * @param parameters
     * @return
     */
    DataRecord selectForDataRecord(String statement,
                                   Map<String, Object> parameters);

    /**
     * 根据xml配置的statement，查询单个值
     *
     * @param statement
     * @param parameters
     * @param <T>
     * @return
     */
    <T> T selectForObject(String statement,
                          Map<String, Object> parameters);

    /**
     * 根据xml配置的statement，进行分页查询
     *
     * @param sqlStatement
     * @param countStatement
     * @param parameters
     * @return
     */
    DataSet<DataRecord> selectListLimit(String sqlStatement, String countStatement, Map<String, Object> parameters);

    int update(SQLUpdate sqlUpdate);
    Long insertSelective(T t);
    Long updateSelective(T t);
    List<T> selectSelective(T t);

    int getCount(String statement);
}
