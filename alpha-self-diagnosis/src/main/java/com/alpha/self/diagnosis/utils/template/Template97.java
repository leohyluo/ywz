package com.alpha.self.diagnosis.utils.template;

import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template97 extends ChildrenTemplate {

    /**
     3771	发病年龄？
     3772	鼻出血有多久了？
     3773	鼻出血是哪侧？
     3774	鼻出血是什么程度？
     3775	鼻出血是否反复发作？
     3776	发病诱因？
     3777	常见伴随症状？（可多选）
     3778	伴随症状？（可多选）
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template97() {}

    public Template97(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template97(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.鼻出血;
    }

    @Override
    public String getSymptomName() {
        String sympName = "鼻衄" + question.get("3772");
        return sympName;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3772")).append("前出现");
        strBuff.append(question.get("3773")).append("鼻衄").append("，");
        strBuff.append(question.get("3774")).append("，");
        if ("否".equals(question.get("3775"))) {
            strBuff.append("无");
        }
        strBuff.append("反复发作").append("，");
        if (!"不清楚".equals(question.get("3776"))) {
            strBuff.append("出血前曾有").append(question.get("3776")).append("，");
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
        strBuff.append("流");
        if (!"不清楚".equals(question.get("3862"))) {
            strBuff.append(question.get("3862")).append("，");
        }
        strBuff.append("涕。");
        return strBuff.toString();
    }
}
