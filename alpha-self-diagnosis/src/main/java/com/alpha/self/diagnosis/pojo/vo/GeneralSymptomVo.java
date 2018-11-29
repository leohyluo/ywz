package com.alpha.self.diagnosis.pojo.vo;

/**
 * 一般症状属性
 */
public class GeneralSymptomVo {

    /**
     *  精神状态
     */
    private String mentality;

    /**
     * 食欲
     */
    private String appetite;

    /**
     * 大便
     */
    private String shit;

    /**
     * 小便
     */
    private String urine;

    public String getMentality() {
        return mentality;
    }

    public void setMentality(String mentality) {
        this.mentality = mentality;
    }

    public String getAppetite() {
        return appetite;
    }

    public void setAppetite(String appetite) {
        this.appetite = appetite;
    }

    public String getShit() {
        return shit;
    }

    public void setShit(String shit) {
        this.shit = shit;
    }

    public String getUrine() {
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }
}
