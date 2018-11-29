package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template89 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template89() {}

    public Template89(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template89(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.便秘;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3687");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3687"));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3691")) || "不清楚".equals(question.get("3691"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText()).append("，");
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3691")).append("，");
        }
        strBuff.append("特点为");
        strBuff.append(question.get("3689"));
        strBuff.append("，");
        strBuff.append("大便呈");
        strBuff.append(question.get("3690"));
        strBuff.append("，");
        strBuff.append("平时");
        strBuff.append(question.get("3688"));
        strBuff.append("，");
        strBuff.append("约");
        strBuff.append(question.get("3693"));
        strBuff.append("解一次大便，");
        String result = strBuff.toString();
        return result;
    }
}
