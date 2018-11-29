package com.alpha.commons.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HP on 2018/6/4.
 * 儿童医院门诊信息表（一卡通信息表）
 *
 */
@Entity
@Table(name = "outpatientinfo")
public class OutPatientInfo {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "patient_name")
    private String patientName;
    @Column(name = "mzhm")
    private String outpatientNo;
    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "cardNo")
    private String cardNo;
    @Column(name = "contact_address")
    private String contactAddress;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "bob")
    private String birth;
    @Column(name = "remark")
    private String remark;
    @Column(name = "insurance")
    private String insurance;
    @Column(name = "patient_type")
    private Integer patientType;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "identity_no")
    private String identityNo;
    @Column(name = "family_address")
    private String familyAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getOutpatientNo() {
        return outpatientNo;
    }

    public void setOutpatientNo(String outpatientNo) {
        this.outpatientNo = outpatientNo;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public Integer getPatientType() {
        return patientType;
    }

    public void setPatientType(Integer patientType) {
        this.patientType = patientType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

}