package com.alpha.user.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "diagnosis_medical_template")
public class DiagnosisMedicalTemplate implements Serializable {


    private static final long serialVersionUID = 4816837761154385643L;
    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 模板编号
     */
    @Column(name = "template_code")
    private String templateCode;

    /**
     * 病历模版内容
     */
    @Column(name = "template_name")
    private String templateName;

    /**
     * 主诉症状内容代码
     */
    @Column(name = "symptom_name")
    private String symptomName;

    /**
     * 疾病史内容代码
     */
    @Column(name = "present_illness_history")
    private String presentIllnessHistory;

    /**
     * 就诊经理内容代码
     */
    @Column(name = "present_illness_history_hospital")
    private String presentIllnessHistoryHospital;

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
     * 对象版本 0 默认旧数据，1,儿童版 ，2 ，妇科版
     */
    @Column(name = "object_version")
    private Integer objectVersion;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }


    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return this.templateName;
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


    public void setObjectVersion(Integer objectVersion) {
        this.objectVersion = objectVersion;
    }

    public Integer getObjectVersion() {
        return this.objectVersion;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getPresentIllnessHistory() {
        return presentIllnessHistory;
    }

    public void setPresentIllnessHistory(String presentIllnessHistory) {
        this.presentIllnessHistory = presentIllnessHistory;
    }

    public String getPresentIllnessHistoryHospital() {
        return presentIllnessHistoryHospital;
    }

    public void setPresentIllnessHistoryHospital(String presentIllnessHistoryHospital) {
        this.presentIllnessHistoryHospital = presentIllnessHistoryHospital;
    }
}
