package com.alpha.server.rpc.diagnosis.pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "diagnosis_mainsymp_concsymp")
public class DiagnosisMainsympConcsymp implements Answer, Serializable {

    private static final long serialVersionUID = 8717362722851998796L;

    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 主症状编码
     */
    @Column(name = "main_symp_code")
    private String mainSympCode;

    /**
     * 疾病编码
     */
    @Column(name = "disease_code")
    private String diseaseCode;

    /**
     * 伴随症状编码
     */
    @Column(name = "conc_symp_code")
    private String concSympCode;

    /**
     * 伴随症状名称
     */
    @Transient
    @Column(name = "symp_name")
    private String sympName;

    /**
     * 通俗化名称
     */
    @Transient
    @Column(name = "popu_name")
    private String popuName;
    /**
     * 拼音助记符
     */
    @Transient
    @Column(name = "symbol")
    private String symbol;
    /**
     * 性别
     */
    @Transient
    @Column(name = "gender")
    private Integer gender;
    /**
     * 最小年龄
     */
    @Transient
    @Column(name = "min_age")
    private Double minAge;
    /**
     * 最大年龄
     */
    @Transient
    @Column(name = "max_age")
    private Double maxAge;


    /**
     * 1正向特异性 -1反向特异性
     */
    @Column(name = "symp_spec")
    private Integer sympSpec;

    /**
     * 权重
     */
    @Column(name = "rate")
    private Double rate;
    
    /**
     * 排序
     */
    @Column(name = "default_order")
    private Integer defaultOrder;

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
    
    /**
     * 客户端显示类型
     */
    @Column(name = "display_type")
    private String displayType;

    /**
     * 同步伴随症状字典表is_common字段
     */
    @Transient
    @Column(name = "is_common")
    private Integer isCommon;

    /**
     * 关键词相似度
     */
    @JsonIgnore
    @Transient
    private Double similarity;

    @Column(name = "type_flag")
    private Integer typeFlag;

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setMainSympCode(String mainSympCode) {
        this.mainSympCode = mainSympCode;
    }

    public String getMainSympCode() {
        return this.mainSympCode;
    }


    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseCode() {
        return this.diseaseCode;
    }


    public void setConcSympCode(String concSympCode) {
        this.concSympCode = concSympCode;
    }

    public String getConcSympCode() {
        return this.concSympCode;
    }


    public void setSympSpec(Integer sympSpec) {
        this.sympSpec = sympSpec;
    }

    public Integer getSympSpec() {
        return this.sympSpec;
    }


    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return this.rate;
    }


    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public Integer getDefaultOrder() {
        return this.defaultOrder;
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

    public String getSympName() {
        return sympName;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
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

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

    public Integer getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(Integer isCommon) {
        this.isCommon = isCommon;
    }

    public Integer getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Integer typeFlag) {
        this.typeFlag = typeFlag;
    }
}
