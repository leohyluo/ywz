package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.*;

import com.alpha.commons.enums.DisplayType;
import com.alpha.commons.enums.Unit;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "diagnosis_mainsymp_question")
public class DiagnosisMainsympQuestion implements Question, Serializable {


    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 问题编码
     */
    @Column(name = "question_code")
    private String questionCode;

    /**
     * 主症状编码
     */
    @Column(name = "main_symp_code")
    private String mainSympCode;

    /**
     * 问题内容
     */
    @Column(name = "title")
    private String title;

    /**
     * 问题内容通俗化
     */
    @Column(name = "popu_title")
    private String popuTitle;

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
     * 主症状名称
     */
    @Column(name = "symp_name")
    private String sympName;
    /**
     * 主症状名称>通俗化名称
     */
    @Column(name = "popu_name")
    private String popuName;

    /**
     * 顺序
     */
    @Column(name = "default_order")
    private Integer defaultOrder;

    /**
     * 问题类型 普通问题101，伴随症状问题102
     */
    @Column(name = "question_type")
    private Integer questionType;

    /**
     * 疾病编码
     */
    @Transient
    @Column(name = "disease_code")
    private String diseaseCode;

    /**
     * 疾病下问题权重
     */
    @Transient
    @Column(name = "weight")
    private Double questionWeight;

    /**
     * 答案数量
     */
    @Transient
    @Column(name = "answer_total")
    private Long answerTotal;

    /**
     * 问题权重，计算后的值
     */
    @Transient
    private Double weightValue;
    
    /**
     * 单选、多选
     */
    @Column(name = "display_type")
    private String displayType;

    /**
     * 控件显示范围
     */
    @Column(name = "display_range")
    private String displayRange;

    /**
     * 控件默认值
     */
    @Column(name = "display_default")
    private String displayDefault;
    
    /**
     * 转化类型(hour,day,month)
     */
    @Column(name = "parse_class")
    private String parseClass;
    
    @Column(name = "is_show")
    private Integer showFlag;
    
    /**
     * 依赖问题编码
     */
    @Column(name = "dependency_question_code")
    private String dependencyQuestionCode;

    /**
     * 是否为附加问题
     */
    @Column(name = "is_additional")
    private Integer isAdditional;
    /**
     *
     */
    @Column(name = "incre_flag")
    private String increFlag;

    /**
     *
     */
    @Column(name = "opera_flag")
    private String operaFlag;

    /**
     *
     */
    @Column(name = "operate_type")
    private String operateType;

    /**
     *
     */
    @Column(name = "data_version")
    private Integer dataVersion;

    /**
     *
     */
    @Column(name = "version_evolution")
    private String versionEvolution;

    /**
     * 来源
     */
    @Column(name = "source_")
    private String source;

    /**
     *
     */
    @Column(name = "version_")
    private String version;

    /**
     *
     */
    @Column(name = "creator")
    private String creator;

    /**
     *
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     *
     */
    @Column(name = "reviewer")
    private String reviewer;

    /**
     *
     */
    @Column(name = "review_time")
    private Date reviewTime;

    /**
     * 是否按医学指定的顺序显示
     */
    @Column(name = "maual_order")
    private Integer maualOrder;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionCode() {
        return this.questionCode;
    }


    public void setMainSympCode(String mainSympCode) {
        this.mainSympCode = mainSympCode;
    }

    public String getMainSympCode() {
        return this.mainSympCode;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


    public void setPopuTitle(String popuTitle) {
        this.popuTitle = popuTitle;
    }

    public String getPopuTitle() {
        return this.popuTitle;
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

    public String getSympName() {
        return sympName;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    public String getPopuName() {
        return popuName;
    }

    public void setPopuName(String popuName) {
        this.popuName = popuName;
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

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Double getQuestionWeight() {
        return questionWeight;
    }

    public void setQuestionWeight(Double questionWeight) {
        this.questionWeight = questionWeight;
    }

    public Double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Double weightValue) {
        this.weightValue = weightValue;
    }

    public Long getAnswerTotal() {
        return answerTotal;
    }

    public void setAnswerTotal(Long answerTotal) {
        this.answerTotal = answerTotal;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	
	public String getDependencyQuestionCode() {
		return dependencyQuestionCode;
	}

	public void setDependencyQuestionCode(String dependencyQuestionCode) {
		this.dependencyQuestionCode = dependencyQuestionCode;
	}

	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public String getParseClass() {
		if(this.displayType != null && this.displayType.equals(DisplayType.TEMPERATURE.getValue())) {
			parseClass = Unit.TEMPERATURE.getValue();
		}
		if(this.displayType != null && this.displayType.equals(DisplayType.NUM_OF_TIMES.getValue())) {
			parseClass = Unit.NUM_OF_TIMES.getValue();
		}
		return parseClass;
	}

	public void setParseClass(String parseClass) {
		this.parseClass = parseClass;
	}

    public Integer getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Integer isAdditional) {
        this.isAdditional = isAdditional;
    }

    public Integer getMaualOrder() {
        return maualOrder;
    }

    public void setMaualOrder(Integer maualOrder) {
        this.maualOrder = maualOrder;
    }

    public String getDisplayRange() {
        return displayRange;
    }

    public void setDisplayRange(String displayRange) {
        this.displayRange = displayRange;
    }

    public String getDisplayDefault() {
        return displayDefault;
    }

    public void setDisplayDefault(String displayDefault) {
        this.displayDefault = displayDefault;
    }
}
