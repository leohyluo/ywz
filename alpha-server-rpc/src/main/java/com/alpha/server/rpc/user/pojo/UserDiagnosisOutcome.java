package com.alpha.server.rpc.user.pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_diagnosis_outcome")
public class UserDiagnosisOutcome {

    /**
     *
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 诊断号
     */
    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    /**
     * 疾病名称
     */
    @Column(name = "disease_name")
    private String diseaseName;

    /**
     * 疾病编码
     */
    @Column(name = "disease_code")
    private String diseaseCode;
    /**
     * 疾病描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 特异性属性
     */
    @JsonIgnore
    @Column(name = "answer_spec")
    private Integer answerSpec;

    /**
     * 发病概率
     */
    @Column(name = "probability")
    private Double probability;

    /**
     * 计算出来的权重
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * 计算公式
     */
    @JsonIgnore
    @Column(name = "calculation_formula")
    private String calculationFormula;
    /**
     * 治疗建议
     */
    @Column(name = "suggest")
    private String suggest;

    /**
     * 是否用户/医生确认的疾病
     */
    @Column(name = "confirm")
    private Integer confirm;

    @Transient
    @Column(name = "system_code")
    private String systemCode;

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

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Integer getAnswerSpec() {
        return answerSpec;
    }

    public void setAnswerSpec(Integer answerSpec) {
        this.answerSpec = answerSpec;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCalculationFormula() {
        return calculationFormula;
    }

    public void setCalculationFormula(String calculationFormula) {
        this.calculationFormula = calculationFormula;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
