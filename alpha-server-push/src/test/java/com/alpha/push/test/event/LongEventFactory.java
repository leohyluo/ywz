package com.alpha.push.test.event;

import com.lmax.disruptor.EventFactory;

/**
 * Created by MR.Wu on 2018-07-13.
 * Disruptor 通过 EventFactory 在 RingBuffer 中预创建 Event 的实例
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
