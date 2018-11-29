package com.alpha.self.diagnosis.controller;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.DiagnosisStatus;
import com.alpha.commons.enums.RegisterType;
import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.service.etyy.RegisterService;
import com.alpha.redis.RedisMrg;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.service.*;
import com.alpha.self.diagnosis.service.impl.QuestionReplyAdaptor;
import com.alpha.self.diagnosis.utils.MedicineTemplateFactory;
import com.alpha.self.diagnosis.utils.PreQuestionUtils;
import com.alpha.self.diagnosis.utils.template.Template;
import com.alpha.self.diagnosis.utils.template.WomenTemplate;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.pojo.vo.PageData;
import com.alpha.user.service.UserBasicRecordService;
import com.alpha.user.service.UserInfoService;
import com.alpha.wechar.service.OfficalAccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by xc.xiong on 2017/9/1.
 * 问诊流程
 */
@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Resource
    private DiagnosisService diagnosisService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserDiagnosisOutcomeService userDiagnosisOutcomeService;
    @Resource
    private UserBasicRecordService userBasicRecordService;
    @Resource
    private OfficalAccountService officalAccountService;
    @Autowired
    YwzCountTimesService ywzCountTimesService;
    @Resource
    private DiagnosisHistoryService diagnosisHistoryService;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private UserMedicalRecordService userMedicalRecordService;
    @Resource
    private DiagnosisDraftService diagnosisDraftService;
    @Resource
    private RegisterService registerService;
    @Resource(name = "diagnosisMainSymptomServiceImpl")
    private QuestionService diagnosisMainSymptomQuestionService;
    @Resource(name = "baseQuestionServiceImpl")
    private QuestionService baseQuestionService;
    @Resource(name = "diagnosisQuestionServiceImpl")
    private QuestionService diagnosisQuestionService;
    @Resource(name = "diagnosisConcsympQuestionServiceImpl")
    private QuestionService concsympQuestionService;
    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;
    @Resource
    private QuestionReplyAdaptor questionReplyAdaptor;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 开始问诊，生成问诊编号
     *
     * @return diagnosisId  唯一诊断编号
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST, consumes = {"application/json", "application/x-www-form-urlencoded"})
    public ResponseMessage diagnosisStart(Long userId, Integer inType, String type, String channel, String pno, Integer diagnosisType, Long diagnosisId) {
        logger.info("生成问诊编号,为导诊做准备: {} {}", userId, inType);
        if(userId == null || StringUtils.isEmpty(pno)) {
        	return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        BasicQuestionVo firstQuestion = diagnosisService.start(userId, inType, type, channel, pno, diagnosisType, diagnosisId);
        return WebUtils.buildSuccessResponseMessage(firstQuestion);
    }

    /**
     * 循环获取下一个问题
     * 接受答案信息
     * 如果没有问题编号，从第一个开始
     *
     * @return diagnosisId  唯一诊断编号
     */
    @PostMapping("/pre/next")
    public ResponseMessage nextQuestion(String allParam) {
        ReplyQuestionVo questionVo = JSON.parseObject(allParam,ReplyQuestionVo.class);
        String requestParam = JSON.toJSONString(questionVo);
        logger.info("循环获取下一个问题: {}", requestParam);
        if (questionVo == null || questionVo.getDiagnosisId() == null || StringUtils.isEmpty(questionVo.getUserId())
                || StringUtils.isEmpty(questionVo.getSystemType())) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        UserInfo userInfo = userInfoService.queryByUserId(Long.valueOf(questionVo.getUserId()));
        if (userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        BasicQuestionVo basicQuestionVo;
        //获取第一个基础问题
        if (StringUtils.isEmpty(questionVo.getQuestionCode())) {
            questionVo.setQuestionType(QuestionEnum.介绍信息.getValue());
            PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.介绍信息);
            questionVo.setQuestionCode(preQuestion.getQuestionCode());
            basicQuestionVo = baseQuestionService.ask(questionVo);
            return WebUtils.buildSuccessResponseMessage(basicQuestionVo);
        }
        //回答问题
        basicQuestionVo = questionReplyAdaptor.answer(questionVo);
        if(basicQuestionVo == null) {
            //生成病历
            Long diagnosisId = questionVo.getDiagnosisId();
            UserBasicRecord userBasicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
            Function<String, String> function = e ->{
                StringBuilder sb = new StringBuilder();
                sb.append("患者对").append("<span style=\"color:red\">").append(userBasicRecord.getAllergicHistoryText()).append("</span>").append("过敏").append("。");
                return sb.toString();
            };
            UserMedicalRecord userMedicalRecord = userMedicalRecordService.build(diagnosisId, function);
            DiagnosisResultVo diagnosisResultVo = new DiagnosisResultVo(userInfo, diagnosisId, userMedicalRecord);
            return WebUtils.buildResponseMessage(ResponseStatus.DIAGNOSIS_COMPLETED, diagnosisResultVo);
        }
        return WebUtils.buildSuccessResponseMessage(basicQuestionVo);
    }

    /**
     * 获取问题和答案
     * @return diagnosisId  唯一诊断编号
     */
    @PostMapping("/questionAndAnswer/get")
    public ResponseMessage getDiagnosisQuestionAndAnswer(Long diagnosisId, String mainSympCode, String questionCode, Integer questionType, Long userId, String systemType) {
        logger.info("获取问题和答案请求参数diagnosisId={}，mainSympCode={},questionCode={},questionType={},userId={}",diagnosisId, mainSympCode, questionCode, questionType, userId);
        if(diagnosisId == null || questionType == null || userId == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        IQuestionVo result = null;
        if(userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        AppType appType = AppType.CHILD;
        if(StringUtils.isNotEmpty(systemType)) {
            appType = AppType.findByValue(systemType);
        }
        if(appType == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
        }
        ReplyQuestionVo replyQuestionVo = new ReplyQuestionVo();
        replyQuestionVo.setUserId(userId.toString());
        replyQuestionVo.setQuestionType(questionType);
        replyQuestionVo.setSystemType(appType.getValue());
        replyQuestionVo.setDiagnosisId(diagnosisId);
        replyQuestionVo.setSympCode(mainSympCode);
        replyQuestionVo.setQuestionCode(questionCode);

        if(QuestionEnum.isBasicQuestion(questionType)) {
            QuestionEnum questionEnum = QuestionEnum.getQuestion(questionType);
            PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(questionEnum);
            if(preQuestion == null) {
                throw new ServiceException(ResponseStatus.BASIC_QUESTION_NOT_FOUND);
            }
            replyQuestionVo.setQuestionCode(preQuestion.getQuestionCode());
            //result = preQuestionService2.get(replyQuestionVo, preQuestion);
            result = baseQuestionService.ask(replyQuestionVo);
        } else if (QuestionEnum.主症状.getValue() == questionType){
            //result = basicQuestionService.getMainSymptomsQuestion(AppType.PRE.getValue(), diagnosisId, userInfo);
            result = diagnosisMainSymptomQuestionService.ask(replyQuestionVo);
        } else if (QuestionEnum.isDiagnosisQuestion(questionType)) {
            if (com.alpha.commons.util.StringUtils.isEmpty(mainSympCode, questionCode)) {
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
            if (questionType.intValue() == QuestionEnum.常见伴随症状.getValue().intValue() || questionType.intValue() == QuestionEnum.伴随症状.getValue().intValue()) {
                result = concsympQuestionService.ask(replyQuestionVo);
            } else {
                result = diagnosisQuestionService.ask(replyQuestionVo);
            }
        }
        return WebUtils.buildSuccessResponseMessage(result);
    }

    /**
     * 确认诊断结果
     *
     * @param diagnosisId
     * @return
     */
    @PostMapping("/outcome/confirm")
    public ResponseMessage confirmOutcome(Long diagnosisId, String diseaseCode) {
        try {
            if (diagnosisId == null || StringUtils.isEmpty(diseaseCode)) {
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
            userDiagnosisOutcomeService.confirmODiagnosisOutcome(diagnosisId, diseaseCode);
        } catch (Exception e) {
            logger.error("确认诊断结果发现异常", e);
        }
        return WebUtils.buildResponseMessage(ResponseStatus.SUCCESS);
    }
    
    /**
     * 问诊结束后展示就诊信息
     *
     * @param basicRequestVo
     * @return
     */
    @PostMapping("/showResult")
    public ResponseMessage showDiagnosisResult(BasicRequestVo basicRequestVo) {
        Long userId = basicRequestVo.getUserId();
        Long diagnosisId = basicRequestVo.getDiagnosisId();
        if (userId == null || diagnosisId == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        if (userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
        if(record == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        DiagnosisResultVo diagnosisResultVo = null;
        try {
            Template template = MedicineTemplateFactory.build(diagnosisId);
            if(template == null) {
                return WebUtils.buildResponseMessage(ResponseStatus.TEMPLATE_NOT_FOUND);
            }
            UserMedicalRecord userMedicalRecord = userMedicalRecordService.getByDiagnosisId(diagnosisId);
            if (userMedicalRecord == null) {
                userMedicalRecord = new UserMedicalRecord();
            }
            Function<String, String> function = e ->{
                StringBuilder sb = new StringBuilder();
                sb.append("患者对").append("<span style=\"color:red\">").append(record.getAllergicHistoryText()).append("</span>").append("过敏").append("。");
                return sb.toString();
            };
            if (template instanceof WomenTemplate) {
                WomenTemplate womenTemplate = (WomenTemplate) template;
                userMedicalRecord.setDiagnosisId(diagnosisId);
                userMedicalRecord.setMainSymptom(womenTemplate.getMultiSymptomName());
                userMedicalRecord.setHistoryOfPresent(womenTemplate.getPresenetIllHistory());
                userMedicalRecord.setHistoryOfPast(womenTemplate.getPastIllHistory(function));
                userMedicalRecord.setHistoryOfMenstruation(womenTemplate.getMenstruationHistory());
                userMedicalRecord.setHistoryOfMarriage(womenTemplate.getMarriageHistory());
            } else {
                userMedicalRecord.setDiagnosisId(diagnosisId);
                userMedicalRecord.setMainSymptom(template.getMultiSymptomName());
                userMedicalRecord.setHistoryOfPresent(template.getPresenetIllHistory());
                userMedicalRecord.setHistoryOfPast(template.getPastIllHistory(function));
            }
            userMedicalRecordService.save(userMedicalRecord);
            diagnosisResultVo = new DiagnosisResultVo(userInfo, diagnosisId, userMedicalRecord);
        } catch (Exception e) {
            diagnosisResultVo = new DiagnosisResultVo();
            e.printStackTrace();
        }

        //DiagnosisResultVo diagnosisResult = diagnosisService.showDiagnosisResult(userId, diagnosisId);
        return WebUtils.buildSuccessResponseMessage(diagnosisResultVo);
    }

    /**
     * 查看用户病历-对接页面
     * @return
     */
    @PostMapping("/diagnosisRecord/{diagnosisId}")
    public ResponseMessage queryMedicalRecord4His(@PathVariable Long diagnosisId) {
        if (diagnosisId == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        UserBasicRecord basicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
        if(basicRecord == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        HisDiagnosisResultVo diagnosisResult = diagnosisService.showHisDiagnosisResult(basicRecord.getUserId(), basicRecord.getDiagnosisId());

        return WebUtils.buildSuccessResponseMessage(diagnosisResult);
    }

    /**
     * 查看病历列表
     * @param pageIndex 页码
     * @param pageCount 数量
     * @return
     */
    @PostMapping("/diagnosisRecord/list")
    public ResponseMessage queryMedicalRecord(int pageIndex, int pageCount) {
        int copyIndex = pageIndex;
        pageIndex = pageIndex > 0 ? (pageIndex - 1) * pageCount : pageIndex;

        PageData pageData = userBasicRecordService.findByMedicalList(pageIndex, pageCount);
        pageData.setCurPage(copyIndex);
        if(pageData == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        return WebUtils.buildSuccessResponseMessage(pageData);
    }

    /**
     * 确认病历
     * @param diagnosisId
     * @return
     */

    @PostMapping("/result/confirm")
    public ResponseMessage confirmDiagnosisResult(String mobile, Long diagnosisId){
    	if(diagnosisId == null) {
    		return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
    	}
    	UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
    	if(record == null) {
    		return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
    	}
    	UserInfo userInfo = userInfoService.queryByUserId(record.getUserId());
    	if(userInfo == null) {
    		return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
    	}
    	UserMedicalRecord userMedicalRecord = userMedicalRecordService.getByDiagnosisId(diagnosisId);
    	if(userMedicalRecord == null) {
    	    return WebUtils.buildResponseMessage(ResponseStatus.MEDICAL_TEMPLATE_NOT_FOUND);
        }
    	
    	if(StringUtils.isNotEmpty(mobile)) {
    		record.setPhoneNum(mobile);
    		userInfo.setPhoneNumber(mobile);
    		userInfoService.save(userInfo);
    	}
    	record.setStatus(DiagnosisStatus.PRE_DIAGNOSIS_FINISH.getValue());
    	userMedicalRecord.setStatus(Integer.parseInt(DiagnosisStatus.PRE_DIAGNOSIS_FINISH.getValue()));
    	//生成二维码
    	/*QRCodeDTO codeDto = officalAccountService.getTempQRCode(userInfo.getUserId().toString(), diagnosisId);
    	if(codeDto != null && codeDto.isSuccess()) {
    		record.setQrCode(codeDto.getMsg());
    	}*/
    	//标记此次预问诊已结束
    	userBasicRecordService.updateUserBasicRecord(record);
    	userMedicalRecordService.save(userMedicalRecord);

        //将完成问诊标识存入缓存中
        String pno = record.getHisRegisterNo();
        String key = GlobalConstants.REDIS_KEY_WENZHEN_OVER_FLAG_ + pno;
        RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire(key, "1", GlobalConstants.REDIS_KEY_WENZHEN_OVER_TIME_, RedisMrg.DB4);


        YwzCountTimes ywzCountTimes=new YwzCountTimes();
        ywzCountTimes.setType(4);
        ywzCountTimes.setDiseaseId(String.valueOf(diagnosisId));
        ywzCountTimesService.addTimes(ywzCountTimes);
        return WebUtils.buildSuccessResponseMessage();
    }

    @PostMapping("/isFinish")
    public ResponseMessage diagnosisCompleted(String outPatientNo, String pno, String type) {
        if (com.alpha.commons.util.StringUtils.isEmpty(outPatientNo, pno, type)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        HisRegisterRecord hrr = registerService.getHisRegisterRecord(pno);
        LocalDate today = LocalDate.now();
        String visitTimeStr = hrr.getVisitTime();
        LocalDate visitTime = DateUtils.string2LocalDate(visitTimeStr);
        if(visitTime.isBefore(today)) {
            throw new ServiceException(ResponseStatus.VISIT_TIME_EXPIRED);
        }

        UserInfo userInfo = userInfoService.getByOutPatientNo(outPatientNo);
        List<UserBasicRecord> userBasicRecordList = new ArrayList<>();
        UserBasicRecord userBasicRecord = null;
        if (type.equals(RegisterType.LIVE.getValue())) {
            userBasicRecordList = userBasicRecordService.getForLive(pno);
        } else if (type.equals(RegisterType.APPOINTMENT.getValue())) {
            userBasicRecordList = userBasicRecordService.getForAppointment(pno);
            //解决bug 6200 (推送type=2 未完成预问诊（门诊号17012114082），再推送type=3，完成预问诊后，再进去type=2推送链接，显示不是提交病历后的界面)
            if(CollectionUtils.isEmpty(userBasicRecordList)) {
                //点击链接时，判断有没有从线下取号后推送的链接进去完成预问诊
                HisRegisterYygh param = new HisRegisterYygh();
                param.setYno(pno);
                param.setType(Integer.parseInt(RegisterType.GET_FOR_APPOINTMENT.getValue()));
                List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.select(param);
                hisRegisterYyghList = hisRegisterYyghList.stream().filter(e->StringUtils.isNotEmpty(e.getPno())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(hisRegisterYyghList)) {
                    HisRegisterYygh hisRegisterYygh = hisRegisterYyghList.get(0);
                    userBasicRecordList = userBasicRecordService.getForLive(hisRegisterYygh.getPno());
                }
            }
        } else if (type.equals(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
            userBasicRecordList = userBasicRecordService.getForLive(pno);
            //解决bug 6200
            if(CollectionUtils.isEmpty(userBasicRecordList)) {
                //点击链接时，判断有没有从预约连接进去完成预问诊
                HisRegisterYygh param = new HisRegisterYygh();
                param.setPno(pno);
                param.setType(Integer.parseInt(RegisterType.GET_FOR_APPOINTMENT.getValue()));
                List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.select(param);
                hisRegisterYyghList = hisRegisterYyghList.stream().filter(e -> StringUtils.isNotEmpty(e.getYno())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(hisRegisterYyghList)) {
                    HisRegisterYygh hisRegisterYygh = hisRegisterYyghList.get(0);
                    userBasicRecordList = userBasicRecordService.getForAppointment(hisRegisterYygh.getYno());
                }
            }
        }
        if(CollectionUtils.isNotEmpty(userBasicRecordList)) {
            userBasicRecord = userBasicRecordList.get(0);
        }
        String diagnosisId = "";
        String userId = userInfo == null ? "" : userInfo.getUserId().toString();
        if(userBasicRecord != null) {
            diagnosisId = userBasicRecord.getDiagnosisId().toString();
            //userId = userBasicRecord.getUserId().toString();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("diagnosisId", diagnosisId);
        return WebUtils.buildSuccessResponseMessage(result);
    }

    @PostMapping("/detail/{userId}/{diagnosisId}")
    public ResponseMessage diagnosisDetail(@PathVariable Long userId, @PathVariable Long diagnosisId) {
        logger.info("diagnosisDetail request param userId={},diagnosisId={}", userId, diagnosisId);
        if(userId == null || diagnosisId == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        AppType appType = AppType.CHILD;
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        if(userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        UserBasicRecord userBasicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
        if(userBasicRecord == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        String pno = userBasicRecord.getHisRegisterNo();
        logger.info("diagnosisId {} 对应的 pno 为 {}", diagnosisId, pno);
        HisRegisterRecord hisRegisterRecord = registerService.getHisRegisterRecord(pno);
        String patientName = "";
        String visitTime = "";
        if(hisRegisterRecord != null) {
            patientName = hisRegisterRecord.getPatientName();
            if (userBasicRecord.getUpdateTime() != null) {
                visitTime = DateUtils.date2String(userBasicRecord.getUpdateTime(), DateUtils.DATE_TIME_FORMAT);
            }
        }
        List<BasicQuestionVo> basicQuestionVoList = diagnosisDraftService.confirm(diagnosisId, appType);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("patientName", patientName);
        resultMap.put("visitTime", visitTime);
        resultMap.put("diagnosisDetail", basicQuestionVoList);
        return WebUtils.buildSuccessResponseMessage(resultMap);
    }

    @PostMapping("/presentIllHistory/rebuild/{diagnosisId}")
    public ResponseMessage rebuildUserBasicRecord(@PathVariable Long diagnosisId) {
        Template template = null;
        try {
            template = MedicineTemplateFactory.build(diagnosisId);
            if(template == null) {
                return WebUtils.buildResponseMessage(ResponseStatus.TEMPLATE_NOT_FOUND);
            }
            UserMedicalRecord userMedicalRecord = userMedicalRecordService.getByDiagnosisId(diagnosisId);
            if (userMedicalRecord == null) {
                logger.error("根据诊断ID{}没找到对应的病历", diagnosisId);
                return WebUtils.buildResponseMessage(ResponseStatus.MEDICAL_TEMPLATE_NOT_FOUND);
            }
            //更新病历的现病史
            userMedicalRecord.setMainSymptom(template.getSymptomName());
            userMedicalRecord.setHistoryOfPresent(template.getPresenetIllHistory());
            userMedicalRecordService.save(userMedicalRecord);
        } catch (Exception e) {
            logger.error("重新生成病历出现异常", e);
        }

        //medicineQuestionService.createPresentIllnessHistory4MedicalRecord(AppType.PRE, diagnosisId, null);
        return WebUtils.buildSuccessResponseMessage();
    }

    @PostMapping("/draft/{systemType}/{pno}")
    public ResponseMessage draft(@PathVariable String systemType, @PathVariable String pno) {
        if(com.alpha.commons.util.StringUtils.isEmpty(pno, systemType)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        AppType appType = AppType.findByValue(systemType);
        if(appType == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
        }
        List<BasicQuestionVo> basicQuestionVoList = diagnosisDraftService.getDraft(pno, appType);
        return WebUtils.buildSuccessResponseMessage(basicQuestionVoList);
    }

}
