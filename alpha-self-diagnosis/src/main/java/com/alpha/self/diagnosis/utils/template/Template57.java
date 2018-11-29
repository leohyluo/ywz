package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Template57 extends ChildrenTemplate {

    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    /**
     * 3377	有无咳痰
     * 3378	痰的性状
     * 3379	咳嗽性质
     * 3380	病程（天）
     * 3381	发作特点
     * 3382	致病相关因素
     * 3601	伴随症状
     * 3624	发病年龄（月）
     * 3642	发病季节
     * 3665	常见伴随症状
     * 3840	咳嗽发生的时间
     * 3842	有无喘息
     */

    public Template57() {}

    public Template57(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
    }

    public Template57(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;
    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.咳嗽;
    }

    @Override
    public String getSymptomName() {
        String result = getMainSymptom().getText() + question.get("3380");
        return result;
    }

    @Override
    public String getDiagnosisProcess() {
        StringBuffer strBuff = new StringBuffer();
        //strBuff.append("患儿约");
        strBuff.append(question.get("3380"));

        strBuff.append("前");
        if (StringUtils.isEmpty(question.get("3382")) || "不清楚".equals(question.get("3382"))) {
            strBuff.append("无明显诱因出现");
            strBuff.append(getMainSymptom().getText());
            strBuff.append("，");

            /*if("有".equals(question.get("3377"))) {
                if("不清楚".equals(question.get("3378"))) {
                    strBuff.append("有痰");
                } else {
                    strBuff.append("咳");
                    strBuff.append(question.get("3378"));
                }
                strBuff.append("，");
            } else {
                strBuff.append("无痰，");
            }*/

        } else {
            strBuff.append("出现");
            strBuff.append(getMainSymptom().getText()).append("，");

            String signStr = "干咳,湿咳,呛咳,刺激性咳嗽,鸡鸣样回音,犬吠性咳嗽";
            Set<String> signSet = Stream.of(signStr.split(",")).collect(Collectors.toSet());
            if(signSet.contains(question.get("3379"))) {
                strBuff.append("为单声咳").append("，");
            } else if ("痉挛性咳嗽".equals(question.get("3379"))) {
                strBuff.append("为连声咳").append("，");
            }

            strBuff.append("发病前曾有");
            strBuff.append(question.get("3382"));
            strBuff.append("。");
        }
        if("不清楚".equals(question.get("3381"))) {
            //strBuff.append("发作特点不详，");
        } else {
            //strBuff.append("有");
            strBuff.append(question.get("3381")).append("，");
            //strBuff.append("的特点，");
        }

        if("有".equals(question.get("3377"))) {
            if("不清楚".equals(question.get("3378"))) {
                strBuff.append("有痰");
            } else {
                strBuff.append("咳");
                strBuff.append(question.get("3378"));
            }
            strBuff.append("，");
        } else if("无".equals(question.get("3377"))){
            strBuff.append("无痰，");
        } else if ("有痰但咳不出来".equals(question.get("3377"))) {
            strBuff.append("有痰不易咳出").append("，");
        }
        if (question.get("3382") != null && !"异物吸入".equals(question.get("3382"))) {
            strBuff.append("否认异物吸入史。");
        }
        String result = strBuff.toString();
        return result;
    }

    @Override
    public String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint) {
        StringBuffer strBuf = new StringBuffer();
        if (isMainComplaint) {
            strBuf.append(question.get("3380")).append("前");
            strBuf.append("出现咳嗽").append("，");
        } else {
            strBuf.append("伴咳嗽").append("，");
        }
        if("有".equals(question.get("3377"))) {
            strBuf.append("咳");
            if(GlobalConstants.UNKNOWN_ANSWER.equals(question.get("3378"))) {
                strBuf.append("痰");
            } else {
                strBuf.append(question.get("3378"));
            }
            strBuf.append("，");
        } else if ("无".equals(question.get("3377"))) {
            strBuf.append("无咳痰").append("，");
        } else if ("有痰但咳不出来".equals(question.get("3377"))) {
            strBuf.append("有痰不易咳出").append("，");
        }
        return strBuf.toString();
    }
}
