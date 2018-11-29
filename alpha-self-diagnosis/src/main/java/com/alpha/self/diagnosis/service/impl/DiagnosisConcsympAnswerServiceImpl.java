package com.alpha.self.diagnosis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympConcsympDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.MedicineDiagnosisService;
import com.alpha.self.diagnosis.service.SymptomAccompanyService;
import com.alpha.self.diagnosis.utils.MedicineSortUtil;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.SyCommonConcSymptom;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * 伴随症状答案业务处理类
 */
@Service
public class DiagnosisConcsympAnswerServiceImpl implements AnswerService {

    @Resource
    DiagnosisMainsympConcsympDao diagnosisMainsympConcsympDao;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private UserDiagnosisDetailDao userDiagnosisDetailDao;
    @Resource
    private MedicineDiagnosisService medicineDiagnosisService;

    @Resource(name = "diagnosisQuestionAnswerServiceImpl")
    private AnswerService diagnosisQuestionAnswerService;
    @Resource(name = "commonConcsympAnswerServiceImpl")
    private AnswerService commonConcsympAnswerService;
    @Resource
    private SymptomAccompanyService symptomAccompanyService;


    @Override
    public LinkedHashSet<DiagnosisMainsympConcsymp> get(Long diagnosisId, String mainSympCode, String QuestionCode, UserInfo userInfo) {
        List<DiagnosisMainsympConcsymp> dmcs = diagnosisMainsympConcsympDao.listDiagnosisMainsympConcsymp(mainSympCode);
        for (Iterator iterator = dmcs.iterator(); iterator.hasNext(); ) {
            DiagnosisMainsympConcsymp answer = (DiagnosisMainsympConcsymp) iterator.next();
            if (answer.getGender() != null && answer.getGender() > 0 && answer.getGender() != userInfo.getGender()) {
                iterator.remove();
                continue;//过滤性别
            }
            float age = DateUtils.getAge(userInfo.getBirth());
            if ((answer.getMinAge() != null && answer.getMinAge() > age) || (answer.getMaxAge() != null && answer.getMaxAge() < age)) {
                iterator.remove();
                continue;//过滤年龄
            }
        }
        LinkedHashSet<DiagnosisMainsympConcsymp> diagnosisMainsympConcsympLinkedHashSet = new LinkedHashSet<>(dmcs);
        return diagnosisMainsympConcsympLinkedHashSet;
    }

    @Override
    public LinkedHashSet<IAnswerVo> getAnswerView(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo) {
        LinkedHashSet<DiagnosisMainsympConcsymp> diagnosisMainsympConcsympLinkedHashSet = this.get(diagnosisId, mainSympCode, null, userInfo);
        List<DiagnosisMainsympConcsymp> dmcs = diagnosisMainsympConcsympLinkedHashSet.stream().collect(toList());
        Optional<DiagnosisMainsympConcsymp> optionalNone = dmcs.stream().filter(e->StringUtils.isNotEmpty(e.getSympName())).filter(e->e.getSympName().equals(GlobalConstants.NONE)).findFirst();
        DiagnosisMainsympConcsymp none = null;
        if(optionalNone.isPresent()) {
            none = optionalNone.get();
        }
        //将“都没有”从伴随症状中排掉
        dmcs = dmcs.stream().filter(e->!e.getSympName().equals(GlobalConstants.NONE)).collect(toList());
        //过滤常见伴随症状
        String questionCodeOfCommonSymp = null;
        List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listDiagnosisMainsympQuestion(mainSympCode);
        Optional<DiagnosisMainsympQuestion> questionOptional = dmQuestions.stream().filter(e->e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).findFirst();
        if(questionOptional.isPresent()) {
            questionCodeOfCommonSymp = questionOptional.get().getQuestionCode();
            String questionCode4NormalCocnSympCode = questionOptional.get().getQuestionCode();
            LinkedHashSet<DiagnosisMainsympConcsymp> normalCocnSympAnswersSet = commonConcsympAnswerService.get(diagnosisId, mainSympCode, questionCode4NormalCocnSympCode, userInfo);
            List<DiagnosisMainsympConcsymp> normalCocnSympList = normalCocnSympAnswersSet.stream().collect(toList());
            Set<String> normalCocnSympCodeSet = normalCocnSympList.stream().filter(e->!e.getSympName().equals(GlobalConstants.NONE)).map(DiagnosisMainsympConcsymp::getConcSympCode).distinct().collect(toSet());
            dmcs = dmcs.stream().filter(e->!normalCocnSympCodeSet.contains(e.getConcSympCode())).collect(toList());
        }
        //过滤常见伴随症状对应的同义词
        if (StringUtils.isNotEmpty(questionCodeOfCommonSymp)) {
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
                Set<String> synonymCodeSet = syCommonConcSymptomList.stream().map(SyCommonConcSymptom::getSympCode).collect(toSet());
                dmcs = dmcs.stream().filter(e->!synonymCodeSet.contains(e.getConcSympCode())).collect(toList());
            }
        }

        //过滤主症状
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sympCode", mainSympCode);
        List<DiagnosisMainSymptoms> mainSymptomsList = diagnosisMainSymptomsDao.query(paramMap);
        Set<String> mainSympNameSet = mainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympName).distinct().collect(toSet());
        dmcs = dmcs.stream().filter(e->!mainSympNameSet.contains(e.getSympName())).collect(toList());


        List<UserDiagnosisOutcome> userDiagnosisOutcomes = medicineDiagnosisService.diagnosisOutcome(diagnosisId, mainSympCode,userInfo);//计算疾病的权重
        userDiagnosisOutcomes = MedicineSortUtil.specUserDiagnosisOutcome(userDiagnosisOutcomes);//根据特异性重新计算权重
        MedicineSortUtil.sortUserDiagnosisOutcome(userDiagnosisOutcomes);//排序

        //依次取出疾病的伴随症状（共10个）
        List<String> diseaseCodeList = userDiagnosisOutcomes.stream().map(UserDiagnosisOutcome::getDiseaseCode).distinct().collect(toList());
        List<DiagnosisMainsympConcsymp> topConcsympList = new ArrayList<>();
        //如果诊断结果常见的伴随症状不足10个则用权重最高的来补满10个
        int concsympLen = 10;
        int remain = 0;
        //如果一问诊的过程没有产生疾病
        if(CollectionUtils.isEmpty(diseaseCodeList)) {
            //主症状常见伴随症状
            List<DiagnosisMainsympConcsymp> normalDmcs = diagnosisMainsympConcsympDao.listCommonDiagnosisMainsympConcsymp(mainSympCode);
            addCocnSymp(topConcsympList, normalDmcs);
            remain = concsympLen - normalDmcs.size();
            //如主症状的常见伴随症状不满10个则用主症状的非常见伴随症状补满10个
            if(remain > 0) {
                Set<String> normalDmcCodes = normalDmcs.stream().map(DiagnosisMainsympConcsymp::getConcSympCode).collect(toSet());
                List<DiagnosisMainsympConcsymp> unNormalDmcs = dmcs.stream().filter(e->!normalDmcCodes.contains(e.getConcSympCode())).limit(remain).collect(toList());
                addCocnSymp(topConcsympList, unNormalDmcs);
            }
        } else {
            //疾病对应的常见伴随症状排在前面
            Map<String, List<DiagnosisMainsympConcsymp>> concsympMap = dmcs.stream().collect(groupingBy(DiagnosisMainsympConcsymp::getDiseaseCode));
            for(String diseaseCode : diseaseCodeList) {
                List<DiagnosisMainsympConcsymp> diseaseConcsympList = concsympMap.get(diseaseCode);
                if(CollectionUtils.isNotEmpty(diseaseConcsympList)) {
                    //取出疾病常见的伴随症状
                    List<DiagnosisMainsympConcsymp> diseaseCommonConcsympList = diseaseConcsympList.stream().filter(e->e.getIsCommon() != null && e.getIsCommon() == 1)
                            .filter(e->!e.getSympName().equals(GlobalConstants.NONE)).collect(toList());
                    if(CollectionUtils.isNotEmpty(diseaseCommonConcsympList)) {
                        addCocnSymp(topConcsympList, diseaseCommonConcsympList);
                    }
                }
                if(topConcsympList.size() >= 10) {
                    break;
                }
            }
            //如果诊断结果常见的伴随症状不足10个则用权重最高的来补满10个
            remain = concsympLen - topConcsympList.size();
            if(remain > 0) {
                for(String diseaseCode : diseaseCodeList) {
                    List<DiagnosisMainsympConcsymp> diseaseConcsympList = concsympMap.get(diseaseCode);
                    if (CollectionUtils.isNotEmpty(diseaseConcsympList)) {
                        //取出疾病非常见的伴随症状
                        List<DiagnosisMainsympConcsymp> diseaseUnCommonConcsympList = diseaseConcsympList.stream().filter(e -> e.getIsCommon() == null || (e.getIsCommon() != null && e.getIsCommon() != 1)).collect(toList());
                        diseaseUnCommonConcsympList = diseaseUnCommonConcsympList.stream().filter(e -> e.getRate() != null).sorted(Comparator.comparing(DiagnosisMainsympConcsymp::getRate, Comparator.reverseOrder()))
                                .collect(toList());
                        addCocnSymp(topConcsympList, diseaseUnCommonConcsympList);
                        remain = concsympLen - topConcsympList.size();
                    }
                    if(remain <= 0) break;
                }
            }
            //疾病常见的伴随症状+非常见的伴随症状不够10个则用主症状下的伴随症状填充
            if (remain > 0) {
                //主症状常见的伴随症状
                List<DiagnosisMainsympConcsymp> normalDmcs = diagnosisMainsympConcsympDao.listCommonDiagnosisMainsympConcsymp(mainSympCode);
                addCocnSymp(topConcsympList, normalDmcs);
                remain = concsympLen - topConcsympList.size();
                //如主症状的常见伴随症状不满10个则用主症状的非常见伴随症状补满10个
                if(remain > 0) {
                    Set<String> normalDmcCodes = normalDmcs.stream().map(DiagnosisMainsympConcsymp::getConcSympCode).collect(toSet());
                    List<DiagnosisMainsympConcsymp> unNormalDmcs = dmcs.stream().filter(e->!normalDmcCodes.contains(e.getConcSympCode())).limit(remain).collect(toList());
                    addCocnSymp(topConcsympList, unNormalDmcs);
                }
            }
        }
        //modify by wfh 20180524 修改当主诉为“发热”时且常见伴随症状选择“咳嗽”时，还有哪些症状答案中不应出现“咳痰”答案项
        if(mainSympCode.equals("55")){
            topConcsympList = topConcsympList.stream().filter(e->!e.getSympName().equals("咳痰")).collect(toList());
        }
        if(none != null) {
            topConcsympList.add(none);
        }

        List<IAnswerVo> answers = new ArrayList<>();
        for (DiagnosisMainsympConcsymp dmc : topConcsympList) {
            BasicAnswerVo answer = new BasicAnswerVo(dmc);
            answers.add(answer);
        }
        return new LinkedHashSet<>(answers);
    }

    private void addCocnSymp(List<DiagnosisMainsympConcsymp> concsympList, List<DiagnosisMainsympConcsymp> newConcsympList) {
        for(DiagnosisMainsympConcsymp dmc : newConcsympList) {
            if(concsympList.size() < 10) {
                Optional<DiagnosisMainsympConcsymp> optional = concsympList.stream().filter(e->e.getConcSympCode().equals(dmc.getConcSympCode())).findAny();
                if(!optional.isPresent()) {
                    concsympList.add(dmc);
                }
            }
        }
    }

}
