package com.alpha.server.rpc.his.pojo;

import com.alpha.commons.core.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserCard extends BasePojo<UserCard> {
    private Long id;

    private String patientname;

    private String birthday;

    private String contactphone;

    private Integer sex;

    private String nation;

    private String nationality;

    private String contactname;

    private Long relationship;

    private String contactidcard;

    private Long patientcertitype;

    private String patientcertino;

    private String school;

    private String address;

    private String cardnumber;

    private String hospitalcode;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    private Long status;

    private Long contactidcardtype;

    private Long cardorder;

    private String qrCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname == null ? null : patientname.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone == null ? null : contactphone.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname == null ? null : contactname.trim();
    }

    public Long getRelationship() {
        return relationship;
    }

    public void setRelationship(Long relationship) {
        this.relationship = relationship;
    }

    public String getContactidcard() {
        return contactidcard;
    }

    public void setContactidcard(String contactidcard) {
        this.contactidcard = contactidcard == null ? null : contactidcard.trim();
    }

    public Long getPatientcertitype() {
        return patientcertitype;
    }

    public void setPatientcertitype(Long patientcertitype) {
        this.patientcertitype = patientcertitype;
    }

    public String getPatientcertino() {
        return patientcertino;
    }

    public void setPatientcertino(String patientcertino) {
        this.patientcertino = patientcertino == null ? null : patientcertino.trim();
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

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public String getHospitalcode() {
        return hospitalcode;
    }

    public void setHospitalcode(String hospitalcode) {
        this.hospitalcode = hospitalcode == null ? null : hospitalcode.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getContactidcardtype() {
        return contactidcardtype;
    }

    public void setContactidcardtype(Long contactidcardtype) {
        this.contactidcardtype = contactidcardtype;
    }

    public Long getCardorder() {
        return cardorder;
    }

    public void setCardorder(Long cardorder) {
        this.cardorder = cardorder;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}