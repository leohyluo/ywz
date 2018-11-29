package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.service.UserDiagnosisOutcomeService;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.service.UserBasicRecordService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiagnosisResultVo {
	
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
    //用户诊断过程(v1.1.0)
    private List<UserDiagnosisDetail> userDiagnosisDetailList;

    //月经史
    private String historyOfMenstruation;

    //生育史
    private String historyOfMarry;

    /**
     *  精神状态(v1.1.0)
     */
    private String mentality;

    /**
     * 食欲(v1.1.0)
     */
    private String appetite;

    /**
     * 大便(v1.1.0)
     */
    private String shit;

    /**
     * 小便(v1.1.0)
     */
    private String urine;

    //预问诊时间v1.1.0
    private String preDiagnosisTime;
    //出生日期v1.1.0
    private String birth;
    //女性特殊时期（月经期、备孕中、妊娠期、哺乳期、无） v1.1.0
    private String menstrualPeriod;

    public DiagnosisResultVo() {
    }

    public String getPreDiagnosisTime() {
        return preDiagnosisTime;
    }

    public void setPreDiagnosisTime(String preDiagnosisTime) {
        this.preDiagnosisTime = preDiagnosisTime;
    }

    public DiagnosisResultVo(UserInfo userInfo, Long diagnosisId, UserMedicalRecord userMedicalRecord) {
        UserDiagnosisOutcomeService userDiagnosisOutcomeService = SpringContextHolder.getBean("userDiagnosisOutcomeServiceImpl");
        UserBasicRecordService userBasicRecordService = SpringContextHolder.getBean("userBasicRecordServiceImpl");

        UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);

        this.diagnosisId = record.getDiagnosisId();
        this.userName = userInfo.getUserName();
        this.gender = userInfo.getGender();
        if (record.getBirth() != null) {
            this.age = DateUtils.getAgeText(record.getBirth(), true);
            this.birth = DateUtils.date2String(record.getBirth(), DateUtils.DATE_FORMAT);
        }
        if(StringUtils.isNotEmpty(record.getWeight())) {
            this.weight = record.getWeight() + "kg";
        } else {
            this.weight = "未测量";
        }
        this.department = record.getDepartment();
        if (record.getCureTime() != null) {
            this.cureTime = DateUtils.date2String(record.getCureTime(), DateUtils.DATE_FORMAT);
        }
        this.mainSymptomName = userMedicalRecord.getMainSymptom();
        this.presentIllnessHistory = userMedicalRecord.getHistoryOfPresent();
        this.buildPastIllHistory(record);
        this.pastmedicalHistoryText = userMedicalRecord.getHistoryOfPast();
        //拼装诊断结果
        List<UserDiagnosisOutcome> udos = userDiagnosisOutcomeService.listTop5UserDiagnosisOutcome(diagnosisId, record.getMainSymptomCode());
        List<UserDiagnosisOutcomeVo> diseasevoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(udos)) {
            diseasevoList = udos.stream().map(UserDiagnosisOutcomeVo::new).collect(Collectors.toList());
        }
        this.diseaseList = diseasevoList;
        this.phoneNumber = record.getPhoneNum();
        //此属性在病情确认接口才用到，所以在病情确认接口单独为此属性赋值
        this.userDiagnosisDetailList = null;
        this.historyOfMenstruation = userMedicalRecord.getHistoryOfMenstruation();
        this.historyOfMarry = userMedicalRecord.getHistoryOfMarriage();
        this.mentality = record.getMentality();
        this.appetite = record.getAppetite();
        this.shit = record.getShit();
        this.urine = record.getUrine();
        if (record.getUpdateTime() != null) {
            this.preDiagnosisTime = DateUtils.date2String(record.getUpdateTime(), DateUtils.DATE_TIME_FORMAT);
        }
        this.menstrualPeriod = record.getMenstrualPeriod();
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public List<UserDiagnosisDetail> getUserDiagnosisDetailList() {
        return userDiagnosisDetailList;
    }

    public void setUserDiagnosisDetailList(List<UserDiagnosisDetail> userDiagnosisDetailList) {
        this.userDiagnosisDetailList = userDiagnosisDetailList;
    }

    public String getMentality() {
        return mentality;
    }

    public void setMentality(String mentality) {
        this.mentality = mentality;
    }

    public String getAppetite() {
        return appetite;
    }

    public void setAppetite(String appetite) {
        this.appetite = appetite;
    }

    public String getShit() {
        return shit;
    }

    public void setShit(String shit) {
        this.shit = shit;
    }

    public String getUrine() {
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }

    public String getMenstrualPeriod() {
        return menstrualPeriod;
    }

    public void setMenstrualPeriod(String menstrualPeriod) {
        this.menstrualPeriod = menstrualPeriod;
    }

    public String getHistoryOfMenstruation() {
        return historyOfMenstruation;
    }

    public void setHistoryOfMenstruation(String historyOfMenstruation) {
        this.historyOfMenstruation = historyOfMenstruation;
    }

    public String getHistoryOfMarry() {
        return historyOfMarry;
    }

    public void setHistoryOfMarry(String historyOfMarry) {
        this.historyOfMarry = historyOfMarry;
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
        vo.setGestationalAge(record.getGestationalAge());
        vo.setVaccinationHistoryText(record.getVaccinationHistoryText());
        this.pastmedicalHistory = vo;
    }

}
