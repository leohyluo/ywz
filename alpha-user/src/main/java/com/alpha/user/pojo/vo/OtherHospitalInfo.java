package com.alpha.user.pojo.vo;

import java.util.Date;
import java.util.List;

public class OtherHospitalInfo {

    /**
     * 用户唯一编号,问诊人
     */
    private Long userId;

    /**
     * 诊断号唯一编码
     */
    private Long diagnosisId;

    /**
     * 是否到其它医院就诊
     */
    private Integer otherHospital;

    /**
     * 其它医院就诊时间
     */
    private Date otherHospitalCureTime;

    /**
     * 是否使用药物治疗
     */
    private Integer otherHospitalUseDrug;
    
    /**
     * 其它医院用药列表
     */
    private List<DrugVo> otherHospitalUseDrugList;

    /**
     * 治疗效果
     */
    private String otherHospitalEffect;

    /**
     * 其它医院诊断
     */
    private String otherHospitalDiagnosis;

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

	public List<DrugVo> getOtherHospitalUseDrugList() {
		return otherHospitalUseDrugList;
	}

	public void setOtherHospitalUseDrugList(List<DrugVo> otherHospitalUseDrugList) {
		this.otherHospitalUseDrugList = otherHospitalUseDrugList;
	}
}
