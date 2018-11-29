package com.alpha.self.diagnosis.utils.template;

import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template102 extends ChildrenTemplate {

    /**
     * 3762	发病年龄？
     * 3766	病程
     * 3763	鼻塞是哪侧？
     * 3770	鼻塞程度
     * 3764	鼻塞特点
     * 3767	发病诱因？
     * 3768	常见伴随症状？（可多选）
     * 3769	伴随症状？
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template102() {}

    public Template102(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template102(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.鼻塞;
    }

    @Override
    public String getSymptomName() {
        String sympName = getMainSymptom().getText() + question.get("3766");
        return sympName;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3766")).append("前出现");
        if (!"不清楚".equals(question.get("3770"))) {
            strBuff.append(question.get("3770"));
        }
        strBuff.append("鼻塞").append("，");
        if (!"不清楚".equals(question.get("3763"))) {
            strBuff.append(question.get("3763")).append("较为明显").append("，");
        }
        if (!"不清楚".equals(question.get("3764"))) {
            strBuff.append("呈").append(question.get("3764")).append("，");
        }
        if (!"不清楚".equalsIgnoreCase(question.get("3767"))) {
            strBuff.append("发病前曾有").append(question.get("3767")).append("。");
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
            strBuff.append(question.get("3766")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        if (!"不清楚".equals(question.get("3770"))) {
            strBuff.append(question.get("3770"));
        }
        strBuff.append("鼻塞").append("，");
        if (!"不清楚".equals(question.get("3764"))) {
            strBuff.append("呈").append(question.get("3764")).append("，");
        }
        return strBuff.toString();
    }
}
