package com.alpha.commons.core.init;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.enums.HospitalCode;
import com.alpha.redis.RedisMrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by MR.Wu on 2018-07-05.
 */
@Component
public class ApplicationStartup implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Value("${hospital.code}")
    private  String code;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("=============系统初始化===============");
        //存放为120秒
        RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire(GlobalConstants.REDIS_KEY_WEIXIN_LOG_FLAG, "1", 120, RedisMrg.DB3);
        logger.info("=============系统初始化=======完成========");
        logger.info("正在运行的是："+ HospitalCode.getText(code)+"系统");
    }
}
