package com.alpha.push.mapper;


import com.alpha.push.domain.UserBasicRecord;

/**
 * Created by MR.Wu on 2018-07-31.
 */
public interface UserBasicRecordMapper {

    /**
     * 根据挂号码/预约码获取现场号已完成的就诊记录
     * @param pno
     * @return
     */
    int getForLive(String pno);
    
    

    /**
     * 根据挂号码/预约码获预约号已完成的就诊记录
     * @param pno
     * @return
     */
    int getForAppointment(String pno);

    UserBasicRecord getForLiveObject(String yno);
}
