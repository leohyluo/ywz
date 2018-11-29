package com.alpha.self.diagnosis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.BasicQuestionType;
import com.alpha.commons.enums.DiagnosisStatus;
import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.mapper.UserBasicRecordNewMapper;
import com.alpha.his.utils.AppUtils;
import com.alpha.self.diagnosis.dao.DiagnosisConcomitantSymptomDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.mapper.BasicQuestionMapper;
import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.processor.AbstractBasicAnswerProcessor;
import com.alpha.self.diagnosis.processor.BasicAnswerProcessorAdaptor;
import com.alpha.self.diagnosis.service.BaseQuestionService;
import com.alpha.self.diagnosis.service.DiagnosisDraftService;
import com.alpha.self.diagnosis.service.UserMedicalRecordService;
import com.alpha.self.diagnosis.utils.PreQuestionUtils;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserBasicRecordDao;
import com.alpha.user.dao.UserInfoDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 预问诊草稿业务接口
 */
@Service
public class DiagnosisDraftServiceImpl implements DiagnosisDraftService {

    @Resource
    private UserBasicRecordDao userBasicRecordDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private DiagnosisConcomitantSymptomDao diagnosisConcomitantSymptomDao;
    @Resource
    private UserBasicRecordNewMapper userBasicRecordMapper;
    @Resource
    private UserMedicalRecordService userMedicalRecordService;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource
    private BaseQuestionService baseQuestionService;
    @Resource
    private BasicQuestionMapper basicQuestionMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<BasicQuestionVo> getDraft(String pno, AppType appType) throws ServiceException {
        UserBasicRecord param = new UserBasicRecord();
        param.setHisRegisterNo(pno);
        List<UserBasicRecord> userBasicRecordList = userBasicRecordMapper.select(param);
        if(CollectionUtils.isEmpty(userBasicRecordList)) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        //如果已提交预问诊则问诊过程为空
        Optional<String> optional = userBasicRecordList.stream().map(UserBasicRecord::getStatus).filter(StringUtils::isNotEmpty).filter(DiagnosisStatus.PRE_DIAGNOSIS_FINISH.getValue()::equals).findAny();
        if(optional.isPresent()) {
            throw new ServiceException(ResponseStatus.DIAGNOSIS_COMPLETED);
        }
        //找出最近一条问诊记录
        userBasicRecordList = userBasicRecordList.stream().filter(e->e.getUpdateTime() != null).sorted(Comparator.comparing(UserBasicRecord::getUpdateTime, Comparator.reverseOrder())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(userBasicRecordList)) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        UserBasicRecord userBasicRecord = userBasicRecordList.get(0);
        Long diagnosisId = userBasicRecord.getDiagnosisId();
        Long userId = userBasicRecord.getUserId();
        logger.info("挂号码{}最近一次问诊为{}", pno, diagnosisId);
        //过滤掉不需要回答的基础问题
        UserInfo userInfo = userInfoDao.queryByUserId(userBasicRecord.getUserId());
        Map<QuestionEnum, String> basicQuestionMap = baseQuestionService.filterBasicQuestion(appType.getValue(), userId, diagnosisId);
        //依次获取问题与答案，如问题已回答则跳到下一个问题，否则终止
        List<BasicQuestionVo> basicQuestionVoList = Lists.newArrayList();
        //获取所有大类基础问题
        List<PreQuestion> preQuestionList = PreQuestionUtils.getParentQuestion(appType);
        for(PreQuestion preQuestion : preQuestionList) {
            QuestionEnum questionEnum = QuestionEnum.getQuestion(preQuestion.getQuestionType());
            if(questionEnum == null) {
                continue;
            }
            if(!basicQuestionMap.containsKey(questionEnum)) {
                continue;
            }
            if(basicQuestionMap.get(questionEnum).equals("0")) {
                continue;
            }
            if (questionEnum == QuestionEnum.引言 && appType != AppType.WOMAN) {
                continue;
            }

            Boolean repliedAll = false;
            if(questionEnum == QuestionEnum.介绍信息) {
                repliedAll = getRepliedOfProduce(appType.getValue(), userBasicRecord, basicQuestionVoList);
                if(repliedAll == false) {
                    break;
                }
            } else if (questionEnum == QuestionEnum.引言 && appType == AppType.WOMAN) {
                List<BasicQuestionVo> diagnosisQuestionVoList = Lists.newArrayList();
                //获取医学问诊过程
                repliedAll = getRepliedOfDiagnosis(diagnosisId, userInfo, diagnosisQuestionVoList);
                basicQuestionVoList.addAll(diagnosisQuestionVoList);
            } else if (questionEnum == QuestionEnum.完善信息) {
                repliedAll = getRepliedOfBasicInfo(appType.getValue(),userBasicRecord, basicQuestionVoList);
                if(repliedAll) {
                    List<BasicQuestionVo> diagnosisQuestionVoList = Lists.newArrayList();
                    //获取医学问诊过程
                    repliedAll = getRepliedOfDiagnosis(diagnosisId, userInfo, diagnosisQuestionVoList);
                    basicQuestionVoList.addAll(diagnosisQuestionVoList);
                }
            } else if (questionEnum == QuestionEnum.鼓励1) {
                //不需要拼答案
                BasicQuestionVo basicQuestionVo = buildBasicQuestionVo(appType.getValue(),userBasicRecord, preQuestion);
                basicQuestionVoList.add(basicQuestionVo);
                repliedAll = true;
            } else if (questionEnum == QuestionEnum.一般情况) {
                repliedAll = getRepliedOfGeneralSymp(appType.getValue(), userBasicRecord, basicQuestionVoList);
            } else if (questionEnum == QuestionEnum.鼓励2) {
                repliedAll = getRepliedOfPastMedicalHistory(appType.getValue(), userInfo, userBasicRecord, basicQuestionVoList);
            }
            if(repliedAll == false) {
                break;
            }
        }
        return basicQuestionVoList;
    }


    @Override
    public List<BasicQuestionVo> confirm(Long diagnosisId, AppType appType) throws ServiceException {
        UserBasicRecord userBasicRecord = userBasicRecordDao.findByDiagnosisId(diagnosisId);
        Long userId = userBasicRecord.getUserId();
        //过滤掉不需要回答的基础问题
        UserInfo userInfo = userInfoDao.queryByUserId(userBasicRecord.getUserId());
        Map<QuestionEnum, String> basicQuestionMap = baseQuestionService.filterBasicQuestion(appType.getValue(), userId, diagnosisId);
        //依次获取问题与答案，如问题已回答则跳到下一个问题，否则终止
        List<BasicQuestionVo> basicQuestionVoList = Lists.newArrayList();

        //获取所有大类基础问题
        List<PreQuestion> preQuestionList = PreQuestionUtils.getParentQuestion(appType);
        for(PreQuestion preQuestion : preQuestionList) {
            QuestionEnum questionEnum = QuestionEnum.getQuestion(preQuestion.getQuestionType());
            if(questionEnum == null) {
                continue;
            }
            if(!basicQuestionMap.containsKey(questionEnum)) {
                continue;
            }
            if(basicQuestionMap.get(questionEnum).equals("0")) {
                continue;
            }

            Boolean repliedAll = false;
            if(questionEnum == QuestionEnum.介绍信息 || questionEnum == QuestionEnum.引言) {
                repliedAll = true;
            } else if (questionEnum == QuestionEnum.引言 && appType == AppType.WOMAN) {
                List<BasicQuestionVo> diagnosisQuestionVoList = Lists.newArrayList();
                //获取医学问诊过程
                repliedAll = getRepliedOfDiagnosis(diagnosisId, userInfo, diagnosisQuestionVoList);
                basicQuestionVoList.addAll(diagnosisQuestionVoList);
            } else if (questionEnum == QuestionEnum.完善信息) {
                repliedAll = true;
                if(repliedAll) {
                    List<BasicQuestionVo> diagnosisQuestionVoList = Lists.newArrayList();
                    //获取医学问诊过程
                    repliedAll = getRepliedOfDiagnosis(diagnosisId, userInfo, diagnosisQuestionVoList);
                    basicQuestionVoList.addAll(diagnosisQuestionVoList);
                }
            } else if (questionEnum == QuestionEnum.鼓励1) {
                repliedAll = true;
            } else if (questionEnum == QuestionEnum.一般情况) {
                repliedAll = getRepliedOfGeneralSymp(appType.getValue(), userBasicRecord, basicQuestionVoList);
            } else if (questionEnum == QuestionEnum.鼓励2) {
                repliedAll = getRepliedOfPastMedicalHistory(appType.getValue(), userInfo, userBasicRecord, basicQuestionVoList);
            }
            if(repliedAll == false) {
                break;
            }
        }
        return basicQuestionVoList;
    }

    /**
     * 产品介绍、引言
     * @return
     */
    private Boolean getRepliedOfProduce(String appType, UserBasicRecord userBasicRecord, List<BasicQuestionVo> basicQuestionVoList) {
        PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.介绍信息);
        BasicQuestionVo basicQuestionVo = buildBasicQuestionVo(appType, userBasicRecord, preQuestion);
        basicQuestionVoList.add(basicQuestionVo);

        preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.引言);
        basicQuestionVo = buildBasicQuestionVo(appType, userBasicRecord, preQuestion);
        basicQuestionVoList.add(basicQuestionVo);
        return true;
    }

    /**
     * 获取完善个人信息的答案
     * @return
     */
    private Boolean getRepliedOfBasicInfo(String appType,UserBasicRecord userBasicRecord, List<BasicQuestionVo> basicQuestionVoList) {
        Boolean repliedAll = true;
        Boolean repliedAny = false;
        PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.完善信息);
        //要回答的问题列表
        Map<QuestionEnum, String> needReplyQuestionMap = baseQuestionService.filterBasicQuestion(AppType.CHILD.getValue(), userBasicRecord.getUserId(), userBasicRecord.getDiagnosisId());

        List<PreQuestion> subPreQuestionList = PreQuestionUtils.getSubQuestion(preQuestion);
        List<SubQuestionVo> subQuestionVoList = Lists.newArrayList();
        List<PreQuestionAnswer> subAnswerList;
        for(PreQuestion subQuestion : subPreQuestionList) {
            if(subQuestion.getQuestionType().intValue() == QuestionEnum.出生日期.getValue().intValue()) {
                if(userBasicRecord.getBirth() != null) {
                    repliedAny = true;
                    subAnswerList = Lists.newArrayList();
                    PreQuestionAnswer subAnswer = new PreQuestionAnswer();
                    String answerContent = DateUtils.date2String(userBasicRecord.getBirth(), DateUtils.DATE_FORMAT);
                    subAnswer.setAnswerContent(answerContent);

                    subAnswerList.add(subAnswer);
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, subAnswerList);
                    subQuestionVoList.add(subQuestionVo);
                } else {
                    repliedAll = false;
                }
            } else if (subQuestion.getQuestionType().intValue() == QuestionEnum.性别.getValue().intValue()) {
                subAnswerList = Lists.newArrayList();
                PreQuestionAnswer subAnswer = new PreQuestionAnswer();
                Integer gender = userBasicRecord.getGender();
                if(gender != null) {
                    repliedAny = true;
                    String answerContent = gender.toString();
                    subAnswer.setAnswerContent(answerContent);
                    subAnswerList.add(subAnswer);
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, subAnswerList);
                    subQuestionVoList.add(subQuestionVo);
                } else {
                    repliedAll = false;
                }
            } else if (subQuestion.getQuestionType().intValue() == QuestionEnum.体重.getValue().intValue()) {
                if (needReplyQuestionMap.get(QuestionEnum.体重) != null && !needReplyQuestionMap.get(QuestionEnum.体重).equals("0")) {
                    String answerContent = userBasicRecord.getWeight();
                    subAnswerList = Lists.newArrayList();

                    PreQuestionAnswer subAnswer = new PreQuestionAnswer();
                    subAnswer.setAnswerContent(answerContent);

                    subAnswerList.add(subAnswer);
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, subAnswerList);
                    subQuestionVoList.add(subQuestionVo);
                    repliedAny = true;
                }
            }
        }
        if(repliedAny) {
            BasicQuestionVo basicQuestionVo = buildBasicQuestionVo(appType, userBasicRecord, preQuestion);
            basicQuestionVo.setSubQuestion(subQuestionVoList);
            basicQuestionVoList.add(basicQuestionVo);
        }
        return repliedAll;
    }

    /**
     * 获取诊断过程的答案
     * @return
     */
    private Boolean getRepliedOfDiagnosis(Long diagnosisId, UserInfo userInfo, List<BasicQuestionVo> basicQuestionVoList) {
        List<UserDiagnosisDetail> userDiagnosisDetailList = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
        if(CollectionUtils.isEmpty(userDiagnosisDetailList)) {
            return false;
        }

        //顺序获取问诊过程
        List<UserDiagnosisDetail> userDiagnosisDetails = userDiagnosisDetailList.stream().filter(this::userDiagnosisFilter).sorted(Comparator.comparing(UserDiagnosisDetail::getId)).collect(Collectors.toList());
        for(UserDiagnosisDetail udd : userDiagnosisDetails) {
            String answerJson = udd.getAnswerJson();

            if(StringUtils.isEmpty(answerJson)) {
                return false;
            }
            BasicQuestionVo questionVo = new BasicQuestionVo();
            questionVo.setSympCode(udd.getSympCode());
            questionVo.setQuestionCode(udd.getQuestionCode());
            questionVo.setDiagnosisId(diagnosisId);
            questionVo.setQuestionTitle(udd.getQuestionContent());
            questionVo.setPopuTitle(udd.getQuestionContent());
            questionVo.setQuestionType(udd.getQuestionType());

            List<IAnswerVo> answerVoList = Lists.newArrayList();
            List<BasicAnswerVo> basicAnswerVoList = parse2BasicAnswerVo(udd);
            answerVoList = Optional.ofNullable(basicAnswerVoList).orElseGet(ArrayList::new).stream().collect(Collectors.toList());

            questionVo.setAnswers(answerVoList);
            basicQuestionVoList.add(questionVo);
        }
        return true;
    }

    /**
     * 获取一般症状的答案
     * @return
     */
    private Boolean getRepliedOfGeneralSymp(String appType, UserBasicRecord userBasicRecord, List<BasicQuestionVo> basicQuestionVoList) {
        Boolean repliedAll = true;

        Map<QuestionEnum, String> basicQuestionMap = baseQuestionService.filterBasicQuestion(AppType.CHILD.getValue(), userBasicRecord.getUserId(), userBasicRecord.getDiagnosisId());
        PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.一般情况);
        List<PreQuestion> subPreQuestionList = PreQuestionUtils.getSubQuestion(preQuestion);
        List<SubQuestionVo> subQuestionVoList = Lists.newArrayList();
        List<PreQuestionAnswer> subAnswerList;

        BasicQuestionVo basicQuestionVo = buildBasicQuestionVo(appType, userBasicRecord, preQuestion);
        for(PreQuestion subQuestion : subPreQuestionList) {
            QuestionEnum questionEnum = QuestionEnum.getQuestion(subQuestion.getQuestionType());
            if(questionEnum == null) continue;
            if(!basicQuestionMap.containsKey(questionEnum) || basicQuestionMap.get(questionEnum).equals("0")) continue;

            String answerContent = null;
            if(questionEnum == QuestionEnum.精神情况) {
                answerContent = userBasicRecord.getMentality();
            } else if (subQuestion.getQuestionType().intValue() == QuestionEnum.食欲情况.getValue().intValue()) {
                answerContent = userBasicRecord.getAppetite();
            } else if (subQuestion.getQuestionType().intValue() == QuestionEnum.大便情况.getValue().intValue()) {
                answerContent = userBasicRecord.getShit();
            } else if (subQuestion.getQuestionType().intValue() == QuestionEnum.小便情况.getValue().intValue()) {
                answerContent = userBasicRecord.getUrine();
            }
            if (StringUtils.isEmpty(answerContent)) {
                repliedAll = false;
            } else {
                if(StringUtils.isNotEmpty(answerContent)) {
                    final String selectedAnswerContent = answerContent;
                    Consumer<PreQuestionAnswer> consumer = e -> {
                        String checed = e.getAnswerContent().equals(selectedAnswerContent) ? "Y" : null;
                        e.setChecked(checed);
                    };
                    List<PreQuestionAnswer> preQuestionAnswerList = PreQuestionUtils.getByQuestionCode(subQuestion.getQuestionCode());
                    preQuestionAnswerList = preQuestionAnswerList.stream().peek(consumer).collect(Collectors.toList());
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, preQuestionAnswerList);
                    subQuestionVoList.add(subQuestionVo);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(subQuestionVoList)) {
            basicQuestionVo.setSubQuestion(subQuestionVoList);
            basicQuestionVoList.add(basicQuestionVo);
        }
        return repliedAll;
    }

    /**
     * 获取既往史的答案
     * @return
     */
    private Boolean getRepliedOfPastMedicalHistory(String appType, UserInfo userInfo, UserBasicRecord userBasicRecord, List<BasicQuestionVo> basicQuestionVoList) {
        Boolean repliedAll = true;
        Long diagnosisId = userBasicRecord.getDiagnosisId();
        PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(QuestionEnum.鼓励2);
        List<PreQuestion> subPreQuestionList = PreQuestionUtils.getSubQuestion(preQuestion);
        List<SubQuestionVo> subQuestionVoList = Lists.newArrayList();
        List<PreQuestionAnswer> subAnswerList;
        for(PreQuestion subQuestion : subPreQuestionList) {
            String answerCodes = "";
            String answerTitles = "";
            List<PreQuestionAnswer> preQuestionAnswerList = Lists.newArrayList();
            if(subQuestion.getQuestionType().intValue() == QuestionEnum.既往史.getValue().intValue()) {
                answerTitles = userBasicRecord.getPastmedicalHistoryText();

                BasicQuestion question = basicQuestionMapper.findByQuestionCode(BasicQuestionType.PAST_MEDICAL_HISTORY.getValue());
                AbstractBasicAnswerProcessor answerProcessor = BasicAnswerProcessorAdaptor.getProcessor(question.getQuestionCode());
                IQuestionVo questionVo = answerProcessor.build(diagnosisId, question, userInfo);

                //将既往史转为PreQuestionAnswer对象后统一处理
                BasicQuestionWithSearchVo resultVo = (BasicQuestionWithSearchVo) questionVo;
                List<IAnswerVo> pastMedicalList = resultVo.getAnswers();
                preQuestionAnswerList = toPreQuestionAnswer(pastMedicalList, subQuestion);
            } else if(subQuestion.getQuestionType().intValue() == QuestionEnum.过敏史.getValue().intValue()) {
                answerTitles = userBasicRecord.getAllergicHistoryText();
                BasicQuestion question = basicQuestionMapper.findByQuestionCode(BasicQuestionType.ALLERGIC_HISTORY.getValue());
                AbstractBasicAnswerProcessor answerProcessor = BasicAnswerProcessorAdaptor.getProcessor(question.getQuestionCode());
                IQuestionVo questionVo = answerProcessor.build(diagnosisId, question, userInfo);

                //将过敏史转为PreQuestionAnswer对象后统一处理
                BasicQuestionWithSearchVo resultVo = (BasicQuestionWithSearchVo) questionVo;
                List<IAnswerVo> allergicList = resultVo.getAnswers();
                preQuestionAnswerList = toPreQuestionAnswer(allergicList, subQuestion);
            } else if(subQuestion.getQuestionType().intValue() == QuestionEnum.出生史.getValue().intValue()) {
                answerTitles = userBasicRecord.getFertilityType();
                preQuestionAnswerList = PreQuestionUtils.getByQuestionCode(subQuestion.getQuestionCode());
            } else if(subQuestion.getQuestionType().intValue() == QuestionEnum.胎龄.getValue().intValue()) {
                answerTitles = userBasicRecord.getGestationalAge();
                preQuestionAnswerList = PreQuestionUtils.getByQuestionCode(subQuestion.getQuestionCode());
            } else if(subQuestion.getQuestionType().intValue() == QuestionEnum.喂养史.getValue().intValue()) {
                answerTitles = userBasicRecord.getFeedType();
                preQuestionAnswerList = PreQuestionUtils.getByQuestionCode(subQuestion.getQuestionCode());
            } else if(subQuestion.getQuestionType().intValue() == QuestionEnum.预防接种史.getValue().intValue()) {
                answerTitles = userBasicRecord.getVaccinationHistoryText();
                preQuestionAnswerList = PreQuestionUtils.getByQuestionCode(subQuestion.getQuestionCode());
            } else if(subQuestion.getQuestionType().intValue() == QuestionEnum.是否处于月经期.getValue().intValue()) {
                answerTitles = userBasicRecord.getMenstrualPeriod();
                if (StringUtils.isNotEmpty(answerTitles)) {
                    answerTitles = answerTitles.equals("1") ? "是" : "否";
                }
                preQuestionAnswerList = PreQuestionUtils.getByQuestionCode(subQuestion.getQuestionCode());
            }
            if(StringUtils.isEmpty(answerTitles)) {
                repliedAll = false;
            } else {
                Set<String> selectedAnsewrContentList = Stream.of(answerTitles.split(",")).collect(Collectors.toSet());
                Consumer<PreQuestionAnswer> consumer = e -> {
                    String checked = selectedAnsewrContentList.contains(e.getAnswerContent()) ? "Y" : null;
                    e.setChecked(checked);
                };
                preQuestionAnswerList = preQuestionAnswerList.stream().peek(consumer).collect(Collectors.toList());
                SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, preQuestionAnswerList);
                subQuestionVoList.add(subQuestionVo);
            }
        }
        if(CollectionUtils.isNotEmpty(subQuestionVoList)) {
            BasicQuestionVo basicQuestionVo = buildBasicQuestionVo(appType, userBasicRecord, preQuestion);
            basicQuestionVo.setSubQuestion(subQuestionVoList);
            basicQuestionVoList.add(basicQuestionVo);
        }
        return repliedAll;
    }

    /**
     * 从已回答的医学问题中获取答案编码
     * @param userDiagnosisDetail
     * @return
     */
    private String getAnswerCode(UserDiagnosisDetail userDiagnosisDetail) {
        String answerCode = userDiagnosisDetail.getAnswerCode();
        if(StringUtils.isNotEmpty(answerCode)) {
            JSONArray jarr = JSONArray.parseArray(answerCode);
            StringJoiner sj = new StringJoiner(",");
            for(int i = 0; i < jarr.size(); i++) {
                sj.add(jarr.getString(i));
            }
            answerCode = sj.toString();
        }
        return answerCode;
    }

    /**
     * 把已回答的医学问题转化为前端视图
     * @param userDiagnosisDetail
     * @return
     */
    public List<BasicAnswerVo> parse2BasicAnswerVo(UserDiagnosisDetail userDiagnosisDetail) {
        List<BasicAnswerVo> basicAnswerVoList = Lists.newArrayList();
        String answerJson = userDiagnosisDetail.getAnswerJson();
        if(StringUtils.isNotEmpty(answerJson)) {
            String answerTitle = userDiagnosisDetail.getAnswerContent();
            Set<String> answerTitleSet = Stream.of(answerTitle.split("、")).collect(Collectors.toSet());

            JSONArray jarr = JSONArray.parseArray(answerJson);

            for(int i = 0; i < jarr.size(); i++) {
                JSONObject json = jarr.getJSONObject(i);
                String answerTitleItem = StringUtils.isNotEmpty(json.getString("answerPopuTitle")) ? json.getString("answerPopuTitle") : json.getString("answerTitle");
                if(answerTitleSet.contains(json.getString("answerTitle"))) {
                    String answerCodeItem = json.getString("answerCode");
                    BasicAnswerVo basicAnswerVo = new BasicAnswerVo(answerCodeItem, answerTitleItem);
                    basicAnswerVoList.add(basicAnswerVo);
                }
            }
        }
        return basicAnswerVoList;
    }

    /**
     * 过滤前端不需要展示的医学问题
     * @param userDiagnosisDetail
     * @return
     */
    private Boolean userDiagnosisFilter(UserDiagnosisDetail userDiagnosisDetail) {
        if(userDiagnosisDetail.getQuestionType().intValue() == QuestionEnum.年龄问题.getValue().intValue()) {
            return false;
        }
        if(userDiagnosisDetail.getQuestionType().intValue() == QuestionEnum.季节问题.getValue().intValue()) {
            return false;
        }
        return true;
    }

    /**
     * 把已回答的基础问题转化为前端视图
     * @param userBasicRecord
     * @param preQuestion
     * @return
     */
    private BasicQuestionVo buildBasicQuestionVo(String appType, UserBasicRecord userBasicRecord, PreQuestion preQuestion) {
        BasicQuestionVo basicQuestionVo = new BasicQuestionVo();
        Long diagnosisId = userBasicRecord.getDiagnosisId();
        String userId = userBasicRecord.getUserId().toString();
        String sympCode = userBasicRecord.getMainSymptomCode();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));

        basicQuestionVo.setUserId(userId);
        basicQuestionVo.setDiagnosisId(diagnosisId);
        basicQuestionVo.setSympCode(sympCode);
        basicQuestionVo.setDisplayType(preQuestion.getDisplayType());
        basicQuestionVo.setQuestionTitle(AppUtils.setUserNameAtQuestionTitle(appType, preQuestion.getTitle(), userInfo, userBasicRecord.getDoctorName()));
        basicQuestionVo.setPopuTitle(preQuestion.getPopuTitle());
        basicQuestionVo.setQuestionType(preQuestion.getQuestionType());
        basicQuestionVo.setQuestionCode(preQuestion.getQuestionCode());
        return basicQuestionVo;
    }

    private List<PreQuestionAnswer> toPreQuestionAnswer(List<IAnswerVo> answerVoList, PreQuestion preQuestion) {
        List<PreQuestionAnswer> resultList  = new ArrayList<>();
        for(IAnswerVo pm : answerVoList) {
            if (pm instanceof BasicAnswerVo) {
                BasicAnswerVo basicAnswerVo = (BasicAnswerVo)pm;
                PreQuestionAnswer preQuestionAnswer = new PreQuestionAnswer();
                preQuestionAnswer.setQuestionCode(preQuestion.getQuestionCode());
                preQuestionAnswer.setAnswerCode(basicAnswerVo.getAnswerCode());
                preQuestionAnswer.setAnswerContent(basicAnswerVo.getAnswerTitle());
                resultList.add(preQuestionAnswer);
            } else if (pm instanceof SelectedBasicAnswerVo) {
                SelectedBasicAnswerVo selectedBasicAnswerVo = (SelectedBasicAnswerVo) pm;
                PreQuestionAnswer preQuestionAnswer = new PreQuestionAnswer();
                preQuestionAnswer.setQuestionCode(preQuestion.getQuestionCode());
                preQuestionAnswer.setAnswerCode(selectedBasicAnswerVo.getAnswerValue());
                preQuestionAnswer.setAnswerContent(selectedBasicAnswerVo.getAnswerTitle());
                resultList.add(preQuestionAnswer);
            }
        }
        return resultList;
    }
}
