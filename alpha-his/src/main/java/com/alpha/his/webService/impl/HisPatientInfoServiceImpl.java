package com.alpha.his.webService.impl;

import com.alpha.commons.core.mapper.HospitalizedPatientInfoNew1Mapper;
import com.alpha.commons.core.pojo.BaseData;
import com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1;
import com.alpha.commons.core.pojo.ResultData;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.XStreamUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.webService.HisPatientInfoService;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfoNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.jws.WebService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2018/6/15.
 */
@WebService(targetNamespace = "http://webService.his.alpha.com",
        endpointInterface = "com.alpha.his.webService.HisPatientInfoService",serviceName = "HisPatientInfoService")
@Component
public class HisPatientInfoServiceImpl implements HisPatientInfoService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HospitalizedPatientInfoNew1Mapper hospitalizedPatientInfoNew1Mapper;

    @Override
    public String hisPatientInfo(String outPatientNo, String patientName) {
        logger.info("住院获取住院登记信息参数：门诊号{},患者名字{}",outPatientNo,patientName);
        ResultData resultData = new ResultData();
        if(null == patientName && null == outPatientNo){
            resultData.getResult().setCode(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        try {
            HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew1=hospitalizedPatientInfoNew1Mapper.hisPatientInfo
                    (outPatientNo,patientName);
            if(null == hospitalizedPatientInfoNew1){
                resultData.getResult().setCode(ResponseStatus.NULLDATA);
            }else{
                if(null == hospitalizedPatientInfoNew1.getPatientCertiNo()){
                    hospitalizedPatientInfoNew1.setPatientCertiNo("");
                }
                String age=DateUtils.getAgeText(new SimpleDateFormat("yyyy-MM-dd").parse(hospitalizedPatientInfoNew1.getBirthday()), true);
                hospitalizedPatientInfoNew1.setAge(age);
                resultData.setDataSet(hospitalizedPatientInfoNew1);
                
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("xml", ResultData.class);
        String result = XStreamUtils.Object2XMLString(resultData,aliasMap);
        return result;
    }

}
