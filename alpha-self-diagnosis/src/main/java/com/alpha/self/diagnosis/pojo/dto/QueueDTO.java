package com.alpha.self.diagnosis.pojo.dto;

import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.util.StringUtils;

/**
 * 数据传输类-排队提醒
 * @author Administrator
 *
 */
public class QueueDTO {

	//提示
	private String tip;
	// 姓名
	private String userName;
	// 年龄
	private String age;
	// 性别
	private Integer gender;
	//医院
	private String hospitalName;
	// 挂号科室
	private String department;
	//排队号码
	private String queuingNumber;
	//前面等待数量
	private String prevQueuingNumber;
	//预估等待时间
	private String waitTime;
	//排队状态(未排队、排队中)
	private String status;
	//医生姓名
	private String doctorName;
	//时间
	private String currentTime;
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getQueuingNumber() {
		return queuingNumber;
	}
	public void setQueuingNumber(String queuingNumber) {
		this.queuingNumber = queuingNumber;
	}
	public String getPrevQueuingNumber() {
		return prevQueuingNumber;
	}
	public void setPrevQueuingNumber(String prevQueuingNumber) {
		this.prevQueuingNumber = prevQueuingNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public JSONObject toWecharTemplateData() {
		JSONObject data = new JSONObject();
		/*addTemplateContent(data, "tips", this.tip, null);
		addTemplateContent(data, "userName", this.userName, null);
		addTemplateContent(data, "gender", Gender.getGenderText(this.gender), null);
		addTemplateContent(data, "age", this.age, null);
		addTemplateContent(data, "hospitalName", this.hospitalName, null);
		addTemplateContent(data, "department", this.department, null);
		addTemplateContent(data, "queuingNumber", this.queuingNumber, null);
		addTemplateContent(data, "prevQueuingNumber", this.prevQueuingNumber, null);
		addTemplateContent(data, "waitTime", this.waitTime, null);
		addTemplateContent(data, "status", DiagnosisStatus.getText(this.status), null);*/
		
		addTemplateContent(data, "keyword1", this.queuingNumber, null);
		addTemplateContent(data, "keyword2", this.department, null);
		addTemplateContent(data, "keyword3", this.doctorName, null);
		addTemplateContent(data, "remark", "您前面还有"+this.prevQueuingNumber+"个人在排队", null);
		
		return data;
	}
	
	private void addTemplateContent(JSONObject data, String key, String value, String color) {
		value = StringUtils.isEmpty(value) ? "暂无" : value;
		JSONObject node = new JSONObject();
		node.put("value", value);
		if(StringUtils.isNotEmpty(color)) {
			node.put("color", color);
		}
		data.put(key, node);
	}
}
