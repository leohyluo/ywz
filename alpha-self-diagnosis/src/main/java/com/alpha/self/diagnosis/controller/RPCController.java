package com.alpha.self.diagnosis.controller;

import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.his.pojo.dto.EmrInfoDetailDTO;
import com.alpha.his.service.etyy.HospitalService;
import com.alpha.self.diagnosis.pojo.vo.HisDiagnosisResultVo;
import com.alpha.self.diagnosis.pojo.vo.UserDiagnosisOutcomeVo;
import com.alpha.self.diagnosis.service.DiagnosisService;
import com.alpha.self.diagnosis.service.UserMedicalRecordService;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.service.UserBasicRecordService;
import com.alpha.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rpc")
public class RPCController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserBasicRecordService userBasicRecordService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DiagnosisService diagnosisService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserMedicalRecordService userMedicalRecordService;

    /**
     * 根据医院编码和门诊号获取预问诊记录
     * @param hospitalCode
     * @param cardNo
     * @return
     */
    @PostMapping("/basicRecord/get")
    public UserBasicRecord getUserBasicRecord(String hospitalCode,String cardNo) {
        //return userBasicRecordService.getByHospitalCodeAndHisRegisterNo(hospitalCode, cardNo);
        List<UserBasicRecord> userBasicRecordList = userBasicRecordService.listFinishByAppointmentOrOffLine(cardNo);
        return CollectionUtils.isNotEmpty(userBasicRecordList) ? userBasicRecordList.get(0) : null;
    }

    /**
     * 当医生导入后，病历下次打开时不提供展现
     * @param pNo
     */
    @PostMapping("/medicineRecord/import/update")
    public Integer medicineRecordImportUpdate(String pNo) {
        diagnosisService.updateMedicineImportAction(pNo);
        return 0;
    }

    /**
     * 判断病历是否被导入过
     * @param pNo
     */
    @PostMapping("/medicineRecord/show/exists")
    public Integer medicineRecordShowExists(String pNo) {
        Integer flag = diagnosisService.medicineRecordShowExists(pNo);
        return flag;
    }

    /**
     * 根据医院编码和挂号码获取预问诊记录及医生工作站的记录
     * @param hospitalCode
     * @param pno 挂号码
     * @return
     */
    @PostMapping("/medicineRecord/get")
    public EmrInfoDetailDTO getHisMedicineRecord(String hospitalCode, String pno) {
        UserBasicRecord userBasicRecord = userBasicRecordService.getByHospitalCodeAndHisRegisterNo(hospitalCode, pno);
        if(userBasicRecord == null) {
            logger.info("根据医院编码{}挂号码{}无法找到就诊记录", hospitalCode, pno);
            return null;
        }
        HisDiagnosisResultVo hisDiagnosisResult = diagnosisService.showHisDiagnosisResult(userBasicRecord.getUserId(), userBasicRecord.getDiagnosisId());

        EmrInfoDetailDTO emrInfoDetailDTO = new EmrInfoDetailDTO();
        if(hisDiagnosisResult != null) {
            emrInfoDetailDTO.setUserName(hisDiagnosisResult.getUserName());
            emrInfoDetailDTO.setGender(hisDiagnosisResult.getGender());
            emrInfoDetailDTO.setAge(hisDiagnosisResult.getAge());
            emrInfoDetailDTO.setWeight(hisDiagnosisResult.getWeight());
            emrInfoDetailDTO.setDepartment(hisDiagnosisResult.getDepartment());
            emrInfoDetailDTO.setCureTime(hisDiagnosisResult.getCureTime());

            String mainSymptomName = hisDiagnosisResult.getMainSymptomName();
            if(StringUtils.isNotEmpty(hisDiagnosisResult.getMainSymptomName4His())) {
                mainSymptomName = hisDiagnosisResult.getMainSymptomName4His();
            }
            String presentIllnessHistory = hisDiagnosisResult.getPresentIllnessHistory();
            if(StringUtils.isNotEmpty(hisDiagnosisResult.getPresentIllnessHistory4His())) {
                presentIllnessHistory = hisDiagnosisResult.getPresentIllnessHistory4His();
            }
            String pastmedicalHistory = hisDiagnosisResult.getPastmedicalHistoryText();
            if(StringUtils.isNotEmpty(hisDiagnosisResult.getPastmedicalHistoryText4His())) {
                pastmedicalHistory = hisDiagnosisResult.getPastmedicalHistoryText4His();
            } else {
                /*String mainSympCode = userBasicRecord.getMainSymptomCode();
                Map<String, Object> param = new HashMap<>();
                param.put("sympCode", mainSympCode);
                List<DiagnosisPastmedicalHistory> pastmedicalHistoryList = diagnosisPastmedicalHistoryService.queryPastmedicalHistory(param);*/
                UserMedicalRecord userMedicalRecord = userMedicalRecordService.getByDiagnosisId(userBasicRecord.getDiagnosisId());
                pastmedicalHistory = userMedicalRecord.getHistoryOfPast();
            }
            String diseases = hisDiagnosisResult.getDiseases4His();
            if(StringUtils.isEmpty(diseases)) {
                List<UserDiagnosisOutcomeVo> diagnosisOutcomeVoList = hisDiagnosisResult.getDiseaseList();
                if(CollectionUtils.isNotEmpty(diagnosisOutcomeVoList)) {
                    diseases = diagnosisOutcomeVoList.stream().map(UserDiagnosisOutcomeVo::getDiseaseName).collect(Collectors.joining(","));
                }
            }
            emrInfoDetailDTO.setMainSymptomName(mainSymptomName);
            emrInfoDetailDTO.setPresentIllnessHistory(presentIllnessHistory);
            emrInfoDetailDTO.setPastmedicalHistory(pastmedicalHistory);
            emrInfoDetailDTO.setDiseases(diseases);
        }
        return emrInfoDetailDTO;
    }

    /**
     * 根据挂号码/预约码获取现场号已完成的就诊记录
     * @param pno
     * @return
     */
    @PostMapping("/basicRecord/live")
    public UserBasicRecord getUserBasicRecord4Live(String pno) {
        logger.info("根据门诊号{}查询现场号已完成预问诊的就诊记录", pno);
        List<UserBasicRecord> userBasicRecordList = userBasicRecordService.getForLive(pno);
        return CollectionUtils.isNotEmpty(userBasicRecordList) ? userBasicRecordList.get(0) : null;
    }

    /**
     * 根据挂号码/预约码获取现场号已完成的就诊记录
     * @param pno
     * @return
     */
    @PostMapping("/basicRecord/appointment")
    public UserBasicRecord getUserBasicRecord4Appointment(String pno) {
        logger.info("根据门诊号{}查询预约号已完成预问诊的就诊记录", pno);
        List<UserBasicRecord> userBasicRecordList = userBasicRecordService.getForAppointment(pno);
        return CollectionUtils.isNotEmpty(userBasicRecordList) ? userBasicRecordList.get(0) : null;
    }

    /**
     * 根据门诊号查询用户是否存在
     * @param outPatientNo
     * @return
     */
    @PostMapping("/userInfo/getByOutPatientNo")
    public UserInfo getUserInfoByOutPatientNo(String outPatientNo) {
        return userInfoService.queryUserInfoFromLocal(outPatientNo);
    }

    /**
     * 根据预约记录创建用户
     * @param userInfo
     */
    @PostMapping("/userInfo/create")
    public void createUserInfo(UserInfo userInfo) {
        if(StringUtils.isNotEmpty(userInfo.getBirthStr())) {
            String birthStr = userInfo.getBirthStr();
            try {
                Date birth = DateUtils.string2Date(birthStr);
                userInfo.setBirth(birth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        userInfo.setInType(1);
        userInfoService.create(userInfo);
    }
}
