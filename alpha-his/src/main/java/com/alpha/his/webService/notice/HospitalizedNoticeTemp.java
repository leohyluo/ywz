package com.alpha.his.webService.notice;

import java.io.Serializable;

public class HospitalizedNoticeTemp implements Serializable {

    /**
     * 门诊号
     */
    private String outPatientNo;
    /**
     * 通知时间
     */
    private String notifytime;
    /**
     * 患者名称
     */
    private String patientName;
    /**
     * 联系电话
     */
    private String contactphone;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 链接
     */
    private String url;

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getNotifytime() {
        return notifytime;
    }

    public void setNotifytime(String notifytime) {
        this.notifytime = notifytime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
