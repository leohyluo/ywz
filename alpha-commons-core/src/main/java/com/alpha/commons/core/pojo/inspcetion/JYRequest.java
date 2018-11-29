package com.alpha.commons.core.pojo.inspcetion;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by edz on 2018/10/27.
 * 检验请求单
 */
@Entity
@Table(name = "jy_request")
public class JYRequest {
    @Id
    private Integer id;
    private String requestNo;
    private String patientId;
    private String patientName;
    private String patientType;
    private String sex;
    private String age;
    private String diagnose;
    private String specimenType;
    private String receiveDate;
    private String deptRequestName;
    private String doctorRequestName;
    private String reportId;
    private String reportName;
    private String deptReportName;
    private String reporter;
    private String reportDate;
    private String reportStatus;
    private String recordDate;
    private Integer pushStatus;

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }
    public String getDeptReportName() {
        return deptReportName;
    }

    public void setDeptReportName(String deptReportName) {
        this.deptReportName = deptReportName;
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

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(String specimenType) {
        this.specimenType = specimenType;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getDeptRequestName() {
        return deptRequestName;
    }

    public void setDeptRequestName(String deptRequestName) {
        this.deptRequestName = deptRequestName;
    }

    public String getDoctorRequestName() {
        return doctorRequestName;
    }

    public void setDoctorRequestName(String doctorRequestName) {
        this.doctorRequestName = doctorRequestName;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }


    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }


}
