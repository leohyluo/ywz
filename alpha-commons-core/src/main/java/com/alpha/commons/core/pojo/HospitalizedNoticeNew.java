package com.alpha.commons.core.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 住院-通知单信息
 * Created by HP on 2018/3/29.
 */
@Entity
@Table(name = "hospitalized_notice")
public class HospitalizedNoticeNew {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "age")
    private String age;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "patient_name")
    private String patientName;
    @Column(name = "out_patient_no")
    private String outPatientNo;
    @Column(name = "inDep")
    private String inDep;
    @Column(name = "inType")
    private String inType;
    @Column(name = "inCase")
    private String inCase;
    @Column(name = "inChannel")
    private String inChannel;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "isFect")
    private Integer isFect;
    @Column(name = "notifyTime")
    private String notifyTime;
    @Column(name = "disDesc")
    private String disDesc;
    @Column(name = "contactPhone")
    private String contactPhone;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "doctorName")
    private String doctorName;
    @Column(name = "noticeId")
    private String noticeId;
    @Column(name = "push_times")
    private Integer pushTimes;
    @Column(name = "status")
    private Integer status;
    @Column(name = "signUrl")
    private String signUrl;

    public String getSignUrl() {
        return signUrl;
    }
    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }
    public Integer getPushTimes() {
        return pushTimes;
    }
    public void setPushTimes(Integer pushTimes) {
        this.pushTimes = pushTimes;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getInDep() {
        return inDep;
    }

    public void setInDep(String inDep) {
        this.inDep = inDep;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public String getInCase() {
        return inCase;
    }

    public void setInCase(String inCase) {
        this.inCase = inCase;
    }

    public String getInChannel() {
        return inChannel;
    }

    public void setInChannel(String inChannel) {
        this.inChannel = inChannel;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getIsFect() {
        return isFect;
    }

    public void setIsFect(Integer isFect) {
        this.isFect = isFect;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getDisDesc() {
        return disDesc;
    }

    public void setDisDesc(String disDesc) {
        this.disDesc = disDesc;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}
