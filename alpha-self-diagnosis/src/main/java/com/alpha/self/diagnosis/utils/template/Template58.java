package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template58 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private UserDiagnosisOutcome firstDiagnosis;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template58() {}

    public Template58(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template58(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.体重下降;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3621");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3621"));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3404")) || "不清楚".equals(question.get("3404"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText()).append("，");
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3404")).append("，");
        }
        strBuff.append("近段时间");
        strBuff.append(question.get("3405")).append("，");

        String result = strBuff.toString();
        return result;
    }
}
