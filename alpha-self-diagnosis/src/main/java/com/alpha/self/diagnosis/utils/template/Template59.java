package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template59 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    /*
    皮疹
    3558	皮疹部位
    3559	皮疹分布
    3560	皮疹特点
    3561	发病诱因
    3562	既往史、家族史
    3619	病程（天）
    3620	伴随症状
    3626	发病年龄（月）
    3644	发病季节
    3667	常见伴随症状
    3683	出疹顺序
    */

    /*
    发热
    3328	发热程度
    3327	病程
    3837	最高体温
     */

    public Template59() {}

    public Template59(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template59(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.皮疹;
    }

    @Override
    public String getSymptomName() {
        String result = "皮疹" + question.get("3619");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(question.get("3619"));
        strBuff.append("前");
        if("不清楚".equals(question.get("3561"))) {
            strBuff.append("无明显诱因");
        }
        strBuff.append("出现").append(question.get("3558")).append("皮疹").append("，");
        if (!"不清楚".equals(question.get("3561"))) {
            strBuff.append("发病前").append(question.get("3561")).append("，");
        }
        if (!GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3560"))) {
            strBuff.append("皮疹为").append(question.get("3560")).append("。");
        }

        String result = strBuff.toString();
        return result;
    }
}
