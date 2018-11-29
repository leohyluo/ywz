package com.alpha.push.test;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * Created by MR.Wu on 2018-07-19.
 * 消息者
 */
public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>,WorkHandler<TradeTransaction> {

    @Override
    public void onEvent(TradeTransaction event, long sequence,
                        boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(TradeTransaction event) throws Exception {
        //这里做具体的消费逻辑
        event.setId(UUID.randomUUID().toString());//简单生成下ID
        System.out.println(event.getId()+" -- " + event.getPrice());
    }

}
