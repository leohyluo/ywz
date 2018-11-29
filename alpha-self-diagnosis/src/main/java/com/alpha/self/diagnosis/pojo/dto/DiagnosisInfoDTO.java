package com.alpha.self.diagnosis.pojo.dto;

/**
 * 数据传输类-诊断结果
 * @author Administrator
 *
 */
public class DiagnosisInfoDTO {
	
	//挂号码
	private String hisRegisterNo;

	//icd10
	private String icd10;
	
	//确诊疾病名称
	private String diseaseName;
	
	//检查列表(json数组)
	private String checkList;
	
	//药品列表(json数组)
	private String drugList;
	
	//状态
	private String status;
	
	//备注
	private String memo;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getHisRegisterNo() {
		return hisRegisterNo;
	}

	public void setHisRegisterNo(String hisRegisterNo) {
		this.hisRegisterNo = hisRegisterNo;
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
	
	
}
