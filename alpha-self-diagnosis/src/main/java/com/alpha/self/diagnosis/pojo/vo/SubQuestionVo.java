package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubQuestionVo {

    private Integer questionType;
    private String questionCode;
    private String questionTitle;
    private List<IAnswerVo> answers;

    public SubQuestionVo(PreQuestion preQuestion, List<PreQuestionAnswer> preQuestionAnswerList) {
        this.questionType = preQuestion.getQuestionType();
        this.questionCode = preQuestion.getQuestionCode();
        this.questionTitle = preQuestion.getTitle();
        this.answers = Optional.ofNullable(preQuestionAnswerList).orElseGet(ArrayList::new).stream().map(e->{
            BasicAnswerVo item = new BasicAnswerVo();
            item.setAnswerValue(e.getAnswerCode());
            item.setAnswerTitle(e.getAnswerContent());
            item.setDefaultOrder(e.getDefaultOrder());
            item.setChecked(e.getChecked());
            return item;
        }).collect(Collectors.toList());
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
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

    public List<IAnswerVo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<IAnswerVo> answers) {
        this.answers = answers;
    }
}
