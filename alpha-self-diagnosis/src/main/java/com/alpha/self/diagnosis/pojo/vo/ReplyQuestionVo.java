package com.alpha.self.diagnosis.pojo.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 回答问题数据传输类
 */
public class ReplyQuestionVo {
    private String systemType;
    private String userId;
    private Long diagnosisId;
    private String sympCode;
    private Integer questionType;
    private String questionCode;
    private List<ReplyAnswerVo> answers;

    public ReplyQuestionVo() {}

    public ReplyQuestionVo(QuestionRequestVo questionVo) {
        this.systemType = questionVo.getSystemType();
        this.userId = questionVo.getUserId();
        this.diagnosisId = questionVo.getDiagnosisId();
        this.sympCode = questionVo.getSympCode();
        this.questionType = questionVo.getType();
        this.questionCode = questionVo.getQuestionCode();

        List<AnswerRequestVo> answerList = questionVo.getAnswers();
        this.answers = Optional.ofNullable(answerList).orElseGet(ArrayList::new).stream().map(e->{
            ReplyAnswerVo replyAnswerVo = new ReplyAnswerVo();
            replyAnswerVo.setAnswerCode(e.getContent());
            replyAnswerVo.setAnswerTitle(e.getAnswerTitle());
            return replyAnswerVo;
        }).collect(Collectors.toList());
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getSympCode() {
        return sympCode;
    }

    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public List<ReplyAnswerVo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ReplyAnswerVo> answers) {
        this.answers = answers;
    }
}
