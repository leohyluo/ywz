package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.enums.MenstruationStatus;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

/**
 * 下腹部疼痛
 */
public class Template79 extends WomenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template79() {}

    public Template79(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template79(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.下腹部疼痛;
    }

    /**
     * 3714	年龄
     * 3713	病程
     * 3708	疼痛的部位
     * 3712	下腹痛处于的时间段
     * 3799	停经多久了？
     * 3850	停经后有做以下处理吗？
     * 3851	停经后是否有恶心、呕吐？
     * 3709	疼痛的性质
     * 3715	疼痛的特点
     * 3795	加重因素
     * 3716	致病相关因素
     * 3711	常见伴随症状
     * 3717	伴随症状
     * @return
     */

    @Override
    public String getSymptomName() {
        StringBuffer strBuff = new StringBuffer();
        if (question.containsKey("3712") && question.get("3712").contains("停经")) {
            strBuff.append(question.get("3712")).append(getPeriodOfDisease(question.get("3799")));
            strBuff.append("，下腹痛").append(getPeriodOfDisease(question.get("3713")));
        } else {
            strBuff.append("下腹痛").append(getPeriodOfDisease(question.get("3713")));
        }
        return strBuff.toString();
    }

    public String getSymptomName_bak() {
        if ("有".equals(question.get("3712"))) {
            return "停经" + getPeriodOfDisease(question.get("3799")) + "," + getMainSymptom().getText() + getPeriodOfDisease(question.get("3713"));
        }
        return getMainSymptom().getText() + getPeriodOfDisease(question.get("3713"));
    }

    /**
     * 获取现病史
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        String menarcheStatus = userBasicRecord.getMenarcheStatus();
        if (!MenstruationStatus.ABSOLUTE.getValue().equals(menarcheStatus)) {
            strBuff.append("平素月经");
            if (MenstruationStatus.NORMAL.getValue().equals(menarcheStatus)) {
                strBuff.append("规律").append("，");
            } else if (MenstruationStatus.UNNORMAL.getValue().equals(menarcheStatus)) {
                strBuff.append("不规律").append("，");
            }
            String zhouqi = userBasicRecord.getMenarcheCycle();
            String jingqi = userBasicRecord.getMenarchePeroid();
            String lmp = userBasicRecord.getLmp();
            strBuff.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");
        }

        if (question.containsKey("3850") && !question.get("3850").contains("都没有")) {
            strBuff.append("停经后").append(question.get("3850")).append("，");
        }
        if (question.containsKey("3851")) {
            if (question.get("3851").contains("都没有")) {
                strBuff.append("无恶心呕吐").append("。");
            } else {
                strBuff.append("伴").append(question.get("3851")).append("。");
            }
        }
        strBuff.append("约").append(getPeriodOfDisease(question.get("3713"))).append("前");
        if ("不清楚".equals(question.get("3716"))) {
            strBuff.append("无明显诱因");
        }
        strBuff.append("出现");
        strBuff.append(question.get("3708"));
        if (!"不清楚".equals(question.get("3709"))) {
            strBuff.append(question.get("3709")).append("，");
        } else {
            strBuff.append("痛").append("，");
        }
        if (!"不清楚".equals(question.get("3715"))) {
            strBuff.append(question.get("3715")).append("，");
        }
        if (!"不清楚".equals(question.get("3795"))) {
            strBuff.append(question.get("3795")).append("，");
        }

        String result = strBuff.toString();
        return result;
    }

    /**
     * 获取现病史
     * @return
     */
    public String getDiagnosisProcess_bak() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("患者");
        if ("有".equals(question.get("3712"))) {
            strBuff.append("停经");
            strBuff.append(getPeriodOfDisease(question.get("3799"))).append(",");
        } else {
            strBuff.append("约");
        }
        strBuff.append(getPeriodOfDisease(question.get("3713")));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3716")) || "不清楚".equals(question.get("3716"))) {
            strBuff.append("无明显诱因出现");
        } else {
            strBuff.append("出现");
        }

        if (StringUtils.isEmpty(question.get("3709")) || "不清楚".equals(question.get("3709"))) {
            strBuff.append(question.get("3708")).append("疼痛，");
            //strBuff.append(question.get("3709")).append("，");
        } else {
            strBuff.append(question.get("3708")).append("疼痛，");
            strBuff.append(question.get("3709")).append("，");
        }
        if (!"不清楚".equals(question.get("3715"))) {
            strBuff.append(question.get("3715")).append("，");
        }
        if (!"不清楚".equals(question.get("3795"))) {
            strBuff.append(question.get("3795")).append("，");
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuff = new StringBuffer();
        if(isMainComplaint) {
            strBuff.append(question.get("3713")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        if (!"不清楚".equals(question.get("3709"))) {
            strBuff.append("下腹");
            strBuff.append(question.get("3709")).append("，");
        } else {
            strBuff.append("下腹痛").append("，");
        }
        if (!"不清楚".equals(question.get("3715"))) {
            strBuff.append(question.get("3715")).append("，");
        }
        return strBuff.toString();
    }
}
