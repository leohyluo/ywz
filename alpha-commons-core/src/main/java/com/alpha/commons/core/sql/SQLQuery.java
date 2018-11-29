package com.alpha.commons.core.sql;


import com.alpha.commons.core.sql.enums.SQLConditionType;
import com.alpha.commons.core.sql.enums.SQLOrderType;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 简易SQL查询工具
 * 只支持单表查询
 * 生成SQL语句
 *
 * @author xiaojin
 */
public class SQLQuery {

    public String version = "1.0";
    public String group = "mysql";

    public SQLQuery(Class<?> cls) {
        conditions = new ArrayList<SQLCondition>();
        columns = new ArrayList<String>();
        orders = new ArrayList<SQLOrder>();
        this.cls = cls;
    }

    //表名
    private Class<?> cls;

    //查询条件
    private List<SQLCondition> conditions;

    //返回列
    private List<String> columns;

    //排序列
    private List<SQLOrder> orders;

    //分页
    private SQLLimit limit;

    public void addSQLCondition(SQLCondition sqlCondition) {
        conditions.add(sqlCondition);
    }

    public void addSQLCondition(String column, SQLConditionType type, Object value) {
        addSQLCondition(new SQLCondition(column, type, value));
    }

    public void addSQLColumn(String column) {
        columns.add(column);
    }

    public void addSQLColumn(List<String> columns) {
        columns.addAll(columns);
    }

    public void addSQLOrder(SQLOrder order) {
        orders.add(order);
    }

    public void addSQLOrder(String column, SQLOrderType type) {
        addSQLOrder(new SQLOrder(column, type));
    }

    public void setLimit(SQLLimit limit) {
        this.limit = limit;
    }

    public void setLimit(int offset, int limit) {
        setLimit(new SQLLimit(offset, limit));
    }

    private String getColumnsString() {
        StringBuffer sql = new StringBuffer();
        //列名
        if (columns != null && columns.size() > 0) {
            for (int i = 0; i < columns.size(); i++) {
                sql.append(columns.get(i));
                if (i < columns.size() - 1) {
                    sql.append(',');
                }
            }
        } else {
            sql.append("*");
        }
        return sql.toString();
    }

    private String getTableName() {
        //表名
        Table table = cls.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        }
        return cls.getName().substring(cls.getName().lastIndexOf(".") + 1);
    }

    private String getConditionsString() {
        //查询条件
        StringBuffer sql = new StringBuffer();
        for (int i = 0; i < conditions.size(); i++) {
            if (i == conditions.size() - 1) {
                SQLCondition lastCondition = conditions.get(i);

                String lastSuffix = lastCondition.getSuffix().replaceAll("\\s", "");

                String suffix = "";
                if (lastSuffix.lastIndexOf(")") != 0) {
                    suffix = "";
                } else if (lastSuffix.lastIndexOf(")and") == 0) {
                    suffix = ")";
                } else if (lastSuffix.lastIndexOf(")or") == 0) {
                    suffix = ")";
                }

                sql.append(" ");
                sql.append(lastCondition.getPrefix());
                sql.append(" ");
                sql.append(lastCondition.getKey());
                sql.append(" ");
                sql.append(lastCondition.getType());
                sql.append(" ");
                sql.append(SQLCondition.convertObjectToSqlString(lastCondition.getValue()));
                sql.append(" ");
                sql.append(suffix);
                sql.append(" ");

            } else {
                sql.append(conditions.get(i));
            }
        }
        return sql.toString();
    }

    private String getOrderString() {
        StringBuffer sql = new StringBuffer();
        for (int i = 0; i < orders.size(); i++) {
            sql.append(orders.get(i));
            if (i < orders.size() - 1) {
                sql.append(',');
            }
        }
        return sql.toString();
    }

    private String getLimitString() {
        StringBuffer sql = new StringBuffer();
        sql.append(limit.getOffset() + "," + limit.getLimit());
        return sql.toString();
    }

    /**
     * 获取查询语句
     *
     * @return
     */
    public String getQuerySql() {
        //select ${columns} from ${tabelname} where ${conditions} order by ${order} limit ${limit}
        StringBuffer sql = new StringBuffer("select ${columns} from " + getTableName());

        int columnsIndex = sql.indexOf("${columns}");
        sql.replace(columnsIndex, columnsIndex + "${columns}".length(), getColumnsString());

        if (conditions != null && conditions.size() > 0) {
            sql.append(" where ");
            sql.append(getConditionsString());
        }

        if (orders != null && orders.size() > 0) {
            sql.append(" order by ");
            sql.append(getOrderString());
        }

        if (limit != null) {
            sql.append(" limit ");
            sql.append(getLimitString());
        }

        return sql.toString().replaceAll("\\s+", " ");
    }

    /**
     * 获取查询总条数的语句
     *
     * @return
     */
    public String getQueryLimitCountSql() {
        //select count(*) from ${tabelname} where ${conditions}
        StringBuffer sql = new StringBuffer("select count(*) from " + getTableName());

        if (conditions != null && conditions.size() > 0) {
            sql.append(" where ");
            sql.append(getConditionsString());
        }

        return sql.toString().replaceAll("\\s+", " ");
    }

    @Override
    public String toString() {
        return getQuerySql();
    }

}
