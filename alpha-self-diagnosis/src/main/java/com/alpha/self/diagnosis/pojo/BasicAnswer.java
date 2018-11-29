package com.alpha.self.diagnosis.pojo;

public class BasicAnswer {


    /**
     * id
     */
    private Long id;

    /**
     * 问题id
     */
    private Long basicQuestionId;

    /**
     * 答案编码
     */
    private String answerCode;

    /**
     * 答案值
     */
    private String answerValue;

    /**
     * 答案内容
     */
    private String answerTitle;


    /**
     * 计算公式
     */
    private String expression;

    /**
     * 序号
     */
    private Integer defaultOrder;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setBasicQuestionId(Long basicQuestionId) {
        this.basicQuestionId = basicQuestionId;
    }

    public Long getBasicQuestionId() {
        return this.basicQuestionId;
    }


    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerCode() {
        return this.answerCode;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }


    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }


}
