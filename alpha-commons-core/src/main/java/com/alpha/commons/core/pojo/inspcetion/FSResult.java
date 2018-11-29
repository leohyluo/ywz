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
@Table(name = "fs_result")
public class FSResult {
    @Id
    private Integer id;
    private String examSno;
    private String examPartCode;
    private String examMethod;
    private String examResult;
    private String examDesc;
    private String examDate;
    private String examReportDate;
    private String examReportorName;
    private String examVerifyDoctorName;
    private String recordDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamReportorName() {
        return examReportorName;
    }

    public void setExamReportorName(String examReportorName) {
        this.examReportorName = examReportorName;
    }
    public String getExamSno() {
        return examSno;
    }

    public void setExamSno(String examSno) {
        this.examSno = examSno;
    }

    public String getExamPartCode() {
        return examPartCode;
    }

    public void setExamPartCode(String examPartCode) {
        this.examPartCode = examPartCode;
    }

    public String getExamMethod() {
        return examMethod;
    }

    public void setExamMethod(String examMethod) {
        this.examMethod = examMethod;
    }

    public String getExamResult() {
        return examResult;
    }

    public void setExamResult(String examResult) {
        this.examResult = examResult;
    }

    public String getExamDesc() {
        return examDesc;
    }

    public void setExamDesc(String examDesc) {
        this.examDesc = examDesc;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(examDate);
            String da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            this.examDate = da;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getExamReportDate() {
        return examReportDate;
    }

    public void setExamReportDate(String examReportDate) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(examReportDate);
            String da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            this.examReportDate = da;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getExamVerifyDoctorName() {
        return examVerifyDoctorName;
    }

    public void setExamVerifyDoctorName(String examVerifyDoctorName) {
        this.examVerifyDoctorName = examVerifyDoctorName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
