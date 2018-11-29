package com.alpha.self.diagnosis.service.impl;

import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.QuestionService;
import com.alpha.self.diagnosis.service.UserDiagnosisDetailService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.LinkedHashSet;

/**
 * 医学问题业务处理类
 */
@Service
public class DiagnosisQuestionServiceImpl implements QuestionService {

    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource(name = "diagnosisQuestionAnswerServiceImpl")
    private AnswerService diagnosisQuestionAnswerService;
    @Resource(name = "diagnosisConcsympAnswerServiceImpl")
    private AnswerService diagnosisConcsympAnswerService;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserDiagnosisDetailService userDiagnosisDetailService;

    @Resource(name = "diagnosisMainSymptomServiceImpl")
    private QuestionService mainSympQuestionService;

    @Override
    public BasicQuestionVo ask(ReplyQuestionVo replyQuestionVo) {
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        String mainSympCode = replyQuestionVo.getSympCode();
        String questionCode = replyQuestionVo.getQuestionCode();
        Integer questionType = replyQuestionVo.getQuestionType();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(replyQuestionVo.getUserId()));
        //医学问题
        DiagnosisMainsympQuestion dmQuestion = diagnosisMainsympQuestionDao.getDiagnosisMainsympQuestion(questionCode, mainSympCode);
        //组装问题对应的答案视图
        LinkedHashSet<IAnswerVo> diagnosisQuestionAnswerList = diagnosisQuestionAnswerService.getAnswerView(diagnosisId, mainSympCode, questionCode, userInfo);
        //组装问题、答案视图
        BasicQuestionVo basicQuestionVo = new BasicQuestionVo(dmQuestion, diagnosisId, mainSympCode, userInfo, replyQuestionVo.getSystemType());
        if(questionType.intValue() == QuestionEnum.附加医学问题.getValue().intValue()) {
            basicQuestionVo.setQuestionType(questionType);
        }
        basicQuestionVo.setAnswers(new ArrayList<>(diagnosisQuestionAnswerList));
        userDiagnosisDetailService.addUserDiagnosisDetail(basicQuestionVo, userInfo);
        return basicQuestionVo;
    }

    @Override
    public BasicQuestionVo answer(ReplyQuestionVo replyQuestionVo) {
        String mainSympCode = replyQuestionVo.getSympCode();
        String questionCode = replyQuestionVo.getQuestionCode();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(replyQuestionVo.getUserId()));
        //当前问题
        DiagnosisMainsympQuestion dmQuestion = diagnosisMainsympQuestionDao.getDiagnosisMainsympQuestion(questionCode, mainSympCode);
        //下一个问题
        //Integer isAdditional = dmQuestion.getIsAdditional() == null ? 0 : dmQuestion.getIsAdditional();
        DiagnosisMainsympQuestion nextQuestion = getNextQuestion(dmQuestion, replyQuestionVo, userInfo, 0);
        userDiagnosisDetailService.updateUserDiagnosisDetail(replyQuestionVo, userInfo, dmQuestion);

        QuestionService questionService = getQuestionService(nextQuestion.getQuestionType());
        replyQuestionVo.setQuestionCode(nextQuestion.getQuestionCode());
        replyQuestionVo.setQuestionType(nextQuestion.getQuestionType());
        return questionService.ask(replyQuestionVo);
    }
}
