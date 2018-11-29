package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.server.rpc.diagnosis.pojo.DiagnosisAllergicHistory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisSuballergicHistory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisSubpastmedicalHistory;

public class SelectedBasicAnswerVo implements IAnswerVo {


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
     * 是否需要选中
     */
    private String checked;


    public SelectedBasicAnswerVo() {

    }

    public SelectedBasicAnswerVo(DiseaseVo diseaseVo) {
        this.answerValue = diseaseVo.getDiseaseCode();
        this.answerTitle = diseaseVo.getDiseaseName();
        this.description = diseaseVo.getDescription();
        this.checked = "Y";
    }

    public SelectedBasicAnswerVo(DiagnosisPastmedicalHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public SelectedBasicAnswerVo(DiagnosisSubpastmedicalHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public SelectedBasicAnswerVo(DiagnosisAllergicHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
    }

    public SelectedBasicAnswerVo(DiagnosisSuballergicHistory disease) {
        this.answerValue = disease.getDiseaseCode();
        this.answerTitle = disease.getDiseaseName();
        this.description = disease.getDescription();
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


    public String getChecked() {
        return checked;
    }


    public void setChecked(String checked) {
        this.checked = checked;
    }


}
