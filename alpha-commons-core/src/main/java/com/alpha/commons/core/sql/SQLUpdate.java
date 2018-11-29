package com.alpha.commons.core.sql;


import com.alpha.commons.core.sql.enums.SQLConditionType;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLUpdate {

    //private static final Logger logger = LoggerFactory.getLogger(SQLUpdate.class);

    private String tableName;
    private Map<String, Object> setFields;

    //查询条件
    private List<SQLCondition> conditions;

    public SQLUpdate(Class<?> clz, Map<String, Object> setFields) {

        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        this.setFields = setFields;
        conditions = new ArrayList<SQLCondition>();
    }

    public void addSQLCondition(SQLCondition sqlCondition) {
        conditions.add(sqlCondition);
    }

    public void addSQLCondition(String column, SQLConditionType type, Object value) {
        addSQLCondition(new SQLCondition(column, type, value));
    }

    public void addSQLCondition(List<SQLCondition> sqlConditions) {
        conditions.addAll(sqlConditions);
    }

    private String getConditionsString() {
        //查询条件
        StringBuffer sql = new StringBuffer();
        for (int i = 0; i < conditions.size(); i++) {
            if (i == conditions.size() - 1) {
                conditions.get(i).setSuffix("");
            }
            sql.append(conditions.get(i));
        }
        return sql.toString();
    }

    public String getUpdateSQL() {
        //update ${tablename} set ${columns} where ${conditions})
        StringBuffer sql = new StringBuffer("update ${tablename} set ${columns}");

        int tableNameIndex = sql.indexOf("${tablename}");
        sql.replace(tableNameIndex, tableNameIndex + "${tablename}".length(), tableName);

        int columnIndex = sql.indexOf("${columns}");
        sql.replace(columnIndex, columnIndex + "${columns}".length(), getColumnSQL());

        if (conditions != null && conditions.size() > 0) {
            sql.append(" where ");
            sql.append(getConditionsString());
        }

        return sql.toString().replaceAll("\\s+", " ");

    }

    private String getColumnSQL() {
        int length = setFields.size();
        int i = 0;
        StringBuffer sql = new StringBuffer();
        for (String key : setFields.keySet()) {
            sql.append(key);
            sql.append('=');
            sql.append("#{");
            sql.append(key);
            sql.append("}");
            if (i < length - 1) {
                sql.append(',');
            }
            i++;
        }
        return sql.toString();
    }

    public Map<String, Object> getValues() {
        Map<String, Object> temp = new LinkedHashMap<String, Object>();
        temp.putAll(setFields);
        return temp;
    }

    @Override
    public String toString() {
        return getUpdateSQL();
    }

}
