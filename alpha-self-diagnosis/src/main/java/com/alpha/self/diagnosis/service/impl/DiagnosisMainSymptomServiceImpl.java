package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.core.thread.ThreadPoolScheduler;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.DisplayType;
import com.alpha.commons.enums.System;
import com.alpha.commons.util.DateUtils;
import com.alpha.his.utils.AppUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.service.MedicineAnswerAutoService;
import com.alpha.self.diagnosis.service.QuestionService;
import com.alpha.self.diagnosis.service.UserDiagnosisDetailService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.enums.ObjectVersionEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserBasicRecordDao;
import com.alpha.user.dao.UserInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class DiagnosisMainSymptomServiceImpl implements QuestionService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private UserDiagnosisDetailService userDiagnosisDetailService;
    @Resource
    private UserBasicRecordDao userBasicRecordDao;
    @Resource
    private MedicineAnswerAutoService medicineAnswerAutoService;

    @Override
    public BasicQuestionVo ask(ReplyQuestionVo replyQuestionVo) {
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        String systemType = replyQuestionVo.getSystemType();
        String userId = replyQuestionVo.getUserId();

        BasicQuestionVo questionVo = new BasicQuestionVo();
        List<IAnswerVo> basicAnswers = new ArrayList<>();

        questionVo.setQuestionCode("9990");
        questionVo.setDiagnosisId(diagnosisId);

        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));
        //预问诊不需要主诉提问，直接返回主诉列表
        if(systemType.equals(AppType.CHILD.getValue())) {
            questionVo.setType(QuestionEnum.主症状.getValue());
            questionVo.setDisplayType(DisplayType.RADIO_MORE_INPUT_CONFIRM.getValue());
            String questionTitle = "请问{userName}哪里最不舒服?(单选)";
            questionTitle = AppUtils.setUserNameAtQuestionTitle(systemType, questionTitle, userInfo, null);
            questionVo.setQuestionTitle(questionTitle);
            questionVo.setTitle(questionTitle);
            //查询符合条件的主症状
            Map<String, Object> param = new HashMap<>();
            List<DiagnosisMainSymptoms> mainList = diagnosisMainSymptomsDao.query(param);
            mainList = mainList.stream().filter(e -> e.mainSymptomPredicate(userInfo, userInfo.getInType(), ObjectVersionEnum.儿童版.getValue())).collect(toList());
            basicAnswers = mainList.stream().map(BasicAnswerVo::new).collect(toList());
            questionVo.setAnswers(basicAnswers);
        } else if (systemType.equals(AppType.WOMAN.getValue())) {
            questionVo.setType(QuestionEnum.主症状.getValue());
            questionVo.setDisplayType(DisplayType.RADIO_MORE_INPUT_CONFIRM.getValue());
            String questionTitle = "请问您哪里最不舒服?(单选)";
            questionVo.setQuestionTitle(questionTitle);
            questionVo.setTitle(questionTitle);
            //查询符合条件的主症状
            Map<String, Object> param = new HashMap<>();

            List<DiagnosisMainSymptoms> mainList = diagnosisMainSymptomsDao.query(param);

            mainList = mainList.stream().filter(e -> e.mainSymptomPredicate(userInfo, userInfo.getInType(), ObjectVersionEnum.妇女版.getValue()))
                    .filter(e->e.getIsShow() != null && e.getIsShow() == 1).collect(toList());

            basicAnswers = mainList.stream().map(BasicAnswerVo::new).collect(toList());
            questionVo.setAnswers(basicAnswers);
        } else {//阿尔法医生需要主诉提问
            questionVo.setType(QuestionEnum.主症状语义分析.getValue());
            questionVo.setDisplayType("mainsymptom_input");
            String questionTitle = "{userName}的基本情况我已经清楚了解，现在告诉我最不舒服的是什么";
            questionTitle = AppUtils.setUserNameAtQuestionTitle(systemType, questionTitle, userInfo, null);
            questionVo.setQuestionTitle(questionTitle);
            //questionVo.setTitle(userBasicRecordService.getUserName(diagnosisId, userInfo) + "的基本情况我已经清楚了解，现在告诉我最不舒服的是什么");
            questionVo.setTitle("您的基本情况我已经清楚了解，现在告诉我最不舒服的是什么");
            questionVo.setAnswers(basicAnswers);
        }
        questionVo.setUserId(userInfo.getUserId()+"");
        questionVo.setSympCode("");
        // 保存正向反向特异性疾病
        userDiagnosisDetailService.addUserDiagnosisDetail(questionVo, userInfo);
        return questionVo;
    }

    @Override
    public BasicQuestionVo answer(ReplyQuestionVo replyQuestionVo) {
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        ReplyAnswerVo answerVo = replyQuestionVo.getAnswers().get(0);
        replyQuestionVo.setSympCode(answerVo.getAnswerCode());//如果是主症状提问，那么答案编码就是主症状编码

        String mainSympCode = replyQuestionVo.getSympCode();
        UserBasicRecord userBasicRecord = userBasicRecordDao.findByDiagnosisId(diagnosisId);
        userBasicRecord.setMainSymptomCode(mainSympCode);
        userBasicRecord.setUpdateTime(new Date());
        userBasicRecordDao.update(userBasicRecord);

        String userId = replyQuestionVo.getUserId();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));
        userDiagnosisDetailService.updateUserDiagnosisDetail(replyQuestionVo, userInfo, null);

        //异步回答年龄、季节问题
        ThreadPoolScheduler.addTask(()->{
            //先处理下不需要回答的问题(年龄、季节)
            medicineAnswerAutoService.autoCalculateAnswer(diagnosisId, replyQuestionVo.getSympCode(), userInfo);
        });
        //获取主症状的第一个问题
        DiagnosisMainsympQuestion firstQuestion = getFirstDiagnosisMainsympQuestion(replyQuestionVo.getSympCode(), 0, userInfo, 0);
        QuestionService questionService = getQuestionService(firstQuestion.getQuestionType());
        replyQuestionVo.setQuestionCode(firstQuestion.getQuestionCode());
        replyQuestionVo.setQuestionType(firstQuestion.getQuestionType());
        return questionService.ask(replyQuestionVo);
    }

    /**
     * 获取第一个问题
     * @param mainSympCode
     * @param defaultOrder
     * @param userInfo
     * @return
     */
    private DiagnosisMainsympQuestion getFirstDiagnosisMainsympQuestion(String mainSympCode, int defaultOrder, UserInfo userInfo, int isAdditional) {
        DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao = SpringContextHolder.getBean("diagnosisMainsympQuestionDaoImpl");
        List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listNextAllQuestion(mainSympCode, defaultOrder);
        if(isAdditional == 1) {
            dmQuestions = dmQuestions.stream().filter(e->e.getIsAdditional() != null).filter(e->e.getIsAdditional().intValue() == 1).collect(toList());
        }
        if (dmQuestions.size() == 0) {
            return null;
        }
        for (DiagnosisMainsympQuestion question : dmQuestions) {
            if (question.getGender() != null && question.getGender() > 0 && question.getGender() != userInfo.getGender()) {
                continue;//过滤性别
            }
            float age = DateUtils.getAge(userInfo.getBirth());
            if ((question.getMinAge() != null && question.getMinAge() > age) || (question.getMaxAge() != null && question.getMaxAge() < age)) {
                continue;//过滤年龄
            }
            return question;
        }
        return null;
    }
}
