package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template62 extends ChildrenTemplate {

    /**
     * 3582	起病急缓
     * 3583	程度
     * 3584	疼痛的加重因素
     * 3585	诱因
     * 3613	病程（天）
     * 3614	伴随症状
     * 3629	年龄
     * 3647	发病季节
     * 3670	常见伴随症状
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template62() {}

    public Template62(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template62(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.咽痛;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3613");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3613"));
        strBuff.append("前出现咽痛").append("，");
        strBuff.append(question.get("3583")).append("，");
        if (!"不清楚".equals(question.get("3584"))) {
            strBuff.append(question.get("3584")).append("加重").append("，");
        }
        if (!"不清楚".equals(question.get("3585"))) {
            strBuff.append("发病前曾").append(question.get("3585")).append("，");
        }
        String result = strBuff.toString();
        return result;
    }
}
