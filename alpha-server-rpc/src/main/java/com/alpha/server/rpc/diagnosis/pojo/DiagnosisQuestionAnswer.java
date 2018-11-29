package com.alpha.server.rpc.diagnosis.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "diagnosis_question_answer")
public class DiagnosisQuestionAnswer implements Answer, Serializable {

    private static final long serialVersionUID = 982534919320049029L;

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
     * 问题权重
     */
    @Column(name = "question_weight")
    private Double questionWeight;

    /**
     * 答案编码
     */
    @Column(name = "answer_code")
    private String answerCode;

    @Column(name = "answer_title")
    private String answerTitle;

    /**
     * 答案范围值的最小值
     */
    @Column(name = "min_value")
    private Double minValue;

    /**
     * 答案范围值的最大值
     */
    @Column(name = "max_value")
    private Double maxValue;

    /**
     * 答案内容
     */
    @Transient
    @Column(name = "content")
    private String content;

    /**
     * 答案描述
     */
    @Transient
    @Column(name = "popu_content")
    private String popuContent;

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
    
    @Transient
    @Column(name = "answer_image")
    private String image;

    /**
     * 疾病编码
     */
    @Column(name = "disease_code")
    private String diseaseCode;

    /**
     * 1正向特异性,-1反向特异性，0无
     */
    @Column(name = "answer_spec")
    private Integer answerSpec;

    /**
     * 权重
     */
    @Column(name = "weight")
    private Double weight;
    
    /**
     * 所属答案大类
     */
    @Transient
    private SyDiagnosisAnswer syAnswer;
    
    /**
     * 所属大类编码
     */
    @Column(name = "conn_code")
    private String syAnswerCode;
    
    /**
     * 计算公式
     */
    @Column(name = "calculation_formula")
    private String calculationFormula;

    /**
     * 顺序
     */
    @Column(name = "default_order")
    private Integer defaultOrder;

    /**
     * 下一个问题id
     */
    @Column(name = "next_question_id")
    private String nextQuestionId;

    /**
     * 答案权重，计算后的值
     */
    @Transient
    private Double weightValue;
    
    /**
     * 是否存在互相排斥属性，1=存在；如果存在，默认选中互斥的答案
     */
    @Column(name = "mutually_exclusive")
    private Integer mutuallyExclusive;
    
    /**
     * 互斥答案编码
     */
    @Column(name = "mutually_answer_code")
    private String mutuallyAnswerCode;

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


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionCode() {
        return this.questionCode;
    }


    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerCode() {
        return this.answerCode;
    }


    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseCode() {
        return this.diseaseCode;
    }

    public String getPopuContent() {
        return popuContent;
    }

    public void setPopuContent(String popuContent) {
        this.popuContent = popuContent;
    }

    public void setAnswerSpec(Integer answerSpec) {
        this.answerSpec = answerSpec;
    }

    public Integer getAnswerSpec() {
        return this.answerSpec;
    }


    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return this.weight;
    }


    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public Integer getDefaultOrder() {
        return this.defaultOrder;
    }


    public void setNextQuestionId(String nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public String getNextQuestionId() {
        return this.nextQuestionId;
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

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
    
    public Integer getMutuallyExclusive() {
		return mutuallyExclusive;
	}

	public void setMutuallyExclusive(Integer mutuallyExclusive) {
		this.mutuallyExclusive = mutuallyExclusive;
	}

	public String getMutuallyAnswerCode() {
		return mutuallyAnswerCode;
	}

	public void setMutuallyAnswerCode(String mutuallyAnswerCode) {
		this.mutuallyAnswerCode = mutuallyAnswerCode;
	}

	public SyDiagnosisAnswer getSyAnswer() {
		return syAnswer;
	}

	public void setSyAnswer(SyDiagnosisAnswer syAnswer) {
		this.syAnswer = syAnswer;
	}

	public String getSyAnswerCode() {
		return syAnswerCode;
	}

	public void setSyAnswerCode(String syAnswerCode) {
		this.syAnswerCode = syAnswerCode;
	}

	public String getCalculationFormula() {
		return calculationFormula;
	}

	public void setCalculationFormula(String calculationFormula) {
		this.calculationFormula = calculationFormula;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAnswerTitle() {
		return answerTitle;
	}

	public void setAnswerTitle(String answerTitle) {
		this.answerTitle = answerTitle;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiagnosisQuestionAnswer)) return false;

        DiagnosisQuestionAnswer answer = (DiagnosisQuestionAnswer) o;

        if (!questionCode.equals(answer.questionCode)) return false;
        if (!answerCode.equals(answer.answerCode)) return false;
        if(StringUtils.isEmpty(diseaseCode) || StringUtils.isEmpty(this.diseaseCode)) return false;
        return diseaseCode.equals(answer.diseaseCode);
    }

    @Override
    public int hashCode() {
        int result = questionCode.hashCode();
        result = 31 * result + answerCode.hashCode();
        //答案不清楚没有关联的疾病和权重
        if(StringUtils.isNotEmpty(diseaseCode))
        	result = 31 * result + diseaseCode.hashCode();
        return result;
    }
}
