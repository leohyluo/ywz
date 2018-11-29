package com.alpha.user.pojo.vo;

import java.util.Date;
import java.util.List;

import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.his.utils.AppUtils;
import com.alpha.server.rpc.user.pojo.HisRegisterInfo;
import com.alpha.server.rpc.user.pojo.UserInfo;

/**
 * 用于接收医院返回的用户实体类
 *
 * @author Administrator
 */
public class HisUserInfoVo {

    private String hospitalCode;

    private String systemType;
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
    //门诊号
    private String outPatientNo;
    //身份证号码
    private String idcard;
    //电话号码
    private String phoneNumber;
    //渠道
    private int inType;
    //年龄
    private String age;
    //47岁10个月
    private String wholeAge;
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
    //是否到其它医院就诊
    private Integer otherHospital;
    //其它医院就诊时间
    private Date otherHospitalCureTime;
    //是否使用药物治疗
    private Integer otherHospitalUseDrug;
    //其它医院用药列表
    private List<DrugVo> otherHospitalDrugList;
    //治疗效果
    private String otherHospitalEffect;
    //其它医院诊断
    private String otherHospitalDiagnosis;
    //是否自己
    private String self;
    //用户头像
    private String userIcon;
    //医院公众号二维码
    private String qrCode4OfficalAccount;

    public String getFilterDepartment() {
        return filterDepartment;
    }

    public void setFilterDepartment(String filterDepartment) {
        this.filterDepartment = filterDepartment;
    }
    //被过滤的科室
    private String filterDepartment;
    
    /**
     * 将挂号科室json字符串转为List集合
     */
    private List<HisRegisterInfo> hisDepartmentList; 
    
    public HisUserInfoVo() {}
    
    /*public HisUserInfoVo(UserInfo userInfo) {
    	BeanCopierUtil.copy(userInfo, this);
    	if(userInfo.getBirth() != null) {
    		this.birth = DateUtils.date2String(userInfo.getBirth(), DateUtils.DATE_FORMAT);
    	}
    	if(userInfo.getCureTime() != null) {
    		this.cureTime = DateUtils.date2String(userInfo.getCureTime(), DateUtils.DATE_FORMAT);
    	}
    	processSensitive(userInfo);
    }*/
    
    public HisUserInfoVo(UserInfo userInfo, List<HisRegisterInfo> hisDepartmentList) {
    	BeanCopierUtil.copy(userInfo, this);
    	if(userInfo.getBirth() != null) {
    		this.birth = DateUtils.date2String(userInfo.getBirth(), DateUtils.DATE_FORMAT);
    		float age = DateUtils.getAge(userInfo.getBirth());
    		if(age > 14) {
    		    this.weight = "";
            }
    	}
    	this.hisDepartmentList = hisDepartmentList;
    	this.userIcon = AppUtils.getUserIcon(userInfo);
    	if(CollectionUtils.isEmpty(hisDepartmentList)) {
    	    this.setQrCode4OfficalAccount("images/his/eryycode.jpg");
        }
    	processSensitive(userInfo);
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getMenstrualPeriod() {
		return menstrualPeriod;
	}

	public void setMenstrualPeriod(String menstrualPeriod) {
		this.menstrualPeriod = menstrualPeriod;
	}

	public String getVaccinationHistoryCode() {
		return vaccinationHistoryCode;
	}

	public void setVaccinationHistoryCode(String vaccinationHistoryCode) {
		this.vaccinationHistoryCode = vaccinationHistoryCode;
	}

	public List<HisRegisterInfo> getHisDepartmentList() {
		return hisDepartmentList;
	}

	public void setHisDepartmentList(List<HisRegisterInfo> hisDepartmentList) {
		this.hisDepartmentList = hisDepartmentList;
	}

	public List<DrugVo> getOtherHospitalDrugList() {
		return otherHospitalDrugList;
	}

	public void setOtherHospitalDrugList(List<DrugVo> otherHospitalDrugList) {
		this.otherHospitalDrugList = otherHospitalDrugList;
	}

    public String getQrCode4OfficalAccount() {
        return qrCode4OfficalAccount;
    }

    public void setQrCode4OfficalAccount(String qrCode4OfficalAccount) {
        this.qrCode4OfficalAccount = qrCode4OfficalAccount;
    }

    public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    /**
     * 敏感信息处理
     */
    private void processSensitive(UserInfo userInfo) {
        /*if (StringUtils.isNotEmpty(this.userName)) {
            this.userName = "*".concat(this.userName.substring(1));
        }*/
        if (userInfo.getBirth() != null) {
            this.age = DateUtils.getAgeText(userInfo.getBirth(), true);
            this.wholeAge = DateUtils.getAgeText(userInfo.getBirth(), false);
        }
        /*if (StringUtils.isNotEmpty(idcard) && idcard.length() > 8) {
            String remainStr = this.idcard.substring(4, this.idcard.length() - 4);
            this.idcard = "****".concat(remainStr).concat("****");
        }*/
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getWholeAge() {
        return wholeAge;
    }

    public void setWholeAge(String wholeAge) {
        this.wholeAge = wholeAge;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}
