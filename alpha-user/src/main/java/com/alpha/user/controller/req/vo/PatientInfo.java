package com.alpha.user.controller.req.vo;

public class PatientInfo extends BasicRequestVo {

    private Long diagnosisId;

    //用户唯一编号
    private Long userId;

    //第三方用户唯一编号
    private String externalUserId;

    //性别 男/女
    private int gender;

    //用户姓名
    private String userName;

    //出生日期，格式：2017-9-4 09:40:26
    private String birth;

    //身高 cm
    private String heigth;

    //体重 kg
    private String weight;

    //特殊时期
    private String specialPeriod;

    //产史
    private String fertility;

    //喂养史
    private String feed;

    //既往史
    private String pastmedicalHistory;

    //过敏史
    private String allergicHistory;

    //最后更新时间
    private String lastUpdateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHeigth() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth = heigth;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSpecialPeriod() {
        return specialPeriod;
    }

    public void setSpecialPeriod(String specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public String getFertility() {
        return fertility;
    }

    public void setFertility(String fertility) {
        this.fertility = fertility;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getPastmedicalHistory() {
        return pastmedicalHistory;
    }

    public void setPastmedicalHistory(String pastmedicalHistory) {
        this.pastmedicalHistory = pastmedicalHistory;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }
}
