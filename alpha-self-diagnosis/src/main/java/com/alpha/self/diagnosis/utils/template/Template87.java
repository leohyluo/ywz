package com.alpha.self.diagnosis.utils.template;

import com.alpha.commons.enums.MenstruationStatus;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class Template87 extends WomenTemplate {

    //3743	病程
    //3533  月经周期
    //3534  经期
    //3535  月经量
    //3536  致病相关因素
    //3841	常见伴随症状
    //3744	伴随症状


    private Map<String, String> question;
    private UserBasicRecord userBasicRecord;
    private List<UserDiagnosisOutcome> diagnosisOutcomeList;

    private String zqi = null;
    private String jqi = null;
    private String bcheng = null;
    private String lmp = null;
    private String zbys = null;
    private String yjl = null;

    private String zhouqi = null;
    private String jingqi = null;
    private String menarcheStatus = null;

    public Template87() {
    }

    public Template87(UserBasicRecord userBasicRecord, Map<String, String> question) {
        super(userBasicRecord, question);
        this.userBasicRecord = userBasicRecord;
        this.question = question;

        init();
    }

    public Template87(UserBasicRecord userBasicRecord, Map<String, String> question, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        super(userBasicRecord, question, diagnosisOutcomeList);
        this.userBasicRecord = userBasicRecord;
        this.question = question;
        this.diagnosisOutcomeList = diagnosisOutcomeList;

        init();
    }

    private void init() {
        zqi = StringUtils.removeBracketsChar(question.get("3533")); //月经周期
        jqi = StringUtils.removeBracketsChar(question.get("3534"));  //经期
        bcheng = question.get("3743"); //病程
        lmp = userBasicRecord.getLmp(); //末
        zbys = question.get("3536"); //致病相关
        yjl = StringUtils.removeBracketsChar(question.get("3535")); //月经量

        zhouqi = userBasicRecord.getMenarcheCycle();
        jingqi = userBasicRecord.getMenarchePeroid();
        menarcheStatus = userBasicRecord.getMenarcheStatus();


    }

    @Override
    public MainSymptomEnum getMainSymptom() {
        return MainSymptomEnum.月经失调;
    }

    /**
     * 主症状组装
     * //3533  月经周期
     * //3534  经期
     * if 病人选择周期的答案为正常或者不清楚  then  （病人选择经期的答案）+（病程）
     * if 病人选择周期的答案、经期都为正常时  then 月经（病人选择经量的答案  量少-.减少 量多增多）+（病程）
     *
     * @return
     */
    @Override
    public String getSymptomName() {
        StringBuffer sman = new StringBuffer();

        String zqi = question.get("3533"); //月经周期
        String jqi = question.get("3534"); //经期
        String jliang = question.get("3535");//经量
        String bcheng = question.get("3743"); //病程

        if (!zqi.contains("正常") && !zqi.contains("不清楚")) { // 病人选择周期除了选择“正常与不清楚”时模板
            //zqi 替换() （）
            zqi = StringUtils.removeBracketsChar(zqi);
            sman.append("月经").append(zqi).append(bcheng);
            logger.info("第一种情况..........");
        } else if (zqi.contains("正常") && !jqi.contains("不清楚")) {
            logger.info("第二种情况..........");
            jqi = StringUtils.removeBracketsChar(jqi);
            sman.append(jqi).append(bcheng);
        } else if (zqi.contains("正常") && jqi.contains("不清楚") && !jliang.contains("不清楚")) {
            //量少-->减少 量多增多
            if (jliang.contains("量少")) {
                jliang = "减少";
            } else if (jliang.contains("量多")) {
                jliang = "增多";
            }
            sman.append("月经").append(jliang).append(bcheng);
            logger.info("第三种情况..........");
        } else if (zqi.contains("正常") && jqi.contains("不清楚") && jliang.contains("不清楚")) {
            sman.append("月经失调").append(bcheng);
        }
        return sman.toString();
    }

    /**
     * 现病史
     *
     * @return
     */
    @Override
    public String getDiagnosisProcess() {
        if (!zqi.equals("正常") && !zqi.equals("不清楚")) {
            return getDiagnosisProcessDouble();
        } else if (zqi.contains("正常") && !jqi.contains("不清楚")) {
            return getDiagnosisProcessNormal();
        } else if (zqi.contains("正常") && jqi.contains("不清楚") && !yjl.contains("不清楚")) {
            return getDiagnosisProcessNormalAll();
        } else if (zqi.contains("正常") && jqi.contains("不清楚") && yjl.contains("不清楚")) {
            return getDiagnosisProcessNormalAllCont();
        }
        return null;
    }

    private String getPreFlag() {
        String flagNormal = "规律";
        String flagUnNormal = "不规律";
        StringBuffer sb = new StringBuffer();
        String menarcheStatus = userBasicRecord.getMenarcheStatus();
        if (MenstruationStatus.NORMAL.getValue().equals(menarcheStatus)) {
            sb.append("既往月经").append(flagNormal).append("，");
            sb.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");
        } else if (MenstruationStatus.UNNORMAL.getValue().equals(menarcheStatus)) {
            sb.append("既往月经").append(flagUnNormal).append("，");
            sb.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");
        }
//        sb.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");
//        sb.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");
//        sb.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");
        return sb.toString();
    }

    /**
     * 现病史  病人选择周期、经期都为正常时模板
     */
    private String getDiagnosisProcessNormalAll() {
        String jliang = yjl;
        if (jliang.contains("量少")) {
            jliang = "减少";
        } else if (jliang.contains("量多")) {
            jliang = "增多";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(getPreFlag());


        if ("不清楚".equals(zbys)) {
            sb.append("约").append(bcheng).append("前无明显诱因出现月经").append(jliang).append("，");
        } else {
            sb.append("约").append(bcheng).append("出现月经").append(jliang).append("，");
        }
        sb.append(jqi).append("，").append(zqi).append("。");

//        sb.append("约").append(bcheng).append("前出现月经").append(jliang).append("，");
//        sb.append(zbys).append("，").append(jqi).append("，").append(zqi).append("。");
        return sb.toString();
    }

    /**
     * 现病史  病人选择周期的答案为正常或者不清楚时模板
     */
    private String getDiagnosisProcessNormal() {
        String jliang = yjl;
        if (jliang.contains("量少")) {
            jliang = "减少";
        } else if (jliang.contains("量多")) {
            jliang = "增多";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(getPreFlag());


        if ("不清楚".equals(zbys)) {
            sb.append("约").append(bcheng).append("前无明显诱因出现").append(jqi).append("，");
        } else {
            sb.append("约").append(bcheng).append("出现").append(jqi).append("，");
        }
        sb.append(jliang).append("，").append(zqi).append("。");


//        sb.append("约").append(bcheng).append("前出现").append(jqi).append("，");
//        sb.append(zbys).append("，").append(jliang).append("，").append(zqi);
        return sb.toString();
    }

    /**
     * 现病史  月经周期 经期都有值
     */
    private String getDiagnosisProcessDouble() {
        StringBuffer sb = new StringBuffer();
        sb.append(getPreFlag());


        if ("不清楚".equals(zbys)) {
            sb.append("约").append(bcheng).append("前无明显诱因出现月经").append(zqi).append("，");
        } else {
            sb.append("约").append(bcheng).append("出现月经").append(zqi).append("，");
        }

        if (!jqi.contains("不清楚") && !yjl.contains("不清楚")) {
            sb.append(jqi).append("，").append(yjl).append("。");
        } else if (jqi.contains("不清楚") && !yjl.contains("不清楚")) {
            sb.append(yjl).append("。");
        } else if (!jqi.contains("不清楚") && yjl.contains("不清楚")) {
            sb.append(jqi).append("。");
        }
        return sb.toString();
    }

    /**
     * 周期正常,经期、经量都为不清楚
     *
     * @return
     */
    private String getDiagnosisProcessNormalAllCont() {
        StringBuffer sb = new StringBuffer();
        sb.append(getPreFlag());
        sb.append(jingqi).append("/").append(zhouqi).append("天，末次月经为").append(lmp).append("。");

        if ("不清楚".equals(zbys)) {
            sb.append("约").append(bcheng).append("前无明显诱因出现月经失调。");
        } else {
            sb.append("约").append(bcheng).append("出现月经失调。");
        }
        return sb.toString();
    }
}
