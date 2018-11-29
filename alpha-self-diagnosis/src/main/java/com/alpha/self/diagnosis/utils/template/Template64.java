package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template64 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template64() {}

    public Template64(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template64(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.头痛;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3608");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3608"));

        strBuff.append("前");
        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3388")) || "不清楚".equals(question.get("3388"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText());
            strBuff.append("，");
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3388"));
            strBuff.append("。");
        }
        if (StringUtils.isEmpty(question.get("3387")) || "不清楚".equals(question.get("3387"))) {
			/*
			strBuff.append("发作特点不详");
			strBuff.append("，");
			*/
        } else {
            strBuff.append("疼痛为");
            strBuff.append(question.get("3387"));
            strBuff.append("，");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3383")) || "不清楚".equals(question.get("3383"))) {
			/*
			strBuff.append("头痛加重或减轻因素不详");
			strBuff.append("，");
			*/
        } else {
            strBuff.append(question.get("3383"));
            strBuff.append("，");
        }

        String result = strBuff.toString();
        return result;
    }
}
