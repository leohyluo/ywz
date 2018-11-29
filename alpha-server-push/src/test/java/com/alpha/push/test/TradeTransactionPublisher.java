package com.alpha.push.test;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by MR.Wu on 2018-07-19.
 * 生产者
 */
public class TradeTransactionPublisher implements Runnable{
    Disruptor<TradeTransaction> disruptor;
    private CountDownLatch latch;
    private static int LOOP=10;//模拟一千万次交易的发生

    public TradeTransactionPublisher(CountDownLatch latch,Disruptor<TradeTransaction> disruptor) {
        this.disruptor=disruptor;
        this.latch=latch;
    }

    @Override
    public void run() {
        TradeTransactionEventTranslator tradeTransloator=new TradeTransactionEventTranslator();
        System.out.println("-----------------");
        for(int i=0;i<LOOP;i++){
            //在发布一条Event的时候，这些Translator的translate方法会被调用
            disruptor.publishEvent(tradeTransloator);
        }
        latch.countDown();
    }


}
