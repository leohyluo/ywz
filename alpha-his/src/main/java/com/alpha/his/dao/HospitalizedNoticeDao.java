package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;

import java.util.List;

public interface HospitalizedNoticeDao extends IBaseDao<HospitalizedNotice, Long> {

    /**
     * 获取住院记录
     * @param hospitalCode
     * @param hosNo
     * @return
     */
    HospitalizedNotice getByHospitalCodeAndHosNo(String hospitalCode, String hosNo);

    /**
     * 获取住院记录
     * @param hospitalCode
     * @param outPatientNo 门诊号
     * @return
     */
    List<HospitalizedNotice> listByHospitalCodeAndOutPatientNo(String hospitalCode, String outPatientNo);
}
