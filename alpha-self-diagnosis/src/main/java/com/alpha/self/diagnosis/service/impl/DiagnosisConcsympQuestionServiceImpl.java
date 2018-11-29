package com.alpha.self.diagnosis.service.impl;

import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
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
import java.util.ArrayList;
import java.util.LinkedHashSet;

@Service
public class DiagnosisConcsympQuestionServiceImpl implements QuestionService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource
    private UserDiagnosisDetailService userDiagnosisDetailService;
    @Resource(name = "diagnosisConcsympAnswerServiceImpl")
    private AnswerService diagnosisConcsympAnswerService;
    @Resource(name = "commonConcsympAnswerServiceImpl")
    private AnswerService commonConcsympAnswerService;
    @Resource(name = "diagnosisQuestionServiceImpl")
    private QuestionService diagnosisQuestionService;
    @Resource(name = "extQuestionServiceImpl")
    private QuestionService extQuestionService;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;


    @Override
    public BasicQuestionVo ask(ReplyQuestionVo replyQuestionVo) {
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        String mainSympCode = replyQuestionVo.getSympCode();
        String questionCode = replyQuestionVo.getQuestionCode();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(replyQuestionVo.getUserId()));
        //伴随症状问题
        DiagnosisMainsympQuestion dmQuestion = diagnosisMainsympQuestionDao.getDiagnosisMainsympQuestion(questionCode, mainSympCode);
        //伴随症状答案视图
        LinkedHashSet<IAnswerVo> diagnosisQuestionAnswerList;
        if (replyQuestionVo.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()) {
            diagnosisQuestionAnswerList = commonConcsympAnswerService.getAnswerView(diagnosisId, mainSympCode, questionCode, userInfo);
        } else {
            diagnosisQuestionAnswerList = diagnosisConcsympAnswerService.getAnswerView(diagnosisId, mainSympCode, questionCode, userInfo);
        }
        //组装问题、答案视图
        BasicQuestionVo basicQuestionVo = new BasicQuestionVo(dmQuestion, diagnosisId, mainSympCode, userInfo, replyQuestionVo.getSystemType());
        basicQuestionVo.setAnswers(new ArrayList<>(diagnosisQuestionAnswerList));
        userDiagnosisDetailService.addUserDiagnosisDetail(basicQuestionVo, userInfo);
        return basicQuestionVo;
    }

    @Override
    public BasicQuestionVo answer(ReplyQuestionVo replyQuestionVo) {
        //回答常见伴随症状的逻辑跟答案一样
        if (replyQuestionVo.getQuestionType() == QuestionEnum.常见伴随症状.getValue()) {
            return diagnosisQuestionService.answer(replyQuestionVo);
        } else {
            String mainSympCode = replyQuestionVo.getSympCode();
            String questionCode = replyQuestionVo.getQuestionCode();
            UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(replyQuestionVo.getUserId()));
            //当前问题
            DiagnosisMainsympQuestion dmQuestion = diagnosisMainsympQuestionDao.getDiagnosisMainsympQuestion(questionCode, mainSympCode);
            userDiagnosisDetailService.updateUserDiagnosisDetail(replyQuestionVo, userInfo, dmQuestion);
            //提问附加问题前先将同一诊号编号的附加问题删除
            userDiagnosisDetailDao.deleteAdditonalUserDiagnosisDetail(replyQuestionVo.getDiagnosisId());
            //接着问常见伴随症状的附加问题
            if (dmQuestion != null) {
                replyQuestionVo.setQuestionCode(dmQuestion.getQuestionCode());
                replyQuestionVo.setQuestionType(dmQuestion.getQuestionType());
                return extQuestionService.ask(replyQuestionVo);
            }
        }
        return null;
    }
}
