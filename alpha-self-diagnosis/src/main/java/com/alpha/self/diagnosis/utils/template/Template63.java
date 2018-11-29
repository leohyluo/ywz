package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template63 extends ChildrenTemplate {

    /**
     * 3609	病程（天）
     * 3844	抽搐（最长一次）持续了多长时间？
     * 3845	抽搐时的具体表现有哪些？
     * 3847	抽搐的次数
     * 3671	常见伴随症状
     * 3455	部位
     * 3456	性质
     * 3457	频度
     * 3458	是否意识清醒
     * 3610	伴随症状
     * 3630	发病年龄（月）
     * 3648	发病季节
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template63() {}

    public Template63(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template63(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.抽搐;
    }

    @Override
    public String getSymptomName() {
        return getMainSymptom().getText() + question.get("3847");
    }

    @Override
    public String getDiagnosisProcess() {
        return null;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuff = new StringBuffer();
        if(isMainComplaint) {
            strBuff.append(question.get("3609")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        strBuff.append("抽搐").append("，");
        strBuff.append("共发作").append(question.get("3847")).append("，");
        strBuff.append("最长持续抽搐").append(question.get("3844")).append("，");
        if(!GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3845"))) {
            strBuff.append(question.get("3845")).append("，");
        }
        return strBuff.toString();
    }
}
