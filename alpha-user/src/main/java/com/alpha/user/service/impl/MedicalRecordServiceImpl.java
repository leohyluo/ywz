package com.alpha.user.service.impl;

import bsh.EvalError;
import bsh.Interpreter;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.user.dao.DiagnosisMedicalTemplateDao;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;
import com.alpha.user.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/10/12.
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisMedicalTemplateDao diagnosisMedicalTemplateDao;


    /**
     * 获取当前问诊过程内容
     *
     * @param
     * @param templateCode
     */
    public void getMedicalRecord(String templateCode, List<UserDiagnosisDetail> udds, DiagnosisMainSymptoms symptom, UserBasicRecord record) {
        Map<String, String> question = new HashMap<>();
        for (UserDiagnosisDetail udd : udds) {
            question.put(udd.getQuestionCode(), udd.getAnswerContent());
        }
        System.out.println(runCode1(question, symptom));
        System.out.println(runCode2(question, symptom));
        System.out.println(runCode3(record));

    }

    public String getMaminSymptomName(DiagnosisMedicalTemplate diagnosisMedicalTemplate, Map<String, String> question, DiagnosisMainSymptoms symptom) {
//        String result = runCode1(question, symptom);
//        System.out.println(result);
        Interpreter bsh = new Interpreter();
        try {
            bsh.set("question", question);
            bsh.set("symptom", symptom);
            bsh.eval(diagnosisMedicalTemplate.getSymptomName());
            String result = (String) bsh.get("result");
            return result;
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPresentIllnessHistory(DiagnosisMedicalTemplate diagnosisMedicalTemplate, Map<String, String> question, DiagnosisMainSymptoms symptom, String unSelectedCommonConcSymp, String generalSymptomText) {
        Interpreter bsh = new Interpreter();
        try {
            bsh.set("question", question);
            bsh.set("symptom", symptom);
            bsh.set("unSelectedCommonConcSymp", unSelectedCommonConcSymp);
            bsh.set("generalSymptomText", generalSymptomText);
            bsh.eval(diagnosisMedicalTemplate.getPresentIllnessHistory());
            String result = (String) bsh.get("result");
            return result;
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPresentIllnessHistoryHospital_bak(DiagnosisMedicalTemplate diagnosisMedicalTemplate, UserBasicRecord record) {
//        String result = runCode3(record);
//        System.out.println(result);
        Interpreter bsh = new Interpreter();
        try {
            bsh.set("record", record);
            bsh.eval(diagnosisMedicalTemplate.getPresentIllnessHistoryHospital());
            String result = (String) bsh.get("result");
            return result;
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPresentIllnessHistoryHospital(DiagnosisMedicalTemplate diagnosisMedicalTemplate, UserBasicRecord record) {
        try {
            StringBuffer strBuff = new StringBuffer();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            if (record.getOtherHospital() != null && record.getOtherHospital() != 0) {
                String hospitalName = "";
                if(record.getOtherHospital() == 1) {
                    hospitalName = "我院门诊";
                } else {
                    hospitalName = "外院就诊";
                }
                strBuff.append(sdf.format(record.getOtherHospitalCureTime()));
                strBuff.append("患儿到").append(hospitalName).append("，");
                if(org.apache.commons.lang3.StringUtils.isEmpty(record.getOtherHospitalDiagnosis())){
                    strBuff.append("具体诊断不清楚");
                }else{
                    strBuff.append("诊断为");
                    strBuff.append(record.getOtherHospitalDiagnosis());
                }
                strBuff.append("，");
                if (record.getOtherHospitalUseDrug() == 1) {
                    String drugNames = com.alpha.his.utils.AppUtils.getDiseaseFromDiagnosisRecord(record.getOtherHospitalDrugList());
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(drugNames)){
                        strBuff.append("给予药物").append(drugNames).append("治疗后，");
                    } else {
                        strBuff.append("给予药物治疗后，");
                    }
                } else {
                    strBuff.append("未进行治疗，");
                }
                strBuff.append("病情");
                strBuff.append(record.getOtherHospitalEffect()).append("，");
            }
            String result = strBuff.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String runCode1(Map<String, String> question, DiagnosisMainSymptoms symptom) {
        String result = symptom.getSympName() + question.get("3319")+"天";
        return result;
    }

    public String runCode2(Map<String, String> question, DiagnosisMainSymptoms symptom) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("患儿约");
        strBuff.append(question.get("3319"));
        strBuff.append("天前");
        if (org.apache.commons.lang3.StringUtils.isEmpty(question.get("3325"))) {
            strBuff.append("无明显诱因");
        } else {
            strBuff.append("因");
            strBuff.append(question.get("3325"));
        }
        strBuff.append("出现");
        strBuff.append(symptom.getSympName());
        strBuff.append(",");
        strBuff.append("起病");
        strBuff.append(question.get("3321"));
        strBuff.append(",");
        strBuff.append("大便为");
        strBuff.append(question.get("3316"));
        strBuff.append("、");
        strBuff.append(question.get("3324"));
        strBuff.append("、");
        strBuff.append(question.get("3323"));
        strBuff.append("、");
        strBuff.append(question.get("3320"));
        strBuff.append("次/天，伴");
        strBuff.append(question.get("3338"));
        strBuff.append("。");
        String result = strBuff.toString();
        return result;

    }

    public String runCode3(UserBasicRecord record) {
        StringBuffer strBuff = new StringBuffer();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        if (record.getOtherHospital() != null && record.getOtherHospital() == 1) {
            strBuff.append(sdf.format(record.getOtherHospitalCureTime()));
            strBuff.append("前患儿到其它医院就诊，");
            if(org.apache.commons.lang3.StringUtils.isEmpty(record.getOtherHospitalDiagnosis())){
                strBuff.append("具体诊断不清楚");
            }else{
                strBuff.append("诊断为");
                strBuff.append(record.getOtherHospitalDiagnosis());
            }
            strBuff.append(",");
            if (record.getOtherHospitalUseDrug() == 1) {
                strBuff.append("给予药物治疗,病情");
                strBuff.append(record.getOtherHospitalEffect());
                strBuff.append(",");
            } else {
                strBuff.append("未进行治疗,");
            }
            strBuff.append("为进一步诊治今天来就诊。");
        }
        String result = strBuff.toString();
        return result;
    }
    private static String classBody = "org.apache.commons.lang3.StringUtils.isEmpty(record.getMainSymptomName());" +
            "record.setMainSymptomName(\"主症状\");" +
            "        return record;";

}
