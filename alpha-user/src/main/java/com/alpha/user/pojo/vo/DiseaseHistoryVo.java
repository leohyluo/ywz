package com.alpha.user.pojo.vo;

public class DiseaseHistoryVo {

	/**
     * 答案值
     */
    private String answerValue;

    /**
     * 问题内容
     */
    private String answerTitle;

    public DiseaseHistoryVo() {
    }

    public DiseaseHistoryVo(String answerValue, String answerTitle) {
        this.answerValue = answerValue;
        this.answerTitle = answerTitle;
    }

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerTitle() {
		return answerTitle;
	}

	public void setAnswerTitle(String answerTitle) {
		this.answerTitle = answerTitle;
	}

    
}
