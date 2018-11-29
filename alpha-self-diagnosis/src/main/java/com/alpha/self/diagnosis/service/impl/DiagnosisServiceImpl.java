package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.api.baidu.naturelanguage.NatureLanguageApi;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.service.SysSequenceService;
import com.alpha.commons.core.util.DateUtil;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.his.dao.HisRegisterRecordDao;
import com.alpha.his.service.etyy.HospitalService;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.service.*;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.service.UserBasicRecordService;
import com.alpha.user.service.UserInfoService;
import com.alpha.user.service.UserMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class DiagnosisServiceImpl implements DiagnosisService {

    @Resource
    private UserMemberService userMemberService;
    @Resource
    private SysSequenceService sysSequenceService;
    @Resource
    private BasicAnswerService basicAnswerService;
    @Resource
    private UserBasicRecordService userBasicRecordService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserDiagnosisOutcomeService userDiagnosisOutcomeService;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private HisRegisterRecordDao hisRegisterRecordDao;
    @Resource
    private UserMedicalRecordService userMedicalRecordService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public BasicQuestionVo start(Long userId, Integer inType, String type, String channel, String pno, Integer diagnosisType, Long inputDiagnosisId) {
        Long diagnosisId = 0L;
        if(inputDiagnosisId == null) {
            UserInfo userInfo = userInfoService.queryByUserId(userId);
            HisRegisterRecord hisRegisterRecord = hisRegisterRecordDao.getByPno(pno);
            diagnosisId = sysSequenceService.getNextSequence("diagnosis_seq");
            //添加就诊记录
            UserBasicRecord record = new UserBasicRecord();
            record.setUserId(userId);
            record.setGender(userInfo.getGender());
            record.setBirth(userInfo.getBirth());
            record.setWeight(userInfo.getWeight());
            record.setDiagnosisId(diagnosisId);
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setType(type);
            record.setChannel(channel);
            record.setHisRegisterNo(pno);
            record.setDiagnosisType(diagnosisType);
            record.setDoctorName(hisRegisterRecord.getDoctorName());
            record.setDepartment(hisRegisterRecord.getDeptName());
            if(StringUtils.isNotEmpty(hisRegisterRecord.getVisitTime())) {
                Date cureTime = DateUtils.stringToDate(hisRegisterRecord.getVisitTime());
                record.setCureTime(cureTime);
            }
            userBasicRecordService.addUserBasicRecord(record);
        } else {
            diagnosisId = inputDiagnosisId;
        }
        BasicQuestionVo basicQuestionVo = new BasicQuestionVo();
        basicQuestionVo.setDiagnosisId(diagnosisId);

        return basicQuestionVo;
    }

    private void updateAnswerVo(BasicAnswerVo basicAnswer, Long userId) {
        String answerCode = "0";
        if ("自己".equals(basicAnswer.getAnswerTitle())) {    //自己
            answerCode = String.valueOf(userId);
            basicAnswer.setAnswerValue(answerCode);
        }
        if ("他人".equals(basicAnswer.getAnswerTitle())) { //他人
            basicAnswer.setAnswerValue(answerCode);
        }
    }

    private List<DiagnosisMainSymptoms> queryByBaidu(String content, List<String> wordList, List<DiagnosisMainSymptoms> allMainList) {
        Set<String> keywordSet = Stream.of(GlobalConstants.MAINSYMPTOM_KEYWORDS.split(",")).collect(Collectors.toSet());
        //找出短语包含的主症状关键字
        final Set<String> filterKeywordSet = keywordSet.stream().filter(e -> wordContainKeyword(wordList, e)).collect(Collectors.toSet());
        //查询包含有关键字的主症状
        final List<DiagnosisMainSymptoms> filterMainList = allMainList.stream().filter(e -> mainSymptomContainKeyword(filterKeywordSet, e)).collect(toList());
        //如果主症状名称与文本相似度超过60%的则把主症状拿出来
        NatureLanguageApi languageApi = new NatureLanguageApi();
        Predicate<DiagnosisMainSymptoms> simnetPredicate = (e) -> {
            Double simnetRate = languageApi.simnet(content, e.getSympName());
            if (simnetRate.doubleValue() >= 0.5) {
                return true;
            }
            return false;
        };
        List<DiagnosisMainSymptoms> resultList = filterMainList.stream().filter(simnetPredicate).collect(toList());
        return resultList;
    }

    /**
     * 短语是否包含有主症状部位关键字
     *
     * @param wordList
     * @param keyword
     * @return
     */
    private Boolean wordContainKeyword(List<String> wordList, String keyword) {
        return wordList.stream().anyMatch(e -> e.contains(keyword));
    }

    /**
     * 主症状是否包含有主症状部位关键字
     *
     * @param
     * @param mainSymptom
     * @return
     */
    private Boolean mainSymptomContainKeyword(Set<String> keywordSet, DiagnosisMainSymptoms mainSymptom) {
        return keywordSet.stream().anyMatch(e -> mainSymptom.getSympName().contains(e));
    }

    @Override
    public HisDiagnosisResultVo showHisDiagnosisResult(Long userId, Long diagnosisId) {
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        if (userInfo == null) {
            logger.info("根据用户id{}无法找到用户信息，无法生成病历", userId);
            return null;
        }
        HisDiagnosisRecord hisDiagnosisRecord = hospitalService.getByDiagnosisId(diagnosisId);
        UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
        if(record == null)
            return null;
        UserMedicalRecord userMedicalRecord = userMedicalRecordService.getByDiagnosisId(diagnosisId);
        if(userMedicalRecord == null) {
            logger.error("就诊编码{}没有找到病历", diagnosisId);
        }
        //HisDiagnosisResultVo resultVo = new HisDiagnosisResultVo(record, hisDiagnosisRecord);
        HisDiagnosisResultVo resultVo = new HisDiagnosisResultVo(record, hisDiagnosisRecord, userMedicalRecord);

        resultVo.merge(userInfo);
        //拼装诊断结果
        List<UserDiagnosisOutcome> udos = userDiagnosisOutcomeService.listTop5UserDiagnosisOutcome(diagnosisId, record.getMainSymptomCode());
        List<UserDiagnosisOutcomeVo> diseasevoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(udos)) {
            diseasevoList = udos.stream().map(UserDiagnosisOutcomeVo::new).collect(Collectors.toList());
        }
        String outPatientNo = userInfo.getOutPatientNo();
        String pno = record.getHisRegisterNo();
        if(!StringUtils.isEmpty(outPatientNo, pno)) {
            HisRegisterRecord hisRegisterRecord = hisRegisterRecordDao.getByOutPatientNoAndPno(outPatientNo, pno);
            if(hisRegisterRecord != null) {
                String vistTime = DateUtils.date2String(DateUtil.parseDate(hisRegisterRecord.getVisitTime()), DateUtils.DATE_FORMAT);
                resultVo.setCureTime(vistTime);
                resultVo.setDoctor(hisRegisterRecord.getDoctorName()==null?null:hisRegisterRecord.getDoctorName());
                resultVo.setBirth(hisRegisterRecord.getBirthday());
                resultVo.setOutPatientNo(hisRegisterRecord.getOutPatientNo());
            }
        }
        resultVo.setDiseaseList(diseasevoList);
        return resultVo;
    }

    @Override
    public void updateMedicineImportAction(String pNo) {
        //根据pNO查出当前的诊断ID号
        UserBasicRecord userBasicRecord = userBasicRecordService.getByHospitalCodeAndHisRegisterNo("", pNo);
        if(userBasicRecord == null) {
            logger.info("根据医院编码{} 与 挂号码{}无法找到就诊记录", "", pNo);
            return;
        }

        Long diD = userBasicRecord.getDiagnosisId();
        userMedicalRecordService.update(diD);
    }

    @Override
    public Integer medicineRecordShowExists(String pNo) {
        //默认-1:表示未能导入
        Integer flag = -1;
        UserBasicRecord userBasicRecord = userBasicRecordService.getByHospitalCodeAndHisRegisterNo("", pNo);
        if(userBasicRecord == null) {
            logger.info("根据医院编码{}挂号码{}无法找到就诊记录", "", pNo);
            return flag;
        }
        Long diagnosisId = userBasicRecord.getDiagnosisId();
        UserMedicalRecord userMedicalRecord = userMedicalRecordService.getByDiagnosisId(diagnosisId);
        if(null != userMedicalRecord){
            flag = userMedicalRecord.getImportFlag()==null?flag:userMedicalRecord.getImportFlag();
        }
        return flag;
    }
}
