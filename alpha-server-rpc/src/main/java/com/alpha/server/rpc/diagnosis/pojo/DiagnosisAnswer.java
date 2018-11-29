package com.alpha.server.rpc.diagnosis.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "diagnosis_answer")
public class DiagnosisAnswer implements Serializable {


    private static final long serialVersionUID = 3300750084893617949L;
    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 答案编码
     */
    @Column(name = "answer_code")
    private String answerCode;

    /**
     * 答案图片
     */
    @Column(name = "answer_image")
    private String answerImage;
    
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


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerCode() {
        return this.answerCode;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }


    public void setPopuContent(String popuContent) {
        this.popuContent = popuContent;
    }

    public String getPopuContent() {
        return this.popuContent;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
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


    public void setFormual(String formual) {
        this.formual = formual;
    }

    public String getFormual() {
        return this.formual;
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

	public String getAnswerImage() {
		return answerImage;
	}

	public void setAnswerImage(String answerImage) {
		this.answerImage = answerImage;
	}

    
}
