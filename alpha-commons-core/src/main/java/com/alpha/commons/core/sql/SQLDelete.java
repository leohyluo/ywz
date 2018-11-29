package com.alpha.commons.core.sql;


import com.alpha.commons.core.sql.enums.SQLConditionType;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

public class SQLDelete {

    //private static final Logger logger = LoggerFactory.getLogger(SQLDelete.class);

    private String tableName;
    //查询条件
    private List<SQLCondition> conditions;

    public SQLDelete(Class<?> clz) {

        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        conditions = new ArrayList<SQLCondition>();
    }

    public void addSQLCondition(SQLCondition sqlCondition) {
        conditions.add(sqlCondition);
    }

    public void addSQLCondition(List<SQLCondition> sqlConditions) {
        conditions.addAll(sqlConditions);
    }

    public void addSQLCondition(String column, SQLConditionType type, Object value) {
        addSQLCondition(new SQLCondition(column, type, value));
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
        //delete from ${tablename} where ${conditions})
        StringBuffer sql = new StringBuffer("delete from ${tablename}");

        int tableNameIndex = sql.indexOf("${tablename}");
        sql.replace(tableNameIndex, tableNameIndex + "${tablename}".length(), tableName);

        if (conditions != null && conditions.size() > 0) {
            sql.append(" where ");
            sql.append(getConditionsString());
        }

        return sql.toString().replaceAll("\\s+", " ");

    }

    @Override
    public String toString() {
        return getUpdateSQL();
    }

    public static void main(String[] args) {

    }

}
