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
 * 阴道流血
 */
public class Template77 extends WomenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template77() {}

    public Template77(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template77(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.阴道流血;
    }

    /**
     * 3702	起病年龄
     * 3703	病程
     * 3528	出血时间段
     * 3529	出血量的多少
     * 3532	出血的颜色
     * 3530	出血规律
     * 3531	致病相关因素
     * 3750	常见伴随症状
     * 3704	伴随症状
     * 3796	停经多久了？
     * 3848	停经后有做以下处理吗？
     * 3849	停经后是否有恶心、呕吐？
     * 3798	绝经多久了？
     * 3797	产后多久了？
     * @return
     */

    @Override
    public String getSymptomName() {
        if(question.containsKey("3528")) {
            if (question.get("3528").equals("不清楚") || question.get("3528").equals("经间期") || question.get("3528").equals("月经期")) {
                return getMainSymptom().getText() + getPeriodOfDisease(question.get("3703"));
            } else if (question.get("3528").equals("停经后")) {
                String sympName = "停经" + getPeriodOfDisease(question.get("3796")) + "，" + getMainSymptom().getText() + getPeriodOfDisease(question.get("3703"));
                return sympName;
            } else if (question.get("3528").equals("绝经后")) {
                String sympName = "绝经" + getPeriodOfDisease(question.get("3798")) + "，" + getMainSymptom().getText() + getPeriodOfDisease(question.get("3703"));
                return sympName;
            } else if (question.get("3528").equals("产后")) {
                String sympName = question.get("3528") + getPeriodOfDisease(question.get("3797")) + "，" + getMainSymptom().getText() + getPeriodOfDisease(question.get("3703"));
                return sympName;
            }
            return question.get("3528") + getMainSymptom().getText() + getPeriodOfDisease(question.get("3703"));
        } else {
            return getMainSymptom().getText() + getPeriodOfDisease(question.get("3703"));
        }

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
        if (question.containsKey("3848") && !question.get("3848").contains("都没有")) {
            strBuff.append("停经后").append(question.get("3848")).append("，");
        }
        if (question.containsKey("3849")) {
            if (question.get("3849").contains("都没有")) {
                strBuff.append("无恶心呕吐").append("，");
            } else {
                strBuff.append("伴").append(question.get("3849")).append("，");
            }
        }
        strBuff.append("约").append(getPeriodOfDisease(question.get("3703"))).append("前");
        if ("不清楚".equals(question.get("3531"))) {
            strBuff.append("无明显诱因");
        }
        strBuff.append("出现");
        if(question.get("3530").equals("不清楚")) {
            strBuff.append(getMainSymptom().getText()).append("，");
        } else {
            strBuff.append(question.get("3530")).append(getMainSymptom().getText()).append("，");
        }
        if(!question.get("3531").equals("不清楚")) {
            String answerContent = StringUtils.removeBracketsChar(question.get("3531"));
            strBuff.append(answerContent).append("。");
        }
        //量
        if(!question.get("3529").equals("不清楚")) {
            String answerContent = StringUtils.removeBracketsChar(question.get("3529"));
            strBuff.append(answerContent).append("，");
        }
        //颜色
        if(!question.get("3532").equals("不清楚")) {
            strBuff.append("呈").append(question.get("3532")).append("。");
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
        if(question.get("3528").equals("不清楚") || question.get("3528").equals("经间期") || question.get("3528").equals("月经期")) {
            strBuff.append("患者约");
            strBuff.append(getPeriodOfDisease(question.get("3703")));
            strBuff.append("前出现");
            if(question.get("3530").equals("不清楚")) {
                strBuff.append(getMainSymptom().getText()).append("，");
            } else {
                strBuff.append(question.get("3530")).append(getMainSymptom().getText()).append("，");
            }
            if(!question.get("3529").equals("不清楚")) {
                String answerContent = StringUtils.removeBracketsChar(question.get("3529"));
                strBuff.append(answerContent).append("。");
            }
        } else {
            if (question.get("3528").equals("停经后")) {
                strBuff.append("患者停经").append(getPeriodOfDisease(question.get("3796"))).append("，");
            }  else if (question.get("3528").equals("绝经后")) {
                strBuff.append("患者绝经").append(getPeriodOfDisease(question.get("3798"))).append("，");
            } else if (question.get("3528").equals("产后")) {
                strBuff.append("患者").append(question.get("3528")).append(getPeriodOfDisease(question.get("3797"))).append("，");
            } else {
                strBuff.append("患者");
            }
            strBuff.append("约").append(getPeriodOfDisease(question.get("3703"))).append("前出现");
            if(!question.get("3530").equals("不清楚")) {
                strBuff.append(question.get("3530"));
            }
            strBuff.append(getMainSymptom().getText()).append("，");
            if(!question.get("3529").equals("不清楚")) {
                String answerContent = StringUtils.removeBracketsChar(question.get("3529"));
                strBuff.append(answerContent).append("。");
            }
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuff = new StringBuffer();
        if(isMainComplaint) {
            strBuff.append(question.get("3703")).append("前出现");
        } else {
            strBuff.append("伴");
        }
        if (!"不清楚".equals(question.get("3530"))) {
            strBuff.append(question.get("3530")).append("阴道流血").append("，");
        } else {
            strBuff.append("阴道流血").append("，");
        }
        if (!"不清楚".equals(question.get("3529"))) {
            String answerContent = StringUtils.removeBracketsChar(question.get("3529"));
            strBuff.append(answerContent).append("，");
        }
        return strBuff.toString();
    }
}
