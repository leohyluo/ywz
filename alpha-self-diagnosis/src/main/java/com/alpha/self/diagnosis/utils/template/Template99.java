package com.alpha.self.diagnosis.utils.template;

import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template99 extends ChildrenTemplate {

    /**
     3788	发病年龄
     3789	病程
     3791	一周有几个晚上会打鼾
     3790	鼾声是怎么样的
     3799	有没有出现呼吸暂时停止
     3800	一晚会出现几次
     3792	诱因
     3793	常见伴随症状（可多选）
     3794	伴随症状（可多选）
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template99() {}

    public Template99(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template99(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.打鼾;
    }

    @Override
    public String getSymptomName() {
        String sympName = "打鼾" + question.get("3789");
        return sympName;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3789")).append("前出现打鼾").append("，");
        strBuff.append("一周大约有").append(question.get("3791")).append("晚上会出现打鼾").append("，");
        if (!"不清楚".equals(question.get("3790"))) {
            strBuff.append(question.get("3790")).append("，");
        }
        if ("无".equals(question.get("3799"))) {
            strBuff.append("睡眠过程中无呼吸暂停").append("，");
        } else if ("有".equals(question.get("3799"))) {
            strBuff.append("睡眠过程大约出现").append(question.get("3800")).append("次呼吸暂停").append("，");
        }
        if (!"不清楚".equals(question.get("3792"))) {
            strBuff.append("发病前曾有").append(question.get("3792")).append("，");
        }
        return strBuff.toString();
    }
}
