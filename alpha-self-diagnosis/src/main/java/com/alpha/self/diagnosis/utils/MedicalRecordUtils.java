package com.alpha.self.diagnosis.utils;

import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.pojo.enums.ConstructMainSympType;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.self.diagnosis.pojo.enums.MultiMainSymptomConditionEnum;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class MedicalRecordUtils {

    /**
     * 多主症状排序
     * @param diagnosisMainSymptomsList
     * @param userDiagnosisDetailList
     * @return
     */
    public static List<DiagnosisMainSymptoms> sortMultiMainSymptoms(List<DiagnosisMainSymptoms> diagnosisMainSymptomsList, List<UserDiagnosisDetail> userDiagnosisDetailList) {
        //主症状编码与构成主诉的问题编码的关系
        Map<MainSymptomEnum, MultiMainSymptomConditionEnum> map = diagnosisMainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympCode).map(MultiMainSymptomConditionEnum::findByMainSymptomCode)
                .collect(toMap(MultiMainSymptomConditionEnum::getMainSymptomEnum, Function.identity()));
        //按病程构成主症状
        Set<String> constructTypeSet = map.values().stream().map(MultiMainSymptomConditionEnum::getConstructType).collect(toSet());
        if (constructTypeSet.size() == 1 && constructTypeSet.contains(ConstructMainSympType.病程.getCode())) {
            return sortMainSymptomsByPeriodDesc(diagnosisMainSymptomsList, userDiagnosisDetailList);
        }
        //按病程和次数构成主症状
        else if (constructTypeSet.contains(ConstructMainSympType.病程.getCode()) && constructTypeSet.contains(ConstructMainSympType.次数.getCode())) {
            return sortMainSymptomsByPeriodAndTimes(diagnosisMainSymptomsList, userDiagnosisDetailList);
        }
        return diagnosisMainSymptomsList;
    }

    /**
     * 多主症状根据病程倒序排列
     * @param diagnosisMainSymptomsList
     * @param userDiagnosisDetailList
     * @return
     */
    private static List<DiagnosisMainSymptoms> sortMainSymptomsByPeriodDesc(List<DiagnosisMainSymptoms> diagnosisMainSymptomsList, List<UserDiagnosisDetail> userDiagnosisDetailList) {
        String currentMainSympCode = userDiagnosisDetailList.stream().filter(e->e.getQuestionType().intValue() == QuestionEnum.主症状.getValue()).map(UserDiagnosisDetail::getSympCode).findFirst().get();
        //主症状编码与病程编码的关系
        Map<MainSymptomEnum, String> map = diagnosisMainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympCode).map(MultiMainSymptomConditionEnum::findByMainSymptomCode)
                .collect(toMap(MultiMainSymptomConditionEnum::getMainSymptomEnum, MultiMainSymptomConditionEnum::getQuestionCode));
        //从问诊记录获取多主症状的具体病程，value为具体的病程
        Map<MainSymptomEnum, String> periodMap = new HashMap<>();
        for(Map.Entry<MainSymptomEnum, String> item : map.entrySet()) {
            String mainSympCode = item.getKey().getValue();
            String questionCode = item.getValue();
            Optional<UserDiagnosisDetail> udd = userDiagnosisDetailList.stream().filter(e->e.getSympCode().equals(mainSympCode) && e.getQuestionCode().equals(questionCode)).findFirst();
            udd.ifPresent(e->periodMap.put(item.getKey(), e.getAnswerContent()));
        }
        //多主症状根据病程倒序排列
        for(Map.Entry<MainSymptomEnum, String> item : periodMap.entrySet()) {
            String period = item.getValue();
            Double periodTime = DateUtils.toMillSeond(period);
            for (DiagnosisMainSymptoms dms : diagnosisMainSymptomsList) {
                if (dms.getSympCode().equals(item.getKey().getValue())) {
                    dms.setIllPeriod(periodTime);
                }
            }
        }
        //多主症状的病程是否一致,如果一致则将主症状放前面，弱主症状放后面
        long illPeriodCount = diagnosisMainSymptomsList.stream().map(DiagnosisMainSymptoms::getIllPeriod).distinct().count();
        if (illPeriodCount == 1) {
            //表示病程一致
            DiagnosisMainSymptoms mainSymptoms = diagnosisMainSymptomsList.stream().filter(e -> e.getSympCode().equals(currentMainSympCode)).findAny().get();
            List<DiagnosisMainSymptoms> otherMainSymptoms = diagnosisMainSymptomsList.stream().filter(e -> !e.getSympCode().equals(currentMainSympCode)).collect(toList());
            diagnosisMainSymptomsList.clear();
            diagnosisMainSymptomsList.add(mainSymptoms);
            diagnosisMainSymptomsList.addAll(otherMainSymptoms);
            return diagnosisMainSymptomsList;
        }
        //如果多主症状病程不一致，则按病程倒序排列
        diagnosisMainSymptomsList = diagnosisMainSymptomsList.stream().filter(e->e.getIllPeriod() != null).sorted(Comparator.comparing(DiagnosisMainSymptoms::getIllPeriod, Comparator.reverseOrder())).collect(toList());
        return diagnosisMainSymptomsList;
    }

    /**
     * 多主症状根据病程和次数排序
     * @param diagnosisMainSymptomsList
     * @param userDiagnosisDetailList
     * @return
     */
    private static List<DiagnosisMainSymptoms> sortMainSymptomsByPeriodAndTimes(List<DiagnosisMainSymptoms> diagnosisMainSymptomsList, List<UserDiagnosisDetail> userDiagnosisDetailList) {
        //主症状编码与构成主诉的问题编码的关系
        Map<MainSymptomEnum, MultiMainSymptomConditionEnum> map = diagnosisMainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympCode).map(MultiMainSymptomConditionEnum::findByMainSymptomCode)
                .collect(toMap(MultiMainSymptomConditionEnum::getMainSymptomEnum, Function.identity()));

        DiagnosisMainSymptoms firstMainSymptoms = null;
        List<DiagnosisMainSymptoms> otherMainSympList = new ArrayList<>();
        for(Map.Entry<MainSymptomEnum, MultiMainSymptomConditionEnum> item : map.entrySet()) {
            MultiMainSymptomConditionEnum conditionEnum = item.getValue();
            //构成主诉的问题类型，1病程 2次数
            String constructType = conditionEnum.getConstructType();
            DiagnosisMainSymptoms mainSymptoms = diagnosisMainSymptomsList.stream().filter(e -> e.getSympCode().equals(conditionEnum.getMainSymptomEnum().getValue())).findFirst().get();
            if (ConstructMainSympType.病程.getCode().equals(constructType)) {
                firstMainSymptoms =mainSymptoms;
            } else if (ConstructMainSympType.次数.getCode().equals(constructType)) {
                otherMainSympList.add(mainSymptoms);
            }
        }
        diagnosisMainSymptomsList.clear();
        diagnosisMainSymptomsList.add(firstMainSymptoms);
        diagnosisMainSymptomsList.addAll(otherMainSympList);
        return diagnosisMainSymptomsList;
    }

}
