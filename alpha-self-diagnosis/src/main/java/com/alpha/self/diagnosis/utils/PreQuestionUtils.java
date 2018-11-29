package com.alpha.self.diagnosis.utils;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.mapper.PreQuestionAnswerMapper;
import com.alpha.self.diagnosis.mapper.PreQuestionMapper;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;

import com.alpha.user.dao.UserInfoDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PreQuestionUtils {

    private PreQuestionUtils() {}

    private static List<PreQuestion> preQuestionList;
    private static List<PreQuestionAnswer> preQuestionAnswerList;
    private UserInfoDao userInfoDao;

    static {
        preQuestionList = new ArrayList<>();
        PreQuestionMapper preQuestionMapper = SpringContextHolder.getBean("preQuestionMapper");
        preQuestionList = preQuestionMapper.selectAll();

        preQuestionAnswerList = new ArrayList<>();
        PreQuestionAnswerMapper preQuestionAnswerMapper = SpringContextHolder.getBean("preQuestionAnswerMapper");
        preQuestionAnswerList = preQuestionAnswerMapper.selectAll();
    }

    public static List<PreQuestion> findAll() {
        return preQuestionList;
    }

    public static List<PreQuestion> getParentQuestion(AppType appType) {
        List<PreQuestion> questions = findAll();
        questions = questions.stream().filter(e -> StringUtils.isEmpty(e.getAppType()) || appType.getValue().equals(e.getAppType()))
                .filter(e-> StringUtils.isEmpty(e.getParentQuestionCode()))
                .filter(e->e.getIsShow() == 1).sorted(Comparator.comparing(PreQuestion::getDefaultOrder)).collect(Collectors.toList());
        return questions;
    }

    public static PreQuestion getByQuestionType(QuestionEnum questionEnum) {
        Optional<PreQuestion> preQuestionOptional = preQuestionList.stream().filter(e->e.getQuestionType().intValue() == questionEnum.getValue().intValue()).findFirst();
        return preQuestionOptional.isPresent() ? preQuestionOptional.get() : null;
    }

    public static List<PreQuestion> getSubQuestion(PreQuestion preQuestion) {
        return preQuestionList.stream().filter(e->StringUtils.isNotEmpty(e.getParentQuestionCode())).filter(e->e.getParentQuestionCode().equals(preQuestion.getQuestionCode())).collect(Collectors.toList());
    }

    public static List<PreQuestionAnswer> getByAnswerCode(String questionCode, String answerCode) {
        List<PreQuestionAnswer> answerList = preQuestionAnswerList.stream().filter(e->e.getQuestionCode().equals(questionCode)).filter(e->e.getAnswerCode().equals(answerCode)).collect(Collectors.toList());
        return answerList;
    }

    public static List<PreQuestionAnswer> getByQuestionCode(String questionCode) {
        List<PreQuestionAnswer> answerList = preQuestionAnswerList.stream().filter(e->e.getQuestionCode().equals(questionCode)).collect(Collectors.toList());
        return answerList;
    }

}
