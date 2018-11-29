package com.alpha.self.diagnosis.utils.template;

import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template111 extends ChildrenTemplate {

    /**
     3859	年龄
     3860	病程
     3861	流涕是哪侧？
     3862	流涕性质
     3863	发病诱因
     3864	伴随症状
     3865	常见伴随症状
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template111() {}

    public Template111(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template111(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.流涕;
    }

    @Override
    public String getSymptomName() {
        String sympName = getMainSymptom().getText() + question.get("3860");
        return sympName;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3860")).append("前出现流涕").append("，");
        if (!"双侧".equals(question.get("3861"))) {
            strBuff.append(question.get("3861")).append("较为明显").append("，");
        }
        if (!"不清楚".equals(question.get("3862"))) {
            strBuff.append("呈").append(question.get("3862")).append("，");
        }
        if (!"不清楚".equals(question.get("3863"))) {
            strBuff.append("发病前曾").append(question.get("3863")).append("，");
        }
        return strBuff.toString();
    }

    /**
     * 常见伴随症状引申问题
     * @return
     */
    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuff = new StringBuffer();
        if(isMainComplaint) {
            strBuff.append(question.get("3860")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        if (!"不清楚".equals(question.get("3862"))) {
            strBuff.append(question.get("3862")).append("。");
        } else {
            strBuff.append("流涕。");
        }
        return strBuff.toString();
    }
}
