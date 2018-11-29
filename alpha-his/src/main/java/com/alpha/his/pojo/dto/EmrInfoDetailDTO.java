package com.alpha.his.pojo.dto;

import com.alpha.commons.util.StringUtils;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;

/**
 * 电子病历数据交互类
 */
public class EmrInfoDetailDTO {

    //姓名
    private String userName;

    //性别
    private Integer gender;

    //1岁8个月
    private String age;

    //体重40kg
    private String weight;

    //挂号科室
    private String department;

    //就诊时间
    private String cureTime;

    //主诉
    private String mainSymptomName;

    //现病史
    private String presentIllnessHistory;

    //既往史
    private String pastmedicalHistory;

    //疾病史
    private String diseases;

    public String getMainSymptomName() {
        return mainSymptomName;
    }

    public void setMainSymptomName(String mainSymptomName) {
        this.mainSymptomName = mainSymptomName;
    }

    public String getPresentIllnessHistory() {
        return presentIllnessHistory;
    }

    public void setPresentIllnessHistory(String presentIllnessHistory) {
        this.presentIllnessHistory = presentIllnessHistory;
    }

    public String getPastmedicalHistory() {
        return pastmedicalHistory;
    }

    public void setPastmedicalHistory(String pastmedicalHistory) {
        this.pastmedicalHistory = pastmedicalHistory;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCureTime() {
        return cureTime;
    }

    public void setCureTime(String cureTime) {
        this.cureTime = cureTime;
    }


}
