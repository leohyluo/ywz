package com.alpha.self.diagnosis.utils.template;

import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template103 extends ChildrenTemplate {

    /**
     3779	年龄
     3783	病程
     3787	耳痛是哪侧
     3785	耳痛的部位
     3786	疼痛的性质
     3784	什么情况下耳痛会加重或减轻
     3780	发病诱因
     3781	常见伴随症状（可多选）
     3782	伴随症状（可多选）
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template103() {}

    public Template103(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template103(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.耳痛;
    }

    @Override
    public String getSymptomName() {
        String sympName = question.get("3787") + "耳痛" + question.get("3783");
        return sympName;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3783")).append("前出现");
        strBuff.append(question.get("3787")).append("耳部");
        if (!"不清楚".equals(question.get("3786"))) {
            strBuff.append(question.get("3786")).append("，");
        } else {
            strBuff.append("疼痛").append("，");
        }
        if (!"不清楚".equals(question.get("3780"))) {
            strBuff.append("发病前曾有").append(question.get("3780"));
        }
        return strBuff.toString();
    }
}
