package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.io.Serializable;
import java.util.List;

public class BasicQuestionWithSearchVo implements Serializable, IQuestionVo {

    private static final String USER_CHAR = "{userName}";

    private static final long serialVersionUID = 6688903127327118326L;

    private String userId;

    private Long diagnosisId;

    private String displayType;

    private String questionTitle;

    private String sympCode;

    private int type;//问题类型

    /**
     * 问题编码
     */
    private String questionCode;

    private List<IAnswerVo> answers;

    private String searchUrl;

    public BasicQuestionWithSearchVo(Long diagnosisId, BasicQuestion basicQuestion, List<IAnswerVo> answers, UserInfo userInfo, String userName) {
        this.diagnosisId = diagnosisId;
        this.displayType = basicQuestion.getDisplayType();
        this.questionCode = basicQuestion.getQuestionCode();
        this.questionTitle = basicQuestion.getTitle();
        this.answers = answers;
        if (userInfo != null) {
            this.userId = userInfo.getUserId().toString();
            if (this.questionTitle.contains(USER_CHAR)) {
                this.questionTitle = this.questionTitle.replace(USER_CHAR, userName);
            }
        }
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

    public List<IAnswerVo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<IAnswerVo> answers) {
        this.answers = answers;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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


    public String getSearchUrl() {
        return searchUrl;
    }


    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }


}
