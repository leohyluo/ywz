package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Template91 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template91() {}

    public Template91(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template91(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.口腔疱疹;
    }

    @Override
    public String getSymptomName() {
        return getMainSymptom().getText() +  question.get("3685");
    }

    /**
     * 获取口腔泡疹现病史病历模板
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3685"));
        strBuff.append("前");
        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3718")) || "不清楚".equals(question.get("3718"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText()).append("，");
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3718")).append("，");
        }
        String part = question.get("3694");
        if(StringUtils.isNotEmpty(part)) {
            Set<String> mouthSet = new HashSet<>();
            mouthSet.add("舌");
            mouthSet.add("硬腭");
            mouthSet.add("咽部");
            mouthSet.add("牙龈");
            mouthSet.add("软腭");
            mouthSet.add("唇内侧");
            mouthSet.add("悬雍垂");
            mouthSet.add("颊粘膜");
            mouthSet.add("扁桃体");

            Set<String> allSet = new HashSet<>();
            allSet.add("口腔");
            allSet.add("手掌");
            allSet.add("足部");
            allSet.add("臀部");

            Set<String> answerSet = new HashSet<>();
            String[] parts = part.split("、");
            for(String answer : parts) {
                if (mouthSet.contains(answer)) {
                    answerSet.add("口腔");
                } else {
                    answerSet.add(answer);
                }
            }
            String answerStr = answerSet.stream().collect(Collectors.joining("、"));
            strBuff.append("主要分布在");
            strBuff.append(answerStr);
            strBuff.append("，");
            String answerStr2 = allSet.stream().filter(e->!answerSet.contains(e)).collect(Collectors.joining("、"));
            if (answerStr2 != null && !"".equals(answerStr2)) {
                strBuff.append(answerStr2).append("无疱疹，");
            }
        }
        String result = strBuff.toString();
        return result;
    }

}
