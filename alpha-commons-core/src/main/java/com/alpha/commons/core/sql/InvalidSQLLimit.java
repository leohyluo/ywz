package com.alpha.commons.core.sql;

public class InvalidSQLLimit {
    /**
     * 第几页
     */
    private int page;
    /**
     * 显示条数
     */
    private int count = 10;

    /**
     * 总条数
     */
    private long totalCount = 0;

    public InvalidSQLLimit(int page, int count) {
        this.page = page;
        this.count = count;
    }


    /**
     * 获取最后一页页码，也就是总页数
     *
     * @return 最后一页页码
     */
    public int getLastPageNumber() {
        int result = (int) (totalCount % count == 0 ?
                totalCount / count
                : totalCount / count + 1);
        if (result <= 1)
            result = 1;
        return result;
    }

    /**
     * 总的数据条目数量，0表示没有数据
     *
     * @return 总数量
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 获取当前页的首条数据的行编码
     *
     * @return 当前页的首条数据的行编码
     */
    public int getThisPageFirstElementNumber() {
        return (getPage() - 1) * getCount();
    }

    /**
     * 根据数据行获取页码
     *
     * @param index 数据行
     * @return 数据行所在的页码
     */
    public int getPageByElementNumber(int index) {
        if (index >= this.getTotalCount()) {
            return this.getLastPageNumber();
        }
        return (int) (Math.floor(index / getPage()) + 1);
    }

    /**
     * 每一页显示的条目数
     *
     * @return 每一页显示的条目数
     */
    public int getCount() {
        return count;
    }

    /**
     * 当前页的页码
     *
     * @return 当前页的页码
     */
    public int getPage() {
        return page;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
