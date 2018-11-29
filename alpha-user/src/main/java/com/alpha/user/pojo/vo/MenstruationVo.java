package com.alpha.user.pojo.vo;

/**
 * 妇科版月经数据交互类
 */
public class MenstruationVo {

    //月经状态 1：正常 0：不正常
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
     * 末次月经时间
     */
    private String lmp;

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

    public String getLmp() {
        return lmp;
    }

    public void setLmp(String lmp) {
        this.lmp = lmp;
    }
}
