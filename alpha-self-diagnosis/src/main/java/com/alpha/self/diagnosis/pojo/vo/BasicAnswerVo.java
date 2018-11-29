package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.self.diagnosis.pojo.BasicAnswer;
import com.alpha.server.rpc.diagnosis.pojo.*;
import com.alpha.server.rpc.user.pojo.UserMember;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasicAnswerVo implements IAnswerVo {


    private String answerCode;
    /**
     * 答案值
     */
    private String answerValue;

    /**
     * 问题内容
     */
    private String answerTitle;

    /**
     * 描述
     */
    private String description;
    /**
     * 序号
     */
    private Integer defaultOrder;
    /**
     * 客户端展示类型
     */
    private String displayType;

    private String image;

    /**
     * 是否需要选中
     */
    private String checked;

    /**
     * 所属大类
     */
    @JsonIgnore
    private SyDiagnosisAnswer syAnswer;

    public BasicAnswerVo(BasicAnswer basicAnswer) {
        BeanUtils.copyProperties(basicAnswer, this);
    }

    public BasicAnswerVo() {

    }

    public BasicAnswerVo(UserMember userMember) {
        this.answerTitle = userMember.getMemberName();
        this.answerValue = userMember.getUserId() + "";
//		this.userId = userMember.getUserId().toString();
    }

    public BasicAnswerVo(DiagnosisPastmedicalHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public BasicAnswerVo(DiagnosisSubpastmedicalHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public BasicAnswerVo(DiagnosisAllergicHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public BasicAnswerVo(DiagnosisSuballergicHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public BasicAnswerVo(DiagnosisMainSymptoms mainSymptom) {
        this.answerValue = mainSymptom.getSympCode();
        this.answerTitle = mainSymptom.getSympName();
        this.defaultOrder = mainSymptom.getDefaultOrder();
        this.description = mainSymptom.getPopuName();
    }

    public BasicAnswerVo(DiagnosisMainsympConcsymp dmc) {
        this.answerValue = dmc.getConcSympCode();
        this.answerTitle = dmc.getSympName();
        this.defaultOrder = dmc.getDefaultOrder();
        this.displayType = dmc.getDisplayType();
        //this.description = dmc.getSympName();
    }

    public BasicAnswerVo(PreQuestionAnswer preQuestionAnswer) {
        this.answerValue = preQuestionAnswer.getAnswerCode();
        this.answerTitle = preQuestionAnswer.getAnswerContent();
    }

    public BasicAnswerVo(String answerCode, String answerTitle) {
        this.answerValue = answerCode;
        this.answerTitle = answerTitle;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }


    public SyDiagnosisAnswer getSyAnswer() {
        return syAnswer;
    }

    public void setSyAnswer(SyDiagnosisAnswer syAnswer) {
        this.syAnswer = syAnswer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    /**
     * 医学答案转换
     *
     * @param dqAnswers
     * @return
     */
    public static List<IAnswerVo> convertListMedicineAnswerVo(Collection<DiagnosisQuestionAnswer> dqAnswers) {
        List<IAnswerVo> answerVos = new ArrayList<>();
        for (DiagnosisQuestionAnswer dqAnswer : dqAnswers) {
            BasicAnswerVo answerVo = new BasicAnswerVo(dqAnswer);
            answerVos.add(answerVo);
        }
        return answerVos;
    }

    /**
     * 医学答案转换
     *
     * @param dqAnswer
     */
    public BasicAnswerVo(DiagnosisQuestionAnswer dqAnswer) {
        this.answerTitle = dqAnswer.getContent();
        this.description = dqAnswer.getPopuContent();
        this.answerValue = dqAnswer.getAnswerCode();
        this.defaultOrder = dqAnswer.getDefaultOrder();
        this.syAnswer = dqAnswer.getSyAnswer();
        this.image = dqAnswer.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicAnswerVo)) return false;

        BasicAnswerVo answerVo = (BasicAnswerVo) o;

        return answerValue.equals(answerVo.answerValue);
    }

    @Override
    public int hashCode() {
        int result = answerValue.hashCode();
        return result;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }


}
