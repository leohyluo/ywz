package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.BasicQuestionType;
import com.alpha.commons.enums.GeneralSymptom;
import com.alpha.commons.enums.System;
import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.mapper.UserBasicRecordNewMapper;
import com.alpha.self.diagnosis.dao.DiagnosisConcomitantSymptomDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.mapper.BasicQuestionMapper;
import com.alpha.self.diagnosis.mapper.PreQuestionAnswerMapper;
import com.alpha.self.diagnosis.mapper.PreQuestionMapper;
import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.processor.AbstractBasicAnswerProcessor;
import com.alpha.self.diagnosis.processor.BasicAnswerProcessorAdaptor;
import com.alpha.self.diagnosis.service.BaseQuestionService;
import com.alpha.self.diagnosis.service.QuestionService;
import com.alpha.self.diagnosis.utils.PreQuestionUtils;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisConcomitantSymptom;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserInfoDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 普通问题业务处理类
 */
@Service
public class BaseQuestionServiceImpl implements BaseQuestionService, QuestionService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private PreQuestionMapper preQuestionMapper;
    @Resource
    private PreQuestionAnswerMapper preQuestionAnswerMapper;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private DiagnosisConcomitantSymptomDao diagnosisConcomitantSymptomDao;
    @Resource
    private UserBasicRecordNewMapper userBasicRecordMapper;
    @Resource
    private BasicQuestionMapper basicQuestionMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public BasicQuestionVo ask(ReplyQuestionVo replyQuestionVo) {
        String userId = replyQuestionVo.getUserId();
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        String questionCode = replyQuestionVo.getQuestionCode();
        String systemType = replyQuestionVo.getSystemType();
        AppType appType = AppType.findByValue(systemType);

        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));

        UserBasicRecord ubrParam = new UserBasicRecord();
        ubrParam.setDiagnosisId(diagnosisId);
        UserBasicRecord userBasicRecord = userBasicRecordMapper.selectOne(ubrParam);
        if(userBasicRecord == null) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        QuestionEnum questionEnum = QuestionEnum.getQuestion(replyQuestionVo.getQuestionType());
        PreQuestion preQuestion = PreQuestionUtils.getByQuestionType(questionEnum);
        if(preQuestion == null) {
            logger.error("无效的问题类型{}", replyQuestionVo.getQuestionType());
            throw new ServiceException(ResponseStatus.INVALID_VALUE);
        }
        Date birth = userBasicRecord.getBirth();
        Integer gender = userBasicRecord.getGender();
        //获取大类问题答案
        List<PreQuestionAnswer> answerList = getAnswer(appType, diagnosisId, questionCode);
        //获取子问题
        List<PreQuestion> subPreQuestions = this.getSubQuestion(gender, birth, appType, preQuestion.getQuestionCode());
        Map<QuestionEnum, String> basicQuestionMap = this.filterBasicQuestion(systemType, Long.valueOf(userId), diagnosisId);
        Set<Integer> basicQuestionSet = basicQuestionMap.keySet().stream().map(QuestionEnum::getValue).collect(toSet());
        Predicate<PreQuestion> questionPredicate = e -> {
            if(basicQuestionSet.contains(e.getQuestionType())) {
                QuestionEnum itemQuestionEnum = QuestionEnum.getQuestion(e.getQuestionType());
                return basicQuestionMap.get(itemQuestionEnum).equals("0") ? false : true;
            } else {
                return true;
            }
        };
        subPreQuestions = subPreQuestions.stream().filter(questionPredicate).collect(toList());
        //获取子问题的答案
        List<SubQuestionVo> subQuestionVoList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(subPreQuestions)) {
            for (PreQuestion subQuestion : subPreQuestions) {
                if(subQuestion.getQuestionType().intValue() == QuestionEnum.既往史.getValue().intValue()) {
                    BasicQuestion question = basicQuestionMapper.findByQuestionCode(BasicQuestionType.PAST_MEDICAL_HISTORY.getValue());
                    AbstractBasicAnswerProcessor answerProcessor = BasicAnswerProcessorAdaptor.getProcessor(question.getQuestionCode());
                    IQuestionVo questionVo = answerProcessor.build(diagnosisId, question, userInfo);

                    BasicQuestionWithSearchVo resultVo = (BasicQuestionWithSearchVo) questionVo;
                    List<IAnswerVo> pastMedicalList = toBasicAnswerVo(resultVo.getAnswers());
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, null);
                    subQuestionVo.setAnswers(pastMedicalList);
                    subQuestionVoList.add(subQuestionVo);
                } else if (subQuestion.getQuestionType().intValue() == QuestionEnum.过敏史.getValue().intValue()) {
                    BasicQuestion question = basicQuestionMapper.findByQuestionCode(BasicQuestionType.ALLERGIC_HISTORY.getValue());
                    AbstractBasicAnswerProcessor answerProcessor = BasicAnswerProcessorAdaptor.getProcessor(question.getQuestionCode());
                    IQuestionVo questionVo = answerProcessor.build(diagnosisId, question, userInfo);

                    BasicQuestionWithSearchVo resultVo = (BasicQuestionWithSearchVo) questionVo;
                    List<IAnswerVo> pastMedicalList = toBasicAnswerVo(resultVo.getAnswers());
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, null);
                    subQuestionVo.setAnswers(pastMedicalList);
                    subQuestionVoList.add(subQuestionVo);
                } else {
                    List<PreQuestionAnswer> subAnswerList = this.getAnswer(appType, diagnosisId, subQuestion.getQuestionCode());
                    SubQuestionVo subQuestionVo = new SubQuestionVo(subQuestion, subAnswerList);
                    subQuestionVoList.add(subQuestionVo);
                }
            }
        }
        String doctorName = userBasicRecord.getDoctorName();
        BasicQuestionVo basicQuestionVo = new BasicQuestionVo(systemType, userInfo, doctorName, diagnosisId, replyQuestionVo.getSympCode(), preQuestion, answerList);
        basicQuestionVo.setSubQuestion(subQuestionVoList);
        return basicQuestionVo;
    }

    @Override
    public BasicQuestionVo answer(ReplyQuestionVo replyQuestionVo) {
        Long userId = Long.valueOf(replyQuestionVo.getUserId());
        Long diagnosisId = replyQuestionVo.getDiagnosisId();
        String questionCode = replyQuestionVo.getQuestionCode();
        Integer questionType = replyQuestionVo.getQuestionType();
        QuestionEnum parentQuestionEnum = QuestionEnum.getQuestion(questionType);
        List<ReplyAnswerVo> answerVoList = replyQuestionVo.getAnswers();

        UserBasicRecord ubrParam = new UserBasicRecord();
        ubrParam.setDiagnosisId(diagnosisId);
        UserBasicRecord userBasicRecord = userBasicRecordMapper.selectOne(ubrParam);
        if(userBasicRecord == null) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        String systemType = replyQuestionVo.getSystemType();
        AppType appType = AppType.findByValue(systemType);
        Date birth = userBasicRecord.getBirth();
        Integer gender = userBasicRecord.getGender();

        //保存问题的答案
        if(CollectionUtils.isNotEmpty(answerVoList)) {
            saveAnswer(userId, diagnosisId, parentQuestionEnum, answerVoList);
        }
        //获取下一个大类问题
        PreQuestion question = getNextQuestion(gender, birth, appType, questionCode, answerVoList);
        if(question == null) {
            logger.warn("问题{}无下一个问题", questionCode);
            return null;
        }
        replyQuestionVo.setQuestionCode(question.getQuestionCode());
        replyQuestionVo.setQuestionType(question.getQuestionType());
        //获取大类问题答案
        BasicQuestionVo basicQuestionVo = this.ask(replyQuestionVo);
        return basicQuestionVo;
    }

    @Override
    public Map<QuestionEnum, String> filterBasicQuestion(String systemType, Long userId, Long diagnosisId) {
        //String birth, int gender
        UserInfo userInfo = userInfoDao.queryByUserId(userId);
        Date birth = userInfo.getBirth();
        Integer gender = userInfo.getGender();
        EnumMap<QuestionEnum, String> result = new EnumMap(QuestionEnum.class);
        String specialPeriodCode = "0";		//女性特殊时期
        String weightCode = "0";			//体重
        String bornHistoryCode = "0";		//出生史
        String vaccinationHistory = "0";	//预防接种史
        //默认提问大便、小便、食欲、精神问题
        String shit = "1";
        String urine = "1";
        String appetite = "1";
        String mentality = "1";

        String menstruation = "0";          //妇科版月经史提问
        String marry = "0";                 //妇科版女性提问

        try {
            if (systemType.equals(AppType.WOMAN.getValue())) {
                menstruation = "1";
                marry = "1";
            } else {
                if (diagnosisId != null) {
                    Set<Integer> generalSymptomSet = new HashSet<>();
                    //根据问诊过程判断是否需要提问大小便、食欲、精神情况
                    List<UserDiagnosisDetail> udds = userDiagnosisDetailDao.listUserDiagnosisDetail(diagnosisId);
                    if (CollectionUtils.isNotEmpty(udds)) {
                        String mainSympCode = udds.get(0).getSympCode();
                        Map<String, Object> param = new HashMap<>();
                        param.put("sympCode", mainSympCode);
                        //主症状的一般症状性质
                        List<DiagnosisMainSymptoms> mainSymptomsList = diagnosisMainSymptomsDao.query(param);
                        if (CollectionUtils.isNotEmpty(mainSymptomsList)) {
                            DiagnosisMainSymptoms mainSymptom = mainSymptomsList.get(0);
                            if (mainSymptom.getGeneralSymptom() != null) {
                                generalSymptomSet.add(mainSymptom.getGeneralSymptom());
                            }
                        }
                        //伴随症状的一般症状性质
                        String cocnSympNames = udds.stream().filter(e -> e.getQuestionType() != null).filter(e -> e.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()
                                || e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).collect(joining("、"));
                        if (StringUtils.isNotEmpty(cocnSympNames)) {
                            List<String> cocnSympNameList = Stream.of(cocnSympNames.split("、")).collect(toList());
                            List<DiagnosisConcomitantSymptom> concomitantSymptomList = diagnosisConcomitantSymptomDao.listBySympName(cocnSympNameList);
                            if(CollectionUtils.isNotEmpty(concomitantSymptomList)) {
                                Set<Integer> generalSymptomSet4ConcSymp = concomitantSymptomList.stream().filter(e->e.getGeneralSymptom() != null).map(DiagnosisConcomitantSymptom::getGeneralSymptom).collect(toSet());
                                generalSymptomSet.addAll(generalSymptomSet4ConcSymp);
                            }
                        }
                        //主症状、伴随症状如果与一般症状有关系，则不提问相关一般症状
                        if(CollectionUtils.isNotEmpty(generalSymptomSet)) {
                            if(generalSymptomSet.contains(GeneralSymptom.SHIT.getValue())) {
                                shit = "0";
                            }
                            if(generalSymptomSet.contains(GeneralSymptom.URINE.getValue())) {
                                urine = "0";
                            }
                            if(generalSymptomSet.contains(GeneralSymptom.APPETITE.getValue())) {
                                appetite = "0";
                            }
                            if(generalSymptomSet.contains(GeneralSymptom.MENTALITY.getValue())) {
                                mentality = "0";
                            }
                        }
                    }
                }
                float age = DateUtils.getAge(birth);
                if(gender == 1) {
                    if(age >= 10 && age < 18) {
                        specialPeriodCode = BasicQuestionType.MENSTRUAL_PERIOD.getValue();
                    } else if (age >=18 && age <= 52) {
                        specialPeriodCode = BasicQuestionType.SPECIAL_PERIOD.getValue();
                    }
                }
                if(age >=0 && age <= 14) {
                    weightCode = BasicQuestionType.WEIGHT.getValue();
                }
                if(age >= 0 && age <= 1) {
                    bornHistoryCode = BasicQuestionType.FERTILITY_TYPE.getValue();
                }
                if(systemType.equals(AppType.CHILD.getValue())) {
                    if(age >= 0 && age <= 18) {
                        vaccinationHistory = BasicQuestionType.VACCINATION_HISTORY.getValue();
                    }
                }
            }
            result.put(QuestionEnum.介绍信息, "1");
            result.put(QuestionEnum.引言, "1");
            result.put(QuestionEnum.初诊, "1");
            result.put(QuestionEnum.完善信息, "1");
            result.put(QuestionEnum.出生日期, "1");
            result.put(QuestionEnum.性别, "1");
            result.put(QuestionEnum.体重, "1");
            result.put(QuestionEnum.鼓励1, "1");
            result.put(QuestionEnum.一般情况, "1");
            result.put(QuestionEnum.食欲情况, appetite);
            result.put(QuestionEnum.精神情况, mentality);
            result.put(QuestionEnum.大便情况, shit);
            result.put(QuestionEnum.小便情况, urine);
            result.put(QuestionEnum.鼓励2, "1");
            result.put(QuestionEnum.预防接种史, vaccinationHistory);
            result.put(QuestionEnum.是否处于月经期, specialPeriodCode);
            result.put(QuestionEnum.体重, weightCode);
            result.put(QuestionEnum.出生史, bornHistoryCode);
            result.put(QuestionEnum.婚育史, marry);
        } catch (Exception e) {
            logger.error("askBasicQuestion发生异常",e);
        }
        return result;
    }

    private void saveAnswer(Long userId, Long diagnosisId, QuestionEnum parentQuestionEnum, List<ReplyAnswerVo> answerVoList) {
        UserBasicRecord ubrParam = new UserBasicRecord();
        ubrParam.setDiagnosisId(diagnosisId);
        UserBasicRecord userBasicRecord = userBasicRecordMapper.selectOne(ubrParam);
        UserInfo userInfo = userInfoDao.queryByUserId(userId);

        if(parentQuestionEnum == QuestionEnum.初诊) {
            ReplyAnswerVo answer = answerVoList.get(0);
            Integer answerCode = Integer.parseInt(answer.getAnswerCode());
            //复诊
            if(answerCode.intValue() == 2001) {
                //如果是复诊则清空之前保存的信息
                UserBasicRecord emptyUserBasicRecord = new UserBasicRecord();
                emptyUserBasicRecord.setId(userBasicRecord.getId());
                emptyUserBasicRecord.setUserId(userId);
                emptyUserBasicRecord.setDiagnosisId(diagnosisId);
                emptyUserBasicRecord.setDiagnosisType(answerCode);
                emptyUserBasicRecord.setHisRegisterNo(userBasicRecord.getHisRegisterNo());
                emptyUserBasicRecord.setCreateTime(userBasicRecord.getCreateTime());
                emptyUserBasicRecord.setUpdateTime(new Date());
                userBasicRecordMapper.updateByPrimaryKey(emptyUserBasicRecord);
                //清空医学问题问诊过程
                userDiagnosisDetailDao.deleteUserDiagnosisDetail(diagnosisId, null);
            } else {
                userBasicRecord.setDiagnosisType(answerCode);
                userBasicRecord.setUpdateTime(new Date());
                userBasicRecordMapper.updateByPrimaryKey(userBasicRecord);
            }
        } else if (parentQuestionEnum == QuestionEnum.完善信息) {
            BasicInfoVo basicInfoVo = buildBasicInfoVo(answerVoList);
            BeanCopierUtil.copy(basicInfoVo, userBasicRecord);
            BeanCopierUtil.copy(basicInfoVo, userInfo);
            userBasicRecord.setUpdateTime(new Date());
            userInfoDao.update(userInfo);
            userBasicRecordMapper.updateByPrimaryKey(userBasicRecord);
        } else if(parentQuestionEnum == QuestionEnum.一般情况) {
            GeneralSymptomVo generalSymptomVo = buildGeneralSymptomVo(answerVoList);
            BeanCopierUtil.copy(generalSymptomVo, userBasicRecord);
            userBasicRecord.setUpdateTime(new Date());
            userBasicRecordMapper.updateByPrimaryKey(userBasicRecord);
        } else if (parentQuestionEnum == QuestionEnum.鼓励2) {
            PastMedicalHistoryVo pastMedicalHistoryVo = buildPastMedicalHistoryVo(answerVoList);
            BeanCopierUtil.copy(pastMedicalHistoryVo, userBasicRecord);
            userBasicRecord.setUpdateTime(new Date());
            userBasicRecordMapper.updateByPrimaryKey(userBasicRecord);
        }
    }

    private BasicInfoVo buildBasicInfoVo(List<ReplyAnswerVo> answerVoList) {
        BasicInfoVo basicInfoVo = new BasicInfoVo();
        for (ReplyAnswerVo answer : answerVoList) {
            Integer subQuestionType = answer.getSubQuestionType();
            QuestionEnum question = QuestionEnum.getQuestion(subQuestionType);
            if (question == null) continue;
            String answerCode = answer.getAnswerCode();
            String answerTitle = answer.getAnswerTitle();
            if (question == QuestionEnum.出生日期) {
                basicInfoVo.setBirth(DateUtils.stringToDate(answerTitle));
            } else if (question == QuestionEnum.性别) {
                basicInfoVo.setGender(Integer.parseInt(answerCode));
            } else if (question == QuestionEnum.体重) {
                basicInfoVo.setWeight(answerTitle);
            }
        }
        return basicInfoVo;
    }

    private GeneralSymptomVo buildGeneralSymptomVo(List<ReplyAnswerVo> answerVoList) {
        GeneralSymptomVo generalSymptomVo = new GeneralSymptomVo();
        for(ReplyAnswerVo answer : answerVoList) {
            Integer subQuestionType = answer.getSubQuestionType();
            QuestionEnum question = QuestionEnum.getQuestion(subQuestionType);
            if(question == null) continue;
            String answerCode = answer.getAnswerCode();
            String answerTitle = answer.getAnswerTitle();
            if (question == QuestionEnum.精神情况) {
                generalSymptomVo.setMentality(answerTitle);
            } else if (question == QuestionEnum.食欲情况) {
                generalSymptomVo.setAppetite(answerTitle);
            } else if (question == QuestionEnum.大便情况) {
                generalSymptomVo.setShit(answerTitle);
            } else if (question == QuestionEnum.小便情况) {
                generalSymptomVo.setUrine(answerTitle);
            }
        }
        return generalSymptomVo;
    }

    private PastMedicalHistoryVo buildPastMedicalHistoryVo(List<ReplyAnswerVo> answerVoList) {
        List<String> pastmedicalHistoryCodeList = new ArrayList<>();
        List<String> pastmedicalHistoryTextList = new ArrayList<>();
        List<String> allergicHistoryCodeList = new ArrayList<>();
        List<String> allergicHistoryTextList = new ArrayList<>();
        PastMedicalHistoryVo pastMedicalHistoryVo = new PastMedicalHistoryVo();
        for(ReplyAnswerVo answer : answerVoList) {
            Integer subQuestionType = answer.getSubQuestionType();
            QuestionEnum question = QuestionEnum.getQuestion(subQuestionType);
            if(question == null) continue;
            String answerCode = answer.getAnswerCode();
            String answerTitle = answer.getAnswerTitle();
            if (question == QuestionEnum.既往史) {
                pastmedicalHistoryCodeList.add(answerCode);
                pastmedicalHistoryTextList.add(answerTitle);
            } else if (question == QuestionEnum.过敏史) {
                allergicHistoryCodeList.add(answerCode);
                allergicHistoryTextList.add(answerTitle);
            } else if (question == QuestionEnum.出生史){
                pastMedicalHistoryVo.setFertilityType(answerTitle);
            }else if (question == QuestionEnum.胎龄) {
                pastMedicalHistoryVo.setGestationalAge(answerTitle);
            } else if (question == QuestionEnum.喂养史) {
                pastMedicalHistoryVo.setFeedType(answerTitle);
            } else if (question == QuestionEnum.预防接种史) {
                pastMedicalHistoryVo.setVaccinationHistoryText(answerTitle);
            } else if (question == QuestionEnum.是否处于月经期) {
                pastMedicalHistoryVo.setMenstrualPeriod(answerCode);
            } else if (question == QuestionEnum.月经初潮) {
                pastMedicalHistoryVo.setMenarche(answerTitle);
            } else if (question == QuestionEnum.月经周期) {
                pastMedicalHistoryVo.setMenarcheCycle(answerTitle);
            } else if (question == QuestionEnum.经期) {
                pastMedicalHistoryVo.setMenarchePeroid(answerTitle);
            } else if (question == QuestionEnum.月经史) {
                pastMedicalHistoryVo.setMenarcheStatus(answerCode);
            } else if (question == QuestionEnum.婚育史) {
                pastMedicalHistoryVo.setMarriage(answerTitle);
            } else if (question == QuestionEnum.足月产孩子个数) {
                pastMedicalHistoryVo.setMatureChildCount(Integer.parseInt(answerTitle));
            } else if (question == QuestionEnum.早产孩子个数) {
                pastMedicalHistoryVo.setPrematureChildCount(Integer.parseInt(answerTitle));
            } else if (question == QuestionEnum.流产孩子个数) {
                pastMedicalHistoryVo.setMiscarryChildCount(Integer.parseInt(answerTitle));
            } else if (question == QuestionEnum.现存孩子个数) {
                pastMedicalHistoryVo.setNowChildCount(Integer.parseInt(answerTitle));
            } else if (question == QuestionEnum.末次月经时间) {
                pastMedicalHistoryVo.setLmp(answerTitle);
            }
        }
        if(CollectionUtils.isNotEmpty(pastmedicalHistoryCodeList)) {
            pastMedicalHistoryVo.setPastmedicalHistoryCode(pastmedicalHistoryCodeList.stream().collect(joining(",")));
            pastMedicalHistoryVo.setPastmedicalHistoryText(pastmedicalHistoryTextList.stream().collect(joining(",")));
        }
        if(CollectionUtils.isNotEmpty(allergicHistoryCodeList)) {
            pastMedicalHistoryVo.setAllergicHistoryCode(allergicHistoryCodeList.stream().collect(joining(",")));
            pastMedicalHistoryVo.setAllergicHistoryText(allergicHistoryTextList.stream().collect(joining(",")));
        }
        return pastMedicalHistoryVo;
    }

    private PreQuestion getNextQuestion(Integer gender, Date birth, AppType appType, String questionCode, List<ReplyAnswerVo> answerVoList) {
        String nextQuestionCode = "";
        if(CollectionUtils.isNotEmpty(answerVoList)) {
            for (ReplyAnswerVo item : answerVoList) {
                if (StringUtils.isNotEmpty(item.getAnswerCode())) {
                    List<PreQuestionAnswer> preQuestionAnswerList = PreQuestionUtils.getByAnswerCode(item.getSubQuestionCode(), item.getAnswerCode());
                    if (CollectionUtils.isNotEmpty(preQuestionAnswerList)) {
                        PreQuestionAnswer preQuestionAnswer = preQuestionAnswerList.get(0);
                        if (StringUtils.isNotEmpty(preQuestionAnswer.getNextQuestionCode())) {
                            nextQuestionCode = preQuestionAnswer.getNextQuestionCode();
                            break;
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(nextQuestionCode)) {
            PreQuestion question = preQuestionMapper.getByQuestionCode(nextQuestionCode);
            return  question;
        }

        PreQuestion question = preQuestionMapper.getByQuestionCode(questionCode);
        if(question == null) {
            logger.error("基础问题{}不存在", questionCode);
            return null;
        }

        PreQuestion param = new PreQuestion();
        if (birth != null) {
            float age = DateUtils.getAge(birth);
            param.setAge(age);
        }
        if(gender != null) {
            param.setGender(gender);
        }

        param.setDefaultOrder(question.getDefaultOrder());
        param.setAppType(appType.getValue());
        question = preQuestionMapper.getNextQuestion(param);
        return question;
    }

    private List<PreQuestion> getSubQuestion(Integer gender, Date birth, AppType appType, String questionCode) {
        PreQuestion param = new PreQuestion();
        param.setAppType(appType.getValue());
        param.setGender(gender);
        if (birth != null) {
            float age = DateUtils.getAge(birth);
            param.setAge(age);
        }
        param.setParentQuestionCode(questionCode);
        List<PreQuestion> subPreQuestions = preQuestionMapper.getSubQuestions(param);
        return subPreQuestions;
    }

    private List<PreQuestionAnswer> getAnswer(AppType appType, Long diagnosisId, String questionCode) {
        UserBasicRecord ubrParam = new UserBasicRecord();
        ubrParam.setDiagnosisId(diagnosisId);
        UserBasicRecord userBasicRecord = userBasicRecordMapper.selectOne(ubrParam);
        if(userBasicRecord == null) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        Integer gender = userBasicRecord.getGender();
        Date birth = userBasicRecord.getBirth();
        PreQuestionAnswer param = new PreQuestionAnswer();
        param.setGender(gender);
        if (birth != null) {
            float age = DateUtils.getAge(birth);
            param.setAge(age);
        }
        param.setQuestionCode(questionCode);
        param.setAppType(appType.getValue());
        return preQuestionAnswerMapper.getByQuestionCode(param);
    }

    private List<IAnswerVo> toBasicAnswerVo(List<IAnswerVo> answerVoList) {
        List<IAnswerVo> resultList  = new ArrayList<>();
        for(IAnswerVo pm : answerVoList) {
            BasicAnswerVo basicAnswerVo = new BasicAnswerVo();
            if (pm instanceof SelectedBasicAnswerVo) {
                SelectedBasicAnswerVo selectedBasicAnswerVo = (SelectedBasicAnswerVo) pm;
                basicAnswerVo.setAnswerValue(selectedBasicAnswerVo.getAnswerValue());
                basicAnswerVo.setAnswerTitle(selectedBasicAnswerVo.getAnswerTitle());
                basicAnswerVo.setChecked("Y");
                resultList.add(basicAnswerVo);
            } else if (pm instanceof BasicAnswerVo) {
                resultList.add((BasicAnswerVo) pm);
            }
        }
        return resultList;
    }
}
