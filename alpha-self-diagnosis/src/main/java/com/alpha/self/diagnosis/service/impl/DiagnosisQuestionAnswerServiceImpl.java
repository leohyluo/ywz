package com.alpha.self.diagnosis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisQuestionAnswerDao;
import com.alpha.self.diagnosis.dao.SyDiagnosisAnswerDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.SyAnswerType;
import com.alpha.self.diagnosis.pojo.vo.BasicAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.Level1AnswerVo;
import com.alpha.self.diagnosis.pojo.vo.Level2AnswerVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.MedicineDiagnosisService;
import com.alpha.self.diagnosis.service.UserDiagnosisDetailService;
import com.alpha.self.diagnosis.utils.MedicineSortUtil;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisQuestionAnswer;
import com.alpha.server.rpc.diagnosis.pojo.SyDiagnosisAnswer;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

@Service
public class DiagnosisQuestionAnswerServiceImpl implements AnswerService {

    @Resource
    private DiagnosisQuestionAnswerDao diagnosisQuestionAnswerDao;
    @Resource
    private SyDiagnosisAnswerDao syDiagnosisAnswerDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private UserDiagnosisDetailService userDiagnosisDetailService;
    @Resource
    private MedicineDiagnosisService medicineDiagnosisService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public LinkedHashSet<DiagnosisQuestionAnswer> get(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo) {
        //查询答案
        ArrayList<String> questionCodes = new ArrayList<>();
        questionCodes.add(questionCode);
        List<DiagnosisQuestionAnswer> dqAnswers = diagnosisQuestionAnswerDao.listDiagnosisQuestionAnswer(mainSympCode, questionCodes);
        //过滤不必要的答案(包括隐藏答案、与性别年龄不符的答案)
        dqAnswers = answerFilter(questionCode, dqAnswers, userInfo);
        //答案去重、排序
        LinkedHashSet<DiagnosisQuestionAnswer> answerLinkedHashSet = new LinkedHashSet<>();
        List<UserDiagnosisOutcome> userDiagnosisOutcomes = medicineDiagnosisService.diagnosisOutcome(diagnosisId, mainSympCode,userInfo);//计算疾病的权重
        LinkedHashSet<DiagnosisQuestionAnswer> answers = MedicineSortUtil.sortAndDistinctAnswer(dqAnswers, userDiagnosisOutcomes);
        answerLinkedHashSet.addAll(answers);
        return answerLinkedHashSet;
    }

    @Override
    public LinkedHashSet<IAnswerVo> getAnswerView(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo) {
        LinkedHashSet<DiagnosisQuestionAnswer> answerLinkedHashSet = this.get(diagnosisId, mainSympCode, questionCode, userInfo);
        List<DiagnosisQuestionAnswer> dqAnswers = answerLinkedHashSet.stream().collect(toList());
        //“不清楚”
        Optional<DiagnosisQuestionAnswer> unknownOptional = dqAnswers.stream().filter(e->e.getContent().equals(GlobalConstants.UNKNOWN_ANSWER)).findAny();
        //"以上都没有"
        Optional<DiagnosisQuestionAnswer> noneOptional = dqAnswers.stream().filter(e->e.getContent().equals(GlobalConstants.NONE)).findAny();
        dqAnswers = dqAnswers.stream().filter(e->!e.getContent().equals(GlobalConstants.UNKNOWN_ANSWER)).filter(e->!e.getContent().equals(GlobalConstants.NONE)).collect(toList());
        //查询答案所属的答案大类编码
        List<String> syanswercodeList = dqAnswers.stream().filter(e->StringUtils.isNotEmpty(e.getSyAnswerCode()))
                .map(DiagnosisQuestionAnswer::getSyAnswerCode).distinct().collect(toList());
        //建立答案大小类关系
        if(CollectionUtils.isNotEmpty(syanswercodeList)) {
            //根据大类编码查询答案大类
            List<SyDiagnosisAnswer> syDiagnosisAnswerList = syDiagnosisAnswerDao.listSyDiagnosisAnswer(syanswercodeList, SyAnswerType.PARENT_ANSWER.getValue());
            Map<String, SyDiagnosisAnswer> syAnswerMap = syDiagnosisAnswerList.stream().collect(toMap(SyDiagnosisAnswer::getAnswerCode, Function.identity()));
            //建立小类与大类的关联关系
            dqAnswers = dqAnswers.stream().peek(e->{
                if(StringUtils.isNotEmpty(e.getSyAnswerCode())) {
                    SyDiagnosisAnswer syAnswer = syAnswerMap.get(e.getSyAnswerCode());
                    e.setSyAnswer(syAnswer);
                }
            }).collect(toList());
        }
        //转换为页面需要显示的数据结构
        List<IAnswerVo> answerVoList = dqAnswers.stream().map(BasicAnswerVo::new).collect(toList());
        List<IAnswerVo> level1AnswerList = new ArrayList<>();
        for(IAnswerVo itemAnswer : answerVoList) {
            BasicAnswerVo bav = (BasicAnswerVo) itemAnswer;
            SyDiagnosisAnswer syAnswer = bav.getSyAnswer();
            if(syAnswer != null) {
                String syAnswerCode = syAnswer.getAnswerCode();
                Optional<Level1AnswerVo> lv1optional = level1AnswerList.stream().map(e->{
                    Level1AnswerVo lv1 = (Level1AnswerVo) e;
                    return lv1;
                }).filter(e->e.getAnswerValue().equals(syAnswerCode)).findFirst();

                if(lv1optional.isPresent()) {
                    Level1AnswerVo level1Answer = lv1optional.get();
                    List<Level2AnswerVo> level2AnswerList = level1Answer.getLevel2Answers();
                    if(level2AnswerList == null) {
                        level2AnswerList = new ArrayList<>();
                        level1Answer.setLevel2Answers(level2AnswerList);
                    }
                    //将BasicAnswerVo转为Level2AnswerVo
                    Level2AnswerVo level2Answer = new Level2AnswerVo(bav);
                    level2AnswerList.add(level2Answer);
                } else {
                    Level1AnswerVo level1Answer = new Level1AnswerVo(syAnswer);
                    Level2AnswerVo level2Answer = new Level2AnswerVo(bav);
                    List<Level2AnswerVo> level2AnswerList = level1Answer.getLevel2Answers();
                    if(level2AnswerList == null) {
                        level2AnswerList = new ArrayList<>();
                        level2AnswerList.add(level2Answer);
                        level1Answer.setLevel2Answers(level2AnswerList);
                    } else {
                        level2AnswerList.add(level2Answer);
                    }

                    level1AnswerList.add(level1Answer);
                }
            } else {
                IAnswerVo level1Answer = new Level1AnswerVo(bav);
                level1AnswerList.add(level1Answer);
            }
        }
        level1AnswerList = level1AnswerList.size() > 10 ? level1AnswerList.subList(0, 10) : level1AnswerList;
        LinkedHashSet<IAnswerVo> lhs = new LinkedHashSet<>(level1AnswerList);
        if (unknownOptional.isPresent()) {
            BasicAnswerVo unknown = new BasicAnswerVo(unknownOptional.get());
            lhs.add(unknown);
        }
        if(noneOptional.isPresent()) {
            BasicAnswerVo none = new BasicAnswerVo(noneOptional.get());
            lhs.add(none);
        }
        return lhs;
    }

    /**
     * 过滤答案（隐藏答案、患者年龄、性别过滤）
     * @param dqAnswers
     */
    private List<DiagnosisQuestionAnswer> answerFilter(String questionCode, List<DiagnosisQuestionAnswer> dqAnswers, UserInfo userInfo) {
        //过滤隐藏答案
        List<DiagnosisQuestionAnswer> hiddenAnswerList = diagnosisQuestionAnswerDao.listHiddenAnswers(questionCode);
        Set<String> hiddenAnswerCodeSet = hiddenAnswerList.stream().map(DiagnosisQuestionAnswer::getAnswerCode).collect(toSet());
        List<DiagnosisQuestionAnswer> answerList = dqAnswers.stream().filter(e->!hiddenAnswerCodeSet.contains(e.getAnswerCode())).collect(toList());

        //根据患者性别过滤
        Predicate<DiagnosisQuestionAnswer> genderPredicate = e -> {
            return e.getGender() == null || e.getGender() < 1 || e.getGender().intValue() == userInfo.getGender().intValue();
        };
        answerList = answerList.stream().filter(genderPredicate).collect(toList());

        //根据患者年龄过滤
        final float age = DateUtils.getAge(userInfo.getBirth());
        Predicate<DiagnosisQuestionAnswer> agePredicate = e -> {
            if(e.getMinAge() != null && age < e.getMinAge()) {
                return false;
            }
            if(e.getMaxAge() != null && age > e.getMaxAge()) {
                return false;
            }
            return true;
        };
        answerList = answerList.stream().filter(agePredicate).collect(toList());
        return answerList;
    }
}
