package com.alpha.self.diagnosis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alpha.commons.enums.System;
import com.alpha.commons.enums.Unit;
import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.ReplyAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.self.diagnosis.service.DiagnosisHistoryService;
import com.alpha.self.diagnosis.service.QuestionService;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.service.UserInfoService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class DiagnosisHistoryServiceImpl implements DiagnosisHistoryService {

    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;

    @Resource
    private UserInfoService userInfoService;

    @Resource(name = "diagnosisQuestionServiceImpl")
    private QuestionService diagnosisQuestionService;

    @Override
    public List<UserDiagnosisDetail> showUserDiagnosisDetail(Long diagnosisId) {
        List<UserDiagnosisDetail> userDiagnosisDetailList = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
        //过滤不需显示的问题（年龄、季节）
        userDiagnosisDetailList = userDiagnosisDetailList.stream().filter(this::userDiagnosisDetailFilter).collect(Collectors.toList());
        userDiagnosisDetailList.stream().peek(e->{
            String popuContents = e.getAnswerPopuContent();
            if(StringUtils.isNotEmpty(popuContents) && popuContents.startsWith("[")) {
                StringJoiner sj = new StringJoiner(",");
                JSONArray jarr = JSONArray.parseArray(popuContents);
                for(int i = 0; i < jarr.size(); i++) {
                    sj.add(jarr.get(i).toString());
                }
                e.setAnswerPopuContent(sj.toString());
            }
        }).peek(this::calcIllDay).collect(Collectors.toList());
        return userDiagnosisDetailList;
    }

    private Boolean userDiagnosisDetailFilter(UserDiagnosisDetail userDiagnosisDetail) {
        if(userDiagnosisDetail.getQuestionType() == null) {
            return false;
        }
        if(userDiagnosisDetail.getQuestionType() == QuestionEnum.年龄问题.getValue() || userDiagnosisDetail.getQuestionType() == QuestionEnum.季节问题.getValue()) {
            return false;
        }
        return true;
    }

    /**
     * 病情确诊时自动更新病程（只针对小时和天）
     * @param userDiagnosisDetail
     */
    private void calcIllDay(UserDiagnosisDetail userDiagnosisDetail) {
        try {
            if(userDiagnosisDetail.getQuestionType() == null) {
                return;
            }
            if(userDiagnosisDetail.getQuestionType() == QuestionEnum.医学问题.getValue() ||  userDiagnosisDetail.getQuestionType() == QuestionEnum.附加医学问题.getValue()){
                String answerContent = userDiagnosisDetail.getAnswerContent();
                //如果病程是天或小时则自动计算并更新
                long diffNum = 0;
                String newAnswer = "";
                if(answerContent.endsWith(Unit.HOUR.getText())) {
                    String answerWithoutUnit = answerContent.replace(Unit.HOUR.getText(), "");
                    Date updateTime = userDiagnosisDetail.getUpdateTime();
                    LocalDateTime updateDateTime = DateUtils.dateToLocalDateTime(updateTime);
                    LocalDateTime now = LocalDateTime.now();
                    diffNum = ChronoUnit.HOURS.between(updateDateTime, now);
                    newAnswer = (Long.valueOf(answerWithoutUnit) + diffNum) + Unit.HOUR.getText();
                } else if (answerContent.endsWith(Unit.DAY.getText())) {
                    String answerWithoutUnit = answerContent.replace(Unit.DAY.getText(), "");
                    Date updateTime = userDiagnosisDetail.getUpdateTime();
                    LocalDateTime updateDateTime = DateUtils.dateToLocalDateTime(updateTime);
                    LocalDateTime now = LocalDateTime.now();
                    diffNum = ChronoUnit.DAYS.between(updateDateTime, now);
                    newAnswer = (Long.valueOf(answerWithoutUnit) + diffNum) + Unit.DAY.getText();
                }
                if (diffNum > 0) {
                    UserInfo userInfo = userInfoService.queryByUserId(userDiagnosisDetail.getUserId());
                    if(userInfo == null) return;

                    Long diagnosisId = userDiagnosisDetail.getDiagnosisId();

                    ReplyAnswerVo answerRequestVo = new ReplyAnswerVo();
                    answerRequestVo.setAnswerTitle(newAnswer);
                    answerRequestVo.setAnswerCode(newAnswer);
                    List<ReplyAnswerVo> answerRequestVoList = Lists.newArrayList();
                    answerRequestVoList.add(answerRequestVo);

                    //QuestionRequestVo questionRequestVo = new QuestionRequestVo();
                    ReplyQuestionVo questionRequestVo = new ReplyQuestionVo();
                    questionRequestVo.setDiagnosisId(diagnosisId);
                    questionRequestVo.setQuestionCode(userDiagnosisDetail.getQuestionCode());
                    questionRequestVo.setQuestionType(userDiagnosisDetail.getQuestionType());
                    questionRequestVo.setSympCode(userDiagnosisDetail.getSympCode());
                    questionRequestVo.setSystemType(System.PRE.getValue());
                    questionRequestVo.setAnswers(answerRequestVoList);
                    diagnosisQuestionService.answer(questionRequestVo);

                    userDiagnosisDetail.setAnswerContent(newAnswer);
                    userDiagnosisDetail.setAnswerPopuContent(newAnswer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
