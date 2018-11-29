package com.alpha.push.service.impl;

import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.push.domain.MessageEvent;
import com.alpha.push.handler.MessageWxPushHandler;
import com.alpha.push.service.NotifyPushService;
import com.alpha.push.service.PushMessageService;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MR.Wu on 2018-07-20.
 * 消息推送
 */
@Service
public class NotifyPushServiceImpl implements NotifyPushService, InitializingBean, DisposableBean {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    private Disruptor<MessageEvent> disruptor;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Override
    public void sendMessage(MessageEvent messageEvent) {
        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();
        //发送事件填充数据
        ringBuffer.publishEvent((event, l, data) -> {
            BeanUtils.copyProperties(data, event);
        }, messageEvent);
    }

    @Override
    public void destroy() throws Exception {
        disruptor.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int bufferSize = 1024;
        ExecutorService executor = Executors.newFixedThreadPool(20);
        disruptor = new Disruptor<MessageEvent>(new EventFactory<MessageEvent>() {
            @Override
            public MessageEvent newInstance() {
                return new MessageEvent();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 创建10个消费者来处理同一个生产者发的消息(这10个消费者不重复消费消息)
        MessageWxPushHandler[] worksHandlers = new MessageWxPushHandler[20];
        for (int i = 0; i < worksHandlers.length; i++) {
            worksHandlers[i] = new MessageWxPushHandler(pushMessageService, hisRegisterYyghMapper, redisIp, redisPort, redisPwd);
        }
        disruptor.handleEventsWithWorkerPool(worksHandlers);

        disruptor.start();
        System.out.println("消息推送 suc..............");
    }
}
