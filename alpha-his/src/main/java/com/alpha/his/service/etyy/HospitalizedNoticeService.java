package com.alpha.his.service.etyy;

import com.alpha.commons.core.service.IBaseService;
import com.alpha.his.pojo.dto.UserHospitalized;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;

import java.util.List;

/**
 * 住院业务接口类
 */
public interface HospitalizedNoticeService extends IBaseService<HospitalizedNotice,Long> {

    /**
     * 根据通知单编号查询通知单
     * @param noticeId
     * @return
     */
    HospitalizedNotice getByNoticeId(String noticeId);
}
