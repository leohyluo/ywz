package com.alpha.push.service;

import com.alpha.push.domain.PushInfo;

/**
 * Created by MR.Wu on 2018-07-19.
 * 消息推送接口服务类
 */
public interface NotifyRetryService {

    void sendMessage(PushInfo pushInfo);
}
