package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "diagnosis_suballergic_history")
public class DiagnosisSuballergicHistory {

    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 疾病编码
     */
    @Column(name = "disease_code")
    private String diseaseCode;

    /**
     * 所属大类编码
     */
    @Column(name = "parent_disease_code")
    private String parentDiseaseCode;

    /**
     * 疾病名称
     */
    @Column(name = "disease_name")
    private String diseaseName;

    /**
     * 通俗化疾病名称
     */
    @Column(name = "popu_name")
    private String popuName;

    /**
     * 拼音助记符
     */
    @Column(name = "symbol")
    private String symbol;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 最小年龄
     */
    @Column(name = "min_age")
    private Double minAge;

    /**
     * 最大年龄
     */
    @Column(name = "max_age")
    private Double maxAge;

    /**
     * 序号
     */
    @Column(name = "default_order")
    private Integer defaultOrder;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 特殊时期
     */
    @Column(name = "special_period")
    private Integer specialPeriod;

    /**
     * 是否肾功能不全
     */
    @Column(name = "is_renal")
    private Boolean isRenal;

    /**
     * 是否肝功能不全
     */
    @Column(name = "is_liver")
    private Boolean isLiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getParentDiseaseCode() {
        return parentDiseaseCode;
    }

    public void setParentDiseaseCode(String parentDiseaseCode) {
        this.parentDiseaseCode = parentDiseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getPopuName() {
        return popuName;
    }

    public void setPopuName(String popuName) {
        this.popuName = popuName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getMinAge() {
        return minAge;
    }

    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSpecialPeriod() {
        return specialPeriod;
    }

    public void setSpecialPeriod(Integer specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public Boolean getIsRenal() {
        return isRenal;
    }

    public void setIsRenal(Boolean isRenal) {
        this.isRenal = isRenal;
    }

    public Boolean getIsLiver() {
        return isLiver;
    }

    public void setIsLiver(Boolean isLiver) {
        this.isLiver = isLiver;
    }


}
