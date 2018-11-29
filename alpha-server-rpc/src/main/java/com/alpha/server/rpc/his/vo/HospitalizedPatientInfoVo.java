package com.alpha.server.rpc.his.vo;

import com.alpha.commons.core.pojo.BasePojo;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class HospitalizedPatientInfoVo extends BasePojo<HospitalizedPatientInfoVo> implements Serializable {
    public static interface SaveGroup {
    }

    public static interface ModifyGroup {
    }

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空!", groups = {ModifyGroup.class})
    private Long id;

    /**
     * 通知单编码
     */
    @NotBlank(message = "通知单编码不能为空!", groups = {SaveGroup.class})
    private String noticeId;
    /**
     * 患者姓名
     */
    @NotBlank(message = "患者姓名不能为空!", groups = {SaveGroup.class})
    @Length(max = 50)
    private String patientName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 年龄
     */
    private String age;
    /**
     * 国籍
     */
    @NotNull(message = "国籍不能为空!", groups = {SaveGroup.class})
    private Integer nationality;
    /**
     * 民族
     */
    @NotNull(message = "民族不能为空!", groups = {SaveGroup.class})
    private Integer nation;
    /**
     * 医院编码
     */
    private String hospitalCode;
    /**
     * 门诊号
     */
    private String outPatientNo;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人地址
     */
    private String contactAddr;
    /**
     * 联系人身份证号
     */
    private String contactIdcard;
    /**
     * 与患者的关系 0:父亲 1:母亲 2:其它
     */
    private Integer relationship;
    /**
     * 患者证件类型
     */
    private String patientCertitype;
    /**
     * 籍贯
     */
    private String nativeAddr;
    /**
     * 患者证件号
     */
    private String patientCertiNo;
    /**
     * 出生地
     */
    @NotNull(message = "出生地不能为空!", groups = {SaveGroup.class})
    @Length(max = 100)
    private String homePlace;
    /**
     * 住院号
     */
    private String hosno;
    /**
     * 户口所在地
     */
    @NotNull(message = "户口所在地不能为空!", groups = {ModifyGroup.class})
    @Length(max = 100)
    private String registeredPermanent;
    /**
     * 当前床位号
     */
    private String bedNo;
    /**
     * 现在住址
     */
    @NotNull(message = "现在住址不能为空!", groups = {ModifyGroup.class})
    @Length(max = 100)
    private String xzz;
    /**
     * 签名图片地址
     */
    private String signUrl;

    private Integer isHospital; //是否住过本院

    private String insurance;//医保类型名称

    private String  mailingAddress;//户口地址

    @NotNull
    @Valid
    @JsonIgnore
    private HospitalizedNotice notice;

    private Integer patientType;

    private Integer nativePlace;//籍贯代码

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

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
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

    public String getPatientCertiNo() {
        return patientCertiNo;
    }

    public void setPatientCertiNo(String patientCertiNo) {
        this.patientCertiNo = patientCertiNo;
    }

    public String getHomePlace() {
        return homePlace;
    }

    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    public String getHosno() {
        return hosno;
    }

    public void setHosno(String hosno) {
        this.hosno = hosno;
    }

    public String getRegisteredPermanent() {
        return registeredPermanent;
    }

    public void setRegisteredPermanent(String registeredPermanent) {
        this.registeredPermanent = registeredPermanent;
    }

    public String getXzz() {
        return xzz;
    }

    public void setXzz(String xzz) {
        this.xzz = xzz;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public HospitalizedNotice getNotice() {
        return notice;
    }

    public void setNotice(HospitalizedNotice notice) {
        this.notice = notice;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getIsHospital() {
        return isHospital;
    }

    public void setIsHospital(Integer isHospital) {
        this.isHospital = isHospital;
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

    public Integer getPatientType() {
        return patientType;
    }

    public void setPatientType(Integer patientType) {
        this.patientType = patientType;
    }

    public Integer getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public Integer getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(Integer nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}