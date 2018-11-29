package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template85 extends WomenTemplate {

    //3518	性状
    //3519	颜色
    //3520	气味
    //3521	致病相关因素
    //3705	白带的量
    //3706	病程
    //3707	伴随症状
    //3748	年龄
    //3749	常见伴随症状

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template85() {}

    public Template85(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template85(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.白带异常;
    }

    @Override
    public String getSymptomName() {
        if ("增多".equals(question.get("3705"))) {
            return "白带" + question.get("3705") + getPeriodOfDisease(question.get("3706"));
        } else if ("无变化".equals(question.get("3705")) && "不清楚".equals(question.get("3518")) && "不清楚".equals(question.get(question.get("3519")))) {
            if(GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3519"))) {
               return "白带异常" + getPeriodOfDisease(question.get("3706"));
            }
            return question.get("3519") + "白带" + getPeriodOfDisease(question.get("3706"));
        } else if ("无变化".equals(question.get("3705")) && "不清楚".equals(question.get("3518"))) {
            if(GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3519"))) {
                return "白带异常" + getPeriodOfDisease(question.get("3706"));
            }
            return question.get("3519") + "白带" + getPeriodOfDisease(question.get("3706"));
        } else if ("无变化".equals(question.get("3705"))) {
            return question.get("3518") + "白带" + getPeriodOfDisease(question.get("3706"));
        }
        return null;
    }

    @Override
    public String getDiagnosisProcess() {
        if ("增多".equals(question.get("3705"))) {
            return getDiagnosisProcess1();
        } else if ("无变化".equals(question.get("3705")) && "不清楚".equals(question.get("3518")) && "不清楚".equals(question.get(question.get("3519")))) {
            return getDiagnosisProcess2();
        } else if ("无变化".equals(question.get("3705")) && "不清楚".equals(question.get("3518"))) {
            return getDiagnosisProcess3();
        } else if ("无变化".equals(question.get("3705"))) {
            return getDiagnosisProcess4();
        }
        return null;
    }

    private String getDiagnosisProcess1() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("约");
        strBuff.append(getPeriodOfDisease(question.get("3706")));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3718")) || "不清楚".equals(question.get("3718"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append("白带").append(question.get("3705")).append("，");
        } else {
            strBuff.append("出现白带");
            strBuff.append(question.get("3705")).append("，");
            //strBuff.append("发病前曾有").append(question.get("3521")).append("，");
        }
        if(StringUtils.isNotEmpty(question.get("3518")) && !"不清楚".equals(question.get("3518"))) {
            strBuff.append("呈").append(question.get("3518")).append("，");
        }
        if(StringUtils.isNotEmpty(question.get("3519")) && !"不清楚".equals(question.get("3519"))) {
            strBuff.append(question.get("3519")).append("，");
        }
        strBuff.append(question.get("3520")).append("。");

        String result = strBuff.toString();
        return result;
    }

    private String getDiagnosisProcess2() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("约");
        strBuff.append(getPeriodOfDisease(question.get("3706")));
        strBuff.append("前");
        if(!"不清楚".equals(question.get("3521"))) {
            strBuff.append("出现白带异常").append("，");
            strBuff.append(question.get("3521")).append("，");
        } else {
            strBuff.append("无明显诱因出现白带异常").append("，");
        }
        strBuff.append(question.get("3520")).append("。");
        String result = strBuff.toString();
        return result;
    }

    private String getDiagnosisProcess3() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("约");
        strBuff.append(getPeriodOfDisease(question.get("3706")));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3718")) || "不清楚".equals(question.get("3718"))) {
            strBuff.append("无明显诱因出现");
            if(GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3519"))) {
                strBuff.append("白带异常，");
            } else {
                strBuff.append(question.get("3519")).append("白带，");
            }
        } else {
            strBuff.append("出现");
            if(GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3519"))) {
                strBuff.append("白带异常，");
            } else {
                strBuff.append(question.get("3519")).append("白带，");
            }
        }
        strBuff.append(question.get("3520")).append("。");

        String result = strBuff.toString();
        return result;
    }

    private String getDiagnosisProcess4() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("约");
        strBuff.append(getPeriodOfDisease(question.get("3706")));
        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3718")) || "不清楚".equals(question.get("3718"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(question.get("3518")).append("白带，");
        } else {
            strBuff.append("出现");
            strBuff.append(question.get("3518")).append("白带，");
            //strBuff.append("发病前曾有").append(question.get("3521")).append("，");
        }
        if(StringUtils.isNotEmpty(question.get("3519")) && !"不清楚".equals(question.get("3519"))) {
            strBuff.append(question.get("3519")).append("，");
        }
        strBuff.append(question.get("3520")).append("。");

        String result = strBuff.toString();
        return result;
    }

}
