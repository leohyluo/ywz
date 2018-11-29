package com.alpha.treatscheme.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 疾病与科室关系表
 * @author Administrator
 *
 */
@Entity
@Table(name = "diagnosis_disease_dept")
public class DiagnosisDiseaseDept {

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "disease_code")
	private String diseaseCode;
	
	@Column(name = "dept_code")
	private String deptCode;
	
	//一级科室名称
	private String deptName1;
	//二级科室名称
	private String deptName2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName2() {
		return deptName2;
	}

	public void setDeptName2(String deptName2) {
		this.deptName2 = deptName2;
	}

	public String getDeptName1() {
		return deptName1;
	}

	public void setDeptName1(String deptName1) {
		this.deptName1 = deptName1;
	}

	
}
