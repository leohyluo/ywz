package com.alpha.push.test;

import com.lmax.disruptor.EventHandler;

/**
 * Created by MR.Wu on 2018-07-19.
 */
public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {

    @Override
    public void onEvent(TradeTransaction event, long sequence,
                        boolean endOfBatch) throws Exception {
        //do send jms message
        System.out.println("开始发送消息..."+event.getPrice());
    }

}
