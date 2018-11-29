package com.alpha.push.handler;

import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.push.domain.PushInfo;
import com.alpha.push.domain.RetryPolicyMessagePush;
import com.alpha.push.service.PushMessageService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MR.Wu on 2018-07-18.
 * 消费者(消息重试)  事件处理器
 */
public class MessageWxRetryPushHandler implements EventHandler<PushInfo>, WorkHandler<PushInfo> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private PushMessageService pushMessageService;

    private HisRegisterYyghMapper hisRegisterYyghMapper;

    private String redisIp;

    private String redisPort;

    private String redisPwd;

    public MessageWxRetryPushHandler(PushMessageService pushMessageService, HisRegisterYyghMapper hisRegisterYyghMapper,
                                     String redisIp, String redisPort, String redisPwd) {
        this.pushMessageService = pushMessageService;
        this.hisRegisterYyghMapper = hisRegisterYyghMapper;
        this.redisIp = redisIp;
        this.redisPort = redisPort;
        this.redisPwd = redisPwd;
    }

    @Override
    public void onEvent(PushInfo messageEvent, long l, boolean b) throws Exception {
        this.onEvent(messageEvent);
    }

    @Override
    public void onEvent(PushInfo e) throws Exception {
        //业务逻辑处理
        RetryPolicyMessagePush retryPolicyMessagePush = e.getRetryPolicy();
        //重试起始时间
        String startTime = retryPolicyMessagePush.getStartTime();
        //重试表达试
        String cron = retryPolicyMessagePush.getRetryCron();
        //余下的重试次数
        int surplusTimes = retryPolicyMessagePush.getSurplusTimes();

        if(0 == surplusTimes){
            //终止重试，从队列中删除

        }

    }

}
