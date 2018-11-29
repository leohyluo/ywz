package com.alpha.self.diagnosis.pojo.vo;

import java.io.Serializable;

public class AnswerRequestVo implements Serializable, IAnswerVo {


    private static final long serialVersionUID = 2075278352245483563L;

    /**
     * 问题内容 》》 answerValue
     */
    private String content;


    /**
     * 问题内容  answerValue
     */
    private String answerTitle;

    private String description;

    public AnswerRequestVo() {

    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
