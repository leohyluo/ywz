package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

public class UserDiagnosisOutcomeVo {

    private String diseaseName;

    private String diseaseCode;

    public UserDiagnosisOutcomeVo() {
    }

    public UserDiagnosisOutcomeVo(UserDiagnosisOutcome obj) {
        BeanCopierUtil.copy(obj, this);
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }


}
