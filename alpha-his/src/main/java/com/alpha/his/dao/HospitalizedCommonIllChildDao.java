package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;

public interface HospitalizedCommonIllChildDao extends IBaseDao<HospitalizedCommonIllChild, Long> {

    /**
     * 获取住院记录
     * @param hospitalCode
     * @param hosNo
     * @return
     */
    HospitalizedCommonIllChild getByHospitalCodeAndHosNo(String hospitalCode, String hosNo);
}
