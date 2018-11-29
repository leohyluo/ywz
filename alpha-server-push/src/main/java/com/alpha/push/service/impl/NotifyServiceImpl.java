package com.alpha.push.service.impl;

import com.alpha.push.domain.PushInfo;
import com.alpha.push.service.NotifyService;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MR.Wu on 2018-07-19.
 */

public class NotifyServiceImpl implements NotifyService, InitializingBean, DisposableBean {

    private Disruptor<PushInfo> disruptor;

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
        disruptor = new Disruptor<PushInfo>(new EventFactory<PushInfo>() {
            @Override
            public PushInfo newInstance() {
                return new PushInfo();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

//        disruptor.handleEventsWith(new MessagePushHandler());
        disruptor.start();
        System.out.println("suc..............");
    }

    /**
     * 发送消息
     *
     * @param pushInfo
     */
    @Override
    public void sendMessage(PushInfo pushInfo) {
        RingBuffer<PushInfo> ringBuffer = disruptor.getRingBuffer();
        //发送事件填充数据
//        ringBuffer.publishEvent(new EventTranslatorOneArg<PushInfo, PushInfo>() {
//
//            @Override
//            public void translateTo(PushInfo pushInfo, long l, PushInfo pushInfo2) {
//                //复制新对象到event中
//                BeanUtils.copyProperties(pushInfo2, pushInfo);
//            }
//        }, pushInfo);

        ringBuffer.publishEvent((event, l, data) -> {
            BeanUtils.copyProperties(data, event);
        }, pushInfo);
    }
}
