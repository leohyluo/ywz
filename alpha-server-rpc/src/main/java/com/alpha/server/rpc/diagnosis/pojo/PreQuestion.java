package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * 预问诊基础问题
 */
@Entity
@Table(name = "pre_question")
public class PreQuestion implements Question {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "question_code")
    private String questionCode;

    @Column(name = "title")
    private String title;

    @Column(name = "popu_title")
    private String popuTitle;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "min_age")
    private Double minAge;

    @Column(name = "max_age")
    private Double maxAge;

    @Column(name = "default_order")
    private Integer defaultOrder;

    @Column(name = "question_type")
    private Integer questionType;

    @Column(name = "display_type")
    private String displayType;

    @Column(name = "is_show")
    private Integer isShow;

    @Column(name = "app_type")
    private String appType;

    @Column(name = "parent_question_code")
    private String parentQuestionCode;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private Float age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopuTitle() {
        return popuTitle;
    }

    public void setPopuTitle(String popuTitle) {
        this.popuTitle = popuTitle;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getMinAge() {
        return minAge;
    }

    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getParentQuestionCode() {
        return parentQuestionCode;
    }

    public void setParentQuestionCode(String parentQuestionCode) {
        this.parentQuestionCode = parentQuestionCode;
    }

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

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }
}
