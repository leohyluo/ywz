package com.alpha.self.diagnosis.utils.template;

import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template105 extends ChildrenTemplate {

    /**
     3811	年龄
     3812	病程
     3813	声嘶的程度
     3818	加重的因素
     3821	发病诱因
     3819	常见伴随症状（可多选）
     3820	伴随症状（可多选）
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template105() {}

    public Template105(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template105(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.声音嘶哑;
    }

    @Override
    public String getSymptomName() {
        String sympName = "声音嘶哑" + question.get("3812");
        return sympName;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3812")).append("前出现声音嘶哑").append("，");
        if (!"不清楚".equals(question.get("3813"))) {
            strBuff.append(question.get("3813")).append("，");
        }
        if (!"不清楚".equals(question.get("3818"))) {
            strBuff.append(question.get("3818")).append("，");
        }
        if (!"不清楚".equals(question.get("3821"))) {
            strBuff.append("发病前曾有").append(question.get("3821")).append("，");
        }
        return strBuff.toString();
    }
}
