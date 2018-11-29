package com.alpha.push.service;


import com.alpha.push.domain.HisRegisterRecord;
import com.alpha.push.domain.HospitalDept;

/**
 * 挂号业务类
 */
public interface RegisterService {


    /**
     * 查询挂号信息
     * @param outPatientNo
     * @param pno
     * @return
     */
    HisRegisterRecord getRegisterRecord(String outPatientNo, String pno);


    void updateHisRegisterRecord(HisRegisterRecord hisRegisterRecord);
}
