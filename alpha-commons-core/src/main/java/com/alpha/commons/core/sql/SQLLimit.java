package com.alpha.commons.core.sql;

public class SQLLimit {
    /**
     * 从第几条开始
     */
    private int offset;
    /**
     * 显示几条
     */
    private int limit = 10;

    public SQLLimit(int offset, int limit) {
        super();
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
