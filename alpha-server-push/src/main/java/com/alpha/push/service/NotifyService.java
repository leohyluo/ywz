package com.alpha.push.service;

import com.alpha.push.domain.PushInfo;

/**
 * Created by MR.Wu on 2018-07-19.
 */
public interface NotifyService {

    void sendMessage(PushInfo pushInfo);
}
