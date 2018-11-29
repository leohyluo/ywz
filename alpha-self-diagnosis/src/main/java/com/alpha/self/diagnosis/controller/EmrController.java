package com.alpha.self.diagnosis.controller;

import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.core.util.DateUtil;
import com.alpha.commons.core.util.StringUtil;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import com.alpha.self.diagnosis.pojo.vo.HisDiagnosisResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by edz on 2018/11/12.
 */
@RestController
@RequestMapping("/emr")
public class EmrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmrController.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiagnosisController diagnosisController;
    /**
     * 模拟his 一键导入功能
     */

    @GetMapping("/import")
    public ResponseMessage importemr(Long diagnosisId,String diagnosis) {
        //1 获取数据
        ResponseMessage responseMessage=diagnosisController.queryMedicalRecord4His(diagnosisId);
        HisDiagnosisResultVo diagnosisResult=(HisDiagnosisResultVo)responseMessage.getData();
        //3 上传数据到电子病历系统
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name",diagnosisResult.getUserName());
        jsonObj.put("sex",diagnosisResult.getGender()==2?"1":"2");
        jsonObj.put("dateOfbirth",diagnosisResult.getBirth());
        try {
            BigDecimal bg = new BigDecimal(DateUtils.getAge(DateUtils.string2DateTime(diagnosisResult.getBirth())));
            double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            int a=(int)f1;
            jsonObj.put("age",a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonObj.put("weight",diagnosisResult.getWeight().equals("未测量")?120:diagnosisResult.getWeight().replace("kg",""));
        jsonObj.put("department",diagnosisResult.getDepartment());
        jsonObj.put("doctor",diagnosisResult.getDoctor()==null?"":diagnosisResult.getDoctor());
        jsonObj.put("diagnoseCardNumber",diagnosisResult.getCardNo()==null?"8888":diagnosisResult.getCardNo());
        jsonObj.put("outpatientNo",diagnosisResult.getOutPatientNo());
        jsonObj.put("type","2");
        jsonObj.put("stature",175);
        jsonObj.put("patientId",diagnosisId);
        jsonObj.put("outpatientCode","");
        jsonObj.put("physicalExamination","");
        jsonObj.put("auxiliaryExamination","");
        if(!StringUtils.isEmpty(diagnosis)){
            jsonObj.put("principalDiagnosis",diagnosis.split(",")[0]);
        }else {
            jsonObj.put("principalDiagnosis","");
        }
        jsonObj.put("secondaryDiagnosis","");
        List<String> aller=diagnosisResult.getPastmedicalHistory().getAllergicHistory();
        String allergicHistory="";
        if("无".equals(aller.get(0)) || "不清楚".equals(aller.get(0))){
            allergicHistory=aller.get(0);
             if("无".equals(allergicHistory)){
                 allergicHistory="无药物过敏";
             }else {
                 allergicHistory="药物过敏不详";
             }
        }else {
            String temp="";
            for (int i = 0; i < aller.size(); i++) {
                if(i ==aller.size()-1){
                    temp=temp+aller.get(i);
                }else {
                    temp=temp+aller.get(i)+",";
                }
            }
            allergicHistory="患者对"+temp+"过敏";
        }
        jsonObj.put("allergicHistory",allergicHistory);
        jsonObj.put("actionInChief",diagnosisResult.getMainSymptomName4His()==null?diagnosisResult.getMainSymptomName():diagnosisResult.getMainSymptomName4His());
        jsonObj.put("historyOfPresentIllness",diagnosisResult.getPresentIllnessHistory4His()==null?diagnosisResult.getPresentIllnessHistory():diagnosisResult.getPresentIllnessHistory4His());
        String previousHistory=diagnosisResult.getPastmedicalHistoryText4His()==null?diagnosisResult.getPastmedicalHistoryText():diagnosisResult.getPastmedicalHistoryText4His();
        String str1 = "<span style=\"color:red\">";
        String str2 = "</span>";
        previousHistory = previousHistory.replace(str1, "");
        previousHistory = previousHistory.replace(str2, "");
        if(previousHistory.contains(allergicHistory)){
            previousHistory=previousHistory.replace(allergicHistory+"。","");
        }
        jsonObj.put("previousHistory",previousHistory);
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toJSONString(), headers);
        LOGGER.info("请求参数是："+jsonObj.toJSONString());
        String result=restTemplate.postForEntity("http://ssm.ebmsz.com:38031/patientBasicInfo/add",formEntity, String.class).getBody();
        LOGGER.info("请求结果是："+result);
        return WebUtils.buildSuccessResponseMessage(result);

    }



}
