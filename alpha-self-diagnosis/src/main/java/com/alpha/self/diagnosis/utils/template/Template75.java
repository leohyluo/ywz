package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

/**
 * 痛经
 */
public class Template75 extends WomenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template75() {}

    public Template75(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template75(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.痛经;
    }

    /**
     * 3735	年龄
     * 3736	病程
     * 3737	痛经特点
     * 3738	痛经的程度
     * 3739	致病相关因素
     * 3740	伴随症状
     * 3741	 月经史
     * 3852	常见伴随症状
     * 3853	疼痛加重或缓解的因素
     * 3855	疼痛的性质
     * 3857	痛经是从初潮后开始的吗？
     * @return
     */

    @Override
    public String getSymptomName() {
        return "经期腹痛" + getPeriodOfDisease(question.get("3736"));
    }

    /**
     * 获取现病史
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(getPeriodOfDisease(question.get("3736")));
        strBuff.append("前出现经期腹部");
        if(!question.get("3855").equals("不清楚")) {
            strBuff.append(question.get("3855")).append("，");
        } else {
            strBuff.append("疼痛").append("，");
        }
        if(!"不清楚".equals(question.get("3738"))) {
            String answerContent = StringUtils.removeBracketsChar(question.get("3738"));
            strBuff.append(answerContent).append("，");
        }
        if (!"不清楚".equals(question.get(question.get("3737")))) {
            strBuff.append(question.get("3737")).append("，");
        }
        if (!"不清楚".equals(question.get(question.get("3853")))) {
            strBuff.append(question.get("3853")).append("。");
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        return null;
    }
}
