package com.alpha.user.pojo.vo;

/**
 * Created by MR.Wu on 2018-09-27.
 */
public class MedicalList {

    private String patientNo;

    private Long userId;

    private String userName;

    private String sex;

    private Long dianosisId;

    private String mainSympt;

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getMainSympt() {
        return mainSympt;
    }

    public void setMainSympt(String mainSympt) {
        this.mainSympt = mainSympt;
    }

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public Long getDianosisId() {
        return dianosisId;
    }

    public void setDianosisId(Long dianosisId) {
        this.dianosisId = dianosisId;
    }
}
