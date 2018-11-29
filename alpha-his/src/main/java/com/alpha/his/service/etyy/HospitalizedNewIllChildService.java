package com.alpha.his.service.etyy;

import com.alpha.commons.core.service.IBaseService;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;


public interface HospitalizedNewIllChildService extends IBaseService<HospitalizedNewIllChild,Long> {

    /**
     * 根据通知单编码获取已填写的新生患儿信息
     * @param noticeId
     * @return
     */
    HospitalizedNewIllChild getByNoticeId(String noticeId);

    /**
     * 根据门诊号查询最近一次病史采集
     * @param outPatientNo
     * @return
     */
    HospitalizedNewIllChild getLastByOutPatientNo(String outPatientNo);
}
