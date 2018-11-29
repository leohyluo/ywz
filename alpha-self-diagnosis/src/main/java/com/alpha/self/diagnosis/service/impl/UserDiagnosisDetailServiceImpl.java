package com.alpha.self.diagnosis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.enums.Unit;
import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympConcsympDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.dao.DiagnosisQuestionAnswerDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.SymptomAccompanyService;
import com.alpha.self.diagnosis.service.UserDiagnosisDetailService;
import com.alpha.self.diagnosis.utils.ServiceUtil;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisQuestionAnswer;
import com.alpha.server.rpc.diagnosis.pojo.SyCommonConcSymptom;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class UserDiagnosisDetailServiceImpl implements UserDiagnosisDetailService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource(name = "diagnosisQuestionAnswerServiceImpl")
    private AnswerService diagnosisQuestionAnswerService;
    @Resource
    private DiagnosisMainsympConcsympDao diagnosisMainsympConcsympDao;
    @Resource
    private DiagnosisQuestionAnswerDao diagnosisQuestionAnswerDao;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource
    private SymptomAccompanyService symptomAccompanyService;

    @Override
    public void addUserDiagnosisDetail(BasicQuestionVo questionVo, UserInfo userInfo) {
        Long diagnosisId = questionVo.getDiagnosisId();
        String questionCode = questionVo.getQuestionCode();
        if(questionVo.getQuestionType() != QuestionEnum.附加医学问题.getValue()) {
            //查询同一就诊过程中主诉是否有变化
            String sympCode = questionVo.getSympCode();
            List<UserDiagnosisDetail> otherMainSympDetailList = userDiagnosisDetailDao.listUserDiagnosisDetail(diagnosisId, sympCode);
            if(CollectionUtils.isNotEmpty(otherMainSympDetailList)) {
                //删除其它主诉的问诊过程
                userDiagnosisDetailDao.deleteUserDiagnosisDetail(diagnosisId, sympCode);
            }
        }

        UserDiagnosisDetail udd = userDiagnosisDetailDao.getUserDiagnosisDetail(diagnosisId, questionCode);
        if(udd != null) {
            return;
        }

        List<String> answerCodes = new ArrayList<>();
        List<String> answerContents = new ArrayList<>();

        for (IAnswerVo answerVo : questionVo.getAnswers()) {
            if (answerVo instanceof BasicAnswerVo) {
                BasicAnswerVo answer = (BasicAnswerVo) answerVo;
                answerCodes.add(answer.getAnswerValue());
                answerContents.add(answer.getAnswerTitle());
            } else if (answerVo instanceof Level1AnswerVo) {
                Level1AnswerVo answer = (Level1AnswerVo) answerVo;
                answerCodes.add(answer.getAnswerValue());
                answerContents.add(answer.getAnswerTitle());
            }
        }
        udd = new UserDiagnosisDetail();
        udd.setDiagnosisId(questionVo.getDiagnosisId());
        udd.setUserId(userInfo.getUserId());
        udd.setMemberId(userInfo.getUserId());
        udd.setQuestionCode(questionVo.getQuestionCode());
        udd.setAnswerCode(JSON.toJSONString(answerCodes));
        udd.setAnswerContent(JSON.toJSONString(answerContents));
        udd.setAnswerPopuContent(JSON.toJSONString(answerContents));
        udd.setForwardDiseaseCode(JSON.toJSONString(new HashSet<>()));
        udd.setReverseDiseaseCode(JSON.toJSONString(new HashSet<>()));
        udd.setNothingDiseaseCode(JSON.toJSONString(new HashSet<>()));
        udd.setQuestionType(questionVo.getQuestionType());
        udd.setSympCode(questionVo.getSympCode());
        //udd.setQuestionContent(questionVo.getQuestionTitle());
        String popuTitle = StringUtils.isNotEmpty(questionVo.getPopuTitle()) ? questionVo.getPopuTitle() : questionVo.getQuestionTitle();
        udd.setQuestioTitle(questionVo.getQuestionTitle());
        udd.setQuestionContent(popuTitle);
        udd.setCreateTime(new Date());
        udd.setUpdateTime(new Date());
        if(questionVo.getQuestionType() == QuestionEnum.附加医学问题.getValue()) {
            udd.setIsAdditional(1);
        }
        userDiagnosisDetailDao.insert(udd);
    }

    @Override
    public void updateUserDiagnosisDetail(ReplyQuestionVo questionVo, UserInfo userInfo, DiagnosisMainsympQuestion diagnosisQuestion) {
        if (questionVo.getAnswers() == null || questionVo.getAnswers().size() == 0) {
            logger.error("没有找到对应的答案");
            return;
        }
        List<String> answerCodes = new ArrayList<>();
        List<String> answerContents = new ArrayList<>();
        List<String> popuAnswerContents = new ArrayList<>();
        Long diagnosisId = questionVo.getDiagnosisId();
        String questionCode = questionVo.getQuestionCode();
        String mainSympCode = questionVo.getSympCode();
        Map<Integer, Set<String>> answerSpecMap = new HashMap<>();

        //判断当前问题是否有答案转化标志
        if(diagnosisQuestion != null && StringUtils.isNotEmpty(diagnosisQuestion.getParseClass())) {
            logger.info("将页面自由选择的答案转化为知识库的标准答案");
            final String parseClass = diagnosisQuestion.getParseClass();
            LinkedHashSet<DiagnosisQuestionAnswer> diagnosisQuestionAnswerLinkedHashSet = diagnosisQuestionAnswerService.get(diagnosisId, mainSympCode, questionCode, userInfo);
            List<DiagnosisQuestionAnswer> diagnosisAnswerList = diagnosisQuestionAnswerLinkedHashSet.stream().collect(Collectors.toList());

            List<ReplyAnswerVo> answervoList = questionVo.getAnswers();
            //遍历客户端回答的答案
            for(ReplyAnswerVo answerVo : answervoList) {
                String inputAnswer = answerVo.getAnswerTitle();
                //如是温度,则将页面传进来的37.5转为37.5℃
                if(parseClass.equals(Unit.TEMPERATURE.getValue())) {
                    if(inputAnswer.contains(Unit.TEMPERATURE.getText()) == false && !inputAnswer.equals(GlobalConstants.TEMPERATURE_UNKNOWN))
                        inputAnswer = inputAnswer.concat(Unit.TEMPERATURE.getText());
                }
                String inputAnswerWithUnit = inputAnswer;
                //页面自由输入的答案与数据库的某一个答案匹配
                Optional<DiagnosisQuestionAnswer> answerOptional = diagnosisAnswerList.stream().filter(e->matchWithUnit(inputAnswerWithUnit, parseClass, e)).findFirst();
                if(answerOptional.isPresent()) {
                    answerCodes.add(answerOptional.get().getAnswerCode());
                    answerContents.add(inputAnswerWithUnit);
                    popuAnswerContents.add(inputAnswerWithUnit);
                    //答案转化成功后模拟页面构造参数
                    answerVo.setAnswerCode(answerOptional.get().getAnswerCode());
                    answerVo.setAnswerTitle(inputAnswerWithUnit);
                } else {
                    logger.error("页面的答案没能与知识库的答案匹配");
                    answerCodes.add("-1");
                    answerContents.add(inputAnswerWithUnit);
                    popuAnswerContents.add(inputAnswerWithUnit);
                }
            }
        } else {
            for (ReplyAnswerVo answerVo : questionVo.getAnswers()) {
                String answerText = answerVo.getAnswerTitle();
                String answerPopuConetnt = StringUtils.isNotEmpty(answerVo.getAnswerPopuTitle()) ? answerVo.getAnswerPopuTitle() : answerText;
                answerCodes.add(answerVo.getAnswerCode());
                answerContents.add(answerText);
                popuAnswerContents.add(answerPopuConetnt);
            }
        }
        //常见伴随症状
        if(questionVo.getQuestionType()== QuestionEnum.常见伴随症状.getValue()) {
            Set<String> commonConcSympNames = answerContents.stream().collect(Collectors.toSet());
            List<DiagnosisMainsympConcsymp> diagnosisMainsympConcsympList = diagnosisMainsympConcsympDao.listByConcSympNames(mainSympCode, commonConcSympNames);
            answerSpecMap = new HashMap<>();
            for (DiagnosisMainsympConcsymp dmc : diagnosisMainsympConcsympList) {
                Set<String> specSet = answerSpecMap.get(dmc.getSympSpec()) == null ? new HashSet<>() : answerSpecMap.get(dmc.getSympSpec());
                if(StringUtils.isNotEmpty(dmc.getDiseaseCode())) {
                    specSet.add(dmc.getDiseaseCode());
                    answerSpecMap.put(dmc.getSympSpec(), specSet);
                }
            }
        }
        //伴随症状
        if(questionVo.getQuestionType()== QuestionEnum.伴随症状.getValue()) {
            List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listDiagnosisMainsympQuestion(mainSympCode);
            Optional<DiagnosisMainsympQuestion> questionOptional = dmQuestions.stream().filter(e->e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).findFirst();
            String questionCodeOfCommonSymp = questionOptional.get().getQuestionCode();
            //已回答的常见伴随症状
            UserDiagnosisDetail userDiagnosisDetail = userDiagnosisDetailDao.getUserDiagnosisDetail(diagnosisId, questionCodeOfCommonSymp);
            String answerCodeStr = userDiagnosisDetail.getAnswerCode();
            JSONArray jarr = JSONArray.parseArray(answerCodeStr);
            Set<String> normalSympCodeSet = new HashSet<>();
            for (int i = 0; i < jarr.size(); i++) {
                String itemSympCode = jarr.getString(i);
                normalSympCodeSet.add(itemSympCode);
            }
            if (CollectionUtils.isNotEmpty(normalSympCodeSet)) {
                //常见伴随症状对应的同义词
                List<String> normalSympCodeList = normalSympCodeSet.stream().collect(toList());
                List<SyCommonConcSymptom> syCommonConcSymptomList = symptomAccompanyService.getSynonymOfCommonConcSymp(normalSympCodeList);
                for (SyCommonConcSymptom item : syCommonConcSymptomList) {
                    answerCodes.add(item.getSympCode());
                    answerContents.add(item.getSympName());
                    popuAnswerContents.add(item.getPopuName());
                }
            }

            List<DiagnosisMainsympConcsymp> diagnosisMainsympConcsympList = diagnosisMainsympConcsympDao.listByMainSymptomAndconcSympCodes(mainSympCode, answerCodes);
            answerSpecMap = new HashMap<>();
            for (DiagnosisMainsympConcsymp dmc : diagnosisMainsympConcsympList) {
                Set<String> specSet = answerSpecMap.get(dmc.getSympSpec()) == null ? new HashSet<>() : answerSpecMap.get(dmc.getSympSpec());
                if(StringUtils.isNotEmpty(dmc.getDiseaseCode())) {
                    specSet.add(dmc.getDiseaseCode());
                    answerSpecMap.put(dmc.getSympSpec(), specSet);
                }
            }
        }
        //查询医学问题下的隐藏答案
        if(questionVo.getQuestionType() == QuestionEnum.医学问题.getValue()) {
            List<String> hiddenAnswerCodes = new ArrayList<>();
            List<DiagnosisQuestionAnswer> hiddenAnswerList = diagnosisQuestionAnswerDao.listHiddenAnswers(questionCode);

            if(CollectionUtils.isNotEmpty(hiddenAnswerList)) {
                logger.info("开始处理问题"+questionCode+"的隐藏答案");
                //找出未回答的隐藏答案编码
                hiddenAnswerCodes = hiddenAnswerList.stream().filter(e -> !answerCodes.contains(e.getMutuallyAnswerCode()))
                        .map(DiagnosisQuestionAnswer::getAnswerCode).distinct().collect(Collectors.toList());
                //将未回答的隐藏答案组装到用户的已回答答案中
                Set<String> hiddenAnswerCodeSet = hiddenAnswerCodes.stream().collect(Collectors.toSet());
                hiddenAnswerList = hiddenAnswerList.stream().filter(e->hiddenAnswerCodeSet.contains(e.getAnswerCode())).collect(Collectors.toList());
                List<ReplyAnswerVo> answerRequestVoList = questionVo.getAnswers();
                Set<String> replayAnsewrCodeSet = new HashSet<>();
                for (DiagnosisQuestionAnswer hiddenAnswer : hiddenAnswerList) {
                    if(!replayAnsewrCodeSet.contains(hiddenAnswer.getAnswerCode())) {
                        replayAnsewrCodeSet.add(hiddenAnswer.getAnswerCode());
                        //AnswerRequestVo hiddenAnswerVo = new AnswerRequestVo();
                        ReplyAnswerVo hiddenAnswerVo = new ReplyAnswerVo();
                        hiddenAnswerVo.setAnswerTitle(hiddenAnswer.getAnswerTitle());
                        hiddenAnswerVo.setAnswerCode(hiddenAnswer.getAnswerCode());
                        answerRequestVoList.add(hiddenAnswerVo);
                    }
                }
                logger.info("问题"+questionCode+"的隐藏答案为"+hiddenAnswerCodes);
            }
            //v1.1.3修改时注释
            //List<DiagnosisQuestionAnswer> dqAnswers = diagnosisQuestionAnswerDao.listDiagnosisQuestionAnswer(mainSympCode, questionVo.getQuestionCode(), answerCodes, hiddenAnswerCodes);

            List<DiagnosisQuestionAnswer> dqAnswers = diagnosisQuestionAnswerDao.listByAnswerCodes(mainSympCode, questionVo.getQuestionCode(), answerCodes, hiddenAnswerCodes);

            answerSpecMap = new HashMap<>();
            for (DiagnosisQuestionAnswer dqa : dqAnswers) {
                Set<String> specSet = answerSpecMap.get(dqa.getAnswerSpec()) == null ? new HashSet<>() : answerSpecMap.get(dqa.getAnswerSpec());
                if(StringUtils.isNotEmpty(dqa.getDiseaseCode())) {
                    specSet.add(dqa.getDiseaseCode());
                    answerSpecMap.put(dqa.getAnswerSpec(), specSet);
                }
            }
        }

        UserDiagnosisDetail udd = userDiagnosisDetailDao.getUserDiagnosisDetail(questionVo.getDiagnosisId(), questionVo.getQuestionCode());

        if (udd == null) {
            throw new ServiceException(ResponseStatus.INVALID_VALUE, "没有找到提问记录");
        }

        Set<String> forwardDiseaseCode = answerSpecMap.get(1) == null ? new HashSet<>() : answerSpecMap.get(1);   //正向特异性
        Set<String> reverseDiseaseCode = answerSpecMap.get(-1) == null ? new HashSet<>() : answerSpecMap.get(-1);   //反向特异性
        Set<String> nothingDiseaseCode = answerSpecMap.get(0) == null ? new HashSet<>() : answerSpecMap.get(0); //无特异性
//        udd.setAnswerCode(ServiceUtil.arrayConvertToString(answerCodes));
        udd.setAnswerCode(JSON.toJSONString(answerCodes));
        udd.setAnswerContent(ServiceUtil.arrayConvertToString(answerContents));
        udd.setAnswerPopuContent(ServiceUtil.arrayConvertToString(popuAnswerContents));
        udd.setAnswerJson(JSON.toJSONString(questionVo.getAnswers()));
        udd.setForwardDiseaseCode(JSON.toJSONString(forwardDiseaseCode));
        udd.setReverseDiseaseCode(JSON.toJSONString(reverseDiseaseCode));
        udd.setNothingDiseaseCode(JSON.toJSONString(nothingDiseaseCode));
        udd.setQuestionType(questionVo.getQuestionType());
        udd.setAnswerTime(new Date());
        udd.setSympCode(questionVo.getSympCode());
        udd.setUpdateTime(new Date());
        userDiagnosisDetailDao.update(udd);

        /**
         * 删除当前问题之后的引申问题
         */
        if (questionVo.getQuestionType().intValue() == QuestionEnum.附加医学问题.getValue()) {
            userDiagnosisDetailDao.deleteAdditonalUserDiagnosisDetailById(questionVo.getDiagnosisId(), udd.getId());
        }
    }

    private static boolean matchWithUnit(String inputAnswer, String parseClass, DiagnosisQuestionAnswer answer) {
        Unit inputUnit = Unit.containText(inputAnswer);
        if(inputUnit == null) {
            return false;
        }
        Double minValue = answer.getMinValue();
        Double maxValue = answer.getMaxValue();
        Double inputAnswerWithoutUnit = Double.valueOf(inputAnswer.replace(inputUnit.getText(), ""));
        if(inputUnit == Unit.MINUTE || inputUnit == Unit.HOUR || inputUnit == Unit.DAY || inputUnit == Unit.WEEK
                || inputUnit == Unit.MONTH || inputUnit == Unit.SEASON || inputUnit == Unit.YEAR) {

            Unit unit = Unit.findByValue(parseClass);

            //Double answerOfInput = DateUtils.toMillSeond(inputAnswerWithoutUnit, inputUnit);
            Double answerOfInput = DateUtils.toMillSeond(inputAnswer);
            Double answerOfMin = DateUtils.toMillSeond(minValue, unit);
            Double answerOfMax = DateUtils.toMillSeond(maxValue, unit);
            if(answerOfInput.doubleValue() >= answerOfMin.doubleValue()
                    && answerOfInput.doubleValue() <= answerOfMax.doubleValue()) {
                return true;
            }
        } else if (inputUnit == Unit.CENTIGRADE) {
            if(inputAnswerWithoutUnit >= minValue && inputAnswerWithoutUnit <= maxValue) {
                return true;
            }

        } else if (inputUnit == Unit.TEMPERATURE) {
            if(inputAnswerWithoutUnit >= minValue && inputAnswerWithoutUnit <= maxValue) {
                return true;
            }

        } else if (inputUnit == Unit.NUM_OF_TIMES) {
            if(inputAnswerWithoutUnit >= minValue && inputAnswerWithoutUnit <= maxValue) {
                return true;
            }
        }
        return false;
    }
}
