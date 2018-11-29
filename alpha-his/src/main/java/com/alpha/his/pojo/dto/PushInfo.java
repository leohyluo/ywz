package com.alpha.his.pojo.dto;

import com.alpha.commons.enums.RegisterType;

/**
 * Created by MR.Wu on 2018-06-25.
 */
public class PushInfo {

    /**
     * 标题
     */
    private String title;

    /**
     * 就诊科室
     */
    private String dep;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 就诊人
     */
    private String userName;

    /**
     * 就诊医生
     */
    private String depDocter;

    /**
     * 就诊时间
     */
    private String watchingTime;

    /**
     * 流水号
     */
    private String pno;

    /**
     * 跳转链接
     */
    private String url;

    /**
     * 1：现场挂号和现场预约(取号时推送)  2:预约号(挂号后推送)  3:预约号取号(取号时推送)
     */
    private RegisterType type;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RegisterType getType() {
        return type;
    }

    public void setType(RegisterType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getDepDocter() {
        return depDocter;
    }

    public void setDepDocter(String depDocter) {
        this.depDocter = depDocter;
    }

    public String getWatchingTime() {
        return watchingTime;
    }

    public void setWatchingTime(String watchingTime) {
        this.watchingTime = watchingTime;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }
}
