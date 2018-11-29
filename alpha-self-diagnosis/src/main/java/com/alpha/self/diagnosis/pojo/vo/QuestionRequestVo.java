package com.alpha.self.diagnosis.pojo.vo;

import java.io.Serializable;
import java.util.List;

import com.alpha.user.controller.req.vo.BasicRequestVo;

public class QuestionRequestVo extends BasicRequestVo implements Serializable {

    private static final long serialVersionUID = 5499892490163380991L;


    private String userId;

    private Long diagnosisId;

    private String displayType;

    private String questionTitle;

    private String sympCode;

    private Integer type;//问题类型

    private Integer inType;
    

    /**
     * 问题编码
     */
    private String questionCode;

    private List<AnswerRequestVo> answers;

    public QuestionRequestVo() {
    }

    public List<AnswerRequestVo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerRequestVo> answers) {
        this.answers = answers;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSympCode() {
        return sympCode;
    }

    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }


}
