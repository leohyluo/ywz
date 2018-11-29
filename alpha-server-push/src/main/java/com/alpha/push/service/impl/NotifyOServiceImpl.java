package com.alpha.push.service.impl;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.push.handler.MessageProccessHandler;
import com.alpha.push.service.AppointmentService;
import com.alpha.push.service.NotifyOService;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MR.Wu on 2018-07-19.
 */
@Service
public class NotifyOServiceImpl implements NotifyOService, InitializingBean, DisposableBean {

    private Disruptor<HisRegisterYygh> disruptor;

    @Autowired
    private AppointmentService appointmentService;

    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    @Override
    public void destroy() throws Exception {
        disruptor.shutdown();
    }

    /**
     * 系统初始化 初始化 disruptor
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        int bufferSize = 1024;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        disruptor = new Disruptor<HisRegisterYygh>(new EventFactory<HisRegisterYygh>() {
            @Override
            public HisRegisterYygh newInstance() {
                return new HisRegisterYygh();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 创建10个消费者来处理同一个生产者发的消息(这10个消费者不重复消费消息)
        MessageProccessHandler[] worksHandlers = new MessageProccessHandler[10];
        for (int i = 0; i < worksHandlers.length; i++) {
            worksHandlers[i] = new MessageProccessHandler(appointmentService);
        }
        disruptor.handleEventsWithWorkerPool(worksHandlers);

        //消息者处理器(单消费者)
        disruptor.start();
        System.out.println("消息预处理 suc..............");
    }

    /**
     * 发送消息
     *
     * @param hisRegisterYygh
     */
    @Override
    public void sendMessage(HisRegisterYygh hisRegisterYygh) {
        RingBuffer<HisRegisterYygh> ringBuffer = disruptor.getRingBuffer();
        //发送事件填充数据
        ringBuffer.publishEvent((event, l, data) -> {
            BeanUtils.copyProperties(data, event);
        }, hisRegisterYygh);
    }
}
