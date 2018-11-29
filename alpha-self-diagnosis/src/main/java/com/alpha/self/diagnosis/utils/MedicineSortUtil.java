package com.alpha.self.diagnosis.utils;

import com.alpha.self.diagnosis.pojo.vo.BasicAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisQuestionAnswer;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by xc.xiong on 2017/9/8.
 */
public class MedicineSortUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(MedicineSortUtil.class);

    /**
     * 计算答案权重，并排序
     *
     * @param dqAnswers
     */
    public static LinkedHashSet<IAnswerVo> sortAnswer(List<DiagnosisQuestionAnswer> dqAnswers, List<UserDiagnosisOutcome> userDiagnosisOutcomes) {
        LinkedHashSet<DiagnosisQuestionAnswer> answerLinkedHashSet = sortAndDistinctAnswer(dqAnswers, userDiagnosisOutcomes);
        LinkedHashSet<IAnswerVo> answerVos = new LinkedHashSet<>();
        for (DiagnosisQuestionAnswer dqAnswer : answerLinkedHashSet) {
            BasicAnswerVo answerVo = new BasicAnswerVo(dqAnswer);
            answerVos.add(answerVo);
        }
        return answerVos;
    }

    /**
     * 计算答案权重，并排序
     *
     * @param dqAnswers
     */
    public static LinkedHashSet<DiagnosisQuestionAnswer> sortAndDistinctAnswer(List<DiagnosisQuestionAnswer> dqAnswers, List<UserDiagnosisOutcome> userDiagnosisOutcomes) {
        userDiagnosisOutcomes = MedicineSortUtil.specUserDiagnosisOutcome(userDiagnosisOutcomes);//根据特异性重新计算权重
        MedicineSortUtil.sortUserDiagnosisOutcome(userDiagnosisOutcomes);//疾病排序
        UserDiagnosisOutcome udo = null;
        if (userDiagnosisOutcomes != null && userDiagnosisOutcomes.size() > 0) {
            udo = userDiagnosisOutcomes.get(0);
            LOGGER.info("首选疾病：{}", udo.getDiseaseName());
        }
        //计算问题权重
        //          1、查询类型为101的 的所有问题
        //2、取出所有的特异性疾病，分为三组，正、空、反
        //3、每组进行权重计算，问题权重+答案权重，正向排序；
        //4、过滤重复，返回答案
        //分特异性 正、空、反
        TreeMap<Integer, Set<DiagnosisQuestionAnswer>> answerSpecMap = new TreeMap<>();
        for (DiagnosisQuestionAnswer dqa : dqAnswers) {
            Set<DiagnosisQuestionAnswer> specSet = answerSpecMap.get(dqa.getAnswerSpec()) == null ? new HashSet<>() : answerSpecMap.get(dqa.getAnswerSpec());
            specSet.add(dqa);
            answerSpecMap.put(dqa.getAnswerSpec(), specSet);
        }
        //排序
        answerSpecMap = MedicineSortUtil.sortAnswerSpecMap(answerSpecMap);
        //计算权重
        for (Map.Entry<Integer, Set<DiagnosisQuestionAnswer>> entry : answerSpecMap.entrySet()) {
            Set<DiagnosisQuestionAnswer> dqAnswerSet = entry.getValue();
            for (DiagnosisQuestionAnswer dqAnswer : dqAnswerSet) {
                Double weight = dqAnswer.getWeight() == null ? 0.0d : dqAnswer.getWeight();
                dqAnswer.setWeight(weight);
                if (udo != null && StringUtils.isNotEmpty(dqAnswer.getDiseaseCode()) && StringUtils.isNotEmpty(udo.getDiseaseCode())
                        && dqAnswer.getDiseaseCode().equals(udo.getDiseaseCode())) {
                    dqAnswer.setWeightValue(weight + 10000);//首选疾病权重
                } else {
                    dqAnswer.setWeightValue(weight);
                }
            }
            //答案排序
            dqAnswerSet = MedicineSortUtil.sortAnswer(dqAnswerSet);
            answerSpecMap.put(entry.getKey(), dqAnswerSet);
        }
        //去重复
        LinkedHashSet<DiagnosisQuestionAnswer> answers = new LinkedHashSet<>();
        for (Map.Entry<Integer, Set<DiagnosisQuestionAnswer>> entry : answerSpecMap.entrySet()) {
            Set<DiagnosisQuestionAnswer> dqAnswerSet = entry.getValue();
            for(DiagnosisQuestionAnswer dqa : dqAnswerSet) {
                Set<String> answerCodeSet = answers.stream().map(DiagnosisQuestionAnswer::getAnswerCode).collect(Collectors.toSet());
                if(!answerCodeSet.contains(dqa.getAnswerCode())) {
                    answers.add(dqa);
                }
            }
        }
        return answers;
    }

    /**
     * 答案权重排序
     *
     * @param dqAnswerSet
     */
    public static Set<DiagnosisQuestionAnswer> sortAnswer(Set<DiagnosisQuestionAnswer> dqAnswerSet) {
        List<DiagnosisQuestionAnswer> dqAnswers = new ArrayList<>(dqAnswerSet);
        Collections.sort(dqAnswers, new Comparator<DiagnosisQuestionAnswer>() {
            @Override
            public int compare(DiagnosisQuestionAnswer o1, DiagnosisQuestionAnswer o2) {
                int difference = (int) (o2.getWeightValue() - o1.getWeightValue());
                if(difference==0){
                    return (int) (o2.getWeight() - o1.getWeight());
                }else {
                    return difference;
                }
            }
        });
        return new LinkedHashSet<>(dqAnswers);
    }
    
    /**
     * 答案 特异性培训
     *
     * @param answerSpecMap
     * @return
     */
    public static TreeMap sortAnswerSpecMap(TreeMap<Integer, Set<DiagnosisQuestionAnswer>> answerSpecMap) {
        //排序
        TreeMap<Integer, Set<DiagnosisQuestionAnswer>> sortMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        sortMap.putAll(answerSpecMap);
        return sortMap;
    }

    /**
     * 答案排序
     *
     * @param answerVos
     * @return
     */
    public static List<IAnswerVo> sortAnswerVo(LinkedHashSet<IAnswerVo> answerVos) {
        List<IAnswerVo> answers = new ArrayList<>(answerVos);
        Collections.sort(answers, new Comparator<IAnswerVo>() {
            @Override
            public int compare(IAnswerVo o1, IAnswerVo o2) {
                BasicAnswerVo answerVo1 = (BasicAnswerVo) o1;
                BasicAnswerVo answerVo2 = (BasicAnswerVo) o2;
                return (answerVo2.getDefaultOrder() - answerVo1.getDefaultOrder());
            }
        });
        return answers;
    }

    /**
     * 伴随症状排序
     *
     * @param dmcs
     * @return
     */
    public static void sortDiagnosisMainsympConcsymp(List<DiagnosisMainsympConcsymp> dmcs) {
        Collections.sort(dmcs, new Comparator<DiagnosisMainsympConcsymp>() {
            @Override
            public int compare(DiagnosisMainsympConcsymp o1, DiagnosisMainsympConcsymp o2) {
                return (int) ((o2.getSimilarity() - o1.getSimilarity()) * 100);
            }
        });
    }

    /**
     * 重新计算权重
     *
     * @param userDiagnosisOutcomes
     */
    public static List<UserDiagnosisOutcome> specUserDiagnosisOutcome(List<UserDiagnosisOutcome> userDiagnosisOutcomes) {
        sortUserDiagnosisOutcome(userDiagnosisOutcomes);
        TreeMap<Integer, List<UserDiagnosisOutcome>> udoMap = new TreeMap<>();
        for (UserDiagnosisOutcome udo : userDiagnosisOutcomes) {
            List<UserDiagnosisOutcome> udos = udoMap.get(udo.getAnswerSpec()) == null ? new ArrayList<>() : udoMap.get(udo.getAnswerSpec());
            udos.add(udo);
            udoMap.put(udo.getAnswerSpec(), udos);
        }
        //排序
        TreeMap<Integer, List<UserDiagnosisOutcome>> sortMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        sortMap.putAll(udoMap);
        List<UserDiagnosisOutcome> udos = new ArrayList<>();
        Iterator iterator = sortMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry1 = (Map.Entry) iterator.next();
            List<UserDiagnosisOutcome> val1 = (List<UserDiagnosisOutcome>) entry1.getValue();
            udos.addAll(val1);
            while (iterator.hasNext()) {
                Map.Entry entry2 = (Map.Entry) iterator.next();
                List<UserDiagnosisOutcome> val2 = (List<UserDiagnosisOutcome>) entry2.getValue();
                for (UserDiagnosisOutcome udo : val2) {
                    udo.setWeight(udo.getWeight() + val1.get(0).getWeight());
                }
                udos.addAll(val2);
            }
        }
        return udos;
    }

    /**
     * 根据权重排序
     *
     * @param userDiagnosisOutcomes
     */
    public static void sortUserDiagnosisOutcome(List<UserDiagnosisOutcome> userDiagnosisOutcomes) {
        try {
            Collections.sort(userDiagnosisOutcomes, new Comparator<UserDiagnosisOutcome>() {
                @Override
                public int compare(UserDiagnosisOutcome o1, UserDiagnosisOutcome o2) {
                    if (o1.getAnswerSpec() == o2.getAnswerSpec()) {
                        return (int) (o2.getWeight() - o1.getWeight());
                    } else {
                        return (o2.getAnswerSpec() - o1.getAnswerSpec());
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Double sortDouble(Double... similarity) {
        Double temp = 0d;
        int size = similarity.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                //交换两数位置
                if (similarity[j] < similarity[j + 1]) {
                    temp = similarity[j];
                    similarity[j] = similarity[j + 1];
                    similarity[j + 1] = temp;
                }
            }
        }
        return similarity[0];
    }
}
