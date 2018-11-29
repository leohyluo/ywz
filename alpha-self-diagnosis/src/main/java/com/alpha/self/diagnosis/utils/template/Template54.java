package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template54 extends ChildrenTemplate {

    /**
     * 3316	大便性质
     * 3317	发病年龄（月）
     * 3318	发病季节
     * 3319	病程(天）
     * 3320	大便次数
     * 3321	起病急慢
     * 3322	个人/集体发病
     * 3323	大便气味
     * 3324	大便颜色
     * 3325	发病诱因
     * 3338	伴随症状
     * 3663	常见伴随症状
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template54() {}

    public Template54(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template54(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.腹泻;
    }

    @Override
    public String getSymptomName() {
        String result = "腹泻" + question.get("3319");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3319"));
        strBuff.append("前");

        if (StringUtils.isEmpty(question.get("3325")) || "不清楚".equals(question.get("3325"))) {
            strBuff.append("无明显诱因出现腹泻");
            strBuff.append("，");
        } else {
            strBuff.append("出现腹泻").append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3325"));
            strBuff.append("。");
        }
        strBuff.append("大便为");
        strBuff.append(question.get("3324"));
        strBuff.append("、");
        strBuff.append(question.get("3316"));
        strBuff.append("，大概");
        strBuff.append(question.get("3320"));
        strBuff.append("/天，");

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuf = new StringBuffer();
        if (isMainComplaint) {
            strBuf.append(question.get("3319")).append("前出现");
        } else {
            strBuf.append("伴");
        }
        strBuf.append("腹泻").append("，");
        strBuf.append("约").append(question.get("3320")).append("/天").append("，");
        strBuf.append(question.get("3316")).append("。");
        return strBuf.toString();
    }
}
