package com.alpha.commons.core.sql;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class DataSet<T> {

    /**
     * 总数
     */
    private Long total;

    /**
     * 数据集合
     */
    private List<T> rows;

    public DataSet() {
        this(null, null);
    }

    public DataSet(List<T> rows) {
        this(null, rows);
    }

    public DataSet(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String toJsonString() {
        Gson gs = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gs.toJson(this);
    }

}
