package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template66 extends ChildrenTemplate {

    /**
     * 3428	部位
     * 3429	疼痛性质
     * 3430	持续时间
     * 3431	起病急缓
     * 3432	发作特点
     * 3433	影响因素
     * 3434	致病相关因素
     * 3602	伴随症状
     * 3606	病程（天）
     * 3633	发病年龄（月）
     * 3651	发病季节
     * 3651	发病季节
     * 3674	常见伴随症状
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template66() {}

    public Template66(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template66(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.腹痛;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3606");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3606"));

        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3434")) || "不清楚".equals(question.get("3434"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(question.get("3428"));
            strBuff.append(question.get("3430"));
            strBuff.append("疼痛，");
        } else {
            strBuff.append("出现");
            strBuff.append(question.get("3428"));
            strBuff.append(question.get("3430"));
            strBuff.append("疼痛，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3434"));
            strBuff.append("。");
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuf = new StringBuffer();
        if (isMainComplaint) {
            strBuf.append(question.get("3606")).append("前出现");
        } else {
            strBuf.append("伴");
        }
        strBuf.append("腹痛").append("，");
        if (!GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3428"))) {
            strBuf.append(question.get("3428")).append("疼痛较为明显").append("。");
        }
        return strBuf.toString();
    }
}
