package com.alpha.push.test;

import com.lmax.disruptor.EventTranslator;

import java.util.Random;

/**
 * Created by MR.Wu on 2018-07-19.
 */
public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private Random random=new Random();
    @Override
    public void translateTo(TradeTransaction event, long sequence) {
        this.generateTradeTransaction(event);
    }
    private TradeTransaction generateTradeTransaction(TradeTransaction trade){
        System.out.println("@@@@@@@@@@@@");
        trade.setPrice(random.nextDouble()*9999);
        return trade;
    }

}
