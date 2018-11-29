package com.alpha.commons.core.sql.metadata;

import java.io.Serializable;
import java.util.Map;

public class Database implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6166669249154576377L;
    private String schema;
    private String catalog;
    private Map<String, Table> tables;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }


    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void setTable(Table table) {
        tables.put(table.getTableName(), table);
    }
}
