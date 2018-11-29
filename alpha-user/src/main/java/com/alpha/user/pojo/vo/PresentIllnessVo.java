package com.alpha.user.pojo.vo;

/**
 * 现病史信息
 */
public class PresentIllnessVo {

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
        if(mentality == null)
            return "";
        return mentality;
    }

    public void setMentality(String mentality) {
        this.mentality = mentality;
    }

    public String getAppetite() {
        if(appetite == null)
            return "";
        return appetite;
    }

    public void setAppetite(String appetite) {
        this.appetite = appetite;
    }

    public String getShit() {
        if(shit == null)
            return "";
        return shit;
    }

    public void setShit(String shit) {
        this.shit = shit;
    }

    public String getUrine() {
        if(urine == null)
            return "";
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }
}
