package com.alpha.push.timer;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.push.domain.EnUrlDTO;
import com.alpha.push.mapper.EnUrlMapper;
import com.alpha.push.service.NotifyOService;
import com.alpha.redis.RedisMrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by MR.Wu on 2018-07-02.
 * 数据处理定时类
 * 包括定时器：
 * 一、timerGo:消息推送服务  每一分钟执行一次
 * 二、syncEnUrlData:加密URL地址信息  每天凌晨12点 与  每天中午12点同步数据到BD中
 * 三、syncPnoToDb:推送信息状态值 每五分钟更新一次
 * 四、推送日志记录：每两分钟同步一次到DB中
 */
@Component
public class DataTimer {

    @Value("${alpha.diagnosis.url}")
    private String diagnosis_server_url;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @Autowired
    private NotifyOService notifyoService;

    @Autowired
    private EnUrlMapper enUrlMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定时器主方法 每一分钟执行一次 * 0/1 * * * ?
     */
    @Deprecated
    //@Scheduled(cron = "0 0/1 * * * ?")
    private void timerGo() {
        long start = System.currentTimeMillis();
        String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat("enable_push");
        SysConfig sysConfig = (SysConfig) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(key, RedisMrg.DB1);
        if (sysConfig != null) {
            Object flag = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(GlobalConstants.REDIS_KEY_SHECHED_FLAG_, RedisMrg.DB2);
            if ("1".equals(sysConfig.getConfigValue()) && null == flag) {
                //定时任务启动中标识
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(GlobalConstants.REDIS_KEY_SHECHED_FLAG_, "1", RedisMrg.DB2);
                List<HisRegisterYygh> registerList = hisRegisterYyghMapper.listNeedPushRecord();
                registerList.forEach(e -> {

                    e.setStatus(GlobalConstants.HIS_STATUS_WAIT);

                    //暂时存入缓存中，由定时器进行数据库落地
                    String tempKey = GlobalConstants.REDIS_KEY_PNO_FLAG_ + e.getPno();
                    RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(tempKey, e.getStatus(), RedisMrg.DB6);

                    //发送事件
                    notifyoService.sendMessage(e);
                });

                //将数据库状态改为 -2 以免下次取到脏数据
                if (null != registerList && !registerList.isEmpty())
                    hisRegisterYyghMapper.batchUpdateStatus(registerList);

                //结束定时任务
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey(GlobalConstants.REDIS_KEY_SHECHED_FLAG_, RedisMrg.DB2);
            } else {
                logger.warn("推送开关处于关闭状态");
            }
        } else {
            logger.error("没有推送的配置，请检查数据库和redis");
        }
        logger.info("总时间：" + (System.currentTimeMillis() - start));
    }

    /**
     * 每天凌晨12点 与  每天中午12点同步数据到BD中 cron = "0 0 12,00 * * ?"
     */
    @Scheduled(cron = "0 0 12,00 * * ?")
    protected void syncEnUrlData() {
        //取出缓存中的数据
        String partKeys = GlobalConstants.REDIS_KEY_ENURL_FLAG_ + "*";
        Set<String> obj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeys(RedisMrg.DB5, partKeys);
        if (null != obj && !obj.isEmpty()) {
            logger.info("正在将URL信息入库中..........");
            //批量存入DB中
            List<EnUrlDTO> enUrlDTOList = new ArrayList<>();
            Set<String> keys = (Set<String>) obj;
            keys.stream().forEach(e -> {
                String subKey = e;
                Object subObj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(subKey, RedisMrg.DB5);
                if (null != subObj) {
                    String url = (String) subObj;
                    EnUrlDTO enUrlDTO = new EnUrlDTO();
                    enUrlDTO.setEnUrl(subKey.substring(subKey.lastIndexOf("_") + 1, subKey.length()));
                    enUrlDTO.setUrl(url);
                    enUrlDTOList.add(enUrlDTO);
                    RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey(subKey, RedisMrg.DB5);
                }
            });
            if (!enUrlDTOList.isEmpty()) {
                enUrlMapper.batchAddData(enUrlDTOList);
                logger.info("URL信息入库完成..........");
            }
        }
    }

    /**
     * 将状态值进行更新---微信推送
     * 每五分钟更新一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    protected void syncPnoToDb() {
        String tempKey = GlobalConstants.REDIS_KEY_PNO_FLAG_ + "*";
        Set<String> obj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeys(RedisMrg.DB6, tempKey);
        if (null != obj && !obj.isEmpty()) {
            logger.info("正在状态值入库中..........");
            //批量存入DB中
            List<HisRegisterYygh> list = new ArrayList<>();
            Set<String> keys = (Set<String>) obj;
            keys.stream().forEach(e -> {
                String subKey = e;
                Object subObj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(subKey, RedisMrg.DB6);
                if (null != subObj) {
                    HisRegisterYygh hisRegisterYygh = new HisRegisterYygh();
                    //在缓存中设置pno的状态值
                    hisRegisterYygh.setPno(subKey.substring(subKey.lastIndexOf("_") + 1, subKey.length()));
                    hisRegisterYygh.setStatus(Integer.valueOf(subObj.toString()));
                    list.add(hisRegisterYygh);
                    RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey(subKey, RedisMrg.DB6);
                }
            });
            if (!list.isEmpty()) {
                hisRegisterYyghMapper.batchUpdateStatus(list);
                logger.info("状态值入库完成..........");
            }
        }
    }
}
