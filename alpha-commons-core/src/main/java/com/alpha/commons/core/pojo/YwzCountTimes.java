package com.alpha.commons.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HP on 2018/5/9.
 */
@Entity
@Table(name = "ywz_count")
public class YwzCountTimes {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "type") //1.开卡，2，扫码 3，进入 4，提交 5，url 6，导入 7，编辑
    private Integer type;
    @Column(name = "descri")

    private String descri;
    @Column(name = "deviceId")

    private String deviceId;
    @Column(name = "pno")

    private String pno;
    @Column(name = "diseaseId")

    private String diseaseId;
    @Column(name = "doctorName")

    private String doctorName;
    @Column(name = "times")

    private Integer times;
    @Column(name = "createTime")

    private String createTime;

    @Column(name = "memberId ")
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
