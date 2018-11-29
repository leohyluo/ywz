package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * 预问诊基础问题答案字典表
 */
@Entity
@Table(name = "pre_question_answer")
public class PreQuestionAnswer implements Answer {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "question_code")
    private String questionCode;

    @Column(name = "answer_code")
    private String answerCode;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "next_question_code")
    private String nextQuestionCode;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "min_age")
    private Double minAge;

    @Column(name = "max_age")
    private Double maxAge;

    @Column(name = "default_order")
    private Integer defaultOrder;

    @Column(name = "app_type")
    private String appType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private float age;

    @Transient
    private String checked;

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

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
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

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
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

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public String getNextQuestionCode() {
        return nextQuestionCode;
    }

    public void setNextQuestionCode(String nextQuestionCode) {
        this.nextQuestionCode = nextQuestionCode;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
