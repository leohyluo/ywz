package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.AnswerRequestVo;
import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.QuestionService;
import com.alpha.self.diagnosis.service.UserDiagnosisDetailService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserInfoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * 引申问题业务处理类
 */
@Service
public class ExtQuestionServiceImpl implements QuestionService {

    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource(name = "diagnosisQuestionAnswerServiceImpl")
    private AnswerService diagnosisQuestionAnswerService;
    @Resource
    private UserDiagnosisDetailService userDiagnosisDetailService;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;

    @Override
    public BasicQuestionVo ask(ReplyQuestionVo replyQuestionVo) {
        Integer questionType = replyQuestionVo.getQuestionType();
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        String userId = replyQuestionVo.getUserId();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));
        DiagnosisMainsympQuestion question = null;

        if (questionType == QuestionEnum.伴随症状.getValue()) {
            question = getNextAdditionalQuestion(userInfo, diagnosisId);
        } else if (questionType == QuestionEnum.附加医学问题.getValue()) {
            String questionCode = replyQuestionVo.getQuestionCode();
            DiagnosisMainsympQuestion dmQuestion = diagnosisMainsympQuestionDao.getDiagnosisMainsympQuestion(questionCode, replyQuestionVo.getSympCode());
            question = getNextQuestion(dmQuestion, replyQuestionVo, userInfo, 1);
            if (question == null) {
                //如果没下一个附加问题找下一个主症状的附加问题
                question = getNextAdditionalQuestion(userInfo, diagnosisId);
            }
        }

        if(question == null)
            return null;
        //组装问题对应的答案视图
        LinkedHashSet<IAnswerVo> diagnosisQuestionAnswerList = diagnosisQuestionAnswerService.getAnswerView(diagnosisId, question.getMainSympCode(), question.getQuestionCode(), userInfo);
        //组装问题、答案视图
        BasicQuestionVo basicQuestionVo = new BasicQuestionVo(question, diagnosisId, question.getMainSympCode(), userInfo, replyQuestionVo.getSystemType());
        basicQuestionVo.setQuestionType(QuestionEnum.附加医学问题.getValue());
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
        userDiagnosisDetailService.updateUserDiagnosisDetail(replyQuestionVo, userInfo, dmQuestion);

        replyQuestionVo.setQuestionCode(dmQuestion.getQuestionCode());
        replyQuestionVo.setQuestionType(QuestionEnum.附加医学问题.getValue());
        return this.ask(replyQuestionVo);
    }

    /**
     * 获取第一个引申问题
     * @param userInfo
     * @param diagnosisId
     * @param
     * @return
     */
    private DiagnosisMainsympQuestion getNextAdditionalQuestion(UserInfo userInfo, Long diagnosisId) {
        List<UserDiagnosisDetail> userDiagnosisDetailList = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
        if(CollectionUtils.isEmpty(userDiagnosisDetailList))
            return null;
        Optional<UserDiagnosisDetail> optionalUserDiagnosisDetail = userDiagnosisDetailList.stream().filter(e->e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue())
                .findFirst();
        UserDiagnosisDetail udd = null;
        if(optionalUserDiagnosisDetail.isPresent()) {
            udd = optionalUserDiagnosisDetail.get();
        }
        if(udd == null)
            return  null;
        //找出此次问诊的主症状与需要问附加问题的常见伴随症状关系
        Optional<String> mainSympOptional = userDiagnosisDetailList.stream().filter(e->e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.主症状.getValue())
                .map(UserDiagnosisDetail::getSympCode).findFirst();
        if(!mainSympOptional.isPresent())
            return null;
        String mainSympCode = mainSympOptional.get();
        Map<String, Object> param = new HashMap<>();
        param.put("sympCode", mainSympCode);
        List<DiagnosisMainSymptoms> diagnosisMainSymptomsList0 = diagnosisMainSymptomsDao.query(param);
        if(CollectionUtils.isEmpty(diagnosisMainSymptomsList0))
            return null;
        DiagnosisMainSymptoms diagnosisMainSymptom = diagnosisMainSymptomsList0.get(0);
        String normalSympCodes = diagnosisMainSymptom.getNormalSymptomCode();
        Set<String> needAskSympCodeSet = new HashSet<>(); //主症状需要问附加问题的常见伴随症状有哪些
        if(StringUtils.isNotEmpty(normalSympCodes)) {
            needAskSympCodeSet = Stream.of(normalSympCodes.split(",")).collect(toSet());
        }

        //根据常见伴随症状的名称查询主症状
        List<DiagnosisMainSymptoms> diagnosisMainSymptomsList = new ArrayList<>();
        String mainSymptomNames = udd.getAnswerContent();
        if(StringUtils.isEmpty(mainSymptomNames) || GlobalConstants.NONE.equals(mainSymptomNames))
            return null;
        String[] mainSymptomNameArr = mainSymptomNames.split("、");
        for(String mainSymptomName : mainSymptomNameArr) {
            DiagnosisMainSymptoms dms = diagnosisMainSymptomsDao.queryByName(mainSymptomName);
            if (dms != null)
                diagnosisMainSymptomsList.add(dms);
        }
        //已回答过附加问题的主症状
        DiagnosisMainsympQuestion question = null;
        List<String> repliedMainSymptomList = userDiagnosisDetailList.stream().filter(e->e.getQuestionType() != null).filter(e->e.getQuestionType() == QuestionEnum.附加医学问题.getValue()).filter(e->StringUtils.isNotEmpty(e.getAnswerJson())).map(UserDiagnosisDetail::getSympCode).distinct().collect(toList());
        if(CollectionUtils.isEmpty(repliedMainSymptomList)) {
            for(DiagnosisMainSymptoms dms : diagnosisMainSymptomsList) {
                //常见伴随症状的附加问题是否需要在主症状下提问
                if(needAskSympCodeSet.contains(dms.getSympCode())) {
                    AnswerRequestVo answerVo = new AnswerRequestVo();
                    answerVo.setContent(dms.getSympCode());
                    question = getNextDiagnosisMainsympQuestion(answerVo.getContent(), 0, userInfo, 1);
                    if (question != null) break;
                }
            }
        } else {
            for(DiagnosisMainSymptoms dms : diagnosisMainSymptomsList) {
                if(!repliedMainSymptomList.contains(dms.getSympCode())) {
                    //常见伴随症状的附加问题是否需要在主症状下提问
                    if(needAskSympCodeSet.contains(dms.getSympCode())) {
                        AnswerRequestVo answerVo = new AnswerRequestVo();
                        answerVo.setContent(dms.getSympCode());
                        question = getNextDiagnosisMainsympQuestion(answerVo.getContent(), 0, userInfo, 1);
                        if(question != null) break;
                    }
                }
            }
        }
        return question;
    }
}
