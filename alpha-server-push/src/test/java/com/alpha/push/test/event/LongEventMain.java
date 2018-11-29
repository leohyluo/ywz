package com.alpha.push.test.event;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by MR.Wu on 2018-07-13.
 */
public class LongEventMain {
    public static void main(String[] args) throws Exception
    {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);

        // Connect the handler
        //disruptor.handleEventsWith(new LongEventHandler());
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            System.out.println("---Event: " + event);
        });

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l < 1000; l++)
        {
            bb.putLong(0, l);
           // producer.onData(bb);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            //Thread.sleep(1000);
        }
    }
}
