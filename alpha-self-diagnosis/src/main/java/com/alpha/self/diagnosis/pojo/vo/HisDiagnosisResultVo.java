package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.DateUtils;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HisDiagnosisResultVo {

	private Long diagnosisId;

    /**
     * 第三方用户编号，用来同步第三方用户信息
     */
    private String externalUserId;

    //姓名
    private String userName;

    //性别
    private Integer gender;

    //1岁8个月
    private String age;

    //体重40kg
    private String weight;

    //挂号科室
    private String department;

    //就诊时间
    private String cureTime;

    //主诉
    private String mainSymptomName;

    //现病史
    private String presentIllnessHistory;

    //既往史
    private PastmedicalHistoryResultVo pastmedicalHistory;

    //经过拼装的既往史
    private String pastmedicalHistoryText;

    //诊断结果
    private List<UserDiagnosisOutcomeVo> diseaseList;

    //联系电话
    private String phoneNumber;

    //主诉-his修改后
    private String mainSymptomName4His;

    //现病史-his修改后
    private String presentIllnessHistory4His;

    //经过拼装的既往史-his修改后
    private String pastmedicalHistoryText4His;

    private String diseases4His;

    private String outPatientNo;
    private String doctor;
    private String birth;
    private String cardNo;

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }



    public HisDiagnosisResultVo() {
    }

    public HisDiagnosisResultVo(UserBasicRecord record, HisDiagnosisRecord hisDiagnosisRecord, UserMedicalRecord userMedicalRecord) {
        if(record != null && userMedicalRecord != null) {
            BeanCopierUtil.copy(record, this);
            if (record.getBirth() != null) {
                this.age = DateUtils.getAgeText(record.getBirth(), true);
            }
            if (record.getCureTime() != null) {
                this.cureTime = DateUtils.date2String(record.getCureTime(), DateUtils.DATE_FORMAT);
            }
            if(StringUtils.isNotEmpty(record.getWeight())) {
                this.weight = record.getWeight() + "kg";
            } else {
                this.weight = "未测量";
            }
            this.mainSymptomName = userMedicalRecord.getMainSymptom();
            this.presentIllnessHistory = userMedicalRecord.getHistoryOfPresent();
            this.pastmedicalHistoryText = userMedicalRecord.getHistoryOfPast();
            this.buildPastIllHistory(record);
            if(record.getCureTime() != null) {
                String cureTimeStr = DateUtils.date2String(record.getCureTime(), DateUtils.DATE_FORMAT);
                this.cureTime = cureTimeStr;
            }
        }

        if(hisDiagnosisRecord != null) {
            this.mainSymptomName4His = hisDiagnosisRecord.getMainSymptomName();
            this.presentIllnessHistory4His = hisDiagnosisRecord.getPresentIllnessHistory();
            this.pastmedicalHistoryText4His = hisDiagnosisRecord.getPastMedicalHistory();
            this.diseases4His = hisDiagnosisRecord.getDiseaseInfo();
        }
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCureTime() {
        return cureTime;
    }

    public void setCureTime(String cureTime) {
        this.cureTime = cureTime;
    }

    public String getMainSymptomName() {
        return mainSymptomName;
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

    public List<UserDiagnosisOutcomeVo> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(List<UserDiagnosisOutcomeVo> diseaseList) {
        this.diseaseList = diseaseList;
    }

    public PastmedicalHistoryResultVo getPastmedicalHistory() {
        return pastmedicalHistory;
    }

    public void setPastmedicalHistory(PastmedicalHistoryResultVo pastmedicalHistory) {
        this.pastmedicalHistory = pastmedicalHistory;
    }

    public void merge(UserInfo userInfo) {
        this.externalUserId = userInfo.getExternalUserId();
        this.userName = userInfo.getUserName();
        this.gender = userInfo.getGender();
        /*if(StringUtils.isNotEmpty(userInfo.getWeight())) {
        	this.weight = userInfo.getWeight() + "kg";
        }*/
        this.phoneNumber = userInfo.getPhoneNumber();
    }
        
    public String getPastmedicalHistoryText() {
		return pastmedicalHistoryText;
	}

	public void setPastmedicalHistoryText(String pastmedicalHistoryText) {
		this.pastmedicalHistoryText = pastmedicalHistoryText;
	}

	public Long getDiagnosisId() {
		return diagnosisId;
	}

	public void setDiagnosisId(Long diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    public String getMainSymptomName4His() {
        return mainSymptomName4His;
    }

    public void setMainSymptomName4His(String mainSymptomName4His) {
        this.mainSymptomName4His = mainSymptomName4His;
    }

    public String getPresentIllnessHistory4His() {
        return presentIllnessHistory4His;
    }

    public void setPresentIllnessHistory4His(String presentIllnessHistory4His) {
        this.presentIllnessHistory4His = presentIllnessHistory4His;
    }

    public String getPastmedicalHistoryText4His() {
        return pastmedicalHistoryText4His;
    }

    public void setPastmedicalHistoryText4His(String pastmedicalHistoryText4His) {
        this.pastmedicalHistoryText4His = pastmedicalHistoryText4His;
    }

    public String getDiseases4His() {
        return diseases4His;
    }

    public void setDiseases4His(String diseases4His) {
        this.diseases4His = diseases4His;
    }

    private void buildPastIllHistory(UserBasicRecord record) {
        PastmedicalHistoryResultVo vo = new PastmedicalHistoryResultVo();
        //手术史
        List<String> operation = new ArrayList<>();
        if(StringUtils.isNotEmpty(record.getOperationText())) {
        	operation = Stream.of(record.getOperationText().split(",")).collect(Collectors.toList());
        }
        vo.setOperation(operation);
        //疾病史
        List<String> diseaseHistory = new ArrayList<>();
        if (StringUtils.isNotEmpty(record.getPastmedicalHistoryText())) {
            diseaseHistory = Stream.of(record.getPastmedicalHistoryText().split(",")).collect(Collectors.toList());
        }
        vo.setDiseaseHistory(diseaseHistory);
        //过敏史
        List<String> allergicHistory = new ArrayList<>();
        if (StringUtils.isNotEmpty(record.getAllergicHistoryText())) {
            allergicHistory = Stream.of(record.getAllergicHistoryText().split(",")).collect(Collectors.toList());
        }
        vo.setAllergicHistory(allergicHistory);
        //出生史
        List<String> fertilityType = new ArrayList<>();
        if (StringUtils.isNotEmpty(record.getFertilityType())) {
            fertilityType.add(record.getFertilityType());
        }
        vo.setFertilityType(fertilityType);
        //喂养史
        List<String> feedType = new ArrayList<>();
        if (StringUtils.isNotEmpty(record.getFeedType())) {
            feedType = new ArrayList<>();
            feedType.add(record.getFeedType());
        }
        vo.setFeedType(feedType);
        //月经史
        List<String> specialPeriod = new ArrayList<>();
        if (StringUtils.isNotEmpty(record.getSpecialPeriod())) {
            specialPeriod.add(record.getSpecialPeriod());
        }
        vo.setSpecialPeriod(specialPeriod);
        this.pastmedicalHistory = vo;
    }

}
