package com.alpha.commons.core.sql.metadata;

import java.io.Serializable;

public class Column implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7113175787759761334L;
    private boolean primaryKey = false;
    private String columnName;
    private String columnComment;
    private int columnType;
    private int length;
    private boolean nullable;

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    private String columnTypeName;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
