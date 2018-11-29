package com.alpha.server.rpc.his.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alpha.commons.api.tencent.qcloud.Module.Base;
import com.alpha.commons.core.pojo.BasePojo;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
@Entity
public class HospitalizedPatientInfoNew extends BasePojo<HospitalizedPatientInfoNew> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String noticeId;

    private String patientName;

    private String sex;

    private String birthday;

    private String nationality;

    private String nation;

    private String hospitalCode;

    private String outPatientNo;

    private String contactPhone;

    private String contactName;

    private String contactAddr;

    private String contactIdcard;

    private String relationship;

    private String insuranceNo;

    private String insuranceType;

    private String patientCertitype;

    private String nativeAddr;

    private String patientCertino;

    private String school;

    private String address;

    private String homeplace;

    private String cardNo;

    private String hosno;

    private String insurance;

    private String mailingAddress;

    private String registeredPermanent;

    private String bedNo;

    private String patAdmCondition;

    private String admittedBy;

    private String occupation;

    private String consultingDoctor;

    private String dischargeDisposition;

    private String wardDischargeFrom;

    private String deptDischargeFrom;

    private Date dischargeDateTime;

    private Date admissionDataTime;

    private Integer nursingGrade;

    private Integer visitTimes;

    private String diagnosis;

    private String status;

    private String inpNo;

    private String patientId;

    private String serviceAgency;

    private String nativePlace;

    private String maritalStatus;

    private String xzz;

    private String company;

    private String signUrl;

    private String ishospitalized;

    private String payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public String getContactIdcard() {
        return contactIdcard;
    }

    public void setContactIdcard(String contactIdcard) {
        this.contactIdcard = contactIdcard;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getPatientCertitype() {
        return patientCertitype;
    }

    public void setPatientCertitype(String patientCertitype) {
        this.patientCertitype = patientCertitype;
    }

    public String getNativeAddr() {
        return nativeAddr;
    }

    public void setNativeAddr(String nativeAddr) {
        this.nativeAddr = nativeAddr;
    }

    public String getPatientCertino() {
        return patientCertino;
    }

    public void setPatientCertino(String patientCertino) {
        this.patientCertino = patientCertino;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomeplace() {
        return homeplace;
    }

    public void setHomeplace(String homeplace) {
        this.homeplace = homeplace;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getHosno() {
        return hosno;
    }

    public void setHosno(String hosno) {
        this.hosno = hosno;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getRegisteredPermanent() {
        return registeredPermanent;
    }

    public void setRegisteredPermanent(String registeredPermanent) {
        this.registeredPermanent = registeredPermanent;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getPatAdmCondition() {
        return patAdmCondition;
    }

    public void setPatAdmCondition(String patAdmCondition) {
        this.patAdmCondition = patAdmCondition;
    }

    public String getAdmittedBy() {
        return admittedBy;
    }

    public void setAdmittedBy(String admittedBy) {
        this.admittedBy = admittedBy;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getConsultingDoctor() {
        return consultingDoctor;
    }

    public void setConsultingDoctor(String consultingDoctor) {
        this.consultingDoctor = consultingDoctor;
    }

    public String getDischargeDisposition() {
        return dischargeDisposition;
    }

    public void setDischargeDisposition(String dischargeDisposition) {
        this.dischargeDisposition = dischargeDisposition;
    }

    public String getWardDischargeFrom() {
        return wardDischargeFrom;
    }

    public void setWardDischargeFrom(String wardDischargeFrom) {
        this.wardDischargeFrom = wardDischargeFrom;
    }

    public String getDeptDischargeFrom() {
        return deptDischargeFrom;
    }

    public void setDeptDischargeFrom(String deptDischargeFrom) {
        this.deptDischargeFrom = deptDischargeFrom;
    }

    public Date getDischargeDateTime() {
        return dischargeDateTime;
    }

    public void setDischargeDateTime(Date dischargeDateTime) {
        this.dischargeDateTime = dischargeDateTime;
    }

    public Date getAdmissionDataTime() {
        return admissionDataTime;
    }

    public void setAdmissionDataTime(Date admissionDataTime) {
        this.admissionDataTime = admissionDataTime;
    }

    public Integer getNursingGrade() {
        return nursingGrade;
    }

    public void setNursingGrade(Integer nursingGrade) {
        this.nursingGrade = nursingGrade;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInpNo() {
        return inpNo;
    }

    public void setInpNo(String inpNo) {
        this.inpNo = inpNo;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getServiceAgency() {
        return serviceAgency;
    }

    public void setServiceAgency(String serviceAgency) {
        this.serviceAgency = serviceAgency;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getXzz() {
        return xzz;
    }

    public void setXzz(String xzz) {
        this.xzz = xzz;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getIshospitalized() {
        return ishospitalized;
    }

    public void setIshospitalized(String ishospitalized) {
        this.ishospitalized = ishospitalized;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}