package com.alpha.push.test.event;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MR.Wu on 2018-07-13.
 */
public class DisruptorStart {
    public static void main(String[] args) {
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory,
                ringBufferSize, executor, ProducerType.SINGLE,
                new YieldingWaitStrategy());

        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();
    }
}
