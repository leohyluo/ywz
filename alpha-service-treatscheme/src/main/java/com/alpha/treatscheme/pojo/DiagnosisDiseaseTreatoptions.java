package com.alpha.treatscheme.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "diagnosis_disease_treatoptions")
public class DiagnosisDiseaseTreatoptions implements Serializable {


    private static final long serialVersionUID = -3735097486409339799L;
    /**
     *
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
     * 治疗方案编码
     */
    @Column(name = "option_code")
    private String optionCode;

    /**
     * 治疗方案大类名称
     */
    @Column(name = "option_name")
    private String optionName;

    /**
     * 治疗方案2类名称
     */
    @Column(name = "option_name2")
    private String optionName2;

    /**
     * 治疗方案2类名称
     */
    @Column(name = "option_name3")
    private String optionName3;

    /**
     * 方案具体内容
     */
    @Column(name = "option_content")
    private String optionContent;

    /**
     * 序号
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
     * 用药方案
     */
    @Column(name = "medication_plan")
    private String medicationPlan;

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


    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseCode() {
        return this.diseaseCode;
    }


    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public String getOptionCode() {
        return this.optionCode;
    }


    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionName() {
        return this.optionName;
    }


    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionContent() {
        return this.optionContent;
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


    public void setMedicationPlan(String medicationPlan) {
        this.medicationPlan = medicationPlan;
    }

    public String getMedicationPlan() {
        return this.medicationPlan;
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

    public String getOptionName2() {
        return optionName2;
    }

    public void setOptionName2(String optionName2) {
        this.optionName2 = optionName2;
    }

    public String getOptionName3() {
        return optionName3;
    }

    public void setOptionName3(String optionName3) {
        this.optionName3 = optionName3;
    }
}
