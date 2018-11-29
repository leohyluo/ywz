package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_medical_record")
public class UserMedicalRecord {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    @Column(name = "main_symptom")
    private String mainSymptom;

    @Column(name = "history_of_present")
    private String historyOfPresent;

    @Column(name = "history_of_past")
    private String historyOfPast;

    @Column(name = "history_of_menstruation")
    private String historyOfMenstruation;

    @Column(name = "history_of_marriage")
    private String historyOfMarriage;

    @Column(name = "history_of_birth")
    private String historyOfBirth;

    @Column(name = "diagnosis_result")
    private String diagnosisResult;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "import_flag")
    private Integer importFlag;

    public Integer getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(Integer importFlag) {
        this.importFlag = importFlag;
    }

    public UserMedicalRecord() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getMainSymptom() {
        return mainSymptom;
    }

    public void setMainSymptom(String mainSymptom) {
        this.mainSymptom = mainSymptom;
    }

    public String getHistoryOfPresent() {
        return historyOfPresent;
    }

    public void setHistoryOfPresent(String historyOfPresent) {
        this.historyOfPresent = historyOfPresent;
    }

    public String getHistoryOfPast() {
        return historyOfPast;
    }

    public void setHistoryOfPast(String historyOfPast) {
        this.historyOfPast = historyOfPast;
    }

    public String getHistoryOfMarriage() {
        return historyOfMarriage;
    }

    public void setHistoryOfMarriage(String historyOfMarriage) {
        this.historyOfMarriage = historyOfMarriage;
    }

    public String getHistoryOfBirth() {
        return historyOfBirth;
    }

    public void setHistoryOfBirth(String historyOfBirth) {
        this.historyOfBirth = historyOfBirth;
    }

    public String getHistoryOfMenstruation() {
        return historyOfMenstruation;
    }

    public void setHistoryOfMenstruation(String historyOfMenstruation) {
        this.historyOfMenstruation = historyOfMenstruation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDiagnosisResult() {
        return diagnosisResult;
    }

    public void setDiagnosisResult(String diagnosisResult) {
        this.diagnosisResult = diagnosisResult;
    }
}
