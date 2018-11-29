package com.alpha.pojo.vo;

import java.util.List;

/**
 * Created by edz on 2018/10/31.
 */
public class FSrequestVO {


    private String hisPatientId;
    private String name;
    private String sex;
    private String age;
    private String examModality;
    private String regDate;
    private String examDeptName;
    private String examDoctorSendName;
    private String itemChName;
    private String examSno;

    private List<FSresultVO> detail;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
        this.regDate = regDate;
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

    public List<FSresultVO> getDetail() {
        return detail;
    }

    public void setDetail(List<FSresultVO> detail) {
        this.detail = detail;
    }


}
