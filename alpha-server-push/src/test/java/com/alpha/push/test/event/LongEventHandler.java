package com.alpha.push.test.event;

import com.lmax.disruptor.EventHandler;

/**
 * Created by MR.Wu on 2018-07-13.
 * 定义事件处理的具体实现
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        //System.out.println("Event: " + event);
    }
}
