package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

/**
 * 外阴疼痛
 */
public class Template80 extends WomenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template80() {}

    public Template80(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template80(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.外阴疼痛;
    }

    /**
     * 3838	年龄
     * 3858	病程
     * 3720	疼痛的性质
     * 3722	疼痛的加重或缓解因素
     * 3723	致病相关因素
     * 3856	常见伴随症状
     * 3724	伴随症状
     */

    @Override
    public String getSymptomName() {
        return getMainSymptom().getText() + getPeriodOfDisease(question.get("3858"));
    }

    /**
     * 获取现病史
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("约");
        strBuff.append(getPeriodOfDisease(question.get("3858")));
        strBuff.append("前");
        if(question.get("3723").equals("不清楚")) {
            strBuff.append("无明显诱因");
        }
        strBuff.append("出现").append(getMainSymptom().getText()).append("，");

        if(!"不清楚".equals(question.get("3720"))) {
            strBuff.append(question.get("3720")).append("，");
        }
        if (!"不清楚".equals(question.get(question.get("3722")))) {
            strBuff.append(question.get("3722")).append("。");
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        return null;
    }
}
