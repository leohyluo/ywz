package com.alpha.self.diagnosis.pojo.vo;

/**
 * 既往史部份属性
 */
public class PastMedicalHistoryVo {

    /**
     * 月经期
     */
    private String menstrualPeriod;

    /**
     * 女性特殊时期（月经期、备孕中、妊娠期、哺乳期、无）
     */
    private String specialPeriod;

    /**
     * 顺产/剖宫产/产钳助产
     */
    private String fertilityType;

    /**
     * 胎龄（出生的时候怀孕了多少周,范围值）
     */
    private String gestationalAge;

    /**
     * 母乳喂养、人工喂养、混合喂养
     */
    private String feedType;

    /**
     * 预防接种史名称
     */
    private String vaccinationHistoryText;

    /**
     * 既往史编码集合
     */
    private String pastmedicalHistoryCode;

    /**
     * 既往史名称集合
     */
    private String pastmedicalHistoryText;

    /**
     * 过敏史编码集合
     */
    private String allergicHistoryCode;

    /**
     * 过敏史名称集合
     */
    private String allergicHistoryText;

    /**
     * 月经状态 1：正常 0：不正常
     */
    private String menarcheStatus;

    /**
     * 月经初潮
     */
    private String menarche;

    /**
     * 月经周期
     */
    private String menarcheCycle;

    /**
     * 经期
     */
    private String menarchePeroid;

    /**
     * 婚史
     */
    private String marriage;

    /**
     * 生育史
     */
    private String historyOfBorn;

    /**
     * 末次月经时间
     */
    private String lmp;

    //足月产孩子个数
    private Integer matureChildCount;

    //早产孩子个数
    private Integer prematureChildCount;

    //流产孩子个数
    private Integer miscarryChildCount;

    //现有孩子个数
    private Integer nowChildCount;

    public String getMenstrualPeriod() {
        return menstrualPeriod;
    }

    public void setMenstrualPeriod(String menstrualPeriod) {
        this.menstrualPeriod = menstrualPeriod;
    }

    public String getSpecialPeriod() {
        return specialPeriod;
    }

    public void setSpecialPeriod(String specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public String getFertilityType() {
        return fertilityType;
    }

    public void setFertilityType(String fertilityType) {
        this.fertilityType = fertilityType;
    }

    public String getGestationalAge() {
        return gestationalAge;
    }

    public void setGestationalAge(String gestationalAge) {
        this.gestationalAge = gestationalAge;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getVaccinationHistoryText() {
        return vaccinationHistoryText;
    }

    public void setVaccinationHistoryText(String vaccinationHistoryText) {
        this.vaccinationHistoryText = vaccinationHistoryText;
    }

    public String getPastmedicalHistoryCode() {
        return pastmedicalHistoryCode;
    }

    public void setPastmedicalHistoryCode(String pastmedicalHistoryCode) {
        this.pastmedicalHistoryCode = pastmedicalHistoryCode;
    }

    public String getPastmedicalHistoryText() {
        return pastmedicalHistoryText;
    }

    public void setPastmedicalHistoryText(String pastmedicalHistoryText) {
        this.pastmedicalHistoryText = pastmedicalHistoryText;
    }

    public String getAllergicHistoryCode() {
        return allergicHistoryCode;
    }

    public void setAllergicHistoryCode(String allergicHistoryCode) {
        this.allergicHistoryCode = allergicHistoryCode;
    }

    public String getAllergicHistoryText() {
        return allergicHistoryText;
    }

    public void setAllergicHistoryText(String allergicHistoryText) {
        this.allergicHistoryText = allergicHistoryText;
    }

    public String getMenarcheStatus() {
        return menarcheStatus;
    }

    public void setMenarcheStatus(String menarcheStatus) {
        this.menarcheStatus = menarcheStatus;
    }

    public String getMenarche() {
        return menarche;
    }

    public void setMenarche(String menarche) {
        this.menarche = menarche;
    }

    public String getMenarcheCycle() {
        return menarcheCycle;
    }

    public void setMenarcheCycle(String menarcheCycle) {
        this.menarcheCycle = menarcheCycle;
    }

    public String getMenarchePeroid() {
        return menarchePeroid;
    }

    public void setMenarchePeroid(String menarchePeroid) {
        this.menarchePeroid = menarchePeroid;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getHistoryOfBorn() {
        return historyOfBorn;
    }

    public void setHistoryOfBorn(String historyOfBorn) {
        this.historyOfBorn = historyOfBorn;
    }

    public String getLmp() {
        return lmp;
    }

    public void setLmp(String lmp) {
        this.lmp = lmp;
    }

    public Integer getMatureChildCount() {
        return matureChildCount;
    }

    public void setMatureChildCount(Integer matureChildCount) {
        this.matureChildCount = matureChildCount;
    }

    public Integer getPrematureChildCount() {
        return prematureChildCount;
    }

    public void setPrematureChildCount(Integer prematureChildCount) {
        this.prematureChildCount = prematureChildCount;
    }

    public Integer getMiscarryChildCount() {
        return miscarryChildCount;
    }

    public void setMiscarryChildCount(Integer miscarryChildCount) {
        this.miscarryChildCount = miscarryChildCount;
    }

    public Integer getNowChildCount() {
        return nowChildCount;
    }

    public void setNowChildCount(Integer nowChildCount) {
        this.nowChildCount = nowChildCount;
    }
}
