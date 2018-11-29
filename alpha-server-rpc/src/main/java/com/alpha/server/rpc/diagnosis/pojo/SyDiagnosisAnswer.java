package com.alpha.server.rpc.diagnosis.pojo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 答案小类、同义词等相关表
 * @author Administrator
 *
 */
@Entity
@Table(name = "sy_diagnosis_answer")
public class SyDiagnosisAnswer implements Serializable {


    private static final long serialVersionUID = 3300750084893617949L;
    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;
    
    /**
     * 答案小类编码
     */
    @Column(name = "answer_code")
    private String answerCode;

    /**
     * 答案内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 答案内容通俗化
     */
    @Column(name = "popu_content")
    private String popuContent;

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
     * 答案图片
     */
    @Transient
    @Column(name = "answer_image")
    private String image;

    /**
     * 计算公式
     */
    @Column(name = "formual")
    private String formual;
    
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
     * 答案类型
     */
    @Column(name = "words_prop")
    private String wordsProperty;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswerCode() {
		return answerCode;
	}

	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPopuContent() {
		return popuContent;
	}

	public void setPopuContent(String popuContent) {
		this.popuContent = popuContent;
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

	public String getFormual() {
		return formual;
	}

	public void setFormual(String formual) {
		this.formual = formual;
	}

	public String getWordsProperty() {
		return wordsProperty;
	}

	public void setWordsProperty(String wordsProperty) {
		this.wordsProperty = wordsProperty;
	}

	public String getIncreFlag() {
		return increFlag;
	}

	public void setIncreFlag(String increFlag) {
		this.increFlag = increFlag;
	}

	public String getOperaFlag() {
		return operaFlag;
	}

	public void setOperaFlag(String operaFlag) {
		this.operaFlag = operaFlag;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Integer getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(Integer dataVersion) {
		this.dataVersion = dataVersion;
	}

	public String getVersionEvolution() {
		return versionEvolution;
	}

	public void setVersionEvolution(String versionEvolution) {
		this.versionEvolution = versionEvolution;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getMedicalDataStatus() {
		return medicalDataStatus;
	}

	public void setMedicalDataStatus(Integer medicalDataStatus) {
		this.medicalDataStatus = medicalDataStatus;
	}

	public Integer getItDataStatus() {
		return itDataStatus;
	}

	public void setItDataStatus(Integer itDataStatus) {
		this.itDataStatus = itDataStatus;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
