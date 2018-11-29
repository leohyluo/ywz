package com.alpha.commons.core.dao.impl;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.commons.core.mapper.IBaseMapper;
import com.alpha.commons.core.sql.*;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.sql.dto.JavaBean;
import com.alpha.commons.core.sql.enums.SQLConditionType;
import com.alpha.commons.core.util.JavaBeanMap;
import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseDao<T, K> extends SqlSessionTemplate implements IBaseDao<T, K> {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    /**
     * mapperClass 无需继承
     */
    private Class<IBaseMapper> type = IBaseMapper.class;

    /**
     * 获取class,生成对应的SQL
     *
     * @return
     */
    public abstract Class<T> getClz();

    /**
     * 存放实体类的属性容器
     */
    public JavaBean getJavaBean() {
        return new JavaBean(getClz());
    }

    @Autowired
    public BaseDao(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    public Long insert(T entity) {
        return insert(entity, false);
    }

    public void insert(List<T> entitys) {
        for (T entity : entitys) {
            insert(entity, false);
        }
    }

    /**
     * @param entity       要插入数据库的实体类
     * @param insertWithPK 插入时，是否插入主键，如果是数据库自动生成则设置成false. 默认为false
     */
    public Long insert(T entity, Boolean insertWithPK) {
        SQLInsert sqlInsert = new SQLInsert(entity, insertWithPK);
        //sqlInsert.setInsertWithPK(insertWithPK);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlInsert.toString());
        params.putAll(sqlInsert.getValues());
        logger.debug(sqlInsert.toString());
        this.getMapper(type).insert(params);
        return (Long) params.get("id");
    }

    public int insertBatch(List<T> list) {
        SQLBatchInsert sqlInsert = new SQLBatchInsert(list);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlInsert.toString());
        params.putAll(sqlInsert.getValues());
        logger.debug(sqlInsert.toString());
        return this.getMapper(type).insertBatch(params);
    }

    protected int update(Map<String, Object> setFields, List<SQLCondition> conditions) {
        SQLUpdate sqlUpdate = new SQLUpdate(getClz(), setFields);
        sqlUpdate.addSQLCondition(conditions);
        return update(sqlUpdate);
    }

    public int update(SQLUpdate sqlUpdate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlUpdate.toString());
        params.putAll(sqlUpdate.getValues());
        logger.debug(sqlUpdate.toString());
        return this.getMapper(type).update(params);
    }

    /**
     * 需要修改的对象
     * 根据entity的Id 修改不为null的属性
     *
     * @param entity 数据对象
     * @return
     */
    public int update(T entity) {
        Map<String, Object> setFields = new HashMap<String, Object>();

        JavaBean bean = new JavaBean(getClz());
        List<String> list = bean.getFieldsName();
        list.remove(bean.getPkName());
        for (String field : list) {
            try {
                Object value = JavaBean.getValue(field, entity);
                if (value != null) {
                    String columnName = bean.getColumnsName().get(field);
                    setFields.put(columnName, JavaBean.getValue(field, entity));
                }
            } catch (Exception e) {
                logger.error(field + "转换失败", e);
            }
        }
        SQLUpdate sqlUpdate = new SQLUpdate(getClz(), setFields);
        try {
            Object id = JavaBean.getValue(bean.getPkName(), entity);
            if (id != null) {
                sqlUpdate.addSQLCondition(bean.getPkName(), SQLConditionType.EQUAL, JavaBean.getValue(bean.getPkName(), entity));
            } else {
                throw new RuntimeException(getClz() + ":获取主键失败");
            }
        } catch (Exception e) {
            logger.error("获取主键失败", e);
            throw new RuntimeException(getClz() + ":获取主键失败");
        }
        return update(sqlUpdate);
    }

    public int updateEntity(T entity) {
        Map<String, Object> setFields = new HashMap<String, Object>();

        JavaBean bean = new JavaBean(getClz());
        List<String> list = bean.getFieldsName();
        list.remove(bean.getPkName());
        for (String field : list) {
            try {
                Object value = JavaBean.getValue(field, entity);
                String columnName = bean.getColumnsName().get(field);
                setFields.put(columnName, JavaBean.getValue(field, entity));
            } catch (Exception e) {
                logger.error(field + "转换失败", e);
            }
        }
        SQLUpdate sqlUpdate = new SQLUpdate(getClz(), setFields);
        try {
            Object id = JavaBean.getValue(bean.getPkName(), entity);
            if (id != null) {
                sqlUpdate.addSQLCondition(bean.getPkName(), SQLConditionType.EQUAL, JavaBean.getValue(bean.getPkName(), entity));
            } else {
                throw new RuntimeException(getClz() + ":获取主键失败");
            }
        } catch (Exception e) {
            logger.error("获取主键失败", e);
            throw new RuntimeException(getClz() + ":获取主键失败");
        }
        return update(sqlUpdate);
    }

    protected int delete(List<SQLCondition> conditions) {
        SQLDelete sqlUpdate = new SQLDelete(getClz());
        sqlUpdate.addSQLCondition(conditions);
        return delete(sqlUpdate);
    }

    public int delete(K id) {
        SQLDelete sqlDelete = new SQLDelete(getClz());
        sqlDelete.addSQLCondition(getJavaBean().getPkName(), SQLConditionType.EQUAL, id);
        return delete(sqlDelete);
    }

    protected int delete(SQLDelete sqlDelete) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlDelete.toString());
        logger.debug(sqlDelete.toString());
        return this.getMapper(type).update(params);
    }

    protected List<T> select(List<String> columns, Map<String, Object> params) {
        SQLQuery sqlQuery = new SQLQuery(getClz());
        sqlQuery.addSQLColumn(columns);
        for (String key : params.keySet()) {
            sqlQuery.addSQLCondition(key, SQLConditionType.EQUAL, params.get(key));
        }
        return select(sqlQuery);
    }

    public List<T> select(SQLQuery sqlQuery) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlQuery.toString());

        List<DataRecord> list = this.getMapper(type).select(params);
        Gson gs = new Gson();
        Gson clzGs = new Gson();
        List<T> result = new ArrayList<T>();
        result = JavaBeanMap.convertListToJavaBean(list, getClz());
//        for (DataRecord dataRecord : list) {
//            String jsonData = gs.toJson(dataRecord);
//            T t = clzGs.fromJson(jsonData, getClz());
//            result.add(t);
//        }
        return result;
    }

    public List<DataRecord> selectDataRecord(SQLQuery sqlQuery) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlQuery.toString());
        List<DataRecord> list = this.getMapper(type).select(params);
        return list;
    }

    public T get(K id) {
        SQLQuery sqlQuery = new SQLQuery(getClz());
        sqlQuery.addSQLCondition(getJavaBean().getPkName(), SQLConditionType.EQUAL, id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlQuery);
        List<T> list = select(sqlQuery);

        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    public T get(SQLQuery sqlQuery) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlQuery);
        List<T> list = select(sqlQuery);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    public DataSet<T> selectLimit(SQLQuery sqlQuery) {
        DataSet<T> dataset = new DataSet<T>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlQuery.getQueryLimitCountSql());

        Long total = this.getMapper(type).selectOne(params);
        dataset.setTotal(total);
        params.put("sql", sqlQuery.toString());
        List<T> rows = select(sqlQuery);
        dataset.setRows(rows);

        return dataset;
    }

    public DataSet<DataRecord> selectLimitDataRecord(SQLQuery sqlQuery) {
        DataSet<DataRecord> dataset = new DataSet<DataRecord>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sql", sqlQuery.getQueryLimitCountSql());
        Long total = this.getMapper(type).selectOne(params);
        dataset.setTotal(total);
        params.put("sql", sqlQuery.toString());
        List<DataRecord> rows = selectDataRecord(sqlQuery);
        dataset.setRows(rows);
        return dataset;
    }

    public DataSet<T> selectLimit(int offset, int limit) {
        SQLQuery sqlQuery = new SQLQuery(getClz());
        sqlQuery.setLimit(offset, limit);
        return selectLimit(sqlQuery);
    }

    public List<DataRecord> selectBySqlDataRecord(String sql, Map<String, Object> params) {
        params.put("sql", sql);
        return this.getMapper(type).select(params);
    }

    public List<T> selectBySql(String sql, Map<String, Object> params) {
        params.put("sql", sql);
        Gson gs = new Gson();
        List<DataRecord> list = this.getMapper(type).select(params);
        Gson clzGs = new Gson();
        List<T> result = new ArrayList<T>();
        result = JavaBeanMap.convertListToJavaBean(list, getClz());
//        for (DataRecord dataRecord : list) {
//            String jsonData = gs.toJson(dataRecord);
//            T t = clzGs.fromJson(jsonData, getClz());
//            result.add(t);
//        }
        return result;
    }

    public <Type> Type getOneBySql(String sql, Map<String, Object> params) {
        params.put("sql", sql);
        return this.getMapper(type).selectOne(params);
    }

    /**
     * 以上是通过java代码生成的sql语句进行数据库操作
     */


    /**
     * 以下是通过xml的sql语句进行数据库操作
     */
    public Long insertByStatement(String statement, Map<String, Object> parameters) {
        insert(statement, parameters);
        Long id = (Long) parameters.get("id");
        return id;
    }

    public int updateByStatement(String statement, Map<String, Object> parameters) {
        return update(statement, parameters);
    }

    public int deleteByStatement(String statement, Map<String, Object> parameters) {
        return delete(statement, parameters);
    }

    public List<DataRecord> selectForList(String statement, Map<String, Object> parameters) {
        return selectList(statement, parameters);
    }

    public List<T> selectForListObject(String statement, Map<String, Object> parameters) {
        return selectList(statement, parameters);
    }

    public DataRecord selectForDataRecord(String statement,
                                          Map<String, Object> parameters) {
        DataRecord dataRecord = selectOne(statement, parameters);
        return dataRecord;
    }

    public <T> T selectForObject(String statement,
                                 Map<String, Object> parameters) {
        return selectOne(statement, parameters);
    }

    public DataSet<DataRecord> selectListLimit(String sqlStatement, String countStatement,
                                               Map<String, Object> parameters) {
        DataSet<DataRecord> ds = new DataSet<DataRecord>();
        List<DataRecord> list = selectForList(sqlStatement, parameters);
        ds.setRows(list);
        Long total = selectForObject(countStatement, parameters);
        ds.setTotal(total);
        return ds;
    }

    @Override
    public Long insertSelective(T t) {
        return Long.valueOf(insert(this.getClass().getName()+".insertSelective", t));
    }
    @Override
    public Long updateSelective(T t) {
        return Long.valueOf(insert(this.getClass().getName()+".updateSelective", t));
    }
    @Override
    public List<T> selectSelective(T t) {
        return selectList(this.getClass().getName()+".selectSelective", t);
    }

    @Override
    public int getCount(String statement) {
        return 0;
    }
}
