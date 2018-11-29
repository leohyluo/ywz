package com.alpha.commons.core.pojo.inspcetion;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edz on 2018/10/27.
 */
@Entity
@Table(name = "fs_request")
public class FSRequest {

    @Id
    private Integer id;
    private String examSystemCode;
    private String hisPatientId;
    private String name;
    private String sex;
    private String age;
    private String snoType;
    private String examModality;
    private String regDate;
    private String examDeptName;
    private String examDoctorSendName;
    private String itemChName;
    private String examSno;
    private String recordDate;
    private Integer pushStatus;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamSystemCode() {
        return examSystemCode;
    }

    public void setExamSystemCode(String examSystemCode) {
        this.examSystemCode = examSystemCode;
    }

    public String getHisPatientId() {
        return hisPatientId;
    }

    public void setHisPatientId(String hisPatientId) {
        this.hisPatientId = hisPatientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSnoType() {
        return snoType;
    }

    public void setSnoType(String snoType) {
        this.snoType = snoType;
    }

    public String getExamModality() {
        return examModality;
    }

    public void setExamModality(String examModality) {
        this.examModality = examModality;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        try {
           Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(regDate);
            String da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            this.regDate = da;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getExamDeptName() {
        return examDeptName;
    }

    public void setExamDeptName(String examDeptName) {
        this.examDeptName = examDeptName;
    }

    public String getExamDoctorSendName() {
        return examDoctorSendName;
    }

    public void setExamDoctorSendName(String examDoctorSendName) {
        this.examDoctorSendName = examDoctorSendName;
    }

    public String getItemChName() {
        return itemChName;
    }

    public void setItemChName(String itemChName) {
        this.itemChName = itemChName;
    }

    public String getExamSno() {
        return examSno;
    }

    public void setExamSno(String examSno) {
        this.examSno = examSno;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }



}
