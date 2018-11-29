package com.alpha.commons.core.sql;


import com.alpha.commons.core.sql.enums.SQLConditionType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SQLCondition {
    /**
     * SQLConditionType
     */
    private SQLConditionType type;
    private String key;
    private Object value;

    /**
     * 前缀为
     * (
     */
    private String prefix;

    /**
     * 后缀为
     * and
     * or
     * ) and
     * ) or
     */
    private String suffix;


    public SQLCondition(String key, SQLConditionType type, Object value) {
        super();
        this.type = type;
        this.key = key;
        this.value = value;

        this.prefix = "";
        this.suffix = " and ";
    }


    @Override
    public String toString() {
        StringBuffer sql = new StringBuffer();
        sql.append(" ");
        sql.append(prefix);
        sql.append(" ");
        sql.append(key);
        sql.append(" ");
        sql.append(type);
        sql.append(" ");
        sql.append(convertObjectToSqlString(value));
        sql.append(" ");
        sql.append(suffix);
        sql.append(" ");
        return sql.toString();
    }


    public static String convertObjectToSqlString(Object obj) {
        if (obj instanceof java.util.Date) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + sdf.format((java.util.Date) obj) + "'";
        } else if (obj instanceof Number) {
            return ((Number) obj).toString();
        } else if (obj instanceof String) {
            return "'" + preparedSQLValue(obj.toString()) + "'";
        } else if (obj instanceof Integer) {
            return "'" + Integer.valueOf(obj.toString()) + "'";
        } else if (obj instanceof String[]) {
            String[] result = (String[]) obj;
            StringBuffer sb = new StringBuffer("");
            if (null != result && result.length > 0) {
                sb.append("(");
                for (int i = 0; i < result.length; i++) {
                    if (i == result.length - 1) {
                        sb.append("'" + result[i] + "'");
                    } else {
                        sb.append("'" + result[i] + "',");
                    }
                }
                sb.append(")");
            }
            return sb.toString();
            //return "'" + preparedSQLValue(obj.toString()) + "'";
        } else {
            return "'" + preparedSQLValue(obj.toString()) + "'";
        }
    }

    /**
     * 预编译，防注入
     *
     * @param value
     * @return
     */
    protected static String preparedSQLValue(String value) {
        return value.replace("'", "''");
    }

    public SQLConditionType getType() {
        return type;
    }

    public void setType(SQLConditionType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
