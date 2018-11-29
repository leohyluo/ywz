package com.alpha.his.service.etyy;

import com.alpha.commons.core.service.IBaseService;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;


public interface HospitalizedCommonIllChildService extends IBaseService<HospitalizedCommonIllChild,Long> {

    /**
     * 根据通知单编码获取已填写的普通患儿信息
     * @param noticeId
     * @return
     */
    HospitalizedCommonIllChild getByNoticeId(String noticeId);

    /**
     * 根据门诊号查询最近一次病史采集
     * @param outPatientNo
     * @return
     */
    HospitalizedCommonIllChild getLastByOutPatientNo(String outPatientNo);
}
