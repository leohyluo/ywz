package com.alpha.server.rpc.diagnosis.pojo.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by xc.xiong on 2017/9/13.
 */
public class MedicineQuestionVo implements Serializable {

    private static final long serialVersionUID = 2999142900060128282L;

    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 主症状编码
     */
    @Column(name = "symp_code")
    private String sympCode;

    /**
     *
     */
    @Column(name = "symp_name")
    private String sympName;
    /**
     *
     */
    @Column(name = "question_standard_deviation")
    private Double questionStandardDeviation;
    /**
     *
     */
    @Column(name = "question_code")
    private String questionCode;
    /**
     *
     */
    @Column(name = "question_type")
    private Integer questionType;
    /**
     *
     */
    @Column(name = "question_title")
    private String questionTitle;
    /**
     *
     */
    @Column(name = "answer_standard_deviation")
    private Double answerStandardDeviation;
    /**
     *
     */
    @Column(name = "question_weight")
    private Double questionWeight;

    /**
     *
     */
    @Column(name = "answer_title")
    private String answerTitle;

    /**
     *
     */
    @Column(name = "answer_code")
    private String answerCode;
    /**
     *
     */
    @Column(name = "answer_spec")
    private Integer answerSpec;
    /**
     *
     */
    @Column(name = "disease_code")
    private String diseaseCode;
    /**
     *
     */
    @Column(name = "answer_weight")
    private Double answerWeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSympCode() {
        return sympCode;
    }

    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public String getSympName() {
        return sympName;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    public Double getQuestionStandardDeviation() {
        return questionStandardDeviation;
    }

    public void setQuestionStandardDeviation(Double questionStandardDeviation) {
        this.questionStandardDeviation = questionStandardDeviation;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Double getAnswerStandardDeviation() {
        return answerStandardDeviation;
    }

    public void setAnswerStandardDeviation(Double answerStandardDeviation) {
        this.answerStandardDeviation = answerStandardDeviation;
    }

    public Double getQuestionWeight() {
        return questionWeight;
    }

    public void setQuestionWeight(Double questionWeight) {
        this.questionWeight = questionWeight;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public Integer getAnswerSpec() {
        return answerSpec;
    }

    public void setAnswerSpec(Integer answerSpec) {
        this.answerSpec = answerSpec;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Double getAnswerWeight() {
        return answerWeight;
    }

    public void setAnswerWeight(Double answerWeight) {
        this.answerWeight = answerWeight;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
}
