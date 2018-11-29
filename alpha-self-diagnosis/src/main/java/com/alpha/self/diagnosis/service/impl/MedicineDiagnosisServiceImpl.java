package com.alpha.self.diagnosis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.self.diagnosis.dao.*;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.DiagnosisOutcomeVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.service.DiagnosisDiseaseService;
import com.alpha.self.diagnosis.service.MedicineDiagnosisService;
import com.alpha.self.diagnosis.service.SymptomAccompanyService;
import com.alpha.self.diagnosis.utils.DiseaseWeightUtil;
import com.alpha.self.diagnosis.utils.MedicineSortUtil;
import com.alpha.self.diagnosis.utils.ServiceUtil;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserBasicRecordDao;
import com.alpha.user.dao.UserInfoDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by xc.xiong on 2017/10/16.
 */
@Service
public class MedicineDiagnosisServiceImpl implements MedicineDiagnosisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicineDiagnosisServiceImpl.class);

    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource
    private DiagnosisQuestionAnswerDao diagnosisQuestionAnswerDao;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private SymptomAccompanyService symptomAccompanyService;
    @Resource
    private DiagnosisDiseaseService diagnosisDiseaseService;
    @Resource
    private DiagnosisMainsympConcsympDao diagnosisMainsympConcsympDao;
    @Resource
    private UserDiagnosisOutcomeDao userDiagnosisOutcomeDao;
    @Resource
    private UserBasicRecordDao userBasicRecordDao;
    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 诊断方法 主要逻辑
     *
     * @param diagnosisId
     */
    @Override
    public List<UserDiagnosisOutcome> diagnosisOutcome(Long diagnosisId, String mainSympCode,UserInfo userInfo) {
        List<UserDiagnosisOutcome> userDiagnosisOutcomes = new ArrayList<>();
        try {
            List<UserDiagnosisDetail> udds = userDiagnosisDetailDao.listUserDiagnosisDetail(diagnosisId);
            Set<String> questionCodes = new HashSet<>();
            Set<String> answerCodes = new HashSet<>();
            Set<String> forwardDiseaseCodeSet = new HashSet<>();
            Set<String> nothingDiseaseCodeSet = new HashSet<>();
            Set<String> reverseDiseaseCodeSet = new HashSet<>();
            UserDiagnosisDetail mainsympConcsympQuestion = null;
            UserDiagnosisDetail normalMainsympConcsympQuestion = null;
            for (Iterator iterator = udds.iterator(); iterator.hasNext(); ) {
                UserDiagnosisDetail udd = (UserDiagnosisDetail) iterator.next();
                if (udd.getAnswerTime() == null)
                    continue;
                questionCodes.add(udd.getQuestionCode());
                if (udd.getQuestionType() == QuestionEnum.伴随症状.getValue()) {
                    mainsympConcsympQuestion = udd;
                    continue;
                }
                if (udd.getQuestionType() == QuestionEnum.常见伴随症状.getValue()) {
                    normalMainsympConcsympQuestion = udd;
                    continue;
                }
                answerCodes.addAll(JSON.parseArray(udd.getAnswerCode(), String.class));

                String forwardDiseaseCode = udd.getForwardDiseaseCode();
                String nothingDiseaseCode = udd.getNothingDiseaseCode();
                String reverseDiseaseCode = udd.getReverseDiseaseCode();
                if (StringUtils.isNotEmpty(forwardDiseaseCode)) {
                    List<String> codes = (List) JSON.parseArray(forwardDiseaseCode);
                    if (codes != null && codes.size() > 0)
                        forwardDiseaseCodeSet.addAll(codes);
                }
                if (StringUtils.isNotEmpty(reverseDiseaseCode)) {
                    List<String> codes = (List) JSON.parseArray(reverseDiseaseCode);
                    if (codes != null && codes.size() > 0)
                        reverseDiseaseCodeSet.addAll(codes);
                }
                if (StringUtils.isNotEmpty(nothingDiseaseCode)) {
                    List<String> codes = (List) JSON.parseArray(nothingDiseaseCode);
                    if (codes != null && codes.size() > 0)
                        nothingDiseaseCodeSet.addAll(codes);
                }
            }

            //获取常见伴随症状数据
            Map<String, List<MedicineQuestionVo>> dmcsMap4Normal = new HashMap<>();
            if (normalMainsympConcsympQuestion != null && StringUtils.isNotEmpty(normalMainsympConcsympQuestion.getAnswerCode())) {
                String concSympName = normalMainsympConcsympQuestion.getAnswerContent();
                //concSympName = concSympName.replace("、",",");
                Set<String> concSympNames = Stream.of(concSympName.split("、")).collect(Collectors.toSet());
                List<DiagnosisMainsympConcsymp> diagnosisMainsympConcsymps = diagnosisMainsympConcsympDao.listByConcSympNames(mainSympCode, concSympNames);

                if (CollectionUtils.isNotEmpty(diagnosisMainsympConcsymps)) {
                    List<String> normalConcSympCodes = diagnosisMainsympConcsymps.stream().map(DiagnosisMainsympConcsymp::getConcSympCode).distinct().collect(Collectors.toList());
                    dmcsMap4Normal = symptomAccompanyService.mapDiagnosisMainsympConcsymp2(mainSympCode, normalConcSympCodes);
                }
            }
            //获取伴随症状数据
            Map<String, List<MedicineQuestionVo>> dmcsMap = new HashMap<>();
            List<String> concSympCodes = new ArrayList<>();
            if (mainsympConcsympQuestion != null && StringUtils.isNotEmpty(mainsympConcsympQuestion.getAnswerCode())) {
                concSympCodes = (List) JSON.parseArray(mainsympConcsympQuestion.getAnswerCode());
                if (concSympCodes != null && concSympCodes.size() > 0) {
                    dmcsMap = symptomAccompanyService.mapDiagnosisMainsympConcsymp2(mainSympCode, concSympCodes);
                }
            }
            //主症状下的问题总数
            List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listDiagnosisMainsympQuestion(mainSympCode);

            // 查找症状下疾病答案最多的答案总数总数，条件：所有疾病、主、问题，排序，取最多的一条
            Map<String, Map<String, DiagnosisMainsympQuestion>> deiseaseQuestionMap = listAnswerCount(mainSympCode);
            Map<String, Map<String, DiagnosisMainsympQuestion>> deiseaseconcSympMap = listConcSympCount(mainSympCode);
            // 获取所有的答案
            //List<MedicineQuestionVo> mqvAnswers = diagnosisQuestionAnswerDao.listMedicineQuestionVo(questionCodes, answerCodes);
            List<MedicineQuestionVo> mqvAnswers = diagnosisQuestionAnswerDao.listMedicineQuestionVo2(mainSympCode, questionCodes, answerCodes);
            Map<String, List<MedicineQuestionVo>> mqvAnswerMap = new HashMap<>();
            for (Iterator iterator = mqvAnswers.iterator(); iterator.hasNext(); ) {
                MedicineQuestionVo mqv = (MedicineQuestionVo) iterator.next();
                if(GlobalConstants.UNKNOWN_ANSWER.equals(mqv.getAnswerTitle())) {
                    continue;
                }
                if(diagnosisDiseaseService.filterByUserInfo(mqv.getDiseaseCode(), userInfo) == true) {
                    List<MedicineQuestionVo> questions = mqvAnswerMap.get(mqv.getDiseaseCode()) == null ? new ArrayList<>() : mqvAnswerMap.get(mqv.getDiseaseCode());
                    questions.add(mqv);
                    mqvAnswerMap.put(mqv.getDiseaseCode(), questions);
                }
            }

            Set<String> allDiseaseCodeSet = new HashSet<>();
            //modify at v1.1.3
            /*allDiseaseCodeSet.addAll(mqvAnswerMap.keySet());
            allDiseaseCodeSet.addAll(dmcsMap4Normal.keySet());
            allDiseaseCodeSet.addAll(dmcsMap.keySet());*/

            allDiseaseCodeSet.addAll(forwardDiseaseCodeSet);
            allDiseaseCodeSet.addAll(nothingDiseaseCodeSet);
            allDiseaseCodeSet.addAll(reverseDiseaseCodeSet);
            //查询所有的疾病名称
            //Map<String, DiagnosisDisease> diagnosisDiseaseMap = diagnosisDiseaseService.mapDiagnosisDisease(mqvAnswerMap.keySet(),userInfo);
            Map<String, DiagnosisDisease> diagnosisDiseaseMap = diagnosisDiseaseService.mapDiagnosisDisease(allDiseaseCodeSet,userInfo);

            for (Map.Entry<String, DiagnosisDisease> entry : diagnosisDiseaseMap.entrySet()) {
                Double diseaseWeight = 0d;
                StringBuffer calculationFormula = new StringBuffer();
                DiagnosisDisease disease = entry.getValue();

                List<MedicineQuestionVo> mqvList4NormalCocnSymp = dmcsMap4Normal.get(disease.getDiseaseCode());
                //用常见伴随症状计算疾病权重
                if(CollectionUtils.isNotEmpty(mqvList4NormalCocnSymp)) {
                    DiagnosisMainsympQuestion dmQuestion = null;
                    String diseaseName = disease.getDiseaseName();
                    Map<String, DiagnosisMainsympQuestion> dmqMap = deiseaseconcSympMap.get(entry.getKey());
                    for (String questionCode : dmqMap.keySet()) {
                        DiagnosisMainsympQuestion dmq = dmqMap.get(questionCode);
                        //常见伴随症状用伴随症状的问题
                        if (dmq.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()) {
                            dmQuestion = dmq;
                            break;
                        }
                    }
                    diseaseWeight = diseaseWeight + DiseaseWeightUtil.diagnosisOutcome2(dmQuestions.size(), diseaseName, calculationFormula, mqvList4NormalCocnSymp, dmQuestion);
                }
                //用医学问题计算疾病权重
                List<MedicineQuestionVo> mqvList4Answer = mqvAnswerMap.get(disease.getDiseaseCode());
                if(CollectionUtils.isNotEmpty(mqvList4Answer)) {
                    for (MedicineQuestionVo mqv : mqvList4Answer) {
                        Integer questionType = mqv.getQuestionType();
                        String diseaseCode = mqv.getDiseaseCode();
                        DiagnosisMainsympQuestion dmQuestion = null;
                        if (StringUtils.isEmpty(diseaseCode)) {
                            continue;
                        }
                        if(questionType == QuestionEnum.医学问题.getValue() || questionType == QuestionEnum.年龄问题.getValue() || questionType == QuestionEnum.季节问题.getValue()) {
                            dmQuestion = deiseaseQuestionMap.get(diseaseCode).get(mqv.getQuestionCode());
                        }
                        if (deiseaseQuestionMap.get(diseaseCode) == null || dmQuestion == null)
                            continue;

                        //DiagnosisMainsympQuestion dmQuestion = deiseaseQuestionMap.get(mqv.getDiseaseCode()).get(mqv.getQuestionCode());
                        calculationFormula.append("<p>" + disease.getDiseaseName() + " " + mqv.getDiseaseCode());
                        calculationFormula.append(" >> " + mqv.getQuestionTitle() + " " + mqv.getQuestionCode());
                        calculationFormula.append(" $" + mqv.getAnswerTitle() + " " + mqv.getAnswerCode() + "</p>");
                        //主症状下的问题权重
                        if (mqv.getQuestionType() == QuestionEnum.医学问题.getValue() || mqv.getQuestionType() == QuestionEnum.年龄问题.getValue() || mqv.getQuestionType() == QuestionEnum.季节问题.getValue()) {
                            Double Y = DiseaseWeightUtil.questionWeightFormula(dmQuestions.size(), mqv.getQuestionWeight(), mqv.getQuestionStandardDeviation(), calculationFormula);
                            Double N = DiseaseWeightUtil.answerWeightFormula(mqv.getAnswerWeight(), mqv.getAnswerStandardDeviation(), dmQuestion, calculationFormula);
                            diseaseWeight = diseaseWeight + DiseaseWeightUtil.diseaseWeightFormula(Y, N, calculationFormula);
                        } else {
                            continue;
                        }
                        //特异性标志 -1是反向特异性，2、是正向特异性，1是正+反特异性 0是无特异性
                        if (nothingDiseaseCodeSet.contains(mqv.getDiseaseCode()))
                            nothingDiseaseCodeSet.add(mqv.getDiseaseCode());
                        if (forwardDiseaseCodeSet.contains(mqv.getDiseaseCode()))
                            forwardDiseaseCodeSet.add(mqv.getDiseaseCode());    //正向特异性编码
                        if (reverseDiseaseCodeSet.contains(mqv.getDiseaseCode()))
                            reverseDiseaseCodeSet.add(mqv.getDiseaseCode());    //反向特异性编码
                    }
                }
                //用伴随症状计算疾病权重
                List<MedicineQuestionVo>  mqvs = dmcsMap.get(entry.getKey());
                if(CollectionUtils.isNotEmpty(mqvs)) {
                    DiagnosisMainsympQuestion dmQuestion = null;
                    String diseaseName = disease.getDiseaseName();
                    Map<String, DiagnosisMainsympQuestion> dmqMap = deiseaseconcSympMap.get(entry.getKey());
                    for (String questionCode : dmqMap.keySet()) {
                        DiagnosisMainsympQuestion dmq = dmqMap.get(questionCode);
                        if (dmq.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()) {
                            dmQuestion = dmq;
                            break;
                        }
                    }
                    diseaseWeight = diseaseWeight + DiseaseWeightUtil.diagnosisOutcome2(dmQuestions.size(), diseaseName, calculationFormula, mqvs, dmQuestion);

                    //当前疾病是否为罕见病,并且不在正向特异性疾病中
                    if(disease.getIsCommon() != null && disease.getIsCommon() == false && forwardDiseaseCodeSet.contains(disease.getDiseaseCode()) == false) {
                        DiagnosisMainsympConcsymp maxConcSymp = symptomAccompanyService.getMaxWeightConcSymp(mainSympCode);
                        if(mainSympCode != null) {
                            //找出权重最高的伴随症状(M)
                            Double maxConcSympWeight = maxConcSymp.getRate();
                            //根据M构建对象传入计算疾病权重的公式中
                            MedicineQuestionVo medicineQuestionVo4KeyConcSymp = mqvs.get(0);
                            medicineQuestionVo4KeyConcSymp.setAnswerCode(maxConcSymp.getConcSympCode());
                            medicineQuestionVo4KeyConcSymp.setAnswerTitle(GlobalConstants.KEY_CONC_SYMP_NAME);
                            medicineQuestionVo4KeyConcSymp.setAnswerWeight(maxConcSympWeight);
                            List<MedicineQuestionVo> mqvList4KeyConcSymp = new ArrayList<>();
                            mqvList4KeyConcSymp.add(medicineQuestionVo4KeyConcSymp);
                            //关键伴随症状分数(问题权重*答案权重)
                            Double weight4KeyConcSymp = DiseaseWeightUtil.calcSingleConcSympWeight(dmQuestions.size(), diseaseName, calculationFormula, mqvList4KeyConcSymp, dmQuestion);
                            //罕见疾病系数 = (疾病权重- 关键伴随症状分数) / 疾病权重
                            Double originDiseaseWeight = diseaseWeight;
                            Double diseaseWeightWithKeyConcSymp = originDiseaseWeight - weight4KeyConcSymp;
                            Double diseaseCoefficient = diseaseWeightWithKeyConcSymp / originDiseaseWeight;
                            //罕见疾病最终权重=疾病权重 * 罕见疾病系数
                            diseaseWeight = originDiseaseWeight * diseaseCoefficient;
                            calculationFormula.append("<p>" + diseaseName + " " + disease.getDiseaseCode());
                            calculationFormula.append(" >> 最终得分</p>" );
                            calculationFormula.append("Double: " + diseaseCoefficient + " = (" + originDiseaseWeight + "-" + weight4KeyConcSymp + ") / " + originDiseaseWeight);
                            calculationFormula.append("</br>");
                            calculationFormula.append("Weight: " + diseaseWeight + " = " + originDiseaseWeight + " * " + diseaseCoefficient);
                        }
                    }
                }
                UserDiagnosisOutcome udo = new UserDiagnosisOutcome();
                udo.setDiagnosisId(diagnosisId);
                udo.setDiseaseCode(disease.getDiseaseCode());
                udo.setDiseaseName(disease.getDiseaseName());
                udo.setDescription(disease.getDefinition());
                udo.setAnswerSpec(checkSpec(disease.getDiseaseCode(), forwardDiseaseCodeSet, nothingDiseaseCodeSet, reverseDiseaseCodeSet));
                udo.setWeight(diseaseWeight);
                udo.setCalculationFormula(calculationFormula.toString());
                userDiagnosisOutcomes.add(udo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDiagnosisOutcomes;
    }

    @Override
    public BasicQuestionVo diagnosisOutcomeView(Long diagnosisId) {
        LOGGER.info("生成诊断结果{}", diagnosisId);
        UserBasicRecord userBasicRecord = userBasicRecordDao.findByDiagnosisId(diagnosisId);
        if (userBasicRecord == null) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        String mainSympCode = userBasicRecord.getMainSymptomCode();
        UserInfo userInfo = userInfoDao.queryByUserId(userBasicRecord.getUserId());

        List<UserDiagnosisOutcome> userDiagnosisOutcomes = this.diagnosisOutcome(diagnosisId, mainSympCode, userInfo);//计算疾病的权重

        userDiagnosisOutcomes = MedicineSortUtil.specUserDiagnosisOutcome(userDiagnosisOutcomes);//根据特异性重新计算权重

        MedicineSortUtil.sortUserDiagnosisOutcome(userDiagnosisOutcomes);//排序

        userDiagnosisOutcomes = calculateProbability(diagnosisId, userDiagnosisOutcomes);//计算发病概率,保存，返回5个结果

        BasicQuestionVo basicQuestionVo = convertOutcome(diagnosisId, userDiagnosisOutcomes);
        if (basicQuestionVo != null) {
            //生成诊断结果后初始化用户反馈信息
            return basicQuestionVo;
        } else {
            LOGGER.error("无法生成诊断结果，诊断号：{}", diagnosisId);
            throw new ServiceException(ResponseStatus.INVALID_VALUE, "抱歉，暂时无法生成诊断结果！");
        }
    }

    /**
     * 处理主症状下的所有问题
     *
     * @param mainSympCode
     * @return
     */
    private Map<String, Map<String, DiagnosisMainsympQuestion>> listAnswerCount(String mainSympCode) {
        long start = System.currentTimeMillis();
        List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listAnswerCount(mainSympCode);
        Map<String, Map<String, DiagnosisMainsympQuestion>> deiseaseQuestionMap = new HashMap<>();
        for (DiagnosisMainsympQuestion dmq : dmQuestions) {
            Map<String, DiagnosisMainsympQuestion> qMap = deiseaseQuestionMap.get(dmq.getDiseaseCode()) == null ? new HashMap<>() : deiseaseQuestionMap.get(dmq.getDiseaseCode());
            qMap.put(dmq.getQuestionCode(), dmq);
            deiseaseQuestionMap.put(dmq.getDiseaseCode(), qMap);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start));
        return deiseaseQuestionMap;
    }

    /**
     * 处理主症状下的所有问题
     *
     * @param mainSympCode
     * @return
     */
    private Map<String, Map<String, DiagnosisMainsympQuestion>> listConcSympCount(String mainSympCode) {
        List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listConcSymptomCount(mainSympCode);
        Map<String, Map<String, DiagnosisMainsympQuestion>> deiseaseQuestionMap = new HashMap<>();
        for (DiagnosisMainsympQuestion dmq : dmQuestions) {
           /* Map<String, DiagnosisMainsympQuestion> qMap = deiseaseQuestionMap.get(dmq.getDiseaseCode()) == null ? new HashMap<>() : deiseaseQuestionMap.get(dmq.getDiseaseCode());
            qMap.put(dmq.getQuestionCode(), dmq);
            deiseaseQuestionMap.put(dmq.getDiseaseCode(), qMap);*/

            String diseaseCode = dmq.getDiseaseCode();
            Map<String, DiagnosisMainsympQuestion> qMap = null;
            if(deiseaseQuestionMap.containsKey(diseaseCode)) {
                qMap = deiseaseQuestionMap.get(dmq.getDiseaseCode());
                qMap.put(dmq.getQuestionCode(), dmq);
            } else {
                qMap = new HashMap<>();
                qMap.put(dmq.getQuestionCode(), dmq);
                deiseaseQuestionMap.put(diseaseCode, qMap);
            }
        }
        return deiseaseQuestionMap;
    }

    /**
     * 计算正向特异性，反向特异性，-1是反向特异性，2、是正向特异性，1是正+反特异性 0是无特异性
     *
     * @param diseaseCode
     * @param forwardDiseaseCodeSet
     * @param nothingDiseaseCodeSet
     * @param reverseDiseaseCodeSet
     * @return
     */
    private int checkSpec(String diseaseCode, Set<String> forwardDiseaseCodeSet, Set<String> nothingDiseaseCodeSet, Set<String> reverseDiseaseCodeSet) {
        int spec = 0;
        if (forwardDiseaseCodeSet.contains(diseaseCode)) {
            spec = spec + 2;
        }
        if (reverseDiseaseCodeSet.contains(diseaseCode)) {
            spec = spec + -1;
        }
        return spec;
    }

    /**
     * 计算发病概率，目前使用随机数,
     * 保存，返回5个结果
     *
     * @param userDiagnosisOutcomes
     * @return
     */
    private List<UserDiagnosisOutcome> calculateProbability(Long diagnosisId, List<UserDiagnosisOutcome> userDiagnosisOutcomes) {
        try {
            //插入前先删除
            userDiagnosisOutcomeDao.deleteByDiagnosisId(diagnosisId);

            Double maxProbability = ServiceUtil.getTempProbability();
            for (UserDiagnosisOutcome udo : userDiagnosisOutcomes) {
                Double probability = ServiceUtil.getTempProbability(maxProbability, userDiagnosisOutcomes.get(0).getWeight(), udo.getWeight());
                udo.setProbability(probability);
                userDiagnosisOutcomeDao.insert(udo);
            }
            if (userDiagnosisOutcomes.size() >= 5) {
                return userDiagnosisOutcomes.subList(0, 5);
            }
            return userDiagnosisOutcomes;
        } catch (Exception e) {
            e.printStackTrace();
            return userDiagnosisOutcomes;
        }
    }

    /**
     * @param diagnosisId
     * @param userDiagnosisOutcomes
     * @return
     */
    private BasicQuestionVo convertOutcome(Long diagnosisId, List<UserDiagnosisOutcome> userDiagnosisOutcomes) {
        List<IAnswerVo> outcomeVos = DiagnosisOutcomeVo.convertDiagnosisOutcomeVo(userDiagnosisOutcomes);
        if (outcomeVos != null && outcomeVos.size() > 0) {
            BasicQuestionVo basicQuestionVo = new BasicQuestionVo();
            basicQuestionVo.setType(QuestionEnum.诊断结果.getValue());
            basicQuestionVo.setDiagnosisId(diagnosisId);
            basicQuestionVo.setQuestionTitle("在您的协助下，阿尔法医生问诊机器人模拟诊断结果如下：（请选择疾病）");
            basicQuestionVo.setTitle("在您的协助下，阿尔法医生问诊机器人模拟诊断结果如下：（请选择疾病）");
            basicQuestionVo.setAnswers(outcomeVos);
            return basicQuestionVo;
        }
        return null;
    }
}

