package com.alpha.commons.core.pojo;


import com.alpha.commons.util.BeanCopierUtil;

/**
 * Created by Administrator on 2018/3/14.
 */
public class UserCardInfoVo {

    private Integer id;

    private String patientName; //患儿名字

    private String birthday;//生日

    private Integer sex;//性别

    private Integer nation;//民族

    private Integer nationality;//国家

    private String contactName;//联系人名字

    private Integer relationship;//联系人和患者关系

    private String contactPhone;//联系人电话

    private String contactIdCard; //联系人身份证号码

    private Integer contactIdCardType;

    private Integer patientCertiType;//患者证件类型

    private String patientCertiNo;//患者证件号码

    private String school;//患者学校

    private String address;//患者住址

    private String hospitalCode;//医院编码

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

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public Integer getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactIdCard() {
        return contactIdCard;
    }

    public void setContactIdCard(String contactIdCard) {
        this.contactIdCard = contactIdCard;
    }

    public Integer getPatientCertiType() {
        return patientCertiType;
    }

    public void setPatientCertiType(Integer patientCertiType) {
        this.patientCertiType = patientCertiType;
    }

    public String getPatientCertiNo() {
        return patientCertiNo;
    }

    public void setPatientCertiNo(String patientCertiNo) {
        this.patientCertiNo = patientCertiNo;
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

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public UserCardInfoVo(){}

    public UserCardInfoVo(UserCardInfo userCardInfo){
        BeanCopierUtil.copy(userCardInfo,this);
    }

    public Integer getContactIdCardType() {
        return contactIdCardType;
    }

    public void setContactIdCardType(Integer contactIdCardType) {
        this.contactIdCardType = contactIdCardType;
    }

}
