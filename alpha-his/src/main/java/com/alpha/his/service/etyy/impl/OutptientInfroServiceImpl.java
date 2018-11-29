package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.mapper.OutPatientMapper;
import com.alpha.commons.core.pojo.OutPatientInfo;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.his.service.etyy.OutptientInfroService;
import com.alpha.commons.core.util.StaticHttpclientCall;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/6/4.
 * 获取门诊一卡通信息基本信息
 */
@Service
public class OutptientInfroServiceImpl implements OutptientInfroService {

    @Value("${hisSevice.outpatientWSDL}")
    private String outpatientWSDL;
    @Value("${hisSevice.outpatientFID}")
    private String outpatientFID;

    @Autowired
    OutPatientMapper outPatientMapper;

    @Override
    public List<OutPatientInfo> getOutpatientInfoByMZHM(String patientName, String patientId, String mzhm, String cardNo) {
        List<OutPatientInfo> listout=new ArrayList<>();
        if(StringUtils.isBlank(mzhm)){
            return null;
        }
        OutPatientInfo param=new OutPatientInfo();
        param.setOutpatientNo(mzhm);
        OutPatientInfo outPatientInfo=outPatientMapper.selectOne(param);
        if(null != outPatientInfo){
            listout.add(outPatientInfo);
            return listout;
        }
        String resultXml= StaticHttpclientCall.outPatientParam(outpatientWSDL,outpatientFID, mzhm);
        if(null == resultXml){
            return null;
        }
        List<String> list = SoapUtil.parseETYYxml(resultXml);
        OutPatientInfo a=new OutPatientInfo();
        String str=a.getClass().toString();
        str=str.replace("class ","");
        str=str.replace(" ","");
        if(null ==list || list.size()<1){
          return null;
        }else {
            List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.outpatientmap);
            objectList.stream().forEach(e -> {listout.add((OutPatientInfo)e);
            });
            outPatientMapper.insertList(listout);
        }
        return listout;
    }
}
