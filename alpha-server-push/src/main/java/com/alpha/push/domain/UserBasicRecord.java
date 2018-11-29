package com.alpha.push.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_basic_record")
public class UserBasicRecord {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 成员唯一编号，张三为李四导药，这里放李四编号
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 用户唯一编号,问诊人
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 诊断号唯一编码
     */
    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    /**
     * 出生日期
     */
    @Column(name = "birth")
    private Date birth;

    /**
     * 肝功能
     */
    @Column(name = "liver_func")
    private Integer liverFunc;

    /**
     * 肝功能
     */
    @Column(name = "liver_func_text")
    private String liverFuncText;

    /**
     * 肾功能
     */
    @Column(name = "renal_func_text")
    private String renalFuncText;

    /**
     * 肾功能
     */
    @Column(name = "renal_func")
    private Integer renalFunc;

    /**
     * 身高
     */
    @Column(name = "height")
    private String height;

    /**
     * 体重
     */
    @Column(name = "weight")
    private String weight;

    /**
     *  精神状态
     */
    @Column(name = "mentality")
    private String mentality;

    /**
     * 食欲
     */
    @Column(name = "appetite")
    private String appetite;

    /**
     * 大便
     */
    @Column(name = "shit")
    private String shit;

    /**
     * 小便
     */
    @Column(name = "urine")
    private String urine;
    
    /**
     * 月经期
     */
    @Column(name = "menstrual_period")
    private String menstrualPeriod;

    /**
     * 女性特殊时期（月经期、备孕中、妊娠期、哺乳期、无）
     */
    @Column(name = "special_period")
    private String specialPeriod;

    /**
     * 顺产/剖宫产/产钳助产
     */
    @Column(name = "fertility_type")
    private String fertilityType;

    /**
     * 胎龄（出生的时候怀孕了多少周,范围值）
     */
    @Column(name = "gestational_age")
    private String gestationalAge;

    /**
     * 母乳喂养、人工喂养、混合喂养
     */
    @Column(name = "feed_type")
    private String feedType;
    
    /**
     * 预防接种史名称
     */
    @Column(name = "vaccination_history_text")
    private String vaccinationHistoryText;
    
    /**
     * 既往史编码集合
     */
    @Column(name = "pastmedical_history_code")
    private String pastmedicalHistoryCode;

    /**
     * 既往史名称集合
     */
    @Column(name = "pastmedical_history_text")
    private String pastmedicalHistoryText;

    /**
     * 过敏史编码集合
     */
    @Column(name = "allergic_history_code")
    private String allergicHistoryCode;

    /**
     * 过敏史名称集合
     */
    @Column(name = "allergic_history_text")
    private String allergicHistoryText;
    
    /**
     * 手术史编码
     */
    @Column(name = "operation_code")
    private String operationCode;
    
    /**
     * operation_text
     */
    @Column(name = "operation_text")
    private String operationText;

    /**
     * 是否到其它医院就诊
     */
    @Column(name = "other_hospital")
    private Integer otherHospital;

    /**
     * 其它医院就诊时间
     */
    @Column(name = "other_hospital_cureTime")
    private Date otherHospitalCureTime;

    /**
     * 是否使用药物治疗
     */
    @Column(name = "other_hospital_useDrug")
    private Integer otherHospitalUseDrug;
    
    /**
     * 用药列表
     */
    @Column(name = "other_hospital_drugList")
    private String otherHospitalDrugList;

    /**
     * 治疗效果
     */
    @Column(name = "other_hospital_effect")
    private String otherHospitalEffect;

    /**
     * 其它医院诊断
     */
    @Column(name = "other_hospital_diagnosis")
    private String otherHospitalDiagnosis;

    //医院推送过来的排队信息
    @Column(name = "queue_info")
    private String queueInfo;

    /**
     * 挂号科室
     */
    @Column(name = "department")
    private String department;

    /**
     * 主症状编码
     */
    @Column(name = "main_symptom_code")
    private String mainSymptomCode;

    /**
     * 主症状名称
     */
    @Column(name = "main_symptom_name")
    private String mainSymptomName;

    /**
     * 现病史
     */
    @Column(name = "present_illness_history")
    private String presentIllnessHistory;

    /**
     * 现病史-其它医院就诊信息
     */
    @Column(name = "present_illness_history_hospital")
    private String presentIllnessHistoryHospital;

    /**
     * 就诊日期
     */
    @Column(name = "cure_time")
    private Date cureTime;
    
    /**
     * 医院编码
     */
    @Column(name = "hospital_code")
    private String hospitalCode;
    
    /**
     * 医院名称
     */
    @Transient
    private String hospitalName;
    
    @Transient
    private String hospitalLogo;
    
    /**
     * 医生姓名
     */
    @Column(name = "doctor_name")
    private String doctorName;
    
    /**
     * 紧急联系电话
     */
    @Column(name = "phone_num")
    private String phoneNum;
    
    /**
     * 状态
     */
    @Column(name = "status")
    private String status;
    
    //icd10
    @Column(name = "icd10")
    private String icd10;
    
    //疾病名称
    @Column(name = "disease_name")
    private String diseaseName;
    
    //检查列表(json数组字符串)
    @Column(name = "checkList")
    private String checkList;
    
    //药品列表(json数组字符串)
    @Column(name = "drugList")
    private String drugList;
    
    /**
     * 医院挂号号码
     */
    @Column(name = "his_register_no")
    private String hisRegisterNo;
    
    //二维码地址
    @Column(name = "qr_code")
    private String qrCode;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getCureTime() {
        return cureTime;
    }

    public void setCureTime(Date cureTime) {
        this.cureTime = cureTime;
    }

    /**
     * 最后更新时间，最后一个回答时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 1:现场挂号 2:预约 3:到现场取号后
     */
    @Column(name ="type")
    private String type;

    /**
     * 预问诊渠道1:线下扫码 2:公众号链接
     */
    @Column(name = "channel")
    private String channel;

    /**
     * 1:初诊 2:复诊
     */
    @Column(name = "diagnosis_type")
    private Integer diagnosisType;

    /**
     * 月经状态 1：正常 0：不正常
     */
    @Column(name = "menarche_status")
    private String menarcheStatus;

    /**
     * 月经初潮
     */
    @Column(name = "menarche")
    private String menarche;

    /**
     * 月经周期
     */
    @Column(name = "menarche_cycle")
    private String menarcheCycle;

    /**
     * 经期
     */
    @Column(name = "menarche_peroid")
    private String menarchePeroid;

    /**
     * 婚史
     */
    @Column(name = "marriage")
    private String marriage;

    /**
     * 生育史
     */
    @Column(name = "history_of_born")
    private String historyOfBorn;

    /**
     * 末次月经时间
     */
    @Column(name = "lmp")
    private String lmp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getLiverFunc() {
        return liverFunc;
    }

    public void setLiverFunc(Integer liverFunc) {
        this.liverFunc = liverFunc;
    }

    public String getLiverFuncText() {
        return liverFuncText;
    }

    public void setLiverFuncText(String liverFuncText) {
        this.liverFuncText = liverFuncText == null ? null : liverFuncText.trim();
    }

    public String getRenalFuncText() {
        return renalFuncText;
    }

    public void setRenalFuncText(String renalFuncText) {
        this.renalFuncText = renalFuncText == null ? null : renalFuncText.trim();
    }

    public Integer getRenalFunc() {
        return renalFunc;
    }

    public void setRenalFunc(Integer renalFunc) {
        this.renalFunc = renalFunc;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getSpecialPeriod() {
        return specialPeriod;
    }

    public void setSpecialPeriod(String specialPeriod) {
        this.specialPeriod = specialPeriod == null ? null : specialPeriod.trim();
    }

    public String getFertilityType() {
        return fertilityType;
    }

    public void setFertilityType(String fertilityType) {
        this.fertilityType = fertilityType == null ? null : fertilityType.trim();
    }

    public String getGestationalAge() {
        return gestationalAge;
    }

    public void setGestationalAge(String gestationalAge) {
        this.gestationalAge = gestationalAge == null ? null : gestationalAge.trim();
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType == null ? null : feedType.trim();
    }

    public String getPastmedicalHistoryCode() {
        return pastmedicalHistoryCode;
    }

    public void setPastmedicalHistoryCode(String pastmedicalHistoryCode) {
        this.pastmedicalHistoryCode = pastmedicalHistoryCode == null ? null : pastmedicalHistoryCode.trim();
    }

    public String getPastmedicalHistoryText() {
        return pastmedicalHistoryText;
    }

    public void setPastmedicalHistoryText(String pastmedicalHistoryText) {
        this.pastmedicalHistoryText = pastmedicalHistoryText == null ? null : pastmedicalHistoryText.trim();
    }

    public String getAllergicHistoryCode() {
        return allergicHistoryCode;
    }

    public void setAllergicHistoryCode(String allergicHistoryCode) {
        this.allergicHistoryCode = allergicHistoryCode == null ? null : allergicHistoryCode.trim();
    }

    public String getAllergicHistoryText() {
        return allergicHistoryText;
    }

    public void setAllergicHistoryText(String allergicHistoryText) {
        this.allergicHistoryText = allergicHistoryText == null ? null : allergicHistoryText.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIncreFlag() {
        return increFlag;
    }

    public void setIncreFlag(String increFlag) {
        this.increFlag = increFlag == null ? null : increFlag.trim();
    }

    public String getOperaFlag() {
        return operaFlag;
    }

    public void setOperaFlag(String operaFlag) {
        this.operaFlag = operaFlag == null ? null : operaFlag.trim();
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
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
        this.versionEvolution = versionEvolution == null ? null : versionEvolution.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
        this.reviewer = reviewer == null ? null : reviewer.trim();
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getOtherHospital() {
        return otherHospital;
    }

    public void setOtherHospital(Integer otherHospital) {
        this.otherHospital = otherHospital;
    }

    public Date getOtherHospitalCureTime() {
        return otherHospitalCureTime;
    }

    public void setOtherHospitalCureTime(Date otherHospitalCureTime) {
        this.otherHospitalCureTime = otherHospitalCureTime;
    }

    public Integer getOtherHospitalUseDrug() {
        return otherHospitalUseDrug;
    }

    public void setOtherHospitalUseDrug(Integer otherHospitalUseDrug) {
        this.otherHospitalUseDrug = otherHospitalUseDrug;
    }

    public String getOtherHospitalEffect() {
        return otherHospitalEffect;
    }

    public void setOtherHospitalEffect(String otherHospitalEffect) {
        this.otherHospitalEffect = otherHospitalEffect;
    }

    public String getOtherHospitalDiagnosis() {
        return otherHospitalDiagnosis;
    }

    public void setOtherHospitalDiagnosis(String otherHospitalDiagnosis) {
        this.otherHospitalDiagnosis = otherHospitalDiagnosis;
    }

    public String getMainSymptomCode() {
        return mainSymptomCode;
    }

    public void setMainSymptomCode(String mainSymptomCode) {
        this.mainSymptomCode = mainSymptomCode;
    }

    public String getMainSymptomName() {
        return mainSymptomName;
    }

    public String getLmp() {
        return lmp;
    }

    public void setLmp(String lmp) {
        this.lmp = lmp;
    }

    public void setMainSymptomName(String mainSymptomName) {
        this.mainSymptomName = mainSymptomName;
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

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperationText() {
		return operationText;
	}

	public void setOperationText(String operationText) {
		this.operationText = operationText;
	}
    public String getMenstrualPeriod() {
		return menstrualPeriod;
	}

	public void setMenstrualPeriod(String menstrualPeriod) {
		this.menstrualPeriod = menstrualPeriod;
	}
	
	public String getVaccinationHistoryText() {
		return vaccinationHistoryText;
	}

	public void setVaccinationHistoryText(String vaccinationHistoryText) {
		this.vaccinationHistoryText = vaccinationHistoryText;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHisRegisterNo() {
		return hisRegisterNo;
	}

	public void setHisRegisterNo(String hisRegisterNo) {
		this.hisRegisterNo = hisRegisterNo;
	}

	public String getOtherHospitalDrugList() {
		return otherHospitalDrugList;
	}

	public void setOtherHospitalDrugList(String otherHospitalDrugList) {
		this.otherHospitalDrugList = otherHospitalDrugList;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getIcd10() {
		return icd10;
	}

	public void setIcd10(String icd10) {
		this.icd10 = icd10;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getCheckList() {
		return checkList;
	}

	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}

	public String getDrugList() {
		return drugList;
	}

	public void setDrugList(String drugList) {
		this.drugList = drugList;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalLogo() {
		return hospitalLogo;
	}

	public void setHospitalLogo(String hospitalLogo) {
		this.hospitalLogo = hospitalLogo;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

    public String getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(String queueInfo) {
        this.queueInfo = queueInfo;
    }

    public String getMentality() {
        return mentality;
    }

    public void setMentality(String mentality) {
        this.mentality = mentality;
    }

    public String getAppetite() {
        return appetite;
    }

    public void setAppetite(String appetite) {
        this.appetite = appetite;
    }

    public String getShit() {
        return shit;
    }

    public void setShit(String shit) {
        this.shit = shit;
    }

    public String getUrine() {
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMenarche() {
        return menarche;
    }

    public void setMenarche(String menarche) {
        this.menarche = menarche;
    }

    public String getMenarcheCycle() {
        return menarcheCycle;
    }

    public void setMenarcheCycle(String menarcheCycle) {
        this.menarcheCycle = menarcheCycle;
    }

    public String getMenarchePeroid() {
        return menarchePeroid;
    }

    public void setMenarchePeroid(String menarchePeroid) {
        this.menarchePeroid = menarchePeroid;
    }

    public String getMenarcheStatus() {
        return menarcheStatus;
    }

    public void setMenarcheStatus(String menarcheStatus) {
        this.menarcheStatus = menarcheStatus;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getHistoryOfBorn() {
        return historyOfBorn;
    }

    public void setHistoryOfBorn(String historyOfBorn) {
        this.historyOfBorn = historyOfBorn;
    }

}
