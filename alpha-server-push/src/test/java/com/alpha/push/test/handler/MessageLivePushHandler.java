package com.alpha.push.test.handler;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.lmax.disruptor.EventHandler;

/**
 * Created by MR.Wu on 2018-07-18.
 */
public class MessageLivePushHandler implements EventHandler<HisRegisterYygh> {

    @Override
    public void onEvent(HisRegisterYygh hisRegisterYygh, long l, boolean b) throws Exception {

    }
}
