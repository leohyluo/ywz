package com.alpha.push.test.handler;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.push.domain.PushInfo;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.BeanUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by MR.Wu on 2018-07-18.
 * 消息事件发布 生产者
 */
public class MessageProducer implements Runnable {

    private RingBuffer<HisRegisterYygh> ringBuffer;

    Disruptor<PushInfo> disruptor;

    private CountDownLatch latch;

    public MessageProducer(RingBuffer<HisRegisterYygh> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 发布事件
     *
     * @param data
     */
    public void onData(HisRegisterYygh data) {
        //请求下一个序列号
        long sequence = ringBuffer.next();
        try {
            //获取此序列号上的事件对象
            HisRegisterYygh h = ringBuffer.get(sequence);
            //设置数据数据对象值
            BeanUtils.copyProperties(data, h);
        } finally {
            ringBuffer.publish(sequence);
        }
        System.out.println("发送消息---------" + data.getCardNo());

    }

    @Override
    public void run() {
        MessageEventTranslator messageEventTranslator = new MessageEventTranslator();
        for (int i = 0; i < 5 ; i++) {
            disruptor.publishEvent(messageEventTranslator);
        }
    }
}
