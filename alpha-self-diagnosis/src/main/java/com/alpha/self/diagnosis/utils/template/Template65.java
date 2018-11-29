package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

public class Template65 extends ChildrenTemplate {

    /**
     * 3351	呕吐量
     * 3352	呕吐时间
     * 3353	呕吐频率
     * 3354	呕吐物是否有酸味
     * 3355	是否有胆汁
     * 3356	与饮食、活动的关系
     * 3357	致病相关因素
     * 3604	伴随症状
     * 3605	病程（天）
     * 3632	发病年龄（月）
     * 3650	发病季节
     * 3660	呕吐方式
     * 3661	呕吐物性质
     * 3673	常见伴随症状
     * 2002	呕吐次数
     */

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    public Template65() {}

    public Template65(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template65(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.呕吐;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3605");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3605"));

        strBuff.append("前");
        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3357")) || "不清楚".equals(question.get("3357"))) {
            strBuff.append("无明显诱因");
            strBuff.append("出现");
            if(StringUtils.isNotEmpty(question.get("3353"))) {
                strBuff.append(question.get("3353"));
            }
            strBuff.append("呕吐，");
        } else {
            strBuff.append("出现");
            //解决先选2次，然后返回去修改为1次。病历上显示1天前出现["间歇性","持续性"]呕吐
            if(!"1次".equals(question.get("2002")) && StringUtils.isNotEmpty(question.get("3353"))) {
                strBuff.append(question.get("3353"));
            }
            strBuff.append("呕吐，");
            strBuff.append("发病前曾有");
            strBuff.append(question.get("3357"));
            strBuff.append("，");
        }

        //次数 2002
        strBuff.append("呕吐").append(question.get("2002")).append("，");

        strBuff.append("为");
        strBuff.append(question.get("3660"));
        strBuff.append("，");
        if (StringUtils.isEmpty(question.get("3661")) || "不清楚".equals(question.get("3661"))) {
			/*
			strBuff.append("呕吐物性质不详");
			strBuff.append("，");
			*/
        } else {
            strBuff.append("呕吐物为");
            strBuff.append(question.get("3661"));
            strBuff.append("，");
        }

        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuf = new StringBuffer();
        if (isMainComplaint) {
            strBuf.append(question.get("3605")).append("前出现");
        } else {
            strBuf.append("伴");
        }
        strBuf.append("呕吐").append(question.get("2002")).append("，");
        if (!GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3661"))) {
            strBuf.append("呕吐物为").append(question.get("3661")).append("。");
        } else  {
            strBuf.append("呕吐物不详").append("。");
        }
        return strBuf.toString();
    }
}
