package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template61 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template61() {}

    public Template61(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template61(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.呼吸困难;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3615");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3615"));

        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3376")) || "不清楚".equals(question.get("3376"))) {
            strBuff.append("无明显诱因");
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText());
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText());
            strBuff.append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3376"));
        }

        strBuff.append("，");
        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3375")) || "不清楚".equals(question.get("3375"))) {

        } else {
            strBuff.append("表现为");
            strBuff.append(question.get("3375"));
            strBuff.append("，");
        }

        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3374")) || "不清楚".equals(question.get("3374"))) {

        } else {
            strBuff.append(question.get("3374"));
            strBuff.append("，");
        }

        String result = strBuff.toString();
        return result;
    }
}
