package com.alpha.his.controller;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.service.HospitalInfoService;
import com.alpha.his.service.etyy.HospitalService;
import com.alpha.his.service.etyy.UrlEncryptService;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private HospitalInfoService hospitalInfoService;

    @Resource
    private HospitalService hospitalService;

    @Resource
    private UrlEncryptService urlEncryptService;

    @Value("${hospital.code}")
    private String defaultHospitalCode;

    @PostMapping("/get")
    public HospitalInfo getHospitalInfo(String hospitalCode) {
        if(StringUtils.isEmpty(hospitalCode)) {
            logger.warn("获取医院信息缺少必要参数");
            return null;
        }
        return hospitalService.getHospitalInfo(hospitalCode);
    }

    /**
     * 保存医生工作站编辑后的病历
     * @param allParam
     * @return
     */
    @GetMapping("/diagnosisResult/save")
    public ResponseMessage saveDiagnosisResult(String allParam) {
        try {
            HisDiagnosisRecord hisDiagnosisRecord = JSON.parseObject(allParam,HisDiagnosisRecord.class);
            if(hisDiagnosisRecord == null) {
                logger.error("hisDiagnosisRecord is null");
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
            if(hisDiagnosisRecord.getDiagnosisId() == null) {
                logger.error("diagnosisId is null");
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
            hospitalService.saveHisDiagnosisRecord(hisDiagnosisRecord);
            return WebUtils.buildSuccessResponseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重定向地址
     * @param url
     * @return
     */
    @PostMapping("/link")
    public ResponseMessage link(String url) {
        if(StringUtils.isEmpty(url)) {
            logger.warn("缺少必要参数");
            return null;
        }
        return urlEncryptService.getDecryptUrl(url);
    }

    @GetMapping("address")
    public ResponseMessage HotAdress(String keyword,Integer type){
        if(null == type){
            return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
        }
        Map<String,Object> list=hospitalService.HotAdress(keyword,type);
        return WebUtils.buildSuccessResponseMessage(list);
    }

    @PostMapping("/initial")
    public ResponseMessage initial() {
        HospitalInfo hospitalInfo =  hospitalInfoService.getByHospitalCode(defaultHospitalCode);
        logger.info("hospital {} hospitalInfo is {}", defaultHospitalCode, hospitalInfo);
        return WebUtils.buildSuccessResponseMessage(hospitalInfo);
    }

    /**
     * 点击科室时验证患者年龄性别是否与科室匹配（暂无使用）
     * @param appType
     * @param pno
     * @return
     */
    @PostMapping("/dept/validate/{appType}/{pno}")
    public ResponseMessage deptValidate(@PathVariable String appType, @PathVariable String pno) {
        hospitalInfoService.deptValidate(appType, pno);
        return WebUtils.buildSuccessResponseMessage();
    }
}
