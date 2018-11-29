package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

/**
 * 下腹部肿块
 */
public class Template78 extends WomenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template78() {}

    public Template78(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template78(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.下腹部肿块;
    }

    /**
     * 3719	年龄
     * 3732	病程
     * 3731	下腹部肿块是怎样发现的？
     * 3854	肿块摸起来有多大？
     * 3727	有无压痛
     * 3728	活动度
     * 3726	增长情况
     * 3846	常见伴随症状
     * 3730	伴随症状
     * @return
     */

    @Override
    public String getSymptomName() {
        //肿块是怎么样发现的答案为：医院检查发现/妇科体检发现-》主诉：（肿块是如何发现的答案）盆腔包块+（病程），伴（常见伴随症状中构成主诉的症状）+（病程）。
        //下腹部肿块答案为：自己偶然发现-》（肿块是如何发现的答案）（主症状）+（病程），伴（常见伴随症状中构成主诉的症状）+（病程）
        StringBuffer strBuff = new StringBuffer();
        if ("妇科体检发现".equals(question.get("3731")) || "医院检查发现".equals(question.get("3731"))) {
            strBuff.append(question.get("3731")).append("盆腔包块").append(getPeriodOfDisease(question.get("3732")));
        } else if ("自己偶然发现".equals(question.get("3731"))) {
            strBuff.append("自己偶然发现").append(getMainSymptom().getText()).append(getPeriodOfDisease(question.get("3732")));
        } else {
            strBuff.append(getMainSymptom().getText()).append(getPeriodOfDisease(question.get("3732")));
        }
        return strBuff.toString();
    }

    /**
     * 获取现病史
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(getPeriodOfDisease(question.get("3732")));
        strBuff.append("前").append(question.get("3731"));
        if ("自己偶然发现".equals(question.get("3731"))) {
            strBuff.append(getMainSymptom().getText()).append("，");
            strBuff.append(question.get("3727")).append("压痛").append("，");
            if(!"不清楚".equals(question.get("3728"))) {
                String hd = question.get("3728");
                if("好".equals(hd) || "差".equals(hd)) {
                    strBuff.append("活动").append(hd).append("，");
                } else {
                    strBuff.append(hd).append("，");
                }
            }
            if(!"不清楚".equals(question.get("3726"))) {
                strBuff.append(question.get("3726")).append("，");
            }
        } else {
            strBuff.append("盆腔包块").append(",");
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        return null;
    }
}
