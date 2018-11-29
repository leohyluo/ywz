package com.alpha.commons.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "diagnosis_disease")
public class DiagnosisDisease implements Serializable {

    private static final long serialVersionUID = -7272250285569874012L;
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
     * 疾病名称
     */
    @Column(name = "disease_name")
    private String diseaseName;

    /**
     * 通俗化名称(别名)
     */
    @Column(name = "popu_name")
    private String popuName;

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
     * 是否常见病
     */
    @Column(name = "is_common")
    private Boolean isCommon;

    /**
     * 是否危重病
     */
    @Column(name = "is_grave")
    private Boolean isGrave;

    /**
     * 疾病定义
     */
    @Column(name = "definition")
    private String definition;

    /**
     * 拼音助记符
     */
    @Column(name = "symbol")
    private String symbol;

    /**
     * 特殊时期
     */
    @Column(name = "special_period")
    private Integer specialPeriod;

    /**
     * 对应ICD10编码
     */
    @Column(name = "icd10_code")
    private String icd10Code;
    
    /**
     * 疾病概述
     */
    @Column(name = "disease_summary")
    private String diseaseSummary;
    
    /**
     * 指南意见
     */
    @Column(name = "guide_option")
    private String guideOption;
    
    /**
     * 家庭微治疗
     */
    @Column(name = "treat_family")
    private String treatFamily;
    
    /**
     * 疾病预防
     */
    @Column(name = "defend_option")
    private String defendOption;
    
    //智能排序阀值
    @Column(name = "threshold")
    private Integer threshold;
    
    //用户选择次数
    @Column(name = "user_select_count")
    private Integer userSelectCount;
    
    //默认排序
    @Column(name = "default_order")
    private String defaultOrder;

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


    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseName() {
        return this.diseaseName;
    }


    public void setPopuName(String popuName) {
        this.popuName = popuName;
    }

    public String getPopuName() {
        return this.popuName;
    }


    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return this.gender;
    }


    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMinAge() {
        return this.minAge;
    }


    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMaxAge() {
        return this.maxAge;
    }


    public void setIsCommon(Boolean isCommon) {
        this.isCommon = isCommon;
    }

    public Boolean getIsCommon() {
        return this.isCommon;
    }


    public void setIsGrave(Boolean isGrave) {
        this.isGrave = isGrave;
    }

    public Boolean getIsGrave() {
        return this.isGrave;
    }


    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return this.definition;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }


    public void setSpecialPeriod(Integer specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public Integer getSpecialPeriod() {
        return this.specialPeriod;
    }


    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    public String getIcd10Code() {
        return this.icd10Code;
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

	public String getDiseaseSummary() {
		return diseaseSummary;
	}

	public void setDiseaseSummary(String diseaseSummary) {
		this.diseaseSummary = diseaseSummary;
	}

	public String getGuideOption() {
		return guideOption;
	}

	public void setGuideOption(String guideOption) {
		this.guideOption = guideOption;
	}

	public String getTreatFamily() {
		return treatFamily;
	}

	public void setTreatFamily(String treatFamily) {
		this.treatFamily = treatFamily;
	}

	public String getDefendOption() {
		return defendOption;
	}

	public void setDefendOption(String defendOption) {
		this.defendOption = defendOption;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Integer getUserSelectCount() {
		return userSelectCount;
	}

	public void setUserSelectCount(Integer userSelectCount) {
		this.userSelectCount = userSelectCount;
	}

	public String getDefaultOrder() {
		return defaultOrder;
	}

	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}

}
