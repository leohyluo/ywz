package com.alpha.server.rpc.user.pojo;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;

import java.util.Date;

/**
 * 医院挂号信息
 * @author Administrator
 *
 */
public class HisRegisterInfo {

	//医院编码
	private String hospitalCode;
	//医生姓名
	private String doctorName;
	//就诊日期
	private String cureTime;
	//挂号科室
	private String department;
	//医院挂号号码
	private String hisRegisterNo;
	//状态(1:排除中 10:预问诊结束)
	private String status;
	//预问诊id(医院不用传,此字段仅作为前端展示)
	private String diagnosisId;
	//科室编码
	private String deptCode;

	public HisRegisterInfo() {}

	public HisRegisterInfo(HisRegisterRecord hisRegisterRecord) {
		this.hospitalCode = hisRegisterRecord.getHospitalCode();
		this.doctorName = hisRegisterRecord.getDoctorName();
		this.cureTime = hisRegisterRecord.getVisitTime();
		this.department = hisRegisterRecord.getDeptName();
		this.hisRegisterNo = hisRegisterRecord.getPno();
	}

	public HisRegisterInfo(HisRegisterYygh appointmentRegister) {
		this.hospitalCode = "";
		this.doctorName = appointmentRegister.getDoctorName();
		this.cureTime = appointmentRegister.getVisitTime();
		this.department = appointmentRegister.getDeptName();
		this.hisRegisterNo = appointmentRegister.getYno();
	}
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getCureTime() {
		return cureTime;
	}
	public void setCureTime(String cureTime) {
		this.cureTime = cureTime;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getHisRegisterNo() {
		return hisRegisterNo;
	}
	public void setHisRegisterNo(String hisRegisterNo) {
		this.hisRegisterNo = hisRegisterNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public String getDiagnosisId() {
		return diagnosisId;
	}
	public void setDiagnosisId(String diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
