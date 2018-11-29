package com.alpha.server.rpc.his.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alpha.commons.core.pojo.BasePojo;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 住院-患者基本信息
 */
@Entity
@Table(name = "hospitalized_patient_info")
public class HospitalizedPatientInfo extends BasePojo<HospitalizedPatientInfo> implements Serializable {
    public static interface SaveGroup{}
    public static interface ModifyGroup{}
    /**
     * id
     */
    @Id
    @Column(name="id")
    @NotNull(message="主键不能为空!",groups = {ModifyGroup.class})
    private Long id;

    /**
     * 医院编码
     */
    @Column(name="hospital_code")
    private String hospitalCode;

    /**
     * 门诊号
     */
    @Column(name="out_patient_no")
    private String outPatientNo;

    /**
     * 住院号
     */
    @Column(name="hosNo")
    private String hosno;

    /**
     * 患者姓名
     */
    @Column(name="patient_name")
    @NotBlank(message="患者姓名不能为空!",groups = {SaveGroup.class})
    private String patientName;

    /**
     * 患者性别
     */
    @Column(name="sex")
    private String sex;

    /**
     * 出生日期
     */
    @Column(name="birthday")
    private String birthday;

    /**
     * 国籍
     */
    @Column(name="nationality")
    @NotBlank(message="国籍不能为空!",groups = {SaveGroup.class})
    private String nationality;

    /**
     * 民族
     */
    @Column(name="nation")
    @NotBlank(message="民族不能为空!",groups = {SaveGroup.class})
    private String nation;

    /**
     * 联系人电话
     */
    @Column(name="contact_phone")
    private String contactPhone;

    /**
     * 联系人姓名
     */
    @Column(name="contact_name")
    private String contactName;

    /**
     * 联系人身份证号
     */
    @Column(name="contact_idcard")
    private String contactIdcard;

    /**
     * 与患者的关系 0:父亲 1:母亲 2:其它
     */
    @Column(name="relationship")
    private String relationship;

    /**
     * 患者证件类型 0: 身份证件 1: 学生证
     */
    @Column(name="patient_certiType")
    private String patientCertitype;

    /**
     * 患者证件号
     */
    @Column(name="patient_certiNo")
    private String patientCertino;

    /**
     * 学校
     */
    @Column(name="school")
    private String school;

    /**
     * 住址
     */
    @Column(name="address")
    @NotBlank(message="住址不能为空!",groups = {SaveGroup.class})
    private String address;
    /**
     * 出生地
     */
    @Column(name="homeplace")
    private String homeplace;
    /**
     * 户口所在地
     */
    @Column(name="registered_permanent")
    private String registeredPermanent;
    /**
     * 是否在本院住过院
     */
    @Column(name="isHospitalized")
    private String isHospitalized;
    @NotNull
    @Valid
    @JSONField(serialize = false)
    private HospitalizedNotice notice;
//    @Column(name = "create_time")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @JsonSerialize(using = JSONDateSerializer.class)
//    private Date createTime;
//
//    @Column(name = "update_time")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @JsonSerialize(using = JSONDateSerializer.class)
//    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo == null ? null : outPatientNo.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactIdcard() {
        return contactIdcard;
    }

    public void setContactIdcard(String contactIdcard) {
        this.contactIdcard = contactIdcard == null ? null : contactIdcard.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getPatientCertitype() {
        return patientCertitype;
    }

    public void setPatientCertitype(String patientCertitype) {
        this.patientCertitype = patientCertitype == null ? null : patientCertitype.trim();
    }

    public String getPatientCertino() {
        return patientCertino;
    }

    public void setPatientCertino(String patientCertino) {
        this.patientCertino = patientCertino == null ? null : patientCertino.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getHosno() {
        return hosno;
    }

    public void setHosno(String hosno) {
        this.hosno = hosno;
    }

    public HospitalizedNotice getNotice() {
        return notice;
    }

    public String getHomeplace() {
        return homeplace;
    }

    public void setHomeplace(String homeplace) {
        this.homeplace = homeplace;
    }

    public String getRegisteredPermanent() {
        return registeredPermanent;
    }

    public void setRegisteredPermanent(String registeredPermanent) {
        this.registeredPermanent = registeredPermanent;
    }

    public String getIsHospitalized() {
        return isHospitalized;
    }

    public void setIsHospitalized(String isHospitalized) {
        this.isHospitalized = isHospitalized;
    }

    public void setNotice(HospitalizedNotice notice) {
        this.notice = notice;
    }
}