package com.alpha.push.service.impl;

import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.push.domain.PushInfo;
import com.alpha.push.handler.MessageWxRetryPushHandler;
import com.alpha.push.service.NotifyRetryService;
import com.alpha.push.service.PushMessageService;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MR.Wu on 2018-07-23.
 * 消息重试 生产者
 */
@Deprecated
public class NotifyRetryServiceImpl implements NotifyRetryService, InitializingBean, DisposableBean {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    private Disruptor<PushInfo> disruptor;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Override
    public void sendMessage(PushInfo pushInfo) {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int bufferSize = 1024;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        disruptor = new Disruptor<PushInfo>(new EventFactory<PushInfo>() {
            @Override
            public PushInfo newInstance() {
                return new PushInfo();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 创建10个消费者来处理同一个生产者发的消息(这10个消费者不重复消费消息)
        MessageWxRetryPushHandler[] worksHandlers = new MessageWxRetryPushHandler[10];
        for (int i = 0; i < worksHandlers.length; i++) {
            worksHandlers[i] = new MessageWxRetryPushHandler(pushMessageService, hisRegisterYyghMapper, redisIp, redisPort, redisPwd);
        }
        disruptor.handleEventsWithWorkerPool(worksHandlers);

        disruptor.start();
        System.out.println("消息重试推送 suc..............");
    }
}
