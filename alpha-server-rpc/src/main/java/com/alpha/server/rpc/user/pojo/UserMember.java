package com.alpha.server.rpc.user.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_member")
public class UserMember {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 用户的userId
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户下成员的userId
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 成员的姓名
     */
    @Column(name = "member_name")
    private String memberName;

    /**
     * 建立关系时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public UserMember() {
    }

    public UserMember(Long userId, Long memberId, String memberName) {
        this.userId = userId;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }


    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return this.memberId;
    }


    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return this.memberName;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }


}
