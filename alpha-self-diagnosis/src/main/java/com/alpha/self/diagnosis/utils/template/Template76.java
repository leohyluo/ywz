package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;

import java.util.List;
import java.util.Map;

/**
 * 外阴瘙痒
 */
public class Template76 extends WomenTemplate {

    /**
     * 3522	范围
     * 3523	程度
     * 3524	发作特点
     * 3525	加重或缓解因素
     * 3526	致病相关因素
     * 3733	病程
     * 3734	伴随症状
     * 3751	年龄
     * 3752	常见伴随症状
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template76() {}

    public Template76(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template76(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.外阴瘙痒;
    }

    @Override
    public String getSymptomName() {
        String period = getPeriodOfDisease(question.get("3733"));
        return getMainSymptom().getText() + period;
    }

    /**
     * 获取现病史
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("约");
        strBuff.append(getPeriodOfDisease(question.get("3733")));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3526")) || "不清楚".equals(question.get("3526"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText()).append("，");
        } else {
            strBuff.append("出现").append(getMainSymptom().getText()).append("，");
            //strBuff.append("发病前曾有").append(question.get("3526")).append("，");
        }
        String yzcd = question.get("3523"); //严重程度
        if(StringUtils.isNotEmpty(yzcd) && !"不清楚".equals(yzcd)) {
            yzcd = StringUtils.removeBracketsChar(yzcd);
            strBuff.append(yzcd).append("，");
        }
        if(StringUtils.isNotEmpty(question.get("3525")) && !"不清楚".equals(question.get("3525"))) {
            strBuff.append(question.get("3525")).append("。");
        }
        /*strBuff.append("伴白带增多，");
        if(StringUtils.isNotEmpty(question.get("3525")) && !"不清楚".equals(question.get("3525"))) {
            strBuff.append("呈");
        }*/


        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuff = new StringBuffer();
        if(isMainComplaint) {
            strBuff.append(question.get("3733")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        strBuff.append("外阴瘙痒").append("，");
        if (!"不清楚".equals(question.get("3523"))) {
            String answerContent = StringUtils.removeBracketsChar(question.get("3523"));
            strBuff.append(answerContent).append("，");
        }
        if (!"不清楚".equals(question.get("3525"))) {
            strBuff.append(question.get("3525")).append("，");
        }
        return strBuff.toString();
    }
}
