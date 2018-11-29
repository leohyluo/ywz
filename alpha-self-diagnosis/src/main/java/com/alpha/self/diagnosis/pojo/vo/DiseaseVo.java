package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.util.BeanCopierUtil;

public class DiseaseVo {

    private String diseaseCode;
    private String diseaseName;
    private String description;

    public DiseaseVo() {
    }

    public DiseaseVo(DiagnosisDisease obj) {
        BeanCopierUtil.copy(obj, this);
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
