package com.alpha.push.service;


import com.alpha.push.domain.HisRegisterRecord;

import java.util.List;

/**
 * Created by MR.Wu on 2018-07-02.
 */
public interface RegisiterService {

    /**
     * 根据挂号码获取挂号信息
     * @param hisRegisterNo
     * @return
     */
    HisRegisterRecord getHisRegisterRecord(String hisRegisterNo);

    /**
     * 保存挂号记录
     * @param hisRegisterRecordList
     */
    void saveHisRegisterRecord(List<HisRegisterRecord> hisRegisterRecordList);

}
