package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template55 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template55() {}

    public Template55(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template55(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.发热;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3327");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3327"));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3331")) || "不清楚".equals(question.get("3331"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText());
            strBuff.append("，");
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3331"));
            strBuff.append("。");
        }
        String currentTemperature = question.get("2001");
        String max = question.get("3328");
        if(!GlobalConstants.TEMPERATURE_UNKNOWN.equals(currentTemperature)) {
            //取最大值为热峰的值
            String min = "";
            String s1 = question.get("3328").substring(0, question.get("3328").indexOf("℃"));
            String s2 = question.get("2001").substring(0, question.get("2001").indexOf("℃"));
            if(Double.valueOf(s1) > Double.valueOf(s2)){
                min = question.get("2001");
            }else{
                min = question.get("3328");
                max = question.get("2001");
            }
            //如果现在大于最高的则不显示现在的
            if(Double.valueOf(s2) < Double.valueOf(s1)){
                strBuff.append("目前");
                strBuff.append(min).append("，");
            }
            strBuff.append("热峰为");
            strBuff.append(max);
        } else {
            strBuff.append("目前体温未测量，");
            strBuff.append("热峰为");
            strBuff.append(max);
        }

        strBuff.append("，");
		/*strBuff.append(持续发热");
        strBuff.append(question.get("3623"));
        strBuff.append("，");*/

        String result = strBuff.toString();
        return result;
    }

    @Override
     public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuff = new StringBuffer();
        if(isMainComplaint) {
            strBuff.append(question.get("3327")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        strBuff.append("发热").append("，");
        strBuff.append("热峰").append(question.get("3328")).append("，");
        return strBuff.toString();
    }
}
