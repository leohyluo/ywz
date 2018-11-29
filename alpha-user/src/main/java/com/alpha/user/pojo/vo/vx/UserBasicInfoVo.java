package com.alpha.user.pojo.vo.vx;

import java.util.List;

import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.DateUtils;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.pojo.vo.DiseaseHistoryVo;

/**
 * 公众号-健康档案-个人资料
 * @author Administrator
 *
 */
public class UserBasicInfoVo {

	//阿尔法用户id
    private Long userId;
    //his系统用户id
    private String externalUserId;
    //患者姓名
    private String userName;
    //出生日期
    private String birth;
    //性别
    private Integer gender;
    //身份证号码
    private String idcard;
    //电话号码
    private String phoneNumber;
    //渠道
    private int inType;
    //年龄
    private String age;
    //月经期
    private String menstrualPeriod;
    //女性特殊时期（月经期、备孕中、妊娠期、哺乳期、无）
    private String specialPeriod;
    //既往史
    private List<DiseaseHistoryVo> pastmedicalHistory;
    //过敏史
    private List<DiseaseHistoryVo> allergicHistory;
    //出生史
    private String fertilityType;
    //胎龄（出生的时候怀孕了多少周,范围值）
    private String gestationalAge;
    //喂养史
    private String feedType;
    //预防接种史编码
    private String vaccinationHistoryCode;
    //体重
    private String weight;
    
    public UserBasicInfoVo(UserInfo userInfo) {
    	BeanCopierUtil.copy(userInfo, this);
    	if(userInfo.getBirth() != null) {
    		this.birth = DateUtils.date2String(userInfo.getBirth(), DateUtils.DATE_FORMAT);
    	}
    }
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getExternalUserId() {
		return externalUserId;
	}
	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getInType() {
		return inType;
	}
	public void setInType(int inType) {
		this.inType = inType;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getMenstrualPeriod() {
		return menstrualPeriod;
	}
	public void setMenstrualPeriod(String menstrualPeriod) {
		this.menstrualPeriod = menstrualPeriod;
	}
	public String getSpecialPeriod() {
		return specialPeriod;
	}
	public void setSpecialPeriod(String specialPeriod) {
		this.specialPeriod = specialPeriod;
	}
	public List<DiseaseHistoryVo> getPastmedicalHistory() {
		return pastmedicalHistory;
	}
	public void setPastmedicalHistory(List<DiseaseHistoryVo> pastmedicalHistory) {
		this.pastmedicalHistory = pastmedicalHistory;
	}
	public List<DiseaseHistoryVo> getAllergicHistory() {
		return allergicHistory;
	}
	public void setAllergicHistory(List<DiseaseHistoryVo> allergicHistory) {
		this.allergicHistory = allergicHistory;
	}
	public String getFertilityType() {
		return fertilityType;
	}
	public void setFertilityType(String fertilityType) {
		this.fertilityType = fertilityType;
	}
	public String getGestationalAge() {
		return gestationalAge;
	}
	public void setGestationalAge(String gestationalAge) {
		this.gestationalAge = gestationalAge;
	}
	public String getFeedType() {
		return feedType;
	}
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
	public String getVaccinationHistoryCode() {
		return vaccinationHistoryCode;
	}
	public void setVaccinationHistoryCode(String vaccinationHistoryCode) {
		this.vaccinationHistoryCode = vaccinationHistoryCode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
    
    
}
