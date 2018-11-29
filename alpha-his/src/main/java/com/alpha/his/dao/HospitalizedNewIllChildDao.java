package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;

public interface HospitalizedNewIllChildDao extends IBaseDao<HospitalizedNewIllChild, Long> {

    /**
     * 获取住院记录
     * @param hospitalCode
     * @param hosNo
     * @return
     */
    HospitalizedNewIllChild getByHospitalCodeAndHosNo(String hospitalCode, String hosNo);
}
