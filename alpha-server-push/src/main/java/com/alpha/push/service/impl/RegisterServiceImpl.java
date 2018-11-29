package com.alpha.push.service.impl;

import com.alpha.push.domain.HisRegisterRecord;
import com.alpha.push.domain.HospitalDept;
import com.alpha.push.mapper.HisRegisterRecordMapper;
import com.alpha.push.service.RegisterService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HisRegisterRecordMapper hisRegisterRecordMapper;

    @Override
    public HisRegisterRecord getRegisterRecord(String outPatientNo, String pno) {
        Map<String, String> param = new HashedMap();
        param.put("outPatientNo", outPatientNo);
        param.put("pno", pno);
        return hisRegisterRecordMapper.getByOutPatientNoAndPno(param);
    }

    @Override
    public void updateHisRegisterRecord(HisRegisterRecord hisRegisterRecord) {
        hisRegisterRecordMapper.update(hisRegisterRecord);
    }
}
