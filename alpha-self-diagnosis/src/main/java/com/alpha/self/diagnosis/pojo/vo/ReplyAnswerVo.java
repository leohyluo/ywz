package com.alpha.self.diagnosis.pojo.vo;

public class ReplyAnswerVo {

    private String subQuestionCode;
    private Integer subQuestionType;
    private String answerCode;
    private String answerTitle;
    private String answerPopuTitle; //通俗化内容

    public Integer getSubQuestionType() {
        return subQuestionType;
    }

    public void setSubQuestionType(Integer subQuestionType) {
        this.subQuestionType = subQuestionType;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getAnswerPopuTitle() {
        return answerPopuTitle;
    }

    public void setAnswerPopuTitle(String answerPopuTitle) {
        this.answerPopuTitle = answerPopuTitle;
    }

    public String getSubQuestionCode() {
        return subQuestionCode;
    }

    public void setSubQuestionCode(String subQuestionCode) {
        this.subQuestionCode = subQuestionCode;
    }
}
