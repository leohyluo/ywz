package com.alpha.commons.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;

import java.util.Date;

public class BasePojo<T> {

    /**
     * 创建时间
     */
    @JsonIgnore
    protected Date createTime;
    /**
     * 创建人
     */
//    protected String ctdBy;
    /**
     * 修改时间
     */
    @JsonIgnore
    protected Date updateTime;
    /**
     * 修改人
     */
//    protected String updBy;
    /**
     * 分页对象
     */
    @JsonIgnore
    protected PageInfo<T> pageInfo;
    /**
     * 排序条件
     */
    @JsonIgnore
    protected String orderByClause;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public PageInfo<T> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<T> pageInfo) {
        if (null != pageInfo && 0 == pageInfo.getPageSize()) {
            pageInfo.setPageSize(10);
        }
        this.pageInfo = pageInfo;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}
