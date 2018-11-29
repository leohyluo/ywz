package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template60 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template60() {}

    public Template60(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template60(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.血尿;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3618");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3618"));

        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3414")) || "不清楚".equals(question.get("3414"))) {
            strBuff.append("无明显诱因");
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
			/*
			strBuff.append(question.get("3409"));
			strBuff.append("尿液，");
			*/
        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");
			/*
			strBuff.append(question.get("3409"));
			strBuff.append("尿液，");
			*/
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3414"));
            strBuff.append("。");
        }
       /*
		if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3408")) || "不清楚".equals(question.get("3408"))) {
			strBuff.append("血尿出现阶段不详");
		} else {
			strBuff.append(question.get("3408"));
		}
		strBuff.append("，");
		if ("是".equals(question.get("3410"))) {
			strBuff.append("尿里有血块");
		}else if ("否".equals(question.get("3410"))) {
			strBuff.append("尿里无血块");
		}else if ("不清楚".equals(question.get("3410"))) {
			strBuff.append("不清楚尿里有无血块");
		}
		strBuff.append("，");
		if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3413")) || "不清楚".equals(question.get("3413"))) {
			strBuff.append("发作特点不详");
		} else {
			strBuff.append(question.get("3413"));
			strBuff.append("出现");
		}
		strBuff.append("，");
		*/

        String result = strBuff.toString();
        return result;
    }
}
