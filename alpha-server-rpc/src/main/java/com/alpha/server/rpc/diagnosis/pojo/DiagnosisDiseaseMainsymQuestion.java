package com.alpha.server.rpc.diagnosis.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "diagnosis_disease_mainsym_question")
public class DiagnosisDiseaseMainsymQuestion implements Serializable {

    private static final long serialVersionUID = -3801945407246022795L;

    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 疾病编号
     */
    @Column(name = "disease_code")
    private String diseaseCode;

    /**
     * 主症状编号
     */
    @Column(name = "main_sym_code")
    private String mainSymCode;

    /**
     * 问题编号
     */
    @Column(name = "question_code")
    private String questionCode;
//
//	/**
//     * 排序字段，弃用
//     */
//	@Column(name = "question_order")
//	private Double questionOrder;

    /**
     * 问题权重
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * 同步时间（暂时不填）
     */
    @Column(name = "incre_flag")
    private String increFlag;

    /**
     * 同步-操作时间
     */
    @Column(name = "opera_flag")
    private String operaFlag;

    /**
     * 同步-操作类型（'I'增 ,'U'改,'D'删）
     */
    @Column(name = "operate_type")
    private String operateType;

    /**
     * 同步-当前版本
     */
    @Column(name = "data_version")
    private Integer dataVersion;

    /**
     * 同步-版本演变
     */
    @Column(name = "version_evolution")
    private String versionEvolution;

    /**
     * 来源
     */
    @Column(name = "source_")
    private String source;

    /**
     * 版本
     */
    @Column(name = "version_")
    private String version;

    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审核人
     */
    @Column(name = "reviewer")
    private String reviewer;

    /**
     * 审核时间
     */
    @Column(name = "review_time")
    private Date reviewTime;

    /**
     * 0 创建 ,1,审核 ，2，反审核，
     */
    @Column(name = "medical_data_status")
    private Integer medicalDataStatus;

    /**
     * 0 默认 ，1,测试 ，2 ，上线
     */
    @Column(name = "it_data_status")
    private Integer itDataStatus;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseCode() {
        return this.diseaseCode;
    }


    public void setMainSymCode(String mainSymCode) {
        this.mainSymCode = mainSymCode;
    }

    public String getMainSymCode() {
        return this.mainSymCode;
    }


    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionCode() {
        return this.questionCode;
    }


//	public void setQuestionOrder(Double questionOrder) {
//		this.questionOrder = questionOrder;
//	}
//
//	public Double getQuestionOrder() {
//		return this.questionOrder;
//	}


    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return this.weight;
    }


    public void setIncreFlag(String increFlag) {
        this.increFlag = increFlag;
    }

    public String getIncreFlag() {
        return this.increFlag;
    }


    public void setOperaFlag(String operaFlag) {
        this.operaFlag = operaFlag;
    }

    public String getOperaFlag() {
        return this.operaFlag;
    }


    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateType() {
        return this.operateType;
    }


    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getDataVersion() {
        return this.dataVersion;
    }


    public void setVersionEvolution(String versionEvolution) {
        this.versionEvolution = versionEvolution;
    }

    public String getVersionEvolution() {
        return this.versionEvolution;
    }


    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return this.creator;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }


    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewer() {
        return this.reviewer;
    }


    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() {
        return this.reviewTime;
    }


    public void setMedicalDataStatus(Integer medicalDataStatus) {
        this.medicalDataStatus = medicalDataStatus;
    }

    public Integer getMedicalDataStatus() {
        return this.medicalDataStatus;
    }


    public void setItDataStatus(Integer itDataStatus) {
        this.itDataStatus = itDataStatus;
    }

    public Integer getItDataStatus() {
        return this.itDataStatus;
    }


}
