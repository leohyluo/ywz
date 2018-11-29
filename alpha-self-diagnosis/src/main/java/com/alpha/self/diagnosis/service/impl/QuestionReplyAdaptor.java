package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.enums.AppType;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.DiagnosisResultVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.self.diagnosis.service.MedicineDiagnosisService;
import com.alpha.self.diagnosis.service.QuestionService;
import com.alpha.self.diagnosis.service.UserMedicalRecordService;
import com.alpha.self.diagnosis.utils.PreQuestionUtils;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.user.service.UserBasicRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.function.Function;

@Service
public class QuestionReplyAdaptor {
    @Resource(name = "baseQuestionServiceImpl")
    private QuestionService baseQuestionService;
    @Resource(name = "diagnosisMainSymptomServiceImpl")
    private QuestionService mainSympQuestionService;
    @Resource(name = "diagnosisQuestionServiceImpl")
    private QuestionService diagnosisQuestionService;
    @Resource(name = "diagnosisConcsympQuestionServiceImpl")
    private QuestionService concsympQuestionService;
    @Resource(name = "extQuestionServiceImpl")
    private QuestionService extDiagnosisQuestionService;
    @Resource
    private UserBasicRecordService userBasicRecordService;

    @Resource
    private MedicineDiagnosisService medicineDiagnosisService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public BasicQuestionVo answer(ReplyQuestionVo replyQuestionVo) {
        Integer questionType = replyQuestionVo.getQuestionType();
        String appType = replyQuestionVo.getSystemType();
        BasicQuestionVo basicQuestionVo = null;
        //基础问题
        if(QuestionEnum.isBasicQuestion(questionType)) {
            basicQuestionVo = baseQuestionService.answer(replyQuestionVo);
            //回答完基础信息，开始提问主症状
            if(questionType == QuestionEnum.完善信息.getValue()) {
                replyQuestionVo.setQuestionCode(null);
                basicQuestionVo = mainSympQuestionService.ask(replyQuestionVo);
            } else if (replyQuestionVo.getSystemType().equals(AppType.WOMAN.getValue()) && questionType.intValue() == QuestionEnum.引言.getValue().intValue()) {
                replyQuestionVo.setQuestionCode(null);
                basicQuestionVo = mainSympQuestionService.ask(replyQuestionVo);
            }
        } else if (QuestionEnum.isDiagnosisQuestion(questionType)) {
            //医学问题
            if(questionType == QuestionEnum.主症状.getValue()) {
                basicQuestionVo = mainSympQuestionService.answer(replyQuestionVo);
            } else if (questionType == QuestionEnum.医学问题.getValue()) {
                basicQuestionVo = diagnosisQuestionService.answer(replyQuestionVo);
            } else if (questionType == QuestionEnum.伴随症状.getValue()  || questionType == QuestionEnum.常见伴随症状.getValue()) {
                basicQuestionVo = concsympQuestionService.answer(replyQuestionVo);
            } else if (questionType == QuestionEnum.附加医学问题.getValue()) {
                basicQuestionVo = extDiagnosisQuestionService.answer(replyQuestionVo);
            }
            //返回诊断结果
            if(basicQuestionVo == null) {
                Long diagnosisId = replyQuestionVo.getDiagnosisId();
                basicQuestionVo = medicineDiagnosisService.diagnosisOutcomeView(diagnosisId);
            }
            if(basicQuestionVo.getQuestionType().intValue() == QuestionEnum.诊断结果.getValue().intValue()) {
                PreQuestion preQuestion;
                if (appType.equals(AppType.WOMAN.getValue())) {
                    preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.鼓励2);
                } else {
                    preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.鼓励1);
                }
                replyQuestionVo.setQuestionType(preQuestion.getQuestionType());
                replyQuestionVo.setQuestionCode(preQuestion.getQuestionCode());
                basicQuestionVo = baseQuestionService.ask(replyQuestionVo);
            }
            //修改bug6407(病情确认页面修改后，时间显示未更新)
            Long diagnosisId = replyQuestionVo.getDiagnosisId();
            UserBasicRecord userBasicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
            if (userBasicRecord != null) {
                userBasicRecord.setUpdateTime(new Date());
                userBasicRecordService.updateUserBasicRecord(userBasicRecord);
            }
        }
        return basicQuestionVo;
    }

}
