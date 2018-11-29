package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;

import java.util.Map;

/**
 * 基础问题业务接口
 */
public interface BaseQuestionService {

    /**
     * 过滤不需要提问的基础问题
     * @param systemType
     * @param userId
     * @param diagnosisId
     * @return
     */
    Map<QuestionEnum, String> filterBasicQuestion(String systemType, Long userId, Long diagnosisId);
}
