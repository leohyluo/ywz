package com.alpha.commons.core.sql;


import com.alpha.commons.core.sql.enums.SQLOrderType;

public class SQLOrder {
    private String key;
    private SQLOrderType type;

    public SQLOrder(String key, SQLOrderType type) {
        super();
        this.key = key;
        this.type = type;
    }

    @Override
    public String toString() {
        return key + " " + type;
    }

    public SQLOrderType getType() {
        return type;
    }

    public void setType(SQLOrderType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
