package com.alpha.commons.core.pojo;

import javax.persistence.*;


/**
 * Created by HP on 2018/6/20.
 * his 未来七天预约挂号表
 */
@Entity
@Table(name = "his_register_yygh")
public class HisRegisterYygh {

    @Id
    private Long id;

    @Column(name = "out_patient_no")
    private String outPatientNo;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "pno")
    private String pno;

    @Column(name = "yno")
    private String yno;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "patient_card_no")
    private String patientCardNo;

    @Column(name = "card_no")
    private String cardNo;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "visit_time")
    private String visitTime;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone_new")
    private String phoneNew;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private Integer type;

    @Column(name = "interval_time")
    private String intervalTime;

    @Transient
    private String desc;

    @Transient
    private String footer;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getYno() {
        return yno;
    }

    public void setYno(String yno) {
        this.yno = yno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPatientCardNo() {
        return patientCardNo;
    }

    public void setPatientCardNo(String patientCardNo) {
        this.patientCardNo = patientCardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneNew() {
        return phoneNew;
    }

    public void setPhoneNew(String phoneNew) {
        this.phoneNew = phoneNew;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public String toString() {
        return "HisRegisterYygh{" +
                "id=" + id +
                ", outPatientNo='" + outPatientNo + '\'' +
                ", patientName='" + patientName + '\'' +
                ", pno='" + pno + '\'' +
                ", yno='" + yno + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", patientCardNo='" + patientCardNo + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", deptName='" + deptName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", visitTime='" + visitTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneNew='" + phoneNew + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", intervalTime='" + intervalTime + '\'' +
                '}';
    }
}
