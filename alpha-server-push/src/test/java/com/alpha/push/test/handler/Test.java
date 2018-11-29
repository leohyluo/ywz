package com.alpha.push.test.handler;

/**
 * Created by MR.Wu on 2018-07-18.
 */
public class Test {
    public static void main(String[] args) {
//        Long time = System.currentTimeMillis();
//        RingBuffer<HisRegisterYygh> ringBuffer;
//        //消息生产者
//        MessageProducer producer = null;
//        // 创建缓冲池
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//        // 创建工厂
//        EventFactory<HisRegisterYygh> factory = new EventFactory<HisRegisterYygh>() {
//            @Override
//            public HisRegisterYygh newInstance() {
//                return new HisRegisterYygh();
//            }
//        };
//
//        // 创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
//        int ringBufferSize = 1024 * 1024; //
//        WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
//        // 创建ringBuffer
//        ringBuffer = RingBuffer.create(ProducerType.MULTI, factory, ringBufferSize, YIELDING_WAIT);
//        SequenceBarrier barriers = ringBuffer.newBarrier();
//
//        // 创建10个消费者来处理同一个生产者发的消息(这10个消费者不重复消费消息)
//        MessagePushHandler[] consumers = new MessagePushHandler[10];
//        for (int i = 0; i < consumers.length; i++) {
//            consumers[i] = new MessagePushHandler();
//        }
//
//        WorkerPool<HisRegisterYygh> workerPool = new WorkerPool<HisRegisterYygh>(ringBuffer, barriers,
//                new IntEventExceptionHandler(), consumers);
//
//        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
//        workerPool.start(executor);
//
//        producer = new MessageProducer(ringBuffer);
//
//        //制造消息
//        for (int i = 0; i < 10; i++) {
//            HisRegisterYygh h = new HisRegisterYygh();
//            h.setCardNo("aa--" + i);
//            producer.onData(h);
//        }
//        System.out.println("花费时间 :" + (System.currentTimeMillis() - time));
//
//        HisRegisterYygh h = new HisRegisterYygh();
//        h.setCardNo("#################--");
//        producer.onData(h);
    }
}
