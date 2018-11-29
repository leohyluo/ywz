package com.alpha.server.rpc.his.vo;

import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

/**
 * 疾病列表
 * @author Administrator
 *
 */
public class DiseaseListVo {

	 /**
     * 疾病编码
     */
    private String diseaseCode;

    /**
     * 疾病名称
     */
    private String diseaseName;
    
    /**
     * 发病概率
     */
    private Double probability;

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}
}
