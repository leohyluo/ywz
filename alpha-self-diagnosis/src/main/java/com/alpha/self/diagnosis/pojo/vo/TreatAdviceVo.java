package com.alpha.self.diagnosis.pojo.vo;

import org.springframework.beans.BeanUtils;

import com.alpha.commons.core.pojo.DiagnosisDisease;

/**
 * 疾病诊疗指南
 * @author Administrator
 *
 */
public class TreatAdviceVo {

	 /**
     * 疾病概述
     */
    private String diseaseSummary;
    
    /**
     * 指南意见
     */
    private String guideOption;
    
    /**
     * 家庭微治疗
     */
    private String treatFamily;
    
    /**
     * 疾病预防
     */
    private String defendOption;
    
    public TreatAdviceVo(DiagnosisDisease disease) {
    	BeanUtils.copyProperties(disease, this);
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
    
    
}
