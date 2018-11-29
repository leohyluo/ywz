package com.alpha.user.pojo.vo;

/**
 * Created by HP on 2018/5/15.
 * 导入编辑模块统计详情
 */
public class ImportAndEditDetailVo {
    private String flage;
    private Integer mainSymptomName;
    private Integer presentIllnessHistory;
    private Integer pastmedicalHistory;
    private Integer diseases;

    public String getFlage() {
        return flage;
    }

    public void setFlage(String flage) {
        this.flage = flage;
    }

    public Integer getMainSymptomName() {
        return mainSymptomName;
    }

    public void setMainSymptomName(Integer mainSymptomName) {
        this.mainSymptomName = mainSymptomName;
    }

    public Integer getPresentIllnessHistory() {
        return presentIllnessHistory;
    }

    public void setPresentIllnessHistory(Integer presentIllnessHistory) {
        this.presentIllnessHistory = presentIllnessHistory;
    }

    public Integer getPastmedicalHistory() {
        return pastmedicalHistory;
    }

    public void setPastmedicalHistory(Integer pastmedicalHistory) {
        this.pastmedicalHistory = pastmedicalHistory;
    }

    public Integer getDiseases() {
        return diseases;
    }

    public void setDiseases(Integer diseases) {
        this.diseases = diseases;
    }


}
