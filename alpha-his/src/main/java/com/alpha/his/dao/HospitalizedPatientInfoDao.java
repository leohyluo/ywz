package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfoNew;

public interface HospitalizedPatientInfoDao extends IBaseDao<HospitalizedPatientInfoNew, Long> {

    /**
     * 获取住院记录
     * @param hospitalCode
     * @param hosNo
     * @return
     */
    HospitalizedPatientInfo getByHospitalCodeAndHosNo(String hospitalCode, String hosNo);

}
