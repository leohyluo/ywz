package com.alpha.commons.core.pojo.inspcetion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edz on 2018/10/31.
 */
@Entity
@Table(name = "cs_report")
public class CSReport {

    @Id
    private Integer id;
    private String requestId;
    private String hospitalizedNo;
    private String name;
    private String sex;
    private String studyAge;
    private String studyMethod;
    private String studyDate;
    private String requestDept;
    private String requestPhysician;
    private String reportDate;
    private String reporter;
    private String studyPart;
    private String conclusion;
    private String report;
    private String recordDate;
    private Integer pushStatus;
    private String patientType;


    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }


    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(studyDate);
            String da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            this.studyDate = da;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHospitalizedNo() {
        return hospitalizedNo;
    }

    public void setHospitalizedNo(String hospitalizedNo) {
        this.hospitalizedNo = hospitalizedNo;
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

    public String getStudyAge() {
        return studyAge;
    }

    public void setStudyAge(String studyAge) {
        this.studyAge = studyAge;
    }

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getRequestDept() {
        return requestDept;
    }

    public void setRequestDept(String requestDept) {
        this.requestDept = requestDept;
    }

    public String getRequestPhysician() {
        return requestPhysician;
    }

    public void setRequestPhysician(String requestPhysician) {
        this.requestPhysician = requestPhysician;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(reportDate);
            String da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            this.reportDate = da;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getStudyPart() {
        return studyPart;
    }

    public void setStudyPart(String studyPart) {
        this.studyPart = studyPart;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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
