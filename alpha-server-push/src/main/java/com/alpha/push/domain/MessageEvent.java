package com.alpha.push.domain;

import com.alpha.commons.core.pojo.HisRegisterYygh;

/**
 * Created by MR.Wu on 2018-07-19.
 * 消息传送体
 */
public class MessageEvent {

    /**
     * 推送给消费者的消息体
     */
    private PushInfo pushInfo;

    /**
     * 病人信息
     */
    private HisRegisterYygh hisRegisterYygh;

    public PushInfo getPushInfo() {
        return pushInfo;
    }

    public void setPushInfo(PushInfo pushInfo) {
        this.pushInfo = pushInfo;
    }

    public HisRegisterYygh getHisRegisterYygh() {
        return hisRegisterYygh;
    }

    public void setHisRegisterYygh(HisRegisterYygh hisRegisterYygh) {
        this.hisRegisterYygh = hisRegisterYygh;
    }
}
