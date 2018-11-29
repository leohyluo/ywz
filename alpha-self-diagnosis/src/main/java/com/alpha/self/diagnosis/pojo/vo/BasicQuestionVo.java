package com.alpha.self.diagnosis.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alpha.his.utils.AppUtils;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.alpha.commons.enums.DisplayType;
import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisQuestionAnswer;
import com.alpha.server.rpc.user.pojo.UserInfo;

public class BasicQuestionVo implements Serializable, IQuestionVo {

    private static final String USER_CHAR = "{userName}";

    private static final long serialVersionUID = 6688903127327118326L;

    private String userId;

    private Long diagnosisId;

    private String sympCode;

    private String displayRange;

    private String displayDefault;

    private String questionCode;

    private String questionTitle;

    private String popuTitle;

    private Integer questionType;//问题类型

    private String displayType;

    private List<SubQuestionVo> subQuestion;

    private List<IAnswerVo> answers;

    public BasicQuestionVo(Long diagnosisId, BasicQuestion basicQuestion, List<IAnswerVo> answers, UserInfo userInfo, String userName) {
        this.diagnosisId = diagnosisId;
        this.displayType = basicQuestion.getDisplayType();
        this.questionCode = basicQuestion.getQuestionCode();
        this.questionTitle = basicQuestion.getTitle();
        this.questionTitle = basicQuestion.getTitle();
        this.answers = answers;
        if (userInfo != null) {
            this.userId = userInfo.getUserId().toString();
            if (this.questionTitle.contains(USER_CHAR)) {
                this.questionTitle = this.questionTitle.replace(USER_CHAR, userName);
            }
        }
    }

    public BasicQuestionVo() {
    }

    /**
     * 把医学问题转换成返回视图
     *
     * @param question
     * @return
     */
    public BasicQuestionVo(DiagnosisMainsympQuestion question, Long diagnosisId, String sympCode, UserInfo userInfo, String appType) {
        if (question.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()) {
            //this.displayType = "checkbox_more_input_confirm";
            this.displayType = DisplayType.CHECKBOX_MORE_INPUT_CONFIRM.getValue();
            //this.searchUrl = "/data/search/concsymp";
        } else {
            //this.displayType = "radio";
            String displayType = StringUtils.isNotEmpty(question.getDisplayType()) ? question.getDisplayType() : "radio";
            this.displayType = displayType;
        }
        this.displayRange = question.getDisplayRange();
        this.displayDefault = question.getDisplayDefault();
        this.diagnosisId = diagnosisId;
        this.questionCode = question.getQuestionCode();
        this.popuTitle = AppUtils.setUserNameAtQuestionTitle(appType, question.getPopuTitle(), userInfo, null);
        this.questionTitle = AppUtils.setUserNameAtQuestionTitle(appType, question.getPopuTitle(), userInfo, null);
        this.sympCode = sympCode;
        this.questionType = question.getQuestionType();
    }

    /**
     * 把医学问题转换成返回视图
     *
     * @param question
     * @return
     */
    public BasicQuestionVo(DiagnosisMainsympQuestion question, List<DiagnosisQuestionAnswer> dqAnswers, Long diagnosisId, String sympCode) {
        this.displayType = "radio";
        this.diagnosisId = diagnosisId;
        this.questionCode = question.getQuestionCode();
        this.questionTitle = question.getPopuTitle();
        this.questionTitle = question.getPopuTitle();
        this.sympCode = sympCode;
        //答案转换
        List<IAnswerVo> answerVos = BasicAnswerVo.convertListMedicineAnswerVo(dqAnswers);
        this.answers = answerVos;
        this.questionType = QuestionEnum.医学问题.getValue();
    }

    /**
     * 把医学问题转换成返回视图
     *
     * @param question
     * @return
     */
    public BasicQuestionVo(DiagnosisMainsympQuestion question, Long diagnosisId) {
        BasicQuestionVo questionVo = new BasicQuestionVo();
        questionVo.setType(QuestionEnum.医学问题.ordinal());
        questionVo.setDiagnosisId(diagnosisId);
        questionVo.setQuestionCode(question.getQuestionCode());
        questionVo.setPopuTitle(question.getPopuTitle());
        questionVo.setQuestionTitle(question.getTitle());
        questionVo.setTitle(question.getPopuTitle());
        questionVo.setDisplayType("radio");
    }

    public BasicQuestionVo(String appType, UserInfo userInfo, String doctorName, Long diagnosisId, String mainSympCode, PreQuestion question, List<PreQuestionAnswer> answerList) {
        this.userId = String.valueOf(userInfo.getUserId());
        this.diagnosisId = diagnosisId;
        this.sympCode = mainSympCode;
        this.displayType = question.getDisplayType();
        this.questionTitle = AppUtils.setUserNameAtQuestionTitle(appType, question.getTitle(), userInfo, doctorName);
        this.popuTitle = question.getPopuTitle();
        this.questionType = question.getQuestionType();
        this.questionCode = question.getQuestionCode();
        this.answers = Optional.ofNullable(answerList).orElseGet(ArrayList::new).stream().map(e->{
            BasicAnswerVo answerVo = new BasicAnswerVo();
            answerVo.setAnswerValue(e.getAnswerCode());
            answerVo.setAnswerTitle(e.getAnswerContent());
            answerVo.setDefaultOrder(e.getDefaultOrder());
            return answerVo;
        }).collect(Collectors.toList());
    }

    public BasicQuestionVo(UserBasicRecord userBasicRecord, String mainSympCode, PreQuestion question, List<BasicAnswerVo> basicAnswerVoList) {
        this.userId = userBasicRecord.getUserId().toString();
        this.diagnosisId = userBasicRecord.getDiagnosisId();
        this.sympCode = mainSympCode;
        this.displayType = question.getDisplayType();
        this.questionTitle = question.getTitle();
        this.popuTitle = question.getPopuTitle();
        this.questionType = question.getQuestionType();
        this.questionCode = question.getQuestionCode();
        this.answers = basicAnswerVoList.stream().collect(Collectors.toList());
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


    public void setType(int type) {
        this.questionType = type;
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

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public void setTitle(String title) {
        this.questionTitle = title;
    }

    public List<SubQuestionVo> getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(List<SubQuestionVo> subQuestion) {
        this.subQuestion = subQuestion;
    }

    public String getPopuTitle() {
        return popuTitle;
    }

    public void setPopuTitle(String popuTitle) {
        this.popuTitle = popuTitle;
    }

}
