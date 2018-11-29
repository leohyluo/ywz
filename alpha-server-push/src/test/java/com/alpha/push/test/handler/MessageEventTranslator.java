package com.alpha.push.test.handler;

import com.alpha.push.domain.PushInfo;
import com.lmax.disruptor.EventTranslator;

/**
 * Created by MR.Wu on 2018-07-19.
 * 生产者具体实现方法
 */
public class MessageEventTranslator implements EventTranslator<PushInfo> {

    @Override
    public void translateTo(PushInfo e, long l) {
        System.out.println(e.toString());
    }
}
