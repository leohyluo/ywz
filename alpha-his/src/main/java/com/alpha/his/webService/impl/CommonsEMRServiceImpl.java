package com.alpha.his.webService.impl;

import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.api.tencent.qcloud.Utilities.MD5;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.UserCardInfoMapper;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.core.util.ParamUtil;
import com.alpha.commons.enums.ImportAndEditeType;
import com.alpha.commons.util.XStreamUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.pojo.dto.*;
import com.alpha.his.util.WSResponseUtils;
import com.alpha.his.webService.CommonsEMRService;
import com.alpha.redis.RedisMrg;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2018/9/30.
 */
@Component
@WebService(serviceName = "CommonsEMRService"
        , targetNamespace = "http://webService.his.alpha.com"
        , endpointInterface = "com.alpha.his.webService.CommonsEMRService")
public class CommonsEMRServiceImpl implements CommonsEMRService {

    @Resource
    UserCardInfoMapper userCardInfoMapper;
    @Resource
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${alpha.diagnosis.url}")
    private String diagnosis_server_url;
    @Value("${alpha.h5.medicalUrl}")
    private String h5_medicalRecord_url;
    @Value("${hisSevice.key}")
    private String key;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;
    @Value("${hospital.code}")
    private  String code;

    @Autowired
    private YwzCountTimesService ywzCountTimesService;

    @Override
    public String getEmrInfoUrl(String cardNo, String pno, String doctorName, String sign) {
        String resultData =recordShowUrl(cardNo,pno,doctorName,sign);
        if(!StringUtils.isBlank(resultData)){
            return resultData;
        }
        long start = System.currentTimeMillis();
        logger.info("拉取电子病历请求参数cardNo=" + cardNo + ",pno=" + pno + ",sign=" + sign);
        if (com.alpha.commons.util.StringUtils.isEmpty(cardNo, pno, sign)) {
            ResultDTO resultDTO = new ResultDTO(ResponseStatus.REQUIRED_PARAMETER_MISSING, sign);
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }
        logger.info("医院{}的key为{}", GlobalConstants.SZ_CHILD_HOSPITAL_CODE, key);
        Map<String, String> signMap = new HashMap<>();
        signMap.put("cardNo", cardNo);
        signMap.put("pno", pno);
        signMap.put("doctorName",doctorName);
        String url = ParamUtil.formatUrlMap(signMap, true, false);
        url = url + "&key=" + key;
        //光明不能用&
        if(code.equals("A002")){
           url="cardNo="+cardNo+"@pno="+pno+"@key="+key;
        }
        String sign1 = MD5.stringToMD5(url).toUpperCase();
        if (!sign.equalsIgnoreCase(sign1)) {
            ResultDTO resultDTO = new ResultDTO(ResponseStatus.INVALID_SIGN, sign);
            logger.info("拉取电子病历UR错误：{}", ResponseStatus.INVALID_SIGN.message());
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }
        String uri = diagnosis_server_url.concat("/rpc/basicRecord/get");
        JSONObject json = new JSONObject();
        json.put("hospitalCode", "A002");
        json.put("cardNo", pno);

        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("allParam", json.toString());

        UserBasicRecord record = restTemplate.postForObject(uri, requestEntity, UserBasicRecord.class);
        String openUrl = "";
        ResponseStatus responseStatus = ResponseStatus.SUCCESS;
        if (record != null) {
            String diagnosisId = String.valueOf(record.getDiagnosisId());
            //openUrl = alpha_h5_url.concat("#/case/1/").concat(diagnosisId);
            openUrl = h5_medicalRecord_url.concat("?diagnosisId=").concat(diagnosisId);
            YwzCountTimes ywzCountTimes = new YwzCountTimes();
            ywzCountTimes.setType(5);
            ywzCountTimes.setPno(pno);
            ywzCountTimes.setDiseaseId(String.valueOf(record.getDiagnosisId()));
            ywzCountTimesService.addTimes(ywzCountTimes);
        } else {
            responseStatus = ResponseStatus.BASIC_RECORD_NOTFOUND;
        }
        ResultDTO resultDTO = new ResultDTO(responseStatus, sign);
        EmrInfoUrlDetailDTO emrInfoUrlDetailDTO = new EmrInfoUrlDetailDTO(openUrl);

        EmrInfoUrlDTO emrInfoUrlDTO = new EmrInfoUrlDTO(resultDTO, emrInfoUrlDetailDTO);

        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("xml", EmrInfoUrlDTO.class);
        String result = XStreamUtils.Object2XMLString(emrInfoUrlDTO, aliasMap);
        logger.info("获取电子病历url耗时：" + (System.currentTimeMillis() - start) + "毫秒");
        return result;
    }

    @Override
    public String getEmrInfoData(String cardNo, String pno, String importName, String doctorName, String sign) {
        recordImport(cardNo,pno,importName,doctorName);
        if (com.alpha.commons.util.StringUtils.isEmpty(cardNo, pno, sign)) {
            ResultDTO resultDTO = new ResultDTO(ResponseStatus.REQUIRED_PARAMETER_MISSING, sign);
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }
        logger.info("医院{}的key为{}", GlobalConstants.SZ_CHILD_HOSPITAL_CODE, key);
        Map<String, String> signMap = new HashMap<>();
        signMap.put("cardNo", cardNo);
        signMap.put("pno", pno);
        signMap.put("importName",importName);
        signMap.put("doctorName",doctorName);
        String url = ParamUtil.formatUrlMap(signMap, true, false);
        url = url + "&key=" + key;
        if(code.equals("A002")){
            url="cardNo="+cardNo+"@importName="+importName+"@pno="+pno+"@key="+key;
        }
        String sign1 = MD5.stringToMD5(url).toUpperCase();
        if (!sign.equalsIgnoreCase(sign1)) {
            ResultDTO resultDTO = new ResultDTO(ResponseStatus.INVALID_SIGN, sign);
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }
        String uri = diagnosis_server_url.concat("/rpc/medicineRecord/get");
        JSONObject json = new JSONObject();
        json.put("hospitalCode", "A002");
        json.put("pno", pno);

        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("allParam", json.toString());

        EmrInfoDetailDTO emrInfoDetailDTO = restTemplate.postForObject(uri, requestEntity, EmrInfoDetailDTO.class);
        ResultDTO resultDTO = new ResultDTO(ResponseStatus.SUCCESS, null);
        if (emrInfoDetailDTO == null) {
            resultDTO = new ResultDTO(ResponseStatus.BASIC_RECORD_NOTFOUND, null);
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }
        String pastmedicalHistory = emrInfoDetailDTO.getPastmedicalHistory();
        if(StringUtils.isNotEmpty(pastmedicalHistory)) {
            String str1 = "<span style=\"color:red\">";
            String str2 = "</span>";
            pastmedicalHistory = pastmedicalHistory.replace(str1, "");
            pastmedicalHistory = pastmedicalHistory.replace(str2, "");
            emrInfoDetailDTO.setPastmedicalHistory(pastmedicalHistory);
        }
        EmrInfoDTO emrInfoDTO = new EmrInfoDTO(resultDTO, emrInfoDetailDTO);

        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("xml", EmrInfoDTO.class);
        String result = XStreamUtils.Object2XMLString(emrInfoDTO, aliasMap);
        RedisMrg.getInstance(redisIp, redisPort, redisPwd).incr("import_ywz", 1);

        //增加导入过不再展现病历
        uri = diagnosis_server_url.concat("/rpc/medicineRecord/import/update");
        JSONObject j_ = new JSONObject();
        j_.put("pNo", pno);
        MultiValueMap<String, String> r_ = new LinkedMultiValueMap<>();
        r_.add("allParam", j_.toString());
       if(!code.equals("A002")){
           restTemplate.postForEntity(uri, r_, Integer.class);
       }
        return result;
    }

    public void recordImport(String cardNo,String pno,String importName,String doctorName){
        logger.info("医生导入病历：挂号号码：{}，导入部分是：{},医生名字是：{}", pno, importName, doctorName);
        if (!StringUtils.isBlank(importName)) {
            String[] list = importName.split(",");
            for (String str : list) {
                if (str.equals(ImportAndEditeType.IMPORT_61.getValue())) {//主诉
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_61.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
                if (str.equals(ImportAndEditeType.IMPORT_62.getValue())) {//现病史
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_62.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
                if (str.equals(ImportAndEditeType.IMPORT_63.getValue())) {//既往史
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_63.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
                if (str.equals(ImportAndEditeType.IMPORT_64.getValue())) {//诊断结果
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_64.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
            }
        }

    }

    public  String recordShowUrl(String cardNo,String pno,String doctorName,String sign){
        logger.info("医生打开病历：挂号号码：{},医生名字是：{}", pno, doctorName);
        //增加如果此病历被导入过，则再次打开时不展现  -1:未能导入过  1:导入过
        String uri = diagnosis_server_url.concat("/rpc/medicineRecord/show/exists");
        JSONObject json = new JSONObject();
        json.put("pNo", pno);
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("allParam", json.toString());
        ResponseEntity<Integer> flag = restTemplate.postForEntity(uri, requestEntity, Integer.class);
        if (new Integer(1).compareTo(flag.getBody()) == 0) {
            logger.info("   ------ {} 此病历已被导入过，不再展现------", pno);
            ResultDTO resultDTO = new ResultDTO(ResponseStatus.MEDICAL_ALREADY_IMPORT, sign);
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }

        //产生空统计  医生和挂号号码 空统计
        YwzCountTimes ywzCountTimes = new YwzCountTimes();
        if (!StringUtils.isBlank(pno)) {
            if (null != doctorName) {
                ywzCountTimes.setDoctorName(doctorName);
            }
            ywzCountTimes.setPno(pno);
            ywzCountTimes.setType(5);
            ywzCountTimesService.addTimes(ywzCountTimes, 5);
        }
        return null;
    }

}
