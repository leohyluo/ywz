package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.VaccinationHistory;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.ConstructMainSympType;
import com.alpha.self.diagnosis.pojo.enums.MultiMainSymptomConditionEnum;
import com.alpha.self.diagnosis.service.MedicalHistoryService;
import com.alpha.self.diagnosis.utils.MedicineTemplateFactory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 儿科抽象模板
 */
public abstract class ChildrenTemplate implements Template {

    private Long diagnosisId;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;
    private Map<String, String> question;

    public ChildrenTemplate() {}

    public ChildrenTemplate(UserBasicRecord userBasicRecord, Map<String, String> question) {
        this.diagnosisId = userBasicRecord.getDiagnosisId();
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public ChildrenTemplate(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        this.diagnosisId = userBasicRecord.getDiagnosisId();
        this.question = question;
        this.userBasicRecord = userBasicRecord;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public String getMultiSymptomName() {
        List<Template> templateObjectList = null;
        List<DiagnosisMainSymptoms> multiMainSymptomsList = new ArrayList<>();
        try {
            templateObjectList = MedicineTemplateFactory.getMultiMainSymptom(this.diagnosisId, this.question, multiMainSymptomsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sympNames = "";
        //病程一致返回“咳嗽伴发热1天”，不一致则返回“咳嗽2天发热1天”
        if(CollectionUtils.isNotEmpty(multiMainSymptomsList) && multiMainSymptomsList.size() > 1) {
            Set<String> contructTypeSet = multiMainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympCode).map(MultiMainSymptomConditionEnum::findByMainSymptomCode)
                    .map(MultiMainSymptomConditionEnum::getConstructType).collect(toSet());
            //两个主症状根据病程构成多主诉
            if (contructTypeSet.size() == 1 && contructTypeSet.contains(ConstructMainSympType.病程.getCode())) {
                //illPeriodSize = 1表示病程一致
                long illPeriodSize = multiMainSymptomsList.stream().map(DiagnosisMainSymptoms::getIllPeriod).distinct().count();
                if (illPeriodSize == 1) {
                    String mainSympCode = userBasicRecord.getMainSymptomCode();
                    MultiMainSymptomConditionEnum multiMainSymptomConditionEnum = MultiMainSymptomConditionEnum.findByMainSymptomCode(mainSympCode);
                    if (multiMainSymptomConditionEnum != null) {
                        String questionCodeOfIllPeriod = multiMainSymptomConditionEnum.getQuestionCode();
                        sympNames = multiMainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympName).collect(joining("伴")) + question.get(questionCodeOfIllPeriod);
                        return sympNames;
                    }
                }
            }
        }
        //多主症状的病程
        sympNames = templateObjectList.stream().map(Template::getSymptomName).collect(joining("，伴"));
        return sympNames;
    }

    /**
     * 获取问诊过程
     * @return
     */
    public abstract String getDiagnosisProcess();

    /**
     * 获取现病史
     * @return
     */
    @Override
    public String getPresenetIllHistory() {
        /*
        多主症状
        case1:主症状病程 > 常见伴随症状病程
        主症状现病史 + 常见伴随症状引申（构成多主症状） + 常见伴随症状引申（不构成多主症状） + 常见伴随症状（无引申问题）、伴随症状、阴性伴随症状
        case2:  常见伴随症状病程 > 主症状病程
        常见伴随症状引申（构成多主症状）+ 主症状现病史 + 常见伴随症状引申（不构成多主症状） + 常见伴随症状（无引申问题）、伴随症状、阴性伴随症状
        case3: 单主症状
        主症状现病史 + 常见伴随症状引申（不构成多主症状） + 常见伴随症状（无引申问题）、伴随症状、阴性伴随症状
        */

        StringBuffer strBuf = new StringBuffer();
        //按病程排序后的多主症状
        List<Template> multiMainSymptomList = null;
        List<DiagnosisMainSymptoms> mainSymptomsList = new ArrayList<>();
        try {
            multiMainSymptomList = MedicineTemplateFactory.getMultiMainSymptom(this.diagnosisId, this.question, mainSymptomsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        strBuf.append("患儿");
        //如果存在多主症状
        List<DiagnosisMainSymptoms> constructMainSympList = new ArrayList<>();
        if (multiMainSymptomList.size() > 1) {
            long illPeriodSize = mainSymptomsList.stream().map(DiagnosisMainSymptoms::getIllPeriod).distinct().count();
            //已构成主诉的常见伴随症状
            constructMainSympList = mainSymptomsList.stream().filter(e->!e.getSympCode().equals(userBasicRecord.getMainSymptomCode())).collect(toList());

            //主症状病程 >= 常见伴随症状病程
            if (multiMainSymptomList.get(0).getMainSymptom().getValue().equals(userBasicRecord.getMainSymptomCode())) {
                //主症状现病史 + 常见伴随症状引申（构成多主症状） + 常见伴随症状引申（不构成多主症状） + 常见伴随症状（无引申问题）、伴随症状、阴性伴随症状
                String diagnosisProcess = getDiagnosisProcess();    //现病史
                strBuf.append(diagnosisProcess);
                //构成多主症状的常见伴随症状引申问题
                for (int i = 1; i < multiMainSymptomList.size(); i++) {
                    Template template = multiMainSymptomList.get(i);
                    String itemExtQuestionText;
                    //illPeriodSize表示主症状病程 = 常见伴随症状病程
                    if (illPeriodSize == 1) {
                        itemExtQuestionText = template.getExtQuestionTextOfCommonConcSymp(false);
                    } else {
                        itemExtQuestionText = template.getExtQuestionTextOfCommonConcSymp(true);
                    }
                    if (StringUtils.isNotEmpty(itemExtQuestionText)) {
                        strBuf.append(itemExtQuestionText);
                    }
                }

                //问题过程中有引申问题的常见伴随症状集合(不包括已构成主诉的常见伴随症状)
                try {
                    List<Template> commonSymptomsWithExtQuestion = MedicineTemplateFactory.getRepliedCommonConcSympWithoutMultiMainSymptom(diagnosisId, question, constructMainSympList);
                    //不会构成多主症状的常见伴随症状引申问题
                    for (Template template : commonSymptomsWithExtQuestion) {
                        String itemExtQuestionText = template.getExtQuestionTextOfCommonConcSymp(false);
                        if (StringUtils.isNotEmpty(itemExtQuestionText)) {
                            strBuf.append(itemExtQuestionText);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //常见伴随症状 + 伴随症状 + 阴性伴随症状
                String concSymptom = getConcSymptomWithoutExtQuestion(AppType.CHILD, diagnosisId, diagnosisOutcomeList);
                strBuf.append(concSymptom);
            } else {    //常见伴随症状(构成主症状)病程 > 主症状病程

                //常见伴随症状引申（构成多主症状）+ 主症状现病史 + 常见伴随症状引申（不构成多主症状） + 常见伴随症状（无引申问题）、伴随症状、阴性伴随症状
                //常见伴随症状引申(构成主症状)
                for (Template template : multiMainSymptomList) {
                    if (!template.getMainSymptom().getValue().equals(getMainSymptom().getValue())) {
                        String itemExtQuestionText = template.getExtQuestionTextOfCommonConcSymp(true);
                        if (StringUtils.isNotEmpty(itemExtQuestionText)) {
                            strBuf.append(itemExtQuestionText);
                        }
                    }
                }
                String diagnosisProcess = getDiagnosisProcess();    //现病史
                strBuf.append(diagnosisProcess);
                //问题过程中有引申问题的常见伴随症状集合(不包括会构成主诉的常见伴随症状)
                try {
                    List<Template> commonSymptomsWithExtQuestion = MedicineTemplateFactory.getRepliedCommonConcSympWithoutMultiMainSymptom(diagnosisId, question, constructMainSympList);
                    //不会构成多主症状的常见伴随症状引申问题
                    for (Template template : commonSymptomsWithExtQuestion) {
                        String itemExtQuestionText = template.getExtQuestionTextOfCommonConcSymp(false);
                        if (StringUtils.isNotEmpty(itemExtQuestionText)) {
                            strBuf.append(itemExtQuestionText);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //常见伴随症状 + 伴随症状 + 阴性伴随症状
                String concSymptom = getConcSymptomWithoutExtQuestion(AppType.CHILD, diagnosisId, diagnosisOutcomeList);
                strBuf.append(concSymptom);
            }
        } else {
            //主症状现病史 + 常见伴随症状引申（不构成多主症状） + 常见伴随症状（无引申问题）、伴随症状、阴性伴随症状
            String diagnosisProcess = getDiagnosisProcess();    //现病史
            strBuf.append(diagnosisProcess);
            //问题过程中有引申问题的常见伴随症状集合(不包括会构成主诉的常见伴随症状)
            try {
                List<Template> commonSymptomsWithExtQuestion = MedicineTemplateFactory.getRepliedCommonConcSympWithoutMultiMainSymptom(diagnosisId, question, constructMainSympList);
                //不会构成多主症状的常见伴随症状引申问题
                for (Template template : commonSymptomsWithExtQuestion) {
                    String itemExtQuestionText = template.getExtQuestionTextOfCommonConcSymp(false);
                    if (StringUtils.isNotEmpty(itemExtQuestionText)) {
                        strBuf.append(itemExtQuestionText);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //常见伴随症状 + 伴随症状
            String concSymptom = getConcSymptomWithoutExtQuestion(AppType.CHILD, diagnosisId, diagnosisOutcomeList);
            strBuf.append(concSymptom);
        }
        String generalSymptom = getGeneralSymptom(userBasicRecord);
        String endText = "今为进一步诊治前来我院就诊。";
        strBuf.append(generalSymptom).append(endText);
        return strBuf.toString();
    }

    @Override
    public String getPastIllHistory(Function<String, String> allergicHistoryFunction) {
        String mainSympCode = userBasicRecord.getMainSymptomCode();
        MedicalHistoryService medicalHistoryService = SpringContextHolder.getBean("medicalHistoryServiceImpl");

        StringBuffer sb = new StringBuffer();
        if (StringUtils.isEmpty(userBasicRecord.getPastmedicalHistoryCode()) || "0".equals(userBasicRecord.getPastmedicalHistoryCode()) || "-1".equals(userBasicRecord.getPastmedicalHistoryCode())) {
            //选择“不清楚”
            if ("0".equals(userBasicRecord.getPastmedicalHistoryCode())) {
                sb.append("既往不详。");
            } else {
                sb.append("既往体健。");
            }
        } else {
            //第一诊断
            UserDiagnosisOutcome firstDiagnosis = CollectionUtils.isNotEmpty(diagnosisOutcomeList) ? diagnosisOutcomeList.get(0) : null;
            String systemCode = firstDiagnosis != null ? firstDiagnosis.getSystemCode() : null;
            List<DiagnosisPastmedicalHistory> pastmedicalHistoryList = medicalHistoryService.listPastmedicalHistoryByMainSympCodeAndSystemCode(mainSympCode, systemCode);
            String selectedPastmedicalHistoryText = userBasicRecord.getPastmedicalHistoryText();
            String noPastmedicalHistoryText = "";
            if (CollectionUtils.isNotEmpty(pastmedicalHistoryList)) {
                if(firstDiagnosis != null && StringUtils.isNotEmpty(firstDiagnosis.getSystemCode())) {
                    pastmedicalHistoryList = pastmedicalHistoryList.stream().filter(e->StringUtils.isNotEmpty(e.getSystemCode()) && e.getSystemCode().equals(firstDiagnosis.getSystemCode())).collect(toList());
                }
                Set<String> pastmedicalHistoryNameSet = pastmedicalHistoryList.stream().map(DiagnosisPastmedicalHistory::getDiseaseName).distinct().collect(toSet());
                Set<String> selectedPastmedicalHistoryNameSet = Stream.of(selectedPastmedicalHistoryText.split(",")).collect(toSet());
                noPastmedicalHistoryText = pastmedicalHistoryNameSet.stream().filter(e -> !selectedPastmedicalHistoryNameSet.contains(e)).collect(joining("、"));
            }
            sb.append("患者有").append("<span style=\"color:red\">").append(selectedPastmedicalHistoryText).append("</span>");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(noPastmedicalHistoryText)) {
                sb.append("，无").append(noPastmedicalHistoryText);
            }
            sb.append("。");
        }
        if (StringUtils.isEmpty(userBasicRecord.getAllergicHistoryCode()) || "0".equals(userBasicRecord.getAllergicHistoryCode())) {
            sb.append("药物过敏不详。");
        } else if (StringUtils.isNotEmpty(userBasicRecord.getAllergicHistoryCode()) && "-1".equals(userBasicRecord.getAllergicHistoryCode())) {
            sb.append("无药物过敏。");
        } else {
            if (allergicHistoryFunction == null) {
                sb.append("患者对").append(userBasicRecord.getAllergicHistoryText()).append("过敏").append("。");
            } else {
                String allergicHistoryText = allergicHistoryFunction.apply(userBasicRecord.getAllergicHistoryText());
                sb.append(allergicHistoryText);
            }
        }
        if (StringUtils.isNotEmpty(userBasicRecord.getMenarcheStatus())) {
            return sb.toString();
        }

        String vaccinationHistoryText = userBasicRecord.getVaccinationHistoryText();
        if (org.apache.commons.lang.StringUtils.isEmpty(vaccinationHistoryText)) {
            sb.append("无预防接种史");
        } else if (VaccinationHistory.INTIME.getText().equals(vaccinationHistoryText)) {
            sb.append("预防接种按时完成。");
        } else if (VaccinationHistory.UNTIMELY.getText().equals(vaccinationHistoryText)) {
            sb.append("预防接种未按时完成。");
        } else if (VaccinationHistory.UNDO.getText().equals(vaccinationHistoryText)) {
            sb.append("预防接种未进行。");
        } else if (VaccinationHistory.UNKNOWN.getText().equals(vaccinationHistoryText)) {
            sb.append("预防接种史不详。");
        }

        if (StringUtils.isNotEmpty(userBasicRecord.getFertilityType()) || StringUtils.isNotEmpty(userBasicRecord.getGestationalAge()) || org.apache.commons.lang.StringUtils.isNotEmpty(userBasicRecord.getFeedType())) {
            sb.append("患儿为");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(userBasicRecord.getGestationalAge()) && !GlobalConstants.UNKNOWN_ANSWER.equals(userBasicRecord.getGestationalAge())) {
                if ("37-42周".equals(userBasicRecord.getGestationalAge())) {
                    sb.append("足月儿，");
                } else if ("＜37周".equals(userBasicRecord.getGestationalAge()) || "<37周".equals(userBasicRecord.getGestationalAge())) {
                    sb.append("早产儿，");
                } else if ("＞42周".equals(userBasicRecord.getGestationalAge()) || ">42周".equals(userBasicRecord.getGestationalAge())) {
                    sb.append("过期产儿，");
                }
            } else {
                sb.append("胎龄不详，");
            }
            if (StringUtils.isNotEmpty(userBasicRecord.getFertilityType()) && !GlobalConstants.UNKNOWN_ANSWER.equals(userBasicRecord.getFertilityType())) {
                sb.append(userBasicRecord.getFertilityType()).append("，");
            } else {
                sb.append("分娩方式不详，");
            }
            if (StringUtils.isNotEmpty(userBasicRecord.getFeedType()) && !GlobalConstants.UNKNOWN_ANSWER.equals(userBasicRecord.getFeedType())) {
                sb.append("出生后").append(userBasicRecord.getFeedType()).append("。");
            } else {
                sb.append("喂养方式不详。");
            }

        }
        if ("1".equals(userBasicRecord.getMenstrualPeriod())) {
            sb.append("目前处于月经期内。");
        }
        return sb.toString();
    }

    /**
     * 常见伴随症状引申问题
     * @return
     */
    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        return null;
    }

}
