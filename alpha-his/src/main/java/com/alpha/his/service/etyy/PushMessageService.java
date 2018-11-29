package com.alpha.his.service.etyy;

import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.his.pojo.dto.PushInfo;
import com.alpha.his.pojo.dto.PushResp;

/**
 * Created by MR.Wu on 2018-06-25.
 */
public interface PushMessageService {

    /**
     * 消息推送接口
     * @param pushInfo
     * @return
     */
    PushResp pushWeiXinMsg(PushInfo pushInfo);


    /**
     * 住院通知单推送
     * @param url    住院信息登记url
     * @param phone  推送url
     * @param mzhm   住院通知单上面的门诊号码
     * @return
     */
    PushResp pushNotice(HospitalizedNoticeNew hospitalizedNoticeNew);

}
