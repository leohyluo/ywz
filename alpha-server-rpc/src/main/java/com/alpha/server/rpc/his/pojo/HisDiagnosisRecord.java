package com.alpha.server.rpc.his.pojo;

import com.alpha.server.rpc.his.vo.DiseaseListVo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "his_diagnosis_record")
public class HisDiagnosisRecord {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    @Column(name = "main_symptom_name")
    private String mainSymptomName;

    @Column(name = "present_illness_history")
    private String presentIllnessHistory;

    @Column(name = "past_medical_history")
    private String pastMedicalHistory;

    @Column(name = "disease_info")
    private String diseaseInfo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private List<DiseaseListVo> diseases;

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

    public String getMainSymptomName() {
        return mainSymptomName;
    }

    public void setMainSymptomName(String mainSymptomName) {
        this.mainSymptomName = mainSymptomName;
    }

    public String getPresentIllnessHistory() {
        return presentIllnessHistory;
    }

    public void setPresentIllnessHistory(String presentIllnessHistory) {
        this.presentIllnessHistory = presentIllnessHistory;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    public String getDiseaseInfo() {
        return diseaseInfo;
    }

    public void setDiseaseInfo(String diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
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

    public List<DiseaseListVo> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<DiseaseListVo> diseases) {
        this.diseases = diseases;
    }
}
