package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.his.OutPatientService;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.his.service.etyy.HospitalService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by HP on 2018/9/30.
 */
public class ETYYHisRegisterImpl implements OutPatientService {

    @Resource
    HospitalService hospitalService;

    @Override
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime) {
        return hospitalService.registrationInfo(outPatientNo,visitTime);
    }

    @Override
    public Object patientInfo(String cardNo) {
        return null;
    }

    @Override
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime) {
        return null;
    }

    @Override
    public void NoticeData(String startTime, String endTime) {

    }
}
