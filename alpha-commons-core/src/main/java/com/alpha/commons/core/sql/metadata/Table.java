package com.alpha.commons.core.sql.metadata;

import java.io.Serializable;
import java.util.*;

public class Table implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4959723536252447504L;

    private String tableName;
    private Map<String, Column> columns = new HashMap<String, Column>();
    private List<Column> primaryKeys;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getPrimaryKeys() {
        if (primaryKeys == null) {
            primaryKeys = new ArrayList<Column>();
            Collection<Column> columns = this.columns.values();
            for (Column column : columns) {
                if (column.isPrimaryKey()) {
                    primaryKeys.add(column);
                }
            }
        }
        return primaryKeys;
    }


    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

}
