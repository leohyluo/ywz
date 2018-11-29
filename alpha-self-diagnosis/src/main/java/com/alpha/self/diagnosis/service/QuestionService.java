package com.alpha.self.diagnosis.service;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.dao.DiagnosisQuestionAnswerDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.self.diagnosis.utils.MedicineQuestionUtils;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisQuestionAnswer;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * 问题业务接口
 */
public interface QuestionService {

    /**
     * 提问
     * @param replyQuestionVo
     * @return
     */
    BasicQuestionVo ask(ReplyQuestionVo replyQuestionVo);

    /**
     * 回答
     * @param replyQuestionVo
     * @return
     */
    BasicQuestionVo answer(ReplyQuestionVo replyQuestionVo);

    default DiagnosisMainsympQuestion getNextQuestion(DiagnosisMainsympQuestion dmQuestion, ReplyQuestionVo questionVo, UserInfo userInfo, Integer isAdditional) {
        String nexQuestionId = null;
        ReplyAnswerVo answerVo = questionVo.getAnswers().get(0);
        //非伴随症状要判断答案是否存在
        if (dmQuestion.getQuestionType().intValue() != QuestionEnum.伴随症状.getValue().intValue()) {
            //程序控制跳转的问题
            nexQuestionId = MedicineQuestionUtils.getNextQuestionCodeByAnswerTitle(questionVo.getSympCode(), questionVo.getQuestionCode(), answerVo.getAnswerCode());
            if (StringUtils.isEmpty(nexQuestionId)) {
                DiagnosisQuestionAnswerDao diagnosisQuestionAnswerDao = SpringContextHolder.getBean("diagnosisQuestionAnswerDaoImpl");
                DiagnosisQuestionAnswer dqAnswer = diagnosisQuestionAnswerDao.getDiagnosisQuestionAnswer(dmQuestion.getQuestionCode(), answerVo.getAnswerCode());
                //目前有的问题会没有答案
                if (dqAnswer == null) {
                    String mainSympCode = dmQuestion.getMainSympCode();
                    int defaultOrder = dmQuestion.getDefaultOrder();
                    DiagnosisMainsympQuestion question = getNextDiagnosisMainsympQuestion(mainSympCode, defaultOrder, userInfo,isAdditional);
                    return question;
                    //throw new ServiceException(ResponseStatus.INVALID_VALUE, "没有找到对应的答案");
                } else {
                    nexQuestionId = dqAnswer.getNextQuestionId();
                }
            }
        }
        String mainSympCode = dmQuestion.getMainSympCode();
        int defaultOrder = dmQuestion.getDefaultOrder();
        // 获取下一个问题 1 答案是否存在下一个问题，2没有就查询下一个问题
        DiagnosisMainsympQuestion question = null;
        if (nexQuestionId == null || nexQuestionId.equals("-1")) {
            question = getNextDiagnosisMainsympQuestion(mainSympCode, defaultOrder, userInfo,isAdditional);
        } else {
            DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao = SpringContextHolder.getBean("diagnosisMainsympQuestionDaoImpl");
            question = diagnosisMainsympQuestionDao.getDiagnosisMainsympQuestion(nexQuestionId, questionVo.getSympCode());
            //判断当前问题是否为附加问题
            if(isAdditional == 1 && question != null && (question.getIsAdditional() == null || question.getIsAdditional() != 1)) {
                return null;
            }
        }
        return question;
    }

    default DiagnosisMainsympQuestion getNextDiagnosisMainsympQuestion(String mainSympCode, int defaultOrder, UserInfo userInfo, Integer isAdditional) {
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

    default QuestionService getQuestionService(Integer questionType) {
        QuestionService questionService = null;
        if (QuestionEnum.主症状.getValue().intValue() == questionType.intValue()) {
            questionService = SpringContextHolder.getBean("diagnosisMainSymptomServiceImpl");
        } else if (QuestionEnum.医学问题.getValue().intValue() == questionType.intValue()) {
            questionService = SpringContextHolder.getBean("diagnosisQuestionServiceImpl");
        } else if (QuestionEnum.伴随症状.getValue().intValue() == questionType.intValue()  || QuestionEnum.常见伴随症状.getValue().intValue() == questionType.intValue()) {
            questionService = SpringContextHolder.getBean("diagnosisConcsympQuestionServiceImpl");
        }
        return questionService;
    }
}
