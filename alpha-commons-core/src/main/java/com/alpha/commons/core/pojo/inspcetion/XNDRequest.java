package com.alpha.commons.core.pojo.inspcetion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by edz on 2018/10/27.
 */
@Entity
@Table(name = "xnd_request")
public class XNDRequest
{

    @Id
    private Integer id;
    private String requestNo;
    private String patientId;
    private String patientType;
    private String patientName;
    private String sex;
    private String birthday;
    private String receiveDate;
    private String deptRequestName;
    private String doctorRequestName;
    private String reportId;
    private String reportName;
    private String reportDept;
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

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
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

    public String getReportDept() {
        return reportDept;
    }

    public void setReportDept(String reportDept) {
        this.reportDept = reportDept;
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
