package com.alpha.push.handler;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.push.domain.MessageEvent;
import com.alpha.push.domain.PushInfo;
import com.alpha.push.domain.PushResp;
import com.alpha.push.service.PushMessageService;
import com.alpha.redis.RedisMrg;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MR.Wu on 2018-07-18.
 * 消费者  事件处理器
 */
public class MessageWxPushHandler implements EventHandler<MessageEvent>, WorkHandler<MessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private PushMessageService pushMessageService;

    private HisRegisterYyghMapper hisRegisterYyghMapper;

    private String redisIp;

    private String redisPort;

    private String redisPwd;

    public MessageWxPushHandler(PushMessageService pushMessageService, HisRegisterYyghMapper hisRegisterYyghMapper,
                                String redisIp, String redisPort, String redisPwd) {
        this.pushMessageService = pushMessageService;
        this.hisRegisterYyghMapper = hisRegisterYyghMapper;
        this.redisIp = redisIp;
        this.redisPort = redisPort;
        this.redisPwd = redisPwd;
    }

    @Override
    public void onEvent(MessageEvent messageEvent, long l, boolean b) throws Exception {
        this.onEvent(messageEvent);
    }

    /**
     * 同业务调用线程
     * @param e
     * @throws Exception
     */
    @Override
    public void onEvent(MessageEvent e) throws Exception {
        //业务逻辑处理
        logger.info("接收到消息 {} --  {}", Thread.currentThread().getName(), e.getPushInfo().getCardNo());
        PushInfo pushInfo = e.getPushInfo();
        HisRegisterYygh appointmentPatient = e.getHisRegisterYygh();
        long l = System.currentTimeMillis();
        PushResp pushResp = pushMessageService.pushWeiXinMsg(pushInfo);
        if (pushResp != null && pushResp.getResult() == 1) {
            //消息推送成功后更新状态
            logger.info("{} 给患者{}-{}推送消息成功,时间为：{}", Thread.currentThread().getName(), appointmentPatient.getPatientName(), appointmentPatient.getOutPatientNo(), (System.currentTimeMillis() - l));

            //这里只更新缓存中的内容，数据落地由定时器完成
            String tempKey = GlobalConstants.REDIS_KEY_PNO_FLAG_ + appointmentPatient.getPno();
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).updateKey(tempKey, GlobalConstants.HIS_STATUS_SUCCESS, RedisMrg.DB6);
        } else {
            //重试
            pushMessageService.retryPush(pushInfo);
        }
    }

    private void updateHisRegisterStatus(HisRegisterYygh appointmentPatient, Integer status) {
        //更新状态
        appointmentPatient.setStatus(status);
        hisRegisterYyghMapper.updateByPrimaryKey(appointmentPatient);
    }

    public static void main(String[] args) {

    }
}
