package com.alpha.server.rpc.his.pojo;

import com.alpha.commons.core.pojo.BasePojo;
import com.alpha.commons.core.util.JSONDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 住院-通知单信息
 */
@Entity
@Table(name = "hospitalized_notice")
public class HospitalizedNotice extends BasePojo<HospitalizedNotice> implements Serializable {
    public static interface SaveGroup {
    }

    public static interface ModifyGroup {
    }

    /**
     * id
     */
    @Id
    @Column(name = "id")
    @NotBlank(message = "主键不能为空!", groups = {HospitalizedPatientInfo.SaveGroup.class})
    private Long id;

    @Column(name = "noticeId")
    @NotBlank(message = "通知单编码不能为空!", groups = {HospitalizedPatientInfo.ModifyGroup.class})
    private String noticeId;

    /**
     * 医院编码
     */
    @Column(name = "hospital_code")
    private String hospitalCode;

    /**
     * 门诊号
     */
    @Column(name = "out_patient_no")
    private String outPatientNo;

    /**
     * 住院结算方式
     */
    @Column(name = "payType")
    @NotBlank(message = "住院结算方式不能为空!", groups = {HospitalizedPatientInfo.SaveGroup.class})
    private String paytype;

    /**
     * 住院号
     */
    @Column(name = "hosNo")
    private String hosno;

    /**
     * 入院科室
     */
    @Column(name = "inDep")
    private String indep;

    /**
     * 入院方式
     */
    @Column(name = "inType")
    private String intype;

    /**
     * 入院情况
     */
    @Column(name = "inCase")
    private String incase;

    /**
     * 入院途径
     */
    @Column(name = "inChannel")
    private String inchannel;

    /**
     * 初步诊断
     */
    @Column(name = "diagnosis")
    private String diagnosis;

    /**
     * 是否收感染床
     */
    @Column(name = "isFect")
    private String isfect;

    /**
     * 通知日期
     */
    @Column(name = "notifyTime")
    private String notifytime;

    /**
     * 联系电话
     */
    @Column(name = "contactPhone")
    private String contactphone;

    /**
     * 病情描述
     */
    @Column(name = "disDesc")
    private String disdesc;

    /**
     * 签名
     */
    private String signUrl;
    /**
     * 签章
     */
    private String signature;
    /**
     * 生日
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @JsonSerialize(using = JSONDateSerializer.class)
    private Date birthday;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 患者名字
     */
    private String patientName;
    /**
     * 年龄
     */
    private String age;
//    @Column(name = "create_time")
//    private Date createTime;
//
//    @Column(name = "update_time")
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

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public String getHosno() {
        return hosno;
    }

    public void setHosno(String hosno) {
        this.hosno = hosno == null ? null : hosno.trim();
    }

    public String getIndep() {
        return indep;
    }

    public void setIndep(String indep) {
        this.indep = indep == null ? null : indep.trim();
    }

    public String getIntype() {
        return intype;
    }

    public void setIntype(String intype) {
        this.intype = intype == null ? null : intype.trim();
    }

    public String getIncase() {
        return incase;
    }

    public void setIncase(String incase) {
        this.incase = incase == null ? null : incase.trim();
    }

    public String getInchannel() {
        return inchannel;
    }

    public void setInchannel(String inchannel) {
        this.inchannel = inchannel == null ? null : inchannel.trim();
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis == null ? null : diagnosis.trim();
    }

    public String getIsfect() {
        return isfect;
    }

    public void setIsfect(String isfect) {
        this.isfect = isfect == null ? null : isfect.trim();
    }

    public String getNotifytime() {
        return notifytime;
    }

    public void setNotifytime(String notifytime) {
        this.notifytime = notifytime == null ? null : notifytime.trim();
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone == null ? null : contactphone.trim();
    }

    public String getDisdesc() {
        return disdesc;
    }

    public void setDisdesc(String disdesc) {
        this.disdesc = disdesc == null ? null : disdesc.trim();
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl == null ? null : signUrl.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}