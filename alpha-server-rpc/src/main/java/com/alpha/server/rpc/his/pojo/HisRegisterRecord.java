package com.alpha.server.rpc.his.pojo;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.enums.RegisterType;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.util.Date;

@Entity
@Table(name = "his_register_record")
public class HisRegisterRecord {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hospital_code")
    private String hospitalCode;

    @Column(name = "out_patient_no")
    private String outPatientNo;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "pno")
    private String pno;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "patient_card_no")
    private String idCard;

    @Column(name = "dep_name")
    private String deptName;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "visit_time")
    private String visitTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    //挂号类型，1 现场挂号，2 预约挂号，3，预约挂号到现场取号
    @Column(name = "type")
    private Integer type;

    //1:取号完成
    @Column(name = "fetch_complete")
    private String fetchComplete;

    public HisRegisterRecord() {}

    public HisRegisterRecord(HisRegisterYygh registerInfo) {
        this.outPatientNo = registerInfo.getOutPatientNo();
        this.patientName = registerInfo.getPatientName();
        if(registerInfo.getType() == Integer.parseInt(RegisterType.APPOINTMENT.getValue())) {
            this.pno = registerInfo.getYno();
        } else {
            this.pno = registerInfo.getPno();
        }
        this.sex = registerInfo.getSex();
        this.birthday = registerInfo.getBirthday();
        this.idCard = registerInfo.getPatientCardNo();
        this.deptName = registerInfo.getDeptName();
        this.doctorName = registerInfo.getDoctorName();
        this.setType(registerInfo.getType());
        if(StringUtils.isNotEmpty(registerInfo.getVisitTime())) {
            try {
                Date date = DateUtils.string2Date(registerInfo.getVisitTime());
                String visitTime = DateUtils.date2String(date, DateUtils.DATE_FORMAT);
                this.visitTime = visitTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        this.createTime = new Date();
        this.updateTime = new Date();
    }

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
        this.hospitalCode = hospitalCode;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFetchComplete() {
        return fetchComplete;
    }

    public void setFetchComplete(String fetchComplete) {
        this.fetchComplete = fetchComplete;
    }
}
