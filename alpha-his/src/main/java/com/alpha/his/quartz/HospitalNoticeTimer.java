package com.alpha.his.quartz;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HospitalizedNoticeNewMapper;
import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.his.pojo.dto.PushResp;
import com.alpha.his.service.etyy.PushMessageService;
import com.alpha.redis.RedisMrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2018/7/2.
 * 定时推送 住院登记链接 到患者预留在通知单上面的联系电话号码
 * <p>
 * 只推送一次，未关注的用户扫描通知单上面的二维码也是可以的
 */
@Component
public class HospitalNoticeTimer {

    private static Logger logger = LoggerFactory.getLogger(HospitalNoticeTimer.class);

    @Resource
    private HospitalizedNoticeNewMapper hospitalizedNoticeNewMapper;

    @Resource
    private PushMessageService pushMessageService;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    //一分钟推一次
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void pushNotice() {
        //noticePushSwitch 推送开关：0 不推送，1，推送开启
        String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat("notice_push_switch");
        SysConfig sysConfig =  (SysConfig) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(key, RedisMrg.DB1);
        String noticePushSwitch = sysConfig.getConfigValue();
        if (noticePushSwitch.equals("0")) {
            logger.info("住院推送暂时关闭");
            return;
        }
        long startTime = System.currentTimeMillis();
        //1. 查询所有没有推送过的通知单
        List<HospitalizedNoticeNew> list = hospitalizedNoticeNewMapper.selectAllNotice();
        if (null == list || list.size() < 1) {
            return;
        }
        list.stream().forEach(e -> {
            //2.推送  只推送住院通知单上留下的手机号
            PushResp resp = pushMessageService.pushNotice(e);
            //3.更新为已推送  （resp 不管成功失败）
            e.setStatus(1);
            e.setPushTimes(1);
            e.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            hospitalizedNoticeNewMapper.updateByPrimaryKeySelective(e);
        });
        logger.info("推送住院通知单共{}条，总耗时{}毫秒", list.size(), (System.currentTimeMillis() - startTime));
    }


}
