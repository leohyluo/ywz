package com.alpha.his.webService.impl;

import com.alpha.commons.core.pojo.ResultData;
import com.alpha.commons.enums.DiagnosisStatus;
import com.alpha.commons.util.XStreamUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.mapper.UserBasicRecordNewMapper;
import com.alpha.his.webService.HisPatientWeightService;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2018/5/17.
 */
@WebService(targetNamespace = "http://webService.his.alpha.com",
        endpointInterface = "com.alpha.his.webService.HisPatientWeightService",serviceName = "HisPatientWeightService")
@Component
public class HisPatientWeightServiceImpl implements HisPatientWeightService {

    @Resource
   private UserBasicRecordNewMapper userBasicRecordNewMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 内网接口可以 暂时不需要 sign 加密
     * @param doctorName  医生名字
     * @param pno 挂号号码
     * @param mzhm 门诊号码
     * @return
     */
    @Override
    public String patientWeight(String doctorName, String pno, String mzhm) {
        logger.info("his 获取预问诊患者体重参数是：医生名字：{},挂号号码：{},门诊号：{}",doctorName,pno,mzhm);
        ResultData resultData = new ResultData();
        UserBasicRecord param=new UserBasicRecord();
        if(!StringUtils.isBlank(pno)){
            param.setStatus(DiagnosisStatus.PRE_DIAGNOSIS_FINISH.getValue());
            param.setHisRegisterNo(pno);
            UserBasicRecord userBasicRecord= userBasicRecordNewMapper.selectOne(param);
            if(null !=userBasicRecord){
                 if(null ==userBasicRecord.getDoctorName()){
                     if(!StringUtils.isBlank(doctorName)){
                         userBasicRecord.setDoctorName(doctorName);
                         userBasicRecordNewMapper.updateByPrimaryKeySelective(userBasicRecord);//把医生获取过来，更新到我们平台
                     }
                 }
                 if(null == userBasicRecord.getWeight()){
                     resultData.getResult().setCode(ResponseStatus.NULLDATA);
                 }
                 UserBasicRecord userBasicRecord1=new UserBasicRecord();
                 userBasicRecord1.setWeight(userBasicRecord.getWeight());
                 resultData.setDataSet(userBasicRecord1);
            }else {
                logger.info("挂号号码为：{}的患者没有进行预问诊",pno);
                resultData.getResult().setCode(ResponseStatus.NULLDATA);
            }
        }else {
            resultData.getResult().setCode(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }

        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("xml", ResultData.class);
        String result = XStreamUtils.Object2XMLString(resultData, aliasMap);

        return result;
    }

}

