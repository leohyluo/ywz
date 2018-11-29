package com.alpha.push.service;


import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.push.domain.PushInfo;
import com.alpha.push.domain.PushResp;
import com.alpha.push.excepton.RetryException;

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

    void retryPush(PushInfo pushInfo) throws RetryException;

    void pushMessage(HisRegisterYygh hisRegisterYygh);
}
