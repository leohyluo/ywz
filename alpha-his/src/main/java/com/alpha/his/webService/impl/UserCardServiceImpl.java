package com.alpha.his.webService.impl;

import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.api.tencent.qcloud.Utilities.MD5;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.UserCardInfoMapper;
import com.alpha.commons.core.pojo.ResultData;
import com.alpha.commons.core.pojo.UserCardInfo;
import com.alpha.commons.core.pojo.UserCardInfoVo;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.core.util.ParamUtil;
import com.alpha.commons.enums.ImportAndEditeType;
import com.alpha.commons.util.XMLUtils;
import com.alpha.commons.util.XStreamUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.mapper.HospitalDeptMapper;
import com.alpha.his.pojo.dto.*;
import com.alpha.his.util.WSResponseUtils;
import com.alpha.his.webService.UserCardService;
import com.alpha.redis.RedisMrg;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/14.
 */

@WebService(serviceName = "UserCardService"
        , targetNamespace = "http://webService.his.alpha.com"
        , endpointInterface = "com.alpha.his.webService.UserCardService")
@Component
public class UserCardServiceImpl implements UserCardService {

    @Resource
    UserCardInfoMapper userCardInfoMapper;
    @Resource
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${alpha.diagnosis.url}")
    private String diagnosis_server_url;
    @Value("${alpha.h5.url}")
    private String alpha_h5_url;
    @Value("${alpha.h5.medicalUrl}")
    private String h5_medicalRecord_url;
    @Value("${hisSevice.key}")
    private String key;
    @Value("${hospital.code}")
    private String defaultHospitalCode;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Autowired
    private YwzCountTimesService ywzCountTimesService;
    @Autowired
    private CommonsEMRServiceImpl commonsEMRService;
    @Resource
    private HospitalDeptMapper hospitalDeptMapper;

    /**
     * 扫码开卡  根据二维码信息 调我们的信息
     *
     * @param param xml 参数结构
     * @return 返回xml 参数结构
     */

    @Override
    public String patientInfo(String param) {
        logger.info("调用开卡参数是：{}", param);
        ResultData resultData = new ResultData();
        try {
            Map map1 = parm2map(param);
            if (checkParam(map1)) {
                String ord = (String) map1.get("id");
                Integer order = null;
                if (!StringUtils.isBlank(ord)) {
                    order = Integer.parseInt(ord);
                }
                String phone = (String) map1.get("phone");
//                UserCardInfo userCardInfo1param=new UserCardInfo();
//                userCardInfo1param.setCardOrder(Integer.parseInt((String) map1.get("id")));
                UserCardInfo userCardInfo = userCardInfoMapper.orderOrPhone(order, phone);
                if (null != userCardInfo) {
                    UserCardInfoVo userCardInfoVo = new UserCardInfoVo(userCardInfo);
                    resultData.setDataSet(userCardInfoVo);
                } else {
                    resultData = new ResultData(ResponseStatus.PATIENT_INFO_NOTFOUND);
                }
            } else {
                resultData = new ResultData(ResponseStatus.INVALID_VALUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("his 调用开卡信息失败，{}", e.toString());
            resultData = new ResultData(ResponseStatus.FAIL);
        }
        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("xml", ResultData.class);
        String result = XStreamUtils.Object2XMLString(resultData, aliasMap);
        return result;
    }

    /**
     * 嘉和电子病历 显示弹框 接口
     *
     * @param cardNo 门诊号
     * @param pno    挂号号码
     * @param sign
     * @return
     */

    @Override
    public String getEmrInfoUrl(String cardNo, String pno, String sign) {
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
        String url = ParamUtil.formatUrlMap(signMap, true, false);
        url = url + "&key=" + key;
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
    public String getEmrInfoUrlNew(String cardNo, String pno, String doctorName, String sign) {
        String result=commonsEMRService.recordShowUrl(cardNo,pno,doctorName,sign);
        if(StringUtils.isBlank(result)){
            return getEmrInfoUrl(cardNo, pno, sign);
        }
       return result;
    }


    /**
     * 嘉和电子病历  一键导入 获取数据接口
     *
     * @param cardNo 门诊号
     * @param pno    挂号号码
     * @param sign
     * @return
     */
    @Override
    public String getEmrInfoData(String cardNo, String pno, String sign) {
        if (com.alpha.commons.util.StringUtils.isEmpty(cardNo, pno, sign)) {
            ResultDTO resultDTO = new ResultDTO(ResponseStatus.REQUIRED_PARAMETER_MISSING, sign);
            return WSResponseUtils.buildResponseMessage(resultDTO);
        }

        logger.info("医院{}的key为{}", GlobalConstants.SZ_CHILD_HOSPITAL_CODE, key);
        Map<String, String> signMap = new HashMap<>();
        signMap.put("cardNo", cardNo);
        signMap.put("pno", pno);

        String url = ParamUtil.formatUrlMap(signMap, true, false);
        url = url + "&key=" + key;
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
        restTemplate.postForEntity(uri, r_, Integer.class);

        return result;
    }

    @Override
    public String getEmrInfoDataNew(String cardNo, String pno, String importName, String doctorName, String sign) {
       recordImport(cardNo,pno,importName,doctorName);
        return getEmrInfoData(cardNo, pno, sign);
    }

    private Map parm2map(String param) {
        Map map = new HashMap();
        try {
            Document document = XMLUtils.getXMLByString(param);
            Element element = document.getRootElement();
            List<Element> list = element.elements();
            for (Element ele : list) {
                map.put(ele.getName(), ele.getTextTrim());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info("解析开卡参数异常：{}", e.toString());
            return null;
        }
        return map;
    }

    private boolean checkParam(Map map) {
        boolean flag = false;
        try {
            String method = (String) map.get("method");
            String timestamp = (String) map.get("timestamp");
            String idCard = (String) map.get("idCard");
            String phone = (String) map.get("phone");
            String id = (String) map.get("id");
            String sign = (String) map.get("sign");

            if (StringUtils.isBlank(id) && StringUtils.isBlank(phone)) {
                return flag;
            }
            if (StringUtils.isBlank(method) || StringUtils.isBlank(timestamp) ||
                    StringUtils.isBlank(sign)) {
                return flag;
            }
            Map paraMap = new HashMap();
            paraMap.put("method", method);
            paraMap.put("timestamp", timestamp);
            paraMap.put("idCard", idCard);
            paraMap.put("phone", phone);
            paraMap.put("id", id);
            String url = ParamUtil.formatUrlMap(paraMap, true, false);
            url = url + "&key=" + key;
            logger.info("开卡参数拼接是：{}", url);
            String signCheck = MD5.stringToMD5(url).toUpperCase();
            logger.info("加密参数sign值是：{}", signCheck);
            if (sign.equals(signCheck)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("校验开卡参数异常：{}", e.toString());
            return false;
        }
        return flag;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String cardNo = "18062610026";
        String pno = "14104808";
        String doctorName = "王医生";
        String key = "zhihuiyixue@#$%123456";
        Map<String, String> signMap = new HashMap<>();
        signMap.put("cardNo", cardNo);
        signMap.put("pno", pno);
//        signMap.put("doctorName", doctorName);
//        signMap.put("importName", "mainSymptomName,diseases");
        String url = ParamUtil.formatUrlMap(signMap, true, false);
        System.out.println(url);
        url = url + "&key=" + key;
        System.out.println(url);
        String sign1 = MD5.stringToMD5(url).toUpperCase();
        System.out.println(sign1);

    }

    public void recordImport(String cardNo,String pno,String importName,String doctorName) {
        logger.info("医生导入病历：挂号号码：{}，导入部分是：{},医生名字是：{}", pno, importName, doctorName);
        if (!StringUtils.isBlank(importName)) {
            String[] list = importName.split(",");
            for (String str : list) {
                if (str.equals(ImportAndEditeType.IMPORT_61.getText())) {//主诉
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_61.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
                if (str.equals(ImportAndEditeType.IMPORT_62.getText())) {//现病史
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_62.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
                if (str.equals(ImportAndEditeType.IMPORT_63.getText())) {//既往史
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(6);
                    ywzCountTimes.setPno(pno);
                    ywzCountTimes.setDoctorName(doctorName);
                    ywzCountTimes.setDescri(ImportAndEditeType.IMPORT_63.getValue());
                    ywzCountTimesService.addTimes(ywzCountTimes);
                }
                if (str.equals(ImportAndEditeType.IMPORT_64.getText())) {//诊断结果
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
}
