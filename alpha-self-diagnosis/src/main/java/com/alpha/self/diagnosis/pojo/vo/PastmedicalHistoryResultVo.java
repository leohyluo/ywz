package com.alpha.self.diagnosis.pojo.vo;

import java.util.List;

public class PastmedicalHistoryResultVo {

    //手术史
    private List<String> operation;
    //疾病史
    private List<String> diseaseHistory;
    //过敏史
    private List<String> allergicHistory;
    //出生史
    private List<String> fertilityType;
    //喂养史
    private List<String> feedType;
    //月经史
    private List<String> specialPeriod;
    //胎龄（出生的时候怀孕了多少周,范围值）v1.1.0
    private String gestationalAge;
    //预防接种史名称 v1.1.0
    private String vaccinationHistoryText;

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }

    public List<String> getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(List<String> diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public List<String> getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(List<String> allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

    public List<String> getFertilityType() {
        return fertilityType;
    }

    public void setFertilityType(List<String> fertilityType) {
        this.fertilityType = fertilityType;
    }

    public List<String> getFeedType() {
        return feedType;
    }

    public void setFeedType(List<String> feedType) {
        this.feedType = feedType;
    }

    public List<String> getSpecialPeriod() {
        return specialPeriod;
    }

    public void setSpecialPeriod(List<String> specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public String getGestationalAge() {
        return gestationalAge;
    }

    public void setGestationalAge(String gestationalAge) {
        this.gestationalAge = gestationalAge;
    }

    public String getVaccinationHistoryText() {
        return vaccinationHistoryText;
    }

    public void setVaccinationHistoryText(String vaccinationHistoryText) {
        this.vaccinationHistoryText = vaccinationHistoryText;
    }
}
