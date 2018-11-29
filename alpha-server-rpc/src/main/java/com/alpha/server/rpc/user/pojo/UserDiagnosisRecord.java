package com.alpha.server.rpc.user.pojo;

import java.util.Date;

public class UserDiagnosisRecord {


    /**
     *
     */
    private Long id;

    /**
     * 成员唯一编号，张三为李四导药，这里放李四编号
     */
    private Integer memberId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 基本信息记录id
     */
    private Long basicRecordId;

    /**
     * 创建时间
     */
    private Date createTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberId() {
        return this.memberId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }


    public void setBasicRecordId(Long basicRecordId) {
        this.basicRecordId = basicRecordId;
    }

    public Long getBasicRecordId() {
        return this.basicRecordId;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }


}
