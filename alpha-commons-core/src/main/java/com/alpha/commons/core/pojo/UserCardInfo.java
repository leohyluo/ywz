package com.alpha.commons.core.pojo;

import com.alpha.commons.core.pojo.ao.UserCardInfoAO;
import com.alpha.commons.util.BeanCopierUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 * 用户开卡信息
 */
@Entity
@Table(name = "user_card_info")
public class UserCardInfo implements Serializable{

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "patientName")
    private String patientName; //患儿名字
    @Column(name = "birthday")
    private String birthday;//生日
    @Column(name = "sex")
    private Integer sex;//性别
    @Column(name = "nation")
    private Integer nation;//民族
    @Column(name = "nationality")
    private Integer nationality;//国家
    @Column(name = "contactName")
    private String contactName;//联系人名字
    @Column(name = "relationship")
    private Integer relationship;//联系人和患者关系
    @Column(name = "contactPhone")
    private String contactPhone;//联系人电话
    @Column(name = "contactIdCard")
    private String contactIdCard;

    public Integer getContactIdCardType() {
        return contactIdCardType;
    }

    public void setContactIdCardType(Integer contactIdCardType) {
        this.contactIdCardType = contactIdCardType;
    }

    @Column(name = "contactIdCardType")
    private Integer contactIdCardType;
    @Column(name = "patientCertiType")
    private Integer patientCertiType;//患者证件类型
    @Column(name = "patientCertiNo")
    private String patientCertiNo;//患者证件号码
    @Column(name = "school")
    private String school;//患者学校
    @Column(name = "address")
    private String address;//患者住址
    @Column(name = "cardNumber")
    private String cardNumber;//卡号
    @Column(name = "hospitalCode")
    private String hospitalCode;//医院编码
    @Column(name = "createtime") //创建时间
    private String createTime;
    @Column(name = "status")
    private Integer status;

    public Integer getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(Integer cardOrder) {
        this.cardOrder = cardOrder;
    }

    @Column(name = "cardOrder")
    private Integer cardOrder;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public UserCardInfo(){}
    public UserCardInfo(UserCardInfoAO ao){
        BeanCopierUtil.copy(ao,this);
    }

}
